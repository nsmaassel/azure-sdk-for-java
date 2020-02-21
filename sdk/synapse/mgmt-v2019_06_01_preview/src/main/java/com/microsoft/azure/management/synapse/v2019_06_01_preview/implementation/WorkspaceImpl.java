/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.synapse.v2019_06_01_preview.implementation;

import com.microsoft.azure.arm.resources.models.implementation.GroupableResourceCoreImpl;
import com.microsoft.azure.management.synapse.v2019_06_01_preview.Workspace;
import rx.Observable;
import com.microsoft.azure.management.synapse.v2019_06_01_preview.WorkspacePatchInfo;
import com.microsoft.azure.management.synapse.v2019_06_01_preview.DataLakeStorageAccountDetails;
import com.microsoft.azure.management.synapse.v2019_06_01_preview.VirtualNetworkProfile;
import java.util.Map;
import com.microsoft.azure.management.synapse.v2019_06_01_preview.ManagedIdentity;
import rx.functions.Func1;

class WorkspaceImpl extends GroupableResourceCoreImpl<Workspace, WorkspaceInner, WorkspaceImpl, SynapseManager> implements Workspace, Workspace.Definition, Workspace.Update {
    private WorkspacePatchInfo updateParameter;
    WorkspaceImpl(String name, WorkspaceInner inner, SynapseManager manager) {
        super(name, inner, manager);
        this.updateParameter = new WorkspacePatchInfo();
    }

    @Override
    public Observable<Workspace> createResourceAsync() {
        WorkspacesInner client = this.manager().inner().workspaces();
        return client.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
            .map(new Func1<WorkspaceInner, WorkspaceInner>() {
               @Override
               public WorkspaceInner call(WorkspaceInner resource) {
                   resetCreateUpdateParameters();
                   return resource;
               }
            })
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<Workspace> updateResourceAsync() {
        WorkspacesInner client = this.manager().inner().workspaces();
        return client.updateAsync(this.resourceGroupName(), this.name(), this.updateParameter)
            .map(new Func1<WorkspaceInner, WorkspaceInner>() {
               @Override
               public WorkspaceInner call(WorkspaceInner resource) {
                   resetCreateUpdateParameters();
                   return resource;
               }
            })
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<WorkspaceInner> getInnerAsync() {
        WorkspacesInner client = this.manager().inner().workspaces();
        return client.getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    private void resetCreateUpdateParameters() {
        this.updateParameter = new WorkspacePatchInfo();
    }

    @Override
    public Map<String, String> connectivityEndpoints() {
        return this.inner().connectivityEndpoints();
    }

    @Override
    public DataLakeStorageAccountDetails defaultDataLakeStorage() {
        return this.inner().defaultDataLakeStorage();
    }

    @Override
    public ManagedIdentity identity() {
        return this.inner().identity();
    }

    @Override
    public String managedResourceGroupName() {
        return this.inner().managedResourceGroupName();
    }

    @Override
    public String provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public String sqlAdministratorLogin() {
        return this.inner().sqlAdministratorLogin();
    }

    @Override
    public String sqlAdministratorLoginPassword() {
        return this.inner().sqlAdministratorLoginPassword();
    }

    @Override
    public VirtualNetworkProfile virtualNetworkProfile() {
        return this.inner().virtualNetworkProfile();
    }

    @Override
    public WorkspaceImpl withConnectivityEndpoints(Map<String, String> connectivityEndpoints) {
        this.inner().withConnectivityEndpoints(connectivityEndpoints);
        return this;
    }

    @Override
    public WorkspaceImpl withDefaultDataLakeStorage(DataLakeStorageAccountDetails defaultDataLakeStorage) {
        this.inner().withDefaultDataLakeStorage(defaultDataLakeStorage);
        return this;
    }

    @Override
    public WorkspaceImpl withSqlAdministratorLogin(String sqlAdministratorLogin) {
        this.inner().withSqlAdministratorLogin(sqlAdministratorLogin);
        return this;
    }

    @Override
    public WorkspaceImpl withVirtualNetworkProfile(VirtualNetworkProfile virtualNetworkProfile) {
        this.inner().withVirtualNetworkProfile(virtualNetworkProfile);
        return this;
    }

    @Override
    public WorkspaceImpl withIdentity(ManagedIdentity identity) {
        if (isInCreateMode()) {
            this.inner().withIdentity(identity);
        } else {
            this.updateParameter.withIdentity(identity);
        }
        return this;
    }

    @Override
    public WorkspaceImpl withSqlAdministratorLoginPassword(String sqlAdministratorLoginPassword) {
        if (isInCreateMode()) {
            this.inner().withSqlAdministratorLoginPassword(sqlAdministratorLoginPassword);
        } else {
            this.updateParameter.withSqlAdministratorLoginPassword(sqlAdministratorLoginPassword);
        }
        return this;
    }

}