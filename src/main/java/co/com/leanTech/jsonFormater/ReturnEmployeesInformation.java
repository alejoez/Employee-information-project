package co.com.leanTech.jsonFormater;

import co.com.leanTech.dto.PersonDTO;
import lombok.Data;

@Data
public class ReturnEmployeesInformation {
	
	private Integer id; //idEmployee
	private String salary;
	private PersonDTO person;
	
	private Integer idPosition;
}
