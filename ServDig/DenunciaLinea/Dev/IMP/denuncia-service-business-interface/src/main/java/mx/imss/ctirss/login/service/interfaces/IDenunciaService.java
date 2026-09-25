package mx.imss.ctirss.login.service.interfaces;

import java.util.List;

import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.denuncia.vo.DenunciaVO;
import mx.imss.ctirss.denuncia.vo.DenunciaVODT;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltDerivaSub;
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.session.UserSession;

public interface IDenunciaService <T extends AbstractModel>{

	public DltDenuncia saveDenuncia(DltDenuncia denuncia, UserSession usuarioDenuncia);
	
	public DltDenuncia getDenunciaByNumFolio(String numFolio);
	
	public DltDenuncia updateDenuncia(DltDenuncia denuncia, UserSession usuarioDenuncia);
	
	public String generaNumFolioDenuncia(DltDenuncia dltDenuncia);
		
	public DltDenuncia generaDenunciaPatronComplemento(DltDenuncia denuncia, DltDatospatron dltDatospatron, UserSession usuario);
	
	public List<DltDenuncia> getDenuncias(DltUsuarioden dltUsuarioden);
	
	public DltDenuncia actualizaNumFolio(DltDenuncia dltDenuncia);	

	public DenunciaVO guardarDenuncia(DenunciaVO denunciaVO,UserSession usuario);		
	
	public DenunciaVO ratificaDenuncia(DenunciaVO denunciaVO,UserSession usuario);	
	
	public List<DenunciaVODT> recuperaDenunciasConsulta(UserSession user, boolean consultaPorUsuario, DenunciaVO den);	
	
	public DenunciaVO consultaDenuncia(DltDenuncia denuncia);
	
	
	public boolean guardaDocumentoPersona(DltDenuncia dltDenuncia,byte[] archivo, Long tipoDocumento, String nombreArchivo);
	
	
	public boolean guardaFormaPago(DltDenuncia dltDenuncia,byte[] archivo, Long cveFormaPago, String nombreArchivo);
	
	
	public void eliminarDomicilio(Long idDomicilio);
	
	public void saveDerivaSub(DltDerivaSub deriva);
	
	public void guardarSeguimiento(DltDerivaSub deriva);
}
