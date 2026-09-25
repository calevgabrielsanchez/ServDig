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
public class InstanciaBySubdelegacionSpecification extends BaseSpecification {
    private final String subdelegacion;
    public InstanciaBySubdelegacionSpecification(String subdelegacion){
        this.subdelegacion = subdelegacion;
    }

    @Override
    public String prepareSQL(){
        return " inst.DES_BDOC_INSTANCIA like :subdelegacion ";
    }

    @Override
    public void setParameter(SQLQuery query){

        query.setParameter("subdelegacion", "%subdelegacion&quot;:&quot;" + this.subdelegacion + "&quot;%");
    }

  
}
