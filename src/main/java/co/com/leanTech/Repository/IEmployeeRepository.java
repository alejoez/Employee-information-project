package co.com.leanTech.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.leanTech.models.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer>{
	
	@Query(value="SELECT EM.ID, EM.ID_PERSON, EM.ID_POSITION, PE.NAME, PE.LAST_NAME, PE.CITY_NAME, PE.CELLPHONE, PE.ADDRESS, EM.SALARY, PO.POSITION_NAME "
			+ "FROM EMPLOYEE EM, PERSON PE, POSITION PO "
			+ "WHERE EM.ID_PERSON=PE.ID AND EM.ID_POSITION=PO.ID "
			+ "AND PO.ID=(CASE WHEN :idPosition = -1 then PO.ID else :idPosition END) "
			+ "AND PE.NAME =(CASE WHEN :personName = '' then PE.NAME else :personName END)", nativeQuery=true)
	List<Employee> findEmployeeByPositionOrPersonName(@Param("idPosition") Integer idPosition, @Param("personName") String personName);
	
	@Query(value="SELECT EM.ID, EM.SALARY, EM.ID_PERSON, EM.ID_POSITION, PE.ADDRESS, PE.CELLPHONE, PE.CITY_NAME, PE.LAST_NAME, PE.NAME,PO.POSITION_NAME "
			+ "FROM EMPLOYEE EM, PERSON PE, POSITION PO "
			+ "WHERE EM.ID_POSITION=PO.ID AND EM.ID_PERSON=PE.ID "
			+ "GROUP BY EM.ID, EM.ID_POSITION", nativeQuery=true)
	List<Employee> findEmployeesByPosition();
	
	@Query(value="SELECT DISTINCT EM.ID, PE.ID, PE.NAME, PE.LAST_NAME, EM.ID_PERSON, EM.ID_POSITION, EM.SALARY "
			+ "FROM EMPLOYEE EM, PERSON PE "
			+ "WHERE EM.ID_PERSON=PE.ID "
			+ "AND EM.ID = :idEmployee AND PE.ID = :idPerson", nativeQuery=true)
	Employee findRelationshipBetweenEmployeeAndPerson(@Param("idEmployee") Integer idEmployee, @Param("idPerson") Integer idPerson);
}