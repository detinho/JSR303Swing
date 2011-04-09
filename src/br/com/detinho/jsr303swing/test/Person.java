package br.com.detinho.jsr303swing.test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;



public class Person {

	@NotEmpty
	private String name;
	
	@Min(value=5)
	@Max(value=99)
	private Integer age;
	
	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
	
/*	public String getName() {
		return name;
	}
	
	public Integer getAge() {
		return age;
	}*/
	
}
