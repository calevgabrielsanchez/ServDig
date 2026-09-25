/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import org.hibernate.SQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antonio
 */
public class TareaByIdTareaSpecification extends BaseSpecification {

  @Override
  public String prepareSQL(){
    return " tarusu.CVE_ID_TAREA IN (:idsTarea) ";
  }
  
  @Override
  public void setParameter(SQLQuery query){

    List<Integer> idsTarea = new ArrayList<Integer>();
    idsTarea.add(1);
    idsTarea.add(2);

    query.setParameterList("idsTarea", idsTarea);
  }

  
}
