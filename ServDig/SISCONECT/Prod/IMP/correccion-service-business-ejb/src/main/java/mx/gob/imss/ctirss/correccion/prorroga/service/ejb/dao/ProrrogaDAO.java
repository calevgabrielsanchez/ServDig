package mx.gob.imss.ctirss.correccion.prorroga.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
 

public interface ProrrogaDAO <T extends AbstractModel> {

	public T agregar(T model);
	public List<T> consultar(String regPatronal);
	public T consultarPorFolio(T model);
	public T consultarPorClaveAnexoSol(T model);
	public boolean validaProrroga(CrtSolicitudcorr filtro);
	public String obtenerRegPatronal(Integer cveSolicitud);
	public CrtAnexosolcorrpat consultarDom(long domicilioId);
	public boolean  validaPresentacionCorr (CrtSolicitudcorr filtro);
	// Metodos para Autorizacion de Prorroga     ---  EDJ
	public List<T> consultarAP(T model);
	public List<T> llenarStatus();
	public T buscaTipoCorr(Long id );
	public void guardaStatus(List<T> lst);
	
}
