package mx.gob.imss.ctirss.gestionpersonas.servicios.publicos;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

@Local
public interface ServiciosPersonaBusinessLocal {

	List<Fisica> localizarPersonaFisica(String sCurp,
			String sRfc,			
			String sNombre,
			String sPrimerApellido,
			String sSegundoApellido,
			Date fechaNacimiento,
			EntidadFederativa lugarNacimiento,
			Sexo sexo) throws ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException;
	
	/**
	 * 191807 201212 Este metodo busca una persona fisica en el IMSS, sus respectivos documentos probatorios, los domicilios y medios de contacto; finalmente
	 * los concentra en el mismo objeto
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	Fisica buscarPersonaFisicayDPyDyMCEnIMSS(Long idPersona)
			throws PersonaFisicaNoEncontradaException;
	
	/**
	 * 191807 201212 Este metodo busca una persona fisica en el IMSS, sus
	 * respectivos documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 * @throws NssRelacionadoVariasPersonasException
	 * @throws PersonasNoLocalizadasException
	 */
	Fisica buscarPersonaFisicayDPyDyMCEnIMSSbyNSS(String nss)
			throws PersonaFisicaNoEncontradaException,
			PersonasNoLocalizadasException, NssRelacionadoVariasPersonasException;
	
	/**
	 * Metodo que complementa la informaci�n de una persona Fisica en documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * @param  objeto Fisica fIMSS
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	Fisica complementaPersonaFisicaDPyDyMCEnIMSS(Fisica  pfIMSS) 
			throws PersonaFisicaNoEncontradaException;
			
	
	/**
	 * 
	 * Este metodo busca una persona moral en el IMSS, sus respectivos
	 * documentos probatorios, los domicilios y medios de contacto; finalmente
	 * los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 */
	Moral buscarPersonaMoralyDPyDyMCEnIMSS(Long idPersona);
	
	/**
	 * Servicio que busca una persona fisica en el IMSS y obtiene su domicilio
	 * fiscal. Este servicio es especial para la modificacion manual, ya que
	 * para la modificacion manual solo se necesita obtener el domicilio fiscal,
	 * las dem�s relaciones se obtienen de manera as�ncrona.
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	Fisica buscarPersonaFisicaParaModificacionManual(Long idPersona)
			throws PersonaFisicaNoEncontradaException;
	
	/**
	 * Servicio que busca una persona moral en el IMSS y obtiene su domicilio
	 * fiscal. Este servicio es especial para la modificacion manual, ya que
	 * para la modificacion manual solo se necesita obtener el domicilio fiscal,
	 * las dem�s relaciones se obtienen de manera as�ncrona.
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	Moral buscarPersonaMoralParaModificacionManual(Long idPersona);

	/**
	 * 191807 201212 Este metodo busca una persona fisica en el IMSS, sus respectivos documentos probatorios, los domicilios y medios de contacto; finalmente
	 * los concentra en el mismo objeto
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	Moral buscarPMyDPyDyMCEnIMSS_AP(Long idPersona);

}
