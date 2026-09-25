package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

@ManagedBean(name="recuperaSolicitudOtraAdscripcionMB")
@CustomScoped("#{window}")
public class RecuperaSolicitudOtraAdscripcionMB {
	
	@ManagedProperty(value="#{catalogoMB}")	 
	private CatalogosMB catalogosMB;
	
	@ManagedProperty(value="#{usuarioMB}")	 
	private UsuarioMB usuarioMB;
	
	private List<SelectItem> lstAreaNormativaOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstDelegacionOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstSubDelegacionOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstUMFOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstDeptoOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuestoOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstModuloOtraAdscripcion = new ArrayList<SelectItem>();
	
	private List<SelectItem> lstDeptoModOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstDeptoRolOtraAdscripcion = new ArrayList<SelectItem>();
	
	private List<SelectItem> lstPuestoAddOtraAdscripcion = new ArrayList<SelectItem>();
	private List<SelectItem> lstModuloAddOtraAdscripcion = new ArrayList<SelectItem>();
	
	private long cveAreaNormativaOtraAdscripcion = 1;
	private long cveDelegacionOtraAdscripcion = 1;
	private long cveSubdelegacionOtraAdscripcion = 1;
	private long cveUmfOtraAdscripcion = 1;
   	private long cveDeptoOtraAdscripcion = 1;
   	private long cvePuestoOtraAdscripcion = 1;
   	
   	private long cveDeptoRolAddOtraAdscripcion = 1;
   	private long cveRolAddOtraAdscripcion = 1;
   	private long cveDeptoModAddOtraAdscripcion = 1;
   	
   	private static int AREA_NIVEL_UMF = 4;
   	
   	@PostConstruct
	public void init() throws AdmonUsuariosException {	
		cargarAreaNormativaNCat();
		cargarDelegacionNCat();
	}
   	
   	public void cargarAreaNormativaNCat() throws AdmonUsuariosException {
		this.lstAreaNormativaOtraAdscripcion.clear();
		List<AreaNormativaDTO> lista = catalogosMB.cargaAreaNormativaNCat();
		for (AreaNormativaDTO areaNormativaDTO : lista) {
			this.lstAreaNormativaOtraAdscripcion.add(new SelectItem(areaNormativaDTO.getCveSsoareanorma(), areaNormativaDTO.getDesAreanorma()));
		}
	}
   	
   	public void cargarDelegacionNCat() throws AdmonUsuariosException {
		this.lstDelegacionOtraAdscripcion.clear();
		List<DelegacionDTO> lista = catalogosMB.cargaDelegacionNCat();
		for (DelegacionDTO delegacionDTO : lista) {
			this.lstDelegacionOtraAdscripcion.add(new SelectItem(delegacionDTO.getCveDelegacion(), delegacionDTO.getNombreDelegacion()));
		}
	}
   	
   	public void cargarSubdelegacionNCat(Long idDelegacion) throws AdmonUsuariosException {
		this.lstSubDelegacionOtraAdscripcion.clear();
		List<SubdelegacionDTO> lista = catalogosMB.cargaSubdelegacionNCat(idDelegacion);
		for (SubdelegacionDTO subdelegacionDTO : lista) {
			this.lstSubDelegacionOtraAdscripcion.add(new SelectItem(subdelegacionDTO.getCveSubelegacion(), subdelegacionDTO.getNombreSubelegacion()));
		}
	}
   	
   	public void cargarUmfCat(Long idSubdeleg) throws AdmonUsuariosException {
		this.lstUMFOtraAdscripcion.clear();
		List<UmfDTO> lista = catalogosMB.cargaUMFCatSinHospitales(idSubdeleg);
		for (UmfDTO umfDTO : lista) {
			this.lstUMFOtraAdscripcion.add(new SelectItem(umfDTO.getCveUmf(), umfDTO.getNombreUmf()));
		}
	}
   	
