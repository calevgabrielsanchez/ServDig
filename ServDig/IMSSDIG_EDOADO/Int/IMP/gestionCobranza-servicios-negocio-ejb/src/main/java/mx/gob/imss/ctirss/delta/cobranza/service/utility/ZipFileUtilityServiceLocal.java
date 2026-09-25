package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.io.IOException;
import java.util.Map;

import javax.ejb.Local;

/**
 * Utileria para generar archivos Zip
 * @author Adali Cruz
 * 
 */
@Local
public interface ZipFileUtilityServiceLocal {

	/**
	 * Genera un byte array que descrive un archivo compreso en formato ZIP
	 * @param datosarchivos Mapa con el nombre del archivo y el byte array del archivo a comprimir
	 * @return byte array de respuesta
	 */
	byte[] generaArchivoZip(Map<String, Object> datosarchivos) throws IOException;
}
