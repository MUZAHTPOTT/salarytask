/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.salarytask.models.Employee;
import ru.spring.salarytask.repositories.EmployeeRepository;

/**
 * Сервис для предоставления и работы с данными о сотрудниках  
 */
@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Возвращает сотрудника по заданному идентификатору в виде Optional
     * @param id идентификатор сотрудника
     */
    public Optional<Employee> getEmployeeOptionalByID(Long id){
        return employeeRepository.findById(id);
    }
    
    /**
     * Возвращает сотрудника по заданному идентификатору
     * @param id идентификатор сотрудника
     */
    public Employee getEmployeeFromOptionalByID(Long id){
        Optional<Employee> employeeOptional = getEmployeeOptionalByID(id);
        ArrayList<Employee> res = new ArrayList<>();
        employeeOptional.ifPresent(res::add);
        Employee employee = res.get(0);
        
        return employee;
    }
    
    /**
     * Возвращает всех сотрудников из базы данных
     */
    public Iterable<Employee> getAllEmployees(){
        return employeeRepository.findAllEmployees();
    }
    
    /**
     * Возвращает всех сотрудников с должностью 'manager' или 'salesman' из базы данных
     */
    public Iterable<Employee> getAllManagersAndSalesmen(){
        return employeeRepository.findAllManagersAndSalesmen();
    }
    
    /**
     * Сохранения сотрудника в базе данных
     * @param employee объект Employee для сохранения
     */
    public void save(Employee employee){
        employeeRepository.save(employee);
    }
    
    /**
     * Удаление сотрудника из базы данных
     * @param id идентификатор сотрудника
     */
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
