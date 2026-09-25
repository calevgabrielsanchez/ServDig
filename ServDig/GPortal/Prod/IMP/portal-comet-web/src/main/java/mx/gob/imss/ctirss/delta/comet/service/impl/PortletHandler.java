package mx.gob.imss.ctirss.delta.comet.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.ctirss.delta.comet.model.enums.CometChannelEnum;
import mx.gob.imss.ctirss.delta.comet.model.enums.PortletEnum;
import mx.gob.imss.ctirss.delta.comet.service.PortletHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.util.DeltaPubSubSeverClient;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortletHandler extends AbstractServiceBusiness implements
		PortletHandlerLocal {

	@Autowired
	private DeltaPubSubSeverClient client;

	@Override
	public void publicarModificacionRepresentadoLegal(Persona persona)
			throws IOException {
		log.info("publicando modificacion representado legal");

		if (persona instanceof Fisica) {
			Fisica fisica = (Fisica) persona;
			publish(PortletEnum.ID_PORTLET_REPRESENTADOS.getCodigo(), fisica
					.getIdPersona().toString());
		} else {
			Moral moral = (Moral) persona;
			publish(PortletEnum.ID_PORTLET_REPRESENTADOS.getCodigo(), moral
					.getCveMoral().toString());
		}
	}

	@Override
	public void publicarModificacionRepresentantesLegales(Persona persona)
			throws IOException {
		log.info("publicando modificacion representantes legales");

		if (persona instanceof Fisica) {
			Fisica fisica = (Fisica) persona;
			publish(PortletEnum.ID_PORTLET_REPRESENTANTES_LEGALES.getCodigo(),
					fisica.getIdPersona().toString());
		} else {
			Moral moral = (Moral) persona;
			publish(PortletEnum.ID_PORTLET_REPRESENTANTES_LEGALES.getCodigo(),
					moral.getCveMoral().toString());
		}
	}

	@Override
	public void publicarModificacionPatronesAsociados(Fisica fisica)
			throws IOException {
		log.info("publicando modificacion patrones asociados");
		publish(PortletEnum.ID_PORTLET_PATRONES_ASOCIADOS.getCodigo(), fisica
				.getIdPersona().toString());
	}

	@Override
	public void publicaModificacionClasificacion(String numeroRegistroPatronal)
			throws IOException {
		log.info("publicando modificacion clasificacion");
		publish(PortletEnum.ID_PORTLET_MODIFICACION_CLASIFICACION.getCodigo(),
				numeroRegistroPatronal);
	}

	@Override
	public void publicarSolicitudesPatron(String numeroRegistroPatronal)
			throws IOException {
		log.info("publicando portlet solicitudes de persona");
		publish(PortletEnum.ID_PORTLET_SOLICITUDES_PATRON.getCodigo(),
				numeroRegistroPatronal);
	}

	@Override
	public void publicarSolicitudesPersona(Persona persona) throws IOException {
		log.info("publicando portlet solicitudes de persona");

		if (persona instanceof Fisica) {
			Fisica fisica = (Fisica) persona;
			publish(PortletEnum.ID_PORTLET_SOLICITUDES.getCodigo(), fisica
					.getIdPersona().toString());
		} else {
			Moral moral = (Moral) persona;
			publish(PortletEnum.ID_PORTLET_SOLICITUDES.getCodigo(), moral
					.getCveMoral().toString());
		}
	}

	@Override
	public void publicarModificacionPersonaAutorizada(Persona persona)
			throws IOException {
		log.info("publicando portlet de persona autorizada");
		publish(PortletEnum.ID_PORTLET_PERSONA_AUTORIZADA.getCodigo(), persona
				.getIdPersona().toString());
	}

	@Override
	public void publicarIVRO(Persona persona) throws IOException {
		log.info("publicando portlet de IVRO");
		publish(PortletEnum.ID_PORTLET_IVRO.getCodigo(), persona.getIdPersona()
				.toString());
	}

	@Override
	public void publicarDetalleIdentidad(Persona persona) throws IOException {
		log.info("publicando detalle de identidad");
		publish(PortletEnum.ID_DETALLE_IDENTIDAD.getCodigo(), persona
				.getIdPersona().toString());
	}

	@Override
	public void publicarDetalleSujeto(Persona persona) throws IOException {
		log.info("publicando detalle de sujeto");
		publish(PortletEnum.ID_DETALLE_SUJETO.getCodigo(), persona
				.getIdPersona().toString());
	}

	private void publish(String idPortlet, String idPersona) throws IOException {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("idPersona", idPersona);
		data.put("idPortlet", idPortlet);

		this.client.publish(
				CometChannelEnum.AFECTAR_PORTLETS.getCodigo()
						+ idPersona, data);

	}
}