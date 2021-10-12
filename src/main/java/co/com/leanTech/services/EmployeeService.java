package co.com.leanTech.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.com.leanTech.Repository.IEmployeeRepository;
import co.com.leanTech.Repository.IPersonRepository;
import co.com.leanTech.Repository.IPositionRepository;
import co.com.leanTech.dto.EmployeeDTO;
import co.com.leanTech.models.Employee;
import co.com.leanTech.models.Person;
import co.com.leanTech.models.Position;

@Component
public class EmployeeService implements IEmployeeService{

	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Autowired
	IPersonRepository personRepository;
	
	@Autowired
	IPositionRepository positionRepository;
	
	@Override
	@Transactional
	public String createEmployee(EmployeeDTO employee)throws Exception{
		// TODO Auto-generated method stub
		String message = null;
		try {

			Person person = new Person();
			Position position = new Position();

			position.setId(employee.getPositionId());
			position.setName(employee.getPositionName());

			person.setName(employee.getPersonName());
			person.setLastName(employee.getPersonLastName());
			person.setAddress(employee.getPersonAddress());
			person.setCellphone(employee.getPersonCellphone());
			person.setCityName(employee.getPersonCityName());
			
			personRepository.save(person);
			
			employeeRepository.save(new Employee(person, position, employee.getEmployeeSalary()));
			
			message="The employee has been successfully created";
		} catch (Exception e) {
			throw e;
		}

		return message;
	}

	@Override
	public String updateEmployee(EmployeeDTO employee)throws Exception{
		// TODO Auto-generated method stub
		String message = null;
		try {
			
			//Validate that the employee and person are related
			Employee employeeRelatedWithPerson = findPersonByEmployee(employee.getEmployeeId(), employee.getPersonId());
			
			//Validate that the data entered are correct, like position, employee and person info
			
			Optional<Position> positionToValidate = positionRepository.findById(employee.getPositionId());
			
			Optional<Employee> employeeToValide = employeeRepository.findById(employee.getEmployeeId());
			
			Optional<Person> personToValidate = personRepository.findById(employee.getPersonId());
			
			if(!employeeToValide.isPresent()) {
				throw new Exception("The employee id doesn't exist");
			}
			
			if(!personToValidate.isPresent()) {
				throw new Exception("The person id doesn't exist");
			}
			
			if( !positionToValidate.isPresent()) {
				throw new Exception("The position id doesn't exist");
			}
			
			if(employeeRelatedWithPerson==null) {
				throw new Exception("Doesn't exist the relation between Employee and Person Info");
			}
			
			Position position = positionToValidate.get();
			Person person = personToValidate.get();
			Employee employeeE = employeeToValide.get();
			
			position.setId(employee.getPositionId());

			person.setName(employee.getPersonName());
			person.setLastName(employee.getPersonLastName());
			person.setAddress(employee.getPersonAddress());
			person.setCellphone(employee.getPersonCellphone());
			person.setCityName(employee.getPersonCityName());
			
			personRepository.save(person);
			
			employeeE.setPerson(person);
			employeeE.setPosition(position);
			employeeE.setSalary(employee.getEmployeeSalary());
			
			Employee employeeUpdated = employeeRepository.save(employeeE);
			
			message="The employee " + employeeUpdated.getId().toString() + " has been successfully updated";
		}catch(Exception e) {
			throw e;
		}
		
		return message;
	}

	@Override
	@Transactional
	public String deleteEmployee(int idEmployee){
		// TODO Auto-generated method stub
		String message = null;
		try {

			Optional<Employee> employee = this.employeeRepository.findById(idEmployee);

			if (employee.isPresent()) {
				employeeRepository.deleteById(idEmployee);
				personRepository.deleteById(employee.get().getPerson().getId());
				
				message = "The employee " + idEmployee + " has been successfully removed";
				
			} else {
				message = "The employee "+idEmployee+" doesn't exists in data base";
			}
			
		} catch (Exception e) {
			throw e;
		}

		return message;
	}


	@Override
	public List<Employee> findEmployeeByPositionOrPersonName(Integer idPosition, String personName) {
		// TODO Auto-generated method stub
		try {
			
			return employeeRepository.findEmployeeByPositionOrPersonName(idPosition, personName);
			
		}catch(Exception e) {
			throw e;
		}
	}

	@Override
	public List<Employee> findEmployeesByPosition() {
		// TODO Auto-generated method stub
		return employeeRepository.findEmployeesByPosition();
	}

	@Override
	public Employee findPersonByEmployee(Integer idEmployee, Integer idPerson) {
		// TODO Auto-generated method stub
		return employeeRepository.findRelationshipBetweenEmployeeAndPerson(idEmployee, idPerson);
	}
}
