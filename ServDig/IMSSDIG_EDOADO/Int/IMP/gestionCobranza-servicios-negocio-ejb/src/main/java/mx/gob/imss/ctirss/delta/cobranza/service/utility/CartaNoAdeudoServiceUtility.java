package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.DatosValidacionCartaNoAdeudoWrapper;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.ParametrosServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.RespuestaOpinion32DEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite32D;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.core.io.ClassPathResource;

@Stateless(name = "cartaNoAdeudoServiceUtility", mappedName = "cartaNoAdeudoServiceUtility")
public class CartaNoAdeudoServiceUtility extends AbstractServiceUtility
	implements CartaNoAdeudoServiceUtilityLocal{

	@EJB
	private ParametrosServiceBusinessLocal parametrosServiceBusiness;

    @EJB
    private PersonaBusinessRemote personaBusiness;

	private static final String DESC_TRAMITE_SOLICITUD = "Carta de No Adeudo Art. 32D";
	private static final String PATH_SUBREPORTES = "reportes/cartaNoAdeudo/";
	private static final String RESOLUCION_POSITIVA = PATH_SUBREPORTES+"CartaNoAdeudoP.jasper";
	private static final String RESOLUCION_NEGATIVA = PATH_SUBREPORTES+"CartaNoAdeudoN.jasper";
	private static final String SIN_RESOLUCION = PATH_SUBREPORTES+"CartaNoAdeudoSinOpinion.jasper";
	private static final String RECONSTRUIR_RESOLUCION = PATH_SUBREPORTES+"CartaNoAdeudoReconstruir.jasper";
	private static final String MSG_ERROR = "Ocurri&oacute; un error al generar la carta de no adeudo.";

    private static final String CURP_GENERICA = "XEXX010101HNEXXXA4";
    private static final String JUICIO_EN_PROCESO = "El Contribuyente cuenta con juicio en proceso.";
    private static final String AUDITORIA_EN_PROCESO = "El Contribuyente cuenta con auditoria en proceso.";
    private static final String CONVENIO_EN_PROCESO = "El Contribuyente cuenta con convenio en proceso.";
	
	@Override
	public Solicitud prepararSolicitudCartaNoAdeudo(Persona persona,
			String usuario, DatosValidacionCartaNoAdeudoWrapper wrapper,
			Date fechaInicio, Long idOrigen) {
		Solicitud solicitud = new Solicitud();
		Calendar cal = Calendar.getInstance();
		Date fechaActual = cal.getTime();

		solicitud.setFechaSolicitud(fechaInicio);
		solicitud.setFechaPresentacion(fechaInicio);
		
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		
		if(idOrigen.equals(OrigenSolicitudEnum.INTERNET.getId()))
			origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		else if(idOrigen.equals(OrigenSolicitudEnum.VENTANILLA.getId()))
			origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
		
		solicitud.setOrigenSolicitud(origenSolicitud);
		
		solicitud.setFechaConclusion(fechaActual);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(
				TipoSolicitudEnum.CARTA_NO_ADEUDO.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
				EstadoSolicitudEnum.ATENDIDA.getCodigo());
		
		Usuario solicitante = new Usuario();
		solicitante.setUsuario(usuario);
		solicitud.setSolicitante(solicitante);
		
		solicitud.setTramites(new ArrayList<Tramite>());
		Tramite32D tramite = new Tramite32D();
		tramite.setPersonaFM(persona);
		tramite.setRespuestaOpinion(wrapper.getRespuestaOpinion().getDesc());
		tramite.setResultado(wrapper.getRespuestaOpinion().isOpinion());
		
		if(wrapper.getPatrones() !=null && !wrapper.getPatrones().isEmpty()) {
			tramite.setPatrones(getNrpFromSujetoObligado(wrapper.getPatrones()));
			tramite.setPatronesVigentes(getNrpFromSujetoObligado(wrapper.getPatronesVigentes()));
			tramite.setPatronesHuelga(getNrpFromSujetoObligado(wrapper.getPatronesHuelga()));
			tramite.setPatronesBaja(getNrpFromSujetoObligado(wrapper.getPatronesBaja()));
			tramite.setNumPatrones(wrapper.getPatrones().size());
			tramite.setNumPatronesVigentes(wrapper.getPatronesVigentes().size());
			tramite.setNumPatronesHuelga(wrapper.getPatronesHuelga().size());
			tramite.setNumPatronesBaja(wrapper.getPatronesBaja().size());
		}
		
		tramite.setNumTrabajadores(wrapper.getNumTrabajadores());
		tramite.setTieneAdeudos(wrapper.isTieneAdeudos());
		
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(
				EstadoTramiteEnum.CERRADO.getCodigo());
		tramite.getEstadoTramite().setDescripcion(
				EstadoTramiteEnum.CERRADO.getDescripcion());
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(
				TipoTramiteEnum.CARTA_NO_ADEUDO.getCodigo());
		tramite.setFechaTramite(fechaInicio);
		tramite.setFechaPresentacion(fechaInicio);
		tramite.setFechaConclusion(fechaActual);
		tramite.setIndRatificado(false);
		
		solicitud.getTramites().add(tramite);
		
		return solicitud;
	}

	@Override
	public String generarCadenaOriginal(Solicitud solicitud, Persona persona, String usuario) {
		StringBuffer contenidoAFirmar = new StringBuffer();
		Date fechaInicioVigencia = solicitud.getFechaSolicitud();
        Date fechaFinVigencia;

        final String diasIncrementoCURPGenerica = "DIAS_INC_GEN_32D";
        final String diasIncremento = "DIAS_INC_32D";

        List<String> paramKeys = new ArrayList<String>(2);
        paramKeys.add(diasIncrementoCURPGenerica);
        paramKeys.add(diasIncremento);


        Map<String, String> params = this.parametrosServiceBusiness.obtenerGrupoParametros(paramKeys);
        this.log.debug("Parametros para 32D -> " + params);

        String diasIncGen = params.get(diasIncrementoCURPGenerica);
        String diasInc = params.get(diasIncremento);

        log.info("diasIncrementoGen: "+diasIncGen+ "  diasIncremento: "+diasInc);

       Integer dias_incremento_curp_generica = Integer.parseInt( diasIncGen);
       Integer dias_incremento = Integer.parseInt(diasInc);

        if(usuario.trim().equals(CURP_GENERICA)){
            this.log.info("Curp generica: 1 dia");
            fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento_curp_generica);
        }else{
            this.log.info("Curp normal: 30 dias");
            fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento);
        }

		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_TRAMITE_SOLICITUD).append("|");
		// Fecha Electronica
		String strFechaElectronica = obtenerFechaLargaFormateada(solicitud.getFechaSolicitud());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");		
		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		// Nombre O razon social
		String nombreRazonSocialPersona = personaBusiness.getRazonSocial(persona.getIdPersona(), persona.getTipoPersona().getIdTipoPersona());
		
		String nombreRazonSocial = personaBusiness.getRazonSocial(persona.getRfc(),nombreRazonSocialPersona);
		
