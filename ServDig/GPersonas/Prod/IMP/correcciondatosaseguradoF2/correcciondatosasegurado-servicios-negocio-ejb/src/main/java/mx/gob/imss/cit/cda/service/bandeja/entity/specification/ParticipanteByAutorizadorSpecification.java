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
public class ParticipanteByAutorizadorSpecification extends BaseSpecification {
  private final String autorizador;
  public ParticipanteByAutorizadorSpecification(String autorizador){
    this.autorizador = autorizador;
  }
  
  @Override
  public String prepareSQL(){
    return " tarusu.CVE_USUARIO = :autorizador ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("autorizador", this.autorizador);
  }

  
}
