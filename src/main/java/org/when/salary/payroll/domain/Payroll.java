package org.when.salary.payroll.domain;

import org.when.salary.employee.domain.EmployeeId;

import java.time.LocalDate;

public class Payroll {
    private EmployeeId employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Salary amount;

    public Payroll(EmployeeId employeeId, LocalDate startDate, LocalDate endDate, Salary amount) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public Salary amount() {
        return amount;
    }
}
