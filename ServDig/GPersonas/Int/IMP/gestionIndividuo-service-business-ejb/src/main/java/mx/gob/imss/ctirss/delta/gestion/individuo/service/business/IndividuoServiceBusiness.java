package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.entity.IndividuoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Project: gestionIndividuo-service-business-ejb
 * IndividuoServiceBusiness.java
 * @author Hugo Armando Martinez Chamonica
 * 19/07/2012 09:52:50
 */
@Stateless(name="individuoServiceBusiness" ,mappedName="individuoServiceBusiness")
public class IndividuoServiceBusiness implements IndividuoServiceBusinessRemote {
	
	@EJB
	IndividuoServiceEntityLocal entity;
	@EJB
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private PersonaMoralBusinessRemote moralBusiness;
	@EJB
	private ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaBusiness;

	@Override
	public Fisica consultarDatosBasicosPersonaFisica(Fisica persona) {
		return entity.buscarPersonaFisicaPorIdentificador(persona);
	}
	
	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public Moral consultarDatosBasicosPersonaMoral(Moral persona) {
		return entity.buscarPersonaMoralPorIdentificador(persona);
	}

	@Override
	public Fisica consultarPersonaFisicaIMSSPorRFC(Persona persona) throws PersonasNoLocalizadasException, 
	ClienteWebserviceRenapoCurpException, ClienteWebserviceSatRfcException, ErrorComparacionDatosRENAPOException, 
	ErrorComparacionDatosSATException, CURPNoLocalizadoEnEntidadExternaException, RFCNoLocalizadoEnEntidadExternaException, 
	DiferenciasRENAPOContraSAT, ErrorValidacionDatosConsultaEnEntidaExternaException, PersonaSinCalificacionesException {
		List<Fisica> personaEncontradas = personaBusiness.buscarPersonaFisicaPorRfcEnImss(persona.getRfc());
		if(personaEncontradas==null || (personaEncontradas!=null && personaEncontradas.size()==0))
			throw new PersonasNoLocalizadasException();
		Fisica candidata = null;
		Fisica fisicaConCurp = obtenerPersonaMasActualConCURP(personaEncontradas);
		if(fisicaConCurp!=null)
			candidata = consultaPersonaBusiness.getPersonaByCurpImssEntidadesExternas(fisicaConCurp);
		else
			candidata = obtenerPrimerPersonaFisicaCalificadaPorSAT(personaEncontradas);
		
		if(candidata==null)
			candidata = obtenerPrimerPersonaFisica(personaEncontradas);
		
		return candidata;
	}

	@Override
	public Moral consultarPersonaMoralIMSSPorRFC(Persona persona) throws PersonasNoLocalizadasException{
		List<Moral> personaEncontradas = moralBusiness.buscarPersonaMoralPorRfcEnImss(persona.getRfc());
		log.error("Personas morales encontradas...." + personaEncontradas);
		if(personaEncontradas==null || (personaEncontradas!=null && personaEncontradas.size()==0))
			throw new PersonasNoLocalizadasException();
		Moral candidata = obtenerPrimerPersonaMoralCalificadaPorSAT(personaEncontradas); 
		log.error("Persona moral obtenida por calificacion del SAT...." + candidata);
		if(candidata==null)
			candidata = obtenerPrimerPersonaMoral(personaEncontradas);
		log.error("Al no econtrar persona calificada por el SAT se obtiene por la fecha mas actual...." + candidata);
		return candidata;
	}
	
	@Override
	public Moral consultarPersonaMoralIMSSPorRFC_AP(Persona persona) throws PersonasNoLocalizadasException{
		List<Moral> personaEncontradas = moralBusiness.buscarPersonaMoralPorRfcEnImss_AP(persona.getRfc());
		log.error("Personas morales encontradas...." + personaEncontradas);
		if(personaEncontradas==null || (personaEncontradas!=null && personaEncontradas.size()==0))
			throw new PersonasNoLocalizadasException();
		Moral candidata = obtenerPrimerPersonaMoralCalificadaPorSAT(personaEncontradas); 
		log.error("Persona moral obtenida por calificacion del SAT...." + candidata);
		if(candidata==null)
			candidata = obtenerPrimerPersonaMoral(personaEncontradas);
		log.error("Al no econtrar persona calificada por el SAT se obtiene por la fecha mas actual...." + candidata);
		return candidata;
	}
	
