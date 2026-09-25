package mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacTipoObra;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericDAO;

public interface SelectorDAO extends GenericDAO<CrtSelector, Long> {
	
	public void insertaSelector(CrtSelector selector);
	public SacSubdelegacion obtenerSubDelegacion(Long idDelegacion, Long idSubDelegacion);
	public CgtCatCriterioeleccion obtenerCriterio(Long idCriterio);
	public List<CrtSelector> obtenerCriteriosSeleccion(Long idDelegacion, Long idSubDelegacion, Long idCriterio);
	
	/*metodo para guardar las detecciones*/
	public CrtDeteccion guardarDeteccionCarga(CrtDeteccion deteccion);
	public boolean existeNumeroReporteObra(CrtDeteccion deteccion);
	public List<SegUsuarioFuncionario> cargarCensoresDeteccion(Long idDelegacion, Long idSubDelegacion);
	public SacTipoObra obtenerTipoObra(Long id);
	public CrtNroFolio obtenerSiguienteFolio(Long idDelegacion, Long idSubdelegacion, Integer tipoCorrecion, Date fechaFolio);
	
	/*Promocion Exhorto*/
	public CrtPromocion guardarPromocion(CrtPromocion promocion);
	public CrtSelector actualizarSelector(CrtSelector selector);
	public void guardarPromocionReplica(CgtPromocion replica);
}
