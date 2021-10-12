package co.com.leanTech.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.leanTech.models.Person;

public interface IPersonRepository extends JpaRepository<Person, Integer>{

}
