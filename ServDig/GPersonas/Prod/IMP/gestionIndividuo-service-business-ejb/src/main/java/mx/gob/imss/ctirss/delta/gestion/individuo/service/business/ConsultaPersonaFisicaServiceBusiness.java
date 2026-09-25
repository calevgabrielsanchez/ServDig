/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoActualizadaRenapoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.usuario.ActualizaUsuarioEsquemaSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoRegistradoEnEsquemaDeSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioRegistradoSSOException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CalculoPorcentajeProbabilidadUtility;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CalculoPorcentajeProbabilidadUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.PersonaGlobalUtilityLocal;


/**
 * @author Lucio Duran Silva
 * @model
 */

@Stateless(name = "consultaPersonaFisicaServiceBusiness", mappedName = "consultaPersonaFisicaServiceBusiness")
public class ConsultaPersonaFisicaServiceBusiness extends AbstractServiceBusiness implements ConsultaPersonaFisicaServiceBusinessRemote {

	@EJB
	private PersonaBusinessLocal personaBusiness;

	@EJB
	private CalculoPorcentajeProbabilidadUtilityLocal calculoPorcentajeProbabilidadUtility;

	@EJB
	private LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote localizarPersonaFisicaEnEntidadesExternasServiceBusiness;

	@EJB
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;

	//@EJB
	//private CompararPersonaFisicaEntidadExternaUtilityLocal compararPersonaFisicaEntidadExternaUtility;

	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

	@EJB
	private ComponentesExternosBusinessLocal componentesExternosBusiness;
	
	@EJB
	private PersonaGlobalUtilityLocal personaUtilityService;
	
