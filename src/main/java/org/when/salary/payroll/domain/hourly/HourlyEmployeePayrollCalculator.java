package org.when.salary.payroll.domain.hourly;

import org.when.salary.payroll.domain.DateRange;
import org.when.salary.payroll.domain.Payroll;
import org.when.salary.payroll.domain.PayrollCalculator;
import org.when.salary.payroll.repository.HourlyEmployeeRepository;

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
