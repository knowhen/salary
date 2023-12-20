package org.when.salary.context.domain.employee;

import java.time.Duration;
import java.time.LocalDate;

public class TimeCard {
    private static final int MAX_NORMAL_HOURS = 8;
    private LocalDate workDay;
    private Duration workHours;

    public TimeCard(LocalDate data, Duration hours) {
        this.workDay = data;
        this.workHours = hours;
    }

    public LocalDate getWorkDay() {
        return workDay;
    }

    public void setWorkDay(LocalDate workDay) {
        this.workDay = workDay;
    }

    public Duration getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Duration workHours) {
        this.workHours = workHours;
    }

    public long getOvertimeHours() {
        return getWorkHours().toHours() - MAX_NORMAL_HOURS;
    }

    public boolean isOvertime() {
        return getWorkHours().toHours() > MAX_NORMAL_HOURS;
    }

    public long getNormalHours() {
        return isOvertime() ? MAX_NORMAL_HOURS : getWorkHours().toHours();
    }

}
