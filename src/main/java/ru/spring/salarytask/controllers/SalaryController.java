/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spring.salarytask.models.Employee;
import ru.spring.salarytask.models.EmployeeBonus;
import ru.spring.salarytask.services.EmployeeService;
import ru.spring.salarytask.services.SalaryService;

/**
 * Контроллер, обрабатывающий запросы, связанные с заработной платой
 */
@Controller
@RequestMapping("/salary")
public class SalaryController {
    
    @Autowired
    private SalaryService salaryService;
    
    @Autowired
    private EmployeeService employeeService;
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Обработчик запроса для перехода к форме для заполнения параметров расчета заработной платы сотрудника
     * @param employeeID идентификатор сотрудника
     * @param model модель для передачи данных в представление
     * @return представление для отображения формы для задачи параметров расчета заработной платы
     */
    @GetMapping("/{employee_id}")
    public String goEmployeeSalary(@PathVariable(value = "employee_id") Long employeeID, Model model){
        
        model.addAttribute("type", 0);
        model.addAttribute("employee_id", employeeID);
        
        return "salary/salaryPre";
    }
    
    /**
     * Обработчик POST-запроса для расчета заработной платы сотрудника
     * @param employeeID идентификатор сотрудника
     * @param date заданая дата
     * @param model модель для передачи данных в представление
     * @return представление для отображения результатов расчета заработной платы
     */
    @PostMapping("/{employee_id}")
    public String calculateEmployeeSalary(@PathVariable(value = "employee_id") Long employeeID, @RequestParam("need_date") LocalDate date, Model model){
        
        List<EmployeeBonus> employeeBonusList = new ArrayList<>();        
        employeeBonusList = salaryService.getEmployeeBonusListAfterSalaryCalculation(employeeService.getEmployeeFromOptionalByID(employeeID), date);
        
        model.addAttribute("list", employeeBonusList);
        model.addAttribute("type", 0);
                   
        return "salary/salaryResult";     
    }
    
    /**
     * Обработчик запроса для перехода к форме для заполнения параметров расчета затрат компании на заработные платы сотрудников
     * @param model модель для передачи данных в представление
     * @return представление для отображения формы для задачи параметров расчета затрат компании на заработные платы сотрудников
     */
    @GetMapping("/companysalary")
    public String goCompanySalary(Model model){

        model.addAttribute("type", 1);
        
        return "salary/salaryPre";
    }
    
    /**
     * Обработчик POST-запроса для расчета затрат компании на заработные платы сотрудников
     * @param date заданая дата
     * @param model модель для передачи данных в представление
     * @return представление для отображения результатов расчета затрат компании на заработные платы сотрудников
     */
    @PostMapping("/companysalary")
    public String calculateCompanySalary(@RequestParam("need_date") LocalDate date, Model model){
        
        List< List<EmployeeBonus> > allEmployeeBonusLists = new ArrayList<>();
        
        for (Employee employee : salaryService.getSubordinateWithoutBoss()){
            
            List<EmployeeBonus> employeeBonusList = new ArrayList<>();        
            employeeBonusList = salaryService.getEmployeeBonusListAfterSalaryCalculation(employee, date);
            
            allEmployeeBonusLists.add(employeeBonusList);
        }
        
        model.addAttribute("all_list", allEmployeeBonusLists);
        
        Double totalCompanySalary = 0.0;
        for (List<EmployeeBonus> list : allEmployeeBonusLists){
            for(EmployeeBonus employeeBonus : list){
                totalCompanySalary += employeeBonus.getTotalSalary();
            }
        }
        model.addAttribute("global_total", totalCompanySalary);
        model.addAttribute("type", 1);
        
        return "salary/salaryResult";
    }
}