//		if (persona instanceof Fisica) {
//			nombreRazonSocial = ((Fisica)persona).getNombreCompleto();
//		} else {
//			nombreRazonSocial = ((Moral)persona).getRazonSocial();
//            if (nombreRazonSocial.contains("#")) {
//				nombreRazonSocial = partiallyNormalize(nombreRazonSocial);
//				nombreRazonSocial.toUpperCase();
//			}
//		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(nombreRazonSocial).append("|");
		// CURP
		contenidoAFirmar.append("CURP:");
		if (persona instanceof Fisica) {
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");			
		} else {
			contenidoAFirmar.append("|");
		}
		
		
		contenidoAFirmar.append("Opinion:");
		contenidoAFirmar.append(getOpinionFromSolicitud(solicitud).getDesc().toUpperCase()).append("|");
		contenidoAFirmar.append("FechaInicioVigencia:");
		contenidoAFirmar.append(obtenerFechaLargaFormateada(fechaInicioVigencia)).append("|");
		contenidoAFirmar.append("FechaFinVigencia:");
		contenidoAFirmar.append(obtenerFechaFormateada(fechaFinVigencia)+", 23:59:59").append("||");
		
		return contenidoAFirmar.toString();
	}
    
    public static String partiallyNormalize(String string){
        string = string.replace('#', '\002');
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        string = string.replace('\002', '\u00F1');
        return string;
    }

	@Override
	public FirmaElectronica generarFirmaElectronica(RespuestaFirmadoSimple selloDigital, String cadenaOriginal) {
		FirmaElectronica firmaElectronica = new FirmaElectronica();
        firmaElectronica.setCadenaOriginal(cadenaOriginal);
        firmaElectronica.setReciboNotarial(selloDigital.getTramite());
        firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
        firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
        firmaElectronica.setRecibo(selloDigital.getSello());
        firmaElectronica.setUrlAcuseFirma("");
        firmaElectronica.setIniciaVigenciaCertificado(new Date());
        firmaElectronica.setFinVigenciaCertificado(new Date());
        return firmaElectronica;
	}

	@Override
	public byte[] prepararTemplateCartaNoAdeudo(Persona persona,
			Solicitud solicitud, FirmaElectronica firmaElectronica,
			BufferedImage imagenCodeQR, DatosValidacionCartaNoAdeudoWrapper wrapper,
			String usuario, Boolean juicioEnProceso, Boolean auditoriaEnProceso
			, Boolean convenioEnProceso)
			throws EstadoAdeudoException {
		
		byte[] documento = null;
			
		try {
			// Se define que tipo de template se usara, si el de respuesta POSITIVA o NEGATIVA
			String reporte = getReporteResolucion(getOpinionFromSolicitud(solicitud));
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Map<String, Object> parameters = getParametrosDocumento(persona, solicitud, firmaElectronica, 
				imagenCodeQR, wrapper,usuario);

			if(juicioEnProceso){
                parameters.put("leyendaJuicio", JUICIO_EN_PROCESO);
            }else{
                parameters.put("leyendaJuicio", "");
            }

            if(auditoriaEnProceso){
                parameters.put("leyendaAuditoria", AUDITORIA_EN_PROCESO);
            }else{
                parameters.put("leyendaAuditoria", "");
            }
            
            if(convenioEnProceso){
                parameters.put("leyendaConvenio", CONVENIO_EN_PROCESO);
            }else{
                parameters.put("leyendaConvenio", "");
            }

            log.debug("+++leyenda Juicio: "+parameters.get("leyendaJuicio"));
            log.debug("+++leyenda Auditoria: "+parameters.get("leyendaAuditoria"));
            log.debug("+++leyenda Convenio: "+parameters.get("leyendaConvenio"));
            
            log.debug("+++reporte: "+reporte);
            

            JasperReport report = (JasperReport) JRLoader.loadObject(
				new ClassPathResource(reporte).getInputStream());
            
            log.debug("+++report: "+report.getName());
            
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			exporter.exportReport();
			documento = byteArrayOutputStream.toByteArray();		
		} catch (Exception e) {
			e.printStackTrace();
			log.info("-----------");
			log.error(e);
			throw new EstadoAdeudoException(MSG_ERROR);
		}
		
		if(documento==null)
			throw new EstadoAdeudoException(MSG_ERROR);

		return documento;
	}
		
	@Override
	public Date obtenerFechaFinVigencia(Date fechaSolicitud, int diasIncremento){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaSolicitud);
		calendar.add(Calendar.DATE, diasIncremento);
		return calendar.getTime();
	}
	
	
	private Map<String, Object> getParametrosDocumento(Persona persona,
			Solicitud solicitud, FirmaElectronica firmaElectronica,
			BufferedImage imagenCodeQR, DatosValidacionCartaNoAdeudoWrapper wrapper,
            String usuario) {

        Map<String, Object> parameters = new HashMap<String, Object>();

		final String numAcuerdoKey = "NUM_ACUERDO_32D";
		final String fecAcuerdoKey = "FEC_ACUERDO_32D";
        final String diasIncrementoCURPGenerica = "DIAS_INC_GEN_32D";
        final String diasIncremento = "DIAS_INC_32D";


        List<String> paramKeys = new ArrayList<String>(2);
        paramKeys.add(numAcuerdoKey);
        paramKeys.add(fecAcuerdoKey);
        paramKeys.add(diasIncrementoCURPGenerica);
        paramKeys.add(diasIncremento);

        Map<String, String> params = this.parametrosServiceBusiness.obtenerGrupoParametros(paramKeys);

        this.log.debug("Parametros para 32D -> " + params);

		String fecha = obtenerFechaFormateada(solicitud.getFechaSolicitud());
		String hora = obtenerHoraFormateada(solicitud.getFechaSolicitud());
		Date fechaInicioVigencia = solicitud.getFechaSolicitud();
        Date fechaFinVigencia;

        Integer dias_incremento_curp_generica = Integer.parseInt( params.get(diasIncrementoCURPGenerica));
        Integer dias_incremento = Integer.parseInt( params.get(diasIncremento));

		if(usuario.trim().equals(CURP_GENERICA)){
            this.log.info("Curp generica: 1 dia");
            fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento_curp_generica);
        }else{
            this.log.info("Curp generica: 30 dias");
            fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento);
        }

