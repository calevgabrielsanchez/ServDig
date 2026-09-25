package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.alta;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.ParametroSistemaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping (value = "/wizard/tramite/registro/movimientos/afiliatorios")
public class WizardRegistroMovimientosAfiliatorios extends AbstractController{
	
	private static final String KEY_FIRMA_ELECTRONICA 		= "datosFirmaElectronica";
	private static final String KEY_DESCRIPCION_TIPO_TRAMITE = "descripcionTipoTramite";
	private static final String KEY_TIPO_SOLICITUD 			= "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD 	= "descripcionTipoSolicitud";
	private static final String KEY_CADENA_ORIGINAL 		= "contenidoFirmar";	
	private static final String INICIO_WIZARD_CONTACTO 		= "wizardContactoCentroTrabajoInit";
	private static final String CONTENIDO_WIZARD_CONTACTO 	= "wizardContactoCentroTrabajoContenido";
	private static final String KEY_ID_SOLICITUD 			= "idSolicitud";
	private static final String KEY_FOLIO_SOLICITUD 		= "folioSolicitud";
	private static final String KEY_ID_TIPO_TRAMITE 		= "idTipoTramite";
	private static final String KEY_ATRIBUTE_MENSAJE 		= "mensaje";
	private static final String KEY_ATRIBUTE_ERROR 			= "error";	
	private static final String SOLICITUD_KEY 				= "solicitudToSession";
	private static final String SUJETO_OBLIGADO_KEY 		= "sujetoObligadoToSession";	
	private static final String DESC_TIPO_SOLICITUD 		= "MODIFICACIÓN DEL CENTRO DE TRABAJO";
	private static final String DESC_TIPO_TRAMITE			= "ACTUALIZACIÓN DE MEDIOS DE CONTACTO";
	private static final String KEY_ATRIBUTE_SOLICITUD 		= "solicitud";

	
	@Autowired
	private SujetoObligadoServiceBusinessRemote registroPatronalService;
	@Autowired
	private SolicitudServiceBusinessRemote gpSolicitudService;
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionBusiness;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired
	private ParametrosServiceBusinessRemote parametrosService;
	
	@RequestMapping(value = "/{numeroRegistroPatronal}/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String init(Model model, HttpSession session, HttpServletRequest request, 
		@PathVariable String numeroRegistroPatronal, @PathVariable Long idPersona, @PathVariable Integer idTipoPersona) {
		limpiarSession(session);
		String idSiteIdse=parametrosService.obtenerParametroDeConfiguracion(ParametroSistemaEnum.ID_SITE_IDSE.getCodigo());
		this.log.info("@@@@@@@@@@@@@@@@@@@@@@: "+idSiteIdse);
		model.addAttribute("idSite", idSiteIdse);
		return "WizardRegistroMovimientosAfiliatorios";
	
	}
	
	@RequestMapping(value = "/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitud(Model model,
			final HttpSession session, HttpServletRequest request,
			@ModelAttribute SujetoObligado sujetoObligado) {
		limpiarSession(session);
		return "WizardRegistroMovimientosAfiliatorios";
	}
	
	
	private void limpiarSession(final HttpSession session) {	
		session.removeAttribute(SOLICITUD_KEY);
		session.removeAttribute(KEY_FOLIO_SOLICITUD);		
		session.removeAttribute(KEY_ID_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);	
		session.removeAttribute(KEY_CADENA_ORIGINAL);		
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);		
		session.removeAttribute(SUJETO_OBLIGADO_KEY);
		session.removeAttribute(KEY_ID_TIPO_TRAMITE);
	}
	
	
}
