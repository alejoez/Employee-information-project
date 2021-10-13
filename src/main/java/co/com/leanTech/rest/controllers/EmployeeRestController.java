package co.com.leanTech.rest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.leanTech.dto.EmployeeDTO;
import co.com.leanTech.jsonFormater.SubmitResult;
import co.com.leanTech.models.Employee;
import co.com.leanTech.models.Position;
import co.com.leanTech.services.IEmployeeService;
import co.com.leanTech.services.IPositionService;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
	private static final Logger log = LoggerFactory.getLogger(EmployeeRestController.class);
	 
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private IPositionService positionService;
	
	private ModelMapper modelMapper;
	
	public EmployeeRestController(ModelMapper mapper) {
		this.modelMapper=mapper;
	}
	
	@PostMapping(value="/createEmployee")
	public String createEmployee(@RequestBody EmployeeDTO employee)throws Exception{
		String message = null;
		try {
			
			Position positionByName = positionService.findByName(employee.getPositionName());
			
			if(positionByName!=null) {
				employee.setPositionId(positionByName.getId());
			}else {
				throw new Exception("The employee cannot be create because position doesn't exists");
			}
			
			message = employeeService.createEmployee(employee);
			
		}catch(Exception e) {
			if (message == null) {
				message = "There was an error trying to create the employee";
			}else {
				message=e.getMessage();
			}
		}
		return message;
	}
	
	@PutMapping(value="/updateEmployee")
	public String updateEmployee(@RequestBody EmployeeDTO employee)throws Exception{
		String message=null;
		try {
			
			message = employeeService.updateEmployee(employee);
			
		}catch(Exception e) {
			message=e.getMessage();
		}
		return message;
	}
	
	@DeleteMapping(value = "/deleteEmployee")
	public String deleteEmployee(@RequestParam(value="idEmployee") Integer idEmployee) {
		String message = null;
		try {

			message = employeeService.deleteEmployee(idEmployee);

		} catch (Exception e) {
			log.info("There was an error trying to delete the employee");
		}

		return message;
	}
	
	@GetMapping(value="/toListEmployees")
	public List<EmployeeDTO> toListEmployees(@RequestParam(value="idPosition", required=false) Integer idPosition,
			@RequestParam(value="employeeName", required=false) String employeeName)throws Exception{
		List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
		
		try {
			
			//if idPosition or employeeName are null, we need to change to data default for generate the consult correctly.
			//if idPosition and employeeName are null we get all employee list.
			if(idPosition==null) {
				idPosition=-1;
			}
			
			if(employeeName==null) {
				employeeName="";
			}
			
			List<Employee> employeeList=employeeService.findEmployeeByPositionOrPersonName(idPosition, employeeName);
			
			EmployeeDTO employeeDTO = new EmployeeDTO();
			
			for(Employee employee: employeeList) {
				employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
				employeeDTOList.add(employeeDTO);
			}
			
		}catch(Exception e) {
			log.info(e.getMessage());
		}
		
		return employeeDTOList;
	}
	
	@GetMapping(value="/toListEmployeesByPosition")
	public HashMap<Integer, SubmitResult> toListEmployeesByPosition()throws Exception{
		
		HashMap<Integer, SubmitResult> submitMap = new HashMap<>();
		
		try {

			submitMap = employeeService.toListEmployeesByPosition();
			
		}catch(Exception e) {
			log.info(e.getMessage());
		}
		
		return submitMap;
	}
	
	
}
