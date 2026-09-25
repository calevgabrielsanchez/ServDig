package mx.gob.imss.ctirss.correccion.invitacion.service.dao;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;

public interface InvitacionDAO<T extends AbstractModel> {
	
	public CrtInvitacion validaInvitacion(Long patron,Date periodoInicial, Date periodoFinal);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	CrtInvitacion guardar(CrtInvitacion invitacion);
	String buscaFolio(String tipoPrograma);
	public List<CrtNroFolio> obtieneFolioPromocion(Long Del,Long SubDel, String cad, String fecha);
	// Metodos para cancelacion de invitacion
	public DatosSalidaPaginador<T> paginaInvitacion(DatosEntradaPaginador<T> params);
	public T consultaPorClave(T model);
	public T obtieneSATIC(String numObra);
	
	public T consultaPorFolio(T model);
	public T obtieneUbicacionPatron(T model);
}
