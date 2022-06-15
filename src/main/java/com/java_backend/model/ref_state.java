package com.java_backend.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="ref_state")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class ref_state implements Serializable {

	@Id
	@NotNull(message = "State ID is required")
	@ApiModelProperty(notes = "Unique ID for State", example = "01", required = true) 
	@Column(name="state_id", updatable = false)
	String stateId;
	
	@NotNull(message = "State Name is required")
	@ApiModelProperty(notes = "Description of State", example = "JOHOR", required = true) 
	String state_desc;
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "state")
    private user user;
    
    public ref_state() {}
    
    public ref_state(String state_desc) {
        this.state_desc = state_desc;
    }
	
	@CreationTimestamp
    @Column(updatable = false)
    Timestamp dateCreated;
	
    @UpdateTimestamp
    Timestamp lastModified;

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getState_desc() {
		return state_desc;
	}

	public void setState_desc(String state_desc) {
		this.state_desc = state_desc;
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