	private Fisica obtenerPersonaMasActualConCURP(List<Fisica> fisicas){
		for(Fisica fisica : fisicas){
			if(!StringUtils.isEmpty(fisica.getCurp()) )
				return fisica;
		}
		
		return null;
	}
	
	private Fisica obtenerPrimerPersonaFisicaCalificadaPorSAT(List<Fisica> fisicas){
		List<Fisica> calificadasSAT = obtenerPersonasFisicasCalificadasPorSAT(fisicas);
		return obtenerPrimerPersonaFisica(calificadasSAT);
	}
	
	
	
	private Map<Long,Fisica> obtenerListaFisicaOrdenadaPorIdPersona(List<Fisica> fisicas){
		Map<Long,Fisica> listaOrdenada = new TreeMap<Long, Fisica>();
		for(Fisica fisica:fisicas){
			listaOrdenada.put(fisica.getFechaModificacion().getTime(), fisica);
		}
		
		return listaOrdenada;
	}
	
	private List<Fisica> obtenerPersonasFisicasCalificadasPorSAT(List<Fisica> fisicas){
		List<Fisica> fisicasCalificadasSAT = new ArrayList<Fisica>();
		for(Fisica fisica : fisicas)
			if(fisica.getPersonaCalificaciones()!=null)
				for(PersonaCalificacion calif : fisica.getPersonaCalificaciones())
					if(calif.getCalificacion()!=null 
					&& calif.getCalificacion().getIdCalificacion().equals(
							CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()))
						fisicasCalificadasSAT.add(fisica);
		return fisicasCalificadasSAT;
	}
	
	private Moral obtenerPrimerPersonaMoralCalificadaPorSAT(List<Moral> morales){
		List<Moral> calificadasSAT = obtenerPersonasMoralesCalificadasPorSAT(morales);
		log.error("Datos obtenidos al pasar pot el metodo de obtener personas morales calificadas por el SAT...." + calificadasSAT);
		return obtenerPrimerPersonaMoral(calificadasSAT);
	}
	
	
	private Map<Long,Moral> obtenerListaMoralOrdenadaPorIdPersona(List<Moral> morales){
		Map<Long,Moral> listaOrdenada = new TreeMap<Long, Moral>();
		for(Moral moral:morales){
			listaOrdenada.put(moral.getFechaModificacion().getTime(), moral);
		}
		
		return listaOrdenada;
	}
	
	private List<Moral> obtenerPersonasMoralesCalificadasPorSAT(List<Moral> morales){
		List<Moral> moralesCalificadasSAT = new ArrayList<Moral>();
		for(Moral moral : morales)
			if(moral.getPersonaCalificaciones()!=null)
				for(PersonaCalificacion calif : moral.getPersonaCalificaciones())
					if(calif.getCalificacion()!=null 
					&& calif.getCalificacion().getIdCalificacion().equals(
							CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()))
						moralesCalificadasSAT.add(moral);
		log.error("Datos encontradon al consultar los calificados por el SAT..." + moralesCalificadasSAT);
		return moralesCalificadasSAT;
	}
	
	private Fisica obtenerPrimerPersonaFisica(List<Fisica> fisicas){
		if(fisicas.size()>1){
			Map<Long, Fisica> listaOrdenadaCalificadas = obtenerListaFisicaOrdenadaPorIdPersona(fisicas);
			Set<Long> ids = listaOrdenadaCalificadas.keySet();
			return listaOrdenadaCalificadas.get(ids.iterator().next());
		}else if(fisicas.size()>0){
			return fisicas.get(0);
		}
		
		return null;
	}
	
