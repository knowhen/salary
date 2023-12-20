package org.when.salary.context.domain;

import java.time.LocalDate;

public class Payroll {
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Money amount;

    public Payroll(String employeeId, LocalDate startDate, LocalDate endDate, Money amount) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public Money amount() {
        return amount;
    }
}
