package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DetalleRegistroObra extends AvisoUbicacionObraDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1159701182023313710L;
	
	private String nombreRazonSocial;
	private String rfc;
	private String registroPatronal;
	private String numRegistroObra;
	private Date fecRegistroObra;
	private String tipoPatron;
	private BigDecimal superficie;
	private BigDecimal montoObra;
	private BigDecimal importeEjercido;
	private String tiooObra;
	private String claseObra;
	private String numObraContratante;
	private Date fecInicioObra;
	private Date fecFinObra;
	private String ultimoBimestrePresentado;
	private String desRegistro;
	
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getNumRegistroObra() {
		return numRegistroObra;
	}
	public void setNumRegistroObra(String numRegistroObra) {
		this.numRegistroObra = numRegistroObra;
	}
	public Date getFecRegistroObra() {
		return fecRegistroObra;
	}
	public void setFecRegistroObra(Date fecRegistroObra) {
		this.fecRegistroObra = fecRegistroObra;
	}
	public String getTipoPatron() {
		return tipoPatron;
	}
	public void setTipoPatron(String tipoPatron) {
		this.tipoPatron = tipoPatron;
	}
	public BigDecimal getSuperficie() {
		return superficie;
	}
	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}
	public BigDecimal getMontoObra() {
		return montoObra;
	}
	public void setMontoObra(BigDecimal montoObra) {
		this.montoObra = montoObra;
	}
	public BigDecimal getImporteEjercido() {
		return importeEjercido;
	}
	public void setImporteEjercido(BigDecimal importeEjercido) {
		this.importeEjercido = importeEjercido;
	}
	public String getTiooObra() {
		return tiooObra;
	}
	public void setTiooObra(String tiooObra) {
		this.tiooObra = tiooObra;
	}
	public String getClaseObra() {
		return claseObra;
	}
	public void setClaseObra(String claseObra) {
		this.claseObra = claseObra;
	}
	public String getNumObraContratante() {
		return numObraContratante;
	}
	public void setNumObraContratante(String numObraContratante) {
		this.numObraContratante = numObraContratante;
	}
	public Date getFecInicioObra() {
		return fecInicioObra;
	}
	public void setFecInicioObra(Date fecInicioObra) {
		this.fecInicioObra = fecInicioObra;
	}
	public Date getFecFinObra() {
		return fecFinObra;
	}
	public void setFecFinObra(Date fecFinObra) {
		this.fecFinObra = fecFinObra;
	}
	public String getUltimoBimestrePresentado() {
		return ultimoBimestrePresentado;
	}
	public void setUltimoBimestrePresentado(String ultimoBimestrePresentado) {
		this.ultimoBimestrePresentado = ultimoBimestrePresentado;
	}
	public String getDesRegistro() {
		return desRegistro;
	}
	public void setDesRegistro(String desRegistro) {
		this.desRegistro = desRegistro;
	}
	
	
	
}
