package builder.person;

import java.time.LocalDate;

public final class PersonBuilder {
	private String    firstName              = "";
	private String    midleName              = "";
	private String    lastName               = "";
	private String    gender                 = "";
	private String    motherFullName         = ""; 
	private String    fatherFullName         = "";
	private LocalDate birthDate              = LocalDate.MIN;
	private float     heightInCentimeters    = Float.NaN;
	private float     weightInKilograms      = Float.NaN;
	private String    ethnicity              = "";
	private String    specialCharacteristics = "";
	private String	  fullName				 = "";
	
	
	public final Person build() {
		Person person = new Person();
		
		person.setFirstName(firstName);
		person.setMidleName(midleName);
		person.setLastName(lastName);
		person.setGender(gender);
		person.setMotherFullName(motherFullName);
		person.setFatherFullName(fatherFullName);
		person.setBirthDate(birthDate);
		person.setHeightInCentimeters(heightInCentimeters);
		person.setWeightInKilograms(weightInKilograms);
		person.setEthnicity(ethnicity);
		person.setSpecialCharacteristics(specialCharacteristics);
		person.setFullName(fullName);
		
		return person;
	}
	
	public PersonBuilder firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public PersonBuilder midleName(String midleName) {
		this.midleName = midleName;
		return this;

	}
	public PersonBuilder lastName(String lastName) {
		this.lastName = lastName;
		return this;

	}
	public PersonBuilder gender(String gender) {
		this.gender = gender;
		return this;

	}
	public PersonBuilder motherFullName(String motherFullName) {
		this.motherFullName = motherFullName;
		return this;

	}
	public PersonBuilder fatherFullName(String fatherFullName) {
		this.fatherFullName = fatherFullName;
		return this;

	}
	public PersonBuilder birthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
		return this;

	}
	public PersonBuilder heightInCentimeters(float heightInCentimeters) {
		this.heightInCentimeters = heightInCentimeters;
		return this;

	}
	public PersonBuilder weightInKilograms(float weightInKilograms) {
		this.weightInKilograms = weightInKilograms;
		return this;

	}
	public PersonBuilder ethnicity(String ethnicity) {
		this.ethnicity = ethnicity;
		return this;

	}
	public PersonBuilder specialCharacteristics(String specialCharacteristics) {
		this.specialCharacteristics = specialCharacteristics;
		return this;

	}
	
	
	
	
}
