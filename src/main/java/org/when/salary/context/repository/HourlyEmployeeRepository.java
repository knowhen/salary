package org.when.salary.context.repository;

import org.when.salary.context.domain.EmployeeId;
import org.when.salary.context.domain.employee.HourlyEmployee;

import java.util.List;
import java.util.Optional;

public interface HourlyEmployeeRepository {
    void save(HourlyEmployee hourlyEmployee);

    Optional<HourlyEmployee> findById(EmployeeId id);

    List<HourlyEmployee> findAll();
}
