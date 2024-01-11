package org.when.salary.context.domain.employee.salaried;

public enum AttendanceStatus {
    NORMAL,
    LATE,
    ABSENT;

    public boolean isNormal() {
        return this == NORMAL;
    }

    public boolean isLate() {
        return this == LATE;
    }

    public boolean isAbsent() {
        return this == ABSENT;
    }
}
