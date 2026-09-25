package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIC_UMF_CODIGO_POSTAL database table.
 * 
 */
@Entity
@Table(name="DIC_UMF_CODIGO_POSTAL")
@NamedQuery(name="DicUmfCodigoPostal.findAll", query="SELECT d FROM DicUmfCodigoPostal d")
public class DicUmfCodigoPostal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_UMF_CODIGO_POSTAL", nullable=false, precision=22)
	private long cveIdUmfCodigoPostal;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_CODIGO_POSTAL")
	private String numCodigoPostal;

	//bi-directional many-to-one association to DicUmf
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_UMF")
	private DicUmf dicUmf;

	public DicUmfCodigoPostal() {
	}

	public long getCveIdUmfCodigoPostal() {
		return this.cveIdUmfCodigoPostal;
	}

	public void setCveIdUmfCodigoPostal(long cveIdUmfCodigoPostal) {
		this.cveIdUmfCodigoPostal = cveIdUmfCodigoPostal;
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

	public String getNumCodigoPostal() {
		return this.numCodigoPostal;
	}

	public void setNumCodigoPostal(String numCodigoPostal) {
		this.numCodigoPostal = numCodigoPostal;
	}

	public DicUmf getDicUmf() {
		return this.dicUmf;
	}

	public void setDicUmf(DicUmf dicUmf) {
		this.dicUmf = dicUmf;
	}

}