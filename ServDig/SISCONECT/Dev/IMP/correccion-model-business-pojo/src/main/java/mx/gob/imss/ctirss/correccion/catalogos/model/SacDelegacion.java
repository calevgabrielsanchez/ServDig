package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractSacDelegacion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="SAC_DELEGACION")
@OnSearchLlavePrimaria (atributos={"cvePk"})
@ComponentComboCampoDescripcion	(atributo ="nomNombre")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SacDelegacion extends AbstractSacDelegacion {

}
