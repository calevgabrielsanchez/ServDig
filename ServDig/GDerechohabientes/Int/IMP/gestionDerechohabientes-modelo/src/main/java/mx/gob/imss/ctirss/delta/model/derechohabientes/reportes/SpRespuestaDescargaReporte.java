package mx.gob.imss.ctirss.delta.model.derechohabientes.reportes;

import java.io.File;
import java.io.Serializable;

public class SpRespuestaDescargaReporte extends SpRespuestaCommon implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5792747513884622099L;

	private String nomArchivo;
	
	private File reporte;

	private byte[] byteArrayReporte;
	
	public SpRespuestaDescargaReporte(String nomArchivo, File reporte, Integer codProceso,
			String desProceso, byte[] byteArrayReporte) {
		super(codProceso, desProceso);
		this.nomArchivo = nomArchivo;
		this.reporte = reporte;
		this.byteArrayReporte = byteArrayReporte;
	}

	public String getNomArchivo() {
		return nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public File getReporte() {
		return reporte;
	}

	public void setReporte(File reporte) {
		this.reporte = reporte;
	}

	public byte[] getByteArrayReporte() {

		return byteArrayReporte;
	}

	public void setByteArrayReporte(byte[] byteArrayReporte) {

		this.byteArrayReporte = byteArrayReporte;
	}
}
