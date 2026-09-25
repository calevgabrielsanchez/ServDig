/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AseguradoConNSSAsignadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoComplementarPersonaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

/**
 * @author vanderluk
 * 
 */
@Stateless(name = "aseguradoComplementarPersonaBusiness", mappedName = "aseguradoComplementarPersonaBusiness")
public class AseguradoComplementarPersonaBusiness extends
		AbstractServiceBusiness implements
		AseguradoComplementarPersonaBusinessRemote {
	@EJB
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;

	@EJB
	private ComplementarCalificacionPersonaFisicaServiceBusinessRemote complementarCalificacionPersonaFisicaServiceBusinessRemote;

	@Override
	public Fisica complementarPersonaFisica(Fisica fisica)
			throws AseguradoConNSSAsignadoException,
			PersonaNoEncontradaException, ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			ClienteWebserviceSatRfcException,
			RFCNoLocalizadoEnEntidadExternaException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException, PersonaFisicaNoEncontradaException {

		Long idPersona = fisica.getIdPersona();
		
		fisica = serviciosPersonaBusinessRemote
				.buscarPersonaFisicayDPyDyMCEnIMSS(fisica.getIdPersona());
		
		if (fisica == null) {
			throw new PersonaNoEncontradaException(idPersona);
		}
		
		// Validamos si la persona tiene NSS
		/*if (fisica.getNss() != null && !fisica.getNss().isEmpty()) {
			this.log.error("La persona ya cuenta con un NSS..");
			throw new AseguradoConNSSAsignadoException(fisica.getNss());
		}*/

		// Complementamos las calificaciones...

		fisica = this.complementarCalificacionPersonaFisicaServiceBusinessRemote
				.complementarCalificaciones(fisica, fisica);

		this.log.debug("Numero de documentos del candidato regresado al asegurado complementar:"
				+ fisica.getDocumentosProbatorios());
		
		// Se quitan los domicilios que no sean particulares
		if (fisica.getDomicilios() != null && !fisica.getDomicilios().isEmpty()) {
			Iterator<Domicilio> it = fisica.getDomicilios().iterator();
			
			while(it.hasNext()) {
				Domicilio domicilio = it.next();
				
				if (domicilio.getDicTipoDomicilio() != null
						&& domicilio.getDicTipoDomicilio().getClave()
								.longValue() != TipoDomicilioEnum.PARTICULAR
								.getId()) {
					it.remove();
				}
			}
		}
		
		return fisica;
	}

}
