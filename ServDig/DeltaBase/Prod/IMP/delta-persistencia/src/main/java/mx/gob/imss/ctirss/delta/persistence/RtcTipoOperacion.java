package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RTC_TIPO_OPERACION")
public class RtcTipoOperacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7973729835158942225L;

	@Id
	@Column(name = "CVE_ID_TIPO_OPERACION")
	private Integer cveIdTipoOperacion;

	@Column(name = "DES_TIPO_OPERACION")
	private String desTipoOperacion;

	public Integer getCveIdTipoOperacion() {
		return cveIdTipoOperacion;
	}

	public void setCveIdTipoOperacion(Integer cveIdTipoOperacion) {
		this.cveIdTipoOperacion = cveIdTipoOperacion;
	}

	public String getDesTipoOperacion() {
		return desTipoOperacion;
	}

	public void setDesTipoOperacion(String desTipoOperacion) {
		this.desTipoOperacion = desTipoOperacion;
	}

}
