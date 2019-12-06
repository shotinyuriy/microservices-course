package com.gridu.kafka.model;

import java.io.Serializable;

public class User implements Serializable {

	private Long id;
	private String name;
	private String age;

	public User() {}

	public User(Long id, String name, String age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User:{id:"+id+", name:"+name+", age:"+age+"}";
	}
}
