package com.java_backend.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_backend.model.ref_state;
import com.java_backend.repository.stateRepository;

@Service
public class stateService {
	
	@Autowired
	private stateRepository stateRepo;
	
	public void saveCsvtoMysql(String path) {
		String line;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if(!data[0].contains("state_id")) {
					ref_state state = new ref_state();
					state.setStateId(data[0].replaceAll("^\"|\"$", ""));
					state.setState_desc(data[1].replaceAll("^\"|\"$", ""));
					stateRepo.save(state);
				}
				
			}
			
			br.close();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
