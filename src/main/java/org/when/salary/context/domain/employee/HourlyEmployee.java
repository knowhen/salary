package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.Money;
import org.when.salary.context.domain.Payroll;

import java.util.List;
import java.util.Objects;

public class HourlyEmployee {
    private static final double OVERTIME_FACTOR = 1.5;
    private String employeeId;
    private List<TimeCard> timeCards;
    private Money hourlySalary;

    public HourlyEmployee(String employeeId, List<TimeCard> timeCards, Money hourlySalary) {
        this.employeeId = employeeId;
        this.timeCards = timeCards;
        this.hourlySalary = hourlySalary;
    }

    public Payroll payroll(DateRange dateRange) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(employeeId, dateRange.getStartDate(), dateRange.getEndDate(), Money.ZERO);
        }
        Money normalSalary = calculateNormalSalary();

        Money overtimeSalary = calculateOvertimeSalary();

        Money totalSalary = normalSalary.add(overtimeSalary);

        return new Payroll(employeeId, dateRange.getStartDate(), dateRange.getEndDate(), totalSalary);
    }

    private Money calculateOvertimeSalary() {
        Long overtimeHours = timeCards.stream()
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeHours)
                .reduce(0L, Long::sum);
        Money overtimeSalary = hourlySalary.multiply(overtimeHours).multiply(OVERTIME_FACTOR);
        return overtimeSalary;
    }

    private Money calculateNormalSalary() {
        Long normalHours = timeCards.stream()
                .map(TimeCard::getNormalHours)
                .reduce(0L, Long::sum);
        Money normalSalary = hourlySalary.multiply(normalHours);
        return normalSalary;
    }
}
