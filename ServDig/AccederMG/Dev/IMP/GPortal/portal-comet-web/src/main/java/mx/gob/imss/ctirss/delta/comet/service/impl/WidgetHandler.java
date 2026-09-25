package mx.gob.imss.ctirss.delta.comet.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.ctirss.delta.comet.model.enums.CometChannelEnum;
import mx.gob.imss.ctirss.delta.comet.model.enums.WidgetEnum;
import mx.gob.imss.ctirss.delta.comet.service.WidgetHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.util.DeltaPubSubSeverClient;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WidgetHandler extends AbstractServiceBusiness implements
		WidgetHandlerLocal {

	@Autowired
	private DeltaPubSubSeverClient client;

	@Override
	public void publicarModificacionPersonaFisica(Fisica persona)
			throws IOException {

		publicarCommon(persona.getIdPersona().toString(),
				WidgetEnum.ID_WIDGET_IDENTIDAD_PERSONA.getCodigo());
	}

	@Override
	public void publicarModificacionPersonaMoral(Moral persona)
			throws IOException {

		publicarCommon(persona.getCveMoral().toString(),
				WidgetEnum.ID_WIDGET_IDENTIDAD_PERSONA.getCodigo());

	}

	@Override
	public void publicarModificacionMediosParticularesFisica(Fisica persona)
			throws IOException {

		publicarCommon(persona.getIdPersona().toString(),
				WidgetEnum.ID_WIDGET_IDENTIDAD_PERSONA.getCodigo());
	}

	@Override
	public void publicarModificacionMediosFiscalesFisica(Fisica persona)
			throws IOException {

		publicarCommon(persona.getIdPersona().toString(),
				WidgetEnum.ID_WIDGET_FISCALES_PERSONA.getCodigo());
	}

	@Override
	public void publicarModificacionMediosFiscalesMoral(Moral persona)
			throws IOException {

		publicarCommon(persona.getCveMoral().toString(),
				WidgetEnum.ID_WIDGET_FISCALES_PERSONA.getCodigo());
	}

	@Override
	public void publicarModificacionDomicilioParticularFisica(Fisica persona)
			throws IOException {

		publicarCommon(persona.getIdPersona().toString(),
				WidgetEnum.ID_WIDGET_IDENTIDAD_PERSONA.getCodigo());
	}

	@Override
	public void publicarModificacionDomicilioFiscalFisica(Fisica persona)
			throws IOException {

		publicarCommon(persona.getIdPersona().toString(),
				WidgetEnum.ID_WIDGET_FISCALES_PERSONA.getCodigo());
	}

	@Override
	public void publicarModificacionDomicilioFiscalMoral(Moral persona)
			throws IOException {

		publicarCommon(persona.getCveMoral().toString(),
				WidgetEnum.ID_WIDGET_FISCALES_PERSONA.getCodigo());
	}

	@Override
	public void publicarModificacionClasificacion(String numeroRegistroPatronal)
			throws IOException {

		log.info("publicando Modificacion de Clasificacion");

		publicarModificacionDomicilioCentroTrabajo(numeroRegistroPatronal);
		publicarModificacionMediosCentroTrabajo(numeroRegistroPatronal);
	}

	@Override
	public void publicarModificacionDomicilioCentroTrabajo(
			String numeroRegistroPatronal) throws IOException {
		publicarCommon(numeroRegistroPatronal,
				WidgetEnum.ID_WIDGET_CENTRO_TRABAJO.getCodigo());
	}

	@Override
	public void publicarModificacionMediosCentroTrabajo(
			String numeroRegistroPatronal) throws IOException {
		publicarCommon(numeroRegistroPatronal,
				WidgetEnum.ID_WIDGET_CENTRO_TRABAJO.getCodigo());
	}

	@Override
	public void publicarAltaBeneficio(Fisica persona) throws IOException {
		publicarCommon(persona.getIdPersona().toString(),
				WidgetEnum.ID_WIDGET_BENEFICIOS.getCodigo());
	}

	@Override
	public void publicarIvro(Fisica persona) throws IOException {
		publicarCommon(persona.getIdPersona().toString(),
				WidgetEnum.ID_WIDGET_IVRO.getCodigo());
	}

	private void publicarCommon(String idPersona, String widgetName)
			throws IOException {

		this.log.info("Se va a publicar para widget - " + widgetName);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("idPersona", idPersona);
		data.put("idWidget", widgetName);

		this.client.publish(CometChannelEnum.PUBLICAR_MODIF_PERSONA.getCodigo()
				+ idPersona, data);

	}
}
