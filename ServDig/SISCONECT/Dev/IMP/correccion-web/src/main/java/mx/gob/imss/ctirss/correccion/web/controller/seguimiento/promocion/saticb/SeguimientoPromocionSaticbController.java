/**
 * SeguimientoPromocionSaticbController.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb
 * @project correccion-web
 * @author Oscar Geman Beltran Ortega
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SeguimientoEstatusObraVO;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IObraService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;
import mx.gob.imss.ctirss.domiciliosInegi.web.controller.DomGeograficosController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Oscar Beltran Ortega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 31/05/2012
 */

@Controller						
@RequestMapping(value="/promocion/seguimiento/saticb")
public class SeguimientoPromocionSaticbController extends AbstractController {
	
	@Autowired
	private PromocionService<CrtPromocion> promocionServiceBean;
	
	@Autowired
	private PromocionService<CgcCatcriterioseleccion> promocionCriteriosServiceBean;
	
	@Autowired
	private	IPatronesService patronesService;
	
	@Autowired
	private IObraService obraService;
	
	@Autowired
	private ICatalogoService<AbstractModel> iCatalogoServiceBean;
	
	@Autowired 
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(SeguimientoPromocionSaticbController.class);
	
	
	private String getCreateForm() {
		return "promocionConsulta";
	}
	
	
	/**
	 * Metodo que consulta al inicio del seguimiento la informacion a desplegar y las condiciones de la 
	 * promocion , define el comportamiento de la pantalla junto con las pestañas
	 * @param  promocion a consultar
	 * @author Oscar Beltran Ortega
	 * @return SeguimientoPromocionSaticbVO VO con toda la informacion a desplegar
	 * @version 1.0.0
	 */
	@RequestMapping(value="/muestraSaticB")
	public @ResponseBody SeguimientoPromocionSaticbVO muestraSatic(@RequestBody CrtPromocion promocion, HttpServletResponse response,HttpServletRequest request){
		logger.info("promocion :" + promocion.getCvePromocion());
		SeguimientoPromocionSaticbVO voRespuesta = new SeguimientoPromocionSaticbVO();
		CgcCatcriterioseleccion criterios = new CgcCatcriterioseleccion();
		
		UserSession user = getUsuarioFirmado(request);
		if(promocion.getCvePromocion()>0){
			promocion = this.promocionServiceBean.consultaPorClave(promocion);	
			if(promocion != null){
				voRespuesta.setRolUsuario(consultaRolUsuario(user.getCveIdUsuario()));
				if(promocion.getIdCriterioSeleccion() != null){
					criterios.setIdCriterioseleccion(promocion.getIdCriterioSeleccion());
					criterios = this.promocionCriteriosServiceBean.consultaCriterioPorClave(criterios);
					if(criterios != null){
						promocion.setDescCriterioseleccion(criterios.getDescCriterioseleccion());
						voRespuesta.setDesCriterioSeleccion(criterios.getDescCriterioseleccion());
						voRespuesta.setNumFolioPromocion(promocion.getNuFoliopromocion());
						voRespuesta.setFechaOficioPromocion(Functions.dateToString(promocion.getFecFechaoficiopro()));
						voRespuesta.setNumOficioPromocion(promocion.getNuOficiopro());
					}
				}
				if(promocion.getCveFkPatron() != null){
					SatPatron patron = this.patronesService.getById(promocion.getCveFkPatron());
					if(patron!=null){
						voRespuesta.setRegistroPatronal(patron.getRegistroPatronal());
						voRespuesta.setNomRazonSocialPatron(patron.getRazonSocial());
						voRespuesta.setCallePatron(patron.getCalle());
						voRespuesta.setColoniaPatron(patron.getColonia());
						voRespuesta.setNumExteriorPatron(patron.getNumeroExterior());
						voRespuesta.setNumInteriorPatron(patron.getNumeroInterior());
						voRespuesta.setCodigoPostalPatron(patron.getCodigoPostal());
						voRespuesta.setCvePatron(String.valueOf(patron.getCvePK()));
					}
				}
				if(promocion.getCveNroregobraSatic() != null){

					SatObra obra = this.obraService.validaObra(promocion.getCveNroregobraSatic().toString());

					if(obra !=null && obra.getCveFkPatron() != null ){
						if(obra.getCveFkObraPrincipal()==null){
							voRespuesta.setTxtSaticb("SATIC1");
						}else{
							voRespuesta.setTxtSaticb("SATIC2");
						}
							
						voRespuesta.setRegistroObra(String.valueOf(obra.getCveNroregobra()));
						
						if(obra.getUbicacion() != null){
							voRespuesta.setCalleObra(obra.getUbicacion().getCalle());
							voRespuesta.setColoniaObra(obra.getUbicacion().getColonia());
							voRespuesta.setNumExteriorObra(obra.getUbicacion().getNumeroExterior());
							voRespuesta.setNumInteriorObra(obra.getUbicacion().getNumeroInterior());
							voRespuesta.setCodigoPostalObra(obra.getUbicacion().getCodigoPostal());
						}
							
					}
				}
				if(promocion.getFecFechaAtencion()!= null){
					SeguimientoEstatusObraVO estatusObraVO = new SeguimientoEstatusObraVO();
					estatusObraVO.setFechaAtencionOficio(Functions.dateToString(promocion.getFecFechaAtencion()));
					if(promocion.getIdRegulaObra()!=null && promocion.getIdRegulaObra().intValue() ==1){
						estatusObraVO.setRegularizarObra(true);
					}else{
						estatusObraVO.setRegularizarObra(false);
					}
					voRespuesta.setSegEstatusObraVo(estatusObraVO);
					
				}
				//si ya tiene un domicilio en DG_DOMICILIO_GEOGRAFICO
				if(promocion.getCveDomGeoPatron() != null){
					
					DgDomicilioGeografico domPatron = new DgDomicilioGeografico();
					domPatron.setDomicilioId(promocion.getCveDomGeoPatron().longValue());
					domPatron = this.domiciliosInegiServiceBean.consultaPorClave(domPatron);
					
					setDomicilioInegiSession("DOM_SATICB_PATRON_OBRA", domPatron, request);
					voRespuesta.setDomicilioGeografico(domPatron);
				}
			}
			voRespuesta.setCvePromocion(promocion.getCvePromocion().toString());
			voRespuesta.setCveEstatus(String.valueOf(promocion.getCveEstatus()));
			voRespuesta.setNombreFuncionario(user.getNombreCompleto());
			if(promocion.getFecFechanotif()!=null){
				SeguimientoSaticbTabVO seguimientoSaticbTabVO = new SeguimientoSaticbTabVO();
				seguimientoSaticbTabVO.setFechaNotificacion(Functions.dateToString(promocion.getFecFechanotif()));
				seguimientoSaticbTabVO.setObservaciones(promocion.getTxObservaciones());
				voRespuesta.setSegSaticTabVo(seguimientoSaticbTabVO);
			}
		}
		return voRespuesta;
	}
	
	
	/**
	 * Metodo que se invoca desde el tab principal del seguimiento de la promocion de SATICB
	 * alamcena la informacion de Promocion y si se modifico el domicilio lo persiste
	 * @param  seguimientoSaticbVO
	 * @author Oscar Beltran Ortega
	 * @return SeguimientoPromocionSaticbVO
	 * @version 1.0.0
	 */
	@RequestMapping(value="/actualizaPromSaticb")
	public @ResponseBody SeguimientoPromocionSaticbVO actualizaPromocionSaticb(@RequestBody SeguimientoPromocionSaticbVO seguimientoSaticbVO, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		
		if(seguimientoSaticbVO != null && seguimientoSaticbVO.getCvePromocion()!=null){
			CrtPromocion promocion = new CrtPromocion();
			promocion.setCvePromocion(Long.valueOf(seguimientoSaticbVO.getCvePromocion()));
			promocion = promocionServiceBean.consultaPorClave(promocion);
			
			if(seguimientoSaticbVO.getSegSaticTabVo()!= null && seguimientoSaticbVO.getSegSaticTabVo().getFechaNotificacion() != null){
				promocion.setFecFechanotif(Functions.stringToDate(seguimientoSaticbVO.getSegSaticTabVo().getFechaNotificacion()));	
			}
			
			promocion.setTxObservaciones(seguimientoSaticbVO.getSegSaticTabVo()!=null && seguimientoSaticbVO.getSegSaticTabVo().getObservaciones()!=null ?seguimientoSaticbVO.getSegSaticTabVo().getObservaciones(): "");
			promocion.setFecFechareg(new Date());
			promocion.setCveUsuario(String.valueOf(user.getCveIdUsuario()));
			
			
			if((Hashtable)getDomicilioInegiSession(request) != null){
				Hashtable<String,Object> hDomicilios = (Hashtable)getDomicilioInegiSession(request);
				DgDomicilioGeografico domicilioGeo = (DgDomicilioGeografico)hDomicilios.get("DOM_SATICB_PATRON_OBRA");
				
				domicilioGeo = this.domiciliosInegiServiceBean.agregar(domicilioGeo);
				logger.debug("idDomicilioGeo   " + domicilioGeo.getDomicilioId());
				promocion.setCveDomGeoPatron(new BigDecimal(domicilioGeo.getDomicilioId()));
			}
			promocion = this.promocionServiceBean.modificar(promocion);
			
			if (promocion.getTxRefDerivacion() != null  && seguimientoSaticbVO.getSegSaticTabVo() != null) {
				seguimientoSaticbVO.getSegSaticTabVo().setObservaciones(promocion.getTxRefDerivacion());	
			} 
			
		}
		
		return seguimientoSaticbVO;
	}
	

	
	/**
	 * Metodo usado por el componente de domicilio el cual sube a la session 
	 * el domicilio seleccionado/creado desde el componende de domicilios
	 * geograficos
	 * 
	 * @param 
	 * @author Oscar Beltran Ortega
	 * @return DgDomicilioGeografico
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/actualizaDomPatronObra", method = RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico actualizaDomPatronObra(@RequestBody CrtSolicitudcorr patron,HttpServletResponse response, HttpServletRequest request) {
		Hashtable domGeografico = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico domicilioGeografico = null;
		if(domGeografico!=null && !domGeografico.isEmpty())	{	 
			
			domicilioGeografico = (DgDomicilioGeografico)((Hashtable)getDomicilioInegiSession(request)).get(patron.getPatron());
			
		}
		return domicilioGeografico;
	}	


		//Crea un HashTable de domicilios
		@RequestMapping(value="/solicitudDomGeograficoPatronObra" , method=RequestMethod.GET)
		public String callDomGeograficosPatronObra(HttpServletResponse response, HttpServletRequest request, Model model) {
			
			DgDomicilioGeografico dg = new DgDomicilioGeografico();
			
			Hashtable doms = (Hashtable)getDomicilioInegiSession(request);
			
			if(doms!=null){
				DgDomicilioGeografico tmp = (DgDomicilioGeografico)doms.get("DOM_SATICB_PATRON_OBRA");
				if(tmp != null)
					dg=tmp;
			}
			
			dg.setHastableKeyDG("DOM_SATICB_PATRON_OBRA");

			return new DomGeograficosController().getCreateGenericForm(model,dg,request);
		}	
		
		//usado por el compoente de domicilio
		@RequestMapping(value="/sessionDomicilioGeografico", method=RequestMethod.POST )
		public @ResponseBody DgDomicilioGeografico almacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,HttpServletRequest request) {
			return new DomGeograficosController().almacenaSessionDomicilioInegi(domicilioInegi, request);
		}

	
		/**
		 * Metodo usado para obtener el Rol del usuario logeado en la 
		 * aplicacion
		 * 
		 * @param idUsuario
		 * @author Oscar Beltran Ortega
		 * @return rol del usuario
		 * @version 1.0.0
		 */
		public String consultaRolUsuario(Long idUsuario){
			String rol = null;
			String queryRol="select  CVE_ROL from seg_perfil_usuario WHERE CVE_ID_USUARIO = "+ idUsuario.toString();
			
			ArrayList listaFuncionarios = (ArrayList) iCatalogoServiceBean.consultaSQL(queryRol);

			rol = listaFuncionarios.get(0).toString();
			
			return rol;
		}
		
		
		/**
		 * Metodo que se encarga de actualizar los datos del seguimiento SBC
		 * @param crtPromocion
		 * @return CrtPromocion
		 * @author Oscar German Beltran Ortega
		 * @version 1.0.0
		 */	
		@RequestMapping(value="/guardarPeriodosSaticB" , method=RequestMethod.POST)
		public @ResponseBody CrtPromocion guardarPeriodosSeguimientoSaticB(@RequestBody CrtPromocion promocion, HttpServletRequest request) {
			logger.info("guardando periodos de saticb");
			UserSession user = getUsuarioFirmado(request);
			
			CrtPromocion promocionGuardar = promocionServiceBean.consultaPorClave(promocion);
			if(promocionGuardar != null){
				if(promocion.getFechaIncial() != null){
					promocionGuardar.setFecInicialDictamen(Functions.stringToDate(promocion.getFechaIncial()));
							
				}
				if(promocion.getFechaFinal() != null){
					promocionGuardar.setFecFinalDictamen(Functions.stringToDate(promocion.getFechaFinal()));
				}
				
						
				promocionGuardar.setFecFechareg(new Date());
				promocionGuardar.setCveUsuario(user.getCveIdUsuario().toString());
				//promocionGuardar.setCveAuditorAsignado(new Long(0));
				
			}
			//promocion = this.promocionServiceBean.agregar(promocionGuardar);
			promocion = this.promocionServiceBean.agregar(promocionGuardar);
			
			logger.info("fin guardando periodos de saticb");
			return promocion;
		}


}
