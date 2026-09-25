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
@Table(name="SSO_CATDEPTOMODULO")
@NamedQueries(
	    {	        
	        @NamedQuery(name = "DeptoModulo.findIdByDepartamento", query = "select d from DeptoModulo d where d.departamento.cveDepartamento = :id"),
	        @NamedQuery(name = "DeptoModulo.findById", query = "select d from DeptoModulo d where d.cveDeptoModulo = :id"),
	        @NamedQuery(name = "DeptoModulo.deleteById", query = "delete from DeptoModulo where cveDeptoModulo = :id"),
	        @NamedQuery(name = "DeptoModulo.findByClaves", query = "select d from DeptoModulo d where d.departamento.cveDepartamento = :cveDepto and d.modulo.idModulo = :cveModulo")
	    }
)
public class DeptoModulo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_SSODEPTOMODULO", nullable = false, updatable = false)
	private Long cveDeptoModulo;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSODEPTO")
	private Departamento departamento;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_MODULO")
	private Modulo modulo;

	public Long getCveDeptoModulo() {
		return cveDeptoModulo;
	}

	public void setCveDeptoModulo(Long cveDeptoModulo) {
		this.cveDeptoModulo = cveDeptoModulo;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}	
}
