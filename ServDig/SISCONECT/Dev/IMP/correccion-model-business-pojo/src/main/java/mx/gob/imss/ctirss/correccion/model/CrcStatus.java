package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrcStatus;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OrderComboBy;


@Entity
@Table(name="CRC_STATUS")
@OnSearchLlavePrimaria(atributos="cveStatus")
@ComponentComboCampoDescripcion(atributo="txDescripcion")
@OrderComboBy(atributos="txDescripcion")
public class CrcStatus extends AbstractCrcStatus{

	public static Integer SOLICITADA=1;
	public static Integer APROBADA=2;
	public static Integer RECHAZADA=3;
	public static Integer PRESENTADA=4;
	
	

}
