package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcTipoobra;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="SAC_TIPOOBRA")
@OnSearchLlavePrimaria		(atributos={"cvePkTipobra"})
@ComponentComboCampoDescripcion	(atributo ="desTipoobra")
public class CrcTipoobra extends AbstractCrcTipoobra{

}
