package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CRT_DOMICILIO_CENTROTRABAJO database table.
 * 
 */
@Entity
@Table(name = "CRT_DOMICILIO_CENTROTRABAJO")
public class AbstractCrtDomicilioCentrotrabajo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_DOMCTROTRAB")
	private String cveDomctrotrab;

	public AbstractCrtDomicilioCentrotrabajo() {
	}

	public String getCveDomctrotrab() {
		return this.cveDomctrotrab;
	}

	public void setCveDomctrotrab(String cveDomctrotrab) {
		this.cveDomctrotrab = cveDomctrotrab;
	}

}