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
@Table(name="SSO_CATDEPARTAMENTO")
@NamedQueries(
	    {	        
	        @NamedQuery(name = "Departamento.findIdByArea", query = "select d from Departamento d where d.areaNormativa.idAreaNormativa = :id"),
	        @NamedQuery(name = "Departamento.findAll", query = "select d from Departamento d"),
	        @NamedQuery(name = "Departamento.findById", query = "select d from Departamento d where d.cveDepartamento = :id")
	    }
)
public class Departamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_SSODEPTO", nullable = false, updatable = false)
	private Long cveDepartamento;
	
	@Column(name="DES_DEPARTAMENTO", nullable=false, length=100)
	private String nombreDepto;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOAREANORMA")
	private AreaNormativa areaNormativa;
   
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_DEPTO_PADRE")
	private Departamento departamentoPadre;


	public Long getCveDepartamento() {
		return cveDepartamento;
	}

	public void setCveDepartamento(Long cveDepartamento) {
		this.cveDepartamento = cveDepartamento;
	}

	public String getNombreDepto() {
		return nombreDepto;
	}

	public void setNombreDepto(String nombreDepto) {
		this.nombreDepto = nombreDepto;
	}

	public AreaNormativa getAreaNormativa() {
		return areaNormativa;
	}

	public void setAreaNormativa(AreaNormativa areaNormativa) {
		this.areaNormativa = areaNormativa;
	}

	public Departamento getDepartamentoPadre() {
		return departamentoPadre;
	}

	public void setDepartamentoPadre(Departamento departamentoPadre) {
		this.departamentoPadre = departamentoPadre;
	}	
}
