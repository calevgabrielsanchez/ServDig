package mx.gob.imss.cdsss.delta.portal.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.UmfDomicilioDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Ciudadano;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GeneraDocumentosAsincronos extends AbstractController{
	private static final String TRAMITE = "tramite";
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private EmailServiceRemote emailServiceRemote;
	@Autowired
	private UmfServiceRemote umfService;
	
	@Async
	@Transactional
	public void generaDocumentosAsincrono(Solicitud solicitud, HttpSession session) {
		log.debug("Empiezo a generar los documentos resultantes del tramite");
		Map<String, byte[]> doctosGenerados = null;
		Ciudadano ciudadano = (Ciudadano) session.getAttribute(Constants.KEY_CIUDADANO_SESSION);
		String correoCiudadano = (String) session.getAttribute(Constants.KEY_CORREO_CIUDADANO);
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) session.getAttribute(Constants.KEY_DATOS_DOM_UMF);
		UmfDomicilioDTO umfDomicilioAsociado = new UmfDomicilioDTO();
		String tipoTramite = (String) session.getAttribute(TRAMITE);

		String descripcionTipoTramite;
		if (StringUtils.isNotBlank(tipoTramite)) {
			if (tipoTramite.equals("cambioClinica")) {
				descripcionTipoTramite = "Cambio de Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else if (tipoTramite.equals("cambioClinicaD")) {
				descripcionTipoTramite = "Cambio de Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else if (tipoTramite.equals("registro")) {
				descripcionTipoTramite = "Alta en Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else if (tipoTramite.equals("registroD")) {
				descripcionTipoTramite = "Alta en Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else {
				descripcionTipoTramite = " ";
			}
		} else {
			descripcionTipoTramite = " ";
		}

		if(solicitud != null) {
			doctosGenerados = new HashMap<String, byte[]>();
			
			solicitud = solicitudBusinessRemote.agregarListadosDocumentosATramites(solicitud);
			
			for(Tramite tramite: solicitud.getTramites()) {
				for(DocumentoPorTipo docto: tramite.getDocumentoPorTipos()) {
					try {
						byte[] doctoGen = solicitudBusinessRemote.obtenerDocumentoResultante(solicitud, tramite.getTramiteId(), docto.getIdDocumentoPorTipo().intValue());
						
						if(doctoGen != null) {
							log.debug("Se genero el documento " + docto.getDocumento().getDesDocumento());
							doctosGenerados.put(docto.getDocumento().getDesDocumento()+".pdf", doctoGen);
							session.setAttribute(""+docto.getIdDocumentoPorTipo(), doctoGen);
						} else {
							log.debug("No se genero el documento " + docto.getDocumento().getDesDocumento());
						}
					} catch(Exception e) {
						log.error("ocurrio un error al generar el documento " + docto.getDocumento().getDesDocumento(), e);
					}
				}
			}
			
			if (correoCiudadano != null) {
				try {
					String[] toEmail = { correoCiudadano };
					String strFechaOperacion = DateFormat.getDateInstance(
							DateFormat.FULL, new Locale("es", "MX")).format(new Date());
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
					String strFechaSolicitud = sdf.format(solicitud.getFechaSolicitud());

					Map<String, String> mailAttr = new HashMap<String, String>();
					mailAttr.put("fechaOperacion", strFechaOperacion);
					mailAttr.put("folio", solicitud.getNoFolioSolicitud());
					mailAttr.put("idSolicitud", solicitud.getSolicitudId().toString());
					
					InstanceofPredicate tramiteRegDhabPredicate = new InstanceofPredicate(TramiteRegistroDerechohabiente.class);
					InstanceofPredicate tramiteCorrDhabPredicate = new InstanceofPredicate(TramiteCorreccionDerechohabiente.class);
					Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteRegDhabPredicate);
					Object tramiteModifInicial = CollectionUtils.find(solicitud.getTramites(), tramiteCorrDhabPredicate);
					
					if (tramiteInicial != null) {
						TramiteRegistroDerechohabiente tramiteRegistro = (TramiteRegistroDerechohabiente) tramiteInicial;
						umfDomicilioAsociado.setMedicoEnTurno(tramiteRegistro.getMedicoEnTurno());
						mailAttr.put("idTipoTramite", tramiteRegistro.getTipoTramite().getIdTipoTramite().toString());
						mailAttr.put("nombreEmpaquetado", "registroDerechoHabiente_" + tramiteRegistro.getTramiteId() + ".pdf");
					} else if (tramiteModifInicial != null) {
						TramiteCorreccionDerechohabiente tramiteCorreccion = (TramiteCorreccionDerechohabiente) tramiteModifInicial;
						umfDomicilioAsociado.setMedicoEnTurno(tramiteCorreccion.getMedicoEnTurno());
						mailAttr.put("idTipoTramite", tramiteCorreccion.getTipoTramite().getIdTipoTramite().toString());
						mailAttr.put("nombreEmpaquetado", "cambioClinica_" + tramiteCorreccion.getTramiteId() + ".pdf");
					}

					if (umfDomicilio == null || umfDomicilio.getMedicoEnTurno() == null
							|| umfDomicilio.getMedicoEnTurno().getUnidadMedicaFamiliar() == null
							|| umfDomicilio.getMedicoEnTurno().getConsultorio() == null
							|| umfDomicilio.getMedicoEnTurno().getTurno() == null) {
						if (tramiteInicial != null || tramiteModifInicial != null) {
							umfDomicilio = umfDomicilioAsociado;
						}
					}
					
					// Datos de umf (si no se encuentran en sesion)
					UnidadMedicaFamiliar umf = umfDomicilio.getMedicoEnTurno().getUnidadMedicaFamiliar();
					if (StringUtils.isBlank(umf.getDesDireccion()) || StringUtils.isBlank(umf.getDescripcion())) {
						try {
							UnidadMedicaFamiliar umfLocalizada = umfService.getUnidadMedicaFamiliarById(umf.getIdUMF());
							umf.setDesDireccion(umfLocalizada.getDesDireccion());
							umf.setDescripcion(umfLocalizada.getDescripcion());
						} catch (DerechohabientesBusinessException e) {
							umf.setDesDireccion("No disponible");
							umf.setDescripcion("No disponible");
						}
					}

					mailAttr.put("fechaSolicitud", strFechaSolicitud);

					if (umfDomicilio != null && umfDomicilio.getMedicoEnTurno() != null) {
						MedicoEnTurno medicoEnTurno = umfDomicilio.getMedicoEnTurno();

						mailAttr.put("clinicaAsignada", medicoEnTurno.getUnidadMedicaFamiliar().getDescripcion());
						mailAttr.put("direccionClinica", medicoEnTurno.getUnidadMedicaFamiliar().getDesDireccion());
						mailAttr.put("turno", medicoEnTurno.getTurno().getDescripcion());
						mailAttr.put("consultorio", medicoEnTurno.getConsultorio().getIdConsultorio().toString());
					} else {
						mailAttr.put("clinicaAsignada", "-");
						mailAttr.put("direccionClinica", "-");
						mailAttr.put("turno", "-");
						mailAttr.put("consultorio", "-");
					}

					if (ciudadano != null
							&& StringUtils.isNotBlank(ciudadano.getNombreCompleto())) {
						mailAttr.put("nombreCompleto", ciudadano.getNombreCompleto());
					} else {
						mailAttr.put("nombreCompleto", " ");
					}

					if (doctosGenerados.isEmpty()) {
						doctosGenerados = null;
					}

					if(StringUtils.isNotBlank(descripcionTipoTramite)){
						mailAttr.put("descripcionTipoTramite", descripcionTipoTramite);
					} else {
						mailAttr.put("descripcionTipoTramite", " ");
					}

					log.info("Parametros de correo: " + mailAttr);
					emailServiceRemote.enviarCorreoCambioRegistroClinicaByQueue(correoCiudadano,
							null, getAsunto(),
							doctosGenerados, mailAttr);
				} catch (Exception e) {
					log.error("No fue posible mandar el correo", e);
				}
			}
		}
	}
	
	private String getAsunto() {
		String asunto = "Tramite de registro en clinica con CURP";
		
		return asunto;
	}
	
	private String getBody() {
		String body = "";
		
		body = "Su solicitud ha finalizado correctamente";
		
		return body;
	}
	
	private static class InstanceofPredicate implements Serializable, Predicate {
		private static final long serialVersionUID = 1L;
		private final Class iType;

		public static Predicate getInstance(Class type) {
			if (type == null) {
				throw new IllegalArgumentException("The type to check instanceof must not be null");
			}

			return new InstanceofPredicate(type);
		}

		public InstanceofPredicate(Class type) {
			this.iType = type;
		}

		public boolean evaluate(Object object) {
			return this.iType.isInstance(object);
		}

		public Class getType() {
			return this.iType;
		}

	}
}
