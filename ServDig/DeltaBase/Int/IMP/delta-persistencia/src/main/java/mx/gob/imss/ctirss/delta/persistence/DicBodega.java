package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_BODEGAS database table.
 * 
 */
@Entity
@Table(name="DIC_BODEGAS")
public class DicBodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DicBodegaPK id;

	@Column(name="CVE_RFC_JEFE", length=13)
	private String cveRfcJefe;

	@Column(name="DES_BODEGA", length=50)
	private String desBodega;

	@Column(name="DES_DOMICILIO_OFICINA", length=50)
	private String desDomicilioOficina;

	@Column(name="DES_OFICINA_JEFE", length=50)
	private String desOficinaJefe;

    public DicBodega() {
    }

	public DicBodegaPK getId() {
		return this.id;
	}

	public void setId(DicBodegaPK id) {
		this.id = id;
	}
	
	public String getCveRfcJefe() {
		return this.cveRfcJefe;
	}

	public void setCveRfcJefe(String cveRfcJefe) {
		this.cveRfcJefe = cveRfcJefe;
	}

	public String getDesBodega() {
		return this.desBodega;
	}

	public void setDesBodega(String desBodega) {
		this.desBodega = desBodega;
	}

	public String getDesDomicilioOficina() {
		return this.desDomicilioOficina;
	}

	public void setDesDomicilioOficina(String desDomicilioOficina) {
		this.desDomicilioOficina = desDomicilioOficina;
	}

	public String getDesOficinaJefe() {
		return this.desOficinaJefe;
	}

	public void setDesOficinaJefe(String desOficinaJefe) {
		this.desOficinaJefe = desOficinaJefe;
	}

}