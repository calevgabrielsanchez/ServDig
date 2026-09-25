package mx.gob.imss.cit.cda.web.utils;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.web.app.responsable.model.Documento;
import mx.gob.imss.cit.cda.web.app.responsable.model.DocumentosNss;
import mx.gob.imss.cit.cda.web.app.responsable.model.DomicilioParticular;
import mx.gob.imss.cit.cda.web.app.responsable.model.HistoriaLaboral;
import mx.gob.imss.cit.cda.web.app.responsable.model.InformacionRENAPO;
import mx.gob.imss.cit.cda.web.app.responsable.model.MotivoAclaracion;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSS;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.cit.cda.web.app.responsable.model.SubDelegacion;
import mx.gob.imss.cit.cda.web.app.responsable.model.TipoNSSCorreccion;
import mx.gob.imss.cit.cda.web.app.responsable.model.TipoRegularizacion;
import mx.gob.imss.cit.cda.web.app.responsable.model.TipoRegularizacionNSS;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.OrigenConsultaNssEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadSolicitudUtils {

	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;
	
	@Autowired
	@Qualifier("registroSolicitudCorreccionDatosAseguradoBusiness")
	private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;
	
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	
	@Autowired 
	@Qualifier("documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote deltaDocumentoProbatorioServiceBusiness;
	
	@Autowired
	private EstadosPantallaUtil estadosPantallaUtil;
	
	@Autowired
	private EstadosPantallaAutorizadorUtil estadosPantallaAutorizadorUtil;
	
	@Autowired
	private TipoRegularizacionUtil tipoRegularizacionUtil;
	
	@Autowired
	private TipoRegularizacionNSSUtil tipoRegularizacionNSSUtil;
	
	@Autowired
	private DeltaUtils deltaUtils;
	
	private static final String SOLICITUD_VENCIDA = " - VENCIDA";
	private final Logger log = LoggerFactory.getLogger(getClass());
	private static final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	

	public Solicitud convertSol(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol) throws DocumentoProbatorioException {

		log.debug("---CDA Ventanilla--- inicia transofrmacion de solicitud: {}", sol.getNoFolioSolicitud());
		Solicitud solicitud = new Solicitud();
		
		List<Page<NSS>> gridsNSS = new ArrayList<Page<NSS>>();
		Page<NSS> gridNSS;
		List<NSS> listNSS;
		NSS nss;
		
		List<Page<Documento>> gridsDocumentosNss = new ArrayList<Page<Documento>>();
		List<Page<DocumentosNss>> gridsDocumentosNssOrigen = new ArrayList<Page<DocumentosNss>>();
		
		List<DocumentoProbatorio> documentosProbatorios= new ArrayList<DocumentoProbatorio>();
		List<DocumentosNss> documentosNssOrigen =  new ArrayList<DocumentosNss>();
		
		TipoRegularizacion tipoRegSol = new TipoRegularizacion();
		
		for (Tramite tramitecda: sol.getTramites()){
			
			TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)tramitecda;
			
			log.debug("---CDA Ventanilla--- tramite: {}", tramite.getTramiteId());
			
			gridNSS = new Page<NSS>();
			listNSS = new ArrayList<NSS>();
			nss = new NSS();
			nss.setNss(tramite.getListaNSS().get(0));
			nss.setConvencional(Boolean.TRUE);
			
			TipoRegularizacionNSS tipoRegNSS = tipoRegularizacionNSSUtil.getTipoRegularizacionNSS(tramite);
			nss.setGrupoCorreccion(tipoRegNSS);

			
            List<Fisica> personasFuenteNSS=serviceBusiness.getAseguradoByNSSLegadosyBDTU(tramite.getListaNSS().get(0),true);
			
			if(personasFuenteNSS != null){
				log.debug("Total de fuentes {}",personasFuenteNSS.size());
			}
			
			/*El tramite principal tiene la persona renapo*/
			if (tramite.getPersonaRENAPO() != null){
				
				Fisica persona = tramite.getPersonaRENAPO();
				log.debug("---CDA Ventanilla--- NSS asociado al tramite: {}", tramite.getListaNSS().get(0));
				
				Map<String, Object> roles = registroSolicitudCorreccionDatosAseguradoBusiness.personaAutorizadaRegistroCDA(persona.getCurp(),persona.getCurpsHistoricas());
				
				log.debug("---CDA Ventanilla--- tiene domicilio de persona RENAPO?: {}, {}", persona.getCurp(),
						!persona.getDomicilios().isEmpty());
				Domicilio domicilio = persona.getDomicilios().get(0);
				DomicilioParticular domicilioParticular = new DomicilioParticular();
				domicilioParticular.setCalle(domicilio.getCalle());
				domicilioParticular.setCp(domicilio.getCodigoPostal().getCodigoPostal());
				domicilioParticular.setEntidadFederativa(
						domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
				domicilioParticular.setDelegacion(domicilio.getAsentamiento().getLocalidad().getMunicipio().getNombre());
				domicilioParticular.setColonia(domicilio.getAsentamiento().getNombre());
				domicilioParticular.setNumeroExterior(domicilio.getNumExteriorAlf());
				domicilioParticular.setNumeroInterior(domicilio.getNumInteriorAlf());
				solicitud.setDomicilioParticular(domicilioParticular);
				
				//---------------------------------------------------->PENDIENTE
				solicitud.setEstatus(tramite.getEstadoTramite().getDescripcion());
				solicitud.setFechaInicio(flujoTrabajoBusiness.obtenerTareaPorIdTramite(tramite.getTramiteId()).getInicioTramite().getFechaSolicitud());
				solicitud.setIdTramite(String.valueOf(tramite.getTramiteId()));
				solicitud.setObservacion(tramite.getObservacion() != null?tramite.getObservacion().toUpperCase():"");
				if (null != tramite.getObservacionesSubdelegacion() && !tramite.getObservacionesSubdelegacion().isEmpty()) {					
					solicitud.setObservacionSubdelegacion(formatearObservacionesSubdelegacion(tramite.getObservacionesSubdelegacion()));
				}
				//agregar roles en caso de tenerlos 
				solicitud.setObservacionSubdelegacion(getInfoRoles(solicitud, roles));
				log.debug("---CDA Ventanilla--- ROLES********* {}", roles);
				solicitud.setMotivoAclaracion(getMotivoAclaracionSolicitud(tramite.getMotivosAclaracion()));
				solicitud.setGridHistoriaLaboral(getGridHistoriaLaboral(tramite));
				log.debug("---CDA--- Estado {}", tramite.getEstadoTramite().getDescripcion());
				//Estados de mostrar pantallas, cambiar al ultimo, recorriendo todos los tramites y asignarle el predominante o el negativo, junto al del estado
				solicitud.setEstadoAutorizacion(
						estadosPantallaAutorizadorUtil.obtenerEstadoAutorizador(tramite.getEstadoTramite().getIdEstadoTramitePersona(), sol.getEstadoSolicitud().getIdEstadoSolicitud()));
				solicitud.setEstadoResponsable(
						estadosPantallaUtil.obtenerEstadoResponsable(tramite.getEstadoTramite().getIdEstadoTramitePersona(),
								sol.getEstadoSolicitud().getIdEstadoSolicitud(),
								sol.getSolicitante() != null ? sol.getSolicitante().getUsuario(): null));
				
				
				
				// Cambiar por el tipo de la solicitud;
				TipoNSSCorreccion tipoCorreccion = new TipoNSSCorreccion();
				tipoCorreccion.setIdTipoNSSCorreccion(TipoNSSCorreccionEnum.CERTIFICADOR.getId());
				nss.setTipoNSS(tipoCorreccion);
				
				log.debug("---CDA Ventanilla--- Tipo de Regularizacion null?: {}", tramite.getTipoRegularizacion() == null);
				if (tramite.getTipoRegularizacion() != null) {
					log.debug("---CDA Ventanilla--- Tipo de Regularizacion sol: {}",
							tramite.getTipoRegularizacion().getIdTipoRegularizacion());
					tipoRegSol = tipoRegularizacionUtil.getTipoRegularizacion(tramite.getTipoRegularizacion().getIdTipoRegularizacion());

				}
				//---------------------------------------------------->PENDIENTE
				
				nss.setInformacionRENAPO(getInformacionRenapo(sol, persona));
				
			}
			
			tramite.setDocumentosProbatorios(obtenerDocumentosProbatorios(tramite.getTramiteId()));
			log.debug("Consultando los NSS Legados del nss {}",tramite.getListaNSS().get(0));
		
			nss.setInformacionFuentesNSS(getListaPersonasNSS(personasFuenteNSS, sol.getObservacion()));

			//Agrega cada NSS
			listNSS.add(nss);

			gridNSS.setData(listNSS);
			gridNSS.setCurrentPage(1);
			gridNSS.setPageSize(10000);
			gridNSS.setTotalOfRecords(1);
			gridsNSS.add(gridNSS);
			
			documentosProbatorios = tramite.getDocumentosProbatorios();
			log.debug("---CDA Ventanilla--- DocumentosProbatorios {}", documentosProbatorios);
			
			gridsDocumentosNss.add(getGridDocumentosProbatoriosNss(documentosProbatorios,sol.getSolicitudId().toString(), sol.getNoFolioSolicitud()));
			
			documentosNssOrigen = obtenerDocumentosProbatoriosNssOrigen(tramite.getTramiteId());
			log.debug("---CDA Ventanilla--- DocumentosProbatorios por NSS y Origen {}", documentosNssOrigen);
			//gridsDocumentosNssOrigen.add(e)
			
		}//Termina de recorrer los tramites de la solicitud
		
	    solicitud.setGridsNSS(gridsNSS);
	    solicitud.setGridsDocumentosNss(gridsDocumentosNss);
	    solicitud.setGridDocumentos(getGridDocumentosProbatorios(documentosProbatorios, sol.getSolicitudId().toString(), sol.getNoFolioSolicitud()));
	    solicitud.setGridsDocumentosNssOrigen(gridsDocumentosNssOrigen);
		
		solicitud.setFolio(sol.getNoFolioSolicitud());
		solicitud.setEstatus(getEstado(sol));
		solicitud.setSubDelegacion(getSubdelegacion(sol));
		solicitud.setResponsable(sol.getSolicitante() != null ? sol.getSolicitante().getUsuario() : "Sin Responsable");
		solicitud.setCurpResponsable(
				sol.getSolicitante() != null ? sol.getSolicitante().getUsuario() : "Sin Responsable");
		solicitud.setId(String.valueOf(sol.getSolicitudId()));
		
		
		solicitud.setGridDocumentosBeneficiario(getGridDocumentosBeneficiario(documentosProbatorios, 
				sol.getSolicitudId().toString(), sol.getNoFolioSolicitud()));
		
		log.debug("---CDA--- usuario {}", sol.getEstadoSolicitud().getIdEstadoSolicitud() != null ?  sol.getEstadoSolicitud().getIdEstadoSolicitud(): "null");
		
		solicitud.setTipoRegularizacion(tipoRegSol);

		return solicitud;
	}
	
	
	
	private List<DocumentosNss> obtenerDocumentosProbatoriosNssOrigen(Long cveIdTramite) throws DocumentoProbatorioException{
		  List<DocumentosNss> documentosProbatorios = new ArrayList<DocumentosNss>();
		     // for (DocumentoProbatorio documentoProbatorio : deltaDocumentoProbatorioServiceBusiness.listaDocumentosProbatoriosActivosTramite(cveIdTramite)) {
		    	//  documentoProbatorio = deltaDocumentoProbatorioServiceBusiness.getDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
		    	  //log.debug("Nombre {} id {} tramite {}", new Object []{documentoProbatorio.getNomNombreDocumento(),documentoProbatorio.getIdDocumentoProbatorio(),cveIdTramite});
		    	  
		    	  //documentosProbatorios.add(documentoProbatorio);
		      //}
		    return documentosProbatorios;
		  
	  }
	
	private List<DocumentoProbatorio> obtenerDocumentosProbatorios(Long cveIdTramite) throws DocumentoProbatorioException{
		  List<DocumentoProbatorio> documentosProbatorios = new ArrayList<DocumentoProbatorio>();
		      for (DocumentoProbatorio documentoProbatorio : deltaDocumentoProbatorioServiceBusiness.listaDocumentosProbatoriosActivosTramite(cveIdTramite)) {
		    	  documentoProbatorio = deltaDocumentoProbatorioServiceBusiness.getDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
		    	  log.debug("Nombre {} id {} tramite {}", new Object []{documentoProbatorio.getNomNombreDocumento(),documentoProbatorio.getIdDocumentoProbatorio(),cveIdTramite});
		    	  
		    	  documentosProbatorios.add(documentoProbatorio);
		      }
		    return documentosProbatorios;
		  
	  }
	
	@SuppressWarnings("unused")
	private InformacionRENAPO getInformacionRenapo(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol, Fisica personaRenapo){
		InformacionRENAPO informacionRENAPO = new InformacionRENAPO();
		informacionRENAPO.setFolio(sol.getNoFolioSolicitud());
		informacionRENAPO.setNombre(personaRenapo.getNombre());
		informacionRENAPO.setApellidoPaterno(personaRenapo.getPrimerApellido());
		informacionRENAPO.setApellidoMaterno(personaRenapo.getSegundoApellido());	
		informacionRENAPO.setCurp(personaRenapo.getCurp());
		informacionRENAPO.setSexo(personaRenapo.getSexo() != null && personaRenapo.getSexo().getDescripcion() != null
				? personaRenapo.getSexo().getDescripcion() : "");
		informacionRENAPO.setFechaNacimiento(personaRenapo.getFechaNacimientoFormateada());
		informacionRENAPO.setDatosDocumentoProbatorio(getDatosDoctoProbatorio(personaRenapo.getActaNacimiento()));
		informacionRENAPO.setLugarNacimiento(
				personaRenapo.getLugarNacimiento() != null ? personaRenapo.getLugarNacimiento().getNombre() : "");
		informacionRENAPO
				.setNacionalidad(personaRenapo.getPais() != null ? personaRenapo.getPais().getNacionalidad() : "");
		
		// medio de contacto
		CorreoElectronico correo = null;
		TelefonoFijo telefono = null;
		TelefonoMovil telefonoMov = null;	
		
		if(personaRenapo.getMediosContacto() != null && !personaRenapo.getMediosContacto().isEmpty()){
			for(MedioContacto med : personaRenapo.getMediosContacto()){
				if(med instanceof CorreoElectronico){
					 correo = (CorreoElectronico)med;	
				}
				
				if(med instanceof TelefonoFijo){
					 telefono = (TelefonoFijo)med;	
				}
				
				if(med instanceof TelefonoMovil){
					telefonoMov = (TelefonoMovil)med;	
				}
			}
		}
		
		informacionRENAPO.setTelefonoFijo(telefono != null ? telefono.getNumero() : "");
		informacionRENAPO.setTelefonoMovil(telefonoMov != null ? telefonoMov.getNumero() : "");
		informacionRENAPO.setCorreoElectronico(correo != null ? correo.getCorreo() : "");
		informacionRENAPO.setErrorSINDO(false);
		
		informacionRENAPO.setCurpsHistoricas(getCurpsHistoricas(personaRenapo.getCurpsHistoricas()));
		return informacionRENAPO;
	}

	private String formatearObservacionesSubdelegacion(List<ObservacionesSubdelegacion>observacionesSubdelegacion){
		
		StringBuilder str = new StringBuilder();
		for (ObservacionesSubdelegacion ob : observacionesSubdelegacion) {
			if(StringUtils.isNotBlank(ob.getDetalle()) || StringUtils.isNotBlank(ob.getResumen()) ){
			String resumen = null == ob.getResumen() ? "" : ob.getResumen();
			str.append(ob.getFechaActualizacion() != null
					? deltaUtils.convertirDateToStringMask(ob.getFechaActualizacion(), "") : "");
			str.append(" - ");
			if(StringUtils.isNotBlank(resumen)){				
				str.append("Resumen: ");
				str.append(resumen.toUpperCase());
				str.append(" / ");
			}
			str.append("Detalle: ");
			str.append(null == ob.getDetalle() ? "" : ob.getDetalle().toUpperCase()).append("\n");			
			}
		}
		
		return str.toString();
		
	}
	
	private String getInfoRoles(Solicitud sol, Map<String, Object> roles){
		
		StringBuilder str = new StringBuilder();
		
		if(sol.getObservacionSubdelegacion() != null){
			str.append(sol.getObservacionSubdelegacion());
			
		}
		
		if(roles != null && !roles.keySet().isEmpty()){
			for (String key : roles.keySet()) {
				str.append("\n");
				str.append(roles.get(key));
			}
		}
		
		return str.toString();
	}
	
	private List<InformacionRENAPO> getListaPersonasNSS(List<Fisica> personasFuenteNSS, String observacionSINDO){
		List<InformacionRENAPO> listaPersonasNSS = new ArrayList<InformacionRENAPO>();

		log.debug("---CDA--- Recorriendo lista {}", personasFuenteNSS);
		for (Fisica personaNSS : personasFuenteNSS) {
			log.debug("---CDA--- Nombre {}", personaNSS.getNombre());
			InformacionRENAPO informacionNSS = new InformacionRENAPO();
			informacionNSS.setNombre(personaNSS.getNombre() != null ? personaNSS.getNombre().trim().toUpperCase() : "");
			informacionNSS.setApellidoPaterno(
					personaNSS.getPrimerApellido() != null ? personaNSS.getPrimerApellido().trim().toUpperCase() : "");
			informacionNSS.setApellidoMaterno(personaNSS.getSegundoApellido() != null
					? personaNSS.getSegundoApellido().trim().toUpperCase() : "");
			informacionNSS.setCurp(personaNSS.getCurp() != null ? personaNSS.getCurp().trim().toUpperCase() : "");
			informacionNSS.setSexo(personaNSS.getSexo() != null && personaNSS.getSexo().getDescripcion() != null
					? personaNSS.getSexo().getDescripcion().trim().toUpperCase() : "");
			informacionNSS.setFechaNacimiento(personaNSS.getFechaNacimientoFormateada() != null
					? personaNSS.getFechaNacimientoFormateada().trim() : "");
			informacionNSS.setDatosDocumentoProbatorio(getDatosDoctoProbatorio(personaNSS.getActaNacimiento()));
			informacionNSS.setLugarNacimiento(pattern.matcher(Normalizer.normalize(
					personaNSS.getLugarNacimiento() != null && personaNSS.getLugarNacimiento().getNombre() != null
							? personaNSS.getLugarNacimiento().getNombre().trim().toUpperCase() : "",
					Normalizer.Form.NFD)).replaceAll(""));
			informacionNSS.setNacionalidad(pattern
					.matcher(Normalizer.normalize(
							personaNSS.getPais() != null && personaNSS.getPais().getNacionalidad() != null
									? personaNSS.getPais().getNacionalidad().trim().toUpperCase() : "",
							Normalizer.Form.NFD))
					.replaceAll(""));

			for (Identificador identificador : personaNSS.getIdentificadores()) {
				OrigenConsultaNssEnum enumOrigen = OrigenConsultaNssEnum
						.obtenerEnumById(Long.valueOf(identificador.getIdIdentificador()).intValue());
				if (enumOrigen.getDescripcion().equalsIgnoreCase(identificador.getIdentificadora())) {
					informacionNSS.setOrigen(identificador.getIdentificadora());
					informacionNSS.setIdOrigen(String.valueOf(enumOrigen.getClave()));
					informacionNSS.setErrorSINDO(getErrorFuente(observacionSINDO, enumOrigen.getClave()));
					break;
				}
			}
			listaPersonasNSS.add(informacionNSS);
		}
		return listaPersonasNSS;
	}

	
	private Page<HistoriaLaboral> getGridHistoriaLaboral(TramiteCorreccionCurp tramite){ 
	
		Page<HistoriaLaboral> gridHistoriaLaboral = new Page<HistoriaLaboral>();
		gridHistoriaLaboral.setTotalOfRecords(0);
		gridHistoriaLaboral.setPageSize(1000);
		gridHistoriaLaboral.setCurrentPage(1);
	
		List<DatosLaborales> datosLaborales = tramite.getDatosLaborales();
		List<HistoriaLaboral> listtHistoria = new ArrayList<HistoriaLaboral>();
		if (datosLaborales != null && !datosLaborales.isEmpty()) {
			for (DatosLaborales d : datosLaborales) {
				HistoriaLaboral historia = new HistoriaLaboral();
				historia.setActividadEmpresa(d.getActividad());
				historia.setDomicilioEmpresa(d.getDomicilio());
				historia.setEntidadFederativa(d.getEntidadFederativa().getNombre());
				historia.setFechaBaja(d.getFechaBaja());
				historia.setFechaInscripcion(d.getFechaInscripcion());
				historia.setNombrePatron(d.getNombrePatron());
				historia.setNumeroRegistroPatronal(d.getNrp());
				listtHistoria.add(historia);
			}
			gridHistoriaLaboral.setData(listtHistoria);
			gridHistoriaLaboral.setTotalOfRecords(listtHistoria.size());
		}
		return gridHistoriaLaboral;
	}

	private Page<Documento> getGridDocumentosProbatorios(List<DocumentoProbatorio> documentosProbatorios, 
			String solicitudId, String numeroFolio){
		log.debug("---CDA LISTA DE DOCUMENTOS PROBATORIOS ASEGURADO{}", documentosProbatorios);
		Page<Documento> gridDocumentos = new Page<Documento>();
		gridDocumentos.setTotalOfRecords(0);
		gridDocumentos.setPageSize(1000);
		gridDocumentos.setCurrentPage(1);
		
		List<Documento> listaDocumentos = new ArrayList<Documento>();
		if (documentosProbatorios != null) {
			for (DocumentoProbatorio documentoProbatorio : documentosProbatorios) {
				int tipoDocumento = documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
				if( tipoDocumento == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId() || tipoDocumento == TipoDocumentoProbatorioEnum.ACTAS.getId()){
					Documento documento = new Documento();
					documento.setIdPersona(solicitudId);
				
					String[] n = documentoProbatorio.getNomNombreDocumento().split("\\.");
					documento.setExtension(n[n.length - 1]);
					documento.setNombreArchivo(documentoProbatorio.getNomNombreDocumento());
					documento.setTipoDocumento(
							documentoProbatorio.getDocumentoPorTipo().getDocumento().getDesDocumento());
					documento.setIdDocBoveda(documentoProbatorio.getBovedaDocId());
					log.debug("---CDA Nombre del tipoDocumento adjuntado {}", documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getDescripcion());
					log.debug("---CDA Nombre del tipoDocumento adjuntado {}", documento.getTipoDocumento());
					log.debug("---CDA Nombre del nombreDocumentoAdjuntado {}", documento.getNombreArchivo());

					documento.setFolio(numeroFolio);
					listaDocumentos.add(documento);
				}
			}
			gridDocumentos.setData(listaDocumentos);
			gridDocumentos.setTotalOfRecords(listaDocumentos.size());
		}
		return gridDocumentos;
	}
	
	private Page<Documento> getGridDocumentosProbatoriosNss(List<DocumentoProbatorio> documentosProbatorios,String solicitudId, String numeroFolio){
		log.debug("---CDA LISTA DE DOCUMENTOS PROBATORIOS POR NSS{}", documentosProbatorios);
		Page<Documento> gridDocumentosNSS = new Page<Documento>();
		gridDocumentosNSS.setTotalOfRecords(0);
		gridDocumentosNSS.setPageSize(1000);
		gridDocumentosNSS.setCurrentPage(1);
		
		if (documentosProbatorios != null) {
			List<Documento> listaDocumentos = new ArrayList<Documento>();
			for (DocumentoProbatorio documentoProbatorio : documentosProbatorios) {
				if(documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()){
					Documento documento = new Documento();
					documento.setIdPersona(solicitudId);
				
					String[] n = documentoProbatorio.getNomNombreDocumento().split("\\.");
					documento.setExtension(n[n.length - 1]);
					documento.setNombreArchivo(documentoProbatorio.getNomNombreDocumento());
					documento.setTipoDocumento(
							documentoProbatorio.getDocumentoPorTipo().getDocumento().getDesDocumento());
					documento.setIdDocBoveda(documentoProbatorio.getBovedaDocId());
					log.debug("---CDA Nombre del tipoDocumento adjuntado {}", documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getDescripcion());
					log.debug("---CDA Nombre del tipoDocumento adjuntado {}", documento.getTipoDocumento());
					log.debug("---CDA Nombre del nombreDocumentoAdjuntado {}", documento.getNombreArchivo());

				
					documento.setFolio(numeroFolio);
					listaDocumentos.add(documento);
					
				}
			}
			gridDocumentosNSS.setData(listaDocumentos);
			gridDocumentosNSS.setTotalOfRecords(listaDocumentos.size());
		}
		
		return gridDocumentosNSS;
	}

	
	private Page<Documento> getGridDocumentosBeneficiario(List<DocumentoProbatorio> documentosProbatorios, 
            String solicitudId, String numeroFolio){
     
     Page<Documento> gridDocumentosBeneficiario = new Page<Documento>();
     gridDocumentosBeneficiario.setTotalOfRecords(0);
     gridDocumentosBeneficiario.setPageSize(1000);
     gridDocumentosBeneficiario.setCurrentPage(1);
     
     List<Documento> listaDocsBeneficiaro = new ArrayList<Documento>();
     if (documentosProbatorios != null) {
            for (DocumentoProbatorio documentoProbatorio : documentosProbatorios) {
            	if (documentoProbatorio.getNomNombreDocumento() != null && documentoProbatorio.getNomNombreDocumento().startsWith("CDA_PI_")) {
                   Documento documento = new Documento();
                   documento.setIdPersona(solicitudId);
                   
                          String[] n = documentoProbatorio.getNomNombreDocumento().split("\\.");
                          documento.setExtension(n[n.length - 1]);
                          documento.setNombreArchivo(documentoProbatorio.getNomNombreDocumento());
                          documento.setTipoDocumento(
                                       documentoProbatorio.getDocumentoPorTipo().getDocumento().getDesDocumento());
                          documento.setIdDocBoveda(documentoProbatorio.getBovedaDocId());
                          log.debug("---CDA Nombre del tipoDocumentoAdjuntadobeneficiario {}", documento.getTipoDocumento());
                          log.debug("---CDA Nombre del nombreDocumentoAdjuntadoBeneficiario {}", documento.getNombreArchivo());
                   documento.setFolio(numeroFolio);
                   listaDocsBeneficiaro.add(documento);
            }}
            gridDocumentosBeneficiario.setData(listaDocsBeneficiaro);
            gridDocumentosBeneficiario.setTotalOfRecords(listaDocsBeneficiaro.size());
     }
     return gridDocumentosBeneficiario;
}
	
	private String getDatosDoctoProbatorio(Nacimiento actaNacimiento){
		log.debug("---CDA ACTA NACIMIENTO--- {}", actaNacimiento);
		StringBuffer acta = new StringBuffer();
		if (actaNacimiento != null) {
			acta.append("Entidad: ")
					.append(actaNacimiento.getMunicipio().getEntidadFederativa().getNombre() != null
							? normalizarCadenas(actaNacimiento.getMunicipio().getEntidadFederativa().getNombre()): " ");
			acta.append("\nMunicipio: ").append(actaNacimiento.getMunicipio().getNombre() != null
					? normalizarCadenas(actaNacimiento.getMunicipio().getNombre()) : " ");
			acta.append("\nA\u00f1o de registro: ").append(actaNacimiento.getAnio() != 0
					? normalizarCadenas(actaNacimiento.getAnio()) : " ");
			acta.append("\nTomo: ").append(!actaNacimiento.getTomo().equals("0")
					? normalizarCadenas(actaNacimiento.getTomo()) : "");
			acta.append("\nN\u00famero de Acta: ").append(!actaNacimiento.getNoActa().equals("0")
					? normalizarCadenas(actaNacimiento.getNoActa()) : "");
			acta.append("\nCRIP: ").append(!actaNacimiento.getCrip().equals("0")
					? normalizarCadenas(actaNacimiento.getCrip()) : "");
			acta.append("\nN\u00famero de Libro: ").append(!actaNacimiento.getNoLibro().equals("0")
					? normalizarCadenas(actaNacimiento.getNoLibro()) : "");
			acta.append("\nN\u00famero de Foja: ").append(!actaNacimiento.getNoFoja().equals("0")
					? normalizarCadenas(actaNacimiento.getNoFoja()) : "");
		}
		if(actaNacimiento== null){
			acta.append("Entidad: ").append( " ");
            acta.append("\nMunicipio: ").append( " ");
			acta.append("\nA\u00f1o de registro: ").append( " ");
			acta.append("\nTomo: ").append( " ");
			acta.append("\nN\u00famero de Acta: ").append( " ");
			acta.append("\nCRIP: ").append( " ");
			acta.append("\nN\u00famero de Libro: ").append( " ");
			acta.append("\nN\u00famero de Foja: ").append( " ");
		}
		
		return acta.toString();

	}
	
	private String normalizarCadenas(Object cadena){
		return pattern.matcher(Normalizer.normalize(cadena.toString(),
				Normalizer.Form.NFD)).replaceAll("");
	}
	
	private MotivoAclaracion getMotivoAclaracionSolicitud(List<mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion> motivos){
		MotivoAclaracion motivoAclaracion = new MotivoAclaracion();
		if (motivos != null && !motivos.isEmpty()) {
			log.debug("--MA-- motivos {}", motivos.size());
			Field[] fields = MotivoAclaracion.class.getDeclaredFields();
			TiposAclaracionEnum[] tipoMotivos = TiposAclaracionEnum.values();
			List<TiposAclaracionEnum> motivosSeleccionados = new ArrayList<TiposAclaracionEnum>();
			log.debug("--MA-- campos {}", fields.length);
			for (mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion motivo : motivos) {
				for (TiposAclaracionEnum tipoMotivo : tipoMotivos) {
					if (motivo.getIdMotivoAclaracion() == (tipoMotivo.getMotivoAclaracion().getId())) {
						motivosSeleccionados.add(tipoMotivo);
					}
					if (motivo.getIdMotivoAclaracion().longValue() == TiposAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO
							.getMotivoAclaracion().getId()) {
						motivoAclaracion.setNumeroCredito(motivo.getDetalleAclaracion().toUpperCase());
					}
					if (motivo.getIdMotivoAclaracion().longValue() == TiposAclaracionEnum.OTRO.getMotivoAclaracion()
							.getId()) {
						motivoAclaracion.setOtroMotivo(motivo.getDetalleAclaracion().toUpperCase());
					}
					
					if(motivo.getIdMotivoAclaracion().longValue()== TiposAclaracionEnum.ACLARACION_SALDO_SUPUESTA.getMotivoAclaracion().getId()){
						motivoAclaracion.setAclaracionSaldoSupuestaVivienda(true);
					}
				}
			}
			log.debug("--MA-- motivos seleccionados {}", motivosSeleccionados.size());
			for (TiposAclaracionEnum tipo : motivosSeleccionados) {
				String nombreMotivoSeleccionado = tipo.toString().replace("_", "");
				for (Field campo : fields) {
					if (campo.getName().equalsIgnoreCase(nombreMotivoSeleccionado)) {
						try {
							campo.setAccessible(true);
							campo.set(motivoAclaracion, true);
						} catch (IllegalArgumentException e) {
							log.error("Argumento ilegal", e);
						} catch (IllegalAccessException e) {
							log.error("Acceso ilegal", e);
						}
					}
				}
			}
		}
		return motivoAclaracion;
	}
	
	private SubDelegacion getSubdelegacion(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol){
		SubDelegacion subDelegacion = new SubDelegacion();
		if(sol.getSubdelegacion() != null){
			subDelegacion.setDescripcion(sol.getSubdelegacion().getClave()+" - "+sol.getSubdelegacion().getDescripcion());
			subDelegacion.setDomicilio(sol.getSubdelegacion().getDelegacion() != null
				? sol.getSubdelegacion().getDelegacion().getDescripcion() : "");
		}
		return subDelegacion;
	}
	private String getCurpsHistoricas(List<String> curpsHistoricas){
		StringBuilder stb = new StringBuilder();
		if (curpsHistoricas != null && !curpsHistoricas.isEmpty()){
			for(String curp :curpsHistoricas){
				stb.append(curp);
				stb.append("\n");
			}
		}
		return stb.toString();
	}
	
	private boolean getErrorFuente(String observacionSINDO, int fuente){
		log.debug("---CDA--- {}, {}", fuente, observacionSINDO);
		return observacionSINDO != null ? observacionSINDO.contains("|" + fuente + "|") : false;
	}
	
	public String getEstado(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol){
		
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol.getTramites().get(0);
		String estatus = EstadoNegocioEnum.obtenerDescripcionNegocio(tramite.getEstadoTramite().getIdEstadoTramitePersona());
		
		InicioTramite inicioTramite = flujoTrabajoBusiness.obtenerTareaPorIdTramite(tramite.getTramiteId()).getInicioTramite();
		
		if (DateUtils.sumaDias(deltaUtils.convertirStringTodate( inicioTramite != null && inicioTramite.getFechaSolicitud() != null  ? inicioTramite.getFechaSolicitud() : DateUtils.dateToStringConFormato(sol.getFechaSolicitud(), "dd/MM/yyyy HH:mm:ss")), 40).before(new Date())) {
			if ( !estatus.equals(EstadoNegocioEnum.ATENDIDA.getDescripcion()) && !estatus.equals(EstadoNegocioEnum.ABANDONADA.getDescripcion())){
				estatus = estatus + SOLICITUD_VENCIDA;
			}
		} 
		
		return estatus;
		
	}
}