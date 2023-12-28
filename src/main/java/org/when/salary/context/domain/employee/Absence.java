package org.when.salary.context.domain.employee;

import java.time.LocalDate;

public class Absence {
    private String employeeId;
    private LeaveReason leaveReason;
    private LocalDate abseceDate;

    public Absence(String employeeId, LocalDate absenceDate, LeaveReason leaveReason) {
        this.employeeId = employeeId;
        this.abseceDate = absenceDate;
        this.leaveReason = leaveReason;
    }

    public boolean withSalary() {
        return leaveReason.withSalary();
    }

    public double deductionRatio() {
        return leaveReason.deductionRatio();
    }
}
