package co.com.leanTech.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.leanTech.dto.EmployeeDTO;
import co.com.leanTech.models.Employee;

@Service
public interface IEmployeeService {
	
	public String createEmployee(EmployeeDTO employee)throws Exception;
	
	public String updateEmployee(EmployeeDTO employee)throws Exception;
	
	public String deleteEmployee(int idEmployee) throws Exception; 
	
	public List<Employee> findEmployeeByPositionOrPersonName(Integer idPosition, String personName);
	
	public List<Employee> findEmployeesByPosition();
	
	public Employee findPersonByEmployee(Integer idEmployee, Integer idPerson);
}
