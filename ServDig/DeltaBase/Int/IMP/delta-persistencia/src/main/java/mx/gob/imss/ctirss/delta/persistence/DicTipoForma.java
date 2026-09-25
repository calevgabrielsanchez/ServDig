package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_FORMA database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_FORMA")
public class DicTipoForma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_FORMA", nullable=false, precision=22)
	private long cveIdTipoForma;

	@Column(name="DES_TIPO_FORMA", length=255)
	private String desTipoForma;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitFormaMigratoria
//	@OneToMany(mappedBy="dicTipoForma")
//	private List<DitFormaMigratoria> ditFormaMigratorias;

    public DicTipoForma() {
    }

	public long getCveIdTipoForma() {
		return this.cveIdTipoForma;
	}

	public void setCveIdTipoForma(long cveIdTipoForma) {
		this.cveIdTipoForma = cveIdTipoForma;
	}

	public String getDesTipoForma() {
		return this.desTipoForma;
	}

	public void setDesTipoForma(String desTipoForma) {
		this.desTipoForma = desTipoForma;
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

//	public List<DitFormaMigratoria> getDitFormaMigratorias() {
//		return this.ditFormaMigratorias;
//	}
//
//	public void setDitFormaMigratorias(List<DitFormaMigratoria> ditFormaMigratorias) {
//		this.ditFormaMigratorias = ditFormaMigratorias;
//	}
	
}