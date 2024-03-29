package com.akshatha.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.akshatha.entity.Employee;
import com.akshatha.service.EmployeeService;

public class EmployeeController {
private static final Logger logger = Logger.getAnonymousLogger();
    
    public EmployeeController() {
        System.out.println("EmployeeController()");
    }

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("createEmployee")
    public ModelAndView createEmployee(@ModelAttribute Employee employee) {
        logger.info("Creating Employee. Data: "+employee);
        return new ModelAndView("employeeForm");
    }
    
    @RequestMapping("editEmployee")
    public ModelAndView editEmployee(@RequestParam long id, @ModelAttribute Employee employee) {
        logger.info("Updating the Employee for the Id "+id);
        employee = employeeService.getEmployee(id);
        return new ModelAndView("employeeForm", "employeeObject", employee);
    }
    
    @RequestMapping("saveEmployee")
    public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
        logger.info("Saving the Employee. Data : "+employee);
        if(employee.getId() == 0){ // if employee id is 0 then creating the employee other updating the employee
            employeeService.createEmployee(employee);
        } else {
            employeeService.updateEmployee(employee);
        }
        return new ModelAndView("redirect:getAllEmployees");
    }
    
    @RequestMapping("deleteEmployee")
    public ModelAndView deleteEmployee(@RequestParam long id) {
        logger.info("Deleting the Employee. Id : "+id);
        employeeService.deleteEmployee(id);
        return new ModelAndView("redirect:getAllEmployees");
    }
    
    @RequestMapping(value = {"getAllEmployees", "/"})
    public ModelAndView getAllEmployees() {
        logger.info("Getting the all Employees.");
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ModelAndView("employeeList", "employeeList", employeeList);
    }
    
    @RequestMapping("searchEmployee")
    public ModelAndView searchEmployee(@RequestParam("searchName") String searchName) {  
        logger.info("Searching the Employee. Employee Names: "+searchName);
        List<Employee> employeeList = employeeService.getAllEmployees(searchName);
        return new ModelAndView("employeeList", "employeeList", employeeList);      
    }
}

