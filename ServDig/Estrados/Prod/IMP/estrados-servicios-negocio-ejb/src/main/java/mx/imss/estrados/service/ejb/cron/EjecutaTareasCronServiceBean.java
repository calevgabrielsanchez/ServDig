package mx.imss.estrados.service.ejb.cron;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.imss.estrados.commons.Constantes;
import mx.imss.estrados.cron.EjecutaTareasCronServiceRemote;
import mx.imss.estrados.entity.NeeCatStatus;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.service.ejb.dao.GenericDAO;
import mx.imss.estrados.service.ejb.tramiteDigital.TramiteDigitalServiceLocal;
import mx.imss.estrados.service.interfaces.ReporteNotificacionServiceRemote;
import mx.imss.estrados.utils.EstradosStringUtils;
import mx.imss.estrados.utils.UtileriaFechas;

import org.apache.log4j.Logger;

@Stateless(name="ejecutaTareasCronServiceBean", mappedName="ejecutaTareasCronServiceBean")
public class EjecutaTareasCronServiceBean implements EjecutaTareasCronServiceRemote {
	
	private final static Logger logger = Logger.getLogger(EjecutaTareasCronServiceBean.class);
	
	@EJB
	public EjecutaTareasCronDAO ejecutaTareasCronDAO;
	
	@EJB
	public GenericDAO<NeeNotificaciones> genericDAO;
	
	@EJB
	private TramiteDigitalServiceLocal tramiteDigital;
	
	@EJB
	private ReporteNotificacionServiceRemote reporteNotificacion;
	
	Map<String, Object> parametros;
	RespuestaFirmadoSimple sello;
	StringBuffer cadenaOriginal;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void modificaNotificacionesRegistradas() {
		String noficioAcuerdo = "";
		String noficioDocumento = "";
		//System.out.println("########## INICIANDO LA PUBLICACION DE NOTIFICACIONES ##########");
		logger.info("########## INICIANDO LA PUBLICACION DE NOTIFICACIONES ##########");
		//List<NeeNotificaciones> notificaciones = ejecutaTareasCronDAO.consultaNotificacionesAModificar(Constantes.ESTATUS.REGISTRADA.getStatus());
		List<Date> fechasAPublicar = ejecutaTareasCronDAO.obtenerFechasConNotificacionesPendientes(Constantes.ESTATUS.REGISTRADA.getStatus());
		logger.info("########## Fechas con notificaciones pendientes de publicar: " + fechasAPublicar.toString() + "##########");
		NeeCatStatus status;
		for(Date fecha: fechasAPublicar) {
			List<NeeNotificaciones> notificaciones = ejecutaTareasCronDAO.consultaNotificacionesAModificarPorFecha(Constantes.ESTATUS.REGISTRADA.getStatus(), fecha);
			//System.out.println("########## REGISTROS A PUBLICAR ["+notificaciones.size()+"] ##########");
			logger.info("########## Registros a publicar ["+notificaciones.size()+"] de fecha [" + fecha + "] ##########");
			for(NeeNotificaciones n: notificaciones) {
				try{
					NeeDocumentosAdjuntos numOficioAcuerdo = ejecutaTareasCronDAO.consultaDoctoAdjunto(n.getCveNotificaciones(), Constantes.TIPO_DOCTO_ADJUNTO.ACUERDO.getDocto());
					NeeDocumentosAdjuntos numOficioDocumento = ejecutaTareasCronDAO.consultaDoctoAdjunto(n.getCveNotificaciones(), Constantes.TIPO_DOCTO_ADJUNTO.DOCUMENTO.getDocto());
					
					if (numOficioAcuerdo != null && numOficioDocumento != null) {
						
						noficioAcuerdo = numOficioAcuerdo.getDesNumOficio();
						noficioDocumento = numOficioDocumento.getDesNumOficio();
						cadenaOriginal = obtieneCadenaOriginal(n, noficioAcuerdo);
						sello = generaSello(n, cadenaOriginal);
						
						status= new NeeCatStatus();
						status.setCveStatus(Constantes.ESTATUS.PUBLICADA.getStatus());
						n.setNeeCatStatus(status);
						n.setDesRefPublicacion(sello.getTramite());
						genericDAO.saveOrUpdate(n);
						
						SsoVwUsuario datosUser = ejecutaTareasCronDAO.consultarDatosUsuario(n.getCveUsuario());
						parametros = llenaParametros(n, sello, cadenaOriginal.toString(), noficioAcuerdo, noficioDocumento, datosUser);
						System.out.println("ENVIANDO CORREO DE PUBLICACION: " +datosUser.getRefCorreoElectronico() );
						parametros.put("correo", datosUser.getRefCorreoElectronico());
						parametros.put("subject", generarAsuntoPublicado());
						parametros.put("cuerpo", generarCuerpoPublicado());
						reporteNotificacion.generarReportePDF(parametros, Constantes.ESTATUS.PUBLICADA.getStatus());
					} else {
						logger.warn("No se encontro oficio acuerdo u oficio documento: " + n.getCveNotificaciones());
					}
				}catch(Exception e){
					//e.printStackTrace();
					logger.error("Ocurrio un error con la publicacion: " + e.getMessage(), e);
				}
				//System.out.println("########## TERMINANDO LA PUBLICACION DE NOTIFICACIONES ##########");
			}
			logger.info("########## Termino de publicar fecha [" + fecha + "] ##########");
		}
		logger.info("########## TERMINANDO LA PUBLICACION DE NOTIFICACIONES ##########");
	}
	
