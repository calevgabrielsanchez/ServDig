/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author vanderluk
 *
 */
public class PersonaMoralValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Moral.class.equals(arg0);
	}
	
	
	@Override
	public void validate(Object personaMoral, Errors errors) {
		
		Moral moral = (Moral) personaMoral;
		System.out.println("moral: " + moral);
	}

}
