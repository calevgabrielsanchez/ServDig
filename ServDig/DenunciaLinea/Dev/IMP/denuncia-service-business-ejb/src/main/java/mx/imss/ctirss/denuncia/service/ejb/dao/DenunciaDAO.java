package mx.imss.ctirss.denuncia.service.ejb.dao;

import java.util.List;

import mx.imss.ctirss.catalogos.model.DlcGrupo;
import mx.imss.ctirss.catalogos.model.DlcStatus;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltNroFolio;
import mx.imss.ctirss.model.DltUsuarioden;

public interface DenunciaDAO <T extends AbstractModel> {
	public List<DltDenuncia> getDenuncias();
	
	public T agrega(T model);
	
	public T actualiza(T model);
	
	public List<DltDenuncia> findDenunciaByUsrDen(T model);
	
	public List<DltUsuarioden> findUsrDenByMail(String email);
	
	public DltDenuncia getDenunciaByClaveFolio(Long cveDenuncia);	
	
	public DltDenuncia getDenunciaByNumFolio(String numFolio);	
	
	public List<DltDenuncia> getSubdenuncias(DltDenuncia denuncia);
	
	public String obtenerDomicilioPorId(Long idDomicilio);
	
	public DlcStatus getStatusByClve(Long idEstatus);
	
	public DlcGrupo getGrupoByActv(Long cveActividad);
	
	public DltNroFolio recuperaSiguienteFolio(Long anio);
}


