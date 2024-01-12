package org.when.salary.context.domain;

import org.junit.jupiter.api.Test;
import org.when.salary.core.domain.Identity;
import org.when.salary.employee.domain.EmployeeId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeIdTest {
    @Test
    public void should_generate_unique_employee_id_following_rule() {
        Identity<String> nextEmpId = EmployeeId.next();
        assertTrue(nextEmpId.value().startsWith("EMP"));
        assertTrue(nextEmpId.value().contains(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        assertTrue(nextEmpId.value().length() == 14);
    }
}