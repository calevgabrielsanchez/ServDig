package mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.correccion.commons.sbc.vo.ExcedentesTopadosVO;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.DetBaseCotOmitidaServiceRemote;
import mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.dao.DetBaseCotOmitidaDAOLocal;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.AnexoSolicitudCorreccionDAO;

import org.hibernate.criterion.Restrictions;

@Stateless(name="detBaseCotOmitidaService", mappedName = "detBaseCotOmitidaService")
public class DetBaseCotOmitidaServiceBean <T extends AbstractModel> extends AbstractService implements DetBaseCotOmitidaServiceRemote<T>{

	
	@EJB AnexoSolicitudCorreccionDAO anexoSolCorrDao;
	@EJB DetBaseCotOmitidaDAOLocal<T> detBaseCotOmitidaDAO;
	
	public List<CrtAnexosolcorrpat> buscaAnexosporidSolicitud(CrtSolicitudcorr solicitud) {
		
		String[] tpRegistros = new String[2];
		tpRegistros[0] = CrtAnexosolcorrpat.TIPO_REGISTRO_RP_CENTRO_TRABAJO;
		tpRegistros[1] = CrtAnexosolcorrpat.TIPO_REGISTRO_RP_INSCRITO;
				
		return (List<CrtAnexosolcorrpat>) this.anexoSolCorrDao.findByCriteria(
				Restrictions.eq("cveSolicitudCorr",
						solicitud.getCveSolicitudCorr()), Restrictions.in("tipoPatron",tpRegistros));

	}

	@Override
	public List<CrcEjercicio> buscaEjercicio(CrcEjercicio ejercicio) {
		
		return this.detBaseCotOmitidaDAO.buscaEjercicio(ejercicio);
	}

	@Override
	public DatosSalidaPaginador<T> paginar(DatosEntradaPaginador<T> params){
		return detBaseCotOmitidaDAO.paginar(params);
	}

	@Override
	public CrtAnexosolcorrpat buscaAnexosById(CrtAnexosolcorrpat model) {
		List<CrtAnexosolcorrpat> lstAnexo = new ArrayList<CrtAnexosolcorrpat>();
		lstAnexo = this.anexoSolCorrDao.findByCriteria(Restrictions.eq("cveAnexoSolicitudCorrPat", model.getCveAnexoSolicitudCorrPat()));
		if(lstAnexo != null && lstAnexo.size() > 0){
			return lstAnexo.get(0);
		}
		return null;
	}

	@Override
	public List<T> buscaPercepciones(T model) {
		
		return this.detBaseCotOmitidaDAO.buscaPercepciones(model);
	}

	@Override
	public T calculaBalanza(T model) {
		return this.detBaseCotOmitidaDAO.calculaBalanza(model);
	}
	
	@Override
	public T calculaMenos(T model) {
		return this.detBaseCotOmitidaDAO.calculaMenos(model);
	}

	@Override
	public T guardaDetBaseCotOm(T model) {
		
		return this.detBaseCotOmitidaDAO.guardaDetBaseCotOm(model);
	}

	@Override
	public T guardaDetBaseCotOmDet(T model) {
		return this.detBaseCotOmitidaDAO.guardaDetBaseCotOmDet(model);
	}

	@Override
	public T validaDetBaseCotOm(T model) {
		
		return this.detBaseCotOmitidaDAO.validaDetBaseCotOm(model);
	}

	@Override
	public ExcedentesTopadosVO calculaExcedenteTopado(ExcedentesTopadosVO excedentesTopadosVO){
		return this.detBaseCotOmitidaDAO.calculaExcedenteTopado(excedentesTopadosVO);
	}

	
}
