package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.Currency;
import org.when.salary.context.domain.Money;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static SalariedEmployee createSalariedEmployee(String employeeId, Money monthlySalary) {
        return new SalariedEmployee(employeeId, monthlySalary);
    }

    public static SalariedEmployee createSalariedEmployee(String employeeId, Money monthlySalary, List<Absence> absences) {
        return new SalariedEmployee(employeeId, monthlySalary, absences);
    }

    public static SalariedEmployee salariedEmployeeWithOneSickAbsence(String employeeId) {
        Absence sickAbsence = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.SICK);
        return createSalariedEmployeeWithAbsences(employeeId, sickAbsence);
    }

    public static SalariedEmployee salariedEmployeeWithOnePaidAbsence(String employeeId) {
        Absence paidAbsence = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.MaternityLeave);
        return createSalariedEmployeeWithAbsences(employeeId, paidAbsence);
    }

    public static SalariedEmployee salariedEmployeeWithManyAbsences(String employeeId) {
        Absence sickAbsence = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.SICK);
        Absence casualAbsence = new Absence(employeeId, LocalDate.of(2019, 9, 3), LeaveReason.CasualLeave);
        Absence paidAbsence = new Absence(employeeId, LocalDate.of(2019, 9, 4), LeaveReason.MaternityLeave);
        Absence disapprovedAbsence = new Absence(employeeId, LocalDate.of(2019, 9, 5), LeaveReason.DisapprovedLeave);

        return createSalariedEmployeeWithAbsences(employeeId, sickAbsence, casualAbsence, paidAbsence, disapprovedAbsence);
    }

    private static SalariedEmployee createSalariedEmployeeWithAbsences(String employeeId, Absence... leaves) {
        List<Absence> absences = new ArrayList<>(Arrays.asList(leaves));
        Money salaryOfMonth = Money.of("10000.00", Currency.RMB);

        return new SalariedEmployee(employeeId, salaryOfMonth, absences);
    }
}
