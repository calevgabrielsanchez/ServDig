package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.util.PasswordUtil;
import mx.gob.imss.ctirss.sso.util.ValidationUtils;

@ManagedBean(name="recuperacionConstrasena")
@CustomScoped("#{window}")
public class RecuperacionContrasena {
	
	@EJB
	private AdmonUsuariosSessionLocal admonUsuariosService;
	
	
	private String curp;
	private String correo;
	private String msgDesc;
	private String patron;
	private boolean msg;
	
	//private static String CURP_PATTERN = "([A-Z-a-z]{4})([0-9]{6})([A-Z-a-z]{6})([A-Z-a-z-0-9]{1})([0-9]{1})";
	private static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	
	public void init(){

		if (!FacesContext.getCurrentInstance().isPostback()) {
			try {
					HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
					FacesContext.getCurrentInstance().getExternalContext().redirect("recuperacionContrasenaSession.xhtml");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	
	
	public String inicializaRecuperacion()
	{
		limpia();
		return "recuperaContrasena";
	}

	public String refresca()
	{
		limpia();
		return "recuperaContrasena";
	}

	public String getCurp() {
		return curp;
	}


	public void setCurp(String curp) {
		this.curp = curp;
	}


	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	public boolean isMsg() {
		return msg;
	}

	public void setMsg(boolean msg) {
		this.msg = msg;
	}
	
	
	public AdmonUsuariosSessionLocal getAdmonUsuariosService() {
		return admonUsuariosService;
	}

	public void setAdmonUsuariosService(
			AdmonUsuariosSessionLocal admonUsuariosService) {
		this.admonUsuariosService = admonUsuariosService;
	}

	public String getPatron() {
		return patron;
	}

	public void setPatron(String patron) {
		this.patron = patron;
	}


	public void showMsg(String mensaje)
	{
		msgDesc = mensaje;
		msg = true;
	}

	public void limpia(){
		msg = false;
		msgDesc = "";
		curp = "";
		correo ="";
	}

	public void inicia(){
		curp = "";
		correo ="";
		patron = "";
		
	}

//	public boolean validarEstructuraCurp(String curp) {
//		if (curp == null || "".equals(curp)||curp.length()<18) 
//			return false;
//
//		boolean res = curp.matches(CURP_PATTERN);
//		return res;
//	}

	public boolean validarEmail(String email) {
		if (email == null || "".equals(email)) 
			return false;

		boolean res = email.matches(EMAIL_PATTERN);
		return res;
	}

	public boolean validarPatron(String pat) {
		if (pat == null || "".equals(pat)) 
			return false;
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		boolean res = SimpleImageCaptchaServlet.validateResponse(request, pat);
		return res;
	}

	public String recuperaContrasena()
	{
		if(validarPatron(patron))
		{
			//if (validarEstructuraCurp(curp.toUpperCase()) == true) 
			if (ValidationUtils.isValidCurp(curp)) 
			{
				if(validarEmail(correo+"@imss.gob.mx") == true)
				{
					try {
						UsuarioDTO user = admonUsuariosService.obtenContrasena(curp.toUpperCase(), correo+"@imss.gob.mx");
						if(user!=null)
						{
							showMsg("La contraseña se ha recuperado exitosamente y la notificación se ha envíado por correo");
							inicia();
						}
						else
						{
							showMsg("No fue posible recuperar la contraseña favor de validar los datos de captura");
							patron = "";
						}
					} catch (Exception e) {
						System.out.println("Error al recuperar la contraseña");
					}
				}
				else
				{
					showMsg("el correo electrónico capturado no es valido");
				}
			}
			else
			{
				showMsg("el CURP capturado no es valido");
			}
		}
		else
		{
			showMsg("Favor de validar el código de seguridad ingresado");
		}
		return "recuperaContrasena";
	}
	
	public void hide(){
		msgDesc = "";
		msg = false;
	}
	
	
}
