package mx.gob.imss.cit.cda.web.bandeja.vo;

import java.math.BigInteger;
import java.util.List;

import mx.gob.imss.cit.cda.web.common.vo.SolicitudBase;

public class SolicitudBandeja extends SolicitudBase implements Comparable<SolicitudBandeja> { 

    /**
     * 
     */
    private static final long serialVersionUID = 5921674985050237326L;

    private String fechaSolicitud;
    private String curp;
    private List<String> nssListaInvolucrados;
    private String origen;
    private String responsable;
    private String autorizo;
    private String estatus;
    private String ultimaActualizacion;
    private String nombreCompletoResponsable;
    private String nombreCompletoAutorizo;
    private String tipo;
    private Boolean esPropietario;
    
    private String folioConsulta;
    private Integer pantallaConsulta;


    /**
     * @return the fechaSolicitud
     */
    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud
     *            the fechaSolicitud to set
     */
    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen
     *            the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable
     *            the responsable to set
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the autorizo
     */
    public String getAutorizo() {
        return autorizo;
    }

    /**
     * @param autorizo
     *            the autorizo to set
     */
    public void setAutorizo(String autorizo) {
        this.autorizo = autorizo;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus
     *            the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the ultimaActualizacion
     */
    public String getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    /**
     * @param ultimaActualizacion
     *            the ultimaActualizacion to set
     */
    public void setUltimaActualizacion(String ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public String getNombreCompletoResponsable() {
        return nombreCompletoResponsable;
    }

    public void setNombreCompletoResponsable(String nombreCompletoResponsable) {
        this.nombreCompletoResponsable = nombreCompletoResponsable;
    }

    public String getNombreCompletoAutorizo() {
        return nombreCompletoAutorizo;
    }

    public void setNombreCompletoAutorizo(String nombreCompletoAutorizo) {
        this.nombreCompletoAutorizo = nombreCompletoAutorizo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getEsPropietario() {
        return esPropietario;
    }

    public void setEsPropietario(Boolean esPropietario) {
        this.esPropietario = esPropietario;
    }

    /**
     * @return the curp
     */
    public String getCurp() {
        return curp;
    }

    /**
     * @param curp
     *            the curp to set
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    public List<String> getNssListaInvolucrados() {
        return nssListaInvolucrados;
    }

    public void setNssListaInvolucrados(List<String> nssListaInvolucrados) {
        this.nssListaInvolucrados = nssListaInvolucrados;
    }

    /**
     * @return the TramitesAsignados
     */
    @Override
    public String toString() {
        return "HistoricoSolicitudes [fechaSolicitud="
                + fechaSolicitud + ", curp=" + curp + ", origen=" + origen + ", responsable="
                + responsable + ", autorizo=" + autorizo + ", estatus="
                + estatus + ", ultimaActualizacion=" + ultimaActualizacion
                + ", nombreCompletoResponsable=" + nombreCompletoResponsable
                + ", nombreCompletoAutorizo=" + nombreCompletoAutorizo
                + ", tipo=" + tipo + ", esPropietario=" + esPropietario + "]";
    }

	public String getFolioConsulta() {
		return folioConsulta;
	}

	public void setFolioConsulta(String folioConsulta) {
		this.folioConsulta = folioConsulta;
	}

	public Integer getPantallaConsulta() {
		return pantallaConsulta;
	}

	public void setPantallaConsulta(Integer pantallaConsulta) {
		this.pantallaConsulta = pantallaConsulta;
	}

    
	public int compareTo(SolicitudBandeja o) {
	    SolicitudBandeja bandeja = (SolicitudBandeja)o;        
        
	    BigInteger local = new BigInteger(this.getFolio());
	    
	    BigInteger recibido = new BigInteger(bandeja.getFolio());
	    
	    return recibido.compareTo(local);
	  
        
    }

    
}

	

