package mx.imss.ctirss.catalogos.model;


import javax.persistence.*;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcPerfilUsuario;



/**
 * The persistent class for the DLC_PERFIL_USUARIO database table.
 * 
 */
@Entity
@Table(name="DLC_PERFIL_USUARIO")
public class DlcPerfilUsuario extends AbstractDlcPerfilUsuario {
	private static final long serialVersionUID = 1L;
}