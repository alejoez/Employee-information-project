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
import co.com.leanTech.dto.PersonDTO;
import co.com.leanTech.jsonFormater.ReturnEmployeesInformation;
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
	public SubmitResult toListEmployeesByPosition()throws Exception{
		
		SubmitResult result = new SubmitResult();
		
		try {
			
			List<Employee> foo = employeeService.findEmployeesByPosition();
			
			List<ReturnEmployeesInformation> resultList = new ArrayList<ReturnEmployeesInformation>();
			
			HashMap<Integer, SubmitResult> submitList = new HashMap<Integer, SubmitResult>();
			
			SubmitResult submitResult = new SubmitResult();
			
			List<ReturnEmployeesInformation> listaResultado=new ArrayList<>();
			
			HashMap<Integer, String> positionMap = new HashMap<Integer, String>();
			
			for (Employee employee : foo) {
				positionMap.put(employee.getPosition().getId(), employee.getPosition().getName());
				
				submitResult = new SubmitResult();
				ReturnEmployeesInformation rre = new ReturnEmployeesInformation();
				PersonDTO personDTO = new PersonDTO();
				
				submitResult.setId(employee.getPosition().getId());
				submitResult.setName(employee.getPosition().getName());
				
				rre.setId(employee.getId());
				rre.setSalary(employee.getSalary());
				
				personDTO.setName(employee.getPerson().getName());
				personDTO.setLastNam2(employee.getPerson().getLastName());
				personDTO.setAddress(employee.getPerson().getAddress());
				personDTO.setCellphone(employee.getPerson().getCellphone());
				personDTO.setCityName(employee.getPerson().getCityName());
				
				rre.setPerson(personDTO);
				
				rre.setIdPosition(employee.getPosition().getId());
				
				listaResultado.add(rre);
				
				resultList.add(rre);
				
				submitResult.setEmployee(resultList);
				
				submitList.put(employee.getPosition().getId(), submitResult);
			}
			
			List<SubmitResult> list = new ArrayList<SubmitResult>(submitList.values());
			
			
			
			
			result.submit=list;
			
		}catch(Exception e) {
			log.info(e.getMessage());
		}
		
		return result;
	}
	
	
}
