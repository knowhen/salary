package org.when.salary.payroll.domain.salaried;

import org.when.salary.payroll.domain.DateRange;
import org.when.salary.payroll.domain.Payroll;
import org.when.salary.payroll.domain.PayrollCalculator;
import org.when.salary.payroll.repository.SalariedEmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SalariedEmployeePayrollCalculator implements PayrollCalculator {
    private SalariedEmployeeRepository repository;

    public void setRepository(SalariedEmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payroll> execute(DateRange settlementPeriod) {
        return repository.findAll().stream()
                .map(employee -> employee.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
