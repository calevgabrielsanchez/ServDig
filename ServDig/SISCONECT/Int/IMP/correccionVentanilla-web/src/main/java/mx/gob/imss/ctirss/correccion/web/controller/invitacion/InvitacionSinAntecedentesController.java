package mx.gob.imss.ctirss.correccion.web.controller.invitacion;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtDeteccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtInvitacionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.SatPatronWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipoObra;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacionRP;
import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

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
 * @date 25/04/2012
 */

@Controller
@RequestMapping(value="/catalogo/invitacionSinAntecedentes")
@JsonIgnoreProperties(ignoreUnknown=true)
public class InvitacionSinAntecedentesController  extends AbstractController{

	@Autowired
	private ICatalogoService<CgcCatcriterioseleccion> catalogoCriteriosServiceBean;
	@Autowired
	private IPatronesService<SatPatron> patronesService;
	@Autowired
	private InvitacionService<CrtDeteccion> invitacionService;
	@Autowired
	private ICatalogoService<SatPatron> catalogoServiceBean;
	@Autowired
	private ICatalogoService<CrtInvitacionRP> invitacionRPServiceBean;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		 return "invitacion/solicitud/invitacionSinAntecedentesMain";
	}
	
	
	/**
	 * @author Enrique Duran JImenez
	 * @param aoData
	 * @return List<CgcCatcriterioseleccion>
	 * @since 02/05/2012
	 *  Metodo que llena el combo CRITERIOS DE SELECCION
	 */
	@RequestMapping(value="/cboCriterios", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatcriterioseleccion> cboCriterios(@RequestBody CgcCatcriterioseleccion aoData) {
		switch(aoData.getIdTipo().intValue()){
			case ConstantesBusiness.TIPO_CORRECCION_INVITACION_CCI:
				aoData.setIdTipo(ConstantesBusiness.TIPO_INVITACION_CCI);
				break;
			case ConstantesBusiness.TIPO_CORRECCION_INVITACION_CI:
				aoData.setIdTipo(ConstantesBusiness.TIPO_INVITACION_CI);
				break;
		}
		String query=" SELECT c.ID_CRITERIOSELECCION,c.DESC_CRITERIOSELECCION FROM CGC_CATCRITERIOSELECCION c WHERE c.ID_TIPO = "+aoData.getIdTipo()+" AND (c.ID_ORIGEN = 2 or c.ID_ORIGEN = 1) ORDER BY  C.DESC_CRITERIOSELECCION ";
		List<CgcCatcriterioseleccion> lstResult = this.catalogoCriteriosServiceBean.consultaSQL(query);
		return lstResult;
	}
	
	/**
	 * @author Enrique Duran JImenez
	 * @since 02/05/2012
	 *  Metodo que obtiene la fecha actual del sistema
	 */
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy);
	}
	
	/**
	 * @author Enrique Duran JImenez
	 * @since 02/05/2012
	 *  Metodo que obtiene la fecha actual del sistema y la limita a 45 dias antes
	 */
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.dd_mm_yyyy);
	}
	
	/**
	 * @author Enrique Duran JImenez
	 * @since 02/05/2012
	 * @param Registro Patronal
	 * @return SatPatron
	 * Metodo que valida si existe el registro patronal y regresa el objeto SAT_PATRON
	 */
	@RequestMapping(value="/validaPatron" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaPatron(@RequestBody String parametro) {
		String valor = parametro.substring(1, parametro.length() -1);
		SatPatron model = new SatPatron();
		model =  patronesService.validaRegistroPatronalWS(valor, true);
		
		return model;
	}
	
	/**
	 * @author Enrique Duran JImenez
	 * @since 02/05/2012
	 * @param Registro Patronal
	 * @return SatPatron
	 * Metodo que valida si existe el registro patronal e inserta en la BD SATBDDS01.SAT_PATRON
	 */
	@RequestMapping(value="/validaSatPatron" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaSatPatron(@RequestBody SatPatron patron) {
		
		SatPatron patronTemp = new SatPatron();
		patronTemp =  patronesService.validaRegistroPatronalWS(patron.getRegistroPatronal(), true);
		SatPatron satPatron = new SatPatron();
		if(patronTemp != null){
			// Validamos en SATIC.SAT_PATRON si existe si no lo insertamos
			satPatron = this.patronesService.getByRegistroPatronal(patronTemp.getRegistroPatronal());
			if(satPatron == null){
				// Insertamos en SatPatron
				satPatron.setRfc(patronTemp.getRfc());
				satPatron.setRazonSocial(patronTemp.getRazonSocial());
				satPatron.setCurp(patronTemp.getCurp());
				satPatron.setRegistroPatronal(patronTemp.getRegistroPatronal());
				satPatron.setFkUbicacion(patronTemp.getFkUbicacion());
				satPatron = this.catalogoServiceBean.agregar(satPatron);
			}
		}
		return satPatron;
	}

	/**
	 * @author Enrique Duran JImenez
	 * @since 02/05/2012
	 * @param CrtInvitacion
	 * @return CrtInvitacion
	 * Metodo que guarda en la base de datos el objeto CrtInvitacion generando el folio correspondiente
	 */
	@RequestMapping(value="/guardar" , method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion guardar(@RequestBody CrtInvitacion invitacion, HttpServletRequest request) {

		UserSession session = this.getUsuarioFirmado(request);
		String val=null;
		val=invitacionService.consultaInvitacionParametros(invitacion);
		if(val!=null){
			invitacion.setError(val);
			return invitacion;
		}
		
		invitacion.setFecFechaoficioinv(new Date());
		invitacion.setFecFechareg(new Date());
		invitacion.setCveUsuario(session.getCurpUsuario() != null ? session.getCurpUsuario().toString() : "");		
		invitacion.setCveFkSubdelegacion(session.getIdSubDelegacion() != null ? BigDecimal.valueOf(session.getIdSubDelegacion()) : null);
		invitacion.setFecFechaemision(Functions.stringToDate(invitacion.getFechaEmision()));		
		
		if(invitacion.getFechaIncial() != null){
			invitacion.setFecPeriodoIni(Functions.stringToDate(invitacion.getFechaIncial()));
		}
		if(invitacion.getFechaFinal() != null){
			invitacion.setFecPeriodoFin(Functions.stringToDate(invitacion.getFechaFinal()));
		}
		invitacion.setUsuarioFirmado(session);
		if(invitacion.getCveFkPatron() != null){
			SatPatron patron = this.patronesService.getById(invitacion.getCveFkPatron());
			if(patron != null){
				invitacion.setSatPatron(patron);
			}
		}
		
		List<SatPatron> lstSatPatron = new ArrayList<SatPatron>();
		
		String model = invitacion.getCveFkPatronTemp();
		
		String bandera = invitacion.getRarioRP();
		if(bandera.equals("simple")){
			invitacion.setIdVariosRPS(0L);
			
		}else if(bandera.equals("varios")){
			invitacion.setIdVariosRPS(1L);
			
			StringTokenizer tokens = new StringTokenizer(model, "$");
			while(tokens.hasMoreTokens()){
				String str =tokens.nextToken();
				if(str != null){
					SatPatron patron = this.patronesService.getById(Long.valueOf(str));
					if(patron != null){					
							lstSatPatron.add(patron);
					}
				}
	        }
			
		}
		invitacion.setCveEstatus(CatEstatus.EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION.getId());
		invitacion.setCveAuditorAsignado("");
		invitacion = this.invitacionService.guardar(invitacion);
		
		// Guardar CRT_INVITACION_RP
		if(bandera.equals("varios")){
			CrtInvitacionRP regPatronales = new CrtInvitacionRP();
			if(invitacion.getCveFkPatron() != null){
				if(lstSatPatron != null && lstSatPatron.size() > 0){
					for (Iterator iterator = lstSatPatron.iterator(); iterator.hasNext();) {
						SatPatron satPatron = (SatPatron) iterator.next();
						regPatronales.setCveInvitacion(invitacion.getCveInvitacion().longValue());
						regPatronales.setCveFkPatron(satPatron.getCvePK());
						this.invitacionRPServiceBean.agregar(regPatronales);
					}
				}
			}
		}
		return invitacion;
	}
	
	/**
	 * @author Enrique Duran JImenez
	 * @since 02/05/2012
	 * @param SatPatronWrapperDataTable
	 * @return DatosSalidaPaginador<SatPatron>
	 * Metodo que llena la lista SAT_PATRON que se mostrara en el grid
	 */
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<SatPatron> pagina(@RequestBody SatPatronWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<SatPatron> reply = new DatosSalidaPaginador<SatPatron>();

		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		String model = aoData.getoForm().getCveFkPatronTemp();
		List<SatPatron> lstSatPatron = new ArrayList<SatPatron>();
		if(! aoData.getoForm().getCveFkPatronTemp().equals("")){
			StringTokenizer tokens = new StringTokenizer(model, "$");
			while(tokens.hasMoreTokens()){
				String str =tokens.nextToken();
				if(str != null && !str.equals("null")){
					SatPatron patron = this.patronesService.getById(Long.valueOf(str));
					if(patron != null){
						if(lstSatPatron.size() > 0){
							boolean resp = true;
							for(int i = 0; i < lstSatPatron.size(); i++){
								SatPatron patronFind = lstSatPatron.get(i);
								if(patronFind.getCvePK().intValue() == patron.getCvePK().intValue()){
									resp = false;
								}
							}
							if(resp == true){
								lstSatPatron.add(patron);
							}
						}else{
							lstSatPatron.add(patron);
						}
					}
				}
	        }
		}
		
		
		if(lstSatPatron != null && lstSatPatron.size() > 0){
			reply.setAaData(lstSatPatron);
			reply.setiTotalDisplayRecords(lstSatPatron.size());
			reply.setiTotalRecords(lstSatPatron.size());
		}else{
			reply.setAaData(lstSatPatron);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		
		reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
}
