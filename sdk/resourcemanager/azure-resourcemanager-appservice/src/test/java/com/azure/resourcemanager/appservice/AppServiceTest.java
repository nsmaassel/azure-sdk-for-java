// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.resourcemanager.appservice;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Post;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.FluxUtil;
import com.azure.resourcemanager.appservice.models.AppServiceCertificateOrder;
import com.azure.resourcemanager.appservice.models.AppServiceDomain;
import com.azure.resourcemanager.appservice.models.PublishingProfile;
import com.azure.resourcemanager.keyvault.KeyVaultManager;
import com.azure.resourcemanager.msi.MsiManager;
import com.azure.resourcemanager.resources.fluentcore.arm.CountryIsoCode;
import com.azure.resourcemanager.resources.fluentcore.arm.CountryPhoneCode;
import com.azure.core.management.Region;
import com.azure.core.management.profile.AzureProfile;
import com.azure.resourcemanager.resources.ResourceManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.azure.resourcemanager.resources.fluentcore.utils.HttpPipelineProvider;
import com.azure.resourcemanager.resources.fluentcore.utils.ResourceManagerUtils;
import com.azure.resourcemanager.test.ResourceManagerTestBase;
import com.azure.resourcemanager.test.utils.TestDelayProvider;
import com.azure.resourcemanager.test.utils.TestIdentifierProvider;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Assertions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

/** The base for app service tests. */
public class AppServiceTest extends ResourceManagerTestBase {
    protected ResourceManager resourceManager;
    protected KeyVaultManager keyVaultManager;
    protected AppServiceManager appServiceManager;
    protected MsiManager msiManager;

    protected AppServiceDomain domain;
    protected AppServiceCertificateOrder certificateOrder;
    protected String rgName = "";

    @Override
    protected HttpPipeline buildHttpPipeline(
        TokenCredential credential,
        AzureProfile profile,
        HttpLogOptions httpLogOptions,
        List<HttpPipelinePolicy> policies,
        HttpClient httpClient) {
        return HttpPipelineProvider.buildHttpPipeline(
            credential,
            profile,
            null,
            httpLogOptions,
            null,
            new RetryPolicy("Retry-After", ChronoUnit.SECONDS),
            policies,
            httpClient);
    }

    @Override
    protected void initializeClients(HttpPipeline httpPipeline, AzureProfile profile) {
        rgName = generateRandomResourceName("javacsmrg", 20);
        ResourceManagerUtils.InternalRuntimeContext.setDelayProvider(new TestDelayProvider(!isPlaybackMode()));
        ResourceManagerUtils.InternalRuntimeContext internalContext = new ResourceManagerUtils.InternalRuntimeContext();
        internalContext.setIdentifierFunction(name -> new TestIdentifierProvider(testResourceNamer));
        resourceManager =
            ResourceManager.authenticate(httpPipeline, profile).withDefaultSubscription();
        keyVaultManager = KeyVaultManager.authenticate(httpPipeline, profile);
        appServiceManager = AppServiceManager.authenticate(httpPipeline, profile);
        msiManager = MsiManager.authenticate(httpPipeline, profile);
        setInternalContext(internalContext, appServiceManager, msiManager);

        // useExistingDomainAndCertificate();
        // createNewDomainAndCertificate();
    }

    @Override
    protected void cleanUpResources() {
        resourceManager.resourceGroups().beginDeleteByName(rgName);
    }

    private void useExistingDomainAndCertificate() {
        String rgName = "rgnemv24d683784f51d";
        String certOrder = "wild2crt8b42374211";
        String domainName = "jsdk79877.com";
        if (System.getenv("appservice-group") != null) {
            rgName = System.getenv("appservice-group");
        }
        if (System.getenv("appservice-domain") != null) {
            domainName = System.getenv("appservice-domain");
        }
        if (System.getenv("appservice-certificateorder") != null) {
            certOrder = System.getenv("appservice-certificateorder");
        }

        domain = appServiceManager.domains().getByResourceGroup(rgName, domainName);
        certificateOrder = appServiceManager.certificateOrders().getByResourceGroup(rgName, certOrder);
    }

