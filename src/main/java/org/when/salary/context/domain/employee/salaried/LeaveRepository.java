package org.when.salary.context.domain.employee.salaried;

import java.time.LocalDate;

public interface LeaveRepository {
    boolean isAbsent(String employeeId, LocalDate localDate);
}
