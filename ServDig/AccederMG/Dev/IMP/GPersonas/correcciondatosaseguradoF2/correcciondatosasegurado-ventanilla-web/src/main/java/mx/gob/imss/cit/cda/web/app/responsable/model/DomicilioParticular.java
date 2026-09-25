/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author antonio
 */
public class DomicilioParticular extends BaseModel{
  private String cp;
  private String colonia;
  private String calle;  
  private String delegacion;
  private String numeroExterior;
  private String numeroInterior;
  private String entidadFederativa;

  /**
   * @return the cp
   */
  public String getCp() {
    return cp;
  }

  /**
   * @param cp the cp to set
   */
  public void setCp(String cp) {
    this.cp = cp;
  }

  /**
   * @return the colonia
   */
  public String getColonia() {
    return colonia;
  }

  /**
   * @param colonia the colonia to set
   */
  public void setColonia(String colonia) {
    this.colonia = colonia;
  }

  /**
   * @return the calle
   */
  public String getCalle() {
    return calle;
  }

  /**
   * @param calle the calle to set
   */
  public void setCalle(String calle) {
    this.calle = calle;
  }

  /**
   * @return the delegacion
   */
  public String getDelegacion() {
    return delegacion;
  }

  /**
   * @param delegacion the delegacion to set
   */
  public void setDelegacion(String delegacion) {
    this.delegacion = delegacion;
  }

  /**
   * @return the numeroExterior
   */
  public String getNumeroExterior() {
    return numeroExterior;
  }

  /**
   * @param numeroExterior the numeroExterior to set
   */
  public void setNumeroExterior(String numeroExterior) {
    this.numeroExterior = numeroExterior;
  }

  /**
   * @return the numeroInterior
   */
  public String getNumeroInterior() {
    return numeroInterior;
  }

  /**
   * @param numeroInterior the numeroInterior to set
   */
  public void setNumeroInterior(String numeroInterior) {
    this.numeroInterior = numeroInterior;
  }

  /**
   * @return the entidadFederativa
   */
  public String getEntidadFederativa() {
    return entidadFederativa;
  }

  /**
   * @param entidadFederativa the entidadFederativa to set
   */
  public void setEntidadFederativa(String entidadFederativa) {
    this.entidadFederativa = entidadFederativa;
  }
}
