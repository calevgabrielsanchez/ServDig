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
public class InstanciaByEstadoSpecification extends BaseSpecification {

    @Override
    public String prepareSQL(){
        return " inst.CVE_ID_EDO_INSTANCIA in (:idsEdosInstancia) ";
    }

    @Override
    public void setParameter(SQLQuery query){

        List<Integer> estadosInstancia = new ArrayList<Integer>();
        estadosInstancia.add(1);
        query.setParameterList("idsEdosInstancia", estadosInstancia);
    }

  
}
