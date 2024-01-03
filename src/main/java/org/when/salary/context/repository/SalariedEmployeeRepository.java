package org.when.salary.context.repository;

import org.when.salary.context.domain.employee.salaried.SalariedEmployee;

import java.util.List;

public interface SalariedEmployeeRepository {
    List<SalariedEmployee> findAll();
}
