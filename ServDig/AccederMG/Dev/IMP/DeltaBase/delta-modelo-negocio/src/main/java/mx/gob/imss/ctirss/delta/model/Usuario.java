/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:Usuario.java
 *  @Paquete:mx.gob.imss.delta.model
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.model;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioOrdinario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

/**
 * @author Lucio Duran Silva, CGM
 * 
 */
@XmlRootElement
public class Usuario extends AbstractModel {

    private static final long serialVersionUID = 1L;
    //Base
    private String usuario; // NOPMD It is somewhat confusing to have a field name matching the declaring class name
    private String password;
    private String passwordConfirmacion;
    private String cveIdUsuario;
    private Long idUmf;
    private Long idTurno;

    //Derechohabiente
    public static final String SES_NAME = "usuarioObj";
    private PerfilUsuario perfilUsuario;
    private UsuarioFuncionario usuarioFuncionario;
    private UsuarioOrdinario usuarioOrdinario;
    private Fisica fisica;
    private String nomMaterno;
    private String nomNombre;
    private String nomPaterno;
    
    //Gestion Patronal
    private Moral moral;
    private Long cveIdSubdelegacion;
    //Gestion RL en GP
    private boolean cuentaRLConActosDeAdmonDominio;
    
    private String telefono;
    private String correo;
    private String correoConfirmacion;
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getCveIdUsuario() {
        return cveIdUsuario;
    }

    public void setCveIdUsuario(final String cveIdUsuario) {
        this.cveIdUsuario = cveIdUsuario;
    }

    public Long getIdUmf() {
        return idUmf;
    }

    public void setIdUmf(final Long idUmf) {
        this.idUmf = idUmf;
    }

    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(final Long idTurno) {
        this.idTurno = idTurno;
    }

    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(final PerfilUsuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public UsuarioFuncionario getUsuarioFuncionario() {
        return usuarioFuncionario;
    }

    public void setUsuarioFuncionario(final UsuarioFuncionario usuarioFuncionario) {
        this.usuarioFuncionario = usuarioFuncionario;
    }

    public UsuarioOrdinario getUsuarioOrdinario() {
        return usuarioOrdinario;
    }

    public void setUsuarioOrdinario(final UsuarioOrdinario usuarioOrdinario) {
        this.usuarioOrdinario = usuarioOrdinario;
    }

    public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(final Fisica fisica) {
        this.fisica = fisica;
    }

    public String getNomMaterno() {
        return nomMaterno;
    }

    public void setNomMaterno(final String nomMaterno) {
        this.nomMaterno = nomMaterno;
    }

    public String getNomNombre() {
        return nomNombre;
    }

    public void setNomNombre(final String nomNombre) {
        this.nomNombre = nomNombre;
    }

    public String getNomPaterno() {
        return nomPaterno;
    }

    public void setNomPaterno(final String nomPaterno) {
        this.nomPaterno = nomPaterno;
    }

	public Moral getMoral() {
		return moral;
	}

	public void setMoral(Moral moral) {
		this.moral = moral;
	}
	
	/**
	 * @return the cveIdSubdelegacion
	 */
	public Long getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	/**
	 * @param cveIdSubdelegacion the cveIdSubdelegacion to set
	 */
	public void setCveIdSubdelegacion(Long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public boolean isCuentaRLConActosDeAdmonDominio() {
		return cuentaRLConActosDeAdmonDominio;
	}

	public void setCuentaRLConActosDeAdmonDominio(
			boolean cuentaRLConActosDeAdmonDominio) {
		this.cuentaRLConActosDeAdmonDominio = cuentaRLConActosDeAdmonDominio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPasswordConfirmacion() {
		return passwordConfirmacion;
	}

	public void setPasswordConfirmacion(String passwordConfirmacion) {
		this.passwordConfirmacion = passwordConfirmacion;
	}

	public String getCorreoConfirmacion() {
		return correoConfirmacion;
	}

	public void setCorreoConfirmacion(String correoConfirmacion) {
		this.correoConfirmacion = correoConfirmacion;
	}
	
	
   
}