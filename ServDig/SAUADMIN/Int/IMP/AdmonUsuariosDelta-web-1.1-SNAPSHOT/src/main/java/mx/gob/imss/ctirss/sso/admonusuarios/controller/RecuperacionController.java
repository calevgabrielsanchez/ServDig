package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.util.ValidationUtils;

@ManagedBean(name="recuperacionController")
@CustomScoped("#{window}")
public class RecuperacionController {
	
	@EJB
	private AdmonUsuariosSessionLocal admonUsuariosService;
	
	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;

	
	private String curp;
	private String correo;
	private String patron;
	
	//private static String CURP_PATTERN = "([A-Z-a-z]{4})([0-9]{6})([A-Z-a-z]{6})([A-Z-a-z-0-9]{1})([0-9]{1})";
	private static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public String inicializaRecuperacion()
	{
		limpia();
		return "recuperaPass";
	}	

	public String inicializaRecuperacionNew()
	{
		limpia();
		return "recuperaPass2";
	}	

	public String refresca()
	{
		limpia();
		return "recuperaPass";
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

	

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}

	public void limpia(){
		curp = "";
		correo ="";
		patron = "";
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

	public void lisCurp(ValueChangeEvent event) throws AdmonUsuariosException {
		curp = (String) event.getNewValue();
	}

	public void lisPatron(ValueChangeEvent event) throws AdmonUsuariosException {
		patron = (String) event.getNewValue();
	}

	public void lisCorreo(ValueChangeEvent event) throws AdmonUsuariosException {
		correo = (String) event.getNewValue();
	}

	
	public void inicializaRecuperacion2() {
		curp = "";
		correo = "";
		patron = "";
	}

	public String recuperaContrasena()
	{
		if(validarPatron(patron))
		{
//			if (validarEstructuraCurp(curp.toUpperCase()) == true) 
			if (ValidationUtils.isValidCurp(curp)) 
			{
				if(validarEmail(correo+"@imss.gob.mx") == true)
				{
					try {
						UsuarioDTO user = admonUsuariosService.obtenContrasena(curp.toUpperCase(), correo+"@imss.gob.mx");
						if(user!=null)
						{
							filtrosConsulta.showMsg("La contraseña se ha recuperado exitosamente y la notificación se ha envíado por correo");
							inicia();
						}
						else
						{
							filtrosConsulta.showMsg("No fue posible recuperar la contraseña favor de validar los datos de captura");
							patron = "";
						}
					} catch (Exception e) {
						System.out.println("Error al recuperar la contraseña");
					}

				}
				else
				{
					filtrosConsulta.showMsg("el correo electrónico capturado no es valido");
				}
			}
			else
			{
				filtrosConsulta.showMsg("el CURP capturado no es valido");
			}
		}
		else
		{
			filtrosConsulta.showMsg("Favor de validar el código de seguridad ingresado");
		}

		return "recuperaPass";
	}
	

	public String recuperaContrasena2()
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
							filtrosConsulta.showMsg("La contraseña se ha recuperado exitosamente y la notificación se ha envíado por correo");
							inicia();
						}
						else
						{
							filtrosConsulta.showMsg("No fue posible recuperar la contraseña favor de validar los datos de captura");
							patron = "";
						}
					} catch (Exception e) {
						System.out.println("Error al recuperar la contraseña");
					}

				}
				else
				{
					filtrosConsulta.showMsg("el correo electrónico capturado no es valido");
				}
			}
			else
			{
				filtrosConsulta.showMsg("el CURP capturado no es valido");
			}
		}
		else
		{
			filtrosConsulta.showMsg("Favor de validar el código de seguridad ingresado");
		}

		return "recuperaPass2";
	}

}
