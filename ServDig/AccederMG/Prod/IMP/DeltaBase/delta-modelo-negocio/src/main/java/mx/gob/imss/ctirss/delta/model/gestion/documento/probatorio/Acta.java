package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;

@XmlRootElement
public class Acta extends DocumentoProbatorio implements Serializable {

	private static final long serialVersionUID = 1L;
	// base
	private String noActa;
	private String noFoja;
	private String noLibro;
	private Municipio municipio;
	private Date fechaSuceso;
	private TipoActa tipoActa;
	private String observaciones;
	private String idEntidadFederativa;
	private Long idMunicipio;
	private String fechaExpedicionCadena;
	private String fechaFallecimientoCadena;
	private String noJuzgado;
	private String tomo;

	
	public String getNoJuzgado() {
		return noJuzgado;
	}

	public void setNoJuzgado(String noJuzgado) {
		this.noJuzgado = noJuzgado;
	}

	// Derechohabiente
	protected EntidadFederativa entidadFederativa;

	public EntidadFederativa getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public String getNoActa() {
		return noActa;
	}

	public void setNoActa(String noActa) {
		this.noActa = noActa;
	}

	public String getNoFoja() {
		return noFoja;
	}

	public void setNoFoja(String noFoja) {
		this.noFoja = noFoja;
	}

	public String getNoLibro() {
		return noLibro;
	}

	public void setNoLibro(String noLibro) {
		this.noLibro = noLibro;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Date getFechaSuceso() {
		return fechaSuceso;
	}

	public void setFechaSuceso(Date fechaSuceso) {
		this.fechaSuceso = fechaSuceso;
	}

	public TipoActa getTipoActa() {
		return tipoActa;
	}

	public void setTipoActa(TipoActa tipoActa) {
		this.tipoActa = tipoActa;
	}

	@Override
	public String toString() {
		return "Acta [noActa=" + noActa + ", noFoja=" + noFoja + ", noLibro="
				+ noLibro + ", municipio=" + municipio + ", fechaSuceso="
				+ fechaSuceso + ", tipoActa=" + tipoActa + "]";
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(String idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getFechaExpedicionCadena() {
		return fechaExpedicionCadena;
	}

	public void setFechaExpedicionCadena(String fechaExpedicionCadena) {
		this.fechaExpedicionCadena = fechaExpedicionCadena;
	}

	public String getFechaFallecimientoCadena() {
		return fechaFallecimientoCadena;
	}

	public void setFechaFallecimientoCadena(String fechaFallecimientoCadena) {
		this.fechaFallecimientoCadena = fechaFallecimientoCadena;
	}

	public String getTomo() {
		return tomo;
	}

	public void setTomo(String tomo) {
		this.tomo = tomo;
	}
	
}
