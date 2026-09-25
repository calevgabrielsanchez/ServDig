package mx.imss.ctirss.presentacion.service.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GeneraReporteService {
	
	@SuppressWarnings("rawtypes")
	public byte[] generaReporte(String rutaArchivoJasper, Map parametros, List<Object> datasource);
	@SuppressWarnings("rawtypes")
	public byte[] generaReporte(String rutaArchivoJasper, Map parametros, Collection<Object> datasource);
	@SuppressWarnings("rawtypes")
	public byte[] generaReporte(String rutaArchivoJasper, Map parametros);

}
