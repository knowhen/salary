package org.when.salary.core.domain;

public abstract class AbstractEntity<ID extends Identity> {
    public abstract ID id();
}
