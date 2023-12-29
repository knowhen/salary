package org.when.salary.core.persistence;

import org.when.salary.core.domain.AggregateRoot;
import org.when.salary.core.domain.Identity;
import org.when.salary.core.persistence.exception.InitializeEntityManagerException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Repository<E extends AggregateRoot, ID extends Identity> {
    private Class<E> entityClass;
    private EntityManager entityManager;
    private TransactionScope transactionScope;

    public Repository(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.transactionScope = new TransactionScope(entityManager);
    }

    public Optional<E> findById(ID id) {
        requireNonNullEntityManager();

        E root = entityManager.find(entityClass, id);
        if (root == null) {
            return Optional.empty();
        }
        return Optional.of(root);

    }

    private void requireNonNullEntityManager() {
        if (entityManager == null) {
            throw new InitializeEntityManagerException();
        }
    }

    public List<E> findAll() {
        CriteriaQuery<E> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }

    public List<E> findBy(Specification<E> specification) {
        if (specification == null) {
            return findAll();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        Root<E> root = query.from(entityClass);

        Predicate predicate = specification.toPredicate(criteriaBuilder, query, root);
        query.where(new Predicate[]{predicate});

        TypedQuery<E> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public void saveOrUpdate(E entity) {
        requireNonNullEntityManager();

        if (Objects.isNull(entity)) {
            // todo: log
            return;
        }

        if (entityManager.contains(entity)) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
    }

    public void delete(E entity) {
        requireNonNullEntityManager();

        if (Objects.isNull(entity)) {
            // todo: log
            return;
        }

        if (!entityManager.contains(entity)) {
            return;
        }

        entityManager.remove(entity);
    }

    public void finalize() {
        entityManager.close();
    }
}
