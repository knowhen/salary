package org.when.salary.payroll.domain;

import java.util.List;

public interface PayrollCalculator {
    List<Payroll> execute(DateRange dateRange);
}
