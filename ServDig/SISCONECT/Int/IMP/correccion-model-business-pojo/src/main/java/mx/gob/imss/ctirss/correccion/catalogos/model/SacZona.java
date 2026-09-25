package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractSacZona;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name = "SAC_ZONA")
@OnSearchLlavePrimaria		(atributos={"cvePk"})
@ComponentComboCampoDescripcion	(atributo ="nomNombre")
public class SacZona extends AbstractSacZona{

}
