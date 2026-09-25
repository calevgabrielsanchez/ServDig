package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;


import mx.gob.imss.ctirss.correccion.base.model.AbstractDicGrupo;
import mx.gob.imss.ctirss.correccion.framework.annotations.IgnoreAtributosEnCriteria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnDeleteAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnInsertAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchBajaLogica;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnUpdateAsignaFechaSistema;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@Entity
@Table(name="DIC_GRUPO")
@OnInsertAsignaFechaSistema    (atributos={"fecRegistroAlta,fecRegistroActualizado"})
@IgnoreAtributosEnCriteria    (atributos={"fecRegistroAlta,fecRegistroActualizado"})
@OnDeleteAsignaFechaSistema    (atributos={"fecRegistroBaja"})
@OnSearchBajaLogica            (atributos={"fecRegistroBaja"})
@OnUpdateAsignaFechaSistema    (atributos={"fecRegistroActualizado"})
@OnSearchLlavePrimaria        (atributos={"cveIdGrupo"})
public class DicGrupo extends AbstractDicGrupo{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String imprimeObjeto(){
        return new StringBuffer().append("DicGrupo{")
                                 .append("cveIdGrupo:").append(this.getCveIdGrupo()).append(";\n")
                                 .append("desGrupo:").append(this.getDesGrupo()).append(";\n")
                                 .append("Division:").append(this.getDicDivision()).append(";\n")
                                 .append("numGrupo:").append(this.getNumGrupo()).append(";\n")
                                 .append("fecRegistroAlta:").append(this.getFecRegistroAlta()).append(";\n")
                                 .append("fecRegistroBaja:").append(this.getFecRegistroBaja()).append(";\n")
                                 .append("fecRegistroActualizado:").append(this.getFecRegistroActualizado()).append(";\n")
                                 .append("}")
                                 .toString();
    }

}
