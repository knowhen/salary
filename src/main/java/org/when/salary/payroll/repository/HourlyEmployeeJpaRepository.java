package org.when.salary.payroll.repository;

import org.when.salary.employee.domain.EmployeeId;
import org.when.salary.payroll.domain.hourly.HourlyEmployee;
import org.when.salary.core.persistence.Repository;
import org.when.salary.payroll.repository.HourlyEmployeeRepository;

import java.util.List;
import java.util.Optional;

public class HourlyEmployeeJpaRepository implements HourlyEmployeeRepository {

    private Repository<HourlyEmployee, EmployeeId> repository;

    public HourlyEmployeeJpaRepository(Repository<HourlyEmployee, EmployeeId> repository) {
        this.repository = repository;
    }

    @Override
    public void save(HourlyEmployee hourlyEmployee) {
        if (hourlyEmployee == null) {
            return;
        }
        repository.saveOrUpdate(hourlyEmployee);
    }

    @Override
    public Optional<HourlyEmployee> findById(EmployeeId id) {
        return repository.findById(id);
    }

    @Override
    public List<HourlyEmployee> findAll() {
        return repository.findAll();
    }
}
