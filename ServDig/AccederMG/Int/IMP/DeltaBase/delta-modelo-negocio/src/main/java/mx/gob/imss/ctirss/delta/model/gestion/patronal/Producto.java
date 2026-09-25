package mx.gob.imss.ctirss.delta.model.gestion.patronal;


public class Producto extends ItemClasificacion{

	/**
	 * 
	 */
	private static final long serialVersionUID = -884730693090713994L;
	private String descripcion;
	private Long id;

	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Producto [descripcion=");
		builder.append(descripcion);
		builder.append(", id=");
		builder.append(id);
		builder.append(", "+super.toString());
		builder.append("]");
		return builder.toString();
	}
}
