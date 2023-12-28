package org.when.salary.core.persistence;

import org.when.salary.core.domain.AggregateRoot;
import org.when.salary.core.domain.Identity;
import org.when.salary.core.persistence.exception.InitializeEntityManagerException;

import javax.persistence.EntityManager;
import java.util.Optional;

public class Repository<E extends AggregateRoot, ID extends Identity> {
    private Class<E> entityClass;
    private EntityManager entityManager;

    public Repository(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public Optional<E> findById(ID id) {
        if (entityManager == null) {
            throw new InitializeEntityManagerException();
        }

        E root = entityManager.find(entityClass, id);
        if (root == null) {
            return Optional.empty();
        }
        return Optional.of(root);

    }
}
