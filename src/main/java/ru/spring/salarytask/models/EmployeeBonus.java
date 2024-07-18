/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.models;

import java.util.List;

/**
 * Класс для хранения информации о компонентах заработной платы сотрудника
 */
public class EmployeeBonus {
    
    private Employee employee;   
    private Double experienceBonus;  
    private Double subordinatesBonus;
    private List<Employee> subordinate;
    
    private Double totalSalary;
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Конструктор по умолчанию
     */
    public EmployeeBonus(){
        
    }
    
    /**
     * Конструктор с параметрами
     * @param employee сотрудник
     * @param experienceBonus надбавка за стаж работы
     * @param subordinatesBonus надбавка за подчиненных
     */
    public EmployeeBonus(Employee employee, Double experienceBonus, Double subordinatesBonus) {
        this.employee = employee;
        this.experienceBonus = experienceBonus;
        this.subordinatesBonus = subordinatesBonus;
    }
        
    ///////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Возвращает сотрудника
     */
    public Employee getEmployee() {
        return employee;
    }
    
    /**
     * Устанавливает сотрудника
     * @param employee сотрудник
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    /**
     * Возвращает надбавку за стаж работы
     */
    public Double getExperienceBonus() {
        return experienceBonus;
    }
    
    /**
     * Устанавливает надбавку за стаж работы
     * @param experienceBonus надбавка за стаж работы
     */
    public void setExperienceBonus(Double experienceBonus) {
        this.experienceBonus = experienceBonus;
    }
    
    /**
     * Возвращает надбавку за подчиненных
     */
    public Double getSubordinatesBonus() {
        return subordinatesBonus;
    }

    /**
     * Устанавливает надбавку за подчиненных
     * @param subordinatesBonus надбавка за подчиненных
     */
    public void setSubordinatesBonus(Double subordinatesBonus) {
        this.subordinatesBonus = subordinatesBonus;
    }
    
    /**
     * Возвращает список подчиненных сотрудников
     */
    public List<Employee> getSubordinate() {
        return subordinate;
    }
    
    /**
     * Устанавливает список подчиненных сотрудников
     * @param subordinate список подчиненных сотрудников
     */
    public void setSubordinate(List<Employee> subordinate) {
        this.subordinate = subordinate;
    }
    
    /**
     * Возвращает общую заработную плату, включая набавки
     */
    public Double getTotalSalary() {
        return totalSalary;
    }
    
    /**
     * Устанавливает общую заработную плату, включая надбавки
     * @param totalSalary общая заработная плата
     */
    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }
    
    /**
     * Переопределение метода toString для удобного вывода информации о компонентах заработной платы сотрудника
     */
    @Override
    public String toString(){
        return String.format("EmployeeBonus:\n %s\n experienceBonus: %f\n subordinatesBonus: %f\n TOTAL: %f\n\n", 
                this.getEmployee().toString(), this.getExperienceBonus(), this.getSubordinatesBonus(), this.getTotalSalary());
    }
}
