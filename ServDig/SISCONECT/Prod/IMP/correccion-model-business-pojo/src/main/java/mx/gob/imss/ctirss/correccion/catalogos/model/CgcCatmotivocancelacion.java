package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCgcCatmotivocancelacion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CGC_CATMOTIVOCANCELACION")
@OnSearchLlavePrimaria		(atributos={"idMotivocancelacion"})
@ComponentComboCampoDescripcion	(atributo ="motivocancelacion")
public class CgcCatmotivocancelacion extends AbstractCgcCatmotivocancelacion{

}
