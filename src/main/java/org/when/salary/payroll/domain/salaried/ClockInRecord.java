package org.when.salary.payroll.domain.salaried;

import org.when.salary.employee.domain.EmployeeId;

import java.time.LocalDateTime;

public class ClockInRecord {
    private final EmployeeId employeeId;
    private final LocalDateTime clockIn;
    private final LocalDateTime clockOut;

    public ClockInRecord(String employeeId, LocalDateTime clockIn, LocalDateTime clockOut) {
        this.employeeId = EmployeeId.of(employeeId);
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    public AttendanceStatus getStatus(WorkHour workHour) {
        if (workHour.isLate(clockIn)) {
            return AttendanceStatus.LATE;
        }
        if (workHour.isLeaveEarly(clockOut)) {
            return AttendanceStatus.ABSENT;
        }
        return AttendanceStatus.NORMAL;
    }
}
