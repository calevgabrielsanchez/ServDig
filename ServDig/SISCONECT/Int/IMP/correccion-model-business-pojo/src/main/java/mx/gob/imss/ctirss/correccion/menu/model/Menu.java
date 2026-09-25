package mx.gob.imss.ctirss.correccion.menu.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.deteccion.base.model.AbstractDeteccion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.menu.base.model.AbstractMenu;

@Entity
@Table(name="SAC_MENUITEM_TEMP")
@OnSearchLlavePrimaria		(atributos={"cvePK"})
public class Menu extends AbstractMenu{
	
	


}
