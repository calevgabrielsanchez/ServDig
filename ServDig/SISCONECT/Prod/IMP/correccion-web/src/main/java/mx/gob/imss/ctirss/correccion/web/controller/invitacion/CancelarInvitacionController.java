package mx.gob.imss.ctirss.correccion.web.controller.invitacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtInvitacionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Enrique Duran Jimenez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 01/02/2012
 */
@Controller
@RequestMapping(value = "/catalogo/cancelarInvitacion")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelarInvitacionController extends AbstractController {

	@Autowired
	private InvitacionService<CrtInvitacion> invitacionService;
	@Autowired
	private ICatalogoService<SegUsuario> catalogoOrigenServiceBean;

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("crtInvitacion", new CrtInvitacion());
		return "invitacion/cancelar/cancelarInvitacionMain";
	}

	@RequestMapping(value = "/paginar", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<CrtInvitacion> pagina(
			@RequestBody CrtInvitacionWrapperDataTable aoData,
			HttpServletRequest request) {

		DatosEntradaPaginador<CrtInvitacion> send = new DatosEntradaPaginador<CrtInvitacion>();
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());

		DatosSalidaPaginador<CrtInvitacion> reply = new DatosSalidaPaginador<CrtInvitacion>();

		if (aoData.getoForm().getFechaIncial() != null
				&& aoData.getoForm().getFechaFinal() != null
				&& !send.getsSearch().equals("-1")) {
			reply = this.invitacionService.paginaInvitacion(send);
			if (reply != null && reply.getAaData().size() > 0) {
				for (Iterator<?> iterator = reply.getAaData().iterator(); iterator
						.hasNext();) {
					CrtInvitacion type = (CrtInvitacion) iterator.next();
					if (type.getFecFechaemision() != null) {
						SimpleDateFormat formato = new SimpleDateFormat(
								"dd-MM-yyyy");
						type.setFechaEmision(formato.format(type
								.getFecFechaemision()));
					}
				}
			}

		} else {
			List<CrtInvitacion> lstInvitacion = new ArrayList<CrtInvitacion>();
			reply.setAaData(lstInvitacion);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());

		return reply;
	}

	@RequestMapping(value = "/buscaInvitacion", method = RequestMethod.POST)
	public @ResponseBody
	CrtInvitacion buscaInvitacion(@RequestBody CrtInvitacion model) {

		model = this.invitacionService.consultaPorClave(model);

		return model;
	}

	@RequestMapping(value = "/llenaAuditor", method = RequestMethod.POST)
	public @ResponseBody
	List<SegUsuario> llenaAuditor(@RequestBody CrtInvitacion model,
			HttpServletRequest request) {

		UserSession session = this.getUsuarioFirmado(request);
		Long del = session.getIdDelegacion();
		Long subDel = session.getIdSubDelegacion();

		List<SegUsuario> lstResult = this.catalogoOrigenServiceBean
				.consultaSQL(" select u.CVE_ID_USUARIO, u.NOM_NOMBRE from SEG_USUARIO u inner join SEG_USUARIO_FUNCIONARIO uf ON u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO where u.CVE_ID_USUARIO in ( select pu.CVE_ID_USUARIO from SEG_PERFIL_USUARIO pu where pu.CVE_ROL in ( select r.CVE_ROL from SEG_ROL r where r.CVE_ROL = 2)) and uf.CVE_ID_DELEGACION = "
						+ del
						+ " and uf.CVE_ID_SUBDELEGACION = "
						+ subDel
						+ " order by u.NOM_NOMBRE ");

		return lstResult;
	}

	@RequestMapping(value = "/guardarCancelacion", method = RequestMethod.POST)
	public @ResponseBody
	CrtInvitacion guardarCancelacion(@RequestBody CrtInvitacion model) {

		CrtInvitacion modelTemp = new CrtInvitacion();
		modelTemp = this.invitacionService.consultaPorClave(model);
		modelTemp.setFecCancelacion(new Date());
		modelTemp.setIdMotivoCancelacion(model.getIdMotivoCancelacion());
		modelTemp.setNumOficioCancelacion(model.getNumOficioCancelacion());
		modelTemp.setCveAuditorAsignado(model.getCveAuditorAsignado());
		model = this.invitacionService.guardar(modelTemp);

		return model;
	}

}
