package builder.person;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonBuilderTest {
	
	String firstName = "João";
	String midleName = "Pedro";
	String lastName = "Leite";
	
	private Person partialWithFullName(){		
		return new PersonBuilder()
				.firstName(firstName)
				.midleName(midleName)
				.lastName(lastName)
				.build();
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
		
		String fullName = firstName + " " + lastName;
		
		assertTrue(fullName.equals("João Leite"));
		
	}
}
