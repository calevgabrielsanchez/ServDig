package mx.gob.imss.ctirss.delta.comet.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.ctirss.delta.comet.model.ProcesamientoSolicitudCometData;
import mx.gob.imss.ctirss.delta.comet.model.enums.CometChannelEnum;
import mx.gob.imss.ctirss.delta.comet.service.SolicitudHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.util.DeltaPubSubSeverClient;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudHandler extends AbstractServiceBusiness implements
		SolicitudHandlerLocal {

	@Autowired
	private DeltaPubSubSeverClient client;

	@Override
	public void publicarInicioProcesamientoSolicitud(
			ProcesamientoSolicitudCometData data) throws IOException {
		publicarCommon(data.getFolio(),
				EstadoSolicitudEnum.PENDIENTE_AUTORIZACION, true, null);
	}

	@Override
	public void publicarFinProcesamientoSolicitud(
			ProcesamientoSolicitudCometData data) throws IOException {
		publicarCommon(data.getFolio(), EstadoSolicitudEnum.ATENDIDA,
				data.isExito(), data.getMensajeError());
	}

	private void publicarCommon(String folio,
			EstadoSolicitudEnum estadoSolicitud, boolean isExitoso,
			String mensajeError) throws IOException {
		log.info("--->Init publicarProcesamientoSolicitud");

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("folioSolicitud", folio);
		data.put("estadoSolicitud", estadoSolicitud.getCodigo());
		data.put("isExitoso", isExitoso);

		if (!isExitoso) {
			if (mensajeError != null && !StringUtils.isEmpty(mensajeError))
				data.put("mensajeError", mensajeError);
			else
				data.put("mensajeError", "Error al finalizar la solicitud "
						+ folio);

			log.error(mensajeError);
		} else {
			if (mensajeError != null && !StringUtils.isEmpty(mensajeError))
				data.put("mensajeExito", mensajeError);
			else
				data.put("mensajeExito", "La solicitud " + folio
						+ " fue finalizada exitosamente");
		}

		this.client.publish(
				CometChannelEnum.PUBLICAR_MODIF_SOLICITUD.getCodigo() + folio,
				data);
	}
}
