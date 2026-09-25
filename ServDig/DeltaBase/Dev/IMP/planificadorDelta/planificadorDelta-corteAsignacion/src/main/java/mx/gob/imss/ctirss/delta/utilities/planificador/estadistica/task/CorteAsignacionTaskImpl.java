package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FtpUploader;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.enums.TipoCorteAsignacionEnum;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteGeneralAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.EstadisticasAsegurados;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.EstadisticasAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.FiltroEstadisticaAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.ParametrosCorreoCorte;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.service.dao.SolicitudDao;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils.CorteEstadisticoProperties;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils.MailComponent;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils.MailUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;

public class CorteAsignacionTaskImpl implements CorteAsignacionTask {
	private static final Logger LOG = Logger.getLogger(CorteAsignacionTaskImpl.class);
	private static final double HRS_DIA_ANT = -24;
	private static final String URL_APPLICATION_PROPERTIES = "config/application.properties";
	private static final String ASIG_TEMPLATE = "config/mail/corteAsignacionTemplate.vm";
	private static final String ASIG_TEMPLATE_RANGO = "config/mail/corteAsignacionRangoTemplate.vm";
	private static final int IDX_INI_CADENA_NSS = 12;
	private static final int IDX_FIN_CADENA_NSS = 23;

	@Autowired
	private SolicitudDao solicitudDao;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private FtpUploader ftpUploader;

	private Properties getGeneralProperties() throws IOException {
		InputStream inputStream = CorteAsignacionTaskImpl.class.getClassLoader().getResourceAsStream(URL_APPLICATION_PROPERTIES);
		Properties prop = new Properties();
		prop.load(inputStream);

		return prop;
	}

	public CorteGeneralAsignacion realizarCorteGeneral(Date fechaCorte) {
		FiltroEstadisticaAsignacion filtro = new FiltroEstadisticaAsignacion();
		filtro.setTipoSolicitud(TipoSolicitudEnum.ASIGNACION_NSS);
		filtro.setEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA);

		List<OrigenSolicitudEnum> listOrigenSolicitud = new ArrayList<OrigenSolicitudEnum>();
		listOrigenSolicitud.add(OrigenSolicitudEnum.INTERNET);
		listOrigenSolicitud.add(OrigenSolicitudEnum.VENTANILLA);
		filtro.setListOrigenSolicitud(listOrigenSolicitud );

		List<TipoTramiteEnum> listTipoTramite = new ArrayList<TipoTramiteEnum>();
		listTipoTramite.add(TipoTramiteEnum.LOCALIZACION_NSS);
		listTipoTramite.add(TipoTramiteEnum.ASIGNACION_NSS);
		filtro.setListTipoTramite(listTipoTramite);

		filtro.setFechaSolicitudInicial(fechaCorte);
		filtro.setFechaSolicitudFinal(fechaCorte);

		CorteGeneralAsignacion corteGeneral = new CorteGeneralAsignacion();
		try {
			List<EstadisticasAsignacion> listSolicitudes = solicitudDao.findEstadisticaAsignacion(filtro);

			for (EstadisticasAsignacion estadisticasAsig : listSolicitudes) {
				OrigenSolicitud origenSol = estadisticasAsig.getOrigenSolicitud();
				TipoTramite tipoTramite = estadisticasAsig.getTipoTramite();

				LOG.info("origenSol: " + origenSol.getIdTipoSolicitud());
				LOG.info("tipoTramite: " + tipoTramite.getIdTipoTramite());
				if (tipoTramite.getIdTipoTramite().intValue() ==
						TipoTramiteEnum.LOCALIZACION_NSS.getCodigo()) {
					if (origenSol.getIdTipoSolicitud().equals(
							OrigenSolicitudEnum.INTERNET.getId())) {
						corteGeneral.setNumLocalizacionInternet(estadisticasAsig.getTotal());
						LOG.info("Localizacion de NSS Internet: " + corteGeneral.getNumLocalizacionInternet());
					} else if (origenSol.getIdTipoSolicitud().equals(
							OrigenSolicitudEnum.VENTANILLA.getId())) {
						corteGeneral.setNumLocalizacionVentanilla(estadisticasAsig.getTotal());
						LOG.info("Localizacion de NSS Ventanilla: " + corteGeneral.getNumLocalizacionVentanilla());
					}
				} else if (tipoTramite.getIdTipoTramite().intValue() ==
						TipoTramiteEnum.ASIGNACION_NSS.getCodigo()) {
					if (origenSol.getIdTipoSolicitud().equals(
							OrigenSolicitudEnum.INTERNET.getId())) {
						corteGeneral.setNumAsignacionInternet(estadisticasAsig.getTotal());
						LOG.info("Asignacion de NSS Internet: " + corteGeneral.getNumAsignacionInternet());
					} else if (origenSol.getIdTipoSolicitud().equals(
							OrigenSolicitudEnum.VENTANILLA.getId())) {
						corteGeneral.setNumAsignacionVentanilla(estadisticasAsig.getTotal());
						LOG.info("Asgnacion de NSS Ventanilla: " + corteGeneral.getNumAsignacionVentanilla());
					}
				}
			}
		} catch (Exception e) {
			corteGeneral.setExcepcionGeneral(e);
		}

