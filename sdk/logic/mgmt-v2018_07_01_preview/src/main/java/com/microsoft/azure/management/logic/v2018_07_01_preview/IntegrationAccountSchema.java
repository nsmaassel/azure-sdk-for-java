/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic.v2018_07_01_preview;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.management.logic.v2018_07_01_preview.implementation.IntegrationAccountSchemaInner;
import com.microsoft.azure.arm.model.Indexable;
import com.microsoft.azure.arm.model.Refreshable;
import com.microsoft.azure.arm.model.Updatable;
import com.microsoft.azure.arm.model.Appliable;
import com.microsoft.azure.arm.model.Creatable;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.logic.v2018_07_01_preview.implementation.LogicManager;
import java.util.Map;
import org.joda.time.DateTime;

/**
 * Type representing IntegrationAccountSchema.
 */
public interface IntegrationAccountSchema extends HasInner<IntegrationAccountSchemaInner>, Indexable, Refreshable<IntegrationAccountSchema>, Updatable<IntegrationAccountSchema.Update>, HasManager<LogicManager> {
    /**
     * @return the changedTime value.
     */
    DateTime changedTime();

    /**
     * @return the content value.
     */
    String content();

    /**
     * @return the contentLink value.
     */
    ContentLink contentLink();

    /**
     * @return the contentType value.
     */
    String contentType();

    /**
     * @return the createdTime value.
     */
    DateTime createdTime();

    /**
     * @return the documentName value.
     */
    String documentName();

    /**
     * @return the fileName value.
     */
    String fileName();

    /**
     * @return the id value.
     */
    String id();

    /**
     * @return the location value.
     */
    String location();

    /**
     * @return the metadata value.
     */
    Object metadata();

    /**
     * @return the name value.
     */
    String name();

    /**
     * @return the schemaType value.
     */
    SchemaType schemaType();

    /**
     * @return the tags value.
     */
    Map<String, String> tags();

    /**
     * @return the targetNamespace value.
     */
    String targetNamespace();

    /**
     * @return the type value.
     */
    String type();

    /**
     * The entirety of the IntegrationAccountSchema definition.
     */
    interface Definition extends DefinitionStages.Blank, DefinitionStages.WithIntegrationAccount, DefinitionStages.WithSchemaType, DefinitionStages.WithCreate {
    }

    /**
     * Grouping of IntegrationAccountSchema definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a IntegrationAccountSchema definition.
         */
        interface Blank extends WithIntegrationAccount {
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify IntegrationAccount.
         */
        interface WithIntegrationAccount {
           /**
            * Specifies resourceGroupName, integrationAccountName.
            * @param resourceGroupName The resource group name
            * @param integrationAccountName The integration account name
            * @return the next definition stage
            */
            WithSchemaType withExistingIntegrationAccount(String resourceGroupName, String integrationAccountName);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify SchemaType.
         */
        interface WithSchemaType {
           /**
            * Specifies schemaType.
            * @param schemaType The schema type. Possible values include: 'NotSpecified', 'Xml'
            * @return the next definition stage
            */
            WithCreate withSchemaType(SchemaType schemaType);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify Content.
         */
        interface WithContent {
            /**
             * Specifies content.
             * @param content The content
             * @return the next definition stage
             */
            WithCreate withContent(String content);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify ContentType.
         */
        interface WithContentType {
            /**
             * Specifies contentType.
             * @param contentType The content type
             * @return the next definition stage
             */
            WithCreate withContentType(String contentType);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify DocumentName.
         */
        interface WithDocumentName {
            /**
             * Specifies documentName.
             * @param documentName The document name
             * @return the next definition stage
             */
            WithCreate withDocumentName(String documentName);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify FileName.
         */
        interface WithFileName {
            /**
             * Specifies fileName.
             * @param fileName The file name
             * @return the next definition stage
             */
            WithCreate withFileName(String fileName);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify Location.
         */
        interface WithLocation {
            /**
             * Specifies location.
             * @param location The resource location
             * @return the next definition stage
             */
            WithCreate withLocation(String location);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify Metadata.
         */
        interface WithMetadata {
            /**
             * Specifies metadata.
             * @param metadata The metadata
             * @return the next definition stage
             */
            WithCreate withMetadata(Object metadata);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify Tags.
         */
        interface WithTags {
            /**
             * Specifies tags.
             * @param tags The resource tags
             * @return the next definition stage
             */
            WithCreate withTags(Map<String, String> tags);
        }

