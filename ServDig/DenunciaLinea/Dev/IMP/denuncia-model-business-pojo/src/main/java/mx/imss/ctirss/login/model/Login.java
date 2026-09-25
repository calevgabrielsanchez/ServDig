package mx.imss.ctirss.login.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.imss.ctirss.login.base.model.AbstractLogin;

@Entity
@Table(name="SAC_MENUITEM_TEMP")
@OnSearchLlavePrimaria		(atributos={"cvePK"})
public class Login extends AbstractLogin{
	
	


}
