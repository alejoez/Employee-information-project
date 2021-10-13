package co.com.leanTech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.leanTech.Repository.IPositionRepository;
import co.com.leanTech.models.Position;

@Service
public class PositionService implements IPositionService{

	@Autowired
	IPositionRepository positionRepository;
	
	@Override
	public Position findByName(String name) {
		// TODO Auto-generated method stub
		return positionRepository.findByName(name);
	}
}
