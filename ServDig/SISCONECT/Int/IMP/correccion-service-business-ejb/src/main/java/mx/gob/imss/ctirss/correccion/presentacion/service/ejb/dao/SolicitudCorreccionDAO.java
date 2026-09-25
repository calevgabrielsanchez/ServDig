package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.EstudioSolicitudCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RecepcionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.model.CrcDiaInhabil;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericDAO;

public interface SolicitudCorreccionDAO extends GenericDAO<CrtSolicitudcorr, Integer> {
	public Collection<CrtSolicitudcorr> findAll(boolean onlyRootCategories);
	public List<ErrorValidation> isFolioCorreccionValidoParaPresentacion(Integer solicitudCorreccion);
	public List<SelectBean> tiposPromocionCarga(Integer idFlujo, Integer idTipo);
	public List<SelectBean> origenesPromocion();
	public List<SelectBean> criterioSeleccionPromocion(Integer idTipo, Integer idOrigen);
	public List<CrcDiaInhabil> obtenerDiasInhabiles();
	public List<Object[]> obtnerSolSeguimientoCorreccion(EstudioSolicitudCorreccionVO solicitudCorr);
	public Object[] obtenerDetalleSolCorr(Integer idSolicitud);
	public List<CrtSolicitudcorr> obtenerCorrInvitacionPorCveInvitacion(BigDecimal cveInvitacion);
	public CrtSolicitudcorr getByClaveSolCorr(Integer claveSolCorr);
	
	
}
