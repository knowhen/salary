package org.when.salary.context.domain.employee;

import org.when.salary.payroll.domain.Currency;
import org.when.salary.payroll.domain.Salary;
import org.when.salary.payroll.domain.hourly.HourlyEmployee;
import org.when.salary.payroll.domain.hourly.TimeCard;
import org.when.salary.payroll.domain.salaried.Absence;
import org.when.salary.payroll.domain.salaried.LeaveReason;
import org.when.salary.payroll.domain.salaried.SalariedEmployee;
import org.when.salary.employee.domain.Email;
import org.when.salary.employee.domain.Employee;
import org.when.salary.employee.domain.EmployeeId;
import org.when.salary.employee.domain.EmployeeType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeFixture {
    public static HourlyEmployee createHourlyEmployee(EmployeeId employeeId, List<TimeCard> timeCards) {
        Salary hourlySalary = Salary.of("1500.00", Currency.RMB);
        return new HourlyEmployee(employeeId, timeCards, hourlySalary);
    }

    public static HourlyEmployee createHourlyEmployee(EmployeeId employeeId, int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        Salary hourlySalary = Salary.of("1500.00", Currency.RMB);
        return new HourlyEmployee(employeeId, createTimeCards(workHours1, workHours2, workHours3, workHours4, workHours5), hourlySalary);
    }

    public static List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        TimeCard timeCard1 = new TimeCard(LocalDate.of(2023, 12, 11), workHours1);
        TimeCard timeCard2 = new TimeCard(LocalDate.of(2023, 12, 12), workHours2);
        TimeCard timeCard3 = new TimeCard(LocalDate.of(2023, 12, 13), workHours3);
        TimeCard timeCard4 = new TimeCard(LocalDate.of(2023, 12, 14), workHours4);
        TimeCard timeCard5 = new TimeCard(LocalDate.of(2023, 12, 15), workHours5);

        List<TimeCard> timeCards = new ArrayList<>();
        timeCards.add(timeCard1);
        timeCards.add(timeCard2);
        timeCards.add(timeCard3);
        timeCards.add(timeCard4);
        timeCards.add(timeCard5);
        return timeCards;
    }

    public static SalariedEmployee createSalariedEmployee(String employeeId, double monthlySalary) {
        return new SalariedEmployee(EmployeeId.of(employeeId), Salary.of(monthlySalary, Currency.RMB));
    }

    public static SalariedEmployee createSalariedEmployee(String employeeId, Salary monthlySalary, List<Absence> absences) {
        return new SalariedEmployee(EmployeeId.of(employeeId), monthlySalary, absences);
    }

    public static SalariedEmployee salariedEmployeeWithOneSickAbsence(String employeeId) {
        Absence sickAbsence = new Absence(LocalDate.of(2019, 9, 2), LeaveReason.SICK);
        return createSalariedEmployeeWithAbsences(employeeId, sickAbsence);
    }

    public static SalariedEmployee salariedEmployeeWithOnePaidAbsence(String employeeId) {
        Absence paidAbsence = new Absence(LocalDate.of(2019, 9, 2), LeaveReason.MaternityLeave);
        return createSalariedEmployeeWithAbsences(employeeId, paidAbsence);
    }

    public static SalariedEmployee salariedEmployeeWithManyAbsences(String employeeId) {
        Absence sickAbsence = new Absence(LocalDate.of(2019, 9, 2), LeaveReason.SICK);
        Absence casualAbsence = new Absence(LocalDate.of(2019, 9, 3), LeaveReason.CasualLeave);
        Absence paidAbsence = new Absence(LocalDate.of(2019, 9, 4), LeaveReason.MaternityLeave);
        Absence disapprovedAbsence = new Absence(LocalDate.of(2019, 9, 5), LeaveReason.DisapprovedLeave);

        return createSalariedEmployeeWithAbsences(employeeId, sickAbsence, casualAbsence, paidAbsence, disapprovedAbsence);
    }

    private static SalariedEmployee createSalariedEmployeeWithAbsences(EmployeeId employeeId, Absence... leaves) {
        List<Absence> absences = new ArrayList<>(Arrays.asList(leaves));
        Salary salaryOfMonth = Salary.of("10000.00", Currency.RMB);

        return new SalariedEmployee(employeeId, salaryOfMonth, absences);
    }

    private static SalariedEmployee createSalariedEmployeeWithAbsences(String employeeId, Absence... leaves) {
        List<Absence> absences = new ArrayList<>(Arrays.asList(leaves));
        Salary salaryOfMonth = Salary.of("10000.00", Currency.RMB);

        return new SalariedEmployee(EmployeeId.of(employeeId), salaryOfMonth, absences);
    }

    public static Employee createEmployee(String employeeId, String name, String email, EmployeeType employeeType) {
        return new Employee(EmployeeId.of(employeeId), name, new Email(email), employeeType, LocalDate.now());
    }
}
