/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Jonathan Sanchez Montiel
 *  @Proyecto: delta
 *  @Archivo: ValidaClasificacionServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha: 02/09/2013
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.exception.clasificacion.GCESujetoObligadoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ValidaClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.FraccionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.persona.TipoPersona;


@Stateless(name = "validaClasificacionServiceBusiness", mappedName = "validaClasificacionServiceBusiness")
public class ValidaClasificacionServiceBusiness extends AbstractServiceBusiness
		implements ValidaClasificacionServiceBusinessRemote {

	@EJB
	private RuleServiceBusinessRemote ruleServiceBusiness;

	@EJB
	private SolicitudBusinessRemote solicitudBusiness;

	@EJB
	SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	@EJB
	private ClasificacionServiceBusinessRemote clasificacionService;


	@Override
	public Clasificacion validaClasificacionGP(Clasificacion clasificacion, String regPat, Long idSolicitud)
			throws GestionPatronalBusinessException {
	
		try {
			
			SujetoObligado sujetoObligado = new SujetoObligado();
			sujetoObligado.setNumeroRegistroPatronal(regPat);
			sujetoObligado = sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);

			solicitud = obtenerSolicitud(solicitud);
			
			sujetoObligado.setNumeroRegistroPatronal(regPat);
			clasificacion.setSujetoObligado(sujetoObligado);
			
			Long idTipoTramite = solicitud.getTipoSolicitud().getIdTipoSolicitud();
			
			//return ruleServiceBusiness.obtenerClasificacionEquivalenteValida(clasificacion, idTipoTramite.intValue());
			return obtenerClasificacionEquivalenteValida(clasificacion, idTipoTramite.intValue());
			
		} catch(GestionPatronalBusinessException e){
			throw e;
		} catch(Exception e){
			e.printStackTrace();
			throw new GestionPatronalBusinessException();
		}
	}
	
	private Clasificacion obtenerClasificacionEquivalenteValida(
			Clasificacion inputObject, Integer idTipoTramite)
			throws GestionPatronalBusinessException {
		inputObject=clasificacionService.obtenerClasificacionEquivalente(inputObject);
		inputObject=validaReglasClasificacion(inputObject, idTipoTramite);
		return inputObject;
	}
	
	/**
	 * Valida reglas de modalidad y clasificación por municipio
	 * @param inputObject
	 * @throws GestionPatronalBusinessException
	 */
	private Clasificacion validaReglasClasificacion(Clasificacion inputObject, Integer idTipoTramite) throws GestionPatronalBusinessException{
		boolean validarActividadPorMunicipio=true;
		Long idPersona = null;
		Long idTipoPersona = null;
		Long idMunicipioIMSS = null;
		String rfc =null;
		String nrp=null;
		Long idPatronSujetoObligado = inputObject.getSujetoObligado()!=null ? inputObject.getSujetoObligado().getCveIdSujetoObligado() : null;//Este dato solo esta presenta en una modificación de SRT o centro de trabajo
		if(inputObject.getSujetoObligado()!=null && 
				inputObject.getSujetoObligado().getMunicipioIMSS()!=null && 
				StringUtils.isNotBlank(inputObject.getSujetoObligado().getMunicipioIMSS().getIdMunicipio())){
			idMunicipioIMSS = Long.valueOf(inputObject.getSujetoObligado().getMunicipioIMSS().getIdMunicipio());
			nrp = inputObject.getSujetoObligado().getNumeroRegistroPatronal();
			if(inputObject.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				idPersona = inputObject.getSujetoObligado().getFisica().getCveFisica();
				idTipoPersona = TipoPersonaFiscal.FISICA.getCodigo().longValue();
				rfc = inputObject.getSujetoObligado().getFisica().getRfc();
			}else{
				idPersona = inputObject.getSujetoObligado().getMoral().getIdPersona();
				idTipoPersona = TipoPersonaFiscal.MORAL.getCodigo().longValue();
				rfc = inputObject.getSujetoObligado().getMoral().getRfc();
			}
		}else{
			log.error("No se tiene municipio asignado no se validara la regla de municipio");
			validarActividadPorMunicipio=false;
		}
		
		if(inputObject!=null && inputObject.getFraccion()!=null && inputObject.getFraccion().getId()!=null){
			Long idFraccion = inputObject.getFraccion().getId();
			log.info("Se validara la regla fraccion municipio");
			log.info("idPersona: ["+idPersona+"]");
			log.info("idTipoPersona: ["+idTipoPersona+"]");
			log.info("idMunicipio: ["+idMunicipioIMSS+"]");
			log.info("idFraccion: ["+idFraccion+"]");
			log.info("idRegistroPatronal: ["+idPatronSujetoObligado+"]");
			
			if(inputObject.getSujetoObligado()!=null && inputObject.getSujetoObligado().getCveIdSujetoObligado()!=null){
				log.info("se valida la regla de modalidad y se calcula la prima de pago");
				
				StringBuffer fraccionCompleta = new StringBuffer().append(
						inputObject.getFraccion().getGrupo().getDivision().getNumDivision()
						).append(inputObject.getFraccion().getGrupo().getNumGrupo()
						).append(inputObject.getFraccion().getNumFraccion());
				
				log.info("fraccionCompleta a validar: " + fraccionCompleta.toString());
				
				//Se agrega modificacion para no validar la fraccion 11-AGRICULTURA
				if(!fraccionCompleta.toString().equals(FraccionEnum.AGRICULTURA.getCodigo())){
					ruleServiceBusiness.validarModalidad(inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);
				}else{
					log.info(":::: La fraccion enviada es AGRICULTURA no se valida la modalidad");
				}
				
				BigDecimal nuevaAsignada = ruleServiceBusiness.calcularPrima(idTipoTramite, inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);
				inputObject.setPrimaSRTActual(nuevaAsignada);
			}
			
			if(validarActividadPorMunicipio){
				ruleServiceBusiness.validarClasificacionPorPatronYMunicipio(rfc, idTipoPersona, idMunicipioIMSS, idFraccion, idPatronSujetoObligado);
			}
			
			ruleServiceBusiness.validarRPC_AP_MOD_MAC(rfc, inputObject.getFraccion().getClase().getClave(), nrp);
		}
		
		return inputObject;
	}
	
	/**
	 * Obtiene la información de la solicitud enviada como parámetro; en caso de
	 * que la solicitudno exista lanza una excepción de negocio
	 * 
	 * @param solicitudParam
	 * @return solcitud
	 * @throws GCESujetoObligadoException
	 */
	private Solicitud obtenerSolicitud(final Solicitud solicitudParam)
			throws GCESujetoObligadoException {
		Solicitud solicitud = null;
		try {
			solicitud = solicitudBusiness.consultar(solicitudParam);
			if (null == solicitud) {
				throw new GCESujetoObligadoException("No existe la solicitud",
						602);
			}
		} catch (final SolicitudNoEncontradaException e) {
			log.error(e.getMessage(), e);
			throw new GCESujetoObligadoException("No existe la solicitud", 602);
		}
		return solicitud;
	}
	

}
