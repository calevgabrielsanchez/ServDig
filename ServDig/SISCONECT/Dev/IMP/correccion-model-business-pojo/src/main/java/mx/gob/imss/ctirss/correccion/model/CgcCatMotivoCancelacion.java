package mx.gob.imss.ctirss.correccion.model;


import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatMotivoCancelacion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CGC_CATMOTIVOCANCELACION database table.
 * 
 */
@Entity
@Table(name="CGC_CATMOTIVOCANCELACION")
@OnSearchLlavePrimaria			(atributos={"idMotivocancelacion"})
@ComponentComboCampoDescripcion (atributo="motivocancelacion")
public class CgcCatMotivoCancelacion extends AbstractCgcCatMotivoCancelacion{
	private static final long serialVersionUID = 1L;


}