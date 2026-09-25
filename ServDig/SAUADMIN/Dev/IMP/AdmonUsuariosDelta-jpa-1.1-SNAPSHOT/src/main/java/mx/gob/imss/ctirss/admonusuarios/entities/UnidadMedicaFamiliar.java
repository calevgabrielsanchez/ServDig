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
 * Clase de entidad para representar una UMF
 * @author Guillermo Vilchis Gonzalez
 * @version 1.0
 *
 */
@Entity
@Table(name="DIC_UMF")
@NamedQueries(
    {
    	@NamedQuery(name = "UnidadMedicaFamiliar.findByID", query = "select u from UnidadMedicaFamiliar u where u.subdelegacion.idSubdelegacion = :id"),
    	@NamedQuery(name = "UnidadMedicaFamiliar.findAll", query = "select u from UnidadMedicaFamiliar u"),
    	@NamedQuery(name = "UnidadMedicaFamiliar.findUmfID", query = "select u from UnidadMedicaFamiliar u where u.idUmf = :id")
    }
)
public class UnidadMedicaFamiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CVE_ID_UMF", nullable = false, updatable = false)
    private Long idUmf;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_SUBDELEGACION")
	private Subdelegacion subdelegacion;
    @Column(name = "NOM_CORTO", nullable = true, length = 255)
    private String descripcionUmf;
    @Column(name = "NOM_UNIDAD", nullable = true, length = 255)
    private String nombreUnidad;      

	/**
	 * @return the idUmf
	 */
	public Long getIdUmf() {
		return idUmf;
	}

	/**
	 * @param idUmf the idUmf to set
	 */
	public void setIdUmf(Long idUmf) {
		this.idUmf = idUmf;
	}

	/**
	 * @return the subdelegacion
	 */
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	/**
	 * @param subdelegacion the subdelegacion to set
	 */
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	/**
	 * @return the descripcionUmf
	 */
	public String getDescripcionUmf() {
		return descripcionUmf;
	}

	/**
	 * @param descripcionUmf the descripcionUmf to set
	 */
	public void setDescripcionUmf(String descripcionUmf) {
		this.descripcionUmf = descripcionUmf;
	}


	
	/**
	 * @return the nombreUnidad
	 */
	public String getNombreUnidad() {
		return nombreUnidad;
	}

	/**
	 * @param nombreUnidad the nombreUnidad to set
	 */
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnidadMedicaFamiliar other = (UnidadMedicaFamiliar) obj;
        if (this.idUmf != other.idUmf && (this.idUmf == null || !this.idUmf.equals(other.idUmf))) {
            return false;
        }
        if ((this.subdelegacion == null) ? (other.subdelegacion != null) : !this.subdelegacion.equals(other.subdelegacion)) {
            return false;
        }
        if ((this.descripcionUmf == null) ? (other.descripcionUmf != null) : !this.descripcionUmf.equals(other.descripcionUmf)) {
            return false;
        }
        if ((this.nombreUnidad == null) ? (other.nombreUnidad != null) : !this.nombreUnidad.equals(other.nombreUnidad)) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.idUmf != null ? this.idUmf.hashCode() : 0);
        hash = 97 * hash + (this.subdelegacion != null ? this.subdelegacion.hashCode() : 0);
        hash = 97 * hash + (this.descripcionUmf != null ? this.descripcionUmf.hashCode() : 0);
        hash = 97 * hash + (this.nombreUnidad != null ? this.nombreUnidad.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "mx.gob.imss.ctirss.admonusuarios.entities[ idUmf=" + idUmf + " ]";
    }
    
}