	// Se comenta el contenido del metodo para publicar las notificaciones que no fueron publicadas
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void modificaNotificacionesPublicadas() {
		String noficioAcuerdo = "";
		String noficioDocumento = "";
		//System.out.println("########## INICIANDO EL RETIRO DE LA PUBLICACION DE NOTIFICACIONES ##########");
		logger.info("########## INICIANDO EL RETIRO DE LA PUBLICACION DE NOTIFICACIONES ##########");
		//List<NeeNotificaciones> notificaciones = ejecutaTareasCronDAO.consultaNotificacionesARetirar(Constantes.ESTATUS.PUBLICADA.getStatus());
		List<Date> fechasARetirar = ejecutaTareasCronDAO.obtenerFechasConNotificacionesPendientes(Constantes.ESTATUS.PUBLICADA.getStatus());
		logger.info("########## Fechas con notificaciones pendientes de retirar: " + fechasARetirar.toString() + "##########");
		NeeCatStatus status;
		for(Date fecha: fechasARetirar) {
			List<NeeNotificaciones> notificaciones = ejecutaTareasCronDAO.consultaNotificacionesAModificarPorFecha(Constantes.ESTATUS.PUBLICADA.getStatus(), fecha);
			//System.out.println("########## REGISTROS A RETIRAR ["+notificaciones.size()+"] ##########");
			logger.info("########## Registros a retirar ["+notificaciones.size()+"] de fecha [" + fecha + "] ##########");
			for(NeeNotificaciones n: notificaciones){
				try{
					NeeDocumentosAdjuntos numOficioAcuerdo = ejecutaTareasCronDAO.consultaDoctoAdjunto(n.getCveNotificaciones(), Constantes.TIPO_DOCTO_ADJUNTO.ACUERDO.getDocto());
					NeeDocumentosAdjuntos numOficioDocumento = ejecutaTareasCronDAO.consultaDoctoAdjunto(n.getCveNotificaciones(), Constantes.TIPO_DOCTO_ADJUNTO.DOCUMENTO.getDocto());
					if(numOficioAcuerdo!=null)
						noficioAcuerdo = numOficioAcuerdo.getDesNumOficio();
					if(numOficioDocumento !=null)
						noficioDocumento = numOficioDocumento.getDesNumOficio();
					cadenaOriginal = obtieneCadenaOriginal(n, noficioAcuerdo);
					sello = generaSello(n, cadenaOriginal);
					status= new NeeCatStatus();
					status.setCveStatus(Constantes.ESTATUS.RETIRADA.getStatus());
					n.setNeeCatStatus(status);
					n.setDesRefRetiro(sello.getTramite());
					genericDAO.saveOrUpdate(n);
					
					SsoVwUsuario datosUser = ejecutaTareasCronDAO.consultarDatosUsuario(n.getCveUsuario());
					
					parametros = llenaParametros(n, sello, cadenaOriginal.toString(), noficioAcuerdo, noficioDocumento, datosUser);
					System.out.println("ENVIANDO CORREO DE RETIRO: " +datosUser.getRefCorreoElectronico() );
					parametros.put("correo", datosUser.getRefCorreoElectronico());
					parametros.put("subject", generarAsuntoRetirado());
					parametros.put("cuerpo", generarCuerpoRetirado());
					reporteNotificacion.generarReportePDF(parametros, Constantes.ESTATUS.RETIRADA.getStatus());
				}catch(Exception e){
					//e.printStackTrace();
					logger.error("Ocurrio un error con el retiro: " + e.getMessage(), e);
				}
			}
			logger.info("########## Termino de retirar fecha [" + fecha + "] ##########");
		}
		//System.out.println("########## TERMINANDO EL RETIRO DE LA PUBLICACION DE NOTIFICACIONES ##########");
		logger.info("########## TERMINANDO EL RETIRO DE LA PUBLICACION DE NOTIFICACIONES ##########");
	}
	
