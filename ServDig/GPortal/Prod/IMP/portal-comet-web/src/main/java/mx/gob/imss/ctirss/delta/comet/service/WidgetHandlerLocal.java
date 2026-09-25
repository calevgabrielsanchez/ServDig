package mx.gob.imss.ctirss.delta.comet.service;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

public interface WidgetHandlerLocal {
	
	void publicarModificacionPersonaFisica(Fisica persona) throws IOException;

	void publicarModificacionPersonaMoral(Moral persona) throws IOException;

	void publicarModificacionMediosParticularesFisica(Fisica persona)
			throws IOException;

	void publicarModificacionMediosFiscalesFisica(Fisica persona)
			throws IOException;

	void publicarModificacionMediosFiscalesMoral(Moral persona)
			throws IOException;

	void publicarModificacionDomicilioParticularFisica(Fisica persona)
			throws IOException;

	void publicarModificacionDomicilioFiscalFisica(Fisica persona)
			throws IOException;

	void publicarModificacionDomicilioFiscalMoral(Moral persona)
			throws IOException;

	void publicarModificacionClasificacion(String numeroRegistroPatronal)
			throws IOException;

	void publicarModificacionDomicilioCentroTrabajo(
			String numeroRegistroPatronal) throws IOException;

	void publicarModificacionMediosCentroTrabajo(String numeroRegistroPatronal)
			throws IOException;

	void publicarAltaBeneficio(Fisica persona) throws IOException;

	void publicarIvro(Fisica persona) throws IOException;
}
