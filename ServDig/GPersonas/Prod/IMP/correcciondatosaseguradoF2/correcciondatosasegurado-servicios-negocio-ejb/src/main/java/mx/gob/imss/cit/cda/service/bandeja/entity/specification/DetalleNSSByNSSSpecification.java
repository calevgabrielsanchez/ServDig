/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import org.hibernate.SQLQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author antonio
 */
public class DetalleNSSByNSSSpecification extends BaseSpecification {
  private final String nss;
  public DetalleNSSByNSSSpecification(String nss){
    this.nss = nss;
  }
  
  @Override
  public String prepareSQL(){
    return " ddnc.num_nss in :nss ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    List<String> listNss = new ArrayList<String>(Arrays.asList(this.nss.trim().
              split(",")));    
    query.setParameterList("nss", listNss);
  }

  
}
