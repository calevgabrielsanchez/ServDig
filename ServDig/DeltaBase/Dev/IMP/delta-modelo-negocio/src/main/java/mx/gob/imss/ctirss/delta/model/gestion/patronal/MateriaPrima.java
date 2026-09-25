/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart-nez Cham-nica
 *  @Proyecto: delta
 *  @Archivo: MateriaPrima.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha: 13:12:50
 */
public class MateriaPrima extends ItemClasificacion{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1016471336664121391L;
	
	private Long id;
	private String descripcion;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MateriaPrima [id=");
		builder.append(id);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append(", "+super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
