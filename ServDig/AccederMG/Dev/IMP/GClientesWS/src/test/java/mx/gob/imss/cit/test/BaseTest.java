package mx.gob.imss.cit.test;

import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/test-application-context.xml" })
public abstract class BaseTest {
	
	protected String buildStringWithRandomCharacters(final int length) {

        final StringBuilder result = new StringBuilder(length);

        final Random random = new Random();

        for (int index = 0; index < length; index++) {
            result.append(Character.toChars(random.nextInt(26) + 65));
        }

        return result.toString();
    }

	protected String buildStringOfSpaces(final int length) {

		return String.format("%1$" + length + 's', "");
	}

}
