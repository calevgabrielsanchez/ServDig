package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;


/**
 * The persistent class for the DIT_SEGURO_IVRO database table.
 * 
 */
@Entity
@Table(name="DIT_SEGURO_IVRO")
@NamedQuery(name="DitSeguroIvro.findAll", query="SELECT d FROM DitSeguroIvro d")
public class DitSeguroIvro implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * IDentificador del seguro
	 */
	@Id
    @SequenceGenerator(name = "DIT_SEGURO_IVRO_CVEIDSEGURO_GENERATOR", sequenceName = "SEQ_DITSEGUROIVRO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SEGURO_IVRO_CVEIDSEGURO_GENERATOR")
    @Column(name="CVE_ID_SEGURO_IVRO", nullable=false, precision=22)
	private long cveIdSeguroIvro;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FIN")
	private Date fecFin;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicEstadoSeguro
	@ManyToOne
	@JoinColumn(name="CVE_ID_ESTADO_SEGURO")
	private DicEstadoSeguro dicEstadoSeguro;

	//bi-directional many-to-one association to DicModalidad
	@ManyToOne
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;

	@ManyToOne
	@JoinColumn(name="CVE_ID_COMPRA")
	private DitCompra ditCompra;
	
	@OneToMany
	@JoinTable(name = "DIT_TRAMITE_SEGURO_IVRO", 
    joinColumns = {@JoinColumn(name = "CVE_ID_SEGURO_IVRO", insertable = false, updatable = false)},
    inverseJoinColumns = @JoinColumn(name = "CVE_ID_TRAMITE" , insertable = false, updatable = false))
	private Set<DitTramite> ditTramites;
	
	public DitSeguroIvro() {
	}

	public long getCveIdSeguroIvro() {
		return this.cveIdSeguroIvro;
	}

	public void setCveIdSeguroIvro(long cveIdSeguroIvro) {
		this.cveIdSeguroIvro = cveIdSeguroIvro;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
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

	public DicEstadoSeguro getDicEstadoSeguro() {
		return this.dicEstadoSeguro;
	}

	public void setDicEstadoSeguro(DicEstadoSeguro dicEstadoSeguro) {
		this.dicEstadoSeguro = dicEstadoSeguro;
	}

	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}

	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

    /**
     * @return the ditCompra
     */
    public DitCompra getDitCompra() {
        return ditCompra;
    }

    /**
     * @param ditCompra the ditCompra to set
     */
    public void setDitCompra(DitCompra ditCompra) {
        this.ditCompra = ditCompra;
    }

    /**
     * @return the ditTramites
     */
    public Set<DitTramite> getDitTramites() {
        return ditTramites;
    }

    /**
     * @param ditTramites the ditTramites to set
     */
    public void setDitTramites(Set<DitTramite> ditTramites) {
        this.ditTramites = ditTramites;
    }

	
}