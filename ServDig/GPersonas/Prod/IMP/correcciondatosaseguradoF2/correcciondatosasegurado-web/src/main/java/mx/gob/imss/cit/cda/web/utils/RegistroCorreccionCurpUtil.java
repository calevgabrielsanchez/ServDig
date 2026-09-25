package mx.gob.imss.cit.cda.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.vo.ConsultaSolicitudTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DatosAdicionalesHistoriaLaboral;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.MotivoAclaracionVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.Institucion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.enums.InstitucionEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import net.sf.jasperreports.engine.JRParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RegistroCorreccionCurpUtil{
	
	/**
     * logger de la clase
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroCorreccionCurpUtil.class);

	private static final String TITULO_FORMULARIO_SOLICITUD = "Solicitud de Regularizaci\u00F3n y/o Correcci\u00F3n de Datos Personales del Asegurado";
	private static final String HOMOCLAVE_TRAMITE = "IMSS-02-012";
	private static final String STATUS_SOL_PROC = "EN PROCESO";
	private static final String STATUS_SOL = "EN PROCESO DE ATENCI\u00D3N";
	private static final String TITULO_TRAMITE = "Comprobante de solicitud de regularizaci\u00F3n y/o correcci\u00F3n de datos personales del asegurado";

	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;

	/**
     * Obtiene el detalle de un tramite como string
     * @param tramite
     * @return el string con el detalle del tramite
     */
    
  public List<DocumentoProbatorio> cargarDatosTramite(TramiteCorreccionCurp tramite, DatosHistoriaLaboralVO documentosNss, DatosAdicionalesHistoriaLaboral contacto){
	  List<DocumentoProbatorio> listDocumentos = null;
    	if(documentosNss!=null){
    		listDocumentos = new ArrayList<DocumentoProbatorio>();
    		for(mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio d: documentosNss.getDocumentoProbatorioList()){
				DocumentoPorTipo tipoDocumento = new DocumentoPorTipo();
				tipoDocumento.setIdDocumentoPorTipo(d.getIdDocumentoPorTipo());
				tipoDocumento.setIdDocumentoPorTipoHashed(String.valueOf(d.getIdDocumentoPorTipo()));
				LOGGER.debug("--CDA-- ID DOCUMENTO POR TIPO: " + tipoDocumento.getIdDocumentoPorTipo());
				LOGGER.debug("--CDA-- ID DOCUMENTO POR TIPO HASHED" + tipoDocumento.getIdDocumentoPorTipoHashed());
				TipoDocumentoProbatorio tipoDoc = new TipoDocumentoProbatorio();
				tipoDoc.setDescripcion(d.getDesDocumento());
				tipoDocumento.setTipoDocumentoProbatorio(tipoDoc);
				Documento doc = new Documento();
				doc.setDesDocumento(d.getDesDocumento());
				doc.setCveIdDocumento(d.getCveIdDocumento());
				tipoDocumento.setDocumento(doc);
				DocumentoProbatorio documentoProbatorio = new DocumentoProbatorio();
				documentoProbatorio.setDocumentoPorTipo(tipoDocumento);
				documentoProbatorio.setNomNombreDocumento(d.getNombre());
				documentoProbatorio.setBovedaDocId(d.getIdDocBoveda());
				listDocumentos.add(documentoProbatorio);
			}
    		
    		LOGGER.debug("el NSS es {}" ,documentosNss.getNSSList().get(0).getNSS());
			List<String> listNss = new ArrayList<String>();
			for(NSSVO nss : documentosNss.getNSSList() ){
				listNss.add(nss.getNSS());
			}
			tramite.setListaNSS(listNss);
		}
    	
    	if(contacto!=null){
    		tramite.setObservacion(contacto.getObservaciones().toUpperCase());
    	}
    	return listDocumentos;
    }

	public Map<String, Object> obtenerParametros(Solicitud solicitud) {

		LOGGER.debug("---CDA--- Genero reporte de Solicitud con ID: {}", solicitud.getSolicitudId());

		TramiteCorreccionCurp tramiteCorreccionCurp = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
		Map<String, Object> parametros1 = new HashMap<String, Object>();
		
		DeltaUtils deltaUtils = new DeltaUtils();
		
		parametros1.put("titulo", TITULO_TRAMITE);
		parametros1.put("folioSolicitud", solicitud.getNoFolioSolicitud());
		
		try {
			parametros1.put("fechaSolicitud", deltaUtils.cambiarFormatoFecha("dd/MM/yyyy", flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCorreccionCurp.getTramiteId()).getInicioTramite().getFechaSolicitud(), "dd / MM / yyyy"));
			parametros1.put("fechaHoraRecepcion", deltaUtils.cambiarFormatoFecha("dd/MM/yyyy", flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCorreccionCurp.getTramiteId()).getInicioTramite().getFechaSolicitud(), "dd / MM / yyyy")); 			
		} catch (ParseException e1) {
			LOGGER.error("-- CDA: Error Fecha Solicitud. ", e1);
		}
		
		parametros1.put("cadenaOriginal", solicitud.getFirmaElectronica().getCadenaOriginal());
		parametros1.put("secuenciaNotarial", solicitud.getFirmaElectronica().getSecuenciaNotaria());
		parametros1.put("selloDigital", solicitud.getFirmaElectronica().getRecibo());
		parametros1.put("numeroSerie", solicitud.getFirmaElectronica().getSerialCertificado());

		return parametros1;
	}
  
  private void obtenerParametrosComplementoStep01(Map<String, Object> parametros2,
          Fisica personaRegistro){
    parametros2.put("CURP", personaRegistro.getCurp() != null ? personaRegistro.getCurp() : " ");
		parametros2.put("RFC", personaRegistro.getRfc() != null ?personaRegistro.getRfc():" ");			
		parametros2.put("NOMBRE", personaRegistro.getNombre() !=null?personaRegistro.getNombre():" ");
		parametros2.put("PRIMER_APELLIDO", personaRegistro.getPrimerApellido()!= null? personaRegistro.getPrimerApellido():" ");
		parametros2.put("SEGUNDO_APELLIDO", personaRegistro.getSegundoApellido() != null ? personaRegistro.getSegundoApellido():" ");
    parametros2.put("DIA_NACIMIENTO", personaRegistro.getFechaNacimientoFormateada() != null ? personaRegistro.getFechaNacimientoFormateada():" ");
  }

  public Map<String, Object> obtenerParametrosComplemento(Solicitud solicitud, DatosHistoriaLaboralVO histLaboral, DomicilioCorto motivosAclaracion){
	 	Map<String, Object> parametros2 = new HashMap<String, Object>();
	 	DeltaUtils deltaUtils = new DeltaUtils();
	 	
	 	TramiteCorreccionCurp tramiteCDA = ((TramiteCorreccionCurp)solicitud.getTramites().get(0));
	 	Fisica personaRegistro = tramiteCDA.getPersonaRENAPO();
                List<String> listaNss = new ArrayList<String>();
                TramiteCorreccionCurp tramitesCDA = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
                for (Iterator<CorreccionNSS> iterator = tramitesCDA.getListaNssCorreccion().iterator(); iterator.hasNext();) {
                    CorreccionNSS correccionNSS = (CorreccionNSS)iterator.next();
                    listaNss.add(correccionNSS.getNss());
                }
	 	
	 	parametros2.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));
		parametros2.put("titulo", TITULO_FORMULARIO_SOLICITUD);
		parametros2.put("title", TITULO_FORMULARIO_SOLICITUD);
		parametros2.put("homoclaveTramite", HOMOCLAVE_TRAMITE);
		parametros2.put("folio", solicitud.getNoFolioSolicitud());
		
		try {
			parametros2.put("fechaSolicitudTramite", deltaUtils.cambiarFormatoFecha("dd/MM/yyyy", flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCDA.getTramiteId()).getInicioTramite().getFechaSolicitud(), "dd / MM / yyyy"));			
		} catch (ParseException e1) {
			
			LOGGER.error("-- CDA: Error Fecha Solicitud. ", e1);
		}
				
	  obtenerParametrosComplementoStep01(parametros2, personaRegistro);