	private StringBuffer obtieneCadenaOriginal(NeeNotificaciones n, String numOficioAcuerdo){
		cadenaOriginal= new StringBuffer();
		cadenaOriginal.append("||Fecha de publicacion:" + n.getFecPublicacion());
		cadenaOriginal.append("|Nombre del sujeto a notificar" + n.getRazonSocial());
		cadenaOriginal.append("|Numero de oficio del acuerdo:" + numOficioAcuerdo + "||");
		return cadenaOriginal;
	}
	
	private RespuestaFirmadoSimple generaSello(NeeNotificaciones n, StringBuffer cadenaOriginal){
		RespuestaFirmadoSimple sello = tramiteDigital.getSelloDigital(cadenaOriginal.toString(), null,null);
		return sello;
	}
	
	private Map<String, Object> llenaParametros(NeeNotificaciones n, RespuestaFirmadoSimple sello, String cadena, 
			String numOficioAcuerdo, String numOficioDocumento, SsoVwUsuario datosUser) {
		parametros = new HashMap<String, Object>();
		parametros.put("tramite", sello.getTramite());
		parametros.put("sello", sello.getSello());
		parametros.put("cadenaOriginalAcuse", cadena);
		
		
		
		// Se comenta el contenido del metodo para publicar las notificaciones que no fueron publicadas
		// En el mes es uno menos ya que calendar toma el mes 0 como enero
		
//		Calendar calendar = Calendar.getInstance();
//	    calendar.set(2019, 06, 03);
//	    Date fecha = calendar.getTime();
//	    
//	    this.parametros.put("fechaActual", UtileriaFechas.parseDateToString(fecha, "dd/MM/yyyy"));
		
		parametros.put("fechaActual", UtileriaFechas.parseDateToString(new Date(), "dd/MM/yyyy"));
		
		
		
		parametros.put("nombreUsuario", (datosUser == null ? "" : datosUser.getNomNombre() + " " + datosUser.getNomPaterno() + " " + datosUser.getNomMaterno()));
		parametros.put("cargoUsuario", (datosUser == null ? "" : datosUser.getDesPuesto()));
		parametros.put("desDelegacion", (datosUser == null ? "" : datosUser.getDesDelegacion()));
		parametros.put("desSubdelegacion", (datosUser == null ? "" : datosUser.getDesSubdelegacion()));
		if(datosUser.getDesDelegacion() == null) {
			parametros.put("areaNormativa", datosUser.getDesAreaNorma()!=null ?datosUser.getDesAreaNorma():"");
			parametros.put("etiquetaAreaRespNotif","Materia del documento a notificar");
		}else{
			parametros.put("areaNormativa", "");
			parametros.put("etiquetaAreaRespNotif","Área responsable de la notificación");
		}
		parametros.put("fecPublicacion", UtileriaFechas.parseDateToString(n.getFecPublicacion(), "dd/MM/yyyy"));
		parametros.put("fecRetiroPublicacion", UtileriaFechas.parseDateToString(n.getFecRetiroPublicacion(), "dd/MM/yyyy"));
		if(n!=null){
			
			//parametros.put("areaRespNotif", n.getNeeCatTipodocumento().getNeeCatProceso().getDesProceso());
			parametros.put("areaRespNotif", n.getNotificacionesDTO().getTipodocumentoDTO().getProcesoDTO().getDesProceso());
		
			
			if (n.getDesNumRegCpa()!=null &&  !n.getDesNumRegCpa().isEmpty()) {
				parametros.put("etiquetaRazonSocial","Nombre: ");
			} else {
				parametros.put("etiquetaRazonSocial","Nombre, denominación o razón social: ");
			}
			
			parametros.put("razonSocial", n.getRazonSocial()!=null ? n.getRazonSocial() : "");
			
			if (n.getRegistroPatronal() != null && !n.getRegistroPatronal().isEmpty()) {
				parametros.put("registroPatronal", n.getRegistroPatronal() == null ? "" : n.getRegistroPatronal());
			} else {
				parametros.put("registroPatronal", n.getDesNumRegCpa() == null ? "" :  n.getDesNumRegCpa());
			}
			
			parametros.put("tipoDocumento", n.getNeeCatTipodocumento().getDesTipodocumento());
		}
		parametros.put("numOficioAcuerdo", numOficioAcuerdo);
		parametros.put("numOficioDocumento", numOficioDocumento);
		parametros.put("fecInicioPublicacion", UtileriaFechas.parseDateToString(n.getFecInicioPublicacion(), "dd/MM/yyyy"));
		parametros.put("fecFinPublicacion", UtileriaFechas.parseDateToString(n.getFecFinPublicacion(), "dd/MM/yyyy"));
		return parametros;
	}
	
