package mx.gob.imss.ctirss.correccion.invitacion.service.ejb.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.invitacion.InvitacionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.dao.DeteccionDAOLocal;
import mx.gob.imss.ctirss.correccion.folio.service.ejb.FoliadorServiceLocal;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.correccion.invitacion.service.dao.InvitacionDAOLocal;
import mx.gob.imss.ctirss.correccion.invitacion.service.ejb.InvitacionServiceRemote;
import mx.gob.imss.ctirss.correccion.model.CrtCorrPromInvita;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao.PromocionDAOLocal;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao.SolicitudCorreccionDAOLocal;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import org.apache.log4j.Logger;


@Stateless(name="invitacionService", mappedName = "invitacionService")
public class InvitacionServiceBean <T extends AbstractModel> extends AbstractService implements InvitacionServiceRemote<T> {
	
	private static final Logger LOGGER = Logger.getLogger(InvitacionServiceBean.class);
	
	@EJB InvitacionDAOLocal<T> daoInvitacion;
	@EJB FoliadorServiceLocal foliador;
	@EJB PromocionDAOLocal<CrtPromocion> promocionDAO;
	@EJB PromocionDAOLocal promocionDAOGen;
	@EJB DeteccionDAOLocal<CrtDeteccion> deteccionDAO;	
	@EJB SolicitudCorreccionDAOLocal daoSolicitudCorreccion;
	
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params){
		return daoInvitacion.pagina(params);
	}

	public CrtInvitacion validaInvitacionExistente(Long patronPK,Date periodoInicial, Date periodoFinal) {
		return daoInvitacion.validaInvitacion(patronPK,periodoInicial,periodoFinal);
	}

	@Override
	public CrtInvitacion guardar(CrtInvitacion invitacion) {
		UserSession user = invitacion.getUsuarioFirmado();
		TipoCorreccion tipoCorreccion = null;
		if(invitacion.getTipoPrograma() != null && !invitacion.getTipoPrograma().equals("")){
			if(invitacion.getTipoPrograma().equals(TipoCorreccion.EXHORTO_DE_LO_ORDINARIO.getIdAsString()) || invitacion.getTipoPrograma().equals(TipoCorreccion.SALARIO_BASE_DE_COTIZACION.getIdAsString())){  // CI
				tipoCorreccion = TipoCorreccion.INVITACION_CI;
			}else if(invitacion.getTipoPrograma().equals(TipoCorreccion.SATIC_B.getIdAsString()) || invitacion.getTipoPrograma().equals(TipoCorreccion.EXHORTO_DE_CONSTRUCCION.getIdAsString())
					 || invitacion.getTipoPrograma().equals(TipoCorreccion.CONTROL_DE_FUENTES_EXTERNAS_DE_INFORMACION_ORDINARIO.getIdAsString()) || invitacion.getTipoPrograma().equals(TipoCorreccion.CONTROL_DETECCION_ORDINARIO.getIdAsString())){  // CCI
				tipoCorreccion = TipoCorreccion.INVITACION_CCI;
			}
		}
		if(invitacion.getCveTipocorr() != null){
			if(invitacion.getCveTipocorr().equals(TipoCorreccion.INVITACION_CI.getIdAsString())){
				tipoCorreccion = TipoCorreccion.INVITACION_CI;
			}
			if(invitacion.getCveTipocorr().equals(TipoCorreccion.INVITACION_CCI.getIdAsString())){
				tipoCorreccion = TipoCorreccion.INVITACION_CCI;
			}
		}
		if(invitacion.getNuFolioInvitacion() == null || invitacion.getNuFolioInvitacion().equals("")){
			String folio = this.foliador.recuperarSiguienteFolio(Long.valueOf(user.getCveCodigoDelegacion()), Long.valueOf(user.getCveCodigoSubDelegacion()), invitacion.getFecFechaemision(), tipoCorreccion);
			invitacion.setNuFolioInvitacion(folio);
		}
		
		return daoInvitacion.guardar(invitacion);
	}
	
	/**
	 * @author CesarAgustin
	 * @version 1.0.0
	 * @since 02/07/2012
	 */
	@Override
	public List<CrtInvitacion> consultaInvitacionesPorFiltros(
			InvitacionSeguimientoVO filtros) {
		
		LOGGER.info("/**** Servicio para obtener las invitaciones para su seguimiento con ID :: ");
		
		List<CrtInvitacion> listInvitaciones = daoInvitacion.consultaInvitacionesSeguimiento(filtros);
		CrtPromocion promocion = new CrtPromocion();
		CrtDeteccion deteccion = new CrtDeteccion();
		
		for (CrtInvitacion itemBD : listInvitaciones) {
			if (itemBD.getCvePromocion()!=null) {
				promocion.setCvePromocion(Long.valueOf(itemBD.getCvePromocion().longValue()));
				itemBD.setFolioAntecedente(promocionDAO.consultaPorClave(promocion).getNuFoliopromocion());
			} else if (itemBD.getCveDeteccion()!=null) {	
				deteccion.setCveDeteccion(Long.valueOf(itemBD.getCveDeteccion().longValue()));
				itemBD.setFolioAntecedente(deteccionDAO.consultaPorClave(deteccion).getNuFoliodeteccion());
			}
			
			if (itemBD.getFecFechaemision()!=null) {
				itemBD.setFechaEmision(Functions.dateToString2(itemBD.getFecFechaemision()));		
			}					
			
		}
		return 	listInvitaciones;	
	}
	
	/**
	 * Metodo para realizar la actualizacion del registro invitacion, almacendando la informacion
	 * del seguimiento
	 * @author CesarAgustin
	 * @version 1.0.0
	 * @since 05/07/2012
	 */
	@Override
	public Boolean actualizaSeguimientoInvitacion(CrtInvitacion invitacion) {
		
		CrtInvitacion invitacionBD = daoInvitacion.guardar(invitacion);
		
		if (invitacionBD.getCveInvitacion()!=null) {
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	
	@Override
	public String buscaFolio(String tipoPrograma) {
		return daoInvitacion.buscaFolio(tipoPrograma);
	}

	@Override
	public List<CrtNroFolio> obtieneFolioPromocion(Long Del,Long SubDel, String cad, String fecha){
		return daoInvitacion.obtieneFolioPromocion(Del, SubDel, cad, fecha);
	}

	@Override
	public DatosSalidaPaginador<T> paginaInvitacion(DatosEntradaPaginador<T> params) {
		return daoInvitacion.paginaInvitacion(params);
	}

	@Override
	public T consultaPorClave(T model) {
		return daoInvitacion.consultaPorClave(model);
	}

	@Override
	public T obtieneSATIC(String numObra) {
		return daoInvitacion.obtieneSATIC(numObra);
	}

	@Override
	public T consultaPorFolio(T model) {
		return daoInvitacion.consultaPorFolio(model);
	}

	@Override
	public T obtieneUbicacionPatron(T model) {
		return daoInvitacion.obtieneUbicacionPatron(model);
	}

	/**
	 * Metodo para obtener una solicitud de correccion
	 * con una clave de invitacion
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */	
	@Override
	public CrtSolicitudcorr obtieneInvitaSolCorr(BigDecimal cveInvitacion) {
		CrtCorrPromInvita corrProminvita = new CrtCorrPromInvita();
		corrProminvita.setCrtInvitacion(new CrtInvitacion());
		corrProminvita.getCrtInvitacion().setCveInvitacion(cveInvitacion);
		
		corrProminvita = (CrtCorrPromInvita)promocionDAOGen.consultaCorrPromInvita(corrProminvita);
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		
		if (corrProminvita != null) {
			solicitud.setCveSolicitudCorr(corrProminvita.getCrtSolicitudcorr().getCveSolicitudCorr());
			solicitud = (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorClave(solicitud);
			LOGGER.debug("Se encontro clave de solicitud de correccion");
		} else {
			solicitud = null;
		}
		return solicitud;
	}

	/**
	 * Metodo para obtener una invitacion 
	 * con una clave de promocion
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */	
	@Override
	public CrtInvitacion obtieneInvitaPromocion(Long cvePromocion) {
		CrtCorrPromInvita corrProminvita = new CrtCorrPromInvita();
		corrProminvita.setCrtPromocion(new CrtPromocion());
		corrProminvita.getCrtPromocion().setCvePromocion(cvePromocion);
		
		corrProminvita = (CrtCorrPromInvita)promocionDAOGen.consultaCorrPromInvita(corrProminvita);
		CrtInvitacion invitacion = new CrtInvitacion();
		
		if (corrProminvita != null) {
			invitacion.setCveInvitacion(corrProminvita.getCrtInvitacion().getCveInvitacion());
			invitacion = (CrtInvitacion) this.consultaPorClave((T)invitacion);
			LOGGER.debug("Se encontro clave de invitacion");
		} else {
			invitacion = null;
		}
		return invitacion;
	}

	/**
	 * Metodo para obtener una promocion
	 * con una clave de invitacion
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */	
	@Override
	public CrtPromocion obtienePromocionInvita(BigDecimal cveInvitacion) {
		CrtCorrPromInvita corrProminvita = new CrtCorrPromInvita();
		corrProminvita.setCrtInvitacion(new CrtInvitacion());
		corrProminvita.getCrtInvitacion().setCveInvitacion(cveInvitacion);
		
		corrProminvita = (CrtCorrPromInvita)promocionDAOGen.consultaCorrPromInvita(corrProminvita);
		CrtPromocion promocion = new CrtPromocion();
		
		if (corrProminvita != null) {
			promocion.setCvePromocion(corrProminvita.getCrtPromocion().getCvePromocion());
			promocion = (CrtPromocion) promocionDAO.consultaPorClave(promocion);
			LOGGER.debug("Se encontro la clave de promocion");
		} else {
			promocion = null;
		}
		return promocion;
	}

	@Override
	public String consultaInvitacionParametros(
			CrtInvitacion crtInvitacion) {
		boolean flag=true;
		boolean flagOfici=true;
		// TODO Auto-generated method stub
		CrtInvitacion per=null;
		List<CrtInvitacion>	invitaci=daoInvitacion.consultaInvitacionPorParametros(crtInvitacion);
		for(CrtInvitacion inv:invitaci){
			if(!validaPeriodo(inv,crtInvitacion)){
				per=inv;
				flag=false;
				break;
			}else if((crtInvitacion.getNuOficioinv().trim()).equals(inv.getNuOficioinv().trim())){
				flagOfici=false;
				break;
			}
		}
		
		if(!flag){
			return "Ya existe un periodo definido del "+per.getFecPeriodoIni()+ " al "+per.getFecPeriodoFin();
		}else if(!flagOfici){
			return "El oficio de invitaci\u00f3n "+crtInvitacion.getNuOficioinv()+" ya se encuentra asociado al registro patronal";
		}else{
			return null;
		}
	}
	
	
	private boolean validaPeriodo(CrtInvitacion crtInvitacionPeriodo,CrtInvitacion invitacionValidar){
		
		boolean flagValido=true;
		Date fechaIniPeriodo=crtInvitacionPeriodo.getFecPeriodoIni();
		Date fechaFinPeriodo=crtInvitacionPeriodo.getFecPeriodoFin();
		
		
		Date fechaIniComp=Functions.stringToDate(invitacionValidar.getFechaIncial());
		Date fechaFinComp=Functions.stringToDate(invitacionValidar.getFechaFinal());
		
		
		if((fechaIniComp.compareTo(fechaIniPeriodo)>0 && fechaIniComp.compareTo(fechaFinPeriodo)<0) ||
				(fechaFinComp.compareTo(fechaIniPeriodo)>0 && fechaFinComp.compareTo(fechaFinPeriodo)<0)){
			flagValido=false;			
		}
		
		if(fechaIniPeriodo.compareTo(fechaIniComp)>0 && fechaFinPeriodo.compareTo(fechaFinComp)<0){
			flagValido=false;
		}
		
		return flagValido;
	}

}
