/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.sample.model;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;

import java.util.Objects;

@Document
public class UserItem {
    private String id;
    private String name;
    private String role;

    public UserItem() {
    }

    public UserItem(String id, String name, String role) {
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UserItem)) {
            return false;
        }
        final UserItem group = (UserItem) o;
        return Objects.equals(this.getName(), group.getName())
                && Objects.equals(this.getRole(), group.getRole())
                && Objects.equals(this.getID(), group.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, role);
    }
}

