package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIT_ANALISIS_CE database table.
 * 
 */
@Entity
@Table(name = "DIT_ANALISIS_CE")
public class DitAnalisisCe implements Serializable {
	private static final long serialVersionUID = 1L;

	// @GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@SequenceGenerator(name = "DIT_ANALISIS_GENERATOR", sequenceName = "SEQ_DITANALISISCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_ANALISIS_GENERATOR")
	@Column(name = "CVE_ID_ANALISIS")
	private long cveIdAnalisis;

	@Column(name = "CVE_ID_SOLICITUD")
	private BigDecimal cveIdSolicitud;

	/*
	 * @Column(name="CVE_ID_USUARIO") private BigDecimal cveIdUsuario;
	 */

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ANALISIS")
	private Date fecAnalisis;

	@Column(name = "IND_MOD_AUT")
	private BigDecimal indModAut;

	// bi-directional many-to-one association to DicEstatusAnalisisCe
//	@ManyToOne
//	@JoinColumn(name = "CVE_ID_USUARIO")
//	private DitUsuario ditUsuario;

	@Column(name="CVE_USUARIO_SSO")
	private String cveIdUsuarioSso;

	
	// bi-directional many-to-one association to DicEstatusAnalisisCe
	@ManyToOne
	@JoinColumn(name = "CVE_ID_ESTATUS_ANALISIS")
	private DicEstatusAnalisisCe dicEstatusAnalisisCe;

	// bi-directional many-to-one association to DicTipoCausaAnalisi
	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_CAUSA")
	private DicTipoCausaAnalisis dicTipoCausaAnalisi;

	// bi-directional many-to-one association to DitClasificacionPropuesta
	@OneToMany(mappedBy = "ditAnalisisCe")
	private List<DitClasificacionPropuesta> ditClasificacionPropuestas;



	// bi-directional many-to-one association to DitDatosClem
	@OneToMany(mappedBy = "ditAnalisisCe")
	private List<DitDatosClem> ditDatosClems;

	// bi-directional many-to-one association to DitHistEstatusAnalisi
	@OneToMany(mappedBy = "ditAnalisisCe")
	private List<DitHistEstatusAnalisis> ditHistEstatusAnalisis;

	// bi-directional many-to-one association to DicGrupoAnalisisCe
	@ManyToOne
	@JoinColumn(name = "CVE_ID_GRUPO_ANALISIS_CE")
	private DicGrupoAnalisisCe dicGrupoAnalisisCe;

	@Column(name = "IND_ACTIVO")
	private Boolean indActivo;
	
	@OneToOne
	@JoinColumn(name = "CVE_ID_ANALISIS")
	private DitResultadoAnexoV ditResultadoAnexoV;
	
	@Column(name="IND_REGISTRA_CAUSA")
	private Boolean indRegistraCausa;
	
	//bi-directional many-to-one association to DitHistTipoCausa
	@OneToMany(mappedBy="ditAnalisisCe")
	private Set<DitHistTipoCausa> ditHistTipoCausas;

	public DitAnalisisCe() {
	}

	public long getCveIdAnalisis() {
		return this.cveIdAnalisis;
	}

	public void setCveIdAnalisis(long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public BigDecimal getCveIdSolicitud() {
		return this.cveIdSolicitud;
	}

	public void setCveIdSolicitud(BigDecimal cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	/*
	 * public BigDecimal getCveIdUsuario() { return this.cveIdUsuario; }
	 * 
	 * public void setCveIdUsuario(BigDecimal cveIdUsuario) { this.cveIdUsuario
	 * = cveIdUsuario; }
	 */

	public Date getFecAnalisis() {
		return this.fecAnalisis;
	}

	public void setFecAnalisis(Date fecAnalisis) {
		this.fecAnalisis = fecAnalisis;
	}

	public BigDecimal getIndModAut() {
		return this.indModAut;
	}

	public void setIndModAut(BigDecimal indModAut) {
		this.indModAut = indModAut;
	}

	public DicEstatusAnalisisCe getDicEstatusAnalisisCe() {
		return this.dicEstatusAnalisisCe;
	}

	public void setDicEstatusAnalisisCe(
			DicEstatusAnalisisCe dicEstatusAnalisisCe) {
		this.dicEstatusAnalisisCe = dicEstatusAnalisisCe;
	}

	public DicTipoCausaAnalisis getDicTipoCausaAnalisi() {
		return this.dicTipoCausaAnalisi;
	}

	public void setDicTipoCausaAnalisi(DicTipoCausaAnalisis dicTipoCausaAnalisi) {
		this.dicTipoCausaAnalisi = dicTipoCausaAnalisi;
	}

	public List<DitClasificacionPropuesta> getDitClasificacionPropuestas() {
		return this.ditClasificacionPropuestas;
	}

	public void setDitClasificacionPropuestas(
			List<DitClasificacionPropuesta> ditClasificacionPropuestas) {
		this.ditClasificacionPropuestas = ditClasificacionPropuestas;
	}


	public List<DitDatosClem> getDitDatosClems() {
		return this.ditDatosClems;
	}

	public void setDitDatosClems(List<DitDatosClem> ditDatosClems) {
		this.ditDatosClems = ditDatosClems;
	}

	public List<DitHistEstatusAnalisis> getDitHistEstatusAnalisis() {
		return this.ditHistEstatusAnalisis;
	}

	public void setDitHistEstatusAnalisis(
			List<DitHistEstatusAnalisis> ditHistEstatusAnalisis) {
		this.ditHistEstatusAnalisis = ditHistEstatusAnalisis;
	}

//	public DitUsuario getDitUsuario() {
//		return ditUsuario;
//	}
//
//	public void setDitUsuario(DitUsuario ditUsuario) {
//		this.ditUsuario = ditUsuario;
//	}
	
	public String getCveIdUsuarioSso() {
		return cveIdUsuarioSso;
	}

	public void setCveIdUsuarioSso(String cveIdUsuarioSso) {
		this.cveIdUsuarioSso = cveIdUsuarioSso;
	}

	public DicGrupoAnalisisCe getDicGrupoAnalisisCe() {
		return dicGrupoAnalisisCe;
	}

	public void setDicGrupoAnalisisCe(DicGrupoAnalisisCe dicGrupoAnalisisCe) {
		this.dicGrupoAnalisisCe = dicGrupoAnalisisCe;
	}

	public Boolean getIndActivo() {
		return indActivo;
	}

	public void setIndActivo(Boolean indActivo) {
		this.indActivo = indActivo;
	}

	public DitResultadoAnexoV getDitResultadoAnexoV() {
		return ditResultadoAnexoV;
	}

	public void setDitResultadoAnexoV(DitResultadoAnexoV ditResultadoAnexoV) {
		this.ditResultadoAnexoV = ditResultadoAnexoV;
	}

	public Boolean getIndRegistraCausa() {
		return indRegistraCausa;
	}

	public void setIndRegistraCausa(Boolean indRegistraCausa) {
		this.indRegistraCausa = indRegistraCausa;
	}
	
	public Set<DitHistTipoCausa> getDitHistTipoCausas() {
		return this.ditHistTipoCausas;
	}

	public void setDitHistTipoCausas(Set<DitHistTipoCausa> ditHistTipoCausas) {
		this.ditHistTipoCausas = ditHistTipoCausas;
	}
}