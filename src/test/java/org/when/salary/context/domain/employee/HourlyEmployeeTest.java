package org.when.salary.context.domain.employee;

import org.junit.jupiter.api.Test;
import org.when.salary.context.domain.Currency;
import org.when.salary.context.domain.DateRange;
import org.when.salary.context.domain.Money;
import org.when.salary.context.domain.Payroll;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.when.salary.context.domain.employee.EmployeeFixture.createHourlyEmployee;


public class HourlyEmployeeTest {
    private final String employeeId = "emp202312010001";
    private final Money hourlySalary = Money.of("1500", Currency.RMB);
    private final DateRange dateRange = new DateRange(LocalDate.of(2023, 12, 11), LocalDate.of(2023, 12, 15));

    @Test
    public void test_calculate_payroll_by_work_hours_in_a_week() {
        HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, 8, 8, 8, 8, 8);

        Payroll payroll = hourlyEmployee.payroll(dateRange);

        assertNotNull(payroll);
        assertEquals(payroll.startDate(), LocalDate.of(2023, 12, 11));
        assertEquals(payroll.endDate(), LocalDate.of(2023, 12, 15));
        assertEquals(payroll.amount(), Money.of("60000", Currency.RMB));
    }

    @Test
    public void test_calculate_payroll_by_work_hours_overtime_in_a_week() {
        HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, 9, 7, 10, 10, 8);

        Payroll payroll = hourlyEmployee.payroll(dateRange);

        assertNotNull(payroll);
        assertEquals(payroll.startDate(), LocalDate.of(2023, 12, 11));
        assertEquals(payroll.endDate(), LocalDate.of(2023, 12, 15));
        assertEquals(payroll.amount(), Money.of("69750", Currency.RMB));
    }

    @Test
    public void test_calculate_payroll_without_any_time_card_in_a_week() {
        HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, new ArrayList<>());

        Payroll payroll = hourlyEmployee.payroll(dateRange);

        assertNotNull(payroll);
        assertEquals(payroll.startDate(), LocalDate.of(2023, 12, 11));
        assertEquals(payroll.endDate(), LocalDate.of(2023, 12, 15));
        assertEquals(payroll.amount(), Money.of("0.00", Currency.RMB));
    }

    @Test
    public void test_calculate_payroll_when_time_card_is_null() {
        HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, null);

        Payroll payroll = hourlyEmployee.payroll(dateRange);

        assertNotNull(payroll);
        assertEquals(payroll.startDate(), LocalDate.of(2023, 12, 11));
        assertEquals(payroll.endDate(), LocalDate.of(2023, 12, 15));
        assertEquals(payroll.amount(), Money.of("0.00", Currency.RMB));
    }


}
