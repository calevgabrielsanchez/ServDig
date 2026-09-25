package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Entity
@Table(name="CRT_BALANZACOMP")
public class CrtBalanzaComp extends AbstractModel{
	
	
	private Integer cveBalanzaComp;	
	private Integer cveEjercicio;
	private Integer cvePercepciones;
	private Integer cveGastos;
	private Double cveImRemuneracion;
	private Double cveImAuxiliarNom;
	private String inIntegraSalario;
	private Integer cveAnexosolcorrpat;
	private Date fecFechaReg;
	private String cveUsuario;
	
	@Transient
	private String tpSeleccionBalanzaOAux;
	
	@Id
	@SequenceGenerator(name="CRS_CVE_BALANZACOMP_NAME", sequenceName="CRS_CVE_BALANZACOMP")
	@GeneratedValue(generator="CRS_CVE_BALANZACOMP_NAME")
	@Column(name="CVE_BALANZACOMP")
	public Integer getCveBalanzaComp() {
		return cveBalanzaComp;
	}
	
	public void setCveBalanzaComp(Integer cveBalanzaComp) {
		this.cveBalanzaComp = cveBalanzaComp;
	}
	
	@Column(name="CVE_EJERCICIO")
	public Integer getCveEjercicio() {
		return cveEjercicio;
	}

	public void setCveEjercicio(Integer cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}

	@Column(name="CVE_PERCEPCION")
	public Integer getCvePercepciones() {
		return cvePercepciones;
	}

	public void setCvePercepciones(Integer cvePercepciones) {
		this.cvePercepciones = cvePercepciones;
	}

	@Column(name="CVE_GASTOS")
	public Integer getCveGastos() {
		return cveGastos;
	}

	public void setCveGastos(Integer cveGastos) {
		this.cveGastos = cveGastos;
	}

	@Column(name="IM_REMUNERACION")
	public Double getCveImRemuneracion() {
		return cveImRemuneracion;
	}

	public void setCveImRemuneracion(Double cveImRemuneracion) {
		this.cveImRemuneracion = cveImRemuneracion;
	}

	@Column(name="IM_AUXILIARNOM")
	public Double getCveImAuxiliarNom() {
		return cveImAuxiliarNom;
	}

	public void setCveImAuxiliarNom(Double cveImAuxiliarNom) {
		this.cveImAuxiliarNom = cveImAuxiliarNom;
	}

	@Column(name="IN_INTEGRA_SALARIO")
	public String getInIntegraSalario() {
		return inIntegraSalario;
	}

	public void setInIntegraSalario(String inIntegraSalario) {
		this.inIntegraSalario = inIntegraSalario;
	}

	@Column(name="CVE_ANEXOSOLCORRPAT")
	public Integer getCveAnexosolcorrpat() {
		return cveAnexosolcorrpat;
	}

	public void setCveAnexosolcorrpat(Integer cveAnexosolcorrpat) {
		this.cveAnexosolcorrpat = cveAnexosolcorrpat;
	}

	@Column(name="FEC_FECHAREG")
	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	@Column(name="CVE_USUARIO")
	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	@Transient
	public String imBalanzaOAux;

	@Transient
	public String getImBalanzaOAux() {
		return imBalanzaOAux;
	}

	public void setImBalanzaOAux(String imBalanzaOAux) {
		this.imBalanzaOAux = imBalanzaOAux;
	}

	@Transient
	public String getTpSeleccionBalanzaOAux() {
		return tpSeleccionBalanzaOAux;
	}

	@Transient
	public void setTpSeleccionBalanzaOAux(String tpSeleccionBalanzaOAux) {
		this.tpSeleccionBalanzaOAux = tpSeleccionBalanzaOAux;
	}
	
	
}
