package mx.gob.imss.ctirss.correccion.presentacion.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.correccion.bean.DataTableSolCorreccion;
import mx.gob.imss.ctirss.correccion.bean.PresentacionCorreccionVO;
import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CorreccionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.EstudioSolicitudCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RecepcionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ResumenSeguimientoVO;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.exception.promocion.NotFoundRegistroPatronalException;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.model.PresentacionCorreccionModel;
import mx.gob.imss.ctirss.correccion.session.UserSession;

public interface PresentacionCorreccionServiceController {
	public List<DataTableSolCorreccion> buscarFoliosSolicitudCorreccionPorRegPatronal(String regPatronal, String folioCorreccion);
	public List<DataTableSolCorreccion> buscarFolioSolicitudCorrecion(String folioSolCorreccion);
	public PresentacionCorreccionModel buscarFolioDeCorreccion(Integer solicitudCorreccion);
	public CrtPresentacorr presentarCorreccion(CrtPresentacorr presentacion, UserSession usuario);
	public PresentacionCorreccionVO obtenerPresentacionCorreccion(Integer solicitudCorreccion);
	public List<ErrorValidation> validarSolicitudCorreccion(Integer solicitudCorreccion);
	
	/*Metodos para la carga promocion*/
	public Map<String, List<SelectBean>> obtenerTipoPromocionYOrigen(Integer idflujo, Integer idTipo);
	public List<SelectBean> obtenerCriteriosSeleccion(Integer idTipo, Integer idOrigen);
	public List<ErrorValidation> guardaCriterioSeleccion(Long idTipo, Long idCriterio, Long delegacion, Long subDelegacion, String registroPatronal, String usuario) throws NotFoundRegistroPatronalException;
	
	/*Metodo para la carga Deteccion*/
	public List<SegUsuarioFuncionario> cargarCensores(Long idDelegacacion, Long idSubdelegacion);
	public List<ErrorValidation> guardarDeteccion(CrtDeteccion deteccion, String delegacion, String subdelegacion,Long idSubDelegacion);
	
	/*Metodo para la carga de promocion en el selector*/
	public Map<String, List<SelectBean>> obtenerTipoPromocionYOrigenSelector(Integer idflujo, Integer idTipo);
		
	/**
	 * 
	 * @param solicitudCorr
	 * @return Lista de solicitudes de correccion obtenidad, de acuerdo a los filtros.
	 */
	public List<EstudioSolicitudCorreccionVO> consultaSolicitudesCorr(EstudioSolicitudCorreccionVO solicitudCorr);
	
	public RecepcionSeguimientoVO detalleSolicitudCorreccion(Integer idSolicitud);
	
	public Boolean guardarRecepcionSeguimiento(RecepcionSeguimientoVO clase);
	
	public List<CrtSolicitudcorr> obtenerCorrInvitacionPorCveInvitacion(BigDecimal cveInvitacion);
	
	public CorreccionSeguimientoGenericoVO llenaCorreccionMain(Integer idSolicitud);
	
	public ResumenSeguimientoVO llenaResumenSeguimiento(PresentacionCorreccionModel presentacionCorrecionModel,String registroPatronal,Integer claveSolCorr,List<?> rpsInscritos);
}
