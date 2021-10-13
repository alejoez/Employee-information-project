package co.com.leanTech.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idPerson")
	private Person person;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idPosition")
	private Position position;
	
	@Column(name="salary")
	private String salary;
	
	public Employee(Person person, Position position, String salary) {
		this.person=person;
		this.position=position;
		this.salary=salary;
	}
}
