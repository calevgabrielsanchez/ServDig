package mx.gob.imss.ctirss.correccion.invitacion.service.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.invitacion.InvitacionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;

public interface InvitacionService<T extends AbstractModel> {
	
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	public CrtInvitacion validaInvitacionExistente(Long patronPK,Date periodoInical, Date PeriodoFinal);
	public CrtInvitacion guardar(CrtInvitacion invitacion);
	public String buscaFolio(String tipoPrograma);
	public List<CrtNroFolio> obtieneFolioPromocion(Long Del,Long SubDel, String cad,String fecha);
	// metodos para cancelar invitacion
	public DatosSalidaPaginador<T> paginaInvitacion(DatosEntradaPaginador<T> params);
	public T consultaPorClave(T model);
	public T obtieneSATIC(String numObra);
	
	public T consultaPorFolio(T model);
	
	public List<CrtInvitacion> consultaInvitacionesPorFiltros(InvitacionSeguimientoVO filtros);
	public Boolean actualizaSeguimientoInvitacion(CrtInvitacion invitacion);
	
	public T obtieneUbicacionPatron(T model);
	public CrtSolicitudcorr obtieneInvitaSolCorr(BigDecimal cveInvitacion) ;
	public CrtInvitacion obtieneInvitaPromocion(Long cvePromocion);
	public CrtPromocion obtienePromocionInvita(BigDecimal cveInvitacion);
	public String  consultaInvitacionParametros(CrtInvitacion crtInvitacion);
	
}