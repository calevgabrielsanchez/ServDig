package mx.gob.imss.ctirss.correccion.promocion.service.interfaces;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;

public interface PromocionService<T extends AbstractModel> {
	
	public T agregar(T model);		
	public List<T> consultar(T model);
	public T consultaPorClave(T model);
	public T modificar(T model);
	public void elimina(T model);
	public CrtDeteccion obtieneDeteccionporClave(CrtDeteccion model);
	public CrtDeteccion obtieneObraporNumeroRegistro(CrtDeteccion obra);
	public List<CrtDeteccion> obtieneObraporNumReg(CrtDeteccion obra);	
	public DatosSalidaPaginador<T> paginaDeteccion(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginaSaticB(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginaSaticBSinIncidencia(DatosEntradaPaginador<T> params);	
	public DatosSalidaPaginador<T> paginaExConstruccion(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> consultaPromocion(DatosEntradaPaginador<T> params);
	public List<CrtNroFolio> obtieneFolioPromocion(Long Del,Long SubDel, String cad,String fecha);
	public String obtieneFolioInvitacion(Long Del,Long SubDel, String cad, String fecha);
	public CrtPromocion validaPromocionExistente(Long patronPK,Date periodoInicial,Date periodoFinal);	
	public List<T> consultarSelector(T model);
	public DatosSalidaPaginador<T> paginaSelector(DatosEntradaPaginador<T> params);
	public CrtPromocion verificaDuplicidad(Long subDelegacion, Long idCriterioseleccion, Long patron);
	public void actualizaSelector(Long cveSelector, String usuario);
	public T consultaPorClaveCS(T model);
	public void replicaPromocion(T model);
	public void replicaPago(CrtRegulapagosdet regulaDet);
	public void replicaEliminaPago(CrtRegulapagosdet regulaDet);
	public T consultaPagoReplicadoPorFolio(T filtro);
	// Metodo para busqueda sobre la tabla CRT_CORRPROMINVITA
	public T consultaCorrPromInvita(T model);
	public T consultaCriterioPorClave(T model);
	public T consultaPromoInvita(T model);
	public T consultaInvitacionPromocion(T model);
	public T guardar(T model);
	public DatosSalidaPaginador<T> obtenerCriterioSelector(DatosEntradaPaginador<T> params, Long delegacion, Long subDelegacion, Long criterio );
	
	public T consultaPorFolio(T model);
	// consuta para periodos
	public T obtieneObraporNumObra(T model);
	public List<T> obtieneIncidenciasporCveObra(T model);
	public List<T> obtieneRelTrabajadoresporCveObra(T model);
	// consulta selector
	public T consultaSelectorPorClave(T model);
	// Busca Numero de folio
	public List<T> consultaNumeroFolio(String model);
}
