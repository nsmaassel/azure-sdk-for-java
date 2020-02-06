/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.v2016_08_01;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.appservice.v2016_08_01.implementation.AppServiceManager;
import com.microsoft.azure.management.appservice.v2016_08_01.implementation.UserInner;

/**
 * Type representing User.
 */
public interface User extends HasInner<UserInner>, HasManager<AppServiceManager> {
    /**
     * @return the id value.
     */
    String id();

    /**
     * @return the kind value.
     */
    String kind();

    /**
     * @return the name value.
     */
    String name();

    /**
     * @return the publishingPassword value.
     */
    String publishingPassword();

    /**
     * @return the publishingPasswordHash value.
     */
    String publishingPasswordHash();

    /**
     * @return the publishingPasswordHashSalt value.
     */
    String publishingPasswordHashSalt();

    /**
     * @return the publishingUserName value.
     */
    String publishingUserName();

    /**
     * @return the type value.
     */
    String type();

    /**
     * @return the userName value.
     */
    String userName();

}