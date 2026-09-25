package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name = "buscaAsignacionXnssCL3", 
			query = "select a from DitAsignacionNssCL3 a where a.numNss=:numNss and a.ditPersona.cveIdPersona=:idPersona"),
	@NamedQuery(name = "buscaAsignacionXnssSinPersonaCL3", 
			query = "select a from DitAsignacionNssCL3 a where a.numNss=:numNss"),
	@NamedQuery(name = "buscaAsignacionXpersonaCL3", 
			query = "select a from DitAsignacionNssCL3 a where a.ditPersona.cveIdPersona=:idPersona")
})
@Entity
@Table(name="DIT_ASIGNACION_NSS_CL3")
public class DitAsignacionNssCL3 implements Serializable{

	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="CVE_ID_ASIGNACION_NSS", nullable=false, precision=22)
	private long cveIdAsignacionNss;
	
	
	@Column(name="CAN_SEMANA_COTIZADA", precision=22)
	private BigDecimal canSemanaCotizada;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_ACTIVO", precision=22)
	private BigDecimal indActivo;

	@Column(name="NUM_NSS", length=50)
	private String numNss;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;

	//bi-directional many-to-one association to DitPersonaView
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_PERSONA", insertable=false, updatable=false)
	private DitPersonaView ditPersonaView;	
	
	//bi-directional many-to-one association to DitGrupoFamiliar
	@OneToMany(mappedBy="ditAsignacionNss")
	private List<DitGrupoFamiliarCL3> ditGrupoFamiliars;

	public long getCveIdAsignacionNss() {
		return cveIdAsignacionNss;
	}

	public void setCveIdAsignacionNss(long cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public BigDecimal getCanSemanaCotizada() {
		return canSemanaCotizada;
	}

	public void setCanSemanaCotizada(BigDecimal canSemanaCotizada) {
		this.canSemanaCotizada = canSemanaCotizada;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getIndActivo() {
		return indActivo;
	}

	public void setIndActivo(BigDecimal indActivo) {
		this.indActivo = indActivo;
	}

	public String getNumNss() {
		return numNss;
	}

	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	public DitPersona getDitPersona() {
		return ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public DitPersonaView getDitPersonaView() {
		return ditPersonaView;
	}

	public void setDitPersonaView(DitPersonaView ditPersonaView) {
		this.ditPersonaView = ditPersonaView;
	}

	public List<DitGrupoFamiliarCL3> getDitGrupoFamiliars() {
		return ditGrupoFamiliars;
	}

	public void setDitGrupoFamiliars(List<DitGrupoFamiliarCL3> ditGrupoFamiliars) {
		this.ditGrupoFamiliars = ditGrupoFamiliars;
	}

	
	
	
	
}
