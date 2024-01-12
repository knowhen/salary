package org.when.salary.payroll.domain.hourly;

import org.when.salary.payroll.domain.DateRange;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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

    public boolean withInRange(DateRange range) {
        return range.contains(workDay);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeCard)) return false;
        TimeCard timeCard = (TimeCard) o;
        return Objects.equals(workDay, timeCard.workDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workDay);
    }
}
