package co.com.leanTech.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import co.com.leanTech.models.Position;

public interface IPositionRepository extends JpaRepository<Position, Integer>{

	@Transactional(readOnly = true)
	public Position findByName(String name);
	
}