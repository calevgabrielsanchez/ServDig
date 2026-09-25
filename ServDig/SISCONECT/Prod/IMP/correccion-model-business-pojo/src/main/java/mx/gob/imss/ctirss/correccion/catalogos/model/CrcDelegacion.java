package mx.gob.imss.ctirss.correccion.catalogos.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcDelegacion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

import java.util.Set;


/**
 * The persistent class for the CRC_DELEGACION database table.
 * @deprecated Usar SacDelegacion 
 */
@Entity
@Table(name="CRC_DELEGACION")
@OnSearchLlavePrimaria (atributos={"cveDelegOrig"})
@ComponentComboCampoDescripcion (atributo="delegDesc")
public class CrcDelegacion extends AbstractCrcDelegacion {
	
}