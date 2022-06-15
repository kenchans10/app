package com.java_backend.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.sound.sampled.Line;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;

import com.java_backend.model.ref_state;
import com.java_backend.model.user;
import com.java_backend.repository.stateRepository;
import com.java_backend.repository.userRepository;
import com.java_backend.services.stateService;
import com.java_backend.util.ConstantValue;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "/api", tags = "All API Controller")
@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired 
	private stateService statesvc;

	@Autowired 
	private stateRepository stateRepo;
	
	@Autowired 
	private userRepository userRepo;
	
	@GetMapping("/")
	public String HelloWorld() {
		return "Hello World";
	}
	
	@ApiOperation(value = "Insert State Csv file to Database")
	@GetMapping("/ref_state/generate")
	public void getTeamCsv() {
		File folder = new File("csv/ref_state.csv");
		statesvc.saveCsvtoMysql(folder.getAbsolutePath());
	}
	
	@ApiOperation(value = "Insert Ref State to Database")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully created"),
			@ApiResponse(code = 500, message = "Created failure") 
	})
	@PostMapping("/ref_state/add")
	public ResponseEntity<?> addStateDesc(@Valid @RequestBody ref_state state) {
		try {
			stateRepo.save(state);
			return ResponseEntity.ok(ConstantValue.CREATE_SUCCESSFULLY);
		} catch (Exception e) {
			return ResponseEntity.ok(ConstantValue.CREATE_FAILURE);
		}
	}
	
	@ApiOperation(value = "View All State")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully retrieved"),
	})
	@GetMapping("/ref_state/viewAll")
	public List<ref_state> getAllState() {
		return stateRepo.findAll();
	}
	
	@ApiOperation(value = "View State By State ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully retrieved"),
	})
	@GetMapping("/ref_state/{id}")
	public ResponseEntity<?> getStateByStateId(@PathVariable(value = "id") String Id) {
		
		ref_state stateData = stateRepo.findByStateId(Id);
		if (stateData != null) {
			return new ResponseEntity<>(stateData, HttpStatus.OK);
		} else {
			return ResponseEntity.ok(Id.toString() + ConstantValue.ID_NOT_FOUND);
		}
	}

	@ApiOperation(value = "Update State Details By State ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully updated"),
	})
	@PutMapping("/ref_state/update/{id}")
	public ResponseEntity<?> updateStateDesc(@PathVariable(value = "id") String Id, @RequestBody ref_state stateDetails) {

		ref_state stateData = stateRepo.findByStateId(Id);
		if (stateData != null) {
			stateData.setState_desc(stateDetails.getState_desc());
			stateRepo.save(stateData);
			return ResponseEntity.ok(ConstantValue.UPDATE_SUCCESSFULLY);
		} else {
			return ResponseEntity.ok(Id.toString() + ConstantValue.ID_NOT_FOUND);
		}
	}

	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully deleted"),
	})
	@DeleteMapping("/ref_state/delete/{id}")
	public ResponseEntity<?> deleteState(@PathVariable(value = "id") Long Id) {
		try {
			stateRepo.deleteById(Id);
			return ResponseEntity.ok(ConstantValue.DELETE_SUCCESSFULLY);
		} catch (Exception e) {
			return ResponseEntity.ok(ConstantValue.DELETE_FAILURE);
		}
	}
	
	/* ================================= User ==================================== */
	
	@PostMapping("/user/add")
	public ResponseEntity<?> addUser(@Valid @RequestBody user user) {
		ref_state stateData = stateRepo.findByStateId(user.getState().getStateId());
		if(stateData != null) {
			user.setState(stateData);
			userRepo.save(user);
			return ResponseEntity.ok(ConstantValue.CREATE_SUCCESSFULLY);
		} else {
			return ResponseEntity.ok(ConstantValue.CREATE_FAILURE);
		}
	}
	
	@ApiOperation(value = "View All User")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully retrieved"),
	})
	@GetMapping("/user/viewAll")
	public List<user> getAllUser() {
		return userRepo.findAll();
	}
	
	@ApiOperation(value = "View User By User ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully retrieved"),
	})
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserByUserId(@PathVariable(value = "id") String Id) {
		
		user userData = userRepo.findByUserId(Id);
		if (userData != null) {
			return new ResponseEntity<>(userData, HttpStatus.OK);
		} else {
			return ResponseEntity.ok(Id.toString() + ConstantValue.ID_NOT_FOUND);
		}
	}

	@ApiOperation(value = "Update User Details By User ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully updated"),
	})
	@PutMapping("/user/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(value = "id") String Id, @RequestBody user userDetails) {

		user userData = userRepo.findByUserId(Id);
		if (userData != null) {
			ref_state stateData = stateRepo.findByStateId(userDetails.getState().getStateId());
			if(stateData != null) {
				userData.setUser_name(userDetails.getUser_name());
				userData.setState(stateData);
				userRepo.save(userData);
				return ResponseEntity.ok(ConstantValue.UPDATE_SUCCESSFULLY);
			} else {
				userData.setUser_name(userDetails.getUser_name());
				userRepo.save(userData);
				return ResponseEntity.ok(ConstantValue.UPDATE_SUCCESSFULLY);
			}
		} else {
			return ResponseEntity.ok(Id.toString() + ConstantValue.ID_NOT_FOUND);
		}
	}

	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully deleted"),
	})
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String Id) {
		try {
			user userData = userRepo.findByUserId(Id);
			userRepo.delete(userData);
			return ResponseEntity.ok(ConstantValue.DELETE_SUCCESSFULLY);
		} catch (Exception e) {
			return ResponseEntity.ok(ConstantValue.DELETE_FAILURE);
		}
	}
}
