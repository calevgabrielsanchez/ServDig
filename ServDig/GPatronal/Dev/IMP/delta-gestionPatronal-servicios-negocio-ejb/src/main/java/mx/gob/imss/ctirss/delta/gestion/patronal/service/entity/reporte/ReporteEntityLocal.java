package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.reporte;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface ReporteEntityLocal {
	
	Connection retrieveCMTConnection();
	
	ByteArrayOutputStream ejecutaAvisoDeModificacion(Map<String, Object> parametros);
}
