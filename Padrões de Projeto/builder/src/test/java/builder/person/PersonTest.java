package builder.person;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PersonTest {

	String firstName = "João";
	String midleName = "Pedro";
	String lastName = "Leite";
	LocalDate birthDate = LocalDate.of(2003, 03, 20);
	String ethnicity = "Caucasian";
	String fatherFullName = "Carlos Leite";
	String motherFullName = "Maria Leite";
	String gender = "Male";
	int heightInCentimeters = 180;
	int weightInKilograms = 75;
	String specialCharacteristics = "Very beautiful";

	private Person partialWithFullName() {
		return new PersonBuilder().firstName(firstName).midleName(midleName).lastName(lastName).build();
	}

	private Person partialWithoutMidleName() {
		return new PersonBuilder().firstName(firstName).lastName(lastName).build();
	}

	@Test
	void shouldInstantiatePersonWithAllFields() {
		Person person = new PersonBuilder()
				.firstName(firstName)
				.midleName(midleName)
				.lastName(lastName)
				.birthDate(birthDate)
				.ethnicity(ethnicity)
				.fatherFullName(fatherFullName)
				.motherFullName(motherFullName)
				.gender(gender)
				.heightInCentimeters(heightInCentimeters)
				.weightInKilograms(weightInKilograms)
				.specialCharacteristics(specialCharacteristics)
				.build();

		assertEquals(firstName, person.getFirstName());
		assertEquals(midleName, person.getMidleName());
		assertEquals(lastName, person.getLastName());
		assertEquals(birthDate, person.getBirthDate());
		assertEquals(ethnicity, person.getEthnicity());
		assertEquals(fatherFullName, person.getFatherFullName());
		assertEquals(motherFullName, person.getMotherFullName());
		assertEquals(gender, person.getGender());
		assertEquals(heightInCentimeters, person.getHeightInCentimeters());
		assertEquals(weightInKilograms, person.getWeightInKilograms());
		assertEquals(specialCharacteristics, person.getSpecialCharacteristics());
	}
	
	@Test
	void shouldCreatePartialObject() {
		Person person = partialWithFullName();

		assertTrue(firstName.equals(person.getFirstName()));
		assertTrue(midleName.equals(person.getMidleName()));
		assertTrue(lastName.equals(person.getLastName()));
	}

	@Test
	void shouldReturnCorrectFullName() {
		Person person = partialWithFullName();

		String fullName = firstName + " " + midleName + " " + lastName;
		assertTrue(fullName.equals(person.getFullName()));
	}

	@Test
	void shouldReturnFullNameWithoutMidleName() {
		Person person = partialWithoutMidleName();

		String fullName = firstName + " " + lastName;

		assertTrue(fullName.equals(person.getFullName()));

	}
}
