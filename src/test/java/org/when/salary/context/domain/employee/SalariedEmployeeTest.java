package org.when.salary.context.domain.employee;

import org.junit.jupiter.api.Test;
import org.when.salary.context.domain.Currency;
import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.Money;
import org.when.salary.context.domain.Payroll;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SalariedEmployeeTest {
    private final String employeeId = "emp202312010001";
    private DateRange settlementPeriod = new DateRange(YearMonth.of(2023, 12));

    @Test
    public void return_monthly_salary_when_employee_present_at_duty_every_day() {

        SalariedEmployee employee = EmployeeFixture.createSalariedEmployee(employeeId, Money.of("10000", Currency.RMB));

        Payroll payroll = employee.payroll(settlementPeriod);

        assertPayroll(payroll, employeeId, LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 31), Money.of("10000", Currency.RMB));

    }

    @Test
    public void deduct_salary_when_employee_asked_for_a_leave() {

        Money monthlySalary = Money.of("10000", Currency.RMB);

        Absence absence = new Absence(employeeId, LocalDate.of(2023, 12, 1), LeaveReason.SICK);
        List<Absence> absences = new ArrayList<>();
        absences.add(absence);

        SalariedEmployee employee = EmployeeFixture.createSalariedEmployee(employeeId, monthlySalary, absences);

        Payroll payroll = employee.payroll(settlementPeriod);

        assertPayroll(payroll, employeeId, LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 31), Money.of("9545.45", Currency.RMB));

    }

    @Test
    public void should_deduct_salary_if_employee_ask_many_leaves() {
        //given
        SalariedEmployee salariedEmployee = EmployeeFixture.salariedEmployeeWithManyAbsences(employeeId);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        Money expectedAmount = Money.of("8863.62", Currency.RMB);
        assertPayroll(
                payroll,
                employeeId,
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 31),
                expectedAmount);
    }

    private static void assertPayroll(Payroll payroll, String employeeId, LocalDate startDate, LocalDate endDate, Money amount) {
        assertNotNull(payroll);
        assertEquals(employeeId, payroll.getEmployeeId());
        assertEquals(startDate, payroll.startDate());
        assertEquals(endDate, payroll.endDate());
        assertEquals(amount, payroll.amount());
    }

}