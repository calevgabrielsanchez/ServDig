package mx.gob.imss.ctirss.gestionpersonas.servicios.publicos;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosPersonaSATNoValidosException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.situacionSAT.SituacionSATNoValidaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.entity.UsuarioPortalEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPerPortalCiudadano;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.DatosPersonaSATServiceBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SituacionSATServiceBusinessLocal;

@Stateless(name = "serviciosPersonaBusiness", mappedName = "serviciosPersonaBusiness")
public class ServiciosPersonaBusiness extends AbstractServiceBusiness implements
		ServiciosPersonaBusinessLocal, ServiciosPersonaBusinessRemote {

	@EJB
	private transient PersonaBusinessLocal personaBusiness;
	
    @EJB
    private ComponentesExternosBusinessLocal componentesExternosBusiness;
    
    @EJB
    private PersonaMoralBusinessLocal personaMoralBusiness;
    
    @EJB
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    
    @EJB
    private SituacionSATServiceBusinessLocal situacionSATServiceBusiness;
    
    @EJB
	private DatosPersonaSATServiceBusinessLocal datosPersonaSATServiceBusiness;
    
    @EJB
    private PersonaFisicaServiceUtilityLocal personaFisicaServiceUtility;
    
    @EJB
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;
    
    @EJB
    private UsuarioPortalEntityLocal usuarioPortalEntityLocal;
    
    @EJB
    private AfiliacionServiceBusinessRemote afiliacionServiceBusiness;

	/*
	 * Introducción:
	 * 
	 * El servicio nos permite localizar los datos de una persona en el IMSS,
	 * RENAPO o SAT.
	 * 
	 * El servicio podrá ser ejecutado bajo cualquiera de las 2 secciones de
	 * par�metros:
	 * 
	 * 1.- CURP: sCurp 2.- Datos B�sicos: sNombre, sPrimerApellido,
	 * sSegundoApellido, fechaNacimeinto, lugarNacimeinto, sexo
	 * 
	 * Secci�n CURP 1.- El servicio busca a la persona por CURP en el IMSS
	 * 
	 * 1.- Si la persona fue encontrada en el IMSS regresar�: 1.1 El idPersona y
	 * los datos b�sicos 1.2 Objeto Calificacion en nulo
	 * 
	 * Secci�n Datos B�sicos. 1.- El servicio busca a la persona por datos
	 * b�sicos en el IMSS
	 * 
	 * 1.- Si la persona fue encontrada en el IMSS regres�: 1.1 El idPersona.
	 * 1.2 Objeto Calificacion en nulo
	 * 
	 * 2.- Si la persona no se encontr� en el IMSS
	 * 
	 * a) Busca por datos b�sicos en el servicio de RENAPO
	 * 
	 * a.1) Si la persona fue encontrada el servicio regresa b.1) el objeto
	 * Calificacion.idSubestadoValidado = 1 "Validado por RENAPO" b.2) el
	 * atributo CURP encontrado en RENAPO
	 * 
	 * a.2) Si la persona no fue encontrada encontrada en RENAPO el servicio
	 * regresa en Calificacion.idSubestadoValidado = 4 "No Validado"
	 * 
	 * Notas: 1) Al encontrar mas de un registro en el IMSS con los par�metros
	 * de busqueda el servicio solo regresar� el primero de los registros.
	 */

	public List<Fisica> localizarPersonaFisicaRespaldo(String sCurp,
			String sRfc, String sNombre, String sPrimerApellido,
			String sSegundoApellido, Date fechaNacimiento,
			EntidadFederativa lugarNacimiento, Sexo sexo) {
		System.out
				.println("ServiciosPersonaBusiness. localizarPersonaFisica. Inicio de servicio...");
		List<Fisica> respuesta = null;
		boolean bEjecucionSeccionDatos = false;

		try {			
			// VERIFICAMOS SI EJECUTAMOS EL SERVICIO POR SECCION 1 DE DATOS
			// (CURP)
			if (sCurp != null) {
				if (!sCurp.equals("")) {
					bEjecucionSeccionDatos = true;

					// BUSCAMOS A LA PERSONA POR CURP EN EL IMSS
					respuesta = personaBusiness
							.buscarPersonaFisicaPorCurpEnImss(sCurp);
				}
			}

			// VERIFICAMOS SI EJECUTAMOS EL SERVICIO POR SECCION 3 DE DATOS
			// (RFC)
			if (sRfc != null) {
				if (!sRfc.equals("")) {
					bEjecucionSeccionDatos = true;

					// BUSCAMOS A LA PERSONA POR CURP EN EL IMSS
					respuesta = personaBusiness
							.buscarPersonaFisicaPorRfcEnImss(sRfc);
				}
			}

			// VERIFICAMOS SI EJECUTAMOS EL SERVICIO POR SECCION 3 DE DATOS
			// (DATOS BASICOS)
			if (!bEjecucionSeccionDatos) {
				Fisica filtroBusqueda = new Fisica();

				// RECUPERAMOS LOS DATOS DE ENTRADA DEL SERVICIO
				filtroBusqueda.setNombre(sNombre);
				filtroBusqueda.setPrimerApellido(sPrimerApellido);
				filtroBusqueda.setSegundoApellido(sSegundoApellido);
				filtroBusqueda.setFechaNacimiento(fechaNacimiento);
				filtroBusqueda.getSexo().setIdSexo(
						new Long(sexo.getIdSexo()).intValue());

				if (lugarNacimiento != null) {
					if (lugarNacimiento.getClave() != null
							&& !lugarNacimiento.getClave().equals("")) {
						filtroBusqueda.getLugarNacimiento().setClave(
								lugarNacimiento.getClave());
					}
				}
				System.out
						.println("ServiciosPersonaBusiness. localizarPersonaFisica. Llamado de servicio de busqueda");

				// OBTIENE LOS RESULTADOS DE LA BASE DE DATOS
				List<Fisica> listaPersonaFisica = personaBusiness
						.buscarPersonaFisicaPorDatosBasicosEnImss(filtroBusqueda);

				// VERIFICA SI ENCONTRO REGISTROS EN LA BASE DE DATOS DEL IMSS
				if (listaPersonaFisica != null) {
					if (listaPersonaFisica.size() > 0) {
						respuesta = listaPersonaFisica;
					} else {

						// PREPARA RESPUESTA
						respuesta = new LinkedList<Fisica>();
						Calificacion calificacion = new Calificacion();
						PersonaCalificacion personaCalificacion = new PersonaCalificacion();
						personaCalificacion.setCalificacion(calificacion);

						// BUSCAMOS A LA PERSONA POR DATOS BASICOS EN EL RENAPO
						Fisica personaFisicaRenapo = personaBusiness
								.buscarPersonaFisicaPorDatosBasicosEnRenapo(
										sNombre, sPrimerApellido,
										sSegundoApellido,
										new Long(sexo.getIdSexo()).intValue(),
										fechaNacimiento, Integer
												.parseInt(lugarNacimiento
														.getClave()));

						// VERIFICA SI SE ENCONTRO A LA PERSONA EN RENAPO
						if (personaFisicaRenapo != null) {

							// PERSONA VALIDADA POR RENAPO
							calificacion
									.setIdCalificacion(CalificacionPersona.VALIDADO_RENAPO
											.longValue());
							personaFisicaRenapo.getPersonaCalificaciones().add(
									personaCalificacion);
							respuesta.add(personaFisicaRenapo);
						} else {
							// PERSONA NO VALIDADA
							Fisica personaNueva = new Fisica();
							personaNueva.setNombre(sNombre);
							personaNueva.setPrimerApellido(sPrimerApellido);
							personaNueva.setSegundoApellido(sSegundoApellido);
							personaNueva.setFechaNacimiento(fechaNacimiento);
							personaNueva.getSexo().setIdSexo(
									new Long(sexo.getIdSexo()).intValue());

							if (lugarNacimiento != null) {
								if (lugarNacimiento.getClave() != null
										&& !lugarNacimiento.getClave().equals(
												"")) {
									personaNueva.getLugarNacimiento().setClave(
											lugarNacimiento.getClave());
								}
							}

							calificacion
									.setIdCalificacion(CalificacionPersona.NO_VALIDADO
											.longValue());
							personaNueva.getPersonaCalificaciones().add(
									personaCalificacion);
							respuesta.add(personaNueva);
						}// VERIFICA SI SE ENCONTRO A LA PERSONA EN RENAPO
					}
				}
			}// VERIFICAMOS SI EJECUTAMOS EL SERVICIO POR SECCION 3 DE DATOS
				// (DATOS BASICOS)

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respuesta;
	}

	public List<Fisica> localizarPersonaFisica(String sCurp, String sRfc,
			String sNombre, String sPrimerApellido, String sSegundoApellido,
			Date fechaNacimiento, EntidadFederativa lugarNacimiento, Sexo sexo) 
					throws ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException{
		System.out.println("ServiciosPersonaBusiness. localizarPersonaFisica. Inicio de servicio...");
		List<Fisica> respuesta = null;

		
		//REALIZAMOS PRIMERO BUSQUEDAS EN EL IMSS
		//1.- POR CURP
		//2.- POR RFC
		//3.- POR DATOS BASICOS
		
		//EL RESULTADO DE CADA UNA DE ELLAS LAS JUNTAMOS Y ENTREGAMOS UN SOLO CONJUNTO DE RESULTADOS
		
		//BUSCAMOS EN IMSS POR RFC
		List<Fisica> personasBusqueda= null;
		if (sRfc != null && !sRfc.equals("")){
			personasBusqueda = personaBusiness.buscarPersonaFisicaPorRfcEnImss(sRfc);
			respuesta = agregaPersonasListaFinal(respuesta, personasBusqueda);				
		}
		
		//BUSCAMOS EN IMSS POR CURP
		if (sCurp != null && !sCurp.equals("")){
			personasBusqueda = personaBusiness.buscarPersonaFisicaPorCurpEnImss(sCurp);
			respuesta = agregaPersonasListaFinal(respuesta, personasBusqueda);				
		}
		
		//BUCAMOS EN IMSS POR DATOS BASICOS
		Fisica filtroBusqueda = null;
		if (sNombre != null && !sNombre.equals("")){
			filtroBusqueda = filtroBusqueda == null ? new Fisica() : filtroBusqueda;
			filtroBusqueda.setNombre(sNombre);
		}
		if (sPrimerApellido != null && !sPrimerApellido.equals("")){
			filtroBusqueda = filtroBusqueda == null ? new Fisica() : filtroBusqueda;
			filtroBusqueda.setPrimerApellido(sPrimerApellido);
		}
		if (sSegundoApellido != null && !sSegundoApellido.equals("")){
			filtroBusqueda = filtroBusqueda == null ? new Fisica() : filtroBusqueda;
			filtroBusqueda.setSegundoApellido(sSegundoApellido);
		}
		if (fechaNacimiento != null ){
			filtroBusqueda = filtroBusqueda == null ? new Fisica() : filtroBusqueda;
			filtroBusqueda.setFechaNacimiento(fechaNacimiento);
		}
			
		if (sexo != null && sexo.getIdSexo() != null){
			filtroBusqueda = filtroBusqueda == null ? new Fisica() : filtroBusqueda;
			filtroBusqueda.getSexo().setIdSexo(
					new Long(sexo.getIdSexo()).intValue());				
		}

		if (lugarNacimiento != null) {
			if (lugarNacimiento.getClave() != null
					&& !lugarNacimiento.getClave().equals("")) {
				filtroBusqueda.getLugarNacimiento().setClave(
						lugarNacimiento.getClave());
			}
		}

		if (filtroBusqueda != null){
			//BUSCAMOS POR DATOS BASICOS EN EL IMSS
			personasBusqueda = personaBusiness.buscarPersonaFisicaPorDatosBasicosEnImss(filtroBusqueda);					    
			respuesta = agregaPersonasListaFinal(respuesta, personasBusqueda);
		}

		//SI LAS CONSULTAS EN EL IMSS NO ARROJARON NINGUN REGISTRO, ENTONCES BUSCAMOS EN ENTIDADES EXTERNAS

		//VERIFICAMOS SI BUSCAMOS EN ENTIDADES EXTERNAS - RENAPO
		if (respuesta == null){
			
			//BUSCAMOS EN RENAPO POR CURP
			if (sCurp != null && !sCurp.equals("")){
				Fisica personaFisicaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(sCurp);
				
				// VERIFICA SI SE ENCONTRO A LA PERSONA EN RENAPO
				if (personaFisicaRenapo != null) {

					// PERSONA VALIDADA POR RENAPO
					Calificacion calificacion = new Calificacion();
					PersonaCalificacion personaCalificacion = new PersonaCalificacion();
					personaCalificacion.setCalificacion(calificacion);
					
					calificacion.setIdCalificacion(CalificacionPersona.VALIDADO_RENAPO.longValue());
					calificacion.setDescripcion(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);						
					personaFisicaRenapo.getPersonaCalificaciones().add(personaCalificacion);
					
					// 191807 301012 se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
//					agregarIdentificadores(personaFisicaRenapo);
					
					respuesta = new LinkedList<Fisica>();
					respuesta.add(personaFisicaRenapo);
				}
			}
		}

		//VERIFICAMOS SI BUSCAMOS EN ENTIDADES EXTERNAS - RENAPO - DATOS BASICOS
		if (respuesta == null){
			//BUSCAMOS EN RENAPO POR CURP
			if (sNombre != null && !sNombre.equals("") &&
					sPrimerApellido != null && !sPrimerApellido.equals("") &&
					sSegundoApellido != null && !sSegundoApellido.equals("") &&
					fechaNacimiento != null &&
					sexo != null && sexo.getIdSexo() != null &&
					lugarNacimiento != null && lugarNacimiento.getClave() != null &&
					!lugarNacimiento.getClave().equals("")
					){
				Fisica personaFisicaRenapo = personaBusiness.buscarPersonaFisicaPorDatosBasicosEnRenapo(
									sNombre,
									sPrimerApellido,
									sSegundoApellido,
									sexo.getIdSexo().intValue(),
									fechaNacimiento,
									Integer.parseInt(lugarNacimiento.getClave()));
				// VERIFICA SI SE ENCONTRO A LA PERSONA EN RENAPO
				if (personaFisicaRenapo != null) {

					// PERSONA VALIDADA POR RENAPO
					Calificacion calificacion = new Calificacion();
					PersonaCalificacion personaCalificacion = new PersonaCalificacion();
					personaCalificacion.setCalificacion(calificacion);
					
					calificacion.setIdCalificacion(CalificacionPersona.VALIDADO_RENAPO.longValue());
					calificacion.setDescripcion(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);
					personaFisicaRenapo.getPersonaCalificaciones().add(personaCalificacion);
					
					// 191807 301012 se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
//					agregarIdentificadores(personaFisicaRenapo);
					
					respuesta = new LinkedList<Fisica>();
					respuesta.add(personaFisicaRenapo);
				}					
			}
		}
		
		//VERIFICAMOS SI BUSCAMOS EN ENTIDADES EXTERNAS - SAT
		if (respuesta == null){
			
			//BUSCAMOS EN SAT POR RFC
			if (sRfc != null && !sRfc.equals("")){
				Fisica personaFisica = personaBusiness.buscarPersonaFisicaPorRfcEnSat(sRfc);
				respuesta = new LinkedList<Fisica>();
				
				
				// 191807 150812
				// De acuerdo a las observaciones de Sandra Escudero, cuando se busca una persona fisica en SAT, debera hacerse una segunda busqueda
				// en RENAPO en caso de que el SAT haya regresado la CURP y llenar los documentos probatorios
				if (personaFisica != null && personaFisica.getCurp() != null && !personaFisica.getCurp().equals("")){
					Fisica personaFisicaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(personaFisica.getCurp());
					if (personaFisicaRenapo != null) {
						personaFisica.setActaNacimiento(personaFisicaRenapo.getActaNacimiento());
						personaFisica.setDocumentoMigratorio(personaFisicaRenapo.getDocumentoMigratorio());
						personaFisica.setCartaNaturalizacion(personaFisicaRenapo.getCartaNaturalizacion());
						personaFisica.setNumeroUnicoExtranjero(personaFisicaRenapo.getNumeroUnicoExtranjero());
						personaFisica.setCertificadoNacionalidadMexicana(personaFisicaRenapo.getCertificadoNacionalidadMexicana());
						personaFisica.setOficioSolicitanteRefugiado(personaFisicaRenapo.getOficioSolicitanteRefugiado());
						personaFisica.setFormaMigratoriaTurista(personaFisicaRenapo.getFormaMigratoriaTurista());
					}
					
					//personaFisica.setDocumentosProbatorios(personaFisicaRenapo.getDocumentosProbatorios());
					
					// PERSONA VALIDADA POR RENAPO
					Calificacion calificacion = new Calificacion();
					PersonaCalificacion personaCalificacion = new PersonaCalificacion();
					personaCalificacion.setCalificacion(calificacion);
					
					calificacion.setIdCalificacion(CalificacionPersona.VALIDADO_RENAPO.longValue());
					calificacion.setDescripcion(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);

					personaFisica.getPersonaCalificaciones().add(personaCalificacion);
								
					respuesta.add(personaFisica);
					
				}
				
				// VERIFICA SI SE ENCONTRO A LA PERSONA EN RENAPO
				if (personaFisica != null) {

					// PERSONA VALIDADA POR SAT
					Calificacion calificacion = new Calificacion();
					PersonaCalificacion personaCalificacion = new PersonaCalificacion();
					personaCalificacion.setCalificacion(calificacion);
					
					calificacion.setIdCalificacion(CalificacionPersona.VALIDADO_SAT.longValue());
					calificacion.setDescripcion(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);

					personaFisica.getPersonaCalificaciones().add(personaCalificacion);
					
					// 191807 301012 se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
//					agregarIdentificadores(personaFisica);
					
					respuesta.add(personaFisica);
				}
				
				// Y finalmente, si la persona no se encontro en ninugn lado, entonces la lista debe regresarse como null, porque si se regresa vacia "[]" 
				// entonces el controller hace pndjds
				if(respuesta.size() == 0){
					respuesta = null;
				}
			}
		}		


		return respuesta;
	}
	
	public List<Fisica> agregaPersonasListaFinal(List<Fisica> listaInicial, List<Fisica> listaAgregar){
		Map<Long,Fisica> mapaFinal = new TreeMap<Long,Fisica>();
		LinkedList<Fisica> listaFinal = null;
		
		//AGREGAMOS LAS LISTAS "INICIAL" Y "AGREGAR" AL MAPA FINAL
		if (listaAgregar != null){
			for (Fisica fisica : listaAgregar){
				mapaFinal.put(fisica.getIdPersona(), fisica);
			}
		}
		if (listaInicial != null){
			for (Fisica fisica : listaInicial){
				if (mapaFinal.get(fisica.getIdPersona()) == null){
					mapaFinal.put(fisica.getIdPersona(), fisica);	
				}
			}
		}
		
		//RECORREMOS EL MAPA FINAL EN ORDEN ASCENDENTE PARA OBTENER LA LISTA FINAL
		for (Map.Entry<Long, Fisica> entry : mapaFinal.entrySet()){
			if (listaFinal == null){
				listaFinal = new LinkedList<Fisica>();
			}
			listaFinal.add(entry.getValue());
		}
		
		return listaFinal;
	}
	
	/**
	 * 191807 201212 Este metodo busca una persona fisica en el IMSS, sus
	 * respectivos documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto a partir de un NSS
	 * 
	 * @param NSS
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 * @throws NssRelacionadoVariasPersonasException 
	 * @throws PersonasNoLocalizadasException 
	 */
	public Fisica buscarPersonaFisicayDPyDyMCEnIMSSbyNSS(String nss)
			throws PersonaFisicaNoEncontradaException,
			PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException {
		
		Fisica pfIMSS = new Fisica();
		pfIMSS = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
		
		return complementaPersonaFisicaDPyDyMCEnIMSS(pfIMSS);
	}
	
	
	/**
	 * 191807 201212 Este metodo busca una persona fisica en el IMSS, sus
	 * respectivos documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	public Fisica buscarPersonaFisicayDPyDyMCEnIMSS(Long idPersona)
			throws PersonaFisicaNoEncontradaException {
    	
		// Buscamos primero a la persona física en el IMSS
		Fisica pfIMSS = new Fisica();
		pfIMSS = personaBusiness.getPersonaFisica(idPersona);
		return complementaPersonaFisicaDPyDyMCEnIMSS(pfIMSS);
		
	}
	
		
	/**
	 * Metodo que complementa la informaci�n de una persona Fisica en documentos probatorios, los domicilios y medios de contacto;
	 * finalmente los concentra en el mismo objeto
	 * @param pfIMSS
	 * @return
	 * @throws PersonaFisicaNoEncontradaException
	 */
	public Fisica complementaPersonaFisicaDPyDyMCEnIMSS(Fisica  pfIMSS) throws PersonaFisicaNoEncontradaException {
			/* Después buscamos sus respectivos documentos probatorios y domicilios, 
			   tanto particulares como fiscales */
			
			Fisica pfDPyDyMC = null;
			if (pfIMSS != null) {
				Long idPersona = pfIMSS.getIdPersona();
				pfDPyDyMC = new Fisica();
				pfDPyDyMC.setIdPersona(idPersona);
				
				try {
					// Se busca el id de la persona fisica
					Long cveFisica = this.personaFisicaServiceBusiness.obtenerIDPersonaFisicaEscVirtual(idPersona);
					pfIMSS.setCveFisica(cveFisica);
					pfDPyDyMC.setCveFisica(cveFisica);
				} catch(PersonaFisicaNoEncontradaException pf) {
					log.error("No se encontro el id de la persona fisica" , pf);
				}
				
				log.debug("pase la busqueda de la persona fisica fisica");
		    	componentesExternosBusiness.getDocumentosProbatoriosPersona(pfDPyDyMC);
		    	componentesExternosBusiness.getDomiciliosPersona(pfDPyDyMC);
		    	componentesExternosBusiness.getMediosContactoPersona(pfDPyDyMC);
		    	
		    	componentesExternosBusiness.getMediosContactoFiscalesPersona(pfDPyDyMC);
		    	componentesExternosBusiness.getDomicilioFiscalPersona(pfDPyDyMC);
		    	
		    	// Se obtiene la situaci�n SAT de la persona
		    	try {
					pfIMSS.setSituacionesSAT(this.situacionSATServiceBusiness
							.obtenerSituacionesPersona(pfIMSS, true));
				} catch (SituacionSATNoValidaException e) {
					this.log.warn(e);
				}
		    	
		    	// Se obtiene los datos SAT de la persona
		    	try {
					pfIMSS.setDatosPersonaSAT(this.datosPersonaSATServiceBusiness
							.obtenerDatosSAT(pfIMSS));
				} catch (DatosPersonaSATNoValidosException e) {
					this.log.warn(e);
				}
		    	
		    	// Asignamos el documento probatorio a partir de la lista de documentos
				// registrados para la persona
				if (pfDPyDyMC.getDocumentosProbatorios() != null
						&& !pfDPyDyMC.getDocumentosProbatorios().isEmpty()) {
					
					pfIMSS.setDocumentosProbatorios(pfDPyDyMC.getDocumentosProbatorios());
					
					this.personaFisicaServiceUtility.asignarDocumentosProbatorios(pfIMSS, pfDPyDyMC.getDocumentosProbatorios());
				}  
		    	    	
				// Se recuperara el domicilio particular
		    	if(pfDPyDyMC.getDomicilios() != null && pfDPyDyMC.getDomicilios().size() > 0){
		    		pfIMSS.setDomicilios(pfDPyDyMC.getDomicilios());
		    	}
		    	    	
				// Se recuperan los medio de contacto particulares
		    	if(pfDPyDyMC.getMediosContacto() != null && pfDPyDyMC.getMediosContacto().size() > 0){
		    		pfIMSS.setMediosContacto(pfDPyDyMC.getMediosContacto());
		    			    	}
		    	
		    	// Se recupera el domicilio fiscal
		    	if(pfDPyDyMC.getDomicilioFiscal() != null){
		    		pfIMSS.setDomicilioFiscal(pfDPyDyMC.getDomicilioFiscal());
		    	}
		    	
		    	// Se recuperan los medio de contacto fiscales
		    	if(pfDPyDyMC.getMediosContactoFiscales() != null && !pfDPyDyMC.getMediosContactoFiscales().isEmpty()){
		    		pfIMSS.setMediosContactoFiscales(pfDPyDyMC.getMediosContactoFiscales());
					
		    		MedioContacto medioContacto = null;
		    		for (int i = 0; i < pfIMSS.getMediosContactoFiscales().size(); i++) {
		    			medioContacto = pfIMSS.getMediosContactoFiscales().get(i);
						
		    			if (medioContacto instanceof TelefonoFijo) {
							pfIMSS.setTelefonoFijoFiscalAux((TelefonoFijo)medioContacto);
						} else if (medioContacto instanceof TelefonoMovil) {
							pfIMSS.setTelefonoMovilFiscalAux((TelefonoMovil)medioContacto);
						} else if (medioContacto instanceof CorreoElectronico) {
							pfIMSS.setCorreoElectronicoFiscalAux((CorreoElectronico)medioContacto);
						}
					}
		    	}
			}
			
	    	
    	return pfIMSS;
	}
	
	
	/**
	 * 191807 201212
	 * Este metodo crea el objeto Nacimiento (de forma similar a como se creo en el metodo ClienteWebserviceCurp.recuperaInformacionRenapoObjetoNacimiento(...)) 
	 * el cual sera incluido en el objeto Fisica para que se pueda usar en el proceso ICA, concretamente en la parte de las comparaciones
	 * @param pfDocumentosProbatorios
	 * @return
	 *
    private Fisica unificarObjetosFisica(Fisica pfDocumentosProbatorios){ 
        
    	if (pfDocumentosProbatorios != null) {
        	
        	//VERIFICA SI AGREGAMOS EL ACTA DE NACIMIENTO
            if (pfDocumentosProbatorios.getDocumentosProbatorios().get(0).getIdDocumentoProbatorio() == TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor()){
            	TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getDescripcion());
            	
                //ACTA DE NACIMIENTO
                Nacimiento actaNacimiento = new Nacimiento();
                actaNacimiento.setAnio(respuesta.getAnioReg());
                actaNacimiento.setTomo(String.valueOf(respuesta.getTomo()));
                actaNacimiento.setCrip(String.valueOf(respuesta.getCRIP()));
                actaNacimiento.setNoFoja(String.valueOf(respuesta.getFoja()));
                actaNacimiento.setNoLibro(String.valueOf(respuesta.getLibro()));
                actaNacimiento.setNoActa(String.valueOf(respuesta.getNumActa()));
                actaNacimiento.setNoJuzgado("0"); //ESTE DATO DEBERA QUITARSE CUANDO LA TABLA DE MGPBDTU1.dit_NACIMIENTO.NUM_JUZGADO ACEPTE NULOS
                
                //MUNICIPIO - ENTIDAD DEL ACTA DE NACIMIENTO
                Municipio municipio = new Municipio();
                
                // 191807 231012 Se hace la asignacion del municipio y entidad de acuerdo a los valores recibidos en el WS
                corregirMunicipioInvalidoRENAPO(respuesta, municipio);
                
                actaNacimiento.setMunicipio(municipio); 
                
                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
                
                persona.setActaNacimiento(actaNacimiento);
                
            }
            else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor()){
            	
            	//DOCUMENTO MIGRATORIO
            	TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getDescripcion());
                Nacimiento actaNacimiento = new Nacimiento();
                
                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
                
                actaNacimiento.setAnio(respuesta.getNumRegExtranjeros()); //NUMERO DEL REGISTRO NACIONAL DE EXTRANJEROS
                
                persona.setActaNacimiento(actaNacimiento);
            	
            }
            else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor()){
            	
            	//CARTA DE NATURALIZACION
            	TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getDescripcion());
            	
                Nacimiento actaNacimiento = new Nacimiento();
                
                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
                
                actaNacimiento.setAnio(respuesta.getAnioReg()); //AÑO DE REGISTRO
                actaNacimiento.setIdDocumentoProbatorio(respuesta.getFolioCarta()); //FOLIO DE LA CARTA
                
                persona.setActaNacimiento(actaNacimiento);
                
            }
            else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor()){
            	
            	//NUMERO UNICO DE EXTRANJERO
            	TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion());
            	
                Nacimiento actaNacimiento = new Nacimiento();
                
                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
                
                actaNacimiento.setIdDocumentoProbatorio(respuesta.getCRIP()); //CRIP
                
                persona.setActaNacimiento(actaNacimiento);
                
            }
            else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor()){
            	
            	//CERTIFICADO DE NACIONALIDAD MEXICANA
            	TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion());

                Nacimiento actaNacimiento = new Nacimiento();
                
                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
                
                actaNacimiento.setAnio(respuesta.getAnioReg()); //AÑO DE REGISTRO
                actaNacimiento.setIdDocumentoProbatorio(respuesta.getFolioCarta()); //FOLIO DE LA CARTA
                persona.setActaNacimiento(actaNacimiento);

            }
            else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor()){
            	
            	//OFICIO SOLICITANTE DE REFUGIADO
            	TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion());
            	
                Nacimiento actaNacimiento = new Nacimiento();
                
                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
                
                actaNacimiento.setIdDocumentoProbatorio(respuesta.getCRIP()); //CRIP = NUMERO DE FOLIO DE OFICIO SOLICITANTE DE REFUGIADO
                persona.setActaNacimiento(actaNacimiento);

            }
            else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor()){
            	
            	//FORMA MIGRATORIA TURISTA
            	TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion());
            	
                Nacimiento actaNacimiento = new Nacimiento();

                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
            	
                actaNacimiento.setIdDocumentoProbatorio(respuesta.getCRIP()); //CRIP = NUMERO DE FOLIO DE OFICIO SOLICITANTE DE REFUGIADO
                persona.setActaNacimiento(actaNacimiento);
            }
            
            // Cuando el documento probatorio no es acta de nacimiento, en tratamiento sera un poco diferente para el resto de los documentos 
            if (respuesta.getDocProbatorio() != TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor()){
            	
            	//MUNICIPIO - ENTIDAD DEL ACTA DE NACIMIENTO
                EntidadFederativa entidadFederativa = new EntidadFederativa();                
                entidadFederativa.setNombre(respuesta.getDesEntidadNac());
                entidadFederativa.setClave(String.valueOf(respuesta.getCveEntidadNac()));   
                
                entidadFederativa.setNombre(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get(entidadFederativa.getClave()).toString());

                Municipio municipio = new Municipio();
                municipio.setEntidadFederativa(entidadFederativa);
       
                persona.getActaNacimiento().setMunicipio(municipio);
            }
        }
        return persona;
    }
	 * @throws PersonaFisicaNoEncontradaException 
	 * @throws PersonaNoEncontradaException 
    */
	
	@Override
	public Fisica buscarPersonaFisicaWidget(Long idPersona) throws PersonaFisicaNoEncontradaException, PersonaNoEncontradaException {
		// Buscamos primero a la persona física en el IMSS
		Fisica pfIMSS = new Fisica();
		pfIMSS = personaBusiness.buscarPersnaPorID(idPersona);
		
		Long cveFisica = this.personaFisicaServiceBusiness.obtenerIDPersonaFisica(idPersona);
		pfIMSS.setCveFisica(cveFisica);
		
		return pfIMSS;
	}
	
	@Override
	public Fisica buscarPersonaWidget(Long idPersona) throws  PersonaNoEncontradaException {
		// Buscamos primero a la persona física en el IMSS
		Fisica pfIMSS = new Fisica();
		pfIMSS = personaBusiness.buscarPersnaPorID(idPersona);		
		
		
		return pfIMSS;
	}
	
	/**
	 * Este metodo busca una persona moral en el IMSS, con sus datos basicos
	 * 
	 * @param idPersona
	 * @return
	 */
	public Moral buscarPMyDPyDyMCEnIMSS(Long idPersona) {
		// Buscamos primero a la persona moral en el IMSS
		Moral pmIMSS = new Moral();
		log.debug("::: V1.1 Buscando la persona Moral en IMSS-BDTU, ServiciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS: " + idPersona);
		Persona p = new Persona();
		p.setIdPersona(idPersona);
		p.setTipoPersona(new TipoPersona());
		p.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);						
		p = afiliacionServiceBusiness.obtenerDatosBasicosPersona(p);
		pmIMSS = (Moral)p;
		log.debug("::: Persona Moral obtenida: " + pmIMSS.getRfc() + " - " + pmIMSS.getRazonSocial());
		return pmIMSS;
	}		
	
	/**
	 * Este metodo busca una persona moral en el IMSS, sus respectivos
	 * documentos probatorios, los domicilios y medios de contacto (tanto
	 * particulares como fiscales); finalmente los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 */
	public Moral buscarPMyDPyDyMCEnIMSS_AP(Long idPersona) {
		log.debug("::: V1.1 Buscando la persona Moral en IMSS-BDTU, ServiciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS_AP: " + idPersona);
		// Buscamos primero a la persona moral en el IMSS
		Moral pmIMSS = new Moral();
		pmIMSS = personaMoralBusiness.getPersonaMoral_AP(idPersona);

		if (pmIMSS != null) {
			/* Despues buscamos sus respectivos documentos probatorios y domicilios, 
			   tanto particulares como fiscales */
			Moral pfDPyDyMC = new Moral();
			pfDPyDyMC.setIdPersona(idPersona);
			pfDPyDyMC.setCveMoral(pmIMSS.getIdPersona());
			pmIMSS.setCveMoral(pmIMSS.getIdPersona());
			
			componentesExternosBusiness.getDocumentosProbatoriosPersona(pfDPyDyMC);
			componentesExternosBusiness.getDomiciliosPersona(pfDPyDyMC);
			componentesExternosBusiness.getMediosContactoPersona(pfDPyDyMC);
			
			componentesExternosBusiness.getDomicilioFiscalPersona(pfDPyDyMC);
			componentesExternosBusiness.getMediosContactoFiscalesPersona(pfDPyDyMC);

			try {
				pmIMSS.setSituacionesSAT(this.situacionSATServiceBusiness
						.obtenerSituacionesPersona(pmIMSS, true));
			} catch (SituacionSATNoValidaException e) {
				this.log.warn(e);
			}

			/*
			 * Asignamos el documento probatorio a partir de la lista de
			 * documentos registrados para la persona
			 */
			if (pfDPyDyMC.getDocumentosProbatorios() != null && 
					pfDPyDyMC.getDocumentosProbatorios().size() > 0) {
				
				Nacimiento actaNacimiento = null;

				for (DocumentoProbatorio docProbatorio : pfDPyDyMC.getDocumentosProbatorios()) {
					if (docProbatorio instanceof Nacimiento) {
						actaNacimiento = (Nacimiento) docProbatorio;
					} 
				}

				pmIMSS.setActaNacimiento(actaNacimiento);				
			}
			
			// Se recupera el domicilio particular
	    	if(pfDPyDyMC.getDomicilios() != null && 
	    			pfDPyDyMC.getDomicilios().size() > 0){
	    		pmIMSS.getDomicilios().add(0, pfDPyDyMC.getDomicilios().get(0));
	    	}
	    	
	    	// Se recuperan los medio de contacto particulares
	    	if(pfDPyDyMC.getMediosContacto() != null && pfDPyDyMC.getMediosContacto().size() > 0){
	    		pmIMSS.setMediosContacto(pfDPyDyMC.getMediosContacto());
	    	}
	    	
	    	// Se recupera el domicilio fiscal
	    	if(pfDPyDyMC.getDomicilioFiscal() != null){
	    		pmIMSS.setDomicilioFiscal(pfDPyDyMC.getDomicilioFiscal());
	    	}
	    	
	    	// Se recuperan los medio de contacto fiscales
	    	if(pfDPyDyMC.getMediosContactoFiscales() != null && !pfDPyDyMC.getMediosContactoFiscales().isEmpty()){
	    		pmIMSS.setMediosContactoFiscales(pfDPyDyMC.getMediosContactoFiscales());
				
	    		MedioContacto medioContacto = null;
	    		for (int i = 0; i < pmIMSS.getMediosContactoFiscales().size(); i++) {
	    			medioContacto = pmIMSS.getMediosContactoFiscales().get(i);
					
	    			if (medioContacto instanceof TelefonoFijo) {
						pmIMSS.setTelefonoFijoFiscalAux((TelefonoFijo)medioContacto);
					} else if (medioContacto instanceof TelefonoMovil) {
						pmIMSS.setTelefonoMovilFiscalAux((TelefonoMovil)medioContacto);
					} else if (medioContacto instanceof CorreoElectronico) {
						pmIMSS.setCorreoElectronicoFiscalAux((CorreoElectronico)medioContacto);
					}
				}
	    	}
		}
		return pmIMSS;
	}		

	/**
	 * Este metodo busca una persona moral en el IMSS, sus respectivos
	 * documentos probatorios, los domicilios y medios de contacto (tanto
	 * particulares como fiscales); finalmente los concentra en el mismo objeto
	 * 
	 * @param idPersona
	 * @return
	 */
	public Moral buscarPersonaMoralyDPyDyMCEnIMSS(Long idPersona) {

		log.debug("::: Buscando la persona Moral en BDTU, ServiciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS - idPM: " + idPersona);
		// Buscamos primero a la persona moral en el IMSS
		Moral pmIMSS = new Moral();
		pmIMSS = personaMoralBusiness.getPersonaMoral(idPersona);

		if (pmIMSS != null) {
			/* Despues buscamos sus respectivos documentos probatorios y domicilios, 
			   tanto particulares como fiscales */
			Moral pfDPyDyMC = new Moral();
			pfDPyDyMC.setIdPersona(idPersona);
			pfDPyDyMC.setCveMoral(pmIMSS.getIdPersona());
			pmIMSS.setCveMoral(pmIMSS.getIdPersona());
			
			componentesExternosBusiness.getDocumentosProbatoriosPersona(pfDPyDyMC);
			componentesExternosBusiness.getDomiciliosPersona(pfDPyDyMC);
			componentesExternosBusiness.getMediosContactoPersona(pfDPyDyMC);
			
			componentesExternosBusiness.getDomicilioFiscalPersona(pfDPyDyMC);
			componentesExternosBusiness.getMediosContactoFiscalesPersona(pfDPyDyMC);

			try {
				pmIMSS.setSituacionesSAT(this.situacionSATServiceBusiness
						.obtenerSituacionesPersona(pmIMSS, true));
			} catch (SituacionSATNoValidaException e) {
				this.log.warn(e);
			}

			/*
			 * Asignamos el documento probatorio a partir de la lista de
			 * documentos registrados para la persona
			 */
			if (pfDPyDyMC.getDocumentosProbatorios() != null && 
					pfDPyDyMC.getDocumentosProbatorios().size() > 0) {
				
				Nacimiento actaNacimiento = null;

				for (DocumentoProbatorio docProbatorio : pfDPyDyMC.getDocumentosProbatorios()) {
					if (docProbatorio instanceof Nacimiento) {
						actaNacimiento = (Nacimiento) docProbatorio;
					} 
				}

				pmIMSS.setActaNacimiento(actaNacimiento);				
			}
			
			// Se recupera el domicilio particular
	    	if(pfDPyDyMC.getDomicilios() != null && 
	    			pfDPyDyMC.getDomicilios().size() > 0){
	    		pmIMSS.getDomicilios().add(0, pfDPyDyMC.getDomicilios().get(0));
	    	}
	    	
	    	// Se recuperan los medio de contacto particulares
	    	if(pfDPyDyMC.getMediosContacto() != null && pfDPyDyMC.getMediosContacto().size() > 0){
	    		pmIMSS.setMediosContacto(pfDPyDyMC.getMediosContacto());
	    	}
	    	
	    	// Se recupera el domicilio fiscal
	    	if(pfDPyDyMC.getDomicilioFiscal() != null){
	    		pmIMSS.setDomicilioFiscal(pfDPyDyMC.getDomicilioFiscal());
	    	}
	    	
	    	// Se recuperan los medio de contacto fiscales
	    	if(pfDPyDyMC.getMediosContactoFiscales() != null && !pfDPyDyMC.getMediosContactoFiscales().isEmpty()){
	    		pmIMSS.setMediosContactoFiscales(pfDPyDyMC.getMediosContactoFiscales());
				
	    		MedioContacto medioContacto = null;
	    		for (int i = 0; i < pmIMSS.getMediosContactoFiscales().size(); i++) {
	    			medioContacto = pmIMSS.getMediosContactoFiscales().get(i);
					
	    			if (medioContacto instanceof TelefonoFijo) {
						pmIMSS.setTelefonoFijoFiscalAux((TelefonoFijo)medioContacto);
					} else if (medioContacto instanceof TelefonoMovil) {
						pmIMSS.setTelefonoMovilFiscalAux((TelefonoMovil)medioContacto);
					} else if (medioContacto instanceof CorreoElectronico) {
						pmIMSS.setCorreoElectronicoFiscalAux((CorreoElectronico)medioContacto);
					}
				}
	    	}
		}

		return pmIMSS;
	}
	
	@Override
	public Fisica buscarPersonaFisicaParaModificacionManual(Long idPersona)
			throws PersonaFisicaNoEncontradaException {

		// Buscamos primero a la persona fisica en el IMSS
		Fisica pfIMSS = new Fisica();
		pfIMSS = personaBusiness.getPersonaFisica(idPersona);

		if (pfIMSS != null) {
			
			Fisica pfDPyDyMC = new Fisica();
			pfDPyDyMC.setIdPersona(idPersona);

			// Se busca el id de la persona fisica
			Long cveFisica = this.personaFisicaServiceBusiness.obtenerIDPersonaFisica(idPersona);
			pfIMSS.setCveFisica(cveFisica);
			pfDPyDyMC.setCveFisica(cveFisica);

			componentesExternosBusiness.getDomicilioFiscalPersona(pfDPyDyMC);

			// Se recupera el domicilio fiscal
			if (pfDPyDyMC.getDomicilioFiscal() != null) {
				pfIMSS.setDomicilioFiscal(pfDPyDyMC.getDomicilioFiscal());
			}
		}

		return pfIMSS;

	}
	
	@Override
	public Moral buscarPersonaMoralParaModificacionManual(Long idPersona) {
		log.debug("::: Buscando la persona Moral en ServiciosPersonaBusiness.buscarPersonaMoralParaModificacionManual");
		// Buscamos primero a la persona moral en el IMSS
		Moral pmIMSS = new Moral();
		pmIMSS = personaMoralBusiness.getPersonaMoral(idPersona);

		if (pmIMSS != null) {
			
			Moral pfDPyDyMC = new Moral();
			pfDPyDyMC.setIdPersona(idPersona);
			pfDPyDyMC.setCveMoral(pmIMSS.getIdPersona());
			pmIMSS.setCveMoral(pmIMSS.getIdPersona());
			
			componentesExternosBusiness.getDomicilioFiscalPersona(pfDPyDyMC);
    	
	    	// Se recupera el domicilio fiscal
	    	if(pfDPyDyMC.getDomicilioFiscal() != null){
	    		pmIMSS.setDomicilioFiscal(pfDPyDyMC.getDomicilioFiscal());
	    	}
		}

		return pmIMSS;
	}

	@Override
	public boolean validarExistenciaCorreoElectronicoPersonaFisica(
			Long idPersona) {
		// Datos de persona
		Persona personaFisica = new Persona();
		personaFisica.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		personaFisica.setTipoPersona(tipoPersona);

		return mediosContactoServiceBusiness
				.validarExistenciaCorreoElectronicoPersona(personaFisica);
	}

	@Override
	public String getCorreoRegistro(Long cveIdPerson) {
		// TODO Auto-generated method stub
		String corre="";
		DitPerPortalCiudadano persona=usuarioPortalEntityLocal.recuperaPersona(cveIdPerson);
		if(persona!=null){
			return persona.getDescCorreoElectronico();
		}
		
		return corre;
	}
	
}
