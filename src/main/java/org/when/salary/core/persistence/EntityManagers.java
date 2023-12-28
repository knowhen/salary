package org.when.salary.core.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagers {
    public static javax.persistence.EntityManager from(String persistenceUnitName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        return entityManagerFactory.createEntityManager();
    }

    public static void main(String[] args) {
        String unitName = "Payroll_JPA";
        EntityManager manager = EntityManagers.from(unitName);
    }
}