        /**
         * The stage of the integrationaccountschema definition allowing to specify TargetNamespace.
         */
        interface WithTargetNamespace {
            /**
             * Specifies targetNamespace.
             * @param targetNamespace The target namespace of the schema
             * @return the next definition stage
             */
            WithCreate withTargetNamespace(String targetNamespace);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created (via {@link WithCreate#create()}), but also allows
         * for any other optional settings to be specified.
         */
        interface WithCreate extends Creatable<IntegrationAccountSchema>, DefinitionStages.WithContent, DefinitionStages.WithContentType, DefinitionStages.WithDocumentName, DefinitionStages.WithFileName, DefinitionStages.WithLocation, DefinitionStages.WithMetadata, DefinitionStages.WithTags, DefinitionStages.WithTargetNamespace {
        }
    }
    /**
     * The template for a IntegrationAccountSchema update operation, containing all the settings that can be modified.
     */
    interface Update extends Appliable<IntegrationAccountSchema>, UpdateStages.WithContent, UpdateStages.WithContentType, UpdateStages.WithDocumentName, UpdateStages.WithFileName, UpdateStages.WithLocation, UpdateStages.WithMetadata, UpdateStages.WithTags, UpdateStages.WithTargetNamespace {
    }

    /**
     * Grouping of IntegrationAccountSchema update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the integrationaccountschema update allowing to specify Content.
         */
        interface WithContent {
            /**
             * Specifies content.
             * @param content The content
             * @return the next update stage
             */
            Update withContent(String content);
        }

        /**
         * The stage of the integrationaccountschema update allowing to specify ContentType.
         */
        interface WithContentType {
            /**
             * Specifies contentType.
             * @param contentType The content type
             * @return the next update stage
             */
            Update withContentType(String contentType);
        }

        /**
         * The stage of the integrationaccountschema update allowing to specify DocumentName.
         */
        interface WithDocumentName {
            /**
             * Specifies documentName.
             * @param documentName The document name
             * @return the next update stage
             */
            Update withDocumentName(String documentName);
        }

        /**
         * The stage of the integrationaccountschema update allowing to specify FileName.
         */
        interface WithFileName {
            /**
             * Specifies fileName.
             * @param fileName The file name
             * @return the next update stage
             */
            Update withFileName(String fileName);
        }

        /**
         * The stage of the integrationaccountschema update allowing to specify Location.
         */
        interface WithLocation {
            /**
             * Specifies location.
             * @param location The resource location
             * @return the next update stage
             */
            Update withLocation(String location);
        }

        /**
         * The stage of the integrationaccountschema update allowing to specify Metadata.
         */
        interface WithMetadata {
            /**
             * Specifies metadata.
             * @param metadata The metadata
             * @return the next update stage
             */
            Update withMetadata(Object metadata);
        }

        /**
         * The stage of the integrationaccountschema update allowing to specify Tags.
         */
        interface WithTags {
            /**
             * Specifies tags.
             * @param tags The resource tags
             * @return the next update stage
             */
            Update withTags(Map<String, String> tags);
        }

        /**
         * The stage of the integrationaccountschema update allowing to specify TargetNamespace.
         */
        interface WithTargetNamespace {
            /**
             * Specifies targetNamespace.
             * @param targetNamespace The target namespace of the schema
             * @return the next update stage
             */
            Update withTargetNamespace(String targetNamespace);
        }

    }
}