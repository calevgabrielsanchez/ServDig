/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.controller;

import mx.gob.imss.ctirss.clasificador.service.DivisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author lucio
 *
 */
@Controller
public class WelcomeController {
	
	
	
	private DivisionService divisionService;
	
	
	@RequestMapping(value = "/welcome")
    public String home() {
        
        return "welcome";
    }


	/**
	 * @param divisionService the divisionService to set
	 */
	@Autowired
	public void setDivisionService(DivisionService divisionService) {
		this.divisionService = divisionService;
	}

}
