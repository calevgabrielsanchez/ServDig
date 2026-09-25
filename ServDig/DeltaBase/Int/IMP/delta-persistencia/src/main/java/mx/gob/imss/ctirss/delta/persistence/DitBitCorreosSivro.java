package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_SEGURO_IVRO database table.
 * 
 */
@Entity
@Table(name="DIT_BIT_CORREOS_SIVRO")
@NamedQuery(name="DitBitCorreosSivro.findAll", query="SELECT d FROM DitBitCorreosSivro d")
public class DitBitCorreosSivro implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * IDentificador del seguro
	 */
	@Id
    @Column(name="CVE_ID_BIT_CORREOS_SIVRO", nullable=false, precision=22)
	private long cveIdBitCorreosSivro;

	@Column(name="CVE_ID_SEGURO_IVRO", precision=22)
	private long cveIdSeguroIvro;
	
	@Column(name="TIP_OPERACION", precision=3)
	private Integer tipOperacion;

	@Column(name="IND_ESTATUS", precision=1)
	private Integer indEstatus;
	
	@Column(name="DES_ERROR", length=300)
	private String desError;

	public long getCveIdBitCorreosSivro() {
		return cveIdBitCorreosSivro;
	}

	public void setCveIdBitCorreosSivro(long cveIdBitCorreosSivro) {
		this.cveIdBitCorreosSivro = cveIdBitCorreosSivro;
	}

	public long getCveIdSeguroIvro() {
		return cveIdSeguroIvro;
	}

	public void setCveIdSeguroIvro(long cveIdSeguroIvro) {
		this.cveIdSeguroIvro = cveIdSeguroIvro;
	}

	public Integer getTipOperacion() {
		return tipOperacion;
	}

	public void setTipOperacion(Integer tipOperacion) {
		this.tipOperacion = tipOperacion;
	}

	public Integer getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Integer indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getDesError() {
		return desError;
	}

	public void setDesError(String desError) {
		this.desError = desError;
	}

}