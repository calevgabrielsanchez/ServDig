package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPC_CAUSA_BAJA database table.
 * 
 */
@Entity
@Table(name="SPC_CAUSA_BAJA")
@NamedQuery(name="SpcCausaBaja.findAll", query="SELECT s FROM SpcCausaBaja s")
public class SpcCausaBaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_CAUSA_BAJA_IDCAUSABAJA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_CAUSA_BAJA_IDCAUSABAJA_GENERATOR")
	@Column(name="ID_CAUSA_BAJA")
	private String idCausaBaja;

	@Column(name="DES_CAUSA_BAJA")
	private String desCausaBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptBajaSolicitud
	@OneToMany(mappedBy="spcCausaBaja")
	private List<SptBajaSolicitud> sptBajaSolicituds;

	public SpcCausaBaja() {
	}

	public String getIdCausaBaja() {
		return this.idCausaBaja;
	}

	public void setIdCausaBaja(String idCausaBaja) {
		this.idCausaBaja = idCausaBaja;
	}

	public String getDesCausaBaja() {
		return this.desCausaBaja;
	}

	public void setDesCausaBaja(String desCausaBaja) {
		this.desCausaBaja = desCausaBaja;
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

	public List<SptBajaSolicitud> getSptBajaSolicituds() {
		return this.sptBajaSolicituds;
	}

	public void setSptBajaSolicituds(List<SptBajaSolicitud> sptBajaSolicituds) {
		this.sptBajaSolicituds = sptBajaSolicituds;
	}

	public SptBajaSolicitud addSptBajaSolicitud(SptBajaSolicitud sptBajaSolicitud) {
		getSptBajaSolicituds().add(sptBajaSolicitud);
		sptBajaSolicitud.setSpcCausaBaja(this);

		return sptBajaSolicitud;
	}

	public SptBajaSolicitud removeSptBajaSolicitud(SptBajaSolicitud sptBajaSolicitud) {
		getSptBajaSolicituds().remove(sptBajaSolicitud);
		sptBajaSolicitud.setSpcCausaBaja(null);

		return sptBajaSolicitud;
	}

}