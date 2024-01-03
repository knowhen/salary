package org.when.salary.context.domain.employee.salaried;

import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.Payroll;
import org.when.salary.context.repository.SalariedEmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SalariedEmployeePayrollCalculator {
    private SalariedEmployeeRepository repository;

    public void setRepository(SalariedEmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Payroll> execute(DateRange settlementPeriod) {
        return repository.findAll().stream()
                .map(employee -> employee.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
