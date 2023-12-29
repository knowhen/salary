package org.when.salary.context.repository;

import org.junit.jupiter.api.Test;
import org.when.salary.context.domain.*;
import org.when.salary.context.domain.employee.EmployeeFixture;
import org.when.salary.context.domain.employee.hourly.HourlyEmployee;
import org.when.salary.context.domain.employee.hourly.TimeCard;
import org.when.salary.context.domain.employee.salaried.Absence;
import org.when.salary.context.domain.employee.salaried.SalariedEmployee;
import org.when.salary.core.persistence.EntityManagers;
import org.when.salary.core.persistence.Repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryIntegrationTest {

    private static final String PERSISTENCE_UNIT_NAME = "Payroll_JPA";

    @Test
    public void should_query_employee_table_and_get_it_by_id() {
        //given
        Repository<Employee, EmployeeId> employeeRepo = new Repository<>(Employee.class, EntityManagers.from("Payroll_JPA"));

        //when
        Optional<Employee> optEmployee = employeeRepo.findById(EmployeeId.of("emp200109101000001"));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        Employee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo("emp200109101000001");
        assertThat(employee.name()).isEqualTo("Bruce");
        assertThat(employee.email().value()).isEqualTo("bruce@payroll.com");
        assertThat(employee.isHourly()).isTrue();
        assertThat(employee.isMale()).isTrue();
        assertThat(employee.address()).isEqualTo(new Address("China", "SiChuan", "chengdu", "qingyang avenue", "600000"));
        assertThat(employee.contact()).isEqualTo(Contact.of("15028150000"));
        assertThat(employee.contact().homePhone()).isNull();
    }

    @Test
    public void should_query_hourly_employee_and_related_timecards_by_id() {
        //given
        String employeeId = "emp200109101000001";
        Repository<HourlyEmployee, EmployeeId> employeeRepo = createHourlyEmployeeRepository();

        //when
        Optional<HourlyEmployee> optEmployee = employeeRepo.findById(EmployeeId.of(employeeId));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        HourlyEmployee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.hourlySalary()).isEqualTo(Salary.of(100.00));

        List<TimeCard> timeCards = employee.timeCards();
        assertThat(timeCards)
                .isNotNull()
                .hasSize(5);

        TimeCard timeCard = timeCards.get(0);
        assertThat(timeCard.getWorkHours()).isEqualTo(8);
        assertThat(timeCard.getNormalHours()).isEqualTo(8);
        assertThat(timeCard.getOvertimeHours()).isEqualTo(0);
        assertThat(timeCard.isOvertime()).isFalse();
    }

    @Test
    public void should_query_salaried_employee_and_related_absences_by_id() {
        //given
        String employeeId = "emp201110101000003";
        Repository<SalariedEmployee, EmployeeId> employeeRepo = createSalariedEmployeeRepository();

        //when
        Optional<SalariedEmployee> optEmployee = employeeRepo.findById(EmployeeId.of(employeeId));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        SalariedEmployee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.monthlySalary()).isEqualTo(Salary.of(10000.00));

        List<Absence> absences = employee.absences();
        assertThat(absences).isNotNull().hasSize(4);

        Absence absence = absences.get(0);
        assertThat(absence.withSalary()).isFalse();
    }

    @Test
    public void should_get_all_entities() {
        //given
        Repository<Employee, EmployeeId> repository = createEmployeeRepository();

        //when
        List<Employee> employees = repository.findAll();

        //then
        assertThat(employees).isNotNull().hasSize(5);
    }

    @Test
    public void should_get_all_entities_by_criteria() {
        Repository<Employee, EmployeeId> repository = createEmployeeRepository();

        List<Employee> hourlyEmployees = repository.findBy((builder, query, root) ->
                builder.equal(root.get("employeeType"), EmployeeType.HOURLY)
        );

        assertThat(hourlyEmployees).isNotNull().hasSize(2);
    }

    @Test
    public void test_find_all_hourly_employees() {
        Repository<HourlyEmployee, EmployeeId> repository = createHourlyEmployeeRepository();

        List<HourlyEmployee> hourlyEmployees = repository.findAll();

        assertThat(hourlyEmployees).isNotNull().hasSize(2);
    }

    @Test
    public void test_find_all_salaried_employees() {
        Repository<SalariedEmployee, EmployeeId> repository = createSalariedEmployeeRepository();

        List<SalariedEmployee> salariedEmployees = repository.findAll();

        assertThat(salariedEmployees).isNotNull().hasSize(1);
    }

    @Test
    public void test_find_all_employees_by_type() {
        Repository<Employee, EmployeeId> repository = createEmployeeRepository();

        List<Employee> typedEmployees = repository.findBy((builder, query, root) ->
                builder.equal(root.get("employeeType"), EmployeeType.HOURLY)
        );

        assertThat(typedEmployees).isNotNull().hasSize(2);
    }

    @Test
    public void test_add_and_remove_employee() {
        String employeeId = "emp200109101000001";
        Repository<Employee, EmployeeId> repository = createEmployeeRepository();
        Employee employee = EmployeeFixture.createEmployee(employeeId, "test", "test@test.com", EmployeeType.HOURLY);

        repository.saveOrUpdate(employee);

        Optional<Employee> newEmployee = repository.findById(EmployeeId.of(employeeId));

        assertThat(newEmployee.isPresent()).isTrue();

        repository.delete(employee);

        Optional<Employee> droppedEmployee = repository.findById(EmployeeId.of(employeeId));
        assertThat(droppedEmployee.isPresent()).isFalse();
    }

    private Repository<SalariedEmployee, EmployeeId> createSalariedEmployeeRepository() {
        return new Repository<>(SalariedEmployee.class, EntityManagers.from(PERSISTENCE_UNIT_NAME));
    }

    private Repository<HourlyEmployee, EmployeeId> createHourlyEmployeeRepository() {
        return new Repository<>(HourlyEmployee.class, EntityManagers.from(PERSISTENCE_UNIT_NAME));
    }

    private Repository<Employee, EmployeeId> createEmployeeRepository() {
        return new Repository<>(Employee.class, EntityManagers.from(PERSISTENCE_UNIT_NAME));
    }
}