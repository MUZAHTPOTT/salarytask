/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spring.salarytask.models.Employee;

/**
 * Репозиторий для управления объектами Employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    /**
     * Найти всех сотрудников
     */
    @Query("SELECT e FROM Employee e")
    Iterable<Employee> findAllEmployees();
    
    /**
     * Найти всех сотрудников с должностью 'manager' или 'salesman'
     */
    @Query("SELECT e FROM Employee e WHERE e.group IN ('salesman', 'manager')")
    Iterable<Employee> findAllManagersAndSalesmen();
    
    /**
     * Найти подчиненных для указанного начальника
     * @param bossId ID начальника
     */
    @Query("SELECT e FROM Employee e WHERE e.boss.id = ?1")
    List<Employee> findSubordinateByEmployee(Long bossID);
    
    /**
     * Найти подчиненных без указанного начальника
     */
    @Query("SELECT e FROM Employee e WHERE e.boss IS NULL")
    List<Employee> findSubordinateWithoutBoss();
}