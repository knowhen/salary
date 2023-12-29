package org.when.salary.fixture;

import org.when.salary.core.persistence.EntityManagers;

import javax.persistence.EntityManager;

public class EntityManagerFixture {
    private static final String PERSISTENCE_UNIT_NAME = "Payroll_JPA";

    public static EntityManager createEntityManager() {
        return EntityManagers.from(PERSISTENCE_UNIT_NAME);
    }
}
