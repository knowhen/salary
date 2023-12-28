package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.EmployeeId;
import org.when.salary.context.domain.Salary;
import org.when.salary.context.domain.Payroll;
import org.when.salary.core.domain.AbstractEntity;
import org.when.salary.core.domain.AggregateRoot;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="employees")
public class HourlyEmployee extends AbstractEntity<EmployeeId> implements AggregateRoot<HourlyEmployee> {
    private static final double OVERTIME_FACTOR = 1.5;
    @EmbeddedId
    private EmployeeId employeeId;
    @Embedded
    private Salary hourlySalary;
    @OneToMany
    @JoinColumn(name = "employeeId", nullable = false)
    private List<TimeCard> timeCards;

    public HourlyEmployee() {
    }

    public HourlyEmployee(EmployeeId employeeId, List<TimeCard> timeCards, Salary hourlySalary) {
        this.employeeId = employeeId;
        this.timeCards = timeCards;
        this.hourlySalary = hourlySalary;
    }

    public Payroll payroll(DateRange dateRange) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(employeeId, dateRange.getStartDate(), dateRange.getEndDate(), Salary.ZERO);
        }
        Salary normalSalary = calculateNormalSalary();

        Salary overtimeSalary = calculateOvertimeSalary();

        Salary totalSalary = normalSalary.add(overtimeSalary);

        return new Payroll(employeeId, dateRange.getStartDate(), dateRange.getEndDate(), totalSalary);
    }

    public Salary hourlySalary() {
        return hourlySalary;
    }

    public List<TimeCard> timeCards() {
        return timeCards;
    }

    private Salary calculateOvertimeSalary() {
        Long overtimeHours = timeCards.stream()
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeHours)
                .reduce(0L, Long::sum);
        Salary overtimeSalary = hourlySalary.multiply(overtimeHours).multiply(OVERTIME_FACTOR);
        return overtimeSalary;
    }

    private Salary calculateNormalSalary() {
        Long normalHours = timeCards.stream()
                .map(TimeCard::getNormalHours)
                .reduce(0L, Long::sum);
        Salary normalSalary = hourlySalary.multiply(normalHours);
        return normalSalary;
    }

    @Override
    public EmployeeId id() {
        return employeeId;
    }

    @Override
    public HourlyEmployee root() {
        return this;
    }
}
