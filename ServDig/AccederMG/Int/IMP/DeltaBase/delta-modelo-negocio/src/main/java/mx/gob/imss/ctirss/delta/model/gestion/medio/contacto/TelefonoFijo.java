/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:TelefonoFijo.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.gestion.medio.contacto
 *  @Fecha:03/05/2012
 */
package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

/**
 * @author Lucio Duran Silva
 *
 */
public class TelefonoFijo extends MedioContacto {

	
	//El numero del telefono fijo
	private String numero;
	
	// Clave de larga distancia
	private String claveLada;
	
	// Extension del telefono.
	private String extension;
	
	
	
	//TNO MODIFICAR NO QUITAR 
	public TelefonoFijo(){
	
	}
	
	
	public TelefonoFijo(String numero, String claveLada, String ext){
		this.numero=numero;
		this.claveLada=claveLada;
		this.extension=ext;
		
		this.setTipoMedioContacto(new  TipoMedioContacto());
		this.getTipoMedioContacto().setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
		
	}
	
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the claveLada
	 */
	public String getClaveLada() {
		return claveLada;
	}

	/**
	 * @param claveLada the claveLada to set
	 */
	public void setClaveLada(String claveLada) {
		this.claveLada = claveLada;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TelefonoFijo [numero=");
		builder.append(numero);
		builder.append(", claveLada=");
		builder.append(claveLada);
		builder.append(", extension=");
		builder.append(extension);
		builder.append("]");
		return builder.toString();
	}
	
}
