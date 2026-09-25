package mx.gob.imss.ctirss.reing.patrones.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the APC_TIPO_SOCIEDAD database table.
 * 
 */
@Entity
@Table(name="APC_TIPO_SOCIEDAD")
public class ApcTipoSociedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_TIPO_SOCIEDAD")
	private long cveTipoSociedad;

	@Column(name="DES_COMPLETA_TIPO_SOCIEDAD")
	private String desCompletaTipoSociedad;

	@Column(name="DES_TIPO_SOCIEDAD")
	private String desTipoSociedad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ACTUALIZACION")
	private Date fecActualizacion;

	@Column(name="IND_ESTATUS_ACTIVO")
	private BigDecimal indEstatusActivo;

	//bi-directional many-to-one association to AptRegistroPatronal
	@OneToMany(mappedBy="apcTipoSociedad")
	private Set<AptRegistroPatronal> aptRegistroPatronals;

    public ApcTipoSociedad() {
    }

	public long getCveTipoSociedad() {
		return this.cveTipoSociedad;
	}

	public void setCveTipoSociedad(long cveTipoSociedad) {
		this.cveTipoSociedad = cveTipoSociedad;
	}

	public String getDesCompletaTipoSociedad() {
		return this.desCompletaTipoSociedad;
	}

	public void setDesCompletaTipoSociedad(String desCompletaTipoSociedad) {
		this.desCompletaTipoSociedad = desCompletaTipoSociedad;
	}

	public String getDesTipoSociedad() {
		return this.desTipoSociedad;
	}

	public void setDesTipoSociedad(String desTipoSociedad) {
		this.desTipoSociedad = desTipoSociedad;
	}

	public Date getFecActualizacion() {
		return this.fecActualizacion;
	}

	public void setFecActualizacion(Date fecActualizacion) {
		this.fecActualizacion = fecActualizacion;
	}

	public BigDecimal getIndEstatusActivo() {
		return this.indEstatusActivo;
	}

	public void setIndEstatusActivo(BigDecimal indEstatusActivo) {
		this.indEstatusActivo = indEstatusActivo;
	}

	public Set<AptRegistroPatronal> getAptRegistroPatronals() {
		return this.aptRegistroPatronals;
	}

	public void setAptRegistroPatronals(Set<AptRegistroPatronal> aptRegistroPatronals) {
		this.aptRegistroPatronals = aptRegistroPatronals;
	}
	
}