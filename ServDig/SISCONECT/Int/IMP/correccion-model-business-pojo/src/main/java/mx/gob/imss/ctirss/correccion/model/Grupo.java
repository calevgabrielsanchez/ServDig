package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractGrupo;
import mx.gob.imss.ctirss.correccion.framework.annotations.IgnoreAtributosEnCriteria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnDeleteAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnInsertAsignaFechaSistema;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchBajaLogica;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnUpdateAsignaFechaSistema;


@Entity
@Table(name="DIC_GRUPO")
@OnInsertAsignaFechaSistema	(atributos={"fecRegistroAlta", "fecRegistroActualizado"})
@OnUpdateAsignaFechaSistema	(atributos={"fecRegistroActualizado"})
@OnDeleteAsignaFechaSistema	(atributos={"fecRegistroBaja"})
@IgnoreAtributosEnCriteria	(atributos={"fecRegistroAlta", "fecRegistroActualizado"})
@OnSearchBajaLogica			(atributos={"fecRegistroBaja"})
@OnSearchLlavePrimaria		(atributos={"cveIdGrupo"})
public class Grupo extends AbstractGrupo{

}
