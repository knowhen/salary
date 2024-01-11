package org.when.salary.context.domain.employee.salaried;

import java.time.LocalDate;
import java.util.Optional;

public interface ClockInRecordRepository {
    Optional<ClockInRecord> findByIdAndDate(String employeeId, LocalDate date);
}
