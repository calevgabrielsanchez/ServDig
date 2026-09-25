/**
 * 
 */
package mx.imss.ctirss.web.controller.firma;

import java.security.cert.X509Certificate;



import mx.imss.ctirss.web.controller.login.AbsractSeguridadController;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Alberto Cortes
 * @author Vladimir Aguirre Piedragil
 * 
 * 
 */
@Controller
@RequestMapping(value="/firma")
public class FirmaElectronicaController extends AbsractSeguridadController {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(FirmaElectronicaController.class);

	
}
