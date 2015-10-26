package com.ramonli.sandbox.spring.customschema;

public class Department {
	private String name;
	private People people;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

}
