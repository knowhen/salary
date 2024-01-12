package org.when.salary.context.domain.employee.salaried;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.when.salary.payroll.domain.DateRange;
import org.when.salary.payroll.domain.Payroll;
import org.when.salary.context.domain.PayrollCalculatorTest;
import org.when.salary.context.domain.employee.EmployeeFixture;
import org.when.salary.payroll.repository.SalariedEmployeeRepository;
import org.when.salary.payroll.domain.salaried.SalariedEmployee;
import org.when.salary.payroll.domain.salaried.SalariedEmployeePayrollCalculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.when.salary.context.domain.employee.EmployeeFixture.createSalariedEmployee;

class SalariedEmployeePayrollCalculatorTest extends PayrollCalculatorTest {
    private DateRange settlementPeriod;
    private SalariedEmployeeRepository mockRepository;
    private List<SalariedEmployee> salariedEmployees;
    private SalariedEmployeePayrollCalculator calculator;

    @BeforeEach
    public void setup() {
        settlementPeriod = new DateRange(LocalDate.of(2019, 9, 2), LocalDate.of(2019, 9, 6));
        mockRepository = mock(SalariedEmployeeRepository.class);
        salariedEmployees = new ArrayList<>();
        calculator = new SalariedEmployeePayrollCalculator();
    }

    @Test
    public void test_calculate_payroll_when_no_matched_employee_found() {
        //given
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());

        SalariedEmployeePayrollCalculator calculator = new SalariedEmployeePayrollCalculator();
        calculator.setRepository(mockRepository);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepository, times(1)).findAll();
        assertThat(payrolls).isNotNull().isEmpty();
    }

    @Test
    public void should_calculate_payroll_when_only_one_matched_employee_found() {
        //given
        String employeeId = "emp200901011111";
        SalariedEmployee salariedEmployee = createSalariedEmployee(employeeId, 10000.00);
        salariedEmployees.add(salariedEmployee);

        when(mockRepository.findAll()).thenReturn(salariedEmployees);
        calculator.setRepository(mockRepository);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepository, times(1)).findAll();

        assertThat(payrolls).isNotNull().hasSize(1);
        assertPayroll(employeeId, payrolls, 0, settlementPeriod, 10000.00);
    }

    @Test
    public void should_calculate_payroll_when_more_than_one_matched_employee_found() {
        //given
        String employeeId1 = "emp200901011111";
        SalariedEmployee salariedEmployee1 = EmployeeFixture.salariedEmployeeWithOneSickAbsence(employeeId1);
        salariedEmployees.add(salariedEmployee1);

        String employeeId2 = "emp200901011112";
        SalariedEmployee salariedEmployee2 = createSalariedEmployee(employeeId2, 5000.00);
        salariedEmployees.add(salariedEmployee2);

        String employeeId3 = "emp200901011113";
        SalariedEmployee salariedEmployee3 = createSalariedEmployee(employeeId3, 8000.00);
        salariedEmployees.add(salariedEmployee3);

        when(mockRepository.findAll()).thenReturn(salariedEmployees);
        calculator.setRepository(mockRepository);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepository, times(1)).findAll();

        assertThat(payrolls).isNotNull().hasSize(3);
        assertPayroll(employeeId1, payrolls, 0, settlementPeriod, 9545.45);
        assertPayroll(employeeId2, payrolls, 1, settlementPeriod, 5000.00);
        assertPayroll(employeeId3, payrolls, 2, settlementPeriod, 8000.00);
    }

}