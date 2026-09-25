package mx.gob.imss.digital.modelo.sindo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultadoVigenciaContVoluntaria", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "resultadoVigenciaContVoluntaria", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class ResultadoVigenciaContVoluntaria implements Serializable {
	private static final long serialVersionUID = -828239577426647371L;
	private String regPatUltimoMov;
	private String modUltimoMov;
	private String tipoUltimoMov;
	private String fecUltimoMov;
	private int estadoVigencia;
	private ModalidadTrabajador[] listModVigentes;
	private String tipoPension;
	private int indTrabajadorIMSS;
	private int indPension;
	private Integer semanasCotizadas;
	private String regPatUltimoObligatorio;
	private String modUltimoObligatorio;
	private String tipoMovObligatorio;
	private String fecMovObligatorio;
	private Float salarioObligatorio;
	private String regPatUltimoMod40;
	private String modUltimoMod40;
	private String tipoMovMod40;
	private String fecMovMod40;
	private Float salarioMod40;

	public String getRegPatUltimoMov() {
		return regPatUltimoMov;
	}

	public void setRegPatUltimoMov(String regPatUltimoMov) {
		this.regPatUltimoMov = regPatUltimoMov;
	}

	public String getModUltimoMov() {
		return modUltimoMov;
	}

	public void setModUltimoMov(String modUltimoMov) {
		this.modUltimoMov = modUltimoMov;
	}

	public String getTipoUltimoMov() {
		return tipoUltimoMov;
	}

	public void setTipoUltimoMov(String tipoUltimoMov) {
		this.tipoUltimoMov = tipoUltimoMov;
	}

	public String getFecUltimoMov() {
		return fecUltimoMov;
	}

	public void setFecUltimoMov(String fecUltimoMov) {
		this.fecUltimoMov = fecUltimoMov;
	}

	public int getEstadoVigencia() {
		return estadoVigencia;
	}

	public void setEstadoVigencia(int estadoVigencia) {
		this.estadoVigencia = estadoVigencia;
	}

	public ModalidadTrabajador[] getListModVigentes() {
		return listModVigentes;
	}

	public void setListModVigentes(ModalidadTrabajador[] listModVigentes) {
		this.listModVigentes = listModVigentes != null ? listModVigentes.clone() : null;
	}

	public String getTipoPension() {
		return tipoPension;
	}

	public void setTipoPension(String tipoPension) {
		this.tipoPension = tipoPension;
	}

	public int getIndTrabajadorIMSS() {
		return indTrabajadorIMSS;
	}

	public void setIndTrabajadorIMSS(int indTrabajadorIMSS) {
		this.indTrabajadorIMSS = indTrabajadorIMSS;
	}

	public Integer getSemanasCotizadas() {
		return semanasCotizadas;
	}

	public void setSemanasCotizadas(Integer semanasCotizadas) {
		this.semanasCotizadas = semanasCotizadas;
	}

	public String getRegPatUltimoObligatorio() {
		return regPatUltimoObligatorio;
	}

	public void setRegPatUltimoObligatorio(String regPatUltimoObligatorio) {
		this.regPatUltimoObligatorio = regPatUltimoObligatorio;
	}

	public String getModUltimoObligatorio() {
		return modUltimoObligatorio;
	}

	public void setModUltimoObligatorio(String modUltimoObligatorio) {
		this.modUltimoObligatorio = modUltimoObligatorio;
	}

	public String getTipoMovObligatorio() {
		return tipoMovObligatorio;
	}

	public void setTipoMovObligatorio(String tipoMovObligatorio) {
		this.tipoMovObligatorio = tipoMovObligatorio;
	}

	public String getFecMovObligatorio() {
		return fecMovObligatorio;
	}

	public void setFecMovObligatorio(String fecMovObligatorio) {
		this.fecMovObligatorio = fecMovObligatorio;
	}

	public Float getSalarioObligatorio() {
		return salarioObligatorio;
	}

	public void setSalarioObligatorio(Float salarioObligatorio) {
		this.salarioObligatorio = salarioObligatorio;
	}

	public int getIndPension() {
		return indPension;
	}

	public void setIndPension(int indPension) {
		this.indPension = indPension;
	}

	public String getRegPatUltimoMod40() {
		return regPatUltimoMod40;
	}

	public void setRegPatUltimoMod40(String regPatUltimoMod40) {
		this.regPatUltimoMod40 = regPatUltimoMod40;
	}

	public String getModUltimoMod40() {
		return modUltimoMod40;
	}

	public void setModUltimoMod40(String modUltimoMod40) {
		this.modUltimoMod40 = modUltimoMod40;
	}

	public String getTipoMovMod40() {
		return tipoMovMod40;
	}

	public void setTipoMovMod40(String tipoMovMod40) {
		this.tipoMovMod40 = tipoMovMod40;
	}

	public String getFecMovMod40() {
		return fecMovMod40;
	}

	public void setFecMovMod40(String fecMovMod40) {
		this.fecMovMod40 = fecMovMod40;
	}

	public Float getSalarioMod40() {
		return salarioMod40;
	}

	public void setSalarioMod40(Float salarioMod40) {
		this.salarioMod40 = salarioMod40;
	}
}
