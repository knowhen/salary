package org.when.salary.context.repository;

import org.junit.jupiter.api.Test;
import org.when.salary.context.domain.Address;
import org.when.salary.context.domain.Contact;
import org.when.salary.context.domain.Employee;
import org.when.salary.context.domain.EmployeeId;
import org.when.salary.core.persistence.EntityManagers;
import org.when.salary.core.persistence.Repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryIntegrationTest {

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
}