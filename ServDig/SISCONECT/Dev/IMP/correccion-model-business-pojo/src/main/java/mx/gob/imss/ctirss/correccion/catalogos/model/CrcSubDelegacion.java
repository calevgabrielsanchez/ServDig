package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
/**
 * @deprecated Usar SacDelegacion
 * @author vaguirre
 *
 */
@Entity
@Table(name="CRC_SUBDELEGACION")
@OnSearchLlavePrimaria (atributos={"id.sdelegOrig"})
@ComponentComboCampoDescripcion (atributo="sdelegDesc")
public class CrcSubDelegacion extends AbstractCrcSubdelegacion{

}
