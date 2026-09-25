package mx.imss.ctirss.menu.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.imss.ctirss.deteccion.base.model.AbstractDeteccion;
import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.imss.ctirss.menu.base.model.AbstractMenu;

@Entity
@Table(name="SAC_MENUITEM_CONSULTA")
@OnSearchLlavePrimaria		(atributos={"cvePK"})
public class Menu extends AbstractMenu{
	
	


}
