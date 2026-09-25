package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_CONTRATACION database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_CONTRATACION")
public class DicTipoContratacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_CONTRATACION", nullable=false, precision=22)
	private long cveIdTipoContratacion;

	@Column(name="DES_TIPO_CONTRATACION", length=20)
	private String desTipoContratacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitContratoAseguramiento
	@OneToMany(mappedBy="dicTipoContratacion")
	private List<DitContratoAseguramiento> ditContratoAseguramientos;

    public DicTipoContratacion() {
    }

	public long getCveIdTipoContratacion() {
		return this.cveIdTipoContratacion;
	}

	public void setCveIdTipoContratacion(long cveIdTipoContratacion) {
		this.cveIdTipoContratacion = cveIdTipoContratacion;
	}

	public String getDesTipoContratacion() {
		return this.desTipoContratacion;
	}

	public void setDesTipoContratacion(String desTipoContratacion) {
		this.desTipoContratacion = desTipoContratacion;
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

	public List<DitContratoAseguramiento> getDitContratoAseguramientos() {
		return this.ditContratoAseguramientos;
	}

	public void setDitContratoAseguramientos(List<DitContratoAseguramiento> ditContratoAseguramientos) {
		this.ditContratoAseguramientos = ditContratoAseguramientos;
	}
	
}