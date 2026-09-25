package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Clase de entidad para representar una Delegacion
 * @author Guillermo Vilchis Gonzalez
 * @version 1.0
 *
 */
@Entity
@Table(name="DIC_DELEGACION")
@NamedQueries(
    {
        @NamedQuery(name = "Delegacion.findAll", query = "select d from Delegacion d"),
         @NamedQuery(name = "Delegacion.findId", query = "select d from Delegacion d where d.idDelegacion = :id")
    }
)
public class Delegacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CVE_ID_DELEGACION", nullable = false, updatable = false)
    private Long idDelegacion;
    @Column(name = "DES_DELEG", nullable = true, length = 255)
    private String descripcionDelegacion;
    @Column(name = "ANIO_INI_OPER", nullable = true, length = 18)
    private String anoIniOperacion;
    @Column(name = "CLAVE_DELEGACION", nullable = true, length = 100)
    private String claveDelegacion;
    @Column(name = "TIP_DELEGACION", nullable = true, length = 18)
    private String tipoDelegacion;
    @Column(name = "DOMICILIO_ID", nullable = true)
    private Integer idDomicilio;
    
    
	/**
	 * @return the idDelegacion
	 */
	public Long getIdDelegacion() {
		return idDelegacion;
	}

	/**
	 * @param idDelegacion the idDelegacion to set
	 */
	public void setIdDelegacion(Long idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	/**
	 * @return the descripcionDelegacion
	 */
	public String getDescripcionDelegacion() {
		return descripcionDelegacion;
	}

	/**
	 * @param descripcionDelegacion the descripcionDelegacion to set
	 */
	public void setDescripcionDelegacion(String descripcionDelegacion) {
		this.descripcionDelegacion = descripcionDelegacion;
	}

	/**
	 * @return the anoIniOperacion
	 */
	public String getAnoIniOperacion() {
		return anoIniOperacion;
	}

	/**
	 * @param anoIniOperacion the anoIniOperacion to set
	 */
	public void setAnoIniOperacion(String anoIniOperacion) {
		this.anoIniOperacion = anoIniOperacion;
	}

	/**
	 * @return the claveDelegacion
	 */
	public String getClaveDelegacion() {
		return claveDelegacion;
	}

	/**
	 * @param claveDelegacion the claveDelegacion to set
	 */
	public void setClaveDelegacion(String claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	/**
	 * @return the tipoDelegacion
	 */
	public String getTipoDelegacion() {
		return tipoDelegacion;
	}

	/**
	 * @param tipoDelegacion the tipoDelegacion to set
	 */
	public void setTipoDelegacion(String tipoDelegacion) {
		this.tipoDelegacion = tipoDelegacion;
	}

	
	/**
	 * @return the idDomicilio
	 */
	public Integer getIdDomicilio() {
		return idDomicilio;
	}

	/**
	 * @param idDomicilio the idDomicilio to set
	 */
	public void setIdDomicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Delegacion other = (Delegacion) obj;
        if (this.idDelegacion != other.idDelegacion && (this.idDelegacion == null || !this.idDelegacion.equals(other.idDelegacion))) {
            return false;
        }
        if ((this.descripcionDelegacion == null) ? (other.descripcionDelegacion != null) : !this.descripcionDelegacion.equals(other.descripcionDelegacion)) {
            return false;
        }
        if ((this.anoIniOperacion == null) ? (other.anoIniOperacion != null) : !this.anoIniOperacion.equals(other.anoIniOperacion)) {
            return false;
        }
        if ((this.claveDelegacion == null) ? (other.claveDelegacion != null) : !this.claveDelegacion.equals(other.claveDelegacion)) {
            return false;
        }
        if ((this.tipoDelegacion == null) ? (other.tipoDelegacion != null) : !this.tipoDelegacion.equals(other.tipoDelegacion)) {
            return false;
        }
        if ((this.idDomicilio == null) ? (other.idDomicilio != null) : !this.idDomicilio.equals(other.idDomicilio)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.idDelegacion != null ? this.idDelegacion.hashCode() : 0);
        hash = 97 * hash + (this.descripcionDelegacion != null ? this.descripcionDelegacion.hashCode() : 0);
        hash = 97 * hash + (this.anoIniOperacion != null ? this.anoIniOperacion.hashCode() : 0);
        hash = 97 * hash + (this.claveDelegacion != null ? this.claveDelegacion.hashCode() : 0);
        hash = 97 * hash + (this.tipoDelegacion != null ? this.tipoDelegacion.hashCode() : 0);
        hash = 97 * hash + (this.idDomicilio != null ? this.idDomicilio.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "mx.gob.imss.ctirss.admonusuarios.entities[ idDelegacion=" + idDelegacion + " ]";
    }
    
}
