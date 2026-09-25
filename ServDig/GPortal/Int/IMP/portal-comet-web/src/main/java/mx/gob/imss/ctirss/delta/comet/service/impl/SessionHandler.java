package mx.gob.imss.ctirss.delta.comet.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.ctirss.delta.comet.model.NotificacionFinSesionCometData;
import mx.gob.imss.ctirss.delta.comet.model.enums.CometChannelEnum;
import mx.gob.imss.ctirss.delta.comet.service.SessionHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.util.DeltaPubSubSeverClient;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionHandler extends AbstractServiceBusiness implements
		SessionHandlerLocal {

	@Autowired
	private DeltaPubSubSeverClient client;

	public void publicarComet(NotificacionFinSesionCometData data)
			throws IOException {
		this.log.info("Publicando fin de sesion para usuario "
				+ data.getUsuario());
		publish(data.getUsuario());
	}

	private void publish(String usuario) throws IOException {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("session_finished", Boolean.TRUE);

		this.client.publish(
				CometChannelEnum.USER_SESSION.getCodigo() + usuario, data);
	}
}
