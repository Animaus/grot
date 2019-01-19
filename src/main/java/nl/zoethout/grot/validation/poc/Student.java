package nl.zoethout.grot.validation.poc;

import org.hibernate.validator.constraints.Range;

public class Student {

	@Range(min = 1, max = 150)
	private Integer age;
	private String name;
	private Integer id;

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public String getClassName() {
		return this.getClass().getCanonicalName();
	}
}