		return corteGeneral;
	}

	@Override
	public void realizarCorteRango() throws IOException, ParseException {
		Date fechaCortePosterior = FechaUtils.getFechaSistema();
		fechaCortePosterior = FechaUtils.getFechaCeroHoras(fechaCortePosterior);

		Date fechaCorte = FechaUtils.sumaHoras(fechaCortePosterior, HRS_DIA_ANT);
		realizarCorteRangoGeneral(fechaCorte, fechaCortePosterior);
	}

	@SuppressWarnings("unchecked")
	private void realizarCorteRangoGeneral(Date fechaCorte,
			Date fechaCortePosterior) throws IOException, ParseException {
		// Descarga del archivo desde un servidor FTP
		fechaCorte = FechaUtils.getFechaCeroHoras(fechaCorte);
		Date fechaCorteAnterior = FechaUtils.sumaHoras(fechaCorte, HRS_DIA_ANT);

		Properties prop = getGeneralProperties();

		// Lectura de los registro del archivo remoto
		List<String> lstRegistrosArchivo = realizarCorteArchivo(prop, fechaCorte, false);
		LOG.info("Se obtuvieron " + lstRegistrosArchivo.size() + " registros");

		// Consulta a Base de Datos
		List<EstadisticasAsegurados> listAseguradosImss = realizarCorteImss(prop, fechaCorte);
		LOG.info("Se obtuvieron " + listAseguradosImss.size() + " registros en BDTU");

		// Se almacena en tabla hash los registros encontrados en el IMSS para facilitar la busqueda posterior
		List<String> lstRegistrosImss = new ArrayList<String>();
		Hashtable<String, EstadisticasAsegurados> tblAseguradosImss = new Hashtable<String, EstadisticasAsegurados>();
		for (EstadisticasAsegurados estAsegurado : listAseguradosImss) {
			lstRegistrosImss.add(estAsegurado.getNss());
			tblAseguradosImss.put(estAsegurado.getNss(), estAsegurado);
		}

		// Se realiza disjuncion para obtener los registros no escritos en BDTU
		List<String> lstDiferencia = new ArrayList<String>();
		lstDiferencia.addAll(CollectionUtils.disjunction(lstRegistrosImss, lstRegistrosArchivo));
		LOG.info("Se obtuvieron " + lstDiferencia.size() + " diferencias");

		// Se obtiene hora de corte
		SimpleDateFormat sdfDateHrs = new SimpleDateFormat("yyyyMMdd-HH:mm", new Locale("es","MX"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String strHoraCorte = prop.getProperty("canase.corte.hora");

		Date fechaIncial = FechaUtils.sumaHoras(fechaCorte, HRS_DIA_ANT);
		String strFechaInicial = sdf.format(fechaIncial);
		Date fechaCorteInicial = sdfDateHrs.parse(strFechaInicial + "-" + strHoraCorte);

		String strFechaFinal = sdf.format(fechaCorte);
		Date fechaCorteFinal = sdfDateHrs.parse(strFechaFinal + "-" + strHoraCorte);

		// Se dividen las diferencias
		List<String> listAseguradosImssPrev = new ArrayList<String>();
		List<String> listAseguradosImssPost = new ArrayList<String>();
		List<String> listAseguradosImssInter = new ArrayList<String>();

		LOG.info("Clasificando diferencias");
		for(String strNssNoEscrito : lstDiferencia) {
			EstadisticasAsegurados estAsegurado = tblAseguradosImss.get(strNssNoEscrito);

			if(estAsegurado != null) {
				if (FechaUtils.esFechaMenor(estAsegurado.getFechaSolicitud(),
						fechaCorteInicial)) {
					listAseguradosImssPrev.add(strNssNoEscrito);
				} else if (FechaUtils.esFechaMayor(estAsegurado.getFechaSolicitud(),
						fechaCorteFinal)) {
					listAseguradosImssPost.add(strNssNoEscrito);
				} else {
					listAseguradosImssInter.add(strNssNoEscrito);
				}
			}
		}

		LOG.info("Se obtuvieron " + listAseguradosImssPrev.size() + " diferencias al inicio del corte");
		System.out.println(listAseguradosImssPrev);
		LOG.info("Se obtuvieron " + listAseguradosImssInter.size() + " diferencias dentro del rango del corte");
		System.out.println(listAseguradosImssInter);
		LOG.info("Se obtuvieron " + listAseguradosImssPost.size() + " diferencias al final del corte");
		System.out.println(listAseguradosImssPost);

		// Se realiza corte de los dias anteriores y posterioresa corte
		List<String> lstRegistrosArchivoPrev = realizarCorteArchivo(prop, fechaCorteAnterior, false);
		List<String> lstRegistrosArchivoPost = realizarCorteArchivo(prop, fechaCortePosterior, true);

		List<String> lstDiferenciaPrev = new ArrayList<String>();
		lstDiferenciaPrev.addAll(CollectionUtils.subtract(listAseguradosImssPrev, lstRegistrosArchivoPrev));
		LOG.info("Se obtuvieron " + lstDiferenciaPrev.size() + " asignaciones NO REGISTRADOS EN CANASE al inicio del corte");
		System.out.println(lstDiferenciaPrev);
		
		List<String> lstDiferenciaInterPrev = new ArrayList<String>();
		lstDiferenciaInterPrev.addAll(CollectionUtils.subtract(listAseguradosImssInter, lstRegistrosArchivoPrev));
		System.out.println(lstDiferenciaInterPrev);
		List<String> lstDiferenciaInterPost = new ArrayList<String>();
		lstDiferenciaInterPost.addAll(CollectionUtils.subtract(listAseguradosImssInter, lstRegistrosArchivoPost));
		System.out.println(lstDiferenciaInterPost);
		List<String> lstDiferenciaInter = new ArrayList<String>();
		lstDiferenciaInter.addAll(CollectionUtils.intersection(lstDiferenciaInterPrev, lstDiferenciaInterPost));
		LOG.info("Se obtuvieron " + lstDiferenciaInter.size() + " asignaciones NO REGISTRADOS EN CANASE dentro del rango del corte");

		List<String> lstDiferenciaPost = new ArrayList<String>();
		lstDiferenciaPost.addAll(CollectionUtils.subtract(listAseguradosImssPost, lstRegistrosArchivoPost));
		LOG.info("Se obtuvieron " + lstDiferenciaPost.size() + " asignaciones NO REGISTRADOS EN CANASE al final del corte");
	}

	private List<String> realizarCorteArchivo(Properties prop, Date fechaCorte, boolean isArchivoActual) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// Se obtiene ruta del archivo de lectura
		String nombreArchivo = prop.getProperty("canase.file.asignacion");
		String rutaOrigen = prop.getProperty("filesystem.canase.output.url");
		String rutaDestino = prop.getProperty("file.canase.tmp.input.url");
		String rutaOrigenActual = prop.getProperty("filesystem.canase.tmp.output.url");

		StringBuffer sbRutaLectura = new StringBuffer();
		if (!isArchivoActual) {
			sbRutaLectura.append(rutaOrigen).append("/")
					.append(sdf.format(fechaCorte)).append("/")
					.append(nombreArchivo);
		} else {
			sbRutaLectura.append(rutaOrigenActual).append("/")
					.append(nombreArchivo);
		}

		StringBuffer sbRutaEscrituraBase = new StringBuffer();
		sbRutaEscrituraBase.append(rutaDestino).append("/")
				.append(sdf.format(fechaCorte));

		// Se crea la carpeta local si no existe
		File directorioDescarga = new File(sbRutaEscrituraBase.toString());
		if (!directorioDescarga.exists()) {
			boolean isDirecorioCreado = directorioDescarga.mkdir();

			if (!isDirecorioCreado) {
				throw new IOException("El directorio '" + sbRutaEscrituraBase.toString() + "' no fue creado");
			} else {
				LOG.info("Se CREA el directorio :" + sbRutaEscrituraBase.toString());
			}
		} else {
			LOG.info("Se LEE el directorio :" + sbRutaEscrituraBase.toString());
		}

		StringBuffer sbRutaEscritura = new StringBuffer();
		sbRutaEscritura.append(sbRutaEscrituraBase).append("/")
				.append(nombreArchivo);

		ftpUploader.downloadFile(sbRutaLectura.toString(), sbRutaEscritura.toString());

		// Lectura del archivo descargado
		File arhivoCanaseDescargado = new File(sbRutaEscritura.toString());
		List<String> lineasArchivos = FileUtils.readLines(arhivoCanaseDescargado);
		List<String> lstRegistros = new ArrayList<String>();

		for (String lineaArchivo : lineasArchivos) {
			if (StringUtils.isNotBlank(lineaArchivo)) {
				lstRegistros.add(lineaArchivo.substring(IDX_INI_CADENA_NSS, IDX_FIN_CADENA_NSS));
			}
		}

		return lstRegistros;
	}

	private List<EstadisticasAsegurados> realizarCorteImss(Properties prop, Date fechaCorte) throws ParseException {
		SimpleDateFormat sdfDateHrs = new SimpleDateFormat("yyyyMMdd-HH:mm", new Locale("es","MX"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String strToleranciaCorte = prop.getProperty("canase.corte.tolerancia");
		String strHoraCorte = prop.getProperty("canase.corte.hora");
		Double minutosTolerancia = Double.valueOf(strToleranciaCorte);

		FiltroEstadisticaAsignacion filtro = new FiltroEstadisticaAsignacion();
		filtro.setTipoSolicitud(TipoSolicitudEnum.ASIGNACION_NSS);
		filtro.setEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA);

		List<OrigenSolicitudEnum> listOrigenSolicitud = new ArrayList<OrigenSolicitudEnum>();
		listOrigenSolicitud.add(OrigenSolicitudEnum.INTERNET);
		listOrigenSolicitud.add(OrigenSolicitudEnum.VENTANILLA);
		filtro.setListOrigenSolicitud(listOrigenSolicitud );

		List<TipoTramiteEnum> listTipoTramite = new ArrayList<TipoTramiteEnum>();
		listTipoTramite.add(TipoTramiteEnum.ASIGNACION_NSS);
		filtro.setListTipoTramite(listTipoTramite);

		Date fechaIncial = FechaUtils.sumaHoras(fechaCorte, HRS_DIA_ANT);
		String strFechaInicial = sdf.format(fechaIncial);
		Date fechaCorteInicial = sdfDateHrs.parse(strFechaInicial + "-" + strHoraCorte);
		fechaCorteInicial = FechaUtils.sumaMinutos(fechaCorteInicial, 0 - minutosTolerancia);

		String strFechaFinal = sdf.format(fechaCorte);
		Date fechaCorteFinal = sdfDateHrs.parse(strFechaFinal + "-" + strHoraCorte);
		fechaCorteFinal = FechaUtils.sumaMinutos(fechaCorteFinal, minutosTolerancia);

		filtro.setFechaSolicitudInicial(fechaCorteInicial);
		filtro.setFechaSolicitudFinal(fechaCorteFinal);

		LOG.info("Fecha Inicial: " + fechaCorteInicial);
		LOG.info("Fecha Final  : " + fechaCorteFinal);
		List<EstadisticasAsegurados> listAsegurados = solicitudDao.findEstadisticasAsegurados(filtro);
		return listAsegurados;
	}

	@Override
	public void enviarCorreoCorte(CorteEstadisticoProperties prop, CorteGeneralAsignacion corteGeneralAsignacion,
			TipoCorteAsignacionEnum tipoCorteAsignacion)
			throws VelocityException, MessagingException, IOException {
		String strFechaCorte = DateFormat.getDateInstance(
				DateFormat.LONG, new Locale("es","MX")).format(prop.getFechaCorteGeneral());

		Map<String, String> corteTemplateAttr = new HashMap<String, String>();

		// Datos del servidor
		MailComponent mailComponent = new MailComponent();
		mailComponent.setHost(prop.getUrlMailServer());
		mailComponent.setUser(prop.getUrlMailServer());
		mailComponent.setPassword(prop.getPasswordMailServer());

		// Destinatario, Asunto y contenido de acuerdo al tipo de Corte Solicitado
		mailComponent.setMailSubject(tipoCorteAsignacion.getDescripcion());

		switch (tipoCorteAsignacion) {
		case CORTE_PARCIAL_DIA_ACTUAL:
			mailComponent.setMailTo(prop.getDestCorteDiaActualAsignacion());

			definirCorteGeneralTemplate(corteGeneralAsignacion, corteTemplateAttr);
			mailComponent.setMailTemplate(ASIG_TEMPLATE);

			corteTemplateAttr.put("tipoCorte", "parcial");
			corteTemplateAttr.put("descripcionCorte", "el d&iacute;a de hoy, hasta el momento");

			SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm");
			String strHoraCorte = sdfh.format(prop.getFechaCorteGeneral());
			corteTemplateAttr.put("fechaHoraConsulta", strFechaCorte + " " + strHoraCorte);

			break;
		case CORTE_TOTAL_DIA_ANTERIOR:
			mailComponent.setMailTo(prop.getDestCorteDiaAnteriorAsignacion());

			definirCorteGeneralTemplate(corteGeneralAsignacion, corteTemplateAttr);
			mailComponent.setMailTemplate(ASIG_TEMPLATE);

			corteTemplateAttr.put("tipoCorte", "diario");
			corteTemplateAttr.put("descripcionCorte", "el d&iacute;a de ayer");
			corteTemplateAttr.put("fechaHoraConsulta", strFechaCorte);

			break;
		case CORTE_TOTAL_RANGO_FECHA:
			mailComponent.setMailTemplate(ASIG_TEMPLATE_RANGO);
			mailComponent.setMailTo(prop.getDestCorteRangoAsignacion());

			break;
		}

		// Remitente y Reply
		mailComponent.setFromMail(prop.getRemitenteCorreo());
		mailComponent.setFromMailName(prop.getNombreRemitenteCorreo());
		mailComponent.setReplyTo(prop.getReplyTo());
		mailComponent.setReplyToName(prop.getReplyToName());

		mailComponent.setMailVelAttributes(corteTemplateAttr);
		mailComponent.setPriority(1);

		LOG.info("Modelo " + corteTemplateAttr);
		StringBuilder sbNotaCorreo = new StringBuilder();
		sbNotaCorreo.append("Enviando Correo... a ")
				.append(mailComponent.getMailTo()).append(" desde ").append(prop.getUrlMailServer());
		LOG.info(sbNotaCorreo.toString());
		mailUtil.sendVelocityMail(mailComponent);
		LOG.info("Correo Enviado");
	}

	private void definirCorteGeneralTemplate(CorteGeneralAsignacion corteGeneralAsignacion,
			Map<String, String> corteTemplateAttr) {
		Long numAsignacionesInternet = corteGeneralAsignacion.getNumAsignacionInternet();
		Long numLocalizacionesInternet = corteGeneralAsignacion.getNumLocalizacionInternet();
		Long numAsignacionVentanilla = corteGeneralAsignacion.getNumAsignacionVentanilla();
		Long numLocalizacionVentanilla = corteGeneralAsignacion.getNumLocalizacionVentanilla();
		Long totalInternet = numAsignacionesInternet + numLocalizacionesInternet;
		Long totalVentanilla = numAsignacionVentanilla + numLocalizacionVentanilla;
		Long totalAsignacion = numAsignacionesInternet + numAsignacionVentanilla;
		Long totalLocalizacion = numLocalizacionVentanilla + numLocalizacionesInternet;
		Long total = totalAsignacion + totalLocalizacion;

		corteTemplateAttr.put("numAsignacionInternet", numAsignacionesInternet.toString());
		corteTemplateAttr.put("numLocalizacionInternet", numLocalizacionesInternet.toString());
		corteTemplateAttr.put("totalInternet", totalInternet.toString());
		corteTemplateAttr.put("numAsignacionVentanilla", numAsignacionVentanilla.toString());
		corteTemplateAttr.put("numLocalizacionVentanilla", numLocalizacionVentanilla.toString());
		corteTemplateAttr.put("totalVentanilla", totalVentanilla.toString());
		corteTemplateAttr.put("totalAsignacion", totalAsignacion.toString());
		corteTemplateAttr.put("totalLocalizacion", totalLocalizacion.toString());
		corteTemplateAttr.put("totalMovimientos", total.toString());

		if (corteGeneralAsignacion.getExcepcionGeneral() != null) {
			corteTemplateAttr.put("ejecucionExitosa", "false");
			corteTemplateAttr.put("msgExcepcionGeneral",  corteGeneralAsignacion.getExcepcionGeneral().getMessage());
		} else {
			corteTemplateAttr.put("ejecucionExitosa", "true");
		}
	}

	private void enviarCorreoCorteAsignacion(
			ParametrosCorreoCorte parametrosCorreo, Properties prop) throws IOException,
			VelocityException, MessagingException {

		

		

		String strFechaOperacion = DateFormat.getDateInstance(
				DateFormat.FULL, new Locale("es","MX")).format(parametrosCorreo.getFechaOperacion());
		String strFechaCorte = DateFormat.getDateInstance(
				DateFormat.LONG, new Locale("es","MX")).format(parametrosCorreo.getFechaCorte());

		Map<String, String> corteTemplateAttr = new HashMap<String, String>();
		corteTemplateAttr.put("fechaOperacion", strFechaOperacion);

		
		
		

		

		

		
	}
}
