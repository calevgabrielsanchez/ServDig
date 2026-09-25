package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.AdeudoFiscal;
import mx.gob.imss.ctirss.delta.cobranza.modelo.DatosValidacionCartaNoAdeudoWrapper;
import mx.gob.imss.ctirss.delta.cobranza.service.entity.CartaNoAdeudoEntityLocal;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CartaNoAdeudoServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.CartaNoAdeudoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GenerarCodigoQRServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.enums.RespuestaOpinion32DEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite32D;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

@Stateless(name = "cartaNoAdeudoService", mappedName = "cartaNoAdeudoService")
public class CartaNoAdeudoService extends AbstractService 
	implements CartaNoAdeudoServiceRemote{

	@EJB
	private SolicitudBusinessRemote solicitudBusiness;
	@EJB
	private FirmaDigitalBusinessRemote firmaDigitalBusiness;
	@EJB
	private CartaNoAdeudoServiceUtilityLocal cartaNoAdeudoServiceUtility;
	@EJB
	private GenerarCodigoQRServiceBusinessRemote generarCodigoQRServiceBusiness;
	@EJB
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@EJB
	private CartaNoAdeudoEntityLocal cartaNoAdeudoEntity;
	
	@Override
	public Map<String, Object> generarSolicitudCartaNoAdeudo(Persona persona, String usuario, Long idOrigen)
			throws EstadoAdeudoException {		
		
		Map<String, Object> mapaSolicitud = new HashMap<String, Object>();
		Date fechaInicio = new Date();
		Map<String, Object> datosReporte = null;
		DatosValidacionCartaNoAdeudoWrapper wrapper = new DatosValidacionCartaNoAdeudoWrapper();
		
		try {
			datosReporte = this.getDatosReporte32D(persona, true, usuario, idOrigen);
			Persona personaFM = (Persona) datosReporte.get("personaFM");
			wrapper = (DatosValidacionCartaNoAdeudoWrapper) datosReporte.get("datos32D");
			
			//Generar solicitud
			Solicitud solicitud = cartaNoAdeudoServiceUtility
					.prepararSolicitudCartaNoAdeudo(personaFM, usuario,
							wrapper, fechaInicio, idOrigen);
			solicitud = solicitudBusiness.crear(solicitud);
			
			//Procesar firma
			FirmaElectronica firmaElectronica = procesarFirma(solicitud, personaFM, usuario);

			//Generar template carta de no adeudo
			byte[] documento = generarCartaNoAdeudo(personaFM,
					firmaElectronica, solicitud, wrapper,usuario);
			
			//Setear valores de retorno
			mapaSolicitud.put("solicitud", solicitud);
			mapaSolicitud.put("firma", firmaElectronica);
			mapaSolicitud.put("documento", documento);			
		} catch (SolicitudNoValidaException e) {
			log.error("Error al guardar la solicitud", e);
			throw new EstadoAdeudoException("No fue posible generar la solicitud de carta de no adeudo.");
		}		
		return mapaSolicitud;
	} 
	
	@Override
	public RespuestaOpinion32DEnum getOpinionRFC(Persona persona,
			String usuario, Long idOrigen) throws EstadoAdeudoException {
		
		Map<String, Object> result = this.getDatosReporte32D(persona, false,usuario, idOrigen);
		DatosValidacionCartaNoAdeudoWrapper wrapper = (DatosValidacionCartaNoAdeudoWrapper) result.get("datos32D");
		
		return wrapper.getRespuestaOpinion();
	}
	
	@Override
	public void validarPersonaMismoRFC(Persona persona) throws EstadoAdeudoException{
		int totalRegistros = personaBusiness.totalRegistroPersonaFMPorRFC(persona);
		if(totalRegistros > 1){
			throw new EstadoAdeudoException("Existen problemas con la informaci?n de su estado de cuenta, " +
				"por favor acuda a su subdelegaci?n para realizar este tr?mite.");
		}
		
	}
	
	private FirmaElectronica procesarFirma(Solicitud solicitud, Persona persona, String usuario)
			throws EstadoAdeudoException{

		FirmaElectronica firmaElectronica = null;

		try {

			String cadenaOriginal = cartaNoAdeudoServiceUtility
				.generarCadenaOriginal(solicitud, persona, usuario);
			RespuestaFirmadoSimple selloDigital = firmaDigitalBusiness
				.getSelloDigital(cadenaOriginal, null, persona.getRfc());
			if (selloDigital != null && (StringUtils.isNotBlank(selloDigital.getSello())) ) {
				firmaElectronica = cartaNoAdeudoServiceUtility
					.generarFirmaElectronica(selloDigital, cadenaOriginal);
				if(firmaElectronica != null)
					firmaDigitalBusiness.insertarSolicitudFirmaDigitalThrowError(solicitud, firmaElectronica);
				
			}else{
				throw new EstadoAdeudoException("Ocurri&oacute; un error al intentar sellar el documento.");
			}
		
		}catch (Exception e) {
			log.error("Ocurrio un error al procesarFirma: "+e.getMessage());
			throw new EstadoAdeudoException("Ocurri&oacute; un error al intentar sellar el documento.");
		}
		return firmaElectronica;

	}
	
	private byte[] generarCartaNoAdeudo(Persona persona,
			FirmaElectronica firmaElectronica, Solicitud solicitud,
			DatosValidacionCartaNoAdeudoWrapper wrapper, String usuario) throws EstadoAdeudoException {
		
		try {
		
			String nombreDocumento = "CartaNoAdeudo_" + persona.getRfc() + ".pdf";
			
			BufferedImage imagenCodeQR = generarCodigoQRServiceBusiness
				.generadorCodigoQrCadena(firmaElectronica.getCadenaOriginal(), 500, 500);
		
			Boolean juicioEnProceso = cartaNoAdeudoEntity.validaJuicioEnProceso(persona.getRfc());
		    Boolean auditoriaEnProceso = cartaNoAdeudoEntity.validaAuditoriaEnProceso(persona.getRfc());
		    Boolean convenioEnProceso = cartaNoAdeudoEntity.validaConvenioEnProceso(persona.getRfc());
		
			byte[] documento = cartaNoAdeudoServiceUtility.prepararTemplateCartaNoAdeudo(persona, solicitud, 
					firmaElectronica, imagenCodeQR, wrapper, usuario, juicioEnProceso, auditoriaEnProceso,convenioEnProceso);
			
			firmaDigitalBusiness.guardarArchivoFirmadoThrowError(firmaElectronica.getSecuenciaNotaria(), 
				nombreDocumento, documento);
			
			return documento;
			
		}catch (Exception e) {
			log.error("Error: "+e.getMessage());
			throw new EstadoAdeudoException(e.getMessage());
		}
	}
	
	private List<SujetoObligado> obtenerRegistrosPatronalesPorRFC(
			Persona persona) throws EstadoAdeudoException {
		
		List<SujetoObligado> listaSujetosObligados = this.cartaNoAdeudoEntity
				.obtenerPatronesParaCartaNoAdeudo(persona);
		
		return listaSujetosObligados;
	}
		
	private List<AdeudoFiscal> obtenerAdeudos(Persona persona, 
			List<SujetoObligado> listaSujetosObligados){
		List<AdeudoFiscal> listAdeudosTotales = new ArrayList<AdeudoFiscal>();		
		for(SujetoObligado so : listaSujetosObligados){
			List<AdeudoFiscal> listaAdeudoRP = cartaNoAdeudoEntity.obtenerAdeudosPorRP(so);
			if(!CollectionUtils.isEmpty(listaAdeudoRP)){
				listAdeudosTotales.addAll(listaAdeudoRP);
			}
		}		
		return listAdeudosTotales;
	}
	
	private Map<String, Object> getDatosReporte32D(Persona persona, Boolean buscarPersona,String usuario, Long idOrigen) throws EstadoAdeudoException {
		
		Map<String, Object> result = new HashMap<String, Object>();
		DatosValidacionCartaNoAdeudoWrapper wrapper = new DatosValidacionCartaNoAdeudoWrapper();
		
		try {
			//Recuperar persona (Fisica o Moral)
			Persona personaFM = null;
			TipoPersonaEnum tipoPersonaE = null;
			
			if( buscarPersona) {
				if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
					personaFM = personaBusiness.getPersonaFisica(persona.getIdPersona());
					tipoPersonaE = TipoPersonaEnum.FISICA;
				}else{
					personaFM = personaMoralBusiness.getPersonaMoral(persona.getIdPersona());
					tipoPersonaE = TipoPersonaEnum.MORAL;
				}			
				
				if(personaFM != null) {
					personaFM.setRfc(persona.getRfc());
					TipoPersona tipoPersona = new TipoPersona();
					tipoPersona.setIdTipoPersona(tipoPersonaE.getId());
					personaFM.setTipoPersona(tipoPersona);
				}
			} else {
				personaFM = persona;
			}
			
			RespuestaOpinion32DEnum respuestaOpinion = null;
			List<SujetoObligado> patrones = obtenerRegistrosPatronalesPorRFC(personaFM);
			
			if(patrones==null || patrones.isEmpty()) {
				respuestaOpinion = RespuestaOpinion32DEnum.SIN_PATRONES; //sin opinion anexo 3
			} else {
				/* 
				 * Se recorre la lista de RPs para obtener el total de trabajadores y
				 * se genera subconjunto de patrones que est?n en huelga, patrones vigentes
				 * y patrones en baja
				 */
				int totalTrabajadores = 0;
				List<SujetoObligado> patHuelga = new ArrayList<SujetoObligado>();
				List<SujetoObligado> patVigentes = new ArrayList<SujetoObligado>();
				List<SujetoObligado> patBaja = new ArrayList<SujetoObligado>();
				
				for(SujetoObligado so : patrones) {
					
					if (so.getNumeroTrabajadores() != null 
							&& !so.getModalidad().getNumModalidad().equals("32")
							&& !so.getModalidad().getNumModalidad().equals("33")
							&& !so.getModalidad().getNumModalidad().equals("40")) {
						totalTrabajadores += so.getNumeroTrabajadores();
					}
					
					if (StringUtils.isNotBlank(so.getDescSituacionBaja())
							&& so.getDescSituacionBaja().equals(CausaBajaPatronEnum.HUELGA.getDescripcion())) {
						patHuelga.add(so);
					} else if (StringUtils.isNotBlank(so.getDescSituacionBaja())
							&& so.getDescSituacionBaja().equals(CausaBajaPatronEnum.BAJA.getDescripcion())) {
						patBaja.add(so);
					} else {
						patVigentes.add(so);
					}
				}
				
				wrapper.setPatrones(patrones);
				wrapper.setPatronesVigentes(patVigentes);
				wrapper.setPatronesHuelga(patHuelga);
				wrapper.setPatronesBaja(patBaja);
				
				this.log.debug("El RFC " + personaFM.getRfc() + " tiene " + patrones.size()
						+ " patrones asociados para validar la carta de no adeudo");
				this.log.debug("El RFC " + personaFM.getRfc() + " tiene "
						+ patVigentes.size() + " patrones vigentes");
				this.log.debug("El RFC " + personaFM.getRfc() + " tiene "
						+ patHuelga.size() + " patrones en huelga");
				this.log.debug("El RFC " + personaFM.getRfc() + " tiene "
						+ patBaja.size() + " patrones en baja");
				this.log.debug("El RFC " + personaFM.getRfc() + " tiene un total de " + totalTrabajadores
						+ " trabajadores en todos sus patrones");
			
				List<AdeudoFiscal> listaAdeudos = null;
				
				if(!patHuelga.isEmpty()) {
					/*
					 * El RFC al menos tiene un patr?n en huelga, se checa que si el
					 * RFC tiene adeudos
					 */
					listaAdeudos = obtenerAdeudos(personaFM, patrones);
					
					if (listaAdeudos.isEmpty()) {
						
						if (totalTrabajadores > 0) {
							respuestaOpinion = RespuestaOpinion32DEnum.POSITIVA; //Opinion positiva ANEXO 2 POS
						} else {
							respuestaOpinion = RespuestaOpinion32DEnum.PATRON_SIN_TRABAJADORES; //Sin opinion ANEXO 4
						}
						
					} else {
						respuestaOpinion = RespuestaOpinion32DEnum.NEGATIVA_ADEUDOS_HUELGA;   //Opinion negativa  ANEXO 1 NEG
						wrapper.setTieneAdeudos(true);
					}
				} else if (!patVigentes.isEmpty()) {
					/*
					 * El RFC al menos tiene un patr?n vigente, se checa que si el
					 * RFC tiene adeudos
					 */
					listaAdeudos = obtenerAdeudos(personaFM, patrones);
					
					if (listaAdeudos.isEmpty()) {
						if (totalTrabajadores > 0) {
							respuestaOpinion = RespuestaOpinion32DEnum.POSITIVA; //Opinion positiva ANEXO 2 POS
						} else {
							respuestaOpinion = RespuestaOpinion32DEnum.PATRON_SIN_TRABAJADORES; //Sin opinion ANEXO 4
						}
					} else {
						respuestaOpinion = RespuestaOpinion32DEnum.NEGATIVA_ADEUDOS; //Opinion negativa  ANEXO 1 NEG
						wrapper.setTieneAdeudos(true);
					}
				} else {
					/*
					 * El RFC tiene todos los patrones en baja, se checa que si el
					 * RFC tiene adeudos
					 */
					listaAdeudos = obtenerAdeudos(personaFM, patrones);
					
					if (listaAdeudos.isEmpty()) {
						respuestaOpinion = RespuestaOpinion32DEnum.PATRONES_BAJA_SIN_ADEUDO; //Sin opinion ANEXO 5
					} else {
						respuestaOpinion = RespuestaOpinion32DEnum.NEGATIVA_ADEUDOS_BAJA; //Opinion negativa  ANEXO 1 NEG
						wrapper.setTieneAdeudos(true);
					}
				}
				
				/*
				 * Se recorren los adeudos para generar subconjutos de acuerdo a la
				 * situaci?n y el origen
				 */
				List<AdeudoFiscal> adeudoCreditosImss = new ArrayList<AdeudoFiscal>();
				List<AdeudoFiscal> adeudoCreditosRcv = new ArrayList<AdeudoFiscal>();
				List<AdeudoFiscal> adeudoBaja251Imss = new ArrayList<AdeudoFiscal>();
				List<AdeudoFiscal> adeudoBaja251Rcv = new ArrayList<AdeudoFiscal>();
				List<AdeudoFiscal> adeudoHuelgaImss = new ArrayList<AdeudoFiscal>();
				List<AdeudoFiscal> adeudoHuelgaRcv = new ArrayList<AdeudoFiscal>();
				
				for (AdeudoFiscal adeudo : listaAdeudos) {
					if (adeudo.getSituacion().equals("Vigente")) {
						if (adeudo.getOrigen().equals("IMSS")){
							adeudoCreditosImss.add(adeudo);
						} else if (adeudo.getOrigen().equals("RCV")){
							adeudoCreditosRcv.add(adeudo);
						}
					} else if (adeudo.getSituacion().equals("B-251")) {
						if (adeudo.getOrigen().equals("IMSS")){
							adeudoBaja251Imss.add(adeudo);
						} else if (adeudo.getOrigen().equals("RCV")){
							adeudoBaja251Rcv.add(adeudo);
						}
					} else if (adeudo.getSituacion().equals("Huelga")) {
						if (adeudo.getOrigen().equals("IMSS")){
							adeudoHuelgaImss.add(adeudo);
						} else if (adeudo.getOrigen().equals("RCV")){
							adeudoHuelgaRcv.add(adeudo);
						}
					}
				}
				
				wrapper.setAdeudoCreditosImss(adeudoCreditosImss);
				wrapper.setAdeudoCreditosRcv(adeudoCreditosRcv);
				wrapper.setAdeudoBaja251Imss(adeudoBaja251Imss);
				wrapper.setAdeudoBaja251Rcv(adeudoBaja251Rcv);
				wrapper.setAdeudoHuelgaImss(adeudoHuelgaImss);
				wrapper.setAdeudoHuelgaRcv(adeudoHuelgaRcv);
				wrapper.setNumTrabajadores(totalTrabajadores);
			}
			
			this.log.debug("El RFC " + personaFM.getRfc()
					+ " obtuvo la respuesta de opinion "
					+ respuestaOpinion.getDesc());
			wrapper.setRespuestaOpinion(respuestaOpinion);
			result.put("personaFM", personaFM);
			this.log.debug("La respuesta de opinion es -> " + respuestaOpinion.getDesc()+ " id:"+respuestaOpinion.getId());
		} catch (Exception e) {
			log.error("Error al guardar la solicitud", e);
			throw new EstadoAdeudoException("No fue posible generar la solicitud de carta de no adeudo.");
		}	
		
		result.put("datos32D", wrapper);
		
		return result;
	}

	private FirmaElectronica procesarFirmaReconstruida(Solicitud solicitud, Persona persona, String usuario)
			throws EstadoAdeudoException{
		FirmaElectronica firmaElectronica = null;
		try {
			String cadenaOriginal = cartaNoAdeudoServiceUtility
					.generarCadenaOriginalReconstruida(solicitud, persona, usuario);
			RespuestaFirmadoSimple selloDigital = firmaDigitalBusiness
					.getSelloDigital(cadenaOriginal, null, persona.getRfc());
			if (selloDigital != null && (StringUtils.isNotBlank(selloDigital.getSello())) ) {
				firmaElectronica = cartaNoAdeudoServiceUtility
						.generarFirmaElectronica(selloDigital, cadenaOriginal);
				if(firmaElectronica != null)
					firmaDigitalBusiness.insertarSolicitudFirmaDigitalThrowError(solicitud, firmaElectronica);
			}else{
				throw new EstadoAdeudoException("Ocurri&oacute; un error al intentar sellar el documento.");
			}
		}catch (Exception e) {
			log.error("Ocurrio un error al procesarFirma: "+e.getMessage());
			throw new EstadoAdeudoException("Ocurri&oacute; un error al intentar sellar el documento.");
		}
		return firmaElectronica;
	}

	private byte[] reconstruirCartaNoAdeudo(Persona persona,
										FirmaElectronica firmaElectronica, Solicitud solicitud,
										DatosValidacionCartaNoAdeudoWrapper wrapper, String usuario) throws EstadoAdeudoException {

		try {

			String nombreDocumento = "CartaNoAdeudo_" + persona.getRfc() + ".pdf";

			BufferedImage imagenCodeQR = generarCodigoQRServiceBusiness
					.generadorCodigoQrCadena(firmaElectronica.getCadenaOriginal(), 500, 500);

			Boolean juicioEnProceso = false;
			Boolean auditoriaEnProceso = false;
			Boolean convenioEnProceso = false;

			byte[] documento = cartaNoAdeudoServiceUtility.reconstruirTemplateCartaNoAdeudo(persona, solicitud,
					firmaElectronica, imagenCodeQR, wrapper, usuario, juicioEnProceso, auditoriaEnProceso,convenioEnProceso);

			firmaDigitalBusiness.guardarArchivoFirmadoThrowError(firmaElectronica.getSecuenciaNotaria(),
					nombreDocumento, documento);

			return documento;

		}catch (Exception e) {
			log.error("Error: "+e.getMessage());
			throw new EstadoAdeudoException(e.getMessage());
		}
	}

	@Override
    public Map<String, Object> generarSolicitudPorFolioCartaNoAdeudo(String noFolioSolicitud, Long idOrigen)
            throws EstadoAdeudoException {

        Map<String, Object> mapaSolicitud = new HashMap<String, Object>();
        DatosValidacionCartaNoAdeudoWrapper wrapper = new DatosValidacionCartaNoAdeudoWrapper();

        try {
            Solicitud solicitud = solicitudBusiness.consultarPorFolioSolicitud(noFolioSolicitud);
			FirmaElectronica firma = firmaDigitalBusiness.getFirmaElectronica(solicitud);
			if(firma!=null){
				log.info("Firma Encontrada: "+firma.getSecuenciaNotaria());
				throw new SolicitudNoEncontradaException("No es posible reconstruir");
			}
            if (solicitud.getTipoSolicitud().getIdTipoSolicitud() != 32) {
                throw new SolicitudNoEncontradaException("Solicitud debe ser Carta No adeudo");
            }
            Tramite tramiteCartaNoAdeudo = null;
            for (Tramite t : solicitud.getTramites()) {
                if (t.getTipoTramite().getIdTipoTramite() == 116) {
                    tramiteCartaNoAdeudo = t;
                    break;
                }
            }
            Tramite32D tramite32D = null;
            if (tramiteCartaNoAdeudo instanceof Tramite32D) {
                tramite32D = (Tramite32D) tramiteCartaNoAdeudo;
            }
            Persona personaFM = tramite32D.getPersonaFM();
            String usuario = "";
            if (personaFM instanceof Fisica) {
                usuario = ((Fisica) personaFM).getCurp();
            } else {
                usuario = "XEXX010101HNEXXXA4";
            }
            FirmaElectronica firmaElectronica = procesarFirmaReconstruida(solicitud, personaFM, usuario);
            wrapper.setRespuestaOpinion(RespuestaOpinion32DEnum.obtenerEnumByName(tramite32D.getRespuestaOpinion()));

			byte[] documento = reconstruirCartaNoAdeudo(personaFM,firmaElectronica, solicitud, wrapper,usuario);

            mapaSolicitud.put("solicitud", solicitud);
            mapaSolicitud.put("firma", firmaElectronica);
            mapaSolicitud.put("documento", documento);
            mapaSolicitud.put("secNot",firmaElectronica.getSecuenciaNotaria());
			mapaSolicitud.put("rfc",personaFM.getRfc());
        } catch (SolicitudNoEncontradaException nfe) {
            log.error("Error al buscar la solicitud", nfe);
        } catch (Exception ex){
            log.error("Error al construir la carta", ex);
        }
        return mapaSolicitud;
    }
}
