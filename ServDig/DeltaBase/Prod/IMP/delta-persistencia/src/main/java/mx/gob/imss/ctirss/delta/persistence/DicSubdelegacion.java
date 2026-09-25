package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_SUBDELEGACION database table.
 * 
 */
@Entity
@Table(name="DIC_SUBDELEGACION")
@OnSearchLlavePrimaria(atributos="cveIdSubdelegacion")
@ComponentComboCampoDescripcion(atributo="desSubdelegacion")
public class DicSubdelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_SUBDELEGACION", nullable=false, precision=22)
	private long cveIdSubdelegacion;

	@Column(name="ANIO_INI_OPER", length=50)
	private String anioIniOper;

	@Column(name="CLAVE_SUBDELEGACION", length=100)
	private String claveSubdelegacion;

	@Column(name="DES_SUBDELEGACION", length=255)
	private String desSubdelegacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    @Column(name="REF_DOMICILIO", length=200)
	private String refDomicilio;

    
	//bi-directional many-to-one association to DicGuarderia
	@OneToMany(mappedBy="dicSubdelegacion")
	private List<DicGuarderia> dicGuarderias;

	//bi-directional many-to-one association to DicDelegacion
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_DELEGACION")
	private DicDelegacion dicDelegacion;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

	//bi-directional many-to-one association to DicUmf
	@OneToMany(mappedBy="dicSubdelegacion")
	private List<DicUmf> dicUmfs;

	//bi-directional many-to-one association to DitAutorizacionPermte
	@OneToMany(mappedBy="dicSubdelegacion")
	private List<DitAutorizacionPermte> ditAutorizacionPermtes;

	//bi-directional many-to-one association to DitMovimientoSubdeleg
	@OneToMany(mappedBy="dicSubdelegacion")
	private List<DitMovimientoSubdeleg> ditMovimientoSubdelegs;

	//bi-directional many-to-one association to DitMunicipioSubdelegacion
	@OneToMany(mappedBy="dicSubdelegacion")
	private List<DitMunicipioSubdelegacion> ditMunicipioSubdelegacions;

	//bi-directional many-to-one association to DitUsuarioFuncionario
	@OneToMany(mappedBy="dicSubdelegacion")
	private List<DitUsuarioFuncionario> ditUsuarioFuncionarios;
	
	@OneToMany(mappedBy="dicSubdelegacion", fetch=FetchType.LAZY)
	private List<DitSolicitud> ditSolicituds;

    public DicSubdelegacion() {
    }

	public long getCveIdSubdelegacion() {
		return this.cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public String getAnioIniOper() {
		return this.anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}

	public String getClaveSubdelegacion() {
		return this.claveSubdelegacion;
	}

	public void setClaveSubdelegacion(String claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
	}

	public String getDesSubdelegacion() {
		return this.desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
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

	public List<DicGuarderia> getDicGuarderias() {
		return this.dicGuarderias;
	}

	public void setDicGuarderias(List<DicGuarderia> dicGuarderias) {
		this.dicGuarderias = dicGuarderias;
	}
	
	public DicDelegacion getDicDelegacion() {
		return this.dicDelegacion;
	}

	public void setDicDelegacion(DicDelegacion dicDelegacion) {
		this.dicDelegacion = dicDelegacion;
	}
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
	public List<DicUmf> getDicUmfs() {
		return this.dicUmfs;
	}

	public void setDicUmfs(List<DicUmf> dicUmfs) {
		this.dicUmfs = dicUmfs;
	}
	
	public List<DitAutorizacionPermte> getDitAutorizacionPermtes() {
		return this.ditAutorizacionPermtes;
	}

	public void setDitAutorizacionPermtes(List<DitAutorizacionPermte> ditAutorizacionPermtes) {
		this.ditAutorizacionPermtes = ditAutorizacionPermtes;
	}
	
	public List<DitMovimientoSubdeleg> getDitMovimientoSubdelegs() {
		return this.ditMovimientoSubdelegs;
	}

	public void setDitMovimientoSubdelegs(List<DitMovimientoSubdeleg> ditMovimientoSubdelegs) {
		this.ditMovimientoSubdelegs = ditMovimientoSubdelegs;
	}
	
	public List<DitMunicipioSubdelegacion> getDitMunicipioSubdelegacions() {
		return this.ditMunicipioSubdelegacions;
	}

	public void setDitMunicipioSubdelegacions(List<DitMunicipioSubdelegacion> ditMunicipioSubdelegacions) {
		this.ditMunicipioSubdelegacions = ditMunicipioSubdelegacions;
	}
	
	public List<DitUsuarioFuncionario> getDitUsuarioFuncionarios() {
		return this.ditUsuarioFuncionarios;
	}

	public void setDitUsuarioFuncionarios(List<DitUsuarioFuncionario> ditUsuarioFuncionarios) {
		this.ditUsuarioFuncionarios = ditUsuarioFuncionarios;
	}

	public List<DitSolicitud> getDitSolicituds() {
		return ditSolicituds;
	}

	public void setDitSolicituds(List<DitSolicitud> ditSolicituds) {
		this.ditSolicituds = ditSolicituds;
	}

	public String getRefDomicilio() {
		return refDomicilio;
	}

	public void setRefDomicilio(String refDomicilio) {
		this.refDomicilio = refDomicilio;
	}
	
	
}