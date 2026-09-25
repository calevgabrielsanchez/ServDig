package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractDicFraccion;
import mx.gob.imss.ctirss.correccion.framework.annotations.IgnoreAtributosEnCriteria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnDeleteAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnInsertAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchBajaLogica;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnUpdateAsignaFechaSistema;


@Entity
@Table(name="DIC_FRACCION")
@OnInsertAsignaFechaSistema    (atributos={"fecRegistroAlta,fecRegistroActualizado"})
@IgnoreAtributosEnCriteria    (atributos={"fecRegistroAlta,fecRegistroActualizado"})
@OnDeleteAsignaFechaSistema    (atributos={"fecRegistroBaja"})
@OnSearchBajaLogica            (atributos={"fecRegistroBaja"})
@OnUpdateAsignaFechaSistema    (atributos={"fecRegistroActualizado"})
@OnSearchLlavePrimaria        (atributos={"cveIdFraccion"})
public class DicFraccion extends AbstractDicFraccion{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String imprimeObjeto(){
        return new StringBuffer().append("DicFraccion{")
                                 .append("cveIdFraccion:").append(this.getCveIdFraccion()).append(";\n")
                                 .append("desFraccion:").append(this.getDesFraccion()).append(";\n")
                                 .append("desActividad:").append(this.getDesActividad()).append(";\n")
                                 .append("cveIdGrupo:").append(this.getDicGrupo().getCveIdGrupo()).append(";\n")
                                 .append("numFraccion:").append(this.getNumFraccion()).append(";\n")
                                 .append("cveIdClase:").append(this.getDicClase().getCveIdClase()).append(";\n")
                                 .append("fecRegistroAlta:").append(this.getFecRegistroAlta()).append(";\n")
                                 .append("fecRegistroBaja:").append(this.getFecRegistroBaja()).append(";\n")
                                 .append("fecRegistroActualizado:").append(this.getFecRegistroActualizado()).append(";\n")
                                 .append("}")
                                 .toString();
    }

}