	@EJB(name = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;

	 /**
		 * 
		 * @param fisica
		 * @return
		 * @throws PersonasNoLocalizadasException 
		 */
		
	@Override
	public List<Candidato> consultarPersonaFisica(Fisica fisica) throws PersonasNoLocalizadasException{
		LinkedList<Candidato> candidatos = new LinkedList<Candidato>();
		Map<Long , Candidato> mapCandidatos = new HashMap<Long, Candidato>();


		//Consultamos primero por CURP en el IMSS.
		List<Candidato> c1 = this.consultarPorCURPEnIMSS(fisica);






		//Consultamos segundo por RFC en el IMSS
		List<Candidato> c2 = this.consultarPorRFCEnIMSS(fisica);


		//Consultamos tercero por DB en el IMSS.
		/*
		 * Parche para que funcione la consulta por datos basicos 
		 * solo con los nombres.
		 */
		Fisica fdummy = new Fisica();
		fdummy.setNombre(fisica.getNombre());
		fdummy.setPrimerApellido(fisica.getPrimerApellido());
		fdummy.setSegundoApellido(fisica.getSegundoApellido());
		fdummy.setLugarNacimiento(fisica.getLugarNacimiento());
		fdummy.setFechaNacimiento(fisica.getFechaNacimiento());
		fdummy.setSexo(fisica.getSexo());

		this.log.debug("Los datos de la persona fisica a buscar en el IMSS son :" +fdummy );

		List<Candidato> c3 = this.consultarPorDatosBasicosEnIMSS(fdummy);

		candidatos.addAll(c1);
		candidatos.addAll(c2);
		candidatos.addAll(c3);

		this.log.debug("Numero de registros de los candidatos :" + candidatos.size());


		/*
		 * Validamos el numero de registros que se ubicaron, si no se ubico a ningun registro
		 * debemos de enviar una excepcion.
		 */

		if(candidatos.isEmpty()){

			throw new PersonasNoLocalizadasException();

		}


		for( Candidato c : candidatos){
			this.log.debug("Candidatos resultados de las 3 busquedas::" + c.getPersona().getIdPersona());
			Candidato cf = mapCandidatos.get(c.getPersona().getIdPersona());

			if(cf == null){
				this.log.debug("El candidato no existe en el mapa , lo agregamos." + c.getPersona().getIdPersona());
				mapCandidatos.put(c.getPersona().getIdPersona(), c);
			}else{
				this.log.debug("El candidato ya existe en el mapa, sumamos probabilidades:" + cf.getProbabilidad());
				Long p = cf.getProbabilidad();
				p = p + c.getProbabilidad();
				cf.setProbabilidad(p);
				this.log.debug("% De probabilidad del candidato " + p + "-"+ cf.getPersona().getIdPersona());
			}

		}

		candidatos = new LinkedList<Candidato>();
		for (Map.Entry<Long, Candidato> entry : mapCandidatos.entrySet()) {
			candidatos.add(entry.getValue());
		}

		//ordenarCandidatos(candidatos);

		return candidatos;
	}





	/**
	 * 
	 * @param fisica
	 * @return
	 */
	public List<Candidato> consultarPorCURPEnIMSS(Fisica fisica){
		List<Fisica> personas = null;
		List<Candidato> candidatos = new ArrayList<Candidato>();
		String curp = fisica.getCurp();
		personas = personaBusiness.buscarPersonaFisicaPorCurpEnImss(curp);

		if(personas != null){
			for(Fisica f : personas){
				try {
					Long idFisica = personaFisicaServiceBusiness.obtenerIDPersonaFisica(f.getIdPersona());
					f.setCveFisica(idFisica);
				} catch (PersonaFisicaNoEncontradaException e) {
					log.debug("No se encontró una persona física para el individuo proporcionado");
					f.setCveFisica(-1l);
				}
				Candidato c = new Candidato();
				Long probabilidad = this.calculoPorcentajeProbabilidadUtility
						.calcularProbabilidadDeCandidato(f,
								CalculoPorcentajeProbabilidadUtility.PESO_CURP);
				c.setPersona(f);
				c.setProbabilidad(probabilidad);
				candidatos.add(c);
			}
		}
		return candidatos;
	}

	/**
	 * 
	 * @param fisica
	 * @return
	 */
	public List<Candidato> consultarPorRFCEnIMSS(Fisica fisica){
		List<Fisica> personas = null;
		List<Candidato> candidatos = new ArrayList<Candidato>();
		String rfc = fisica.getRfc();


		/*
		 * En caso de que no se reciba el RFC no se va a consultar
		 * a la entidad SAT.
		 */
		if(rfc != null && !rfc.isEmpty()){

			personas = this.personaBusiness.buscarPersonaFisicaPorRfcEnImss(rfc);
			if(personas != null){
				for(Fisica f : personas){
					try {
						Long idFisica = personaFisicaServiceBusiness.obtenerIDPersonaFisica(f.getIdPersona());
						f.setCveFisica(idFisica);
					} catch (PersonaFisicaNoEncontradaException e) {
						log.debug("No se encontró una persona física para el individuo proporcionado");
						f.setCveFisica(-1l);
					}
					Candidato c = new Candidato();
					Long probabilidad = this.calculoPorcentajeProbabilidadUtility
							.calcularProbabilidadDeCandidato(f,
									CalculoPorcentajeProbabilidadUtility.PESO_RFC);
					c.setPersona(f);
					c.setProbabilidad(probabilidad);
					candidatos.add(c);
				}
			}
		}


		return candidatos;
	}

	/**
	 * 
	 * @param fisica
	 * @return
	 */
	public List<Candidato> consultarPorDatosBasicosEnIMSS(Fisica fisica){
		List<Fisica> personas = null;
		List<Candidato> candidatos = new ArrayList<Candidato>();
		personas = this.personaBusiness.buscarPersonaFisicaPorDatosBasicosEnImss(fisica);
		if(personas != null){
			for(Fisica f : personas){
				Candidato c = new Candidato();
				Long probabilidad = this.calculoPorcentajeProbabilidadUtility
						.calcularProbabilidadDeCandidato(f,
								CalculoPorcentajeProbabilidadUtility.PESO_IMSS);
				c.setPersona(f);
				c.setProbabilidad(probabilidad);
				candidatos.add(c);
			}
		}
		return candidatos;
	}


	@Override
	public Fisica validaPersonaRegistroUsuario(Fisica fisica) 
			throws ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException,
			ClienteWebserviceRenapoCurpException,
			ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			CURPNoLocalizadoEnEntidadExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			UsuarioRegistradoSSOException, DiferenciasRENAPOContraSAT,
			CURPNoActualizadaRenapoException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			EsquemaSegurdiadException {
		//se valida que la persona se encuentre registrada como usuario en LDPA

		Usuario usuarioAM = null;
		Fiel fiel = null;
		//existeRegistroLdap = componentesExternosBusiness.existeUsuarioEsquemaSeguridadByCURP(fisica.getCurp());
		try {
			usuarioAM =  componentesExternosBusiness.recuperaUsuarioEsquemaSeguridadByCURP(fisica.getCurp());
			this.log.debug("pase la llamad del SSO con valor "+  usuarioAM);
			if(usuarioAM != null){
				if(this.validaRegistroUsuarioAMenBDTU(usuarioAM))
					throw new UsuarioRegistradoSSOException();
				else {
					try {
					componentesExternosBusiness.eliminaUsuarioEsquemaSeguridadByCURP(fisica.getCurp());
					}catch (Exception e) {
						log.error("ocurrio un error al eliminar el usario inconsistente" ,e);
						throw new EsquemaSegurdiadException("No se pudo dar de baja el usuario en el"
								+ " esquema de seguridad " + e.getMessage() );
					}
				}
			fiel = new Fiel();
			fiel.setClaveSerial(usuarioAM.getPassword());
			fiel.setCurpFiel(usuarioAM.getFisica().getCurp());
			}

		}catch (UsuarioNoEncontradoException e) {
			log.debug("no se localizo el usuario en el SSO", e);
		}catch(EsquemaSegurdiadException e) {
			log.debug("no se localizo el usuario en el SSO" ,e);
		}
		this.log.debug("pase la consulta en SSO y no trae usuario");
		Fisica personaRecuperada = getPersonaByCurpImssEntidadesExternas(fisica);
		if(!personaRecuperada.getCurp().equalsIgnoreCase(fisica.getCurp())) {
			throw new CURPNoActualizadaRenapoException();
		}
		
		personaRecuperada.setFiel(fiel);
		return personaRecuperada;
	}

	


	@Override
	public Fisica getPersonaByCurpImssEntidadesExternas(Fisica fisica)
			throws ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException,
			ClienteWebserviceRenapoCurpException,
			ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			CURPNoLocalizadoEnEntidadExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			DiferenciasRENAPOContraSAT,
			ErrorValidacionDatosConsultaEnEntidaExternaException {
		// se valida que la persona se encuentre registrada como usuario en LDPA
		Fisica personaLocalizadaCalificacion = null;
		Fisica personaLocalizadaRenapo = null;
		Fisica fisicaFinalTramite = null;

		// en elgun momneto se evaluo si existia mes de una vez el usuario
		// registrado en el instituto arrojar excepcion
		log.debug("---------------> Realizando busqueda de personas calificadas con CURP" + fisica.getCurp());
		try {
			personaLocalizadaCalificacion = personaBusiness
					.consutalPersonaByCurpConCalificion(fisica.getCurp());
		} catch (ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException exCalificacion) {
			log.error(exCalificacion);
		}
		this.log.debug("pase la primer consulta de calificadas");

		//cuando se localiza una persona con calificacion se debe de utilizar esta para el registro.
		if (personaLocalizadaCalificacion != null) {
			log.debug("---------------> Se localizo a la personas calificada, buscando y comparando con SAT Y RENAPO");
			personaLocalizadaCalificacion.setRfc(fisica.getRfc());
			fisicaFinalTramite = localizarPersonaFisicaEnEntidadesExternasServiceBusiness
					.localizarCompararPersonaFisicaEnEntidadesExternasxCURPyRFC(personaLocalizadaCalificacion);

			try {
				Long idPersonaCalificada = personaLocalizadaCalificacion.getIdPersona();
				Long idPersonaFisicaCalificada = personaFisicaServiceBusiness
						.obtenerIDPersonaFisicaEscVirtual(idPersonaCalificada);
				fisicaFinalTramite.setCveFisica(idPersonaFisicaCalificada);
			} catch (PersonaFisicaNoEncontradaException e) {
				this.log.error("error al recuperar el idFisica de un objeto persona calificado", e);
			}

			log.debug("---------------> La personas calificada tiene coincidencia con SAT Y RENAPO, se toma como referencia");
			fisicaFinalTramite.setIdPersona(personaLocalizadaCalificacion.getIdPersona());
			return fisicaFinalTramite;
		} else {
			log.debug("---------------> No hay personas calificadas con curp : " + fisica.getCurp());
		}

		// se valida que el curp capturado exista en RENAPO
		log.debug("---------------> Se valida existencia del CURP " + fisica.getCurp() + " en RENAPO");
		personaLocalizadaRenapo = localizarPersonaFisicaEnRENAPOServiceBusiness
				.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());

		// Se verifica si existe la persona con curp en caso de no localizarla con calificacion
		log.debug("---------------> Realizando busqueda de personas (sin calif) con CURP" + fisica.getCurp());
		List<Fisica> personas = personaBusiness
				.buscarPersonaFisicaPorCurpEnImss(fisica.getCurp());

		//iteramos los localizados para tratar de encontrar a la persona correcta
		for (Fisica objPersonaLocalizada : personas) {
			this.log.debug("for de personas localizadas por curp");
			try {
				// nos aseguramos de asignar el RFC de la fiel de lap persona
				// que realiza el registro
				if (StringUtils.isBlank(objPersonaLocalizada.getRfc())
						|| objPersonaLocalizada.getRfc().equalsIgnoreCase(fisica.getRfc())) {
					log.debug("---------------> EL RFC de la persona encontrada coincide o esta vacia");
					objPersonaLocalizada.setRfc(fisica.getRfc());

					// se compara la persona con las entidades externas
					log.debug("---------------> Realizando comparacion de la personas (sin calif) con SAT Y RENAPO");
					fisicaFinalTramite = localizarPersonaFisicaEnEntidadesExternasServiceBusiness
							.localizarCompararPersonaFisicaEnEntidadesExternasxCURPyRFC(objPersonaLocalizada);
					try {
						Long idPersonaCalificada = objPersonaLocalizada.getIdPersona();
						Long idPersonaFisicaCalificada = personaFisicaServiceBusiness
								.obtenerIDPersonaFisicaEscVirtual(idPersonaCalificada);
						fisicaFinalTramite.setCveFisica(idPersonaFisicaCalificada);
					} catch (PersonaFisicaNoEncontradaException e) {
						this.log.error("error al recuperar el idFisica de un objeto persona calificado", e);
					}

					log.debug("---------------> La persona (sin calif) tiene coincidencia con SAT Y RENAPO, se toma como referencia");
					fisicaFinalTramite.setIdPersona(objPersonaLocalizada.getIdPersona());
					return fisicaFinalTramite;
				}
			} catch (ErrorComparacionDatosRENAPOException eRe) {
				this.log.error("error al comparar a la persona localizada con curp", eRe);
			} catch (ErrorComparacionDatosSATException eSat) {
				this.log.error("error al comparar datos basicos en sat persona localizada", eSat);
			}
		}

		// Se agrega calificacion de RENAPO
		calificarPersonaLocalizada(personaLocalizadaRenapo, CalificacionPersona.VALIDADO_RENAPO);

		// se setea el rfc de forma y se manda a evaluar en SAT para validar que
		// si exista y no existan diferencoas contra SAT
		personaLocalizadaRenapo.setRfc(fisica.getRfc());
		try {
			log.debug("---------------> Realizando comparacion de persona nueva (RENAPO) con SAT");
			Fisica personaLocalizadaSat = localizarPersonaFisicaEnEntidadesExternasServiceBusiness
					.localizarCompararPersonaFisicaEnSATxRFC(personaLocalizadaRenapo);

			if (personaLocalizadaRenapo != null && personaLocalizadaSat != null) {
				if (StringUtils.isBlank(personaLocalizadaSat.getCurp())) {
					throw new DiferenciasRENAPOContraSAT("Existen diferencias en el CURP de la persona en RENAPO y SAT");
				} else if (!personaLocalizadaRenapo.getCurp().equals(personaLocalizadaSat.getCurp())) {
					throw new DiferenciasRENAPOContraSAT("Existen diferencias en el CURP de la persona en RENAPO y SAT");
				}
			}

			calificarPersonaLocalizada(personaLocalizadaRenapo, CalificacionPersona.VALIDADO_SAT);
		} catch (ErrorComparacionDatosSATException ex) {
			throw new DiferenciasRENAPOContraSAT();
		}

		log.debug("---------------> La persona nueva (RENAPO) tiene coincidencia con SAT, se toma como referencia");
		return personaLocalizadaRenapo;
	}

