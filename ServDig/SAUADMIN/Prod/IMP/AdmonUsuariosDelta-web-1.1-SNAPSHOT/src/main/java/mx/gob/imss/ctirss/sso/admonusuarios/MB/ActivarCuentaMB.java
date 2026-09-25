package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.ActivaCuentaServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;


@ManagedBean(name="activaCuentaaMB")
@CustomScoped("#{window}")
public class ActivarCuentaMB {
	@EJB
	private ActivaCuentaServiceLocal activaCuentaService;
	@EJB
	private AdmonUsuariosSessionLocal admonUsuarios;

	private String cuenta = "";
	public static long ESTATUS_AUTORIZADA = 2;
	
	
	public void activaCuenta(){
		try {
			ActivaCuentaDTO activa = activaCuentaService.obtenActivaCuentaByClaveMD5(cuenta);
			if(activa!=null)
			{
				Date hoy = new Date();
				if(activa.getFechaVigencia().getTime()>hoy.getTime())
				{
					admonUsuarios.activarUsuario(activa.getSolicitud().getDesUsrCurp());
					activa.setEstatus(new EstatusDTO(ESTATUS_AUTORIZADA));
					activa.setFechaActiva(hoy);
					activaCuentaService.actualizaEstatusActivaCuenta(activa);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getCuenta() {
		return cuenta;
	}


	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	
}


