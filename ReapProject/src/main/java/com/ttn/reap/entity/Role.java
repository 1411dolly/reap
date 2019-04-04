package com.ttn.reap.entity;

import javax.persistence.Embeddable;

@Embeddable
public enum Role {
    USER("USER"), SUPERVISOR("SUPERVISOR"), PRACTICE_HEAD("PRACTICE_HEAD");
    String value;

    Role() {
    }

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Role{" +
                "value='" + value + '\'' +
                '}';
    }
}
