package org.when.salary.payroll.repository;

import org.when.salary.payroll.domain.salaried.SalariedEmployee;

import java.util.List;

public interface SalariedEmployeeRepository {
    List<SalariedEmployee> findAll();
}
