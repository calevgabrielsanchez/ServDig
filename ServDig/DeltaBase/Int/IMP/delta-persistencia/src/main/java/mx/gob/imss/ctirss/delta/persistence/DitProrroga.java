package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_PRORROGA database table.
 * 
 */
@Entity
@Table(name="DIT_PRORROGA")
public class DitProrroga implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_DITPRORROGA", sequenceName = "SEQ_DITPRORROGA")
    @GeneratedValue(generator = "SEQ_DITPRORROGA")
	@Column(name="CVE_ID_PRORROGA")
	private Long cveIdProrroga;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FIN_PRORROGA", nullable=true)
	private Date fecFinProrroga;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_INICIO_PRORROGA", nullable=false)
	private Date fecInicioProrroga;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)    
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicCaracter
    @ManyToOne
	@JoinColumn(name="CVE_ID_CARACTER")
	private DicCaracter dicCaracter;
    
  //bi-directional many-to-one association to DicCaracter
    @ManyToOne
	@JoinColumn(name="CVE_ID_ESTADO_PRORROGA")
	private DicEstadoProrroga dicEstadoProrroga;
    
    @ManyToOne
    @JoinColumn(name="CVE_ID_TIPO_PRORROGA")
    private DicTipoProrroga dicTipoProrroga;

  //bi-directional one-to-one association to DitDocumentoProbatorio
    @OneToOne
  	@JoinColumn(name="CVE_ID_TRAMITE")
  	private DitTramite ditTramite;
  	
  	@OneToOne
  	@JoinColumns({
		@JoinColumn(name="CVE_ID_PERSONA_INTEGRANTE", referencedColumnName="CVE_ID_PERSONA_INTEGRANTE"),
		@JoinColumn(name="CVE_ID_ASIGNACION_NSS", referencedColumnName="CVE_ID_ASIGNACION_NSS")		
		})
  	private DitGrupoFamiliar ditGrupoFamiliar;
  	
  	
  	
	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}
	
	
	public Long getCveIdProrroga() {
		return cveIdProrroga;
	}

	public void setCveIdProrroga(Long cveIdProrroga) {
		this.cveIdProrroga = cveIdProrroga;
	}


	public Date getFecFinProrroga() {
		return this.fecFinProrroga;
	}

	public void setFecFinProrroga(Date fecFinProrroga) {
		this.fecFinProrroga = fecFinProrroga;
	}

	public Date getFecInicioProrroga() {
		return this.fecInicioProrroga;
	}

	public void setFecInicioProrroga(Date fecInicioProrroga) {
		this.fecInicioProrroga = fecInicioProrroga;
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

	public DicCaracter getDicCaracter() {
		return this.dicCaracter;
	}

	public void setDicCaracter(DicCaracter dicCaracter) {
		this.dicCaracter = dicCaracter;
	}

	public DicEstadoProrroga getDicEstadoProrroga() {
		return dicEstadoProrroga;
	}

	public void setDicEstadoProrroga(DicEstadoProrroga dicEstadoProrroga) {
		this.dicEstadoProrroga = dicEstadoProrroga;
	}

	/**
	 * @return the ditGrupoFamiliar
	 */
	public DitGrupoFamiliar getDitGrupoFamiliar() {
		return ditGrupoFamiliar;
	}

	/**
	 * @param ditGrupoFamiliar the ditGrupoFamiliar to set
	 */
	public void setDitGrupoFamiliar(DitGrupoFamiliar ditGrupoFamiliar) {
		this.ditGrupoFamiliar = ditGrupoFamiliar;
	}

	public DicTipoProrroga getDicTipoProrroga() {
		return dicTipoProrroga;
	}

	public void setDicTipoProrroga(DicTipoProrroga dicTipoProrroga) {
		this.dicTipoProrroga = dicTipoProrroga;
	}
}