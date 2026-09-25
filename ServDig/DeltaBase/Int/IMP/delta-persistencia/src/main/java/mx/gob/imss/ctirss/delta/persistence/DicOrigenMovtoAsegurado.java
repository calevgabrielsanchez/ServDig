package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ORIGEN_MOVTO_ASEGURADO database table.
 * 
 */
@Entity
@Table(name="DIC_ORIGEN_MOVTO_ASEGURADO")
public class DicOrigenMovtoAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ORIGEN_MOVTO_ASEGURADO", nullable=false, precision=22)
	private long cveIdOrigenMovtoAsegurado;

	@Column(name="DES_ORIGEN", length=255)
	private String desOrigen;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_ORIGEN", length=50)
	private String numOrigen;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@OneToMany(mappedBy="dicOrigenMovtoAsegurado")
	private List<DitMovimientoAsegurado> ditMovimientoAsegurados;

    public DicOrigenMovtoAsegurado() {
    }

	public long getCveIdOrigenMovtoAsegurado() {
		return this.cveIdOrigenMovtoAsegurado;
	}

	public void setCveIdOrigenMovtoAsegurado(long cveIdOrigenMovtoAsegurado) {
		this.cveIdOrigenMovtoAsegurado = cveIdOrigenMovtoAsegurado;
	}

	public String getDesOrigen() {
		return this.desOrigen;
	}

	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen;
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

	public String getNumOrigen() {
		return this.numOrigen;
	}

	public void setNumOrigen(String numOrigen) {
		this.numOrigen = numOrigen;
	}

	public List<DitMovimientoAsegurado> getDitMovimientoAsegurados() {
		return this.ditMovimientoAsegurados;
	}

	public void setDitMovimientoAsegurados(List<DitMovimientoAsegurado> ditMovimientoAsegurados) {
		this.ditMovimientoAsegurados = ditMovimientoAsegurados;
	}
	
}