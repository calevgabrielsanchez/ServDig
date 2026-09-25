package mx.gob.imss.ctirss.correccion.login.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.login.base.model.AbstractLogin;

@Entity
@Table(name="SAC_MENUITEM_TEMP")
@OnSearchLlavePrimaria		(atributos={"cvePK"})
public class Login extends AbstractLogin{
	
	


}