//		Date fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, 30);

        String nombreRazonSocialPersona = personaBusiness.getRazonSocial(persona.getIdPersona(), persona.getTipoPersona().getIdTipoPersona());
		
		String nombreRazonSocial = personaBusiness.getRazonSocial(persona.getRfc(),nombreRazonSocialPersona);
		
		
//		if (persona instanceof Fisica) {
//			nombreRazonSocial = ((Fisica)persona).getNombreCompleto();
//		} else {
//			nombreRazonSocial = ((Moral)persona).getRazonSocial();
//            if (nombreRazonSocial.contains("#")) {
//				nombreRazonSocial = partiallyNormalize(nombreRazonSocial);
//				nombreRazonSocial.toUpperCase();
//			}
//		}
		


		parameters.put("SUBREPORT_DIR", new ClassPathResource(PATH_SUBREPORTES).getPath());
		parameters.put("IMAGENES_DIR", new ClassPathResource("reportes/").getPath());
		
		parameters.put("imagenQR", imagenCodeQR);
		parameters.put("cadenaOriginal", firmaElectronica.getCadenaOriginal());
		parameters.put("selloDigital", firmaElectronica.getRecibo());
		parameters.put("secuenciaNotaria", firmaElectronica.getSecuenciaNotaria());
		parameters.put("numSerialCertificado", firmaElectronica.getSerialCertificado());

		parameters.put("fechaSolicitud", fecha);
		parameters.put("horaSolicitud", hora);
		parameters.put("folioSolicitud", solicitud.getNoFolioSolicitud());
		parameters.put("rfc", persona.getRfc());
		parameters.put("nombreRS", nombreRazonSocial);
		parameters.put("fechaInicioVigencia", obtenerFechaFormateada(fechaInicioVigencia));
		parameters.put("fechaFinVigencia", obtenerFechaFormateada(fechaFinVigencia)+", 23:59:59");

		parameters.put("adeudoCreditosImss", wrapper.getAdeudoCreditosImss());
		parameters.put("adeudoCreditosRcv", wrapper.getAdeudoCreditosRcv());
		parameters.put("adeudoBaja251Imss", wrapper.getAdeudoBaja251Imss());
		parameters.put("adeudoBaja251Rcv", wrapper.getAdeudoBaja251Rcv());
		parameters.put("adeudoHuelgaImss", wrapper.getAdeudoHuelgaImss());
		parameters.put("adeudoHuelgaRcv", wrapper.getAdeudoHuelgaRcv());

		parameters.put("numTrabajadores", wrapper.getNumTrabajadores());
		String respuestaOpinion = ""+wrapper.getRespuestaOpinion().getId();
		log.info("respuestaOpinionReporte: "+respuestaOpinion);
		parameters.put("respuestaOpinion",respuestaOpinion);
		parameters.put("patrones", wrapper.getPatrones());
		parameters.put("numAcuerdo", params.get(numAcuerdoKey));
		parameters.put("fechaAcuerdo", params.get(fecAcuerdoKey));

		return parameters;
	}
	
	private String obtenerFechaFormateada(Date fechaSolicitud){
		if(fechaSolicitud==null)
			fechaSolicitud=new Date();
		
		return DateUtils.dateToStringConFormato(fechaSolicitud, "dd' de 'MMMM' de 'yyyy");
	}
	
	private String obtenerHoraFormateada(Date fechaSolicitud){
		if(fechaSolicitud==null)
			fechaSolicitud=new Date();
		
		return DateUtils.dateToStringConFormato(fechaSolicitud, "HH:mm");
	}
	
	private String obtenerFechaLargaFormateada(Date fecha){
		if(fecha==null)
			fecha=new Date();
		
		return DateUtils.dateToStringConFormato(fecha, "dd 'de' MMMM yyyy, HH:mm:ss");
	}
	
	private String getReporteResolucion(RespuestaOpinion32DEnum opinion) {
		
		String reporte = null;
		if(opinion.isOpinion()) { //si es true se evalurada que reporte usar si el positivo o el de sin opinion
			if(opinion.getId() == 1) { //en caso de ser 1 es el positivo
				reporte = RESOLUCION_POSITIVA;
			} else { //en caso de ser otro es que no hay resolucion
				reporte = SIN_RESOLUCION;
			}
			
		} else { //en caso de ser false se usa el reporte negativo
			reporte = RESOLUCION_NEGATIVA;
		}
		
		return reporte;
	}	
	
	private RespuestaOpinion32DEnum getOpinionFromSolicitud(Solicitud solicitud) {
		
		RespuestaOpinion32DEnum opinion = null;
		
		for(Tramite tramite : solicitud.getTramites()) {
			if(tramite instanceof Tramite32D) {
				Tramite32D tram32 = (Tramite32D) tramite;
				opinion = RespuestaOpinion32DEnum.obtenerEnumByName(tram32.getRespuestaOpinion());
				break;

			}
		}		
		
		return opinion;
	}
	
	private List<String> getNrpFromSujetoObligado(List<SujetoObligado> patrones) {

		List<String> nrps = new ArrayList<String>(patrones.size());
		
		StringBuffer nrp = new StringBuffer();
		
		for(SujetoObligado so : patrones) {
			nrp.append(so.getNumeroRegistroPatronal());
			if (so.getNumeroRegistroPatronal().length() == 8) {
				nrp.append(so.getModalidad().getNumModalidad());
				nrp.append(so.getDigVerificador());
			} else if (so.getNumeroRegistroPatronal().length() == 10){
				nrp.append(so.getDigVerificador());
			}
			nrps.add(nrp.toString());
			nrp.delete(0, nrp.length());
		}
		
		Collections.sort(nrps);
		
		return nrps;
	}

	private Map<String,String> obtenerFechaTramite() {
		Map<String,String> salida = new HashMap<String, String>();
		Date fecha = new Date();
		if (fecha != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			String mes = obtenerNombreMes(cal.get(Calendar.MONTH));
			int anio = cal.get(Calendar.YEAR);
			int hora = cal.get(Calendar.HOUR_OF_DAY);
			int minuto = cal.get(Calendar.MINUTE);
			int segundo = cal.get(Calendar.SECOND);
			salida.put("fechaTramiteDias",String.format("%02d",dia));
			salida.put("fechaTramite",String.format("%s del %d %02d:%02d:%02d",mes, anio, hora, minuto, segundo));
			return salida;
		}
		return salida;
	}

	private String obtenerNombreMes(int mes) {
		String[] nombresMeses = {
				"enero", "febrero", "marzo", "abril", "mayo", "junio",
				"julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
		};
		if (mes >= 0 && mes < 12) {
			return nombresMeses[mes];
		} else {
			return "mes no disponible";
		}
	}

	private Map<String, Object> getParametrosDocumentoReconstruir(Persona persona,
													   Solicitud solicitud, FirmaElectronica firmaElectronica,
													   BufferedImage imagenCodeQR, DatosValidacionCartaNoAdeudoWrapper wrapper,
													   String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		final String numAcuerdoKey = "NUM_ACUERDO_32D";
		final String fecAcuerdoKey = "FEC_ACUERDO_32D";
		final String diasIncrementoCURPGenerica = "DIAS_INC_GEN_32D";
		final String diasIncremento = "DIAS_INC_32D";

		List<String> paramKeys = new ArrayList<String>(2);
		paramKeys.add(numAcuerdoKey);
		paramKeys.add(fecAcuerdoKey);
		paramKeys.add(diasIncrementoCURPGenerica);
		paramKeys.add(diasIncremento);

		Map<String, String> params = this.parametrosServiceBusiness.obtenerGrupoParametros(paramKeys);
		Tramite32D tramite = obtenerTramite32D(solicitud);
		this.log.debug("Parametros para 32D -> " + params);

		String fechaPresentacion = obtenerFechaFormateada(tramite.getFechaPresentacion());
		String horaPresentacion = obtenerHoraFormateada(tramite.getFechaPresentacion());
		Date fechaInicioVigencia = solicitud.getFechaSolicitud();
		Date fechaFinVigencia;

		Integer dias_incremento_curp_generica = Integer.parseInt( params.get(diasIncrementoCURPGenerica));
		Integer dias_incremento = Integer.parseInt( params.get(diasIncremento));

		if(usuario.trim().equals(CURP_GENERICA)){
			this.log.info("Curp generica: 1 dia");
			fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento_curp_generica);
		}else{
			this.log.info("Curp generica: 30 dias");
			fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento);
		}

		String nombreRazonSocial = obtenerRazonSocialReconstruir(persona);
		log.info("nombreRazonSocial--"+nombreRazonSocial);


		parameters.put("SUBREPORT_DIR", new ClassPathResource(PATH_SUBREPORTES).getPath());
		parameters.put("IMAGENES_DIR", new ClassPathResource("reportes/").getPath());

		parameters.put("imagenQR", imagenCodeQR);
		parameters.put("cadenaOriginal", firmaElectronica.getCadenaOriginal());
		parameters.put("selloDigital", firmaElectronica.getRecibo());
		parameters.put("secuenciaNotaria", firmaElectronica.getSecuenciaNotaria());
		parameters.put("numSerialCertificado", firmaElectronica.getSerialCertificado());

		parameters.put("fechaSolicitud", fechaPresentacion);
		parameters.put("horaSolicitud", horaPresentacion);
		parameters.put("folioSolicitud", solicitud.getNoFolioSolicitud());
		parameters.put("rfc", persona.getRfc());
		parameters.put("nombreRS", nombreRazonSocial);
		parameters.put("fechaInicioVigencia", obtenerFechaFormateada(fechaInicioVigencia));
		parameters.put("fechaFinVigencia", obtenerFechaFormateada(fechaFinVigencia)+", 23:59:59");

		Map<String,String> fechasCad = obtenerFechaTramite();

		String respuestaOpinion = ""+wrapper.getRespuestaOpinion().getDesc();
		log.info("respuestaOpinionReporte: "+respuestaOpinion);
		parameters.put("respuestaOpinion",respuestaOpinion);
		parameters.put("numAcuerdo", params.get(numAcuerdoKey));
		parameters.put("fechaAcuerdo", params.get(fecAcuerdoKey));
		parameters.put("fechaTramiteDias", fechasCad.containsKey("fechaTramiteDias") ? (String) fechasCad.get("fechaTramiteDias") :"");
		parameters.put("fechaTramite", fechasCad.containsKey("fechaTramite") ? (String) fechasCad.get("fechaTramite") :"");

		return parameters;
	}

	@Override
	public String generarCadenaOriginalReconstruida(Solicitud solicitud, Persona persona, String usuario) {
		StringBuffer contenidoAFirmar = new StringBuffer();
		Date fechaInicioVigencia = solicitud.getFechaSolicitud();
		Date fechaFinVigencia;
		final String diasIncrementoCURPGenerica = "DIAS_INC_GEN_32D";
		final String diasIncremento = "DIAS_INC_32D";
		List<String> paramKeys = new ArrayList<String>(2);
		paramKeys.add(diasIncrementoCURPGenerica);
		paramKeys.add(diasIncremento);
		Map<String, String> params = this.parametrosServiceBusiness.obtenerGrupoParametros(paramKeys);
		this.log.debug("Parametros para 32D -> " + params);
		String diasIncGen = params.get(diasIncrementoCURPGenerica);
		String diasInc = params.get(diasIncremento);
		log.info("diasIncrementoGen: "+diasIncGen+ "  diasIncremento: "+diasInc);
		Integer dias_incremento_curp_generica = Integer.parseInt( diasIncGen);
		Integer dias_incremento = Integer.parseInt(diasInc);
//		if(usuario.trim().equals(CURP_GENERICA)){
//			this.log.info("Curp generica: 1 dia");
//			fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento_curp_generica);
//		}else{
//			this.log.info("Curp normal: 30 dias");
//			fechaFinVigencia = obtenerFechaFinVigencia(fechaInicioVigencia, dias_incremento);
//		}
		this.log.info(" Fecha fin vigencia = fecha Inicio Vigencia");
		fechaFinVigencia = fechaInicioVigencia;
		
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_TRAMITE_SOLICITUD).append("|");
		// Fecha Electronica
		Tramite32D tramite = obtenerTramite32D(solicitud);
		String strFechaElectronica = obtenerFechaLargaFormateada(tramite.getFechaPresentacion());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		// Nombre O razon social
		String nombreRazonSocial = obtenerRazonSocialReconstruir(persona);

		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(nombreRazonSocial).append("|");
		// CURP
		contenidoAFirmar.append("CURP:");
		if (persona instanceof Fisica) {
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
		} else {
			contenidoAFirmar.append("|");
		}

		contenidoAFirmar.append("Opinion:");
		contenidoAFirmar.append(getOpinionFromSolicitudReconstruida(solicitud).getDesc().toUpperCase()).append("|");
		contenidoAFirmar.append("FechaInicioVigencia:");
		contenidoAFirmar.append(obtenerFechaLargaFormateada(fechaInicioVigencia)).append("|");
		contenidoAFirmar.append("FechaFinVigencia:");
		contenidoAFirmar.append(obtenerFechaFormateada(fechaFinVigencia)+", 23:59:59").append("||");

		return contenidoAFirmar.toString();
	}

	private RespuestaOpinion32DEnum getOpinionFromSolicitudReconstruida(Solicitud solicitud) {
		RespuestaOpinion32DEnum opinion = null;
		for(Tramite tramite : solicitud.getTramites()) {
			if(tramite.getTipoTramite().getIdTipoTramite() == 116 && (tramite instanceof Tramite32D)){
				Tramite32D tram32 = (Tramite32D) tramite;
				opinion = RespuestaOpinion32DEnum.obtenerEnumByName(tram32.getRespuestaOpinion());
				break;
			}
		}
		return opinion;
	}

	private Tramite32D obtenerTramite32D(Solicitud solicitud) {
		Tramite32D tramite32D = null;
		for(Tramite tramite : solicitud.getTramites()) {
			if(tramite.getTipoTramite().getIdTipoTramite() == 116 && (tramite instanceof Tramite32D)){
				return (Tramite32D) tramite;
			}
		}
		return tramite32D;
	}

	private String obtenerRazonSocialReconstruir(Persona persona){
		//String nombreRazonSocial = "";
		
		String nombreRazonSocialPersona = personaBusiness.getRazonSocial(persona.getIdPersona(), persona.getTipoPersona().getIdTipoPersona());
		
		String nombreRazonSocial = personaBusiness.getRazonSocial(persona.getRfc(),nombreRazonSocialPersona);
		
		
//		if (persona instanceof Fisica) {
//			nombreRazonSocial = ((Fisica)persona).getNombreCompleto();
//		} else {
//			String moralP1 =  ((Moral)persona).getRazonSocial();
//			String moralP2 = ((Moral)persona).getTipoSociedad()!=null && ((Moral)persona).getTipoSociedad().getDescripcion()!=null ? " "+((Moral)persona).getTipoSociedad().getDescripcion() :"";
//			nombreRazonSocial = moralP1+moralP2;
//			log.info("");
//			if (nombreRazonSocial.contains("#")) {
//				nombreRazonSocial = partiallyNormalize(nombreRazonSocial);
//				nombreRazonSocial.toUpperCase();
//			}
//		}
		return nombreRazonSocial;
	}

	private String getReporteReconstruirResolucion(RespuestaOpinion32DEnum opinion) {
		return RECONSTRUIR_RESOLUCION;
	}

	@Override
	public byte[] reconstruirTemplateCartaNoAdeudo(Persona persona,
												Solicitud solicitud, FirmaElectronica firmaElectronica,
												BufferedImage imagenCodeQR, DatosValidacionCartaNoAdeudoWrapper wrapper,
												String usuario, Boolean juicioEnProceso, Boolean auditoriaEnProceso
			, Boolean convenioEnProceso)
			throws EstadoAdeudoException {

		byte[] documento = null;

		try {
			// Tipo Template Reconstruir
			String reporte = RECONSTRUIR_RESOLUCION;

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Map<String, Object> parameters = getParametrosDocumentoReconstruir(persona, solicitud, firmaElectronica,
					imagenCodeQR, wrapper,usuario);

			log.debug("+++reporte: "+reporte);

			JasperReport report = (JasperReport) JRLoader.loadObject(
					new ClassPathResource(reporte).getInputStream());

			log.debug("+++report: "+report.getName());

			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			exporter.exportReport();
			documento = byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("-----------");
			log.error(e);
			throw new EstadoAdeudoException(MSG_ERROR);
		}

		if(documento==null)
			throw new EstadoAdeudoException(MSG_ERROR);

		return documento;
	}

}