   	public void cargarDepartamentoCat(Long idArea) throws AdmonUsuariosException {
		this.lstDeptoOtraAdscripcion.clear();
		this.lstDeptoModOtraAdscripcion.clear();
		this.lstDeptoRolOtraAdscripcion.clear();
		List<DepartamentoDTO> lista = catalogosMB.cargaDepartamentoCat(idArea);
		for (DepartamentoDTO departamentoDTO : lista) {
			this.lstDeptoOtraAdscripcion.add(new SelectItem(departamentoDTO.getCveSsodepto(), departamentoDTO.getDesDepartamento()));
			this.lstDeptoModOtraAdscripcion.add(new SelectItem(departamentoDTO.getCveSsodepto(), departamentoDTO.getDesDepartamento()));
			this.lstDeptoRolOtraAdscripcion.add(new SelectItem(departamentoDTO.getCveSsodepto(), departamentoDTO.getDesDepartamento()));
		}
	}
   	
   	public void cargarPuestoCat(Long idDepto) throws AdmonUsuariosException {
		this.lstPuestoOtraAdscripcion.clear();
		this.lstPuestoAddOtraAdscripcion.clear();
		List<PuestoDTO> lista = catalogosMB.cargaPuestoCat(idDepto);
		for (PuestoDTO puestoDTO : lista) {
			this.lstPuestoOtraAdscripcion.add(new SelectItem(puestoDTO.getCvePuesto(), puestoDTO.getNombrePuesto()));
			this.lstPuestoAddOtraAdscripcion.add(new SelectItem(puestoDTO.getCvePuesto(), puestoDTO.getNombrePuesto()));
		}
		if (lista != null && lista.size() > 0) {
			cvePuestoOtraAdscripcion = ((PuestoDTO)lista.get(0)).getCvePuesto();
			cveRolAddOtraAdscripcion = ((PuestoDTO)lista.get(0)).getCvePuesto();
		}
	}
   	
