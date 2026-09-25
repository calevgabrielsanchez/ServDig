package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CRT_SELECTOR database table.
 * 
 */
@Entity
@Table(name="CRT_SELECTOR")
public class CrtSelector extends AbstractModel {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CRS_CVE_SELECTOR_GENERATOR", sequenceName="CRS_CVE_SELECTOR")
	@GeneratedValue(generator="CRS_CVE_SELECTOR_GENERATOR")
	@Column(name="CVE_SELECTOR")
	private Long cveSelector;

	@Column(name="FEC_FECHAREGISTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecFecharegistro;

	@Column(name="ID_PROMOCIONADO")
	private String idPromocionado;
	
	
	@Column(name="NU_REGISTROPATRONAL")
	private String nuRegistroPatronal;
	
	//bi-directional many-to-one association to CgcCatcriterioseleccion
    @ManyToOne
	@JoinColumn(name="ID_CRITERIOSELECCION")
	private CgtCatCriterioeleccion cgcCatcriterioseleccion;

	//bi-directional many-to-one association to SacDelegacion
    @ManyToOne
    @JoinColumn(name="CVE_FK_DELEGACION", referencedColumnName="CVE_PK")
	private SacDelegacion sacDelegacion;

	//bi-directional many-to-one association to SacSubdelegacion
    @ManyToOne
    @JoinColumn(name="CVE_FK_SUBDELEGACION", referencedColumnName="CVE_PK")
	private SacSubdelegacion sacSubdelegacion;

	//bi-directional many-to-one association to SatPatron
    @ManyToOne
	@JoinColumn(name="CVE_FK_PATRON")
	private SatPatron satPatron;
    
    @Column(name="CVE_USUARIO")
    private String cveUsuario;
    
  
    @Transient
	public Long criterioSeleccion;
    
    @Transient
   	public Long patron;
    
    @Transient
	public String registroPatronal;
    
    @Transient
	public String razonSocial;
    
    @Transient
	public String descCriterioSeleccion;
	

	

    
    public CrtSelector(Long cveSelector,Long criterioSeleccion, Long patron,String registroPatronal,
			String razonSocial) {
		super();
		this.cveSelector = cveSelector;
		this.criterioSeleccion = criterioSeleccion;
		this.patron = patron;
		this.registroPatronal = registroPatronal;
		this.razonSocial = razonSocial;
	}

	public CrtSelector() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public CrtSelector(Long cveSelector, Date fecFecharegistro,
			String idPromocionado,
			CgtCatCriterioeleccion cgcCatcriterioseleccion,
			SacDelegacion sacDelegacion, SacSubdelegacion sacSubdelegacion,
			SatPatron satPatron) {
		super();
		this.cveSelector = cveSelector;
		this.fecFecharegistro = fecFecharegistro;
		this.idPromocionado = idPromocionado;
		this.cgcCatcriterioseleccion = cgcCatcriterioseleccion;
		this.sacDelegacion = sacDelegacion;
		this.sacSubdelegacion = sacSubdelegacion;
		this.satPatron = satPatron;
	}

	public CrtSelector(Long cveSelector) {
		super();
		this.cveSelector = cveSelector;
	}

	public Long getCveSelector() {
		return this.cveSelector;
	}

	public void setCveSelector(Long cveSelector) {
		this.cveSelector = cveSelector;
	}

	public Date getFecFecharegistro() {
		return this.fecFecharegistro;
	}

	public void setFecFecharegistro(Date fecFecharegistro) {
		this.fecFecharegistro = fecFecharegistro;
	}

	public String getIdPromocionado() {
		return this.idPromocionado;
	}

	public void setIdPromocionado(String idPromocionado) {
		this.idPromocionado = idPromocionado;
	}

	public CgtCatCriterioeleccion getCgcCatcriterioseleccion() {
		return this.cgcCatcriterioseleccion;
	}

	public void setCgcCatcriterioseleccion(CgtCatCriterioeleccion cgcCatcriterioseleccion) {
		this.cgcCatcriterioseleccion = cgcCatcriterioseleccion;
	}
	
	public SacDelegacion getSacDelegacion() {
		return this.sacDelegacion;
	}

	public void setSacDelegacion(SacDelegacion sacDelegacion) {
		this.sacDelegacion = sacDelegacion;
	}
	
	public SacSubdelegacion getSacSubdelegacion() {
		return this.sacSubdelegacion;
	}

	public void setSacSubdelegacion(SacSubdelegacion sacSubdelegacion) {
		this.sacSubdelegacion = sacSubdelegacion;
	}
	
	public SatPatron getSatPatron() {
		return this.satPatron;
	}

	public void setSatPatron(SatPatron satPatron) {
		this.satPatron = satPatron;
	}
	
	public Long getCriterioSeleccion() {
		return criterioSeleccion;
	}

	public void setCriterioSeleccion(Long criterioSeleccion) {
		this.criterioSeleccion = criterioSeleccion;
	}
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRazonSocial(){
		return razonSocial;
	}

	public Long getPatron() {
		return patron;
	}

	public void setPatron(Long patron) {
		this.patron = patron;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	

	public String getDescCriterioSeleccion() {
		return descCriterioSeleccion;
	}

	public void setDescCriterioSeleccion(String descCriterioSeleccion) {
		this.descCriterioSeleccion = descCriterioSeleccion;
	}

	public String imprimeObjeto(){
		return new StringBuffer().append("CrtSelector{")
				 .append("cveSelector:").append(this.getCveSelector()).append(";\n")
				 .append("criterioSeleccion:").append(this.getCgcCatcriterioseleccion().getIdCriterioseleccion()).append(";\n")
				 .append("sacDelegacion:").append(this.getSacDelegacion()).append(";\n")
				 .append("sacSubdelegacion:").append(this.getSacSubdelegacion()).append(";\n")
				 .append("fecFecharegistro:").append(this.getFecFecharegistro()).append(";\n")
				 .append("idPromocionado:").append(this.getIdPromocionado()).append(";\n")
				 .append("}")
				 .toString();
	}

	public String getNuRegistroPatronal() {
		return nuRegistroPatronal;
	}

	public void setNuRegistroPatronal(String nuRegistroPatronal) {
		this.nuRegistroPatronal = nuRegistroPatronal;
	}

	
	
	
	
}