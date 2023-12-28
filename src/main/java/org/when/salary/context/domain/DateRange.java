package org.when.salary.context.domain;

import org.when.salary.context.domain.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.YearMonth;

public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateRange(YearMonth yearMonth) {
        this(yearMonth.atDay(1), yearMonth.atEndOfMonth());
    }

    public DateRange(int year, int month) {
        if (month < 1 || month > 12) {
            throw new InvalidDateException("Invalid month value: " + month);
        }
        YearMonth yearMonth = YearMonth.of(year, month);
        this.startDate = yearMonth.atDay(1);
        this.endDate = yearMonth.atEndOfMonth();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
