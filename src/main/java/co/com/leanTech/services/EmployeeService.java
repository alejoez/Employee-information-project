package co.com.leanTech.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.leanTech.Repository.IEmployeeRepository;
import co.com.leanTech.Repository.IPersonRepository;
import co.com.leanTech.Repository.IPositionRepository;
import co.com.leanTech.dto.EmployeeDTO;
import co.com.leanTech.dto.PersonDTO;
import co.com.leanTech.jsonFormater.ReturnEmployeesInformation;
import co.com.leanTech.jsonFormater.SubmitResult;
import co.com.leanTech.models.Employee;
import co.com.leanTech.models.Person;
import co.com.leanTech.models.Position;

@Service
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
			
			//Validate that the employee exists
			Optional<Employee> employeeToValide = employeeRepository.findById(employee.getEmployeeId());
			
			//Validate that the person exists
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
			
			//Get and set the information related to position, person and employee,
			//so doesn't save a new register, only overwrite this register and update it.
			
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

			//Search employee register to find the person id related to him.
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

	@Override
	public HashMap<Integer, SubmitResult> toListEmployeesByPosition() throws Exception {
		// TODO Auto-generated method stub
		HashMap<Integer, SubmitResult> submitMap2 = new HashMap<Integer, SubmitResult>();
		try {
			
			List<ReturnEmployeesInformation> returnList = new ArrayList<ReturnEmployeesInformation>();
			HashMap<Integer, SubmitResult> submitMap = new HashMap<Integer, SubmitResult>();
			SubmitResult submitResult = new SubmitResult();
			ReturnEmployeesInformation returnEmployeesInformation = new ReturnEmployeesInformation();
			
			//Search all employees to build the new json format
			List<Employee> employeesList = findEmployeesByPosition();
			
			//this implementation serves to give an structure to json return.
			//SubmitResult is use to controller the form of structured the json.
			//That class has a List called "ReturnEmployeesInformation" that allows show in cascade the information, but It depends on how you have structured it.
			
			for (Employee employee : employeesList) {
				submitResult = new SubmitResult();
				returnEmployeesInformation = new ReturnEmployeesInformation();
				PersonDTO personDTO = new PersonDTO();

				submitResult.setId(employee.getPosition().getId());
				submitResult.setName(employee.getPosition().getName());

				returnEmployeesInformation.setId(employee.getId());
				returnEmployeesInformation.setSalary(employee.getSalary());

				personDTO.setName(employee.getPerson().getName());
				personDTO.setLastNam2(employee.getPerson().getLastName());
				personDTO.setAddress(employee.getPerson().getAddress());
				personDTO.setCellphone(employee.getPerson().getCellphone());
				personDTO.setCityName(employee.getPerson().getCityName());

				returnEmployeesInformation.setPerson(personDTO);
				returnEmployeesInformation.setIdPosition(employee.getPosition().getId());

				returnList.add(returnEmployeesInformation);

				submitResult.setEmployee(returnList);

				submitMap.put(employee.getPosition().getId(), submitResult);
			}
			
			
			//In this part, go through submitMap that contains a key like positionId and the format employees list like a value.
			//each employee has a idPosition and can be diferents positions, but are in the same list.
			//we need to compare each key of the map with each idPosition of the employee list and to add the new values in a new map.
			
			for (Map.Entry<Integer, SubmitResult> entry : submitMap.entrySet()) {
				
				submitResult = new SubmitResult();
				
				submitResult.setId(entry.getKey());
				submitResult.setName(entry.getValue().getName());
				
				returnEmployeesInformation = new ReturnEmployeesInformation();
				returnList = new ArrayList<ReturnEmployeesInformation>();
				
				for(int i=0;i<entry.getValue().getEmployee().size();i++) {
					
					if(entry.getKey()==entry.getValue().getEmployee().get(i).getIdPosition()) {
						
						returnList.add(entry.getValue().getEmployee().get(i));
						
						submitResult.setEmployee(returnList);
					}
				}
				
				submitMap2.put(entry.getKey(), submitResult);
			}
			
		}catch(Exception e){
			throw e;
		}

		return submitMap2;
	}
}
