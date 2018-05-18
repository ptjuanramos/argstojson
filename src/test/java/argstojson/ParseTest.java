package argstojson;

import org.junit.Test;

/**
 * Test class that by using JUnit testing framework, help us to understand how 
 * the conversion works when building the project with maven
 * @author Juan Ramos
 */
public class ParseTest {
	@Test
	public void testConversion() {
		//example of args
		String[] args = {"--vaderisdead=true", "--home.rooms=5", "--home.bathrooms=6"};
		System.out.println("*** Output JSON Object ***");
		System.out.println(ArgsToJson.getJsonObject(args).toString());
	}
}
