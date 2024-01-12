package org.when.salary.payroll.domain.hourly;

import org.hibernate.annotations.DiscriminatorOptions;
import org.when.salary.core.domain.AbstractEntity;
import org.when.salary.core.domain.AggregateRoot;
import org.when.salary.employee.domain.EmployeeId;
import org.when.salary.payroll.domain.DateRange;
import org.when.salary.payroll.domain.Payable;
import org.when.salary.payroll.domain.Payroll;
import org.when.salary.payroll.domain.Salary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "employeeType", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorOptions(force = true)
@DiscriminatorValue(value = "0")
public class HourlyEmployee extends AbstractEntity<EmployeeId> implements AggregateRoot<HourlyEmployee>, Payable {
    private static final double OVERTIME_FACTOR = 1.5;
    @EmbeddedId
    private EmployeeId employeeId;
    @Embedded
    private Salary hourlySalary;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employeeId", nullable = false)
    private List<TimeCard> timeCards = new ArrayList<>();

    public HourlyEmployee() {
    }

    public HourlyEmployee(EmployeeId employeeId, List<TimeCard> timeCards, Salary hourlySalary) {
        this.employeeId = employeeId;
        if (Objects.nonNull(timeCards)) {
            this.timeCards = timeCards;
        }
        this.hourlySalary = hourlySalary;
    }

    @Override
    public Payroll payroll(DateRange dateRange) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(employeeId, dateRange.getStartDate(), dateRange.getEndDate(), Salary.ZERO);
        }
        Salary normalSalary = calculateNormalSalary(dateRange);

        Salary overtimeSalary = calculateOvertimeSalary(dateRange);

        Salary totalSalary = normalSalary.add(overtimeSalary);

        return new Payroll(employeeId, dateRange.getStartDate(), dateRange.getEndDate(), totalSalary);
    }

    public Salary hourlySalary() {
        return hourlySalary;
    }

    public List<TimeCard> timeCards() {
        return timeCards;
    }

    private Salary calculateOvertimeSalary(DateRange dateRange) {
        Long overtimeHours = timeCards.stream().filter(timeCard -> timeCard.withInRange(dateRange)).filter(TimeCard::isOvertime).map(TimeCard::getOvertimeHours).reduce(0L, Long::sum);
        return hourlySalary.multiply(overtimeHours).multiply(OVERTIME_FACTOR);
    }

    private Salary calculateNormalSalary(DateRange dateRange) {
        Long normalHours = timeCards.stream().filter(timeCard -> timeCard.withInRange(dateRange)).map(TimeCard::getNormalHours).reduce(0L, Long::sum);
        return hourlySalary.multiply(normalHours);
    }

    @Override
    public EmployeeId id() {
        return employeeId;
    }

    @Override
    public HourlyEmployee root() {
        return this;
    }

    public void submit(List<TimeCard> timeCards) {
        for (TimeCard timeCard : timeCards) {
            submit(timeCard);
        }
    }

    public void submit(TimeCard submittedTimeCard) {
        if (!this.timeCards.contains(submittedTimeCard)) {
            this.timeCards.add(submittedTimeCard);
        }
    }

    public void remove(TimeCard removedTimeCard) {
        if (this.timeCards.contains(removedTimeCard)) {
            this.timeCards.remove(removedTimeCard);
        }
//        timeCards.removeIf(card -> card.equals(removedTimeCard));
    }

}
