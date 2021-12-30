/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.sample.dao;

import com.microsoft.azure.sample.model.UserItem;
import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserItemRepository extends DocumentDbRepository<UserItem, String> {
}
