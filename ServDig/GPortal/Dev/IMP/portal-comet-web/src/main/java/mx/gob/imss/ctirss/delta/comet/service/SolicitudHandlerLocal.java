package mx.gob.imss.ctirss.delta.comet.service;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.comet.model.ProcesamientoSolicitudCometData;

public interface SolicitudHandlerLocal {
	void publicarInicioProcesamientoSolicitud(
			ProcesamientoSolicitudCometData data) throws IOException;

	void publicarFinProcesamientoSolicitud(ProcesamientoSolicitudCometData data)
			throws IOException;
}
