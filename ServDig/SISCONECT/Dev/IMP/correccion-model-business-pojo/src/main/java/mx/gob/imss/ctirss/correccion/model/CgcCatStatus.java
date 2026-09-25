package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatStatus;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

import java.util.Set;


/**
 * The persistent class for the CGC_CATSTATUS database table.
 * 
 */
@Entity
@Table(name="CGC_CATSTATUS")
@OnSearchLlavePrimaria			(atributos={"idStatus"})
@ComponentComboCampoDescripcion (atributo="descStatus")
public class CgcCatStatus extends AbstractCgcCatStatus {
	private static final long serialVersionUID = 1L;



}