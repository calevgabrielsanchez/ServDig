package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

@Local
public interface ReporteHelperLocal {

	ByteArrayOutputStream ejecutaReporte(Map parametros,List<? extends Serializable> lista, String reporte);
	byte[] reporteSimpleConQR(Solicitud solicitud);
	
	byte[] reporte(Solicitud solicitud);
	byte[] reporteLocalizacionNss(Solicitud solicitud);

	Map<String, Object> getModelRecuperado(TramiteAsegurado tramite,
			String cadenaOriginal, String selloDigital,
			String secuenciaNotaria, String serie, Solicitud solicitud);

	Map<String, Object> getModel(Solicitud solicitud);

	Map<String, Object> getModelExterno(Solicitud solicitud);
	
	byte[] getReporteVacio();
	
	byte[] reporteSimpleConQRImgane(AsignacionNSS asignacionNSS);

}
