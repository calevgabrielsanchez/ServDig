package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_UMF database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_UMF")
public class DicTipoUmf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_UMF", nullable=false, precision=22)
	private long cveIdTipoUmf;

	@Column(name="DES_TIPO_UMF", nullable=false, length=255)
	private String desTipoUmf;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicUmf
	@OneToMany(mappedBy="dicTipoUmf")
	private List<DicUmf> dicUmfs;

    public DicTipoUmf() {
    }

	public long getCveIdTipoUmf() {
		return this.cveIdTipoUmf;
	}

	public void setCveIdTipoUmf(long cveIdTipoUmf) {
		this.cveIdTipoUmf = cveIdTipoUmf;
	}

	public String getDesTipoUmf() {
		return this.desTipoUmf;
	}

	public void setDesTipoUmf(String desTipoUmf) {
		this.desTipoUmf = desTipoUmf;
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

	public List<DicUmf> getDicUmfs() {
		return this.dicUmfs;
	}

	public void setDicUmfs(List<DicUmf> dicUmfs) {
		this.dicUmfs = dicUmfs;
	}
	
}