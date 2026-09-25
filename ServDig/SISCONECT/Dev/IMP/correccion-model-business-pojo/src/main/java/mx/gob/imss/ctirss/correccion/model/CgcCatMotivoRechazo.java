package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatMotivoRechazo;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;



/**
 * The persistent class for the CGC_CATMOTIVORECHAZO database table.
 * 
 */
@Entity
@Table(name="CGC_CATMOTIVORECHAZO")
@OnSearchLlavePrimaria			(atributos={"idMotivorechazo"})
@ComponentComboCampoDescripcion (atributo="motivorechazo")
public class CgcCatMotivoRechazo extends AbstractCgcCatMotivoRechazo {
	private static final long serialVersionUID = 1L;

	
}