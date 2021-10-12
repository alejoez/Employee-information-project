package co.com.leanTech.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="positionName")
	private String name;
	
	/*@OneToMany(targetEntity=Employee.class, cascade=CascadeType.ALL)
	@JoinColumn(name="empl_pos", referencedColumnName = "id")
	private List<Employee> employee;*/
	 
	public Position(String name) {
		this.name=name;
	}
}
