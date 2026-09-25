package mx.imss.estrados.service.ejb.tramiteDigital;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;


@Local
public interface TramiteDigitalServiceLocal {

		public RespuestaFirmadoSimple getSelloDigital(String cadenaOriginal,
				String secuenciaNotaria, String rfc);

	
}
