package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.Currency;
import org.when.salary.context.domain.Money;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFixture {
    public static HourlyEmployee createHourlyEmployee(String employeeId, List<TimeCard> timeCards) {
        Money hourlySalary = Money.of("1500.00", Currency.RMB);
        return new HourlyEmployee(employeeId, timeCards, hourlySalary);
    }

    public static HourlyEmployee createHourlyEmployee(String employeeId, int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        Money hourlySalary = Money.of("1500.00", Currency.RMB);
        return new HourlyEmployee(employeeId, createTimeCards(workHours1, workHours2, workHours3, workHours4, workHours5), hourlySalary);
    }

    private static List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        TimeCard timeCard1 = new TimeCard(LocalDate.of(2023, 12, 11), Duration.ofHours(workHours1));
        TimeCard timeCard2 = new TimeCard(LocalDate.of(2023, 12, 12), Duration.ofHours(workHours2));
        TimeCard timeCard3 = new TimeCard(LocalDate.of(2023, 12, 13), Duration.ofHours(workHours3));
        TimeCard timeCard4 = new TimeCard(LocalDate.of(2023, 12, 14), Duration.ofHours(workHours4));
        TimeCard timeCard5 = new TimeCard(LocalDate.of(2023, 12, 15), Duration.ofHours(workHours5));

        List<TimeCard> timeCards = new ArrayList<>();
        timeCards.add(timeCard1);
        timeCards.add(timeCard2);
        timeCards.add(timeCard3);
        timeCards.add(timeCard4);
        timeCards.add(timeCard5);
        return timeCards;
    }
}
