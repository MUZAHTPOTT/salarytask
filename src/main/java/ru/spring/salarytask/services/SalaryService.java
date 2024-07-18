/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.services;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spring.salarytask.models.Employee;
import ru.spring.salarytask.models.EmployeeBonus;
import ru.spring.salarytask.models.GroupNumber;
import static ru.spring.salarytask.models.GroupNumber.manager;
import static ru.spring.salarytask.models.GroupNumber.salesman;
import ru.spring.salarytask.repositories.EmployeeRepository;

/**
 * Сервис для управления заработными платами сотрудников
 */
@Service
public class SalaryService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
    String pattern = "#.00";
    DecimalFormat decimalFormat = new DecimalFormat(pattern, otherSymbols);
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Возвращает список подчиненных для заданного ID начальника
     * @param id ID начальника
     */
    public List<Employee> getSubordinate(Long id){
        return employeeRepository.findSubordinateByEmployee(id);
    }
    
    /**
     * Возвращает список сотрудников без начальника
     */
    public List<Employee> getSubordinateWithoutBoss(){
        return employeeRepository.findSubordinateWithoutBoss();
    }
    
    /**
     * Рекурсивно возвращает список из всех уровней подчиненных сотрудника для заданного ID
     * @param id ID сотрудника
     */
    public List<Employee> getRecursiveAllSubordinate(Long id){
        
        List<Employee> allSubordinates = new ArrayList<>();
        for (Employee subordinate : getSubordinate(id)) {
            allSubordinates.add(subordinate);
            allSubordinates.addAll(getRecursiveAllSubordinate(subordinate.getId()));
        }
        
        return allSubordinates;
    }
    
    /**
     * Возвращает объект 'EmployeeBonus' для заданного сотрудника и даты с заполненными атрибутами: 
     * employee, experienceBonus, subordinateBonus, subordinates
     * @param employee заданный сотрудник
     * @param needDate заданная дата
     */
    private EmployeeBonus getCreatedEmployeeBonus(Employee employee, LocalDate needDate){
        
         EmployeeBonus employeeBonus = new EmployeeBonus(
            employee,
            getExperienceBonusValue(employee, needDate),
            0.0                   
        );
        if (employee.getGroup() == GroupNumber.manager){
            employeeBonus.setSubordinate(getSubordinate(employee.getId()));
            
        } else if (employee.getGroup() == GroupNumber.salesman){
            List<Employee> allSubordinates = getRecursiveAllSubordinate(employee.getId());
            employeeBonus.setSubordinate(allSubordinates);
            
        } else{
            employeeBonus.setSubordinate(new ArrayList<Employee>());
        }
        
        return employeeBonus;
    }
    
    /**
     * Возвращает объект 'EmployeeBonus' с заполненными атрибутами: subordinatesBonus, totalSalary
     * @param employee заданный сотрудник
     * @param employeeBonusList список, созданных объектов 'EmployeeBonus'
     * @param employeeBonus заданный объект 'EmployeeBonus'
     * @return 
     */
    private EmployeeBonus getTotalSalaryAndSubordinateBonus(Employee employee, List<EmployeeBonus> employeeBonusList, EmployeeBonus employeeBonus){
        
        Double subordinateCoefficient = employee.getGroup().getSubordinateBonusCoefficient();

        Double subordinatesBonus = 0.0;
        for (EmployeeBonus bonus: employeeBonusList){
            if (employeeBonus.getSubordinate().contains(bonus.getEmployee())) {
                subordinatesBonus += subordinateCoefficient * (bonus.getEmployee().getSalary() + bonus.getExperienceBonus() + bonus.getSubordinatesBonus());
            }
        }
        subordinatesBonus = Double.parseDouble(String.format("%.2f", subordinatesBonus).replace(",", "."));

        employeeBonus.setSubordinatesBonus(subordinatesBonus);
        employeeBonus.setTotalSalary(Double.valueOf(decimalFormat.format(
            employeeBonus.getEmployee().getSalary() +
            employeeBonus.getExperienceBonus() +
            employeeBonus.getSubordinatesBonus()
        )));
        
        return employeeBonus;
    }
    
    
    /**
     * Возвращает список объектов 'EmployeeBonus' после расчета всех компонентов заработной платы (надбавки) на заданную дату для заданного сотрудника
     * @param employee сотрудник
     * @param needDate заданная дата
     */
    public List<EmployeeBonus> getEmployeeBonusListAfterSalaryCalculation(Employee employee, LocalDate needDate){        
       
        EmployeeBonus employeeBonus = getCreatedEmployeeBonus(employee, needDate);
        
        List<EmployeeBonus> employeeBonusList = new ArrayList<>();
        employeeBonusList.addAll(getEmployeeBonusList(employee.getId(), needDate));

        employeeBonusList.add(getTotalSalaryAndSubordinateBonus(employee, employeeBonusList, employeeBonus));
        
        return employeeBonusList;
    }
    
    /**
     * Рекурсивно возвращает список объектов 'EmployeeBonus' на заданную дату для заданного ID
     * @param id ID сотрудника
     * @param needDate заданная дата
     */
    private List<EmployeeBonus> getEmployeeBonusList(Long id, LocalDate needDate){
        
        List<EmployeeBonus> employeeBonusList = new ArrayList<>();
        
        for (Employee subordinate : getSubordinate(id)){
            
            EmployeeBonus employeeBonus = getCreatedEmployeeBonus(subordinate, needDate);
            
            employeeBonusList.addAll(getEmployeeBonusList(subordinate.getId(), needDate));
                       
            employeeBonusList.add(getTotalSalaryAndSubordinateBonus(subordinate, employeeBonusList, employeeBonus));                       
        }
        
        return employeeBonusList;
    }
    
    /**
     * Возвращает значение надбавки за стаж работы на заданную дату для заданного сотрудника
     * @param employee сотрудник
     * @param needDate заданная дата
     */
    private Double getExperienceBonusValue(Employee employee, LocalDate needDate){
        
        Period period = Period.between(employee.getDate(), needDate);
        Integer yearsOfExperience = period.getYears();
        
        Double bonusMax = employee.getGroup().getMaxExperienceBonusCoefficient();
        Double bonus = employee.getGroup().getExperienceBonusCoefficient();  

        Double experienceBonus = yearsOfExperience * bonus;
        if (experienceBonus > bonusMax) {
            experienceBonus = bonusMax;
        }
                
        return Double.parseDouble(String.format("%.2f", employee.getSalary() * experienceBonus).replace(",", "."));
    }
    
    
}