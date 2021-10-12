package co.com.leanTech.dto;

import lombok.Data;

@Data
public class EmployeeDTO {

	//Employee Data
	private Integer employeeId;
	private String employeeSalary;
	
	//Person data
	private Integer personId;
	private String personName;
	private String personLastName;
	private String personAddress;
	private String personCellphone;
	private String personCityName;
	
	//Position data
	private Integer positionId;
	private String positionName;
	
	public EmployeeDTO() {
	}
	
	public EmployeeDTO(Integer employeeId, String employeeSalary, Integer personId, String personName,
			String personLastName, String personAddress, String personCellphone, String personCityName,
			Integer positionId, String positionName) {
		super();
		this.employeeId = employeeId;
		this.employeeSalary = employeeSalary;
		this.personId = personId;
		this.personName = personName;
		this.personLastName = personLastName;
		this.personAddress = personAddress;
		this.personCellphone = personCellphone;
		this.personCityName = personCityName;
		this.positionId = positionId;
		this.positionName = positionName;
	}
}


