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
@Table(name="SSO_CATESTATUS")
@NamedQueries(
	    {	        
	        @NamedQuery(name = "Estatus.findByClave", query = "select e from Estatus e where e.cveEstatus = :id")
	    }
)
public class Estatus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_SSOESTATUS", nullable = false, updatable = false)
	private Long cveEstatus;
	
	@Column(name="DES_ESTATUS", nullable=false, length=100)
	private String nombreEstatus;

	public Long getCveEstatus() {
		return cveEstatus;
	}

	public void setCveEstatus(Long cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public String getNombreEstatus() {
		return nombreEstatus;
	}

	public void setNombreEstatus(String nombreEstatus) {
		this.nombreEstatus = nombreEstatus;
	}
}
