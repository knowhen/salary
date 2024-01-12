package org.when.salary.payroll.domain;

public interface Payable {
    Payroll payroll(DateRange dateRange);
}
