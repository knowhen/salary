package org.when.salary.payroll.domain.salaried;

import java.time.LocalDate;

public interface LeaveRepository {
    boolean isAbsent(String employeeId, LocalDate localDate);
}