//		parametros2.put("NSS", tramiteCDA.getListaNSS().get(0));
		parametros2.put("NSS", listaNss);		
								
		parametros2.put("NACIMIENTO_LOCALIDAD", personaRegistro.getLugarNacimiento()!= null ? personaRegistro.getLugarNacimiento().getNombre():"");		
		parametros2.put("NACIMIENTO_PAIS", personaRegistro.getPais() != null ? personaRegistro.getPais().getNacionalidad() : " ");
		parametros2.put("SEXO", personaRegistro.getSexo().getDescripcion());
		
		parametros2.put("OBSERVACIONES", solicitud.getTramites().get(0).getObservacion() != null ? solicitud.getTramites().get(0).getObservacion():" ");
		
		parametros2.putAll(agregarParametrosActaNacimiento(personaRegistro));
		parametros2.putAll(agregarParametrosFirmaElectronica(solicitud.getFirmaElectronica()));
		parametros2.putAll(agregarParametrosDomicilio(personaRegistro));
		parametros2.putAll(agregarParametrosContacto(personaRegistro));
		parametros2.put("ASG_ESTADO", " ");
		parametros2.putAll(agregarParametrosBeneficiario());
		parametros2.put("LISTA_HISTORIA_LAB", tramiteCDA.getDatosLaborales());
        parametros2
                .putAll(agregarParametrosDocumentosProbatorios(obtenerDocumentacionProbatoriaGeneral(solicitud)));
		parametros2.putAll(agregarParametrosMotivosAclaracion(motivosAclaracion));
			
		return parametros2;
  }
  
  private void obtenerDocumentacionProbatoriaGeneralStep01(
    TramiteCorreccionCurp tramitesCDA, List<DocumentoProbatorio> listaDocumentos ){
    if (tramitesCDA.getAsegurado() != null) {
      if (tramitesCDA.getAsegurado().getDocumentosProbatorios() != null
              && !tramitesCDA.getAsegurado().getDocumentosProbatorios().
                      isEmpty()) {
        for (DocumentoProbatorio doc : tramitesCDA.getAsegurado().
                getDocumentosProbatorios()) {
          listaDocumentos.add(doc);
        }
      }
    }
  }
  
  private List<DocumentoProbatorio> obtenerDocumentacionProbatoriaGeneral(Solicitud solicitud){
      LOGGER.debug("Se obtienen los documentos para marcarlos");
      List<DocumentoProbatorio> listaDocumentos = new ArrayList<DocumentoProbatorio>();
      TramiteCorreccionCurp tramitesCDA = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
      LOGGER.debug("Se obtienen los documentos del asegurado");
      obtenerDocumentacionProbatoriaGeneralStep01(tramitesCDA, listaDocumentos);
      LOGGER.debug("Se obtienen los documentos de nss");
      if(tramitesCDA.getListaNssCorreccion()!= null && !tramitesCDA.getListaNssCorreccion().isEmpty()){                      
             for(CorreccionNSS correccionNSS :tramitesCDA.getListaNssCorreccion()){
                 if(correccionNSS.getDocumentosProbatorios()!=null && !correccionNSS.getDocumentosProbatorios().isEmpty()){
                     for(DocumentoProbatorio doc :correccionNSS.getDocumentosProbatorios()){                          
                         listaDocumentos.add(doc);                          
                      }
                 }                 
             }

      }
      return listaDocumentos;
  }
  
  private void agregarParametrosActaNacimientoStep01(Map<String, Object> parametros, Fisica personaRegistro ){
    parametros.put("NUM_ACTA", personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getNoActa() : " ");
	  parametros.put("NUM_FOJA", personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getNoFoja() : " ");
	  parametros.put("NUM_TOMO",personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getTomo() : " ");
	  parametros.put("NUM_CRIP", personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getCrip() : " ");
  }
  
  private Map<String, Object> agregarParametrosActaNacimiento(Fisica personaRegistro){
	  Map<String, Object> parametros = new HashMap<String, Object>();
	  parametros.put("MUNICIPIO",  personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getMunicipio().getNombre() : " ");
	  parametros.put("ENTIDAD_FEDERATIVA",personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getMunicipio().getEntidadFederativa().getNombre() : "");
	  parametros.put("ANIO_REGISTRO", personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getAnio().toString() : " ");
	  parametros.put("NUM_LIBRO",personaRegistro.getActaNacimiento() != null ? personaRegistro.getActaNacimiento().getNoLibro() : " ");
	  agregarParametrosActaNacimientoStep01(parametros, personaRegistro);
	  return parametros;
  }
  
  private Map<String, Object> agregarParametrosFirmaElectronica(FirmaElectronica firmaElectronica){
	  Map<String, Object> parametros = new HashMap<String, Object>();
	  parametros.put("CADENA_ORIGINAL", firmaElectronica.getCadenaOriginal());
	  parametros.put("SECUENCIA_NOTARIAL", firmaElectronica.getSecuenciaNotaria());
	  parametros.put("SELLO_DIGITAL", firmaElectronica.getRecibo());
	  parametros.put("NUM_SERIE", firmaElectronica.getSerialCertificado());
	  return parametros;
  }
  
  private Map<String, Object> agregarParametrosDomicilio(Fisica personaRegistro){
	  Map<String, Object> parametros = new HashMap<String, Object>();
	  parametros.put("ASG_COLONIA", personaRegistro.getDomicilios().get(0).getAsentamiento().getNombre());
	  parametros.put("ASG_CODIGO_POSTAL", personaRegistro.getDomicilios().get(0).getCodigoPostal().getCodigoPostal());
	  parametros.put("ASG_CALLE", personaRegistro.getDomicilios().get(0).getCalle());
	  parametros.put("ASG_NUM_EXT", personaRegistro.getDomicilios().get(0).getNumExteriorAlf());
	  parametros.put("ASG_NUM_INT", personaRegistro.getDomicilios().get(0).getNumInteriorAlf() != null ? personaRegistro.getDomicilios().get(0).getNumInteriorAlf(): " ");
	  parametros.put("ASG_LOCALIDAD", personaRegistro.getDomicilios().get(0).getAsentamiento() != null ? personaRegistro.getDomicilios().get(0).getAsentamiento().getLocalidad().getMunicipio().getNombre(): " ");
	
	  if(personaRegistro.getDomicilios().get(0).getAsentamiento() != null){
		parametros.put("ASG_MUNICIPIO", personaRegistro.getDomicilios().get(0).getAsentamiento().getLocalidad().getMunicipio().getNombre());
		parametros.put("DOM_ASG_ESTADO", personaRegistro.getDomicilios().get(0).getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
	  }else{
		parametros.put("ASG_MUNICIPIO", " ");
		parametros.put("DOM_ASG_ESTADO", " ");
	  }
	  return parametros;
  }
  
  private Map<String, Object> agregarParametrosContacto(Fisica personaRegistro){
	  Map<String, Object> parametros = new HashMap<String, Object>();
	  
	  parametros.put("ASG_MAIL", personaRegistro.getCorreoElectronico() != null ? 
				personaRegistro.getCorreoElectronico().getCorreo():"");
		parametros.put("ASG_TELEFONO_FIJO", personaRegistro.getTelefonoFijo() != null?personaRegistro.getTelefonoFijo().getNumero(): " ");
		parametros.put("ASG_TELEFONO_MOVIL", personaRegistro.getTelefonoMovil() != null?personaRegistro.getTelefonoMovil().getNumero():" ");
	  return parametros;
  }
  
  private Map<String, Object> agregarParametrosBeneficiario(){
	  Map<String, Object> parametros = new HashMap<String, Object>();
	  parametros.put("BNF_CURP", " ");
		parametros.put("BNF_RFC", " ");
		parametros.put("BNF_NOMBRES", " ");
		parametros.put("BNF_PRIMER_APELLIDO", " ");
		parametros.put("BNF_SEGUNDO_APELLIDO", " ");
		parametros.put("BNF_TELEFONO_FIJO", " ");
		parametros.put("BNF_TELEFONO_MOVIL", " ");
		parametros.put("BNF_CORREO_ELECTRONICO", " ");
		parametros.put("BNF_CODIGO_POSTAL", " ");
		parametros.put("BNF_CALLE", " ");
		parametros.put("BNF_NUM_EXTERIOR", " ");
		parametros.put("BNF_NUM_INTERIOR", " ");
		parametros.put("BNF_COLONIA", " ");
		parametros.put("BNF_LOCALIDAD", " ");
		parametros.put("BNF_MUNICIPIO", " ");
		parametros.put("BNF_ESTADO", " ");
		parametros.put("BNF_SEXO", " ");
		parametros.put("BNF_SOLICITANTE", " ");
		return parametros;
  }
  
  private Map<String, Object> agregarParametrosDocumentosProbatorios(
			List<DocumentoProbatorio> documentosProbatorios) {
	  Map<String, Object> parametros = new HashMap<String, Object>();
		
		List<String>documentos = new ArrayList<String>();
		if(documentosProbatorios != null){ 
			for(DocumentoProbatorio doctoProbatorio : documentosProbatorios){
				LOGGER.debug("---CDA--- clave doumento reporte {}",doctoProbatorio.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
				documentos.add(doctoProbatorio.getDocumentoPorTipo().getDocumento().getCveIdDocumento().toString());
			}
		}
		parametros.put("DOC_PROB_LIST",documentos);
		LOGGER.debug("---CDA--- Documentos probatorios {}",documentos);		
		return parametros;
  }
  
  private void agregarParametrosMotivosAclaracionStep02(DomicilioCorto motivosAclaracion,    
    List<String> motivosAclaracionAfore ){
    if (motivosAclaracion.getMotivoAclaracionVO()
              .getMotivosAclaracionAfore() != null) {
        for (String motivoAclaracionAfore : motivosAclaracion
                .getMotivoAclaracionVO().getMotivosAclaracionAfore()) {
          motivosAclaracionAfore.add(motivoAclaracionAfore);
        }
      }
  }
  
  private void agregarParametrosMotivosAclaracionStep01(DomicilioCorto motivosAclaracion,
    List<String> motivosAclaracionIMSS, List<String> motivosAclaracionInfonavit,
    List<String> motivosAclaracionAfore ){
    if (motivosAclaracion.getMotivoAclaracionVO() != null) {
      if (motivosAclaracion.getMotivoAclaracionVO()
              .getMotivosAclaracionIMSS() != null) {
        for (String motivoAclaracion : motivosAclaracion
                .getMotivoAclaracionVO().getMotivosAclaracionIMSS()) {
          motivosAclaracionIMSS.add(motivoAclaracion);
        }
      }
      if (motivosAclaracion.getMotivoAclaracionVO()
              .getMotivosAclaracionInfonavit() != null) {
        for (String motivoAclaracionInfonavit : motivosAclaracion
                .getMotivoAclaracionVO()
                .getMotivosAclaracionInfonavit()) {
          motivosAclaracionInfonavit.add(motivoAclaracionInfonavit);
        }
      }
      agregarParametrosMotivosAclaracionStep02(motivosAclaracion,
              motivosAclaracionAfore);
    }
  }
  
	private Map<String, Object> agregarParametrosMotivosAclaracion(
			DomicilioCorto motivosAclaracion) {
		Map<String, Object> parametros = new HashMap<String, Object>();
	
		List<String> motivosAclaracionIMSS = new ArrayList<String>();
		List<String> motivosAclaracionInfonavit = new ArrayList<String>();
		List<String> motivosAclaracionAfore = new ArrayList<String>();
	
		agregarParametrosMotivosAclaracionStep01(motivosAclaracion,
            motivosAclaracionIMSS, motivosAclaracionInfonavit,
            motivosAclaracionAfore);
	
		parametros.put("MOTIVOS_ACLARACION_IMSS", motivosAclaracionIMSS);
		parametros.put("MOTIVOS_ACLARACION_INFONAVIT",
				motivosAclaracionInfonavit);
		parametros.put("MOTIVOS_ACLARACION_AFORE", motivosAclaracionAfore);
	
		parametros.put("CREDITO_DESCONTADO", motivosAclaracion.getMotivoAclaracionVO().getCreditoDescontado() != null ? motivosAclaracion.getMotivoAclaracionVO().getCreditoDescontado().toUpperCase() : "");
		parametros
				.put("OTRO", motivosAclaracion.getMotivoAclaracionVO().getEspecificacion() !=null ? motivosAclaracion.getMotivoAclaracionVO().getEspecificacion().toUpperCase() : "");
		return parametros;
	}
    
  public List<DatosLaborales> cargarDatosLaborales(List<HistoriaLaboralVO> historiaLaboralGrid){		
		List<DatosLaborales> listDatosLaborales = new ArrayList<DatosLaborales>();
		if(historiaLaboralGrid!=null && !historiaLaboralGrid.isEmpty()){
			for(HistoriaLaboralVO h : historiaLaboralGrid){
				DatosLaborales dato = new DatosLaborales();
				dato.setNombrePatron(h.getNombrePatron());
				EntidadFederativa entidad = new EntidadFederativa();
				entidad.setNombre(h.getEntidadFederativa());
                                entidad.setClave(h.getClaveEntidad());
				dato.setEntidadFederativa(entidad);
				dato.setFechaInscripcion(h.getFechaInscripcion());
				dato.setFechaBaja(h.getFechaBaja());
				dato.setNrp(h.getNumeroRegistroPatronal());
				dato.setDomicilio(h.getDomicilioEmpresa());
				dato.setActividad(h.getActividadEmpresa());
				listDatosLaborales.add(dato);
			}
		}
		return listDatosLaborales;
	}
  
  private void cargarMotivosAclaracionStep01(MotivoAclaracionVO motivos,
    List<MotivoAclaracion> listMotivos ){
    if (motivos.getMotivosAclaracionIMSS() != null) {
			List<TiposAclaracionEnum> motivosIMSS = TiposAclaracionEnum	.obtenerAclaracionesPorClaves(
							TiposAclaracionEnum.Dependencia.IMSS, motivos.getMotivosAclaracionIMSS());
			Institucion institucion = new Institucion();
			institucion.setIdInstitucion(Long.valueOf(InstitucionEnum.IMSS.getId()).intValue());
			institucion.setNombreInstitucion(InstitucionEnum.IMSS.toString());
			for (TiposAclaracionEnum aclaracion : motivosIMSS) {
				MotivoAclaracion motivo = new MotivoAclaracion();
				motivo.setIdMotivoAclaracion(Long.valueOf(aclaracion.getMotivoAclaracion().getId()).intValue());
				motivo.setInstitucion(institucion);
				motivo.setDescripcionMotivo(aclaracion.getMensaje());
				listMotivos.add(motivo);
			}
		}
		if (motivos.getMotivosAclaracionInfonavit() != null) {
			List<TiposAclaracionEnum> motivosInfonavit = TiposAclaracionEnum.obtenerAclaracionesPorClaves(
							TiposAclaracionEnum.Dependencia.INFONAVIT, motivos.getMotivosAclaracionInfonavit());
			Institucion institucion = new Institucion();
			institucion.setIdInstitucion(Long.valueOf(InstitucionEnum.INFONAVIT.getId()).intValue());
			institucion.setNombreInstitucion(InstitucionEnum.INFONAVIT.toString());
			for (TiposAclaracionEnum aclaracion : motivosInfonavit) {
				MotivoAclaracion motivo = new MotivoAclaracion();
				motivo.setIdMotivoAclaracion(Long.valueOf(aclaracion.getMotivoAclaracion().getId()).intValue());
				motivo.setInstitucion(institucion);
				motivo.setDescripcionMotivo(aclaracion.getMensaje());
				if (aclaracion.getMotivoAclaracion().getId() == MotivoAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO.getId()){
					motivo.setDetalleAclaracion(motivos.getCreditoDescontado());
				}
				listMotivos.add(motivo);
			}
		}
  }
  
	public List<MotivoAclaracion> cargarMotivosAclaracion(MotivoAclaracionVO motivos) {
		List<MotivoAclaracion> listMotivos = new ArrayList<MotivoAclaracion>();
		  cargarMotivosAclaracionStep01(motivos, listMotivos);
		if (motivos.getMotivosAclaracionAfore() != null) {
			List<TiposAclaracionEnum> motivosAfore = TiposAclaracionEnum.obtenerAclaracionesPorClaves(
							TiposAclaracionEnum.Dependencia.AFORE, motivos.getMotivosAclaracionAfore());
			Institucion institucion = new Institucion();
			institucion.setIdInstitucion(Long.valueOf(InstitucionEnum.AFORE.getId()).intValue());
			institucion.setNombreInstitucion(InstitucionEnum.AFORE.toString());
			for (TiposAclaracionEnum aclaracion : motivosAfore) {
				MotivoAclaracion motivo = new MotivoAclaracion();
				motivo.setIdMotivoAclaracion(Long.valueOf(aclaracion.getMotivoAclaracion().getId()).intValue());
				motivo.setInstitucion(institucion);
				motivo.setDescripcionMotivo(aclaracion.getMensaje());
				listMotivos.add(motivo);
			}
		}
		if (motivos.getOtro() != null && motivos.getOtro() != ""){
			MotivoAclaracion motivo = new MotivoAclaracion();
			Institucion institucion = new Institucion();
			institucion.setIdInstitucion(Long.valueOf(InstitucionEnum.OTRO.getId()).intValue());
			institucion.setNombreInstitucion(InstitucionEnum.OTRO.toString());
			motivo.setIdMotivoAclaracion(Long.valueOf(MotivoAclaracionEnum.OTRO.getId()).intValue());
			motivo.setInstitucion(institucion);
			motivo.setDescripcionMotivo(InstitucionEnum.OTRO.toString());
			motivo.setDetalleAclaracion(motivos.getEspecificacion());
			listMotivos.add(motivo);
		}
		return listMotivos;
	}
  
private void generarParametrosReporteStep01(TramiteCorreccionCurp tramite,
      Map<String, Object> parametros){
      CorreoElectronico correo = null;
        TelefonoFijo telefono = null;
      if (tramite.getPersona().getMediosContacto() != null
                && !tramite.getPersona().getMediosContacto().isEmpty()) {
            for (MedioContacto med : tramite.getPersona().getMediosContacto()) {
                if (med instanceof CorreoElectronico) {
                    correo = (CorreoElectronico) med;
                }

                if (med instanceof TelefonoFijo) {
                    telefono = (TelefonoFijo) med;
                }
            }
        }

        parametros.put(
                "TELEFONO_FIJO",
                telefono != null && telefono.getNumero() != null ? telefono
                        .getNumero() : "");
        parametros.put("MAIL", correo != null ? correo.getCorreo() : "");
    
    }
	
public Map<String, Object> generarParametrosReporte(Solicitud solicitud, Map<String, String> participantes, Set<List<PeriodoMovimientoAfiliatorio>> cuentas){
		
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		
		Locale locMEX = new Locale("es", "MX");
		SimpleDateFormat sdf = new SimpleDateFormat("dd ' / ' MM ' / 'yyyy", locMEX);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		tramite.setPersona(tramite.getPersonaRENAPO());

		parametros.put("CADENA_ORIGINAL", solicitud.getFirmaElectronica().getCadenaOriginal());
		parametros.put("SELLO_DIGITAL", solicitud.getFirmaElectronica().getRecibo());
		parametros.put("SECUENCIA_NOTARIAL", solicitud.getFirmaElectronica().getSecuenciaNotaria());
		parametros.put("NUMERO_SERIE", solicitud.getFirmaElectronica().getSerialCertificado());
		parametros.put("FECHA_FORMATO_DOF", "31 / 07 /2015");
		parametros.put("FOLIO", solicitud.getNoFolioSolicitud());
		parametros.put("FECHA_EXPEDICION", sdf.format(solicitud.getFechaConclusion()));
		parametros.put("NSS", tramite.getPersona().getNss());
		parametros.put("CURP", tramite.getPersona().getCurp());
		parametros.put("NOMBRE", tramite.getPersona().getNombre());
		parametros.put("PRIMER_APELLIDO", tramite.getPersona().getPrimerApellido());
		parametros.put("SEGUNDO_APELLIDO", tramite.getPersona().getSegundoApellido());
		parametros.put("FECHA_NACIMIENTO", tramite.getPersona().getFechaNacimientoFormateada());
		parametros.put("LUGAR_NACIMIENTO", tramite.getPersona().getLugarNacimiento().getNombre());

		generarParametrosReporteStep01(tramite, parametros);
		
		List<TipoRegularizacionNSS> listTipoRegulacion = obtenerClaveNSSReporte(tramite, tramite.getPersona().getNss() );
		
		parametros.put("LISTA_TIPO_NSS",listTipoRegulacion);
		parametros.put("SEXO", tramite.getPersona().getSexo().getDescripcion()/*.getIdSexo().toString()*/);
		parametros.put("RESPONSABLE", participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		parametros.put("AUTORIZADOR", participantes.get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));


		Iterator<List<PeriodoMovimientoAfiliatorio>> perIterator = cuentas.iterator();
		if(perIterator.hasNext()){
			parametros.put("LISTA_DATOS_LABORALES", crearMovimientosAfiliatorios(perIterator.next()));
			LOGGER.debug("-------CDA------ cuenta{}", parametros.get("LISTA_DATOS_LABORALES"));
		}
		
		if(perIterator.hasNext()){
			parametros.put("LISTA_MOVIMIENTO_FINAL", perIterator.next());
			LOGGER.debug("-------CDA------ cuenta{}", parametros.get("LISTA_MOVIMIENTO_FINAL"));
		}
		
		List <String> listMotivosAclaracion = new ArrayList<String>();
		for(MotivoAclaracion motivoAclaracion : tramite.getMotivosAclaracion()){
			if(motivoAclaracion.toString().toUpperCase().contains("DESCUENTO INDEBIDO")|| motivoAclaracion.toString().toUpperCase().contains("OTRO")){
				listMotivosAclaracion.add(motivoAclaracion.getDescripcionMotivo()+": "+motivoAclaracion.getDetalleAclaracion());
			}
		}
		parametros.put("MOTIVO_ACLARACION",listMotivosAclaracion.toString().toUpperCase());
		
		parametros.put("ORIGEN",solicitud.getOrigenSolicitud().getIdTipoSolicitud().toString());
		LOGGER.debug("VALOR DE ORIGEN: ", solicitud.getOrigenSolicitud().getIdTipoSolicitud());
		
		return parametros;		
	}

private List<TipoRegularizacionNSS> obtenerClaveNSSReporte(TramiteCorreccionCurp tramiteCorreccionCurp, String nss){
	List<TipoRegularizacionNSS> claveNSS = new ArrayList<TipoRegularizacionNSS>();
	
	LOGGER.debug("valor de topo NSS: {}",tramiteCorreccionCurp.getCertificacionNSS().getTipoRegularizacionNSS());
	for (TipoRegularizacionNSS idNSS: tramiteCorreccionCurp.getCertificacionNSS().getTipoRegularizacionNSS()){
		TipoRegularizacionNSS tipoRegNss = new TipoRegularizacionNSS();
		if (idNSS.getIdTipoRegularizacionNSS().equals(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getId())) {
			tipoRegNss.setIdTipoRegularizacionNSS(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getClave());
		} else {
			if (idNSS.getIdTipoRegularizacionNSS().equals(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getId())) {
				tipoRegNss.setIdTipoRegularizacionNSS(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getClave());
			}
		}
		tipoRegNss.setDescripcionRegularizacionNSS(nss);
		claveNSS.add(tipoRegNss);
		
	}
	LOGGER.debug("LA CLAVE ES: {}",claveNSS);
	return claveNSS;
}	
private List<PeriodoMovimientoAfiliatorio> crearMovimientosAfiliatorios(List<PeriodoMovimientoAfiliatorio>periodos){
	List<PeriodoMovimientoAfiliatorio> periodoMovimientos = new ArrayList<PeriodoMovimientoAfiliatorio>();
	if(periodos!=null && !periodos.isEmpty()){
		periodoMovimientos.addAll(periodos);
	}else{
		PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio = new PeriodoMovimientoAfiliatorio();
		periodoMovimientoAfiliatorio.setFechaFinalMovimiento(null);
		periodoMovimientoAfiliatorio.setFechaInicioMovimiento(null);
		periodoMovimientoAfiliatorio.setNrp(null);
		periodoMovimientos.add(periodoMovimientoAfiliatorio);
	}
	
	
	return periodoMovimientos;
}

public List<DocumentoProbatorio> cargarDatosTramiteDocumentAsegurado(TramiteCorreccionCurp tramite,DatosHistoriaLaboralVO documenHistoriaLaboralVO){
        List<DocumentoProbatorio> listDocumentos = null;
        if (documenHistoriaLaboralVO != null) {
            listDocumentos = ensamblaDocumentos(documenHistoriaLaboralVO
                    .getDocumentoProbatorioList());
        }
        if (tramite.getAsegurado() != null) {
            List<DocumentoProbatorio> listPrelimiar = tramite.getAsegurado()
                    .getDocumentosProbatorios();
            if (listPrelimiar != null && !listPrelimiar.isEmpty()) {
                for (DocumentoProbatorio doc : listPrelimiar) {
                    listDocumentos.add(doc);
                }

            }
        }
        return listDocumentos;
    }

    
public TramiteCorreccionCurp cargarDatosTramiteNuevo(TramiteCorreccionCurp tramite, NSSVO documentosNss) {
        List<String> listNss = new ArrayList<String>();
        String nssTramite = documentosNss.getNSS();
        listNss.add(nssTramite);
        tramite.setListaNSS(listNss);

        List<DocumentoProbatorio> listDocumentos = ensamblaDocumentos(documentosNss.getDocumentoProbatorioList());        
        tramite.setDocumentosProbatorios(listDocumentos);
        return tramite;
    }

public List<DocumentoProbatorio> ensamblaDocumentos(List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> documentos) {
        List<DocumentoProbatorio> listDocumentos = new ArrayList<DocumentoProbatorio>();
        for (mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio d : documentos) {
            TipoDocumentoProbatorio tipoDoc = new TipoDocumentoProbatorio();
            tipoDoc.setIdTipoDocumentoProbatorio(d.getTipoDocumento());
            tipoDoc.setDescripcion(d.getDesDocumento());
            
            Documento doc = new Documento();
            doc.setDesDocumento(d.getDesDocumento());
            doc.setCveIdDocumento(d.getCveIdDocumento());
            
            DocumentoPorTipo tipoDocumento = new DocumentoPorTipo();
            tipoDocumento.setIdDocumentoPorTipo(d.getIdDocumentoPorTipo());
            tipoDocumento.setIdDocumentoPorTipoHashed(String.valueOf(d.getIdDocumentoPorTipo()));
            tipoDocumento.setTipoDocumentoProbatorio(tipoDoc);
            tipoDocumento.setDocumento(doc);
            
            DocumentoProbatorio documentoProbatorio = new DocumentoProbatorio();
            documentoProbatorio.setDocumentoPorTipo(tipoDocumento);
            documentoProbatorio.setNomNombreDocumento(d.getNombre());
            documentoProbatorio.setBovedaDocId(d.getIdDocBoveda());
            LOGGER.debug("--CDA-- ID DOCUMENTO POR TIPO: " + tipoDocumento.getIdDocumentoPorTipo());
            LOGGER.debug("--CDA-- ID DOCUMENTO POR TIPO HASHED" + tipoDocumento.getIdDocumentoPorTipoHashed());
            listDocumentos.add(documentoProbatorio);
        }
        return listDocumentos;
    }

    public Solicitud orderByTramite(Solicitud solicitudActiva){
        TramiteCorreccionCurp firstTramite = new TramiteCorreccionCurp();
        Iterator<Tramite> iterador=solicitudActiva.getTramites().iterator();
        
        while(iterador.hasNext()){
            TramiteCorreccionCurp tramiteCurp = ((TramiteCorreccionCurp)iterador.next());
            LOGGER.debug("--CDA-- tramites documentos {}",tramiteCurp.getDocumentosProbatorios());
            if(EstadoTramiteEnum.CANCELADO.getCodigo().intValue()==tramiteCurp.getEstadoTramite().getIdEstadoTramitePersona().intValue()){
                iterador.remove();
            }else {
                if(tramiteCurp.getPersonaRENAPO()!=null){
                    firstTramite=tramiteCurp;
                    iterador.remove();
                }
            }
        }
        solicitudActiva.getTramites().add(0, firstTramite);
        return solicitudActiva;
    }
    
    private void procesarInformacionConsultaStep01(
      ConsultaSolicitudTramiteVO informacionConsulta,
      Solicitud sol, TramiteCorreccionCurp tramiteCDA, StringBuffer listaNss
            ){
      informacionConsulta.setSubdelegacion(sol.getSubdelegacion() != null ?
              sol.getSubdelegacion().getClave()
              + "-" + sol.getSubdelegacion().getDescripcion() :
              "");
      informacionConsulta.setEstatusDescarga(sol.getEstadoSolicitud() != null ?
              sol
                      .getEstadoSolicitud().getIdEstadoSolicitud()
                      .equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()) :
              false);
      informacionConsulta.setNss(listaNss != null ?
              listaNss.toString() :
              "");
      informacionConsulta
              .setFechaSolicitud(
                      flujoTrabajoBusiness.getTareaActivaPorIdTramite(
                              tramiteCDA.getTramiteId()) != null ?
                      flujoTrabajoBusiness.getTareaActivaPorIdTramite(
                              tramiteCDA.getTramiteId()).getInicioTramite().
                              getFechaSolicitud() :
                      sol.getFechaSolicitudParse());
      if (sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(
              EstadoSolicitudEnum.CANCELADA.getCodigo())) {
        informacionConsulta.setEstatusMot(true);
        informacionConsulta.setMotivoCancelacion(obtenerUltimoMotivo(tramiteCDA
                .getObservacionesSubdelegacion()).toUpperCase());
        informacionConsulta.setIdEstadoTramite(EstadoSolicitudEnum.CANCELADA
                .getCodigo());
      }
      if (sol.getTramites().get(0).getEstadoTramite().
              getIdEstadoTramitePersona().equals(
                      EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo())
              && (tramiteCDA.getObservacionesSubdelegacion() != null
              && !tramiteCDA.getObservacionesSubdelegacion().isEmpty()
              && tramiteCDA.getObservacionesSubdelegacion().get(0) != null)) {
        informacionConsulta.setEstatusMot(true);
        informacionConsulta.setMotivoCancelacion(obtenerUltimoMotivo(tramiteCDA.
                getObservacionesSubdelegacion()).toUpperCase());
        informacionConsulta.setStatus(EstadoNegocioEnum.
                obtenerDescripcionNegocio(
                        EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo()));
        informacionConsulta.setIdEstadoTramite(
                EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo());
        informacionConsulta.setInfAdicional(obtenerUltimoMotivo(tramiteCDA.
                getObservacionesSubdelegacion()));
      }
    }
    
    public ConsultaSolicitudTramiteVO procesarInformacionConsulta(Fisica persona, Solicitud sol, TramiteCorreccionCurp tramiteCDA) {
        ConsultaSolicitudTramiteVO informacionConsulta = new ConsultaSolicitudTramiteVO();

        informacionConsulta.setCurp(persona.getCurp());
        
        informacionConsulta.setFolio(sol.getNoFolioSolicitud());
        informacionConsulta.setNombre(persona.getNombreCompleto());
        
        StringBuffer listaNss = new StringBuffer("");
//        for(Tramite tramite:sol.getTramites()){
            TramiteCorreccionCurp trCDA=(TramiteCorreccionCurp)tramiteCDA;
            if(trCDA.getListaNssCorreccion() != null ) {
    	        for(CorreccionNSS nss:trCDA.getListaNssCorreccion()){
                       	if(listaNss!=null && !listaNss.toString().equals("")){
    	        		listaNss.append("<br>");
    	        	}
    	        	listaNss.append(nss.getNss());
    	        }
            }
//        }
        
        if(sol.getEstadoSolicitud() != null)
        {
            if(sol.getEstadoSolicitud().getDescripcion().trim().equals(STATUS_SOL_PROC))
                informacionConsulta.setStatus(STATUS_SOL);
            else
            informacionConsulta.setStatus(sol.getEstadoSolicitud().getDescripcion().trim());
        }
        else
        informacionConsulta.setStatus("");
        
        informacionConsulta.setIdTramite(tramiteCDA.getTramiteId().toString());

        procesarInformacionConsultaStep01(informacionConsulta, sol, tramiteCDA,
                listaNss);
                
        return informacionConsulta;
    }
        
    private String obtenerUltimoMotivo(List<ObservacionesSubdelegacion> motivos) {
        String motivo = "";
        if (motivos != null && !motivos.isEmpty()) {
            motivo = motivos.get(motivos.size() - 1).getDetalle() != null ? motivos
                    .get(motivos.size() - 1).getDetalle() : " ";
        }
        return motivo;
    }

}

