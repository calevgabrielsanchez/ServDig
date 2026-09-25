package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIT_DATOS_CLEM_ARTICULO database table.
 * 
 */
@Entity
@Table(name="DIT_DATOS_CLEM_ARTICULO")
public class DitDatosClemArticulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitDatosClemArticuloPK id;

    public DitDatosClemArticulo() {
    }

	public DitDatosClemArticuloPK getId() {
		return this.id;
	}

	public void setId(DitDatosClemArticuloPK id) {
		this.id = id;
	}
	
}