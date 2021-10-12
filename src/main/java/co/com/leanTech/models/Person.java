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
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="cellphone")
	private String cellphone;
	
	@Column(name="cityName")
	private String cityName;
	
	public Person(String name, String lastName, String address, String cellphone, String cityName) {
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.cellphone = cellphone;
		this.cityName = cityName;
	}
}
