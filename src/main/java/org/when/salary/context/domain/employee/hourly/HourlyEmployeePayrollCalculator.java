package org.when.salary.context.domain.employee.hourly;

import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.Payroll;
import org.when.salary.context.domain.PayrollCalculator;
import org.when.salary.context.repository.HourlyEmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class HourlyEmployeePayrollCalculator implements PayrollCalculator {
    private HourlyEmployeeRepository repository;

    public void setRepository(HourlyEmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payroll> execute(DateRange settlementPeriod) {
        return repository.findAll().stream()
                .map(employee -> employee.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
