package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.Money;
import org.when.salary.context.domain.Payroll;

import java.util.ArrayList;
import java.util.List;

public class SalariedEmployee {
    private static final int WORK_DAYS_EACH_MONTH = 22;
    private String employeeId;
    private Money monthlySalary;
    private List<Absence> absences;

    public SalariedEmployee(String employeeId, Money monthlySalary) {
        this(employeeId, monthlySalary, new ArrayList<>());
    }

    public SalariedEmployee(String employeeId, Money monthlySalary, List<Absence> absences) {
        this.employeeId = employeeId;
        this.monthlySalary = monthlySalary;
        this.absences = absences;
    }

    public Payroll payroll(DateRange settlementPeriod) {
        Money dailySalary = monthlySalary.divide(WORK_DAYS_EACH_MONTH);
        Money deduction = absences.stream()
                .filter(absence -> !absence.withSalary())
                .map(absence -> dailySalary.multiply(absence.deductionRatio()))
                .reduce(Money.ZERO, Money::add);
        return new Payroll(employeeId, settlementPeriod.getStartDate(), settlementPeriod.getEndDate(), monthlySalary.subtract(deduction));
    }

}
