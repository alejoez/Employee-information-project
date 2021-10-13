package co.com.leanTech;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

import co.com.leanTech.Repository.IEmployeeRepository;
import co.com.leanTech.Repository.IPersonRepository;
import co.com.leanTech.Repository.IPositionRepository;
import co.com.leanTech.dto.EmployeeDTO;
import co.com.leanTech.models.Employee;
import co.com.leanTech.models.Person;
import co.com.leanTech.models.Position;
import co.com.leanTech.services.IPositionService;

@SpringBootApplication
public class EmployeeInformationProjectApplication implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	IPositionRepository positionRepository;
	
	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Autowired
	IPersonRepository personRepository;
	
	@Autowired
	IPositionService positionService;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeInformationProjectApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		
		//Default data
		
		//Add information related to employees in the employee list to save.
		List<EmployeeDTO> listEmployeeDTO = addEmployeesToList();
		
		//Save two default position for the employees
		positionRepository.save(new Position("Dev"));
		positionRepository.save(new Position("Qa"));
		
		Person person=null;
		Position positionFound=null;
		for(int i=0;i<listEmployeeDTO.size();i++) {
			
			person = new Person();
			positionFound = new Position();
			
			positionFound = positionService.findByName(listEmployeeDTO.get(i).getPositionName());
			
			if(positionFound!=null) {

				person.setName(listEmployeeDTO.get(i).getPersonName());
				person.setLastName(listEmployeeDTO.get(i).getPersonLastName());
				person.setAddress(listEmployeeDTO.get(i).getPersonAddress());
				person.setCellphone(listEmployeeDTO.get(i).getPersonCellphone());
				person.setCityName(listEmployeeDTO.get(i).getPersonCityName());
				
				personRepository.save(person);
				employeeRepository.save( new Employee(person, positionFound, listEmployeeDTO.get(i).getEmployeeSalary()));
			}
		}
	}
	
	public List<EmployeeDTO> addEmployeesToList(){
		
		List<EmployeeDTO> listEmployeeDTO = new ArrayList<EmployeeDTO>();
		
		EmployeeDTO employee1 = new EmployeeDTO(null, "2000", null, "Camilo", "Ruiz", "cra", "124", "Medellin",
				null, "Dev");
		listEmployeeDTO.add(employee1);
		
		EmployeeDTO employee2 = new EmployeeDTO(null, "1000", null, "Andres", "Escobar", "cra", "1244", "Envigado",
				null, "Dev");
		listEmployeeDTO.add(employee2);
		
		EmployeeDTO employee3 = new EmployeeDTO(null, "1500", null, "Luis", "Perez", "cra", "124", "Medellin",
				null, "Qa");
		listEmployeeDTO.add(employee3);
		
		EmployeeDTO employee4 = new EmployeeDTO(null, "1000", null, "Pedro", "Castro", "cra", "1244", "Envigado",
				null, "Qa");
		listEmployeeDTO.add(employee4);
		
		return listEmployeeDTO;
	}
}
