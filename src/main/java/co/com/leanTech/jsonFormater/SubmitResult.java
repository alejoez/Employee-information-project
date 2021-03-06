package co.com.leanTech.jsonFormater;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SubmitResult {

	@JsonProperty
	public Integer id;
	
	@JsonProperty
	public String name;
	
	@JsonProperty
	public List<ReturnEmployeesInformation> employee;
	
	
	public SubmitResult() {
		this.employee=new ArrayList<ReturnEmployeesInformation>();
	}
}
