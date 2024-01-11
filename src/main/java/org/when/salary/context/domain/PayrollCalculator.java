package org.when.salary.context.domain;

import java.util.List;

public interface PayrollCalculator {
    List<Payroll> execute(DateRange dateRange);
}