    private void createNewDomainAndCertificate() {
        domain =
            appServiceManager
                .domains()
                .define(System.getenv("appservice-domain"))
                .withExistingResourceGroup(System.getenv("appservice-group"))
                .defineRegistrantContact()
                .withFirstName("Jon")
                .withLastName("Doe")
                .withEmail("jondoe@contoso.com")
                .withAddressLine1("123 4th Ave")
                .withCity("Redmond")
                .withStateOrProvince("WA")
                .withCountry(CountryIsoCode.UNITED_STATES)
                .withPostalCode("98052")
                .withPhoneCountryCode(CountryPhoneCode.UNITED_STATES)
                .withPhoneNumber("4258828080")
                .attach()
                .withDomainPrivacyEnabled(true)
                .withAutoRenewEnabled(true)
                .create();
        certificateOrder =
            appServiceManager
                .certificateOrders()
                .define(System.getenv("appservice-certificateorder"))
                .withExistingResourceGroup(System.getenv("appservice-group"))
                .withHostName("*." + domain.name())
                .withWildcardSku()
                .withDomainVerification(domain)
                .withNewKeyVault("graphvault", Region.US_WEST)
                .withValidYears(1)
                .create();
    }

    /**
     * Uploads a file to an Azure web app.
     *
     * @param profile the publishing profile for the web app.
     * @param fileName the name of the file on server
     * @param file the local file
     */
    public static void uploadFileToWebApp(PublishingProfile profile, String fileName, InputStream file) {
        FTPClient ftpClient = new FTPClient();
        String[] ftpUrlSegments = profile.ftpUrl().split("/", 2);
        String server = ftpUrlSegments[0];
        String path = "./site/wwwroot/webapps";
        if (fileName.contains("/")) {
            int lastslash = fileName.lastIndexOf('/');
            path = path + "/" + fileName.substring(0, lastslash);
            fileName = fileName.substring(lastslash + 1);
        }
        try {
            ftpClient.connect(server);
            ftpClient.login(profile.ftpUsername(), profile.ftpPassword());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            for (String segment : path.split("/")) {
                if (!ftpClient.changeWorkingDirectory(segment)) {
                    ftpClient.makeDirectory(segment);
                    ftpClient.changeWorkingDirectory(segment);
                }
            }
            ftpClient.storeFile(fileName, file);
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Response<String> curl(String urlString) throws IOException {
        try {
            Mono<SimpleResponse<Flux<ByteBuffer>>> response =
                HTTP_CLIENT.getString(getHost(urlString), getPathAndQuery(urlString))
                    .retryWhen(Retry
                        .fixedDelay(3, Duration.ofSeconds(30))
                        .filter(t -> t instanceof TimeoutException));
            return stringResponse(response).block();
        } catch (MalformedURLException e) {
            Assertions.fail();
            return null;
        }
    }

    protected String post(String urlString, String body) {
        try {
            return stringResponse(HTTP_CLIENT.postString(getHost(urlString), getPathAndQuery(urlString), body))
                .block()
                .getValue();
        } catch (Exception e) {
            return null;
        }
    }

    private String getHost(String urlString) throws MalformedURLException {
        URL url = new URL(urlString);
        String protocol = url.getProtocol();
        String host = url.getAuthority();
        return protocol + "://" + host;
    }

    private String getPathAndQuery(String urlString) throws MalformedURLException {
        URL url = new URL(urlString);
        String path = url.getPath();
        String query = url.getQuery();
        if (query != null && !query.isEmpty()) {
            path = path + "?" + query;
        }
        return path;
    }

    private static Mono<SimpleResponse<String>> stringResponse(Mono<SimpleResponse<Flux<ByteBuffer>>> responseMono) {
        return responseMono
            .flatMap(
                response ->
                    FluxUtil
                        .collectBytesInByteBufferStream(response.getValue())
                        .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                        .map(
                            str ->
                                new SimpleResponse<>(
                                    response.getRequest(), response.getStatusCode(), response.getHeaders(), str)));
    }

    private static final WebAppTestClient HTTP_CLIENT =
        RestProxy
            .create(
                WebAppTestClient.class,
                new HttpPipelineBuilder()
                    .policies(
                        new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)),
                        new RetryPolicy("Retry-After", ChronoUnit.SECONDS))
                    .build());

    @Host("{$host}")
    @ServiceInterface(name = "WebAppTestClient")
    private interface WebAppTestClient {
        @Get("{path}")
        @ExpectedResponses({200, 400, 404})
        Mono<SimpleResponse<Flux<ByteBuffer>>> getString(
            @HostParam("$host") String host, @PathParam(value = "path", encoded = true) String path);

        @Post("{path}")
        @ExpectedResponses({200, 400, 404})
        Mono<SimpleResponse<Flux<ByteBuffer>>> postString(
            @HostParam("$host") String host,
            @PathParam(value = "path", encoded = true) String path,
            @BodyParam("text/plain") String body);
    }
}
