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
 * @author cesarAgustin
 *
 */
@Entity
@Table(name="SSO_CATPUESTOS")
@NamedQueries(
	    {	        
	        @NamedQuery(name = "Puesto.findIdByDepartamento", query = "select p from Puesto p where p.departamento.cveDepartamento = :id"),
	        @NamedQuery(name = "Puesto.findByClave", query = "select p from Puesto p where p.cvePuesto = :id"),
	        @NamedQuery(name = "Puesto.findAll", query = "select p from Puesto p"),
	        @NamedQuery(name = "Puesto.findByDescripcion", query = "select p from Puesto p where p.nombrePuesto = :nomPuesto")
	    }
)
public class Puesto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_SSOPUESTO", nullable = false, updatable = false)
	private Long cvePuesto;
	
	@Column(name="DES_PUESTO", nullable=false, length=100)
	private String nombrePuesto;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSODEPTO")
	private Departamento departamento;

	public Long getCvePuesto() {
		return cvePuesto;
	}

	public void setCvePuesto(Long cvePuesto) {
		this.cvePuesto = cvePuesto;
	}

	public String getNombrePuesto() {
		return nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}	
}
