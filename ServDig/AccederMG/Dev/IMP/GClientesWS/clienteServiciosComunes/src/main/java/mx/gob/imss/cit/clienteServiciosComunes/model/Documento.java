package mx.gob.imss.cit.clienteServiciosComunes.model;


public class Documento {

	private byte[] archivo;
	private String nombreArchivo;
	private String mimeType;
	private String extencion;
	private boolean encriptado = true;
	private boolean folder = false;	
	private String idDocumento;

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getExtencion() {
		return extencion;
	}

	public void setExtencion(String extencion) {
		this.extencion = extencion;
	}

	public boolean isEncriptado() {
		return encriptado;
	}

	public void setEncriptado(boolean encriptado) {
		this.encriptado = encriptado;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	

}
