package mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

public interface DomiciliosInegiDAO<T extends AbstractModel> {
	
	public T agrega(T model);
	public void elimina(T model);	
	public T modifica(T model);
	public List<T> consulta(T model);
	public T consultaPorClave(T model);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	public DgDomicilioGeografico getDomicilioBDTU(DgDomicilioGeografico domicilio);
	

}
