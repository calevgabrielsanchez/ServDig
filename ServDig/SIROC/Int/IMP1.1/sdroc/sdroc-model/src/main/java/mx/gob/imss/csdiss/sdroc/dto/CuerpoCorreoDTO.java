/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.dto;

/**
 * @author daniel.hernandez
 *
 */
public class CuerpoCorreoDTO {
	
	/**
	 * Uno o varios destinatarios del correo.
	 */
	private String[] destinatarios;
	/**
	 * Destinatario de correo 
	 */
	private String destinatario;
	/**
	 * Revisar las politicas sobre los tipos de archivos permitidos 
	 * y el tamaño de cada archivo. Formato esperado PDF 
	 */
	private byte[] adjunto;
	/**
	 * Titulo del correo.
	 */
	private String titulo;
	/**
	 * Se puede enviar formato plano o texto formateado como HTML.
	 */
	private StringBuilder contenido;
	/**
	 * Nombre del archivo adjunto
	 */
	private String nombreAdjunto;
	
	/**
	 * @return the destinatarios
	 */
	public String[] getDestinatarios() {
		return destinatarios;
	}
	/**
	 * @param destinatarios the destinatarios to set
	 */
	public void setDestinatarios(String[] destinatarios) {
		this.destinatarios = destinatarios;
	}
	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}
	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	/**
	 * @return the adjunto
	 */
	public byte[] getAdjunto() {
		return adjunto;
	}
	/**
	 * @param adjunto the adjunto to set
	 */
	public void setAdjunto(byte[] adjunto) {
		this.adjunto = adjunto;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido.toString();
	}
	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(StringBuilder contenido) {
		this.contenido = contenido;
	}
	/**
	 * @return the nombreAdjunto
	 */
	public String getNombreAdjunto() {
		return nombreAdjunto;
	}
	/**
	 * @param nombreAdjunto the nombreAdjunto to set
	 */
	public void setNombreAdjunto(String nombreAdjunto) {
		this.nombreAdjunto = nombreAdjunto;
	} 
	
}
