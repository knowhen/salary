package org.when.salary.context.repository;

import org.when.salary.context.domain.EmployeeId;
import org.when.salary.context.domain.employee.hourly.HourlyEmployee;
import org.when.salary.core.persistence.Repository;

import java.util.List;
import java.util.Optional;

public class HourlyEmployeeJpaRepository implements HourlyEmployeeRepository{

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
