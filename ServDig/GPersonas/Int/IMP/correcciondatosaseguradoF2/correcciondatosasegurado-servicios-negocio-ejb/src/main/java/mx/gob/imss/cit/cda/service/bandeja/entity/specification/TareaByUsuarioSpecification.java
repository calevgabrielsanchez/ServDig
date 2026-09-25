/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import org.hibernate.SQLQuery;

/**
 *
 * @author antonio
 */
public class TareaByUsuarioSpecification extends BaseSpecification {
  private final String usuario;
  public TareaByUsuarioSpecification(String usuario){
    this.usuario = usuario;
  }
  
  @Override
  public String prepareSQL(){
    return " tarusu.CVE_USUARIO= :usuario ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("usuario", this.usuario);
  }

  
}
