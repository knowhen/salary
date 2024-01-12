package org.when.salary.context.domain.employee.hourly;

import org.junit.jupiter.api.Test;
import org.when.salary.employee.domain.EmployeeId;
import org.when.salary.context.domain.employee.EmployeeFixture;
import org.when.salary.payroll.repository.HourlyEmployeeRepository;
import org.when.salary.payroll.domain.hourly.HourlyEmployee;
import org.when.salary.payroll.domain.hourly.TimeCard;
import org.when.salary.payroll.domain.hourly.TimeCardService;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TimeCardServiceTest {
    private String employeeId = "emp200901011111";
    @Test
    public void test_submit_time_card() {
        TimeCardService timeCardService = new TimeCardService();

        HourlyEmployeeRepository repository = mock(HourlyEmployeeRepository.class);
        HourlyEmployee employee = EmployeeFixture.createHourlyEmployee(EmployeeId.of(employeeId), null);
        when(repository.findById(EmployeeId.of(employeeId))).thenReturn(Optional.of(employee));

        timeCardService.setEmployeeRepository(repository);

        TimeCard timeCard = new TimeCard(LocalDate.now(), 8);

        timeCardService.submit(EmployeeId.of(employeeId), timeCard);

        assertThat(employee.timeCards()).hasSize(1);
    }
}