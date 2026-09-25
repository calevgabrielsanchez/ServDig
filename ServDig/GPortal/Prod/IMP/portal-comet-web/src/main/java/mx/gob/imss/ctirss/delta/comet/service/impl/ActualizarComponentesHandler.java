package mx.gob.imss.ctirss.delta.comet.service.impl;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.comet.model.RefrescarCometData;
import mx.gob.imss.ctirss.delta.comet.service.WidgetHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.service.PortletHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.service.ActualizarComponentesHandlerLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizarComponentesHandler extends AbstractServiceBusiness
		implements ActualizarComponentesHandlerLocal {

	@Autowired
	private WidgetHandlerLocal widgetHandler;
	@Autowired
	private PortletHandlerLocal portletHandler;

	@Override
	public void publicarComet(RefrescarCometData data) throws IOException {
		if (data.getExito() != null && !data.getExito()) {
			this.publicarCometError(data);
		} else {
			this.publicarCometExito(data);
		}
	}

	private void publicarCometExito(RefrescarCometData data) throws IOException {
		
		this.log.info("Se va a publicar mensaje desde exito con los siguientes datos "
				+ data);
		
		Integer idTipoTramite = data.getIdTipoTramite();
		Long idPersona = data.getIdPersona();
		Long idTipoPersona = data.getIdTipoPersona();
		String regPatronal = data.getRegPatronal();

		Fisica fisica = null;
		Moral moral = null;

		if (idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES
				.getCodigo())) {
			this.log.debug("Tramite Datos Generales:::");
			if (idPersona != null && idTipoPersona != null) {
				if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);
					this.log.debug("Refresh componentes datos fisicos:::");
					this.widgetHandler
							.publicarModificacionPersonaFisica(fisica);
					this.widgetHandler
							.publicarModificacionDomicilioFiscalFisica(fisica);
					this.widgetHandler
							.publicarModificacionMediosFiscalesFisica(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);

					this.portletHandler.publicarDetalleIdentidad(fisica);

					this.log.debug("Se publico mensaje desde el tramite de ACTUALIZACION_DATOS_GENERALES_FISICA");

				} else {
					moral = new Moral();
					moral.setIdPersona(idPersona);
					moral.setCveMoral(idPersona);
					this.log.debug("Refresh componentes datos morales:::");
					this.widgetHandler.publicarModificacionPersonaMoral(moral);
					this.widgetHandler
							.publicarModificacionDomicilioFiscalMoral(moral);
					this.widgetHandler
							.publicarModificacionMediosFiscalesMoral(moral);
					this.portletHandler.publicarSolicitudesPersona(moral);

					this.portletHandler.publicarDetalleIdentidad(moral);

					this.log.debug("Se publico mensaje desde el tramite de ACTUALIZACION_DATOS_GENERALES_MORAL");
				}
			} else {
				this.log.error("No se pudo realizar la publicación para ACTUALIZACION_DATOS_GENERALES ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite
				.equals(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR
						.getCodigo())) {
			this.log.debug("Tramite Domicilio:::");
			if (idPersona != null && idTipoPersona != null) {
				if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);
					this.log.debug("Refresh componentes domicilio:::");
					this.widgetHandler
							.publicarModificacionDomicilioParticularFisica(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);

					this.portletHandler.publicarDetalleIdentidad(fisica);

					this.log.debug("Se publico mensaje desde el tramite de ACTUALIZACION_DOMICILIO_PARTICULAR");
				} else {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);
					this.log.debug("Refresh componentes domicilio:::");
					this.widgetHandler
							.publicarModificacionDomicilioParticularFisica(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);

					this.log.debug("Se publico mensaje desde el tramite de ACTUALIZACION_DOMICILIO_PARTICULAR");
					this.log.debug("Se recibio una persona moral para ACTUALIZACION_DOMICILIO_PARTICULAR");
				}
			} else {
				this.log.error("No se pudo realizar la publicación para ACTUALIZACION_DOMICILIO_PARTICULAR ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite
				.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO
						.getCodigo())) {
			if (idPersona != null && idTipoPersona != null) {
				this.log.debug("Tramite Medios Contacto:::");
				if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);
					this.log.debug("Refresh componentes de medios:::");
					this.widgetHandler
							.publicarModificacionMediosParticularesFisica(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);

					this.portletHandler.publicarDetalleIdentidad(fisica);

					this.log.debug("Se publico mensaje desde el tramite de ACTUALIZACION_DATOS_CONTACTO");
				} else {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);
					this.log.debug("Refresh componentes de medios:::");
					this.widgetHandler
							.publicarModificacionMediosParticularesFisica(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);

					this.log.debug("Se recibio una persona moral para ACTUALIZACION_DATOS_CONTACTO");
				}
			} else {
				this.log.error("No se pudo realizar la publicación para ACTUALIZACION_DATOS_CONTACTO ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT.getCodigo())
				|| idTipoTramite
						.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo())) {
			if (idPersona != null && idTipoPersona != null) {
				if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);

					this.portletHandler
							.publicarModificacionPatronesAsociados(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);
					this.log.debug("Se publico mensaje desde el tramite de ALTA_SRT");
				} else {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);

					this.portletHandler
							.publicarModificacionPatronesAsociados(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);
					this.log.debug("Se recibio una persona moral para ALTA_SRT");
				}
			} else {
				this.log.error("No se pudo realizar la publicación para ALTA_SRT ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite
				.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO
						.getCodigo())) {
			if (StringUtils.isNotBlank(regPatronal)) {
				this.widgetHandler
						.publicarModificacionDomicilioCentroTrabajo(regPatronal);
				this.widgetHandler
						.publicarModificacionMediosCentroTrabajo(regPatronal);
				this.portletHandler
						.publicaModificacionClasificacion(regPatronal);
				this.portletHandler.publicarSolicitudesPatron(regPatronal);
				this.log.debug("Se publico mensaje desde el tramite de ACTUALIZACION_CENTRO_TRABAJO");
			} else {
				this.log.error("No se pudo realizar la publicación para ACTUALIZACION_CENTRO_TRABAJO ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA
				.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY
						.getCodigo())
				|| idTipoTramite
						.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES
								.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS
						.getCodigo())
				|| idTipoTramite
						.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES
								.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo())
				|| idTipoTramite
						.equals(TipoTramiteEnum.ENAJENACION.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO
						.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO
						.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL
						.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION
						.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO
						.getCodigo())
				
				) {
			if (StringUtils.isNotBlank(regPatronal)) {
				this.portletHandler
						.publicaModificacionClasificacion(regPatronal);
				this.portletHandler.publicarSolicitudesPatron(regPatronal);
				this.log.debug("Se publico mensaje desde el tramite de MODIFICACION_CLASIFICACION");
			} else {
				this.log.error("No se pudo realizar la publicación para MODIFICACION_CLASIFICACION ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite
				.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL
						.getCodigo())
				|| idTipoTramite
						.equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL
								.getCodigo())) {
			if (idPersona != null && idTipoPersona != null) {
				if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);

					this.portletHandler
							.publicarModificacionRepresentantesLegales(fisica);
					this.portletHandler
							.publicarModificacionRepresentadoLegal(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);
					this.log.debug("Se publico mensaje desde el tramite de REPRESENTANTE_LEGAL");
				} else {
					this.log.info("Se recibio una persona moral para REPRESENTANTE_LEGAL");
					moral = new Moral();
					moral.setCveMoral(idPersona);

					this.portletHandler
							.publicarModificacionRepresentantesLegales(moral);
					this.portletHandler
							.publicarModificacionRepresentadoLegal(moral);
					this.portletHandler.publicarSolicitudesPersona(moral);
				}
			} else {
				this.log.error("No se pudo realizar la publicación para REPRESENTANTE_LEGAL ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite.equals(TipoTramiteEnum.PERSONAS_AUTORIZADAS
				.getCodigo())
				|| idTipoTramite.equals(TipoTramiteEnum.BAJA_PERSONA_AUTORIZADA
						.getCodigo())) {

			if (idPersona != null) {
				Persona persona = new Persona();
				persona.setIdPersona(idPersona);
				this.portletHandler
						.publicarModificacionPersonaAutorizada(persona);
			} else {
				Persona persona = new Persona();
				persona.setIdPersona(idPersona);
				this.portletHandler
						.publicarModificacionPersonaAutorizada(persona);
				this.log.error("No se pudo realizar la publicación para Personas autorizadas ya que no se recibieron los datos necesarios.");
			}
		} else if (idTipoTramite
				.equals(TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL
						.getCodigo())) {
			if (idPersona != null && idTipoPersona != null) {
				if (idPersona.equals(TipoPersonaEnum.FISICA.getId())) {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);

					this.portletHandler
							.publicarModificacionPatronesAsociados(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);
					this.log.debug("Se publico mensaje desde el tramite de ALTA_SRT");
				} else {
					fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);

					this.portletHandler
							.publicarModificacionPatronesAsociados(fisica);
					this.portletHandler.publicarSolicitudesPersona(fisica);
					this.log.debug("Se publico mensaje desde el tramite de ALTA_SRT");
				}
			}
		} else if (idTipoTramite.equals(TipoTramiteEnum.ALTA_RIF.getCodigo())) {
			if (idPersona != null) {
				fisica = new Fisica();
				fisica.setIdPersona(idPersona);
				fisica.setCveFisica(idPersona);

				this.widgetHandler.publicarAltaBeneficio(fisica);
				this.portletHandler.publicarSolicitudesPersona(fisica);
				this.log.debug("Se publico mensaje desde el tramite de ALTA_RIF");
			} else {
				this.log.error("No se publico mensaje para el tramite de ALTA_RIF ya que no se recibieron los datos necesarios");
			}
		} else if (idTipoTramite
				.equals(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo())
				|| idTipoTramite
						.equals(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL
								.getCodigo())) {

			if (idPersona != null) {
				fisica = new Fisica();
				fisica.setIdPersona(idPersona);
				this.widgetHandler.publicarIvro(fisica);
				this.portletHandler.publicarSolicitudesPersona(fisica);
				this.log.debug("Se publico mensaje desde el tramite de IVRO");
			} else {
				this.log.error("No se publico mensaje para el tramite de IVRO ya que no se recibieron los datos necesarios");
			}
		} else if (idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO
				.getCodigo())
				|| idTipoTramite
						.equals(TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO
								.getCodigo())) {

			if (idPersona != null) {
				fisica = new Fisica();
				fisica.setIdPersona(idPersona);
				this.portletHandler.publicarIVRO(fisica);
				this.portletHandler.publicarSolicitudesPersona(fisica);

				this.log.debug("Se publico mensaje desde el tramite de IVRO DOMESTICO");
			} else {
				this.log.error("No se publico mensaje para el tramite de IVRO DOMESTICO ya que no se recibieron los datos necesarios");
			}
		} else {
			this.log.warn("El tipo de tramite [" + idTipoTramite
					+ "] recibido no tiene acciones definidas para refrescar");
		}
	}
	
	private void publicarCometError(RefrescarCometData data) throws IOException {
		
		this.log.info("Se va a publicar mensaje desde error con los siguientes datos "
				+ data);
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(data.getIdPersona());
		
		this.portletHandler.publicarSolicitudesPersona(fisica);
		
	}
}
