package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.Payroll;

import java.util.List;
import java.util.stream.Collectors;

public class HourlyEmployeePayrollCalculator {
    private HourlyEmployeeRepository repository;

    public void setRepository(HourlyEmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Payroll> execute(DateRange settlementPeriod) {
        return repository.allEmployeeOf(settlementPeriod).stream()
                .map(employee -> employee.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}