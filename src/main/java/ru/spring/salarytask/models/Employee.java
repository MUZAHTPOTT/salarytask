/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.models;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;


/**
 * Сущность, представляющая сотрудника
 */
@Entity
@Table(name = "employee")
@TypeDef(name = "group_type", typeClass = PostgreSQLEnumType.class)
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "date_of_employ")
    private LocalDate date;
    
    @Column(name = "salary")
    private Integer salary;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_group")
    @Type(type = "group_type")
    private GroupNumber group;
    
    @ManyToOne
    @JoinColumn(name = "boss_id")
    private Employee boss;
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Конструктор по умолчанию
     */
    public Employee(){
        
    }
    
    /**
     * Полный конструктор для сотрудника с начальником
     * @param id идентификатор
     * @param name имя
     * @param date дата начала работы
     * @param salary базовая заработная плата
     * @param group должность
     * @param boss начальник
     */
    public Employee(Long id, String name, LocalDate date, Integer salary, GroupNumber group, Employee boss) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.salary = salary;
        this.group = group;
        this.boss = boss;
    }
    
    /**
     * Конструктор для сотрудника без начальника
     * @param id идентификатор
     * @param name имя
     * @param date дата начала работы
     * @param salary базовая заработная плата
     * @param group должность
     */
    public Employee(Long id, String name, LocalDate date, Integer salary, GroupNumber group) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.salary = salary;
        this.group = group;
    }
   
    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Возвращает идентификатор сотрудника
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор сотрудника
     * @param id идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает имя сотрудника
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя сотрудника
     * @param name имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает дату начала работы
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Устанавливает дату начала работы
     * @param date начала работы
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Возвращает базовуб заработную плату
     */
    public Integer getSalary() {
        return salary;
    }

    /**
     * Устанавливает базовую заработную плату
     * @param salary базовая заработная плата
     */
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    /**
     * Возвращает должность сотрудника
     */
    public GroupNumber getGroup() {
        return group;
    }

    /**
     * Устанавливает должность сотрудника
     * @param group должность
     */
    public void setGroup(GroupNumber group) {
        this.group = group;
    }

    /**
     * Возвращает начальника сотрудника
     */
    public Employee getBoss() {
        return boss;
    }

    /**
     * Устанавливает начальника сотрудника
     * @param boss начальник
     */
    public void setBoss(Employee boss) {
        this.boss = boss;
    }
    
    /**
     * Переопределение метода toString для удобного вывода информации о сотруднике
     */
    @Override
    public String toString(){
        String boss_id = (this.getBoss() != null) ? String.valueOf(this.getBoss().getId()) : "None";
        return String.format("Employee:\n id: %d\n name: %s\n date: %s\n salary: %d\n group: %s\n boss: %s\n\n", 
                this.getId(), this.getName(), this.getDate(), this.getSalary(), this.getGroup(), boss_id);
    }
    
}
