/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:TelefonoMovil.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.gestion.medio.contacto
 *  @Fecha:03/05/2012
 */
package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

/**
 * @author Lucio Duran Silva
 *
 */
public class TelefonoMovil extends MedioContacto {
	
	public TelefonoMovil() {
		// TODO Auto-generated constructor stub
	}
	
	public TelefonoMovil(String numero) {
		this.setTipoMedioContacto(new  TipoMedioContacto());
		this.getTipoMedioContacto().setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
		this.numero=numero;
		this.setDesFormaContacto(numero);
	}
	
	private String numero;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TelefonoMovil [numero=");
		builder.append(numero);
		builder.append("]");
		return builder.toString();
	}
	
	

}
