package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.Stateless;

/**
 * Utileria para generar archivos Zip
 * @author User
 *
 */
@Stateless
public class ZipFileUtilityService implements ZipFileUtilityServiceLocal {

	/*
	 * (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.cobranza.service.utility.ZipFileUtilityServiceLocal#generaArchivoZip(java.util.Map)
	 */
	@Override
	public byte[] generaArchivoZip(Map<String, Object> datosArchivos) throws IOException {
		
		byte[] byteArchivo = new byte[0];
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ZipOutputStream zout = new ZipOutputStream(out);	
		
		for(String filename : datosArchivos.keySet()){
			byte[] byteArrayFile = (byte[]) datosArchivos.get(filename);
			
			addToZipFile(zout, byteArrayFile, filename);
		}
		
		zout.close();
		out.close();

		byteArchivo = out.toByteArray();

		return byteArchivo;
	}
	
	/**
	 * Agrega un documento al archivo zip
	 * @param zout OutputStream del archivo zip
	 * @param byteArray byte Array con la información del archivo
	 * @param nombreArchivo Nombre del archivo
	 * @throws IOException 
	 */
	private void addToZipFile(ZipOutputStream zout, byte[] byteArray, String nombreArchivo)
			throws IOException {
		int length = byteArray.length;
		ZipEntry zipEntry = new ZipEntry(nombreArchivo);
		zout.putNextEntry(zipEntry);

		zout.write(byteArray, 0, length);
		zout.closeEntry();
	}

}
