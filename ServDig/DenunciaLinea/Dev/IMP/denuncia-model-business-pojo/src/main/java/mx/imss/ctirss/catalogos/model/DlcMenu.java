package mx.imss.ctirss.catalogos.model;


import javax.persistence.*;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcMenu;



/**
 * The persistent class for the DLC_MENU database table.
 * 
 */
@Entity
@Table(name="DLC_MENU")
public class DlcMenu extends AbstractDlcMenu {
	
	private static final long serialVersionUID = 1L;
	
	@Transient
	private Long idPerfil;

	public void setIdUsuario(Long cveIdUsuario) {
		// TODO Auto-generated method stub
		
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

}