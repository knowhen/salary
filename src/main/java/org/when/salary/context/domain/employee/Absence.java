package org.when.salary.context.domain.employee;

import org.when.salary.context.domain.DateRange;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
public class Absence {
    @Enumerated(EnumType.STRING)
    private LeaveReason leaveReason;
    private LocalDate absenceDate;

    public Absence() {
    }

    public Absence(LocalDate absenceDate, LeaveReason leaveReason) {
        this.absenceDate = absenceDate;
        this.leaveReason = leaveReason;
    }

    public boolean withSalary() {
        return leaveReason.withSalary();
    }

    public double deductionRatio() {
        return leaveReason.deductionRatio();
    }

    public boolean withInRange(DateRange range) {
        return range.contains(absenceDate);
    }
}