	private Map<String, Object> llenaParametrosPorFechas(NeeNotificaciones n, RespuestaFirmadoSimple sello, String cadena, 
			String numOficioAcuerdo, String numOficioDocumento, SsoVwUsuario datosUser, Date fechaPublicacionRetiro) {
		parametros = new HashMap<String, Object>();
		parametros.put("tramite", sello.getTramite());
		parametros.put("sello", sello.getSello());
		parametros.put("cadenaOriginalAcuse", cadena);
		
		
		// Se comenta el contenido del metodo para publicar las notificaciones que no fueron publicadas
		// En el mes es uno menos ya que calendar toma el mes 0 como enero
		
//		Calendar calendar = Calendar.getInstance();
//	    calendar.set(2019, 06, 03);
//	    Date fecha = calendar.getTime();
//	    
//	    this.parametros.put("fechaActual", UtileriaFechas.parseDateToString(fecha, "dd/MM/yyyy"));
		
		parametros.put("fechaActual", UtileriaFechas.parseDateToString(fechaPublicacionRetiro, "dd/MM/yyyy"));
		
		
		
		parametros.put("nombreUsuario", (datosUser == null ? "" : datosUser.getNomNombre() + " " + datosUser.getNomPaterno() + " " + datosUser.getNomMaterno()));
		parametros.put("cargoUsuario", (datosUser == null ? "" : datosUser.getDesPuesto()));
		parametros.put("desDelegacion", (datosUser == null ? "" : datosUser.getDesDelegacion()));
		parametros.put("desSubdelegacion", (datosUser == null ? "" : datosUser.getDesSubdelegacion()));
		if(datosUser.getDesDelegacion() == null) {
			parametros.put("areaNormativa", datosUser.getDesAreaNorma()!=null ?datosUser.getDesAreaNorma():"");
			parametros.put("etiquetaAreaRespNotif","Materia del documento a notificar");
		}else{
			parametros.put("areaNormativa", "");
			parametros.put("etiquetaAreaRespNotif","Área responsable de la notificación");
		}
		parametros.put("fecPublicacion", UtileriaFechas.parseDateToString(n.getFecPublicacion(), "dd/MM/yyyy"));
		parametros.put("fecRetiroPublicacion", UtileriaFechas.parseDateToString(n.getFecRetiroPublicacion(), "dd/MM/yyyy"));
		if(n!=null){
			
			//parametros.put("areaRespNotif", n.getNeeCatTipodocumento().getNeeCatProceso().getDesProceso());
			parametros.put("areaRespNotif", n.getNotificacionesDTO().getTipodocumentoDTO().getProcesoDTO().getDesProceso());
		
			
			if (n.getDesNumRegCpa()!=null &&  !n.getDesNumRegCpa().isEmpty()) {
				parametros.put("etiquetaRazonSocial","Nombre: ");
			} else {
				parametros.put("etiquetaRazonSocial","Nombre, denominación o razón social: ");
			}
			
			parametros.put("razonSocial", n.getRazonSocial()!=null ? n.getRazonSocial() : "");
			
			if (n.getRegistroPatronal() != null && !n.getRegistroPatronal().isEmpty()) {
				parametros.put("registroPatronal", n.getRegistroPatronal() == null ? "" : n.getRegistroPatronal());
			} else {
				parametros.put("registroPatronal", n.getDesNumRegCpa() == null ? "" :  n.getDesNumRegCpa());
			}
			
			parametros.put("tipoDocumento", n.getNeeCatTipodocumento().getDesTipodocumento());
		}
		parametros.put("numOficioAcuerdo", numOficioAcuerdo);
		parametros.put("numOficioDocumento", numOficioDocumento);
		parametros.put("fecInicioPublicacion", UtileriaFechas.parseDateToString(n.getFecInicioPublicacion(), "dd/MM/yyyy"));
		parametros.put("fecFinPublicacion", UtileriaFechas.parseDateToString(n.getFecFinPublicacion(), "dd/MM/yyyy"));
		return parametros;
	}
	
