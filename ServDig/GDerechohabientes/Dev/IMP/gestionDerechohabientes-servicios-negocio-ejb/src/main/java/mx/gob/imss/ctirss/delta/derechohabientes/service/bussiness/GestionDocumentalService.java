package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DetalleNivelEdicativoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DoctoReqTramiteDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TipoNivelEducativoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.medicoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GestionDocumentalServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao.DocumentacionTramiteDAOLocal;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao.DocumentoProbatorioDaoLocal;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;





@Stateless(name = "gestionDocumental", mappedName = "gestionDocumental")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GestionDocumentalService implements GestionDocumentalServiceRemote  {
	
	@EJB
	private DoctoReqTramiteDaoLocal doctoReqTramiteDao;
	@EJB
	private DocumentacionTramiteDAOLocal DocumentacionTramiteDao;
	@EJB
	private medicoDaoLocal medicoDao; 
	@EJB
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
	@EJB
	private DetalleNivelEdicativoDaoLocal detalleNivelEdicativoDao;
	@EJB
	private TipoNivelEducativoDaoLocal tipoNivelEdicativoDao;
	@EJB
	private DocumentoProbatorioDaoLocal documentoProbatorioDao;
	
/**
 * Lista documentos Requeridos del tramite
 * @throws DerechohabientesBusinessException 
 */
	
	
	@Override
	public void procesaDocumentos(Long idTramite,
			List<DocumentoProbatorio> documentoProbatorios) throws DerechohabientesBusinessException, Exception {
		//servicio en el pakete de documento Probatorio
		documentoProbatorioServiceBusiness.procesaDocumentos(idTramite,documentoProbatorios);
		
	}
	
	@Override 
	public DocumentoProbatorio getDocumentoProbatorio(Long idDocumentoProbatorio) throws DerechohabientesBusinessException, Exception{
		return this.documentoProbatorioServiceBusiness.getDocumentoProbatorio(idDocumentoProbatorio);
		
	}
	@Override
	public DocumentoProbatorio getDocumentoProbatorioBytes(Long idDocProvatorio){
			
			return this.documentoProbatorioServiceBusiness.getDocumentoProbatorioBytes(idDocProvatorio);
		}
	
	@Override
	public List<DocumentoProbatorio> listaDocumentosProbatoriosTramite(
			Long cveIdTramite) {
		return this.documentoProbatorioServiceBusiness.listaDocumentosProbatoriosTramite(cveIdTramite);

	}
	//Servicios propios de la vista
	
	@Override
	public List<DoctoReqTramite> listaDocumentosTramite(
			Long cveIdTramite) throws Exception {
		 List<DoctoReqTramite> res=null;
		res=this.documentoProbatorioServiceBusiness.getDocumentosRequeridosPorTipoTramite(cveIdTramite);
		return res;
	}	

	@Override
	public List<MedicoFamiliar> getAllMedicos() throws DerechohabientesBusinessException, Exception {
		return this.medicoDao.findAllMedicos();
	}
	@Override
	public List<TipoNivelEducativo> getAllTipoNivelEducativos() throws DerechohabientesBusinessException, Exception {
		return this.tipoNivelEdicativoDao.findAll();
	}
	
	@Override 
	public DetalleNivelEducativo getDetalleNivelEducativo(Long idTipoNivel,Long  idNivel) throws DerechohabientesBusinessException, Exception{
		return this.detalleNivelEdicativoDao.find(idTipoNivel, idNivel);
	}


}
