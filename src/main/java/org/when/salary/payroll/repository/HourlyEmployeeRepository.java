package org.when.salary.payroll.repository;

import org.when.salary.employee.domain.EmployeeId;
import org.when.salary.payroll.domain.hourly.HourlyEmployee;

import java.util.List;
import java.util.Optional;

public interface HourlyEmployeeRepository {
    void save(HourlyEmployee hourlyEmployee);

    Optional<HourlyEmployee> findById(EmployeeId id);

    List<HourlyEmployee> findAll();
}
