package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the SPT_PERS_RECIBE_PAGO_PENS database table.
 * 
 */
@Entity
@Table(name = "SPT_PERS_RECIBE_PAGO_PENS")
@NamedQuery(name = "SptPersRecibePagoPen.findAll", query = "SELECT s FROM SptPersRecibePagoPen s")
public class SptPersRecibePagoPen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTPERSRECIBEPAGOPENS", sequenceName = "SEQ_SPTPERSRECIBEPAGOPENS")
	@GeneratedValue(generator = "SEQ_SPTPERSRECIBEPAGOPENS")
	@Column(name = "CVE_ID_PERS_RECIBE_PAGO_PENS")
	private long cveIdPersRecibePagoPens;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitPersona
	@ManyToOne
	@JoinColumn(name = "CVE_ID_PERSONA")
	private DitPersona ditPersona;

	// bi-directional many-to-one association to SptGrupoFamiliarPension
	@ManyToOne
	@JoinColumn(name = "CVE_ID_GRUPO_FAMILIAR_PENSION")
	private SptGrupoFamiliarPension sptGrupoFamiliarPension;

	// bi-directional many-to-one association to SptPersRecibePagoPensDet
	@OneToMany(mappedBy = "sptPersRecibePagoPen")
	private List<SptPersRecibePagoPensDet> sptPersRecibePagoPensDets;

	public SptPersRecibePagoPen() {
	}

	public long getCveIdPersRecibePagoPens() {
		return this.cveIdPersRecibePagoPens;
	}

	public void setCveIdPersRecibePagoPens(long cveIdPersRecibePagoPens) {
		this.cveIdPersRecibePagoPens = cveIdPersRecibePagoPens;
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

	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public SptGrupoFamiliarPension getSptGrupoFamiliarPension() {
		return this.sptGrupoFamiliarPension;
	}

	public void setSptGrupoFamiliarPension(
			SptGrupoFamiliarPension sptGrupoFamiliarPension) {
		this.sptGrupoFamiliarPension = sptGrupoFamiliarPension;
	}

	public List<SptPersRecibePagoPensDet> getSptPersRecibePagoPensDets() {
		return sptPersRecibePagoPensDets;
	}

	public void setSptPersRecibePagoPensDets(
			List<SptPersRecibePagoPensDet> sptPersRecibePagoPensDets) {
		this.sptPersRecibePagoPensDets = sptPersRecibePagoPensDets;
	}

}