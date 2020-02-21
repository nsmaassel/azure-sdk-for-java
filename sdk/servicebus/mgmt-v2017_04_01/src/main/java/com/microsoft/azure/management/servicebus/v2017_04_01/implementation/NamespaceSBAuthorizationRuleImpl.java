/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.servicebus.v2017_04_01.implementation;

import com.microsoft.azure.management.servicebus.v2017_04_01.NamespaceSBAuthorizationRule;
import com.microsoft.azure.arm.model.implementation.CreatableUpdatableImpl;
import rx.Observable;
import java.util.List;
import com.microsoft.azure.management.servicebus.v2017_04_01.AccessRights;

class NamespaceSBAuthorizationRuleImpl extends CreatableUpdatableImpl<NamespaceSBAuthorizationRule, SBAuthorizationRuleInner, NamespaceSBAuthorizationRuleImpl> implements NamespaceSBAuthorizationRule, NamespaceSBAuthorizationRule.Definition, NamespaceSBAuthorizationRule.Update {
    private final ServiceBusManager manager;
    private String resourceGroupName;
    private String namespaceName;
    private String authorizationRuleName;
    private List<AccessRights> crights;
    private List<AccessRights> urights;

    NamespaceSBAuthorizationRuleImpl(String name, ServiceBusManager manager) {
        super(name, new SBAuthorizationRuleInner());
        this.manager = manager;
        // Set resource name
        this.authorizationRuleName = name;
        //
    }

    NamespaceSBAuthorizationRuleImpl(SBAuthorizationRuleInner inner, ServiceBusManager manager) {
        super(inner.name(), inner);
        this.manager = manager;
        // Set resource name
        this.authorizationRuleName = inner.name();
        // set resource ancestor and positional variables
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.id(), "resourceGroups");
        this.namespaceName = IdParsingUtils.getValueFromIdByName(inner.id(), "namespaces");
        this.authorizationRuleName = IdParsingUtils.getValueFromIdByName(inner.id(), "AuthorizationRules");
        //
    }

    @Override
    public ServiceBusManager manager() {
        return this.manager;
    }

    @Override
    public Observable<NamespaceSBAuthorizationRule> createResourceAsync() {
        NamespacesInner client = this.manager().inner().namespaces();
        return client.createOrUpdateAuthorizationRuleAsync(this.resourceGroupName, this.namespaceName, this.authorizationRuleName, this.crights)
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<NamespaceSBAuthorizationRule> updateResourceAsync() {
        NamespacesInner client = this.manager().inner().namespaces();
        return client.createOrUpdateAuthorizationRuleAsync(this.resourceGroupName, this.namespaceName, this.authorizationRuleName, this.urights)
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<SBAuthorizationRuleInner> getInnerAsync() {
        NamespacesInner client = this.manager().inner().namespaces();
        return client.getAuthorizationRuleAsync(this.resourceGroupName, this.namespaceName, this.authorizationRuleName);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }


    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public List<AccessRights> rights() {
        return this.inner().rights();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public NamespaceSBAuthorizationRuleImpl withExistingNamespace(String resourceGroupName, String namespaceName) {
        this.resourceGroupName = resourceGroupName;
        this.namespaceName = namespaceName;
        return this;
    }

    @Override
    public NamespaceSBAuthorizationRuleImpl withRights(List<AccessRights> rights) {
        if (isInCreateMode()) {
            this.crights = rights;
        } else {
            this.urights = rights;
        }
        return this;
    }

}