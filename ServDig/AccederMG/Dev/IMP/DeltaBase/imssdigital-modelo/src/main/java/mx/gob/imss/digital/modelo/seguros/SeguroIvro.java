/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40;

/**
 * @author NOVUTECK1
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "seguroIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "seguroIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class SeguroIvro implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del seguro
     */
    private Long cveIdSeguroIvro;
    /**
     * Persona fisica titular del seguro
     */
    private Fisica titular;
    /**
     * fecha de inicio del seguro
     */
    private Date fechaInicio;
    /**
     * Fecha final del seguro
     */
    private Date fechaFin;
    /**
     * Estado del seguro
     */
    private EstadoSeguroIvro estadoSeguro;
    /**
     * Identificador de la modalidad
     */
    private Modalidad modalidad;
    /**
     * Fecha en que se da de baja el seguro IVRO
     */
    private Date fechaBaja;

    /**
     * Compra asociada a la ventanilla
     */
    private Compra compra;
    /**
     * Tramite asociado al seguro
     */
    private TramiteSeguroIvro tramite;
    /**
     * Tramite asociado al seguro
     */
    private TramiteSeguroIvroMod33 tramiteSeguroFamiliar;
    /**
     * Tramite asociado al seguro
     */
    private TramiteSeguroIvroMod40 tramiteContVoluntaria;
    /**
     * Propiedad que indica si el seguro se encuentra en periodo de renovacion
     */
    private Boolean enRenovacion = false;
    
    /**
     * Propiedad que indica si el seguro se encuentra en periodo de renovacion
     */
    private Boolean extemporanea = false;
    
    
    /**
     * @return the cveIdSeguroIvro
     */
    public Long getCveIdSeguroIvro() {
        return cveIdSeguroIvro;
    }

    /**
     * @param cveIdSeguroIvro
     *            the cveIdSeguroIvro to set
     */
    public void setCveIdSeguroIvro(Long cveIdSeguroIvro) {
        this.cveIdSeguroIvro = cveIdSeguroIvro;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *            the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the titular
     */
    public Fisica getTitular() {
        return titular;
    }

    /**
     * @param titular
     *            the titular to set
     */
    public void setTitular(Fisica titular) {
        this.titular = titular;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *            the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the estadoSeguro
     */
    public EstadoSeguroIvro getEstadoSeguro() {
        return estadoSeguro;
    }

    /**
     * @param estadoSeguro
     *            the estadoSeguro to set
     */
    public void setEstadoSeguro(EstadoSeguroIvro estadoSeguro) {
        this.estadoSeguro = estadoSeguro;
    }

    /**
     * @return the modalidad
     */
    public Modalidad getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *            the modalidad to set
     */
    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the fechaBaja
     */
    public Date getFechaBaja() {
        return fechaBaja;
    }

    /**
     * @param fechaBaja
     *            the fechaBaja to set
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    /**
     * @return the compra
     */
    public Compra getCompra() {
        return compra;
    }

    /**
     * @param compra
     *            the compra to set
     */
    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    /**
     * @return the tramite
     */
    public TramiteSeguroIvro getTramite() {
        return tramite;
    }

    /**
     * @param tramite
     *            the tramite to set
     */
    public void setTramite(TramiteSeguroIvro tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the enRenovacion
     */
    public Boolean getEnRenovacion() {
        return enRenovacion;
    }

    /**
     * @param enRenovacion the enRenovacion to set
     */
    public void setEnRenovacion(Boolean enRenovacion) {
        this.enRenovacion = enRenovacion;
    }

    /**
	 * @return the extemporanea
	 */
	public Boolean getExtemporanea() {
		return extemporanea;
	}
	/**
	 * @return the extemporanea
	 */
	public Boolean isExtemporanea() {
		return extemporanea;
	}

	/**
	 * @param extemporanea the extemporanea to set
	 */
	public void setExtemporanea(Boolean extemporanea) {
		this.extemporanea = extemporanea;
	}

	public TramiteSeguroIvroMod33 getTramiteSeguroFamiliar() {
		return tramiteSeguroFamiliar;
	}

	public void setTramiteSeguroFamiliar(
			TramiteSeguroIvroMod33 tramiteSeguroFamiliar) {
		this.tramiteSeguroFamiliar = tramiteSeguroFamiliar;
	}

	public TramiteSeguroIvroMod40 getTramiteContVoluntaria() {
		return tramiteContVoluntaria;
	}

	public void setTramiteContVoluntaria(
			TramiteSeguroIvroMod40 tramiteContVoluntaria) {
		this.tramiteContVoluntaria = tramiteContVoluntaria;
	}


}
