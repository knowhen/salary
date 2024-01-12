package org.when.salary.payroll.domain.salaried;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class WorkHour {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public WorkHour(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isLate(LocalDateTime clockIn) {
        LocalTime clockInTime = clockIn.toLocalTime();
        return clockInTime.isAfter(startTime);
    }

    public boolean isLeaveEarly(LocalDateTime clockOut) {
        LocalTime clockOutTime = clockOut.toLocalTime();
        return clockOutTime.isBefore(endTime);
    }
}
