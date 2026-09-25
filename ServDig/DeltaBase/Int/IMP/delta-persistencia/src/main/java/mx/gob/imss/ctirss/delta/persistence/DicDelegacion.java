package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_DELEGACION database table.
 * 
 */
@Entity
@Table(name="DIC_DELEGACION")
@OnSearchLlavePrimaria(atributos="cveIdDelegacion")
@ComponentComboCampoDescripcion(atributo="desDeleg")
public class DicDelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DELEGACION", nullable=false, precision=22)
	private long cveIdDelegacion;

	@Column(name="ANIO_INI_OPER", length=50)
	private String anioIniOper;

	@Column(name="CLAVE_DELEGACION", length=100)
	private String claveDelegacion;

	@Column(name="DES_DELEG", length=255)
	private String desDeleg;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="TIP_DELEGACION", precision=22)
	private BigDecimal tipDelegacion;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

	//bi-directional many-to-one association to DicSubdelegacion
	@OneToMany(mappedBy="dicDelegacion")
	private List<DicSubdelegacion> dicSubdelegacions;

	//bi-directional many-to-one association to DitUsuarioFuncionario
	@OneToMany(mappedBy="dicDelegacion")
	private List<DitUsuarioFuncionario> ditUsuarioFuncionarios;
	
	@Column(name="CVE_CIZ")
	private Integer cveCiz;

	
    public DicDelegacion() {
    }

	public long getCveIdDelegacion() {
		return this.cveIdDelegacion;
	}

	public void setCveIdDelegacion(long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public String getAnioIniOper() {
		return this.anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}

	public String getClaveDelegacion() {
		return this.claveDelegacion;
	}

	public void setClaveDelegacion(String claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	public String getDesDeleg() {
		return this.desDeleg;
	}

	public void setDesDeleg(String desDeleg) {
		this.desDeleg = desDeleg;
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

	public BigDecimal getTipDelegacion() {
		return this.tipDelegacion;
	}

	public void setTipDelegacion(BigDecimal tipDelegacion) {
		this.tipDelegacion = tipDelegacion;
	}

	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
	public List<DicSubdelegacion> getDicSubdelegacions() {
		return this.dicSubdelegacions;
	}

	public void setDicSubdelegacions(List<DicSubdelegacion> dicSubdelegacions) {
		this.dicSubdelegacions = dicSubdelegacions;
	}
	
	public List<DitUsuarioFuncionario> getDitUsuarioFuncionarios() {
		return this.ditUsuarioFuncionarios;
	}

	public void setDitUsuarioFuncionarios(List<DitUsuarioFuncionario> ditUsuarioFuncionarios) {
		this.ditUsuarioFuncionarios = ditUsuarioFuncionarios;
	}

	public Integer getCveCiz() {
		return cveCiz;
	}

	public void setCveCiz(Integer cveCiz) {
		this.cveCiz = cveCiz;
	}
	
}