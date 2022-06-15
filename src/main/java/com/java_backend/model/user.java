package com.java_backend.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class user implements Serializable  {

	@Id
	@GeneratedValue
	Long Id;
	
	@NotNull(message = "User ID is required")
	@ApiModelProperty(notes = "Unique ID for User", example = "910802145803", required = true)
	@Column(name="user_id")
	String userId;
	
	@NotNull(message = "User Name is required")
	@ApiModelProperty(notes = "Description of Name", example = "MUHAMMAD ALIF BIN ZULKIFLI", required = true) 
	String user_name;
	
	@NotNull(message = "State ID is required")
	@ApiModelProperty(notes = "Unique ID for State", example = "01", required = true) 
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private ref_state state;
	
	@CreationTimestamp
    @Column(updatable = false)
    Timestamp dateCreated;
	
    @UpdateTimestamp
    Timestamp lastModified;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public ref_state getState() {
		return state;
	}

	public void setState(ref_state state) {
		this.state = state;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

}