   	public void cargarModuloDeptoCat(Long idDepto) throws AdmonUsuariosException {
   		lstModuloOtraAdscripcion.clear();
		lstModuloAddOtraAdscripcion.clear();
		try {
			List<ModuloDTO> listaModulos = catalogosMB.listarModulosByDepartamento(idDepto.toString());			
			for (ModuloDTO moduloDTO: listaModulos) {
				lstModuloOtraAdscripcion.add(new SelectItem(moduloDTO.getCveIdModulo(),moduloDTO.getDesModulo()));
				lstModuloAddOtraAdscripcion.add(new SelectItem(moduloDTO.getCveIdModulo(),moduloDTO.getDesModulo()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
   	
   	public void cargarPuestoCatAdd(Long idDepto) throws AdmonUsuariosException {
		this.lstPuestoAddOtraAdscripcion.clear();
		
		List<PuestoDTO> lista = null;
		
		if (usuarioMB.getAprobadorSession().getTipoAprobador() == 33) {
			lista = catalogosMB.cargaPuestoCat(idDepto);
		} else {
			lista = catalogosMB.cargaPuestoCat(idDepto);
		}
		
		for (PuestoDTO puestoDTO : lista) {
			this.lstPuestoAddOtraAdscripcion.add(new SelectItem(puestoDTO.getCvePuesto(), puestoDTO.getNombrePuesto()));
		}
	}
   	
   	public void cargarModuloDeptoCatAdd(Long idDepto) throws AdmonUsuariosException {
		lstModuloAddOtraAdscripcion.clear();
		try {
			List<ModuloDTO> listaModulos = catalogosMB.listarModulosByDepartamento(idDepto.toString());			
			for (ModuloDTO moduloDTO: listaModulos) {
				lstModuloAddOtraAdscripcion.add(new SelectItem(moduloDTO.getCveIdModulo(),moduloDTO.getDesModulo()));
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
   	
   	public void listenerDelegacion(ValueChangeEvent ve) {
		try {
			cveDelegacionOtraAdscripcion = new Long(ve.getNewValue().toString());
		} catch(Exception ex) {
			System.out.println("Error al modificar la delegacion");
		}
	}
   	
   	public void listenerSubdelegacion(ValueChangeEvent ve) {
		try {
			cveSubdelegacionOtraAdscripcion = new Long(ve.getNewValue().toString());
		} catch(Exception e) {
			System.out.println("Error al modificar la subdelegacion");
		}
	}

	public void listenerUmf(ValueChangeEvent ve) {
		try {
			cveUmfOtraAdscripcion = new Long(ve.getNewValue().toString());
		} catch(Exception e) {
			System.out.println("Error al modificar la UMF");
		}
	}
	
	public void listenerPuesto2(ValueChangeEvent ve) {
		lstPuestoOtraAdscripcion.clear();
		try {
			cveDeptoOtraAdscripcion = new Long(ve.getNewValue().toString());
			cveDeptoRolAddOtraAdscripcion = new Long(ve.getNewValue().toString());
			cveDeptoModAddOtraAdscripcion = new Long(ve.getNewValue().toString());
			this.cargarPuestoCat(cveDeptoOtraAdscripcion);
			this.cargarPuestoCatAdd(cveDeptoRolAddOtraAdscripcion);
			this.cargarModuloDeptoCatAdd(cveDeptoModAddOtraAdscripcion);
		} catch(Exception e) {
			System.out.println("Error al modificar el Puesto");
		}
	}
	
	public void listenerPuestoAdd(ValueChangeEvent ve) {
		lstPuestoAddOtraAdscripcion.clear();
		try {
			cveDeptoRolAddOtraAdscripcion =new Long(ve.getNewValue().toString()); 
			this.cargarPuestoCatAdd(cveDeptoRolAddOtraAdscripcion);
		} catch(Exception e) {
			System.out.println("Error al modificar el Puesto");
		}
	}
	
	public void listenerModuloAdd(ValueChangeEvent ve) {
		lstModuloAddOtraAdscripcion.clear();
		try {
			cveDeptoModAddOtraAdscripcion = new Long(ve.getNewValue().toString()); 
			this.cargarModuloDeptoCatAdd(cveDeptoModAddOtraAdscripcion);
		} catch(Exception e) {
			System.out.println("Error al modificar el modulo");
		}
	}
	
	public void obtenerDatosUsuario() {
		System.out.println("########## OBTENIENDO DATOS DEL USUARIO PARA LA RECUPERACION EN OTRA AREA DE ADSCRIPCION ##########");
		try {
			cveAreaNormativaOtraAdscripcion = usuarioMB.getAreaNormativa();
			cveDelegacionOtraAdscripcion = usuarioMB.getAprobadorSession().getSolicitud().getDelegacionId();
			cveSubdelegacionOtraAdscripcion = usuarioMB.getAprobadorSession().getSolicitud().getSubdelegacionId();
			cveUmfOtraAdscripcion = new Long(usuarioMB.getAprobadorSession().getSolicitud().getUmfId()).longValue();
			cveDeptoOtraAdscripcion = usuarioMB.getAprobadorSession().getSolicitud().getDepartamentoId();
			cvePuestoOtraAdscripcion = usuarioMB.getAprobadorSession().getSolicitud().getPuestoId();
			cveDeptoRolAddOtraAdscripcion = usuarioMB.getAprobadorSession().getSolicitud().getDepartamentoId();
			cveDeptoModAddOtraAdscripcion = usuarioMB.getAprobadorSession().getSolicitud().getDepartamentoId();
			
			System.out.println("########## CLAVE AREA NORMATIVA [" + cveAreaNormativaOtraAdscripcion + "] ##########");
			System.out.println("########## CLAVE DELEGACION [" + cveDelegacionOtraAdscripcion + "] ##########");
			System.out.println("########## CLAVE SUBDELEGACION [" + cveSubdelegacionOtraAdscripcion + "] ##########");
			System.out.println("########## CLAVE UMF [" + cveUmfOtraAdscripcion + "] ##########");
			System.out.println("########## CLAVE DEPARTAMENTE [" + cveDeptoOtraAdscripcion + "] ##########");
			System.out.println("########## CLAVE PUESTO [" + cvePuestoOtraAdscripcion + "] ##########");
			System.out.println("########## CLAVE DEPARTAMENTO ADD [" + cveDeptoRolAddOtraAdscripcion + "] ##########");
			System.out.println("########## CLAVE MODULO ADD [" + cveDeptoModAddOtraAdscripcion + "] ##########");
			
			if (cveAreaNormativaOtraAdscripcion == 3) {
				cveAreaNormativaOtraAdscripcion = AREA_NIVEL_UMF;
				this.cargarDepartamentoCat(new Long(cveAreaNormativaOtraAdscripcion));
				this.cargarSubdelegacionNCat(new Long(cveDelegacionOtraAdscripcion));
				this.cargarUmfCat(cveSubdelegacionOtraAdscripcion);
				this.cargarModuloDeptoCat(new Long(cveDeptoOtraAdscripcion));
				cveDeptoOtraAdscripcion = -99;
				cvePuestoOtraAdscripcion = -99;
				cveUmfOtraAdscripcion = -99;
			}
			
		} catch (AdmonUsuariosException ex) {
			ex.printStackTrace();
		}
	}

	public CatalogosMB getCatalogosMB() {
		return catalogosMB;
	}

	public void setCatalogosMB(CatalogosMB catalogosMB) {
		this.catalogosMB = catalogosMB;
	}

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}

	public List<SelectItem> getLstAreaNormativaOtraAdscripcion() {
		return lstAreaNormativaOtraAdscripcion;
	}

	public void setLstAreaNormativaOtraAdscripcion(List<SelectItem> lstAreaNormativaOtraAdscripcion) {
		this.lstAreaNormativaOtraAdscripcion = lstAreaNormativaOtraAdscripcion;
	}

	public List<SelectItem> getLstDelegacionOtraAdscripcion() {
		return lstDelegacionOtraAdscripcion;
	}

	public void setLstDelegacionOtraAdscripcion(List<SelectItem> lstDelegacionOtraAdscripcion) {
		this.lstDelegacionOtraAdscripcion = lstDelegacionOtraAdscripcion;
	}

	public List<SelectItem> getLstSubDelegacionOtraAdscripcion() {
		return lstSubDelegacionOtraAdscripcion;
	}

	public void setLstSubDelegacionOtraAdscripcion(List<SelectItem> lstSubDelegacionOtraAdscripcion) {
		this.lstSubDelegacionOtraAdscripcion = lstSubDelegacionOtraAdscripcion;
	}

	public List<SelectItem> getLstUMFOtraAdscripcion() {
		return lstUMFOtraAdscripcion;
	}

	public void setLstUMFOtraAdscripcion(List<SelectItem> lstUMFOtraAdscripcion) {
		this.lstUMFOtraAdscripcion = lstUMFOtraAdscripcion;
	}

	public List<SelectItem> getLstDeptoOtraAdscripcion() {
		return lstDeptoOtraAdscripcion;
	}

	public void setLstDeptoOtraAdscripcion(List<SelectItem> lstDeptoOtraAdscripcion) {
		this.lstDeptoOtraAdscripcion = lstDeptoOtraAdscripcion;
	}

	public List<SelectItem> getLstPuestoOtraAdscripcion() {
		return lstPuestoOtraAdscripcion;
	}

	public void setLstPuestoOtraAdscripcion(List<SelectItem> lstPuestoOtraAdscripcion) {
		this.lstPuestoOtraAdscripcion = lstPuestoOtraAdscripcion;
	}

	public List<SelectItem> getLstModuloOtraAdscripcion() {
		return lstModuloOtraAdscripcion;
	}

	public void setLstModuloOtraAdscripcion(List<SelectItem> lstModuloOtraAdscripcion) {
		this.lstModuloOtraAdscripcion = lstModuloOtraAdscripcion;
	}

	public List<SelectItem> getLstDeptoModOtraAdscripcion() {
		return lstDeptoModOtraAdscripcion;
	}

	public void setLstDeptoModOtraAdscripcion(List<SelectItem> lstDeptoModOtraAdscripcion) {
		this.lstDeptoModOtraAdscripcion = lstDeptoModOtraAdscripcion;
	}

	public List<SelectItem> getLstDeptoRolOtraAdscripcion() {
		return lstDeptoRolOtraAdscripcion;
	}

	public void setLstDeptoRolOtraAdscripcion(List<SelectItem> lstDeptoRolOtraAdscripcion) {
		this.lstDeptoRolOtraAdscripcion = lstDeptoRolOtraAdscripcion;
	}

	public List<SelectItem> getLstPuestoAddOtraAdscripcion() {
		return lstPuestoAddOtraAdscripcion;
	}

	public void setLstPuestoAddOtraAdscripcion(List<SelectItem> lstPuestoAddOtraAdscripcion) {
		this.lstPuestoAddOtraAdscripcion = lstPuestoAddOtraAdscripcion;
	}

	public List<SelectItem> getLstModuloAddOtraAdscripcion() {
		return lstModuloAddOtraAdscripcion;
	}

	public void setLstModuloAddOtraAdscripcion(List<SelectItem> lstModuloAddOtraAdscripcion) {
		this.lstModuloAddOtraAdscripcion = lstModuloAddOtraAdscripcion;
	}

	public long getCveAreaNormativaOtraAdscripcion() {
		return cveAreaNormativaOtraAdscripcion;
	}

	public void setCveAreaNormativaOtraAdscripcion(long cveAreaNormativaOtraAdscripcion) {
		this.cveAreaNormativaOtraAdscripcion = cveAreaNormativaOtraAdscripcion;
		try {
			this.cveAreaNormativaOtraAdscripcion = cveAreaNormativaOtraAdscripcion;
			cargarDepartamentoCat(cveAreaNormativaOtraAdscripcion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getCveDelegacionOtraAdscripcion() {
		return cveDelegacionOtraAdscripcion;
	}

	public void setCveDelegacionOtraAdscripcion(long cveDelegacionOtraAdscripcion) {
		this.cveDelegacionOtraAdscripcion = cveDelegacionOtraAdscripcion;
	}

	public long getCveSubdelegacionOtraAdscripcion() {
		return cveSubdelegacionOtraAdscripcion;
	}

	public void setCveSubdelegacionOtraAdscripcion(long cveSubdelegacionOtraAdscripcion) {
		this.cveSubdelegacionOtraAdscripcion = cveSubdelegacionOtraAdscripcion;
	}

	public long getCveUmfOtraAdscripcion() {
		return cveUmfOtraAdscripcion;
	}

	public void setCveUmfOtraAdscripcion(long cveUmfOtraAdscripcion) {
		this.cveUmfOtraAdscripcion = cveUmfOtraAdscripcion;
	}

	public long getCveDeptoOtraAdscripcion() {
		return cveDeptoOtraAdscripcion;
	}

	public void setCveDeptoOtraAdscripcion(long cveDeptoOtraAdscripcion) {
		this.cveDeptoOtraAdscripcion = cveDeptoOtraAdscripcion;
	}

	public long getCvePuestoOtraAdscripcion() {
		return cvePuestoOtraAdscripcion;
	}

	public void setCvePuestoOtraAdscripcion(long cvePuestoOtraAdscripcion) {
		this.cvePuestoOtraAdscripcion = cvePuestoOtraAdscripcion;
	}

	public long getCveDeptoRolAddOtraAdscripcion() {
		return cveDeptoRolAddOtraAdscripcion;
	}

	public void setCveDeptoRolAddOtraAdscripcion(long cveDeptoRolAddOtraAdscripcion) {
		this.cveDeptoRolAddOtraAdscripcion = cveDeptoRolAddOtraAdscripcion;
	}

	public long getCveRolAddOtraAdscripcion() {
		return cveRolAddOtraAdscripcion;
	}

	public void setCveRolAddOtraAdscripcion(long cveRolAddOtraAdscripcion) {
		this.cveRolAddOtraAdscripcion = cveRolAddOtraAdscripcion;
	}

	public long getCveDeptoModAddOtraAdscripcion() {
		return cveDeptoModAddOtraAdscripcion;
	}

	public void setCveDeptoModAddOtraAdscripcion(long cveDeptoModAddOtraAdscripcion) {
		this.cveDeptoModAddOtraAdscripcion = cveDeptoModAddOtraAdscripcion;
	}
	
}
