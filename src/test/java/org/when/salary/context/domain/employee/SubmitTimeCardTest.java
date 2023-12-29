package org.when.salary.context.domain.employee;

import org.junit.jupiter.api.Test;
import org.when.salary.context.domain.EmployeeId;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitTimeCardTest {
    private final String employeeId = "emp200901011111";

    @Test
    public void test_validate_submitted_time_cards() {
        HourlyEmployee employee = EmployeeFixture.createHourlyEmployee(EmployeeId.of(employeeId), null);

        List<TimeCard> timeCards = EmployeeFixture.createTimeCards(8, 8, 8, 8, 8);

        assertThat(employee.timeCards()).isNotNull().isEmpty();

        employee.submit(timeCards);

        assertThat(employee.timeCards()).isNotNull().hasSize(5);
    }

    @Test
    public void test_submit_repeated_time_cards() {
        HourlyEmployee employee = EmployeeFixture.createHourlyEmployee(EmployeeId.of(employeeId), 8, 8, 8, 8, 8);

        List<TimeCard> timeCards = EmployeeFixture.createTimeCards(8, 8, 8, 8, 8);

        assertThat(employee.timeCards()).isNotNull().hasSize(5);

        employee.submit(timeCards);

        assertThat(employee.timeCards()).isNotNull().hasSize(5);
    }

    @Test
    public void test_submit_single_time_card() {
        HourlyEmployee employee = EmployeeFixture.createHourlyEmployee(EmployeeId.of(employeeId), 8, 8, 8, 8, 8);

        TimeCard repeatedTimeCard = new TimeCard(LocalDate.of(2023, 12, 11), 8);
        TimeCard newTimeCard = new TimeCard(LocalDate.now(), 8);

        assertThat(employee.timeCards()).isNotNull().hasSize(5);

        employee.submit(repeatedTimeCard);
        employee.submit(newTimeCard);

        assertThat(employee.timeCards()).isNotNull().hasSize(6);
    }
}
