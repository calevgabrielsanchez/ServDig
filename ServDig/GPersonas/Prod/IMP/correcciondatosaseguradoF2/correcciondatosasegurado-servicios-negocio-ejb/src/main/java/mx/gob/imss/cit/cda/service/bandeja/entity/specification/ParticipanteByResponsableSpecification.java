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
public class ParticipanteByResponsableSpecification extends BaseSpecification {
  private final String responsable;
  public ParticipanteByResponsableSpecification(String responsable){
    this.responsable = responsable;
  }
  
  @Override
  public String prepareSQL(){
    return "(SELECT TAREA.cve_usuario AS Responsable FROM (SELECT CASE tarusuBIS.cve_usuario WHEN 'BPM_ADMIN21' THEN 'SIN RESPONSABLE' ELSE tarusuBIS.cve_usuario END AS cve_usuario, tarusuBIS.cve_id_instancia FROM dit_tarea_usuario tarusuBIS WHERE tarusuBIS.cve_id_tarea=1 ORDER BY tarusuBIS.cve_id_tarea_usuario DESC) TAREA WHERE TAREA.cve_id_instancia=tarusu.cve_id_instancia AND ROWNUM =1) = :responsable ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("responsable", this.responsable);
  }

  
}
