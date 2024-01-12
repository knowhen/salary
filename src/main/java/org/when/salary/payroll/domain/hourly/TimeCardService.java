package org.when.salary.payroll.domain.hourly;

import org.when.salary.employee.domain.EmployeeId;
import org.when.salary.payroll.repository.HourlyEmployeeRepository;

import java.util.Optional;

public class TimeCardService {
    private HourlyEmployeeRepository employeeRepository;

    public void setEmployeeRepository(HourlyEmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    public void submit(EmployeeId id, TimeCard timeCard) {
        Optional<HourlyEmployee> employee = employeeRepository.findById(id);
        employee.ifPresent(e -> {
            e.submit(timeCard);
            employeeRepository.save(e);
        });

    }
}
