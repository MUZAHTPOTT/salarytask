/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.spring.salarytask.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spring.salarytask.models.Employee;
import ru.spring.salarytask.models.GroupNumber;
import ru.spring.salarytask.services.EmployeeService;
import ru.spring.salarytask.services.SalaryService;

/**
 * Контроллер, обрабатывающий запросы, связанные с представлением данных осотрудниках
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private SalaryService salaryService;
    
    ///////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Обработчик запроса для отображения всех сотрудников
     * @param model модель для передачи данных в представление
     * @return представление для отображения всех сотрудников
     */
    @GetMapping("/allemployees")
    public String goAllEmployees(Model model){
        
        Iterable<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        
        return "employee/allEmployees";
    }
    
    /**
     * Обработчик запроса для отображения данных о сотруднике в форме карточки
     * @param employeeID идентификатор сотрудника
     * @param model модель для передачи данных в представление
     * @return представление для отображения карточки сотрудника
     */
    @GetMapping("/employeecard/{employee_id}")
    public String goEmployeeCard(@PathVariable(value = "employee_id") Long employeeID, Model model){
        
        Employee employee = employeeService.getEmployeeFromOptionalByID(employeeID);
        model.addAttribute("employee", employee);
        
        
        List<Employee> subordinates = new ArrayList<>();
        if(employee.getGroup() == GroupNumber.manager){
            subordinates = salaryService.getSubordinate(employeeID);
        }
        else if(employee.getGroup() == GroupNumber.salesman){
            subordinates = salaryService.getRecursiveAllSubordinate(employeeID);
        }       
        model.addAttribute("subordinates", subordinates);
        
        return "employee/employeeCard";
    }
    
    /**
     * Обработчик запроса для отображения формы для создания сотрудника
     * @param model модель для передачи данных в представление
     * @return представление для отображения формы для создания
     */
    @GetMapping("createemployee")
    public String goCreateEmployee(Model model){
        
        Employee employee = new Employee();
        model.addAttribute("employee", employee); 
        
        Iterable<Employee> bosses = employeeService.getAllManagersAndSalesmen();
        model.addAttribute("employeesBoss", bosses);
        
        model.addAttribute("group", GroupNumber.values());
        
        return "employee/createEmployee";
    }
    
    /**
     * Обработчик POST-запроса для создания сотрудника
     * @param employee сотрудник
     * @param redirectAttributes атрибуты для передачи сообщений при перенаправлении
     * @return перенаправление на страницу информации о сотруднике
     */
    @PostMapping("createemployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, RedirectAttributes redirectAttributes){

        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "Сотрудник успешно создан");
        
        return "redirect:employeecard/" + employee.getId();
    }
    
    /**
     * Обработчик запроса для отображения формы для изменения данных сотрудника
     * @param employeeID идентификатор сотрудника
     * @param model модель для передачи данных в представление
     * @return представление для отображения формы для изменения данных
     */
    @GetMapping("/employeecard/{employee_id}/updatecard")
    public String goUpdateEmployee(@PathVariable(value = "employee_id") Long employeeID, Model model){

        Employee employee = employeeService.getEmployeeFromOptionalByID(employeeID);
        model.addAttribute("employee", employee);
        
        Iterable<Employee> bosses = employeeService.getAllManagersAndSalesmen();
        model.addAttribute("employeesBoss", bosses);
        
        model.addAttribute("group", GroupNumber.values());
        
        return "employee/updateEmployee";
    }
    
    /**
     * Обработчик POST-запроса для обновления информации о сотруднике
     * @param employeeID идентификатор сотрудника
     * @param e обновленная информация о сотруднике
     * @param redirectAttributes атрибуты для передачи сообщений при перенаправлении
     * @return перенаправление на страницу информации о сотруднике
     */
    @PostMapping("/employeecard/{employee_id}/updatecard")
    public String updateEmployee(@PathVariable(value = "employee_id") Long employeeID, @ModelAttribute("employee") Employee e, RedirectAttributes redirectAttributes){
        
        Employee employee = employeeService.getEmployeeFromOptionalByID(employeeID);
        
        employee.setName(e.getName());
        employee.setDate(e.getDate());
        employee.setGroup(e.getGroup());
        employee.setSalary(e.getSalary());
        employee.setBoss(e.getBoss());
        
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "Данные сотрудника успешно обновлены");
        
        return "redirect:/employees/employeecard/" + employee.getId(); 
    }
    
    /**
     * Обработчик POST-запроса для удаления сотрудника
     * @param employeeID идентификатор сотрудника
     * @param redirectAttributes атрибуты для передачи сообщений при перенаправлении
     * @return перенаправление на страницу отображения всех сотрудников
     */
    @PostMapping("/deleteemployee/{employee_id}")
    public String deleteEmployee(@PathVariable("employee_id") Long employeeID, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployeeById(employeeID);
        redirectAttributes.addFlashAttribute("message", "Сотрудник успешно удален");
        
        return "redirect:/employees/allemployees";
    }
}