	private Moral obtenerPrimerPersonaMoral(List<Moral> morales){
		if(morales.size()>1){
			Map<Long, Moral> listaOrdenadaCalificadas = obtenerListaMoralOrdenadaPorIdPersona(morales);
			log.error("lista de personas morales ordenadas por id persona" + listaOrdenadaCalificadas);
			Set<Long> ids = listaOrdenadaCalificadas.keySet();
			log.error("lista ordenada por id..." + ids);
			//Se agrega bloque de c�digo para obtener a la persona con fecha de actualizaci�n m�s reciente
			Long fechaUltimaActualizacion=0l;
			for(Long timeInMillis:ids){
				if(timeInMillis>fechaUltimaActualizacion)
					fechaUltimaActualizacion=timeInMillis;
			}
			return listaOrdenadaCalificadas.get(fechaUltimaActualizacion);
		}else if(morales.size()>0){
			log.error("Si solo es un registro de persona moral entonces es:" + morales);
			return morales.get(0);
		}
		return null;
	}
	
	@Override
	public void revisaSituacionContribuyenteSAT(List<SituacionSAT> situacionesSAT, String rfc, long tipoPersona)
			throws ErrorComparacionDatosSATException {

		boolean sitCont = false;
		boolean sitDom = false;
		boolean sitContDom = false;
		
		if(tipoPersona == TipoPersonaEnum.FISICA.getId()) {
			log.debug("::: Aplicando validacion SAT a situacion del contribuyente persona FISICA, " + rfc);
		}else {
			log.debug("::: Aplicando validacion SAT a situacion del contribuyente persona MORAL, " + rfc);			
		}
		log.debug(":: Revisando situaciones SAT, " + rfc);
		if(situacionesSAT.size() == 3) {
			for (Iterator<SituacionSAT> iterator = situacionesSAT.iterator(); iterator.hasNext();) {
				SituacionSAT sitSAT = iterator.next();
				log.debug("-- " + sitSAT.getCveSituacionSAT() + " : " + sitSAT.getDescripcion());
				// A - ACTIVO, 20 - Domicilio Localizado, 30 - Contribuyente Localizado 
				if(sitSAT.getIdSituacionSAT().intValue() == 1 && sitSAT.getCveSituacionSAT().trim().equals("A")) {
					sitCont = true;
				}else if(sitSAT.getIdSituacionSAT().intValue() == 2 && sitSAT.getCveSituacionSAT().trim().equals("20")) {
					sitDom = true;
				}else if(sitSAT.getIdSituacionSAT().intValue() == 3 && sitSAT.getCveSituacionSAT().trim().equals("30")) {
					sitContDom = true;
				}
			}				
		}else {
			log.debug("::: No se encontraron las situaciones SAT suficientes para validar, " + rfc);
			ErrorComparacionDatosSATException ex = new ErrorComparacionDatosSATException("No se encontraron las situaciones SAT suficientes para validar");
			ex.setCodigo(0); //
			throw ex;
		}
		
		if(!sitCont) {
			log.debug("::: Situacion del contribuyente no valida, " + rfc);
			ErrorComparacionDatosSATException ex = new ErrorComparacionDatosSATException("La situaci\u00F3n fiscal del contribuyente no le permite ser candidato a un Alta patronal");
			ex.setCodigo(1); 
			throw ex;
		} else if(!sitDom){
			log.debug("::: Situacion del domicilio del contribuyente no valida, " + rfc);
			ErrorComparacionDatosSATException ex = new ErrorComparacionDatosSATException("Su domicilio o su situaci\u00F3n en \u00E9l no est\u00E1n verificados ante el SAT, por lo que no le permite ser candidato a un Alta Patronal");
			ex.setCodigo(2); 
			throw ex;
		} else if(!sitContDom){
			log.debug("::: Situacion del contribuyente en su domicilio no valida, " + rfc);
			ErrorComparacionDatosSATException ex = new ErrorComparacionDatosSATException("Su domicilio o su situaci\u00F3n en \u00E9l no est\u00E1n verificados ante el SAT, por lo que no le permite ser candidato a un Alta Patronal");
			ex.setCodigo(3); 
			throw ex;
		}else {
			if(tipoPersona == TipoPersonaEnum.FISICA.getId()) {
				log.debug("::: La situacion actual SAT del patron PF con el SAT es correcta " + rfc);
			}else {
				log.debug("::: La situacion actual SAT del patron PM es correcta, se permite el alta patronal, " + rfc);
			}
		}
	}	
	
