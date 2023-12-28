package org.when.salary.context.repository;

import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.employee.HourlyEmployee;

import java.util.List;

public interface HourlyEmployeeRepository {
    List<HourlyEmployee> findAll(DateRange settlementPeriod);
}
