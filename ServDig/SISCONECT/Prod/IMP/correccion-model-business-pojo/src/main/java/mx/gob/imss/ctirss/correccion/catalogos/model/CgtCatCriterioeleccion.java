package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCgtCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchFiltro;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchFiltro2;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CGC_CATCRITERIOSELECCION")
@OnSearchLlavePrimaria(atributos="idCriterioseleccion")
@ComponentComboCampoDescripcion(atributo="descCriterioseleccion")
@OnSearchFiltro(atributo="idOrigen")
@OnSearchFiltro2(atributo="idTipo")
public class CgtCatCriterioeleccion extends AbstractCgtCatcriterioseleccion{

}
