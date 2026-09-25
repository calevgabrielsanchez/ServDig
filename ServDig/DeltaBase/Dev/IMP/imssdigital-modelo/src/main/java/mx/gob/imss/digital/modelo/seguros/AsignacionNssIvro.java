package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * @author NOVUTECK1
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asignacionNssIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "asignacionNssIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class AsignacionNssIvro implements Serializable {


	private long cveIdAsignacionNss;

	private BigDecimal canSemanaCotizada;

	private Date fecRegistroActualizado;

	private Date fecRegistroAlta;

	private Date fecRegistroBaja;

	private BigDecimal indActivo;

	private String numNss;

    public long getCveIdAsignacionNss() {
        return cveIdAsignacionNss;
    }

    public void setCveIdAsignacionNss(long cveIdAsignacionNss) {
        this.cveIdAsignacionNss = cveIdAsignacionNss;
    }
    
    public BigDecimal getCanSemanaCotizada() {
        return canSemanaCotizada;
    }

    public void setCanSemanaCotizada(BigDecimal canSemanaCotizada) {
        this.canSemanaCotizada = canSemanaCotizada;
    }
    
    
    public BigDecimal getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(BigDecimal indActivo) {
        this.indActivo = indActivo;
    }
    
    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date indAcfecRegistroActualizadotivo) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }
    
    public Date getFecRegistroAlta() {
        return fecRegistroAlta;
    }

    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }

    public Date getFecRegistroBaja() {
        return fecRegistroBaja;
    }

    public void setFecRegistroBaja(Date fecRegistroBaja) {
        this.fecRegistroBaja = fecRegistroBaja;
    }
    
    public String getNumNss() {
        return numNss;
    }

    public void setNumNss(String numNss) {
        this.numNss = numNss;
    }


}
