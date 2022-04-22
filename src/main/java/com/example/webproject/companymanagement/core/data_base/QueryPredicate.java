package com.example.webproject.companymanagement.core.data_base;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface QueryPredicate<T> {
    Predicate predicate(CriteriaBuilder cb, Root<T> r);
}
