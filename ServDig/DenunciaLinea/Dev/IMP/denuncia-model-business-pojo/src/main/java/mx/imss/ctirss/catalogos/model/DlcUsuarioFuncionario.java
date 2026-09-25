package mx.imss.ctirss.catalogos.model;

import javax.persistence.*;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcUsuarioFuncionario;


/**
 * The persistent class for the DLC_USUARIO_FUNCIONARIO database table.
 * 
 */
@Entity
@Table(name="DLC_USUARIO_FUNCIONARIO")
public class DlcUsuarioFuncionario extends AbstractDlcUsuarioFuncionario {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private Long idPerfil;

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	
	

}