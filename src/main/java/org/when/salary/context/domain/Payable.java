package org.when.salary.context.domain;

public interface Payable {
    Payroll payroll(DateRange dateRange);
}
