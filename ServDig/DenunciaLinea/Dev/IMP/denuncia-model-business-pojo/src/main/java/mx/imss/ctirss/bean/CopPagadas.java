package mx.imss.ctirss.bean;

import mx.imss.ctirss.framework.base.model.AbstractModel;

public class CopPagadas extends AbstractModel {
	private String cuotasImss;
	private String cuotasImssActualizacion;
	private String cuotasImssRecargos;
	private String cuotasImssTotal;
	private String rcv;
	private String rcvActualizacion;
	private String rcvRecargos;
	private String rcvTotal;
	private String totalCuotasRcv;
	private String totalActualizacion;
	private String totalRecargos;
	private String granTotal;
	private String calle;
	private String numExt;
	private String numInt;
	private String colonia;
	private String municipio;
	private String localidad;
	private String entidadFederativa;
	private String codigoPostal;
	private String digitoVerificador;
	private String registroPatronal;
	private String numTrabajadoresRegularizados;
	
	public String getNumTrabajadoresRegularizados() {
		return numTrabajadoresRegularizados;
	}
	public void setNumTrabajadoresRegularizados(String numTrabajadoresRegularizados) {
		this.numTrabajadoresRegularizados = numTrabajadoresRegularizados;
	}
	public String getDigitoVerificador() {
		return digitoVerificador;
	}
	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getCuotasImss() {
		return cuotasImss;
	}
	public void setCuotasImss(String cuotasImss) {
		this.cuotasImss = cuotasImss;
	}
	public String getCuotasImssActualizacion() {
		return cuotasImssActualizacion;
	}
	public void setCuotasImssActualizacion(String cuotasImssActualizacion) {
		this.cuotasImssActualizacion = cuotasImssActualizacion;
	}
	public String getCuotasImssRecargos() {
		return cuotasImssRecargos;
	}
	public void setCuotasImssRecargos(String cuotasImssRecargos) {
		this.cuotasImssRecargos = cuotasImssRecargos;
	}
	public String getCuotasImssTotal() {
		return cuotasImssTotal;
	}
	public void setCuotasImssTotal(String cuotasImssTotal) {
		this.cuotasImssTotal = cuotasImssTotal;
	}
	public String getRcv() {
		return rcv;
	}
	public void setRcv(String rcv) {
		this.rcv = rcv;
	}
	public String getRcvActualizacion() {
		return rcvActualizacion;
	}
	public void setRcvActualizacion(String rcvActualizacion) {
		this.rcvActualizacion = rcvActualizacion;
	}
	public String getRcvRecargos() {
		return rcvRecargos;
	}
	public void setRcvRecargos(String rcvRecargos) {
		this.rcvRecargos = rcvRecargos;
	}
	public String getRcvTotal() {
		return rcvTotal;
	}
	public void setRcvTotal(String rcvTotal) {
		this.rcvTotal = rcvTotal;
	}
	public String getTotalCuotasRcv() {
		return totalCuotasRcv;
	}
	public void setTotalCuotasRcv(String totalCuotasRcv) {
		this.totalCuotasRcv = totalCuotasRcv;
	}
	public String getTotalActualizacion() {
		return totalActualizacion;
	}
	public void setTotalActualizacion(String totalActualizacion) {
		this.totalActualizacion = totalActualizacion;
	}
	public String getTotalRecargos() {
		return totalRecargos;
	}
	public void setTotalRecargos(String totalRecargos) {
		this.totalRecargos = totalRecargos;
	}
	public String getGranTotal() {
		return granTotal;
	}
	public void setGranTotal(String granTotal) {
		this.granTotal = granTotal;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumExt() {
		return numExt;
	}
	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}
	public String getNumInt() {
		return numInt;
	}
	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}