	private void calificarPersonaLocalizada(Fisica candidato, Integer idcalificacion) {
		Calificacion calificacion = new Calificacion();
		calificacion.setIdCalificacion(idcalificacion.longValue());

		if (CalificacionPersona.VALIDADO_RENAPO.equals(idcalificacion)) {
			calificacion.setDescripcion(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);
		} else {
			calificacion.setDescripcion(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);
		}

		PersonaCalificacion personaCalif = new PersonaCalificacion();
		personaCalif.setCalificacion(calificacion);
		personaCalif.setPersona(new Persona());
		personaCalif.getPersona().setIdPersona(candidato.getIdPersona());

		candidato.getPersonaCalificaciones().add(personaCalif);
	}

	@Override
	public Fisica validaActualizarUsuarioEnEsquemaSeguridad(Usuario objUsuario) 
			throws
			ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException,
			ClienteWebserviceRenapoCurpException,
			ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			CURPNoLocalizadoEnEntidadExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			UsuarioRegistradoSSOException,
			UsuarioNoRegistradoEnEsquemaDeSeguridadException,
			EsquemaSegurdiadException, UsuarioNoEncontradoException, ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException,
			ActualizaUsuarioEsquemaSeguridadException
	{
		//se valida que la persona se encuentre registrada como usuario en LDPA
		Fisica usuarioFisica = objUsuario.getFisica();
		Fisica personaLocalizadaRenapo = null;
		//Fisica personaLocalizadaCalificacion = null;
		boolean existeRegistroLdap = false;
		String curpRegistrado = objUsuario.getUsuario();
		String curpCertificado = usuarioFisica.getCurp();

		Fisica fisicaBdtu = null;

		//se cambia la llamada para validar la información de idPersona del usuario
		//existeRegistroLdap = componentesExternosBusiness.existeUsuarioEsquemaSeguridadByCURP(curpRegistrado);

		//el objeto usuario trae el curp en su propiedad getUsuario este dato es su llave en SSO
		Usuario objUsuarioAM  = componentesExternosBusiness.recuperaUsuarioEsquemaSeguridadByCURP(curpRegistrado);
		this.log.debug("pase la llamada del SSO con valor "+  existeRegistroLdap);
		if(objUsuarioAM == null){
			throw new UsuarioNoRegistradoEnEsquemaDeSeguridadException("Usuario no registrado en el esquema de seguridad");
		}
		//se valida que exista la persona con el curp registrado en renapo
		personaLocalizadaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curpRegistrado);
		if(personaLocalizadaRenapo == null){
			throw new CURPNoLocalizadoEnEntidadExternaException("No se encontro ninguna persona con el CURP " + curpRegistrado + " en RENAPO");
		}

