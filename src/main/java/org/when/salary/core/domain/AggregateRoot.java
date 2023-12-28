package org.when.salary.core.domain;

public interface AggregateRoot<R extends AbstractEntity> {
    R root();
}
