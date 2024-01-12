package org.when.salary.context.domain;

import org.when.salary.payroll.domain.DateRange;
import org.when.salary.payroll.domain.Payroll;
import org.when.salary.payroll.domain.Salary;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PayrollCalculatorTest {
    protected void assertPayroll(String employeeId, List<Payroll> payrolls, int index, DateRange settlementPeriod, double payrollAmount) {
        Payroll payroll = payrolls.get(index);
        assertThat(payroll.getEmployeeId().value()).isEqualTo(employeeId);
        assertThat(payroll.startDate()).isEqualTo(settlementPeriod.getStartDate());
        assertThat(payroll.endDate()).isEqualTo(settlementPeriod.getEndDate());
        assertThat(payroll.amount()).isEqualTo(Salary.of(payrollAmount));
    }
}