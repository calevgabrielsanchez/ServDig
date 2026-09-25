package mx.gob.imss.ctirss.delta.model.derechohabientes;
 
import java.io.Serializable;

public class ConceptoDTO implements Serializable {

	private String nombre;
	private Integer cantidad;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(final Integer cantidad) {
		this.cantidad = cantidad;
	}

}