		//Fisica fisicaBdtu =personaBusiness.getPersonaFisica(objUsuarioAM.getFisica().getIdPersona());
		try {
			fisicaBdtu = personaBusiness.getFisicaBySolicitudRegistroPortalConFiel(curpRegistrado);
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar a la pesona en BDTU en registro de usuario con feil", e );
			throw new ActualizaUsuarioEsquemaSeguridadException("Ocurrio un error y no se pudo recuperar la información de la persona que realizo el registro de usuario en BDTU "
					+ e.getMessage() );
		}
		if(fisicaBdtu == null) {
			throw new ActualizaUsuarioEsquemaSeguridadException("Se han encontrado inconsistencias y no se econtr\u00f3 informaci\u00f3n de la persona que realiz\u00f3 el registro de usuario en BDTU ");
		}

		if(fisicaBdtu.getRfc() == null){
			throw new ActualizaUsuarioEsquemaSeguridadException("Se han encontrado inconsistencias en la informaci\u00f3n con respecto a su RFC. Favor de acudir a su subdelegación");
		}


		//se valida que exista la persona con el curp registrado en renapo
		personaLocalizadaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curpRegistrado);

		if(personaLocalizadaRenapo == null){
			throw new CURPNoLocalizadoEnEntidadExternaException("No se encontro ninguna persona con el CURP " + curpRegistrado + " en RENAPO");
		}

		//se valida que la persona exista y que tenga rfc para comparar contra el certificado
		/* se cambia la validacion para que evalue si la pesona tiene el mismo idPersonapara el tramite de alta de usuario
		personaLocalizadaCalificacion = this.personaBusiness.consutalPersonaByCurpConCalificion(curpRegistrado);
		if(personaLocalizadaCalificacion == null){
			throw new UsuarioNoRegistradoEnEsquemaDeSeguridadException("No se encontro ninguna persona con el CURP " + curpRegistrado + " en IMSS");
		}*/


		this.actualizarNombreUsuarioBDTU(fisicaBdtu, personaLocalizadaRenapo);


		//se valida que el curp de la consulta en renapo y del certificado sean iguales para garantizar que es la misma persona
		if(personaLocalizadaRenapo.getCurp().equalsIgnoreCase(usuarioFisica.getCurp())){
			//se compara que el curp y rfc registrado y el del certificado sean iguales o no para marcar si hay cambio de datos

			if(fisicaBdtu.getRfc() == null){
				throw new ActualizaUsuarioEsquemaSeguridadException("Se han encontrado inconsistencias en la información con respecto a su RFC. Favor de acudir a su subdelegación");
			}else if(curpRegistrado.equalsIgnoreCase(curpCertificado) && 
					fisicaBdtu.getRfc().equalsIgnoreCase(usuarioFisica.getRfc())){

				//son el mismo registro se valida que el serial del certificado sea correcto
				log.error("El password del usuario en el esquema de seguridad es: " + objUsuarioAM.getPassword());
				log.error("El password dentro del certificado es : " + objUsuario.getPassword());
				if(objUsuarioAM.getPassword().equalsIgnoreCase(objUsuario.getPassword())){
					throw new ActualizaUsuarioEsquemaSeguridadException("No existen diferencias entre el certificado actual y la información en el instituto");
				}
				return fisicaBdtu;
			}else{
				//se adiciona validacion para confirmar que el nuevo curp no exista
				existeRegistroLdap = false;
				existeRegistroLdap = componentesExternosBusiness.existeUsuarioEsquemaSeguridadByCURP(curpCertificado);
				this.log.debug("pase la segunda llamada del SSO con valor "+  existeRegistroLdap);
				if(existeRegistroLdap){
					throw new ActualizaUsuarioEsquemaSeguridadException("El CURP del certificado ya se encuentra registrado en el esquema de seguridad");
				}
				return fisicaBdtu;
			}



		}else{
			throw new ActualizaUsuarioEsquemaSeguridadException("El CURP del certificado no coincide con el CURP en RENAPO");
		}




		//return getPersonaByCurpImssEntidadesExternas(fisica);
	}

	@Override
	public void actualizaNombreUsuarioPatronBDTU(Usuario objUsuario) 
			throws
			ClienteWebserviceRenapoCurpException,
			CURPNoLocalizadoEnEntidadExternaException,
			UsuarioNoRegistradoEnEsquemaDeSeguridadException,
			EsquemaSegurdiadException, UsuarioNoEncontradoException,
			ActualizaUsuarioEsquemaSeguridadException
	{
		//se valida que la persona se encuentre registrada como usuario en LDPA
		Fisica personaLocalizadaRenapo = null;

		String curpRegistrado = objUsuario.getUsuario();

		//el objeto usuario trae el curp en su propiedad getUsuario este dato es su llave en SSO
		Usuario objUsuarioAM  = componentesExternosBusiness.recuperaUsuarioEsquemaSeguridadByCURP(curpRegistrado);
		if(objUsuarioAM == null){
			throw new UsuarioNoRegistradoEnEsquemaDeSeguridadException("Usuario no registrado en el esquema de seguridad");
		}
		//se valida que exista la persona con el curp registrado en renapo
		personaLocalizadaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curpRegistrado);
		if(personaLocalizadaRenapo == null){
			throw new CURPNoLocalizadoEnEntidadExternaException("No se encontro ninguna persona con el CURP " + curpRegistrado + " en RENAPO");
		}

		Fisica fisicaBdtu =personaBusiness.getPersonaFisica(objUsuarioAM.getFisica().getIdPersona());

		if(fisicaBdtu.getRfc() == null){
			throw new ActualizaUsuarioEsquemaSeguridadException("Se han encontrado inconsistencias en la información con respecto a su RFC. Favor de acudir a su subdelegación");
		}

		this.actualizarNombreUsuarioBDTU( fisicaBdtu, personaLocalizadaRenapo);
	}

	@Override
	public void actualizarNombreUsuarioBDTU(Fisica fisicaBdtu, Fisica personaLocalizadaRenapo){

		if(validaNombrePersonaBDTUvsREANAPONomberApellidos( fisicaBdtu, personaLocalizadaRenapo)){
			fisicaBdtu.setNombre(personaLocalizadaRenapo.getNombre());
			fisicaBdtu.setPrimerApellido(personaLocalizadaRenapo.getPrimerApellido());
			fisicaBdtu.setSegundoApellido(personaLocalizadaRenapo.getSegundoApellido());
			AfectarDatosPersonaWrapper afectarDatosPersonaWrapper = new AfectarDatosPersonaWrapper();
			afectarDatosPersonaWrapper.setFisica(fisicaBdtu);
			afectarDatosPersonaWrapper.setModificarNombre(true);
			try {
				personaBusiness.afectarDatosPersona(afectarDatosPersonaWrapper);
			} catch (PersonaNoEncontradaException e) {
				log.error("No se pudo actualizar el nombre de la persona" , e);
			}

		}
	}

	private boolean validaNombrePersonaBDTUvsREANAPONomberApellidos(Fisica fisicaBdtu, Fisica personaLocalizadaRenapo) {

		boolean nombresIguales = false;
		String nombreCompletoBdtu = null;
		String nombreCompletoRenapo = null;

		if ((StringUtils.isEmpty(fisicaBdtu.getPrimerApellido())
				|| fisicaBdtu.getPrimerApellido().toUpperCase().trim().equals("NULL") )
				&& (StringUtils.isEmpty(fisicaBdtu.getSegundoApellido())
						|| fisicaBdtu.getSegundoApellido().toUpperCase().trim().equals("NULL"))){
			nombreCompletoBdtu = personaUtilityService.quitarCaracteresEspeciales(fisicaBdtu.getNombre().toUpperCase()).trim();
			nombreCompletoRenapo = personaUtilityService.quitarCaracteresEspeciales(personaLocalizadaRenapo.getNombreCompleto().toUpperCase()).trim();
			log.debug("nombreCompletoBdtu: "+nombreCompletoBdtu);
			log.debug("nombreCompletoRenapo: "+nombreCompletoRenapo);
			if(StringUtils.replaceChars(nombreCompletoRenapo, ' ', 'a').equals(StringUtils.replaceChars(nombreCompletoBdtu, ' ', 'a'))){
				nombresIguales = true;
			}
		}

		return nombresIguales;

	}

	private boolean validaRegistroUsuarioAMenBDTU(Usuario usuarioAM) {
		boolean isYaRegistrado = true;
		try {
		//se valida la existencia de la persona por Id
		Fisica personsaBdtu = personaBusiness.buscarPersnaPorID(usuarioAM.getFisica().getIdPersona());
		if(personsaBdtu == null)
			return isYaRegistrado=false;
		//si la persona se localizó se valida que tenga los tramites de registro de usuario y firma digital
		List<Solicitud> lstSol =solicitudBusiness.obtenerSolicitudPorPersona(personsaBdtu.getIdPersona(),
				TipoPersonaFiscal.FISICA, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.REGISTRO_USUARIOS_SSO, EstadoSolicitudEnum.ATENDIDA, new Boolean(false));
		if(lstSol == null || lstSol.isEmpty())
			return isYaRegistrado=false;
		}catch(PersonaNoEncontradaException e) {
			log.debug("no existe la persona");
			return isYaRegistrado=false;
		}catch (Exception e) {
			log.error("ocurrio un error al validar la consitencia del usuario registrado" , e);
		}

		return isYaRegistrado;
	}

}
