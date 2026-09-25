package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_NIVEL_EDUCATIVO database table.
 * 
 */
@Entity
@Table(name="DIC_NIVEL_EDUCATIVO")
public class DicNivelEducativo implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -9215896650164421958L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_NIVEL_EDUCATIVO", nullable=false, precision=22)
	private long cveIdNivelEducativo;

	@Column(name="DES_NIVEL_EDUCATIVO", length=255)
	private String desNivelEducativo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitInstitEducativa
	@OneToMany(mappedBy="dicNivelEducativo")
	private List<DitInstitEducativa> ditInstitEducativas;

    public DicNivelEducativo() {
    }

	public long getCveIdNivelEducativo() {
		return this.cveIdNivelEducativo;
	}

	public void setCveIdNivelEducativo(long cveIdNivelEducativo) {
		this.cveIdNivelEducativo = cveIdNivelEducativo;
	}

	public String getDesNivelEducativo() {
		return this.desNivelEducativo;
	}

	public void setDesNivelEducativo(String desNivelEducativo) {
		this.desNivelEducativo = desNivelEducativo;
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

	public List<DitInstitEducativa> getDitInstitEducativas() {
		return this.ditInstitEducativas;
	}

	public void setDitInstitEducativas(List<DitInstitEducativa> ditInstitEducativas) {
		this.ditInstitEducativas = ditInstitEducativas;
	}
	
}