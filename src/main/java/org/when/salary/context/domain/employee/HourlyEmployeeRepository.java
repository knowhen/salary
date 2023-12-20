package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.DateRange;

import java.util.List;

public interface HourlyEmployeeRepository {
    List<HourlyEmployee> allEmployeeOf(DateRange settlementPeriod);
}
