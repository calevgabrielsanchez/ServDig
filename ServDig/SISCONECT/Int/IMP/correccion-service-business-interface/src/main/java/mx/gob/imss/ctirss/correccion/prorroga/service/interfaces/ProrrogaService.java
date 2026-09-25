package mx.gob.imss.ctirss.correccion.prorroga.service.interfaces;

import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


public interface ProrrogaService <T extends AbstractModel> {
	
	public Map agregar(T model);
	public List<T> consultar(T model);
	public T consultarPorFolio(T model);
	public DatosSalidaPaginador<T> pagina(String registroPatronal);
	// Metodos para Autorizacion de prorroga     EDJ
	public DatosSalidaPaginador<T> paginaAP(DatosEntradaPaginador<T> params);
	public List<T> llenarStatus();
	public T buscaTipoCorr(String cveTipoCorr);
	public void guardar(List<T> lst);

}
