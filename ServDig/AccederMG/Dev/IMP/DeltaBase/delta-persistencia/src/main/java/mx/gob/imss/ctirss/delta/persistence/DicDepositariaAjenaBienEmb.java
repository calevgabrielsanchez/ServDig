package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_DEPOSITARIA_AJENA_BIEN_EMB database table.
 * 
 */
@Entity
@Table(name="DIC_DEPOSITARIA_AJENA_BIEN_EMB")
public class DicDepositariaAjenaBienEmb implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DicDepositariaAjenaBienEmbPK id;

	@Column(name="DES_DIRECCION", length=50)
	private String desDireccion;

	@Column(name="DES_NOMBRE", length=50)
	private String desNombre;

    public DicDepositariaAjenaBienEmb() {
    }

	public DicDepositariaAjenaBienEmbPK getId() {
		return this.id;
	}

	public void setId(DicDepositariaAjenaBienEmbPK id) {
		this.id = id;
	}
	
	public String getDesDireccion() {
		return this.desDireccion;
	}

	public void setDesDireccion(String desDireccion) {
		this.desDireccion = desDireccion;
	}

	public String getDesNombre() {
		return this.desNombre;
	}

	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}

}