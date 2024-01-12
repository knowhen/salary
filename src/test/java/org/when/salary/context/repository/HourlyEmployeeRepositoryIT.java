package org.when.salary.context.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.when.salary.employee.domain.EmployeeId;
import org.when.salary.payroll.domain.hourly.HourlyEmployee;
import org.when.salary.payroll.domain.hourly.TimeCard;
import org.when.salary.core.persistence.Repository;
import org.when.salary.fixture.EntityManagerFixture;
import org.when.salary.payroll.repository.HourlyEmployeeJpaRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class HourlyEmployeeRepositoryIT {
    private EntityManager entityManager;
    private Repository<HourlyEmployee, EmployeeId> repository;
    private HourlyEmployeeJpaRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        entityManager = EntityManagerFixture.createEntityManager();
        repository = new Repository<>(HourlyEmployee.class, entityManager);
        employeeRepository = new HourlyEmployeeJpaRepository(repository);
    }

    @Test
    public void should_submit_time_card_then_remove_it() {
        EmployeeId employeeId = EmployeeId.of("emp200109101000001");

        HourlyEmployee hourlyEmployee = employeeRepository.findById(employeeId).get();

        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.timeCards()).hasSize(5);

        TimeCard repeatedCard = new TimeCard(LocalDate.of(2019, 9, 2), 8);
        hourlyEmployee.submit(repeatedCard);
        employeeRepository.save(hourlyEmployee);

        hourlyEmployee = employeeRepository.findById(employeeId).get();
        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.timeCards()).hasSize(5);

        TimeCard submittedCard = new TimeCard(LocalDate.of(2019, 10, 8), 8);
        hourlyEmployee.submit(submittedCard);
        employeeRepository.save(hourlyEmployee);

        hourlyEmployee = employeeRepository.findById(employeeId).get();
        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.timeCards()).hasSize(6);

        hourlyEmployee.remove(submittedCard);
        employeeRepository.save(hourlyEmployee);
        assertThat(hourlyEmployee.timeCards()).hasSize(5);
    }
}
