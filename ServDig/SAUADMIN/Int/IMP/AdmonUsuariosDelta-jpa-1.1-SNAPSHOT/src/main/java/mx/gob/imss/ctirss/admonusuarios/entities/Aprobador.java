/**
 * 
 */
package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Shinji
 *
 */
@Entity
@Table(name="DIT_SSO_APROBADOR")
@NamedQueries(
	    {	@NamedQuery(name = "Aprobador.findAll", query = "select a from Aprobador a"),	     
	        @NamedQuery(name = "Aprobador.findIdModulo", query = "select a from Aprobador a where a.idModulo = :id")
	    }
)
public class Aprobador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_SSOAPROBADOR_CVEIDAPROBADOR_GENERATOR", sequenceName = "SEQ_DITSSOAPROBADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SSOAPROBADOR_CVEIDAPROBADOR_GENERATOR")	
	@Column(name="CVE_ID_APROBADOR", nullable = false, updatable = false)
	private Long idAprobador;
	
	@Column(name="CVE_ID_USUARIO_FUNCIONARIO")
	private Long idUsuarioFuncionario;
	
	@Column(name="CVE_MATRICULA", nullable=false, length=20)
	private String matricula;
	
	@Column(name="CVE_ID_MODULO")
	private Long idModulo;
	
	@Column(name="DES_AREA")
	private String area;

	/**
	 * @return the idAprobador
	 */
	public Long getIdAprobador() {
		return idAprobador;
	}
	/**
	 * @param idAprobador the idAprobador to set
	 */
	public void setIdAprobador(Long idAprobador) {
		this.idAprobador = idAprobador;
	}

	/**
	 * @return the idUsuarioFuncionario
	 */
	public Long getIdUsuarioFuncionario() {
		return idUsuarioFuncionario;
	}
	/**
	 * @param idUsuarioFuncionario the idUsuarioFuncionario to set
	 */
	public void setIdUsuarioFuncionario(Long idUsuarioFuncionario) {
		this.idUsuarioFuncionario = idUsuarioFuncionario;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}
	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	/**
	 * @return the idModulo
	 */
	public Long getIdModulo() {
		return idModulo;
	}
	/**
	 * @param idModulo the idModulo to set
	 */
	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
			
}
