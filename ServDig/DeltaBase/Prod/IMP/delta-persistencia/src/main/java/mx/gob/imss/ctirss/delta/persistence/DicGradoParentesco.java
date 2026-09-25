package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_GRADO_PARENTESCO database table.
 * 
 */
@Entity
@Table(name="DIC_GRADO_PARENTESCO")
public class DicGradoParentesco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_GRADO_PARENTESCO", nullable=false, precision=22)
	private long cveIdGradoParentesco;

	@Column(name="DES_GRADO_PARENTESCO", length=20)
	private String desGradoParentesco;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicParentesco
	@OneToMany(mappedBy="dicGradoParentesco")
	private List<DicParentesco> dicParentescos;

    public DicGradoParentesco() {
    }

	public long getCveIdGradoParentesco() {
		return this.cveIdGradoParentesco;
	}

	public void setCveIdGradoParentesco(long cveIdGradoParentesco) {
		this.cveIdGradoParentesco = cveIdGradoParentesco;
	}

	public String getDesGradoParentesco() {
		return this.desGradoParentesco;
	}

	public void setDesGradoParentesco(String desGradoParentesco) {
		this.desGradoParentesco = desGradoParentesco;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public List<DicParentesco> getDicParentescos() {
		return this.dicParentescos;
	}

	public void setDicParentescos(List<DicParentesco> dicParentescos) {
		this.dicParentescos = dicParentescos;
	}
	
}