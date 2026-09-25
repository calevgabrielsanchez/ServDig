package mx.gob.imss.cit.cda.web.utils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvioCorreoUtils {
	
	private static final String DATE_MASK= "dd/MM/yyyy";
	
	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;
	
	@Value("${encabezado}")
	private String ENCABEZADO_CORREOS;
	
	@Value("${pie.pagina}")
	private String PIE_PAGINA;
	
	@Value("${contenido.correo.derechohabiente}")
	private String CONTENIDO_CORREO_DERECHOHABIENTE;
	
	@Value("${contenido.correo.responsable}")
	private String CONTENIDO_CORREO_RESPONSABLE;
	
	@Value("${contenido.correo.autorizador}")
	private String CONTENIDO_CORREO_AUTORIZADOR;

	public String contenidoCorreoCDA(Solicitud solicitud,String url) {
		
		TramiteCorreccionCurp tramiteCorreccionCurp = ((TramiteCorreccionCurp) solicitud.getTramites().get(0));
		
		StringBuffer body= new StringBuffer();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		
		body.append(ENCABEZADO_CORREOS);
		
		body.append(MessageFormat.format(CONTENIDO_CORREO_DERECHOHABIENTE,
				new Object[] { 
				fecha.format(new Date()), 
				solicitud.getNoFolioSolicitud(),
				StringEscapeUtils.escapeHtml(((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getNombre()),
				StringEscapeUtils.escapeHtml(((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getPrimerApellido()),
				StringEscapeUtils.escapeHtml(((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getSegundoApellido()),
				solicitud.getSubdelegacion() != null ? solicitud.getSubdelegacion().getDescripcion() : "",
				convertStringDateFormat(flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramiteCorreccionCurp.getTramiteId()).getInicioTramite().getFechaSolicitud()),
				url,
				((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getCurp() }));
			
		body.append(PIE_PAGINA);
		
		return body.toString();
	}
	
	public String contenidoCorreoResponsable(Solicitud solicitud, mx.gob.imss.ctirss.delta.model.Usuario responsable) {
		
		StringBuffer body= new StringBuffer();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		
		body.append(ENCABEZADO_CORREOS);
		
		body.append(MessageFormat.format(CONTENIDO_CORREO_RESPONSABLE,
				new Object[] { 
				fecha.format(new Date()), 
				solicitud.getNoFolioSolicitud(),
				StringEscapeUtils.escapeHtml(responsable.getFisica().getNombre()!=null?responsable.getFisica().getNombre():""),
				StringEscapeUtils.escapeHtml(responsable.getFisica().getPrimerApellido()!=null?responsable.getFisica().getPrimerApellido():""),
				StringEscapeUtils.escapeHtml(responsable.getFisica().getSegundoApellido()!=null?responsable.getFisica().getSegundoApellido():""),
				solicitud.getPersonaInteresada().getCurp()!=null?solicitud.getPersonaInteresada().getCurp():"",
				((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getListaNSS().get(0)!=null?((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getListaNSS().get(0):"",
				StringEscapeUtils.escapeHtml(solicitud.getPersonaInteresada().getNombreCompleto()!=null?solicitud.getPersonaInteresada().getNombreCompleto():"")}));
		
		body.append(PIE_PAGINA);
		
		return body.toString();
	}

	public String contenidoCorreoAutorizador(Solicitud solicitud, Fisica autorizador) {
	
		StringBuffer body= new StringBuffer();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		
		body.append(ENCABEZADO_CORREOS);
		
		body.append(MessageFormat.format(CONTENIDO_CORREO_AUTORIZADOR,
				new Object[] { 
				fecha.format(new Date()), 
				solicitud.getNoFolioSolicitud(),
				StringEscapeUtils.escapeHtml(autorizador.getNombre()!=null?autorizador.getNombre():""),
				StringEscapeUtils.escapeHtml(autorizador.getPrimerApellido()!=null?autorizador.getPrimerApellido():""),
				StringEscapeUtils.escapeHtml(autorizador.getSegundoApellido()!=null?autorizador.getSegundoApellido():""),
				StringEscapeUtils.escapeHtml(solicitud.getSubdelegacion().getDescripcion()!=null?solicitud.getSubdelegacion().getDescripcion():""),
				solicitud.getPersonaInteresada().getCurp()!=null?solicitud.getPersonaInteresada().getCurp():"",
				((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getListaNSS().get(0)!=null?((TramiteCorreccionCurp)solicitud.getTramites().get(0)).getListaNSS().get(0):"",
				StringEscapeUtils.escapeHtml(solicitud.getPersonaInteresada().getNombreCompleto()!=null?solicitud.getPersonaInteresada().getNombreCompleto():"")}));
		
		body.append(PIE_PAGINA);
		
		return body.toString();
	}
	
	private String  convertStringDateFormat(String dateString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_MASK);
		try {
			return simpleDateFormat.format(simpleDateFormat.parse(dateString));
		} catch (ParseException e) {
			return "";
		}
	
	}
}
