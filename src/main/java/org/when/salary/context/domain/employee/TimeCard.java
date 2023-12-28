package org.when.salary.context.domain.employee;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "timecards")
public class TimeCard {
    private static final int MAX_NORMAL_HOURS = 8;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate workDay;
    private int workHours;

    public TimeCard() {
    }

    public TimeCard(LocalDate data, int hours) {
        this.workDay = data;
        this.workHours = hours;
    }

    public LocalDate getWorkDay() {
        return workDay;
    }

    public void setWorkDay(LocalDate workDay) {
        this.workDay = workDay;
    }

    public int getWorkHours() {
        return workHours;
    }


    public long getOvertimeHours() {
        return getWorkHours() - MAX_NORMAL_HOURS;
    }

    public boolean isOvertime() {
        return getWorkHours() > MAX_NORMAL_HOURS;
    }

    public long getNormalHours() {
        return isOvertime() ? MAX_NORMAL_HOURS : getWorkHours();
    }

}
