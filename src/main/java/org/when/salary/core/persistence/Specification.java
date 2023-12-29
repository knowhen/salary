package org.when.salary.core.persistence;

import org.when.salary.core.domain.AggregateRoot;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<E extends AggregateRoot> {
    Predicate toPredicate(CriteriaBuilder criteriaBuilder, CriteriaQuery<E> query, Root<E> root);
}