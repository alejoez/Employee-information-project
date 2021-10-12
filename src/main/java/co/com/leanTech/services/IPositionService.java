package co.com.leanTech.services;

import org.springframework.stereotype.Service;

import co.com.leanTech.models.Position;

@Service
public interface IPositionService {
	
	public Position findByName(String name);
}
