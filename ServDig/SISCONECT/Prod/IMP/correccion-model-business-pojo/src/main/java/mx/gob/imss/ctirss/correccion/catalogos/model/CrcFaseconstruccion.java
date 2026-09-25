package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcFaseconstruccion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="SAC_FASECONSTRUCCION")
@OnSearchLlavePrimaria		(atributos={"cvePkFaseconst"})
@ComponentComboCampoDescripcion	(atributo ="desFaseconstruccion")
public class CrcFaseconstruccion extends AbstractCrcFaseconstruccion{

}
