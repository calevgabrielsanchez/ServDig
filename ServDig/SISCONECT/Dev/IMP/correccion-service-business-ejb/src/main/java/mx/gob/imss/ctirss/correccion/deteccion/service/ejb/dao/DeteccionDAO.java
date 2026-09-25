package mx.gob.imss.ctirss.correccion.deteccion.service.ejb.dao;

import java.math.BigDecimal;
import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

public interface DeteccionDAO<T extends AbstractModel> {
	
	public T agrega(T model);
	public void elimina(T model);	
	public T modifica(T model);
	public List<T> consulta(T model);
	public T consultaPorClave(T model);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> valida(DatosEntradaPaginador<T> params,List<DgDomicilioGeografico> domicilios);
	public DatosSalidaPaginador<T> validacionObraSatic(DatosEntradaPaginador<T> params);
	public CrtNroFolio obtieneFolios(Long Del,Long SubDel, String fecha, Integer tipo);
	public T consultarXIdDom(T model);
	public List<T> validaNuReporte(T model);
	public T buscaUbicacion(T model);
}