	private String generarAsuntoPublicado() {
		StringBuilder query = new StringBuilder();
		query.append("Publicación de notificación de documento "+parametros.get("numOficioDocumento"));
		return query.toString();
	}
	
	private String generarCuerpoPublicado() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>");
		stringBuilder.append("<body>");
		stringBuilder.append("<div style='width: 95%; background-color: lightgray; padding: 40px;'>");
		stringBuilder.append("<div style='background-color: white; margin: 0px auto; height: auto;'>");
		stringBuilder.append("<table align='center' style='width: 80%'>");
		stringBuilder.append("<tbody>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='center'>");
		stringBuilder.append("<h2 style='font-size:30px;'>Instituto Mexicano del Seguro Social</h2>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td>");
		stringBuilder.append("<p style='font-size: 16px !important; text-align: justify;'>");
		stringBuilder.append("<br/>");
		
		stringBuilder.append(remplazarAcentosHTML((String) parametros.get("nombreUsuario")));
		stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("cargoUsuario")));
		if (!EstradosStringUtils.isReallyEmptyOrNull((String) parametros.get("desDelegacion"))) {
			stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("desDelegacion")));
			if (!EstradosStringUtils.isReallyEmptyOrNull((String) parametros.get("desSubdelegacion"))) {
				stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("desSubdelegacion")));
			}
		} else {
			stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("areaNormativa")));
		}
		stringBuilder.append("<br/><br/>Se informa que su solicitud de publicaci&oacute;n por estrado electr&oacute;nico dirigida a "+remplazarAcentosHTML((String) parametros.get("razonSocial")));
		stringBuilder.append(", ha sido publicada en la p&aacute;gina www.imss.gob.mx");
		stringBuilder.append("<br/><br/>Se adjunta acuse con n&uacute;mero de folio "+remplazarAcentosHTML((String) parametros.get("tramite"))+", que deber&aacute; imprimir e integrar en el");
		stringBuilder.append(" expediente respectivo conjuntamente con las constancias que se integren para dar cumplimiento");
		stringBuilder.append("a lo establecido en el art&iacute;culo 139 del C&oacute;digo Fiscal de la Federaci&oacute;n.");
		stringBuilder.append("<br/>");
		stringBuilder.append("<br/>");
		
		stringBuilder.append("</p>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</tbody>");
		stringBuilder.append("</table>");
		stringBuilder.append("</div>");
		stringBuilder.append("</div>");
		stringBuilder.append("</body>");
		stringBuilder.append("</html>");
		return stringBuilder.toString();
	}
	
	private String generarAsuntoRetirado() {
		StringBuilder query = new StringBuilder();
		query.append("Retiro de notificación de documento "+parametros.get("numOficioDocumento"));
		return query.toString();
	}
	
	private String generarCuerpoRetirado() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>");
		stringBuilder.append("<body>");
		stringBuilder.append("<div style='width: 95%; background-color: lightgray; padding: 40px;'>");
		stringBuilder.append("<div style='background-color: white; margin: 0px auto; height: auto;'>");
		stringBuilder.append("<table align='center' style='width: 80%'>");
		stringBuilder.append("<tbody>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='center'>");
		stringBuilder.append("<h2 style='font-size:30px;'>Instituto Mexicano del Seguro Social</h2>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td>");
		stringBuilder.append("<p style='font-size: 16px !important; text-align: justify;'>");
		stringBuilder.append("<br/>");
		
		stringBuilder.append(remplazarAcentosHTML((String) parametros.get("nombreUsuario")));
		stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("cargoUsuario")));
		if (!EstradosStringUtils.isReallyEmptyOrNull((String) parametros.get("desDelegacion"))) {
			stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("desDelegacion")));
			if (!EstradosStringUtils.isReallyEmptyOrNull((String) parametros.get("desSubdelegacion"))) {
				stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("desSubdelegacion")));
			}
		} else {
			stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametros.get("areaNormativa")));
		}
		stringBuilder.append("<br/><br/>Se informa que la publicaci&oacute;n por estrado electr&oacute;nico dirigida a "+remplazarAcentosHTML((String) parametros.get("razonSocial")));
		stringBuilder.append(", ha sido retirada de la p&aacute;gina www.imss.gob.mx");
		stringBuilder.append("<br/><br/>Se adjunta acuse con n&uacute;mero de folio "+remplazarAcentosHTML((String) parametros.get("tramite"))+", que deber&aacute; imprimir e integrar en el");
		stringBuilder.append(" expediente respectivo conjuntamente con las constancias que se integren para dar cumplimiento");
		stringBuilder.append("a lo establecido en el art&iacute;culo 139 del C&oacute;digo Fiscal de la Federaci&oacute;n.");
		stringBuilder.append("<br/>");
		stringBuilder.append("<br/>");
		
		stringBuilder.append("</p>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</tbody>");
		stringBuilder.append("</table>");
		stringBuilder.append("</div>");
		stringBuilder.append("</div>");
		stringBuilder.append("</body>");
		stringBuilder.append("</html>");
		return stringBuilder.toString();
	}
	
	public String remplazarAcentosHTML(String stringAcentos) {
		if (!EstradosStringUtils.isReallyEmptyOrNull(stringAcentos)) {
			if (stringAcentos.contains("á")) {
				stringAcentos = stringAcentos.replace("á", "&#225;");
			}
			if (stringAcentos.contains("é")) {
				stringAcentos = stringAcentos.replace("é", "&#233;");
			}
			if (stringAcentos.contains("í")) {
				stringAcentos = stringAcentos.replace("í", "&#237;");
			}
			if (stringAcentos.contains("ó")) {
				stringAcentos = stringAcentos.replace("ó", "&#243;");
			}
			if (stringAcentos.contains("ú")) {
				stringAcentos = stringAcentos.replace("ú", "&#250;");
			}
			if (stringAcentos.contains("ñ")) {
				stringAcentos = stringAcentos.replace("ñ", "&#241;");
			}
			if (stringAcentos.contains("Á")) {
				stringAcentos = stringAcentos.replace("Á", "&#193;");
			}
			if (stringAcentos.contains("É")) {
				stringAcentos = stringAcentos.replace("É", "&#201;");
			}
			if (stringAcentos.contains("Í")) {
				stringAcentos = stringAcentos.replace("Í", "&#205;");
			}
			if (stringAcentos.contains("Ó")) {
				stringAcentos = stringAcentos.replace("Ó", "&#211;");
			}
			if (stringAcentos.contains("Ú")) {
				stringAcentos = stringAcentos.replace("Ú", "&#218;");
			}
			if (stringAcentos.contains("Ñ")) {
				stringAcentos = stringAcentos.replace("Ñ", "&#209;");
			}
		}
		return stringAcentos;
	}

	@Override
	public Integer modificaNotificacionesPublicadasPorFecha(Integer idEstatus, Integer idCambio,
			Date FechaRegistro) {
		String noficioAcuerdo = "";
		String noficioDocumento = "";
		System.out.println("-----------------INICIANDO LA EJECUCION DE NOTIFICACIONES POR FECHAS ------------");
		List<NeeNotificaciones> notificaciones = ejecutaTareasCronDAO.consultaNotificacionesAModificarPorFecha(idEstatus, FechaRegistro);
		NeeCatStatus status;
		for(NeeNotificaciones n: notificaciones) {
			try{
				NeeDocumentosAdjuntos numOficioAcuerdo = ejecutaTareasCronDAO.consultaDoctoAdjunto(n.getCveNotificaciones(), Constantes.TIPO_DOCTO_ADJUNTO.ACUERDO.getDocto());
				NeeDocumentosAdjuntos numOficioDocumento = ejecutaTareasCronDAO.consultaDoctoAdjunto(n.getCveNotificaciones(), Constantes.TIPO_DOCTO_ADJUNTO.DOCUMENTO.getDocto());
				
				if (numOficioAcuerdo != null && numOficioDocumento != null) {
					
					noficioAcuerdo = numOficioAcuerdo.getDesNumOficio();
					noficioDocumento = numOficioDocumento.getDesNumOficio();
					cadenaOriginal = obtieneCadenaOriginal(n, noficioAcuerdo);
					sello = generaSello(n, cadenaOriginal);
					
					status= new NeeCatStatus();
					if(idCambio == Constantes.ESTATUS.PUBLICADA.getStatus()){
						status.setCveStatus(Constantes.ESTATUS.PUBLICADA.getStatus());
						n.setDesRefPublicacion(sello.getTramite());
					}else {
						status.setCveStatus(Constantes.ESTATUS.RETIRADA.getStatus());
						n.setDesRefRetiro(sello.getTramite());
					}
					
					n.setNeeCatStatus(status);
					
					genericDAO.saveOrUpdate(n);
					
					SsoVwUsuario datosUser = ejecutaTareasCronDAO.consultarDatosUsuario(n.getCveUsuario());
					parametros = llenaParametrosPorFechas(n, sello, cadenaOriginal.toString(), noficioAcuerdo, noficioDocumento, datosUser, FechaRegistro);
					
					if(idCambio == Constantes.ESTATUS.PUBLICADA.getStatus()){
						System.out.println("ENVIANDO CORREO DE PUBLICACION: " +datosUser.getRefCorreoElectronico() );
						parametros.put("correo", datosUser.getRefCorreoElectronico());
						parametros.put("subject", generarAsuntoPublicado());
						parametros.put("cuerpo", generarCuerpoPublicado());
						reporteNotificacion.generarReportePDF(parametros, Constantes.ESTATUS.PUBLICADA.getStatus());
					}else{
						System.out.println("ENVIANDO CORREO DE RETIRO: " +datosUser.getRefCorreoElectronico() );
						parametros.put("correo", datosUser.getRefCorreoElectronico());
						parametros.put("subject", generarAsuntoRetirado());
						parametros.put("cuerpo", generarCuerpoRetirado());
						reporteNotificacion.generarReportePDF(parametros, Constantes.ESTATUS.RETIRADA.getStatus());
					}
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("-----------------FIJALIZANDO LA EJECUCION DE NOTIFICACIONES POR FECHAS ------------");
		}
		return notificaciones.size();
	}
	
}
