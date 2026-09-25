/**
 * Clase singleton para el contexto de JAXB.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import mx.gob.imss.webservice.renapo.curp.cliente.ConsultaDatosUsuario;
import mx.gob.imss.webservice.renapo.curp.cliente.ConsultaDatosUsuarioResponse;
import mx.gob.imss.webservice.renapo.curp.cliente.UsuarioInfoDTO;


import org.apache.log4j.Logger;

/**
 * @author Lucio Duran Silva
 * 
 * 
 * 
 */
public class JaxbContextHelper {

	private static Logger log = Logger.getLogger(JaxbContextHelper.class);

	// Contexto Jaxb
	private static volatile JAXBContext context;

	private static final Class[] classesToBeBound = new Class[] {
			ConsultaDatosUsuario.class, ConsultaDatosUsuarioResponse.class,
			UsuarioInfoDTO.class};

	/**
	 * Se implementa el patrón Double Checked Locking of Singleton
	 * 
	 * @return
	 */
	public static JAXBContext getInstance() {
		log.debug("Obteniendo el contexto de JAXB..");

		if (context == null) {
			synchronized (JAXBContext.class) {
				if (context == null) {
					try {
						log.debug("Se va a crear contexto nuevo de JAXB");
						context = JAXBContext.newInstance(classesToBeBound);
						log.debug("Se creo nuevo contexto nuevo de JAXB");
					} catch (JAXBException e) {
						log.error("Error al generar el contexto de Jaxb {"
								+ e.getMessage() + "}");
						e.printStackTrace();
					}
				}
			}
		} else {
			log.debug("Se recupera contexto de JAXB ya creado");
		}

		return context;
	}

}
