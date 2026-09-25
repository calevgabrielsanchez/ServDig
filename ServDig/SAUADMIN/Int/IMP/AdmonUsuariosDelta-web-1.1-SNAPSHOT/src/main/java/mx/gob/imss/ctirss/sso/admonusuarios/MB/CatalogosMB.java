package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoServiceLocal;


@ManagedBean(name="catalogoMB")
@CustomScoped("#{window}")
public class CatalogosMB{
	



	@EJB
	private CatalogoServiceLocal catalogoCriteria;  
    
	@EJB
	private CatalogoServiceLocal catalogoService;  

    
	private String errorAviso;
	private String errorGeneralDatos;
    
	public List<AreaNormativaDTO> cargaAreaNormativaNCat() throws AdmonUsuariosException{
		return catalogoCriteria.listarAreaNormativa();
	}
	
	public List<AreaNormativaDTO> cargaAreaNormativaNCatPensiones() throws AdmonUsuariosException{
		return catalogoCriteria.listarAreaNormativaPensiones();
	}
	
	public List<DelegacionDTO> cargaDelegacionNCat() throws AdmonUsuariosException{
		return catalogoCriteria.listarDelegacion();
	}

	public List<SubdelegacionDTO> cargaSubdelegacionNCat(Long idDelegacion) throws AdmonUsuariosException{
		return catalogoCriteria.listarSubdelegacion(idDelegacion);
	}

	public List<UmfDTO> cargaUfmCat(Long idSubdelg) throws AdmonUsuariosException{
		return catalogoCriteria.listarUmf(idSubdelg);
	}
	
	public List<UmfDTO> cargaHospitalesCat(Long idSubdelg) throws AdmonUsuariosException{
		return catalogoCriteria.listarHospitales(idSubdelg);
	}

	public List<UmfDTO> cargaUMFCatSinHospitales(Long idSubdelg) throws AdmonUsuariosException{
		return catalogoCriteria.listarUMFSinhospitales(idSubdelg);
	}

	public List<DepartamentoDTO> cargaDepartamentoCat(Long idArea) throws AdmonUsuariosException{
		return catalogoCriteria.listarDepartamentosByAreaNormativa(idArea);
	}
	
	public List<DepartamentoDTO> cargaDepartamentoCatPensiones(Long idArea) throws AdmonUsuariosException{
		return catalogoCriteria.listarDepartamentosByAreaNormativaPensiones(idArea);
	}

	public List<PuestoDTO> cargaPuestoCat(Long idDepto) throws AdmonUsuariosException{
		return catalogoCriteria.listarPuestoByDepartamento(idDepto);
	}


	public CatalogoServiceLocal getCatalogoCriteria() {
		return catalogoCriteria;
	}


	public void setCatalogoCriteria(CatalogoServiceLocal catalogoCriteria) {
		this.catalogoCriteria = catalogoCriteria;
	}
	
	public List<ModuloDTO> cargaModulosExistentes() throws AdmonUsuariosException{
		return catalogoCriteria.cargaModulosExistentes();
	}
	
	public List<ModuloDTO> cargaModulosRelacionados(int cveDep) throws AdmonUsuariosException{
		return catalogoCriteria.cargaModulosRelacionados(cveDep);
	}
	
	public void agregaModuloADep(int cveDep, int claveModuloAgregar)
	{
		try{
		catalogoCriteria.agregaModuloADep(cveDep, claveModuloAgregar);
		}
		catch(Exception e){}
	}

	public void eliminaModuloADep(int cveDep, int claveModuloEliminar)
	{
	   try{
		  catalogoCriteria.eliminaModuloADep(cveDep, claveModuloEliminar);
		  }
	   catch(Exception e){}
	}
	
	public String getErrorAviso()
	{
		return errorAviso;
	}
	
	public void setErrorAviso(String errorAviso)
	{
		this.errorAviso = errorAviso;
	}
	
	public String getErrorGeneralDatos()
	{
		return errorGeneralDatos;
	}
	
	public void setErrorGeneralDatos(String errorGeneralDatos)
	{
		this.errorGeneralDatos = errorGeneralDatos;
	}
	
	public boolean ValidarDependencias(int cveDep, int cveMod)
	{
		boolean res = false;
		try{
			res= catalogoCriteria.validarDependencias(cveDep, cveMod);
		}
		catch(Exception e)
		{
			System.out.println("Entro a este error");
		}
		return res;
	}


	public List<ModuloDTO> listarModulosByDepartamento(String cveDep)
	{
		List<ModuloDTO> lista = null;
		try{
			lista = this.catalogoService.listarModulosByDepartamento(cveDep);
		}
		catch(Exception e)
		{
			System.out.println("Entro a este error");
		}
		return lista;
	}
}
