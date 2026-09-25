/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.common.model;

import java.util.List;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author antonio
 */
public class UserProfile extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8211715221932137334L;

    private String usuario;
    private String subdelegacion;
    private String perfil;
    private String perfilDescripcion;
    private Long idPersona;
    private Long idSubdelegacion;
    private Long idDelegacion;
    private String nombreCompleto;
    private String[] sistemas;
    private String password;
    private List<String> roles;

    public String getPerfilInterno() {
        String perfil = null;
        if(perfil != null){
           RolUsuarioEnum  rol = RolUsuarioEnum.fromRol(perfil);
           perfil = rol.getDescripcion();
        }
        return perfil;
    }
    
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *            the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the subdelegacion
     */
    public String getSubdelegacion() {
        return subdelegacion;
    }

    /**
     * @param subdelegacion
     *            the subdelegacion to set
     */
    public void setSubdelegacion(String subdelegacion) {
        this.subdelegacion = subdelegacion;
    }

    /**
     * @return the perfil
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * @param perfil
     *            the perfil to set
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdSubdelegacion() {
        return idSubdelegacion;
    }

    public void setIdSubdelegacion(Long idSubdelegacion) {
        this.idSubdelegacion = idSubdelegacion;
    }

    public Long getIdDelegacion() {
        return idDelegacion;
    }

    public void setIdDelegacion(Long idDelegacion) {
        this.idDelegacion = idDelegacion;
    }

    public String getPerfilDescripcion() {
        return perfilDescripcion;
    }

    public void setPerfilDescripcion(String perfilDescripcion) {
        this.perfilDescripcion = perfilDescripcion;
    }

    /**
     * @return nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto
     *            the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String[] getSistemas() {
        return sistemas != null ? (String[]) sistemas.clone() : null;
    }

    public void setSistemas(String[] sistemas) {
        this.sistemas = sistemas != null ? (String[] ) sistemas.clone() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
