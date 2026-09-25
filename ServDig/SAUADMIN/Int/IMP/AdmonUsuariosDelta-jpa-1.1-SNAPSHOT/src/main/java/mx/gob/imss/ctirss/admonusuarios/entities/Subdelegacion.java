package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Clase de entidad para representar una Subdelegacion
 * @author Guillermo Vilchis Gonzalez
 * @version 1.0
 *
 */
@Entity
@Table(name="DIC_SUBDELEGACION")
@NamedQueries(
    {
    	@NamedQuery(name = "Subdelegacion.findByID", query = "select s from Subdelegacion s where s.delegacion.idDelegacion = :id"),
    	@NamedQuery(name = "Subdelegacion.findAll", query = "select s from Subdelegacion s"),
    	@NamedQuery(name = "Subdelegacion.findSubID", query = "select s from Subdelegacion s where s.idSubdelegacion = :id")
    }
)
public class Subdelegacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CVE_ID_SUBDELEGACION", nullable = false, updatable = false)
    private Long idSubdelegacion;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_DELEGACION")
	private Delegacion delegacion;
    @Column(name = "DES_SUBDELEGACION", nullable = true, length = 255)
    private String descripcionSubelegacion;
    @Column(name = "ANIO_INI_OPER", nullable = true, length = 18)
    private String anoIniOperacion;
    @Column(name = "CLAVE_SUBDELEGACION", nullable = true, length = 100)
    private String claveSubdelegacion;
    @Column(name = "DOMICILIO_ID", nullable = true)
    private Integer idDomicilio;
       
	/**
	 * @return the idSubdelegacion
	 */
	public Long getIdSubdelegacion() {
		return idSubdelegacion;
	}

	/**
	 * @param idSubdelegacion the idSubdelegacion to set
	 */
	public void setIdSubdelegacion(Long idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}

	/**
	 * @return the delegacion
	 */
	public Delegacion getDelegacion() {
		return delegacion;
	}

	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(Delegacion delegacion) {
		this.delegacion = delegacion;
	}

	/**
	 * @return the descripcionSubelegacion
	 */
	public String getDescripcionSubelegacion() {
		return descripcionSubelegacion;
	}

	/**
	 * @param descripcionSubelegacion the descripcionSubelegacion to set
	 */
	public void setDescripcionSubelegacion(String descripcionSubelegacion) {
		this.descripcionSubelegacion = descripcionSubelegacion;
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
	 * @return the claveSubdelegacion
	 */
	public String getClaveSubdelegacion() {
		return claveSubdelegacion;
	}

	/**
	 * @param claveSubdelegacion the claveSubdelegacion to set
	 */
	public void setClaveSubdelegacion(String claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
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
        final Subdelegacion other = (Subdelegacion) obj;
        if (this.idSubdelegacion != other.idSubdelegacion && (this.idSubdelegacion == null || !this.idSubdelegacion.equals(other.idSubdelegacion))) {
            return false;
        }
        if ((this.delegacion == null) ? (other.delegacion != null) : !this.delegacion.equals(other.delegacion)) {
            return false;
        }
        if ((this.anoIniOperacion == null) ? (other.anoIniOperacion != null) : !this.anoIniOperacion.equals(other.anoIniOperacion)) {
            return false;
        }
        if ((this.descripcionSubelegacion == null) ? (other.descripcionSubelegacion != null) : !this.descripcionSubelegacion.equals(other.descripcionSubelegacion)) {
            return false;
        }
        if ((this.claveSubdelegacion == null) ? (other.claveSubdelegacion != null) : !this.claveSubdelegacion.equals(other.claveSubdelegacion)) {
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
        hash = 97 * hash + (this.idSubdelegacion != null ? this.idSubdelegacion.hashCode() : 0);
        hash = 97 * hash + (this.delegacion != null ? this.delegacion.hashCode() : 0);
        hash = 97 * hash + (this.anoIniOperacion != null ? this.anoIniOperacion.hashCode() : 0);
        hash = 97 * hash + (this.descripcionSubelegacion != null ? this.descripcionSubelegacion.hashCode() : 0);
        hash = 97 * hash + (this.claveSubdelegacion != null ? this.claveSubdelegacion.hashCode() : 0);
        hash = 97 * hash + (this.idDomicilio != null ? this.idDomicilio.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "mx.gob.imss.ctirss.admonusuarios.entities[ idSubdelegacion=" + idSubdelegacion + " ]";
    }
    
}
