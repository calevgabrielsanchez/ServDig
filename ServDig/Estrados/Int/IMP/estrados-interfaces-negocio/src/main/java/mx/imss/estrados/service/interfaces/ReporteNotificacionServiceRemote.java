package mx.imss.estrados.service.interfaces;

import java.awt.image.BufferedImage;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public interface ReporteNotificacionServiceRemote {
	
	public boolean generarReportePDF(Map<String, Object> parametrosReporte, Integer tipoReporte);
	public void enviarMailEliminacion(String email, String subject, String cuerpo);
	public BufferedImage generadorCodigoQrCadena(String cadena, int tamanioWidth, int tamanioHeight);

}
