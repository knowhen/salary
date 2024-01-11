package org.when.salary.context.domain.employee.salaried;

import org.hibernate.annotations.DiscriminatorOptions;
import org.when.salary.context.domain.*;
import org.when.salary.core.domain.AbstractEntity;
import org.when.salary.core.domain.AggregateRoot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "employeeType", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorOptions(force = true)
@DiscriminatorValue(value = "1")
public class SalariedEmployee extends AbstractEntity<EmployeeId> implements AggregateRoot<SalariedEmployee>, Payable {
    private static final int WORK_DAYS_EACH_MONTH = 22;
    @EmbeddedId
    private EmployeeId employeeId;
    @Embedded
    private Salary monthlySalary;
    @ElementCollection
    @CollectionTable(name = "absences", joinColumns = @JoinColumn(name = "employeeId"))
    private List<Absence> absences = new ArrayList<>();

    public SalariedEmployee() {
    }

    public SalariedEmployee(EmployeeId employeeId, Salary monthlySalary) {
        this(employeeId, monthlySalary, new ArrayList<>());
    }

    public SalariedEmployee(EmployeeId employeeId, Salary monthlySalary, List<Absence> absences) {
        this.employeeId = employeeId;
        this.monthlySalary = monthlySalary;
        this.absences = absences;
    }

    @Override
    public Payroll payroll(DateRange settlementPeriod) {
        Salary dailySalary = monthlySalary.divide(WORK_DAYS_EACH_MONTH);
        Salary deduction = absences.stream()
                .filter(absence -> absence.withInRange(settlementPeriod))
                .filter(absence -> !absence.withSalary())
                .map(absence -> dailySalary.multiply(absence.deductionRatio()))
                .reduce(Salary.ZERO, Salary::add);
        return new Payroll(employeeId, settlementPeriod.getStartDate(), settlementPeriod.getEndDate(), monthlySalary.subtract(deduction));
    }

    @Override
    public EmployeeId id() {
        return employeeId;
    }

    @Override
    public SalariedEmployee root() {
        return this;
    }

    public Salary monthlySalary() {
        return monthlySalary;
    }

    public List<Absence> absences() {
        return absences;
    }
}