	@Override
	public void revisaSituacionContribuyenteRENAPO(String rfc, String curpSAT, Fisica personaEntidadREN)
			throws ErrorComparacionDatosSATException {
		//Validamos los datos de RENAPO para PF
		boolean enc = false;
		log.debug("::: Validamos la situacion en RENAPO para la PF, " + personaEntidadREN.getCurp() + "-" + rfc);
		log.debug(":: Clave RENAPO: " + personaEntidadREN.getCveEstatusRenapo() + "-" + personaEntidadREN.getEstatusRenapo());
		if(personaEntidadREN.getCveEstatusRenapo().trim().equals("BD")) {
			log.debug("::: CURP en RENAPO no valida, estatus en baja, " + personaEntidadREN.getCurp() + "-" + rfc);
			ErrorComparacionDatosSATException ex = new ErrorComparacionDatosSATException("La situaci\u00F3n actual de la CURP ante RENAPO no le permite ser candidato a un tr\u00E1mite de Alta Patronal. Tampoco podr\u00E1 finalizarlo en subdelegaci\u00F3n. Si considera que se trata de un error, deber\u00E1 acudir ante RENAPO para revisar su situaci\u00F3n");				
			ex.setCodigo(4);
			throw ex;
		}else {
			//validamos si la CURP del SAT conincide con la CURP de RENAPO
			log.debug(":: validamos si la CURP del SAT coincide con la CURP de RENAPO, curpSAT - " + curpSAT + ", curpRENAPO - " + personaEntidadREN.getCurp());
			if(!curpSAT.equals(personaEntidadREN.getCurp())) {
				List<String> curps = personaEntidadREN.getCurpsHistoricas();
				for (Iterator<String> iterator = curps.iterator(); iterator.hasNext();) {
					String curpHist =  iterator.next();
					log.debug(":: CURP historica " + curpHist);
					if(curpHist.equals(curpSAT)) {
						log.debug(":: La CURP historica "+curpHist+" coincide con la CURP SAT, " + personaEntidadREN.getCurp() + "-" + rfc);
						enc = true;
						break;
					}
				}
			}else {
				log.debug("::: La CURP SAT coincide con la CURP en RENAPO, " + personaEntidadREN.getCurp() + "-" + rfc);
				enc = true;
			}
		}
		
		if(!enc) {
			log.debug("::: La CURP SAT no coincide con la CURP en RENAPO, " + personaEntidadREN.getCurp() + "-" + rfc);
			ErrorComparacionDatosSATException ex = new ErrorComparacionDatosSATException("Los datos de su CURP ante RENAPO difieren de su registro ante el SAT y no le permite ser candidato a un tr\u00E1mite de Alta Patronal. Tampoco podr\u00E1 finalizarlo en subdelegaci\u00F3n. Deber\u00E1 acudir al SAT y RENAPO a revisar su situaci\u00F3n para poder ser elegible a concluir este tr\u00E1mite ante el IMSS");				
			ex.setCodigo(5);
			throw ex;
		}else {
			log.debug("::: La situacion actual en RENAPO del patron PF es correcta, " + personaEntidadREN.getCurp() + "-" + rfc);
		}			
	}		
	
}
