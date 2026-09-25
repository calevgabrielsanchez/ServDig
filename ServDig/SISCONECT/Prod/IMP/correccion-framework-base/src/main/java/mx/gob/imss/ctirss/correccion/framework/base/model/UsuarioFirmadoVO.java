/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.base.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <code>Value Object</code> que presenta al usuario firmado dentro del sistema,
 * puede ser un <b>Patran</b> o un <b>Funcionario</b>
 * 
 * @author vaguirre
 * 
 */
public class UsuarioFirmadoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7105262356614698995L;

	private long cveIdUsuario;

	private BigDecimal cveIdPersona;
	
	
	private String nomMaterno;

	private String nomNombre;

	private String nomPaterno;

	private String nomUsuarioSistema;
	
	private Long idDelegacion;
	private Long idSubDelegacion;

	public Long getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(Long idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public Long getIdSubDelegacion() {
		return idSubDelegacion;
	}

	public void setIdSubDelegacion(Long idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}

	public long getCveIdUsuario() {
		return cveIdUsuario;
	}

	public void setCveIdUsuario(long cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	public BigDecimal getCveIdPersona() {
		return cveIdPersona;
	}

	public void setCveIdPersona(BigDecimal cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public String getNomMaterno() {
		return nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	public String getNomNombre() {
		return nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPaterno() {
		return nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	public String getNomUsuarioSistema() {
		return nomUsuarioSistema;
	}

	public void setNomUsuarioSistema(String nomUsuarioSistema) {
		this.nomUsuarioSistema = nomUsuarioSistema;
	}

	public BigDecimal getTipUsuario() {
		return tipUsuario;
	}

	public void setTipUsuario(BigDecimal tipUsuario) {
		this.tipUsuario = tipUsuario;
	}

	private BigDecimal tipUsuario;
	
}
