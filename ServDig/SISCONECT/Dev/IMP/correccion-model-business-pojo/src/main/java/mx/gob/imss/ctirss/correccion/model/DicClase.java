package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractDicClase;
import mx.gob.imss.ctirss.correccion.framework.annotations.IgnoreAtributosEnCriteria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnDeleteAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnInsertAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchBajaLogica;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnUpdateAsignaFechaSistema;

@Entity
@Table(name="DIC_CLASE")
@OnInsertAsignaFechaSistema	(atributos={"fecRegistroAlta", "fecRegistroActualizado"})
@OnUpdateAsignaFechaSistema	(atributos={"fecRegistroActualizado"})
@OnDeleteAsignaFechaSistema	(atributos={"fecRegistroBaja"})
@IgnoreAtributosEnCriteria	(atributos={"fecRegistroAlta", "fecRegistroActualizado"})
@OnSearchBajaLogica			(atributos={"fecRegistroBaja"})
@OnSearchLlavePrimaria		(atributos={"cveIdClase"})
public class DicClase extends AbstractDicClase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5478780802927838525L;

	public String imprimeObjeto(){
		return new StringBuffer().append("DicClase{")
								 .append("cveIdClase:").append(this.getCveIdClase()).append(";\n")
								 .append("desClase:").append(this.getDesClase()).append(";\n")
								 .append("fecIni:").append(this.getFecIni()).append(";\n")
								 .append("fecFin:").append(this.getFecFin()).append(";\n")
								 .append("fecRegistroActualizado:").append(this.getFecRegistroActualizado()).append(";\n")
								 .append("fecRegistroAlta:").append(this.getFecRegistroAlta()).append(";\n")
								 .append("fecRegistroBaja:").append(this.getFecRegistroBaja()).append(";\n")
								 .append("indPrimaMedia:").append(this.getIndPrimaMedia()).append(";\n")
								 .append("numGradoRiesgo:").append(this.getNumGradoRiesgo()).append(";\n")
								 .append("numPorcentaje:").append(this.getNumPorcentaje()).append(";\n")
								 .append("}")
								 .toString();
	}

}
