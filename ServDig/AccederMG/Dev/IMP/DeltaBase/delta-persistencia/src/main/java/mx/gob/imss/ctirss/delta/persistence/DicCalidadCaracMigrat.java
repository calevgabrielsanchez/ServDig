package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CALIDAD_CARAC_MIGRAT database table.
 * 
 */
@Entity
@Table(name="DIC_CALIDAD_CARAC_MIGRAT")
public class DicCalidadCaracMigrat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CALIDAD_CARAC_MIGRAT", nullable=false, precision=22)
	private long cveIdCalidadCaracMigrat;

	@Column(name="DES_CALIDAD_MIGRATORIA", length=255)
	private String desCalidadMigratoria;

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
	@OneToMany(mappedBy="dicCalidadCaracMigrat")
	private List<DitFormaMigratoria> ditFormaMigratorias;

    public DicCalidadCaracMigrat() {
    }

	public long getCveIdCalidadCaracMigrat() {
		return this.cveIdCalidadCaracMigrat;
	}

	public void setCveIdCalidadCaracMigrat(long cveIdCalidadCaracMigrat) {
		this.cveIdCalidadCaracMigrat = cveIdCalidadCaracMigrat;
	}

	public String getDesCalidadMigratoria() {
		return this.desCalidadMigratoria;
	}

	public void setDesCalidadMigratoria(String desCalidadMigratoria) {
		this.desCalidadMigratoria = desCalidadMigratoria;
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

	public List<DitFormaMigratoria> getDitFormaMigratorias() {
		return this.ditFormaMigratorias;
	}

	public void setDitFormaMigratorias(List<DitFormaMigratoria> ditFormaMigratorias) {
		this.ditFormaMigratorias = ditFormaMigratorias;
	}
	
}