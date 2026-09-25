package mx.gob.imss.ctirss.correccion.administracion.auditor;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface AuditorService<T extends AbstractModel> {
	
	public DatosSalidaPaginador<T> paginarPromocion(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginarInvitacion(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginarSolicitud(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginaAuditoresDisponibles(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginaCarga(DatosEntradaPaginador<T> params);
	public T agregar(T model);
	public Integer buscaPatronAnexo(Integer id);
	public T buscaPorCveUsuario(T model);
	
	public DatosSalidaPaginador<T> paginaReasignarAuditoresDisponibles(DatosEntradaPaginador<T> params);
	public T buscarAsignado(T model);
	
}
