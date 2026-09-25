package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the DIC_TIPO_TRAMITE database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_TRAMITE")
@OnSearchLlavePrimaria(atributos="cveIdTipoTramite")
@ComponentComboCampoDescripcion(atributo="desTipoTramite")
public class DicTipoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_TRAMITE", nullable=false, precision=22)
	private Long cveIdTipoTramite;

	@Column(name="DES_TIPO_TRAMITE", nullable=false, length=255)
	private String desTipoTramite;
	
	@Column(name="REF_HOMOCLAVE")
	private String refHomoclave;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Lob()
	@Column(name="REF_GUIA_DETALLADA")
	private byte[] refGuiaDetallada;

    @Lob()
	@Column(name="REF_GUIA_RAPIDA")
	private byte[] refGuiaRapida;

	@Column(name="REF_SIGLA")
	private String refSigla;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="DIC_MODULO_TIPO_TRAMITE"
		, joinColumns={
			@JoinColumn(name="CVE_ID_TIPO_TRAMITE")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CVE_ID_MODULO")
			}
		)
	private List<DicModulo> dicModulos;
	
	//bi-directional many-to-one association to DitDoctoReqTramite
	@OneToMany(mappedBy="dicTipoTramite", fetch=FetchType.LAZY)
	private List<DitDoctoReqTramite> ditDoctoReqTramites;

	//bi-directional many-to-one association to DitTramite
	@OneToMany(mappedBy="dicTipoTramite", fetch=FetchType.LAZY)
	private List<DitTramite> ditTramites;
	
	@Column(name="TIP_TIPO_CONCLUSION")
	private Integer indTipoConclusion;
	
	
	/**

	@OneToMany(mappedBy="dicTipoTramite" , fetch=FetchType.LAZY)
	private List<DitDoctoReqTramOrigenSol> ditDoctoReqTramOrigenSols;
	**/
	
	public DicTipoTramite() {
    }

	public DicTipoTramite(Long cveIdTipoTramite, String desTipoTramite) {
		super();
		this.cveIdTipoTramite = cveIdTipoTramite;
		this.desTipoTramite = desTipoTramite;
	}

	public Long getCveIdTipoTramite() {
		return this.cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

	public String getDesTipoTramite() {
		return this.desTipoTramite;
	}

	public void setDesTipoTramite(String desTipoTramite) {
		this.desTipoTramite = desTipoTramite;
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

	public byte[] getRefGuiaDetallada() {
		return this.refGuiaDetallada;
	}

	public void setRefGuiaDetallada(byte[] refGuiaDetallada) {
		this.refGuiaDetallada = refGuiaDetallada != null ? refGuiaDetallada.clone() : null;
	}

	public byte[] getRefGuiaRapida() {
		return this.refGuiaRapida;
	}

	public void setRefGuiaRapida(byte[] refGuiaRapida) {
		this.refGuiaRapida = refGuiaRapida != null ? refGuiaRapida.clone() : null;
	}

	public String getRefSigla() {
		return this.refSigla;
	}

	public void setRefSigla(String refSigla) {
		this.refSigla = refSigla;
	}
	
	public List<DitDoctoReqTramite> getDitDoctoReqTramites() {
		return this.ditDoctoReqTramites;
	}
	
	public List<DicModulo> getDicModulos() {
		return dicModulos;
	}

	public void setDicModulos(List<DicModulo> dicModulos) {
		this.dicModulos = dicModulos;
	}

	public void setDitDoctoReqTramites(List<DitDoctoReqTramite> ditDoctoReqTramites) {
		this.ditDoctoReqTramites = ditDoctoReqTramites;
	}
	
	public List<DitTramite> getDitTramites() {
		return this.ditTramites;
	}

	public void setDitTramites(List<DitTramite> ditTramites) {
		this.ditTramites = ditTramites;
	}

	public Integer getIndTipoConclusion() {
		return indTipoConclusion;
	}

	public void setIndTipoConclusion(Integer indTipoConclusion) {
		this.indTipoConclusion = indTipoConclusion;
	}

	public String getRefHomoclave() {
		return refHomoclave;
	}

	public void setRefHomoclave(String refHomoclave) {
		this.refHomoclave = refHomoclave;
	}

	/**
	public List<DitDoctoReqTramOrigenSol> getDitDoctoReqTramOrigenSols() {
		return ditDoctoReqTramOrigenSols;
	}

	public void setDitDoctoReqTramOrigenSols(List<DitDoctoReqTramOrigenSol> ditDoctoReqTramOrigenSols) {
		this.ditDoctoReqTramOrigenSols = ditDoctoReqTramOrigenSols;
	}**/
	
}