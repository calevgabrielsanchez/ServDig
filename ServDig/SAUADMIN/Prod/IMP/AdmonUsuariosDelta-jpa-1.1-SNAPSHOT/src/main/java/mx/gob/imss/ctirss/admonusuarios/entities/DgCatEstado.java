/**
 * 
 */
package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Alan Rene Garcia Rico
 *
 */
@Entity
@Table(name="DG_CAT_ESTADO")
@NamedQueries(
	    {	        
	        @NamedQuery(name = "DgCatEstado.findById", query = "select d from DgCatEstado d where d.cveEntidad = :id")
	    }
)
public class DgCatEstado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_ENT", nullable = false, updatable = false)
	private Long cveEntidad;
	
	@Column(name="NOM_ENT", nullable=false, length=100)
	private String nombreEntidad;

	public Long getCveEntidad() {
		return cveEntidad;
	}

	public void setCveEntidad(Long cveEntidad) {
		this.cveEntidad = cveEntidad;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
}
