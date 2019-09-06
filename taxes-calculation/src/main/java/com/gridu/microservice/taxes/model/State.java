package com.gridu.microservice.taxes.model;

import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gridu.microservice.rest.validation.ValidationErrorType;
import com.gridu.microservice.taxes.validation.annotation.ValidStateCode;
import com.gridu.microservice.taxes.validation.group.StateCodeValidationGroup;

@Table(name="state")
@Entity
public class State {

	@NotNull(message = "id."+ValidationErrorType.MISSING)
	@Id
	private Long id;
	
	@ValidStateCode(message = ValidationErrorType.NOT_FOUND, groups = {StateCodeValidationGroup.class})
	private String code;
	
	private String name;

	public State() {
	}

	public State(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public State(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		State that = (State) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		return name != null ? name.equals(that.name) : that.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (code != null ? code.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "State{" +
			"id=" + id +
			", code='" + code + '\'' +
			", name='" + name + '\'' +
			'}';
	}
}
