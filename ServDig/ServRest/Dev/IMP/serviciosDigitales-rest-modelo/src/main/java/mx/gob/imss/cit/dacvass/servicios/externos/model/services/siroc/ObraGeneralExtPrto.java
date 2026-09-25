package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObraGeneralExtPrto extends DetalleRegistroObra implements
		Serializable {

	private static final long serialVersionUID = 1159701182023313710L;

	public String getBaja() {
		return baja;
	}

	public void setBaja(String baja) {
		this.baja = baja;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getFraccion() {
		return fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	public BigDecimal getPrima() {
		return prima;
	}

	public void setPrima(BigDecimal prima) {
		this.prima = prima;
	}

	public String getTerminacionPorIncidencia() {
		return terminacionPorIncidencia;
	}

	public void setTerminacionPorIncidencia(String terminacionPorIncidencia) {
		this.terminacionPorIncidencia = terminacionPorIncidencia;
	}

	public String getDescTipoIncidencia() {
		return descTipoIncidencia;
	}

	public void setDescTipoIncidencia(String descTipoIncidencia) {
		this.descTipoIncidencia = descTipoIncidencia;
	}

	public Date getFechaTerminoIncidencia() {
		return fechaTerminoIncidencia;
	}

	public void setFechaTerminoIncidencia(Date fechaTerminoIncidencia) {
		this.fechaTerminoIncidencia = fechaTerminoIncidencia;
	}

	public BigDecimal getSuperficieIncidencia() {
		return superficieIncidencia;
	}

	public void setSuperficieIncidencia(BigDecimal superficieIncidencia) {
		this.superficieIncidencia = superficieIncidencia;
	}

	public BigDecimal getMontoObraIncidencia() {
		return montoObraIncidencia;
	}

	public void setMontoObraIncidencia(BigDecimal montoObraIncidencia) {
		this.montoObraIncidencia = montoObraIncidencia;
	}

	public BigDecimal getROWNUM_() {
		return ROWNUM_;
	}

	public void setROWNUM_(BigDecimal rOWNUM_) {
		ROWNUM_ = rOWNUM_;
	}

	public Integer getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(Integer idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getMarcaPrto() {
		return marcaPrto;
	}

	public void setMarcaPrto(String marcaPrto) {
		this.marcaPrto = marcaPrto;
	}

	public BigDecimal getCveInformacionObra() {
		return cveInformacionObra;
	}

	public void setCveInformacionObra(BigDecimal cveInformacionObra) {
		this.cveInformacionObra = cveInformacionObra;
	}

	public Integer getIdSubDelegacion() {
		return idSubDelegacion;
	}

	public void setIdSubDelegacion(Integer idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}

	public Integer getNumDias() {
		return numDias;
	}

	public void setNumDias(Integer numDias) {
		this.numDias = numDias;
	}

	private BigDecimal ROWNUM_;

	private String terminacionPorIncidencia;

	private String descTipoIncidencia;

	private Date fechaTerminoIncidencia;

	private BigDecimal superficieIncidencia;

	private BigDecimal montoObraIncidencia;

	private Integer idDelegacion;

	private Integer idSubDelegacion;

	private Integer idMunicipio;

	private Integer numDias;

	private BigDecimal cveInformacionObra;

	private String marcaPrto;

	private BigDecimal prima;

	private String fraccion;

	private String clase;

	private String baja;

}
