package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatDelegacion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

import java.util.Set;


/**
 * The persistent class for the CGC_CATDELEGACION database table.
 * 
 */
@Entity
@Table(name="CGC_CATDELEGACION")
@OnSearchLlavePrimaria		(atributos={"cveDelegacion"})
@ComponentComboCampoDescripcion	(atributo ="descDelegacion")
public class CgcCatDelegacion extends AbstractCgcCatDelegacion {
	private static final long serialVersionUID = 1L;

	
	
}