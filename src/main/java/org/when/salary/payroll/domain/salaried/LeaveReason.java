package org.when.salary.payroll.domain.salaried;

public enum LeaveReason {
    SICK(1),
    CasualLeave(0.5),
    MaternityLeave(0),
    BereavementLeave(0),
    DisapprovedLeave(1);

    private double deductionRatio;

    LeaveReason(double deductionRatio) {
        this.deductionRatio = deductionRatio;
    }

    public double deductionRatio() {
        return this.deductionRatio;
    }

    public boolean withSalary() {
        return deductionRatio == 0;
    }
}
