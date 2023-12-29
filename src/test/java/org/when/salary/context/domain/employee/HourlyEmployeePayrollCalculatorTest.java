package org.when.salary.context.domain.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.when.salary.context.domain.*;
import org.when.salary.context.repository.HourlyEmployeeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.when.salary.context.domain.employee.EmployeeFixture.createHourlyEmployee;

class HourlyEmployeePayrollCalculatorTest {
    private DateRange settlementPeriod;
    private HourlyEmployeeRepository mockRepo;
    private ArrayList<HourlyEmployee> hourlyEmployees;
    private HourlyEmployeePayrollCalculator calculator;

    @BeforeEach
    public void setUp() throws Exception {
        settlementPeriod = new DateRange(LocalDate.of(2023, 12, 11), LocalDate.of(2023, 12, 15));
        mockRepo = mock(HourlyEmployeeRepository.class);
        hourlyEmployees = new ArrayList<>();
        calculator = new HourlyEmployeePayrollCalculator();
    }

    @Test
    public void calculate_payroll_when_no_matched_employee() {

        when(mockRepo.findAll()).thenReturn(new ArrayList());

        calculator.setRepository(mockRepo);

        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        verify(mockRepo, times(1)).findAll();
        assertNotNull(payrolls);
        assertEquals(0, payrolls.size());
    }

    @Test
    public void test() {
        //given
        EmployeeId employeeId = EmployeeId.of("emp202312010001");
        HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, 8, 8, 8, 8, 8);
        hourlyEmployees.add(hourlyEmployee);

        when(mockRepo.findAll()).thenReturn(hourlyEmployees);

        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).findAll();
        assertNotNull(payrolls);
        assertEquals(1, payrolls.size());

        assertPayroll(employeeId, payrolls, 0, settlementPeriod, "60000");
    }

    @Test
    public void should_calculate_payroll_when_more_than_one_matched_employee_found() {
        //given
        EmployeeId employeeId1 = EmployeeId.of("emp200901011111");
        HourlyEmployee hourlyEmployee1 = createHourlyEmployee(employeeId1, 8, 8, 8, 8, 8);
        hourlyEmployees.add(hourlyEmployee1);

        EmployeeId employeeId2 = EmployeeId.of("emp200901011112");
        HourlyEmployee hourlyEmployee2 = createHourlyEmployee(employeeId2, 9, 7, 10, 10, 8);
        hourlyEmployees.add(hourlyEmployee2);

        EmployeeId employeeId3 = EmployeeId.of("emp200901011113");
        HourlyEmployee hourlyEmployee3 = createHourlyEmployee(employeeId3, null);
        hourlyEmployees.add(hourlyEmployee3);

        when(mockRepo.findAll()).thenReturn(hourlyEmployees);
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).findAll();
        assertNotNull(payrolls);
        assertEquals(3, payrolls.size());

        assertPayroll(employeeId1, payrolls, 0, settlementPeriod, "60000.00");
        assertPayroll(employeeId2, payrolls, 1, settlementPeriod, "69750.00");
        assertPayroll(employeeId3, payrolls, 2, settlementPeriod, "0.00");
    }

    private void assertPayroll(EmployeeId employeeId, List<Payroll> payrolls, int index, DateRange settlementPeriod, String payrollAmount) {
        Payroll payroll = payrolls.get(index);
        assertEquals(employeeId, payroll.getEmployeeId());
        assertEquals(settlementPeriod.getStartDate(), payroll.startDate());
        assertEquals(settlementPeriod.getEndDate(), payroll.endDate());
        assertEquals(Salary.of(payrollAmount, Currency.RMB), payroll.amount());
    }
}