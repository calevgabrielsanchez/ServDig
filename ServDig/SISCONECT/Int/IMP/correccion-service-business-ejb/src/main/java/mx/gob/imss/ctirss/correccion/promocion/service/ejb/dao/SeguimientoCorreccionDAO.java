package mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao;

import java.math.BigDecimal;
import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevValAclara;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevision;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivASubd;
import mx.gob.imss.ctirss.correccion.model.CrtRevOficios;
import mx.gob.imss.ctirss.correccion.model.CrtRevRecepcion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;

public interface SeguimientoCorreccionDAO<T extends AbstractModel> {
	
	public CrtRevOficios consultaRevOficiosPorClave(CrtRevOficios crtRevOficios);
	
	public CrtRevRecepcion guardaRecepcion(CrtRevRecepcion crtRevRecepcion);
	public CrtRevRecepcion consultaRecepcion(CrtRevRecepcion crtRevRecepcion);
	public CrtRevOficios guardaReqDoc(CrtRevOficios crtRevOficios);
	public CrtPresentacorr consultaCrtPresentacorrPorClave(CrtPresentacorr crtPresentacorr);
	public CrtPresentacorr consultaCrtPresentacorrByPk(CrtPresentacorr crtPresentacorr);
	public CrtSolicitudcorr getSolicitudCorr(CrtSolicitudcorr crtSolicitudcorr);
	
	public CrtRevOficios consultaRevOficiosPorCveSolCorr(CrtRevOficios crtRevOficios);
	public CrtRevCedRevision guardaCedulaRevision(CrtRevCedRevision crtRevCedRevision);
	public CrtRevCedRevision consultaCedulaRevisionPorParams(CrtRevCedRevision crtRevCedRevision);
	public List<CrtRevCedRevision> consultaCedulaRevisionPorCvePresenta(CrtRevCedRevision crtRevCedRevision);
	public BigDecimal sumaTotalPorAclarar(CrtRevCedRevision crtRevCedRevision);
	public List<Object> consultaRegistroCedulaRevByCvePresenta(Integer cvePresnetacion);
	
	public List<CrtRevCedRevValAclara> consultaRubrosRevCeduAclarado(CrtRevCedRevValAclara crtRevCedRevValAclara);
	public List<CrtRevCedRevValAclara> consultaRubrosRevCeduAclaradoByCvePresentacion(CrtRevCedRevValAclara crtRevCedRevValAclara);
	public CrtRevCedRevValAclara guardaCedulaRevisionAclarado(CrtRevCedRevValAclara crtRevCedRevValAclara);
	public CrtRevCedRevValAclara eliminaCedulaRevisionAclarado(CrtRevCedRevValAclara crtRevCedRevValAclara);
	public CrtAnexosolcorrpat consultaAnexoPorRegPatSolCorrEjer(String regPatr,Long cvSolCorr, Integer ejercicio);
	public List<Object> consultaCedulaValidacionConsolidado(Integer cvePresentacion);
	
	public CrtRevDerivASubd getDerivASubdByClaveSolCorr(Integer cveSolCorr);
	
}
