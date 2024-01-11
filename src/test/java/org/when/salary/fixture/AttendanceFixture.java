package org.when.salary.fixture;

import org.when.salary.context.domain.employee.salaried.ClockInRecord;
import org.when.salary.context.domain.employee.salaried.WorkHour;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceFixture {
    public static WorkHour getWordHour() {
        LocalTime startTime = LocalTime.of(8, 30);
        LocalTime endTime = LocalTime.of(18, 0);
        return new WorkHour(startTime, endTime);
    }

    public static ClockInRecord getNormalClockInRecord() {
        return getClockInRecord(LocalDate.now().atTime(8, 30), LocalDate.now().atTime(18, 0));
    }

    public static ClockInRecord getClockInRecord(LocalDateTime clockIn, LocalDateTime clockOut) {
        String employeeId = "emp001";
        return new ClockInRecord(employeeId, clockIn, clockOut);
    }
}
