package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.UsuarioServiceRemote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ManagedBean(name = "consultaGenerica")
@CustomScoped("#{window}")
public class ConsultaGenericaController {
	
	/** Log de la clase */
	private static final Log logger = LogFactory.getLog(ConsultaGenericaController.class);

	@ManagedProperty(value = "#{catalogoMB}")
	private CatalogosMB catalogoMB;

	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuario;

	@EJB
	private UsuarioServiceRemote usuarioServiceRemote;

	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();

	private List<SelectItem> lstDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstSubDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstUMF = new ArrayList<SelectItem>();

	private List<SelectItem> lstPerfiles = new ArrayList<SelectItem>();
	private List<SelectItem> lstModulo = new ArrayList<SelectItem>();

	private List<SelectItem> lstDepartamentoRol = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamentoMod = new ArrayList<SelectItem>();

	private List<SelectItem> lstPuestoAdd = new ArrayList<SelectItem>();
	private List<SelectItem> lstModuloAdd = new ArrayList<SelectItem>();

	private List<SolicitudDTO> resultados = new ArrayList<SolicitudDTO>();
	private List<SolicitudDTO> solicitudesByDepto = new ArrayList<SolicitudDTO>();

	private static final SelectItem[] POSITION_AVAILABLE = { 
			new SelectItem("bottom", "Bottom"),
			new SelectItem("top", "Top"), 
			new SelectItem("both", "Both")
	};

	private boolean flag = true;
	private String position = POSITION_AVAILABLE[0].getValue().toString();
	private int rows = 10;
	private int startPage = 1;

	private long claveAreaNormativa = 1;
	private long claveDepartamento = 1;
	private long clavePuesto = 1;
	private long claveDelegacion = 1;
	private long claveSubdelegacion = 1;
	private long claveUMF = 1;
	private long claveModulo = 1;

	private long claveDepartamentoRolAdd = 1;
	private long claveRolAdd = 1;
	private long claveDepartamentoModAdd = 1;
	private long claveModAdd = 1;

	private String curp;
	private String matricula;

	private boolean fgUmf = true;
	private boolean fgSubdele = true;
	private boolean fgDeleg = true;

	private String desc = "";
	private boolean msg = false;

	@PostConstruct
	public void init() throws AdmonUsuariosException {
		cargaAreaNormativaNCat();
		cargaDelegacionNCat();
		obtieneDatosUsuario();
	}

	public void showMsg(String mens) {
		desc = mens;
		msg = true;
	}

	public void hide() {
		desc = "";
		msg = false;
	}

	public void show() {
		desc = "this is show!!!";
		msg = true;
	}

	public void obtieneDatosUsuario() {
		logger.info("########## OBTENIENDO DATOS DEL USUARIO ##########");
		try {
			claveAreaNormativa = usuario.getAreaNormativa();

			logger.info("########## CLAVE AREA NORMATIVA [" + claveAreaNormativa + "] ##########");

			if (claveAreaNormativa == 1 || claveAreaNormativa > 4) {
				if (usuario.getAprobadorSession().getSolicitud().getUmfId() == 0
						&& usuario.getAprobadorSession().getSolicitud().getSubdelegacionId() > 0) {
					fgUmf = false;
				} else if (usuario.getAprobadorSession().getSolicitud().getSubdelegacionId() == 0
						&& usuario.getAprobadorSession().getSolicitud().getDelegacionId() > 0) {
					fgSubdele = false;
				} else if (usuario.getAprobadorSession().getSolicitud().getDelegacionId() == 0) {
					fgDeleg = false;
				}
			} else if (claveAreaNormativa == 2) {
				fgSubdele = false;
			} else if (claveAreaNormativa == 3) {
				fgUmf = false;
			}
			// MOAJ760806HDFRLN06

			claveDelegacion = usuario.getAprobadorSession().getSolicitud().getDelegacionId();
			claveSubdelegacion = usuario.getAprobadorSession().getSolicitud().getSubdelegacionId();
			claveUMF = new Long(usuario.getAprobadorSession().getSolicitud().getUmfId()).longValue();
			claveDepartamento = usuario.getAprobadorSession().getSolicitud().getDepartamentoId();
			clavePuesto = usuario.getAprobadorSession().getSolicitud().getPuestoId();

			claveDepartamentoRolAdd = usuario.getAprobadorSession().getSolicitud().getDepartamentoId();
			claveDepartamentoModAdd = usuario.getAprobadorSession().getSolicitud().getDepartamentoId();

			cargaSubdelegacionNCat(new Long(claveDelegacion));
			cargaDepartamentoCat(new Long(claveAreaNormativa));
			cargaPuestoCat(new Long(claveDepartamento));
			cargaUmfCat(claveSubdelegacion);
			cargaModuloDeptoCat(new Long(claveDepartamento));

		} catch (AdmonUsuariosException ex) {
			logger.error("Error::obtieneDatosUsuario " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void seteaDepto() {
		try {
			claveDepartamentoModAdd = claveDepartamento;
			claveDepartamentoRolAdd = claveDepartamento;
			cargaPuestoCatAdd(claveDepartamentoRolAdd);
			cargaModuloDeptoCatAdd(claveDepartamentoModAdd);
		} catch (AdmonUsuariosException ex) {
			logger.error("Error::seteaDepto " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void seteaDepto(long cve) {
		try {
			claveDepartamentoModAdd = cve;
			claveDepartamentoRolAdd = cve;
			cargaPuestoCatAdd(claveDepartamentoRolAdd);
			cargaModuloDeptoCatAdd(claveDepartamentoModAdd);
		} catch (AdmonUsuariosException ex) {
			logger.error("Error::seteaDepto " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void cargaAreaNormativaNCat() throws AdmonUsuariosException {
		this.lstAreaNormativa.clear();
		List<AreaNormativaDTO> lista = catalogoMB.cargaAreaNormativaNCat();
		for (AreaNormativaDTO an : lista) {
			this.lstAreaNormativa.add(new SelectItem(an.getCveSsoareanorma(), an.getDesAreanorma()));
		}
	}

	public void cargaDelegacionNCat() throws AdmonUsuariosException {
		this.lstDelegacion.clear();
		List<DelegacionDTO> lista = catalogoMB.cargaDelegacionNCat();
		for (DelegacionDTO dl : lista) {
			this.lstDelegacion.add(new SelectItem(dl.getCveDelegacion(), dl.getNombreDelegacion()));
		}
	}

	public void cargaSubdelegacionNCat(Long idDelegacion) throws AdmonUsuariosException {
		this.lstSubDelegacion.clear();
		List<SubdelegacionDTO> lista = catalogoMB.cargaSubdelegacionNCat(idDelegacion);
		for (SubdelegacionDTO dl : lista) {
			this.lstSubDelegacion.add(new SelectItem(dl.getCveSubelegacion(), dl.getNombreSubelegacion()));
		}
	}

	public void cargaDepartamentoCat(Long idArea) throws AdmonUsuariosException {
		this.lstDepartamento.clear();
		this.lstDepartamentoMod.clear();
		this.lstDepartamentoRol.clear();
		List<DepartamentoDTO> lista = catalogoMB.cargaDepartamentoCat(idArea);
		for (DepartamentoDTO dl : lista) {
			this.lstDepartamento.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
		}
		for (DepartamentoDTO dl : lista) {
			this.lstDepartamentoRol.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
		}
		for (DepartamentoDTO dl : lista) {
			this.lstDepartamentoMod.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
		}
	}

	public void cargaPuestoCat(Long idDepto) throws AdmonUsuariosException {
		this.lstPuesto.clear();
		this.lstPuestoAdd.clear();
		List<PuestoDTO> lista = catalogoMB.cargaPuestoCat(idDepto);
		for (PuestoDTO dl : lista) {
			this.lstPuesto.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
		}
		for (PuestoDTO dl : lista) {
			this.lstPuestoAdd.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
		}
		if (lista != null && lista.size() > 0) {
			clavePuesto = ((PuestoDTO) lista.get(0)).getCvePuesto();
			claveRolAdd = ((PuestoDTO) lista.get(0)).getCvePuesto();
		}
	}

	public void cargaPuestoCatAdd(Long idDepto) throws AdmonUsuariosException {
		this.lstPuestoAdd.clear();

		List<PuestoDTO> lista = null;

		if (usuario.getAprobadorSession().getTipoAprobador() == 33) {
			lista = catalogoMB.cargaPuestoCat(idDepto);
		} else {
			lista = catalogoMB.cargaPuestoCat(idDepto);
		}
		for (PuestoDTO dl : lista) {
			this.lstPuestoAdd.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
		}
	}

	public void cargaUmfCat(Long idSubdeleg) throws AdmonUsuariosException {
		this.lstUMF.clear();
		List<UmfDTO> lista = catalogoMB.cargaUMFCatSinHospitales(idSubdeleg);
		for (UmfDTO dl : lista) {
			this.lstUMF.add(new SelectItem(dl.getCveUmf(), dl.getNombreUmf()));
		}
	}

	public void listenerPuesto(ValueChangeEvent ve) {
		lstPuesto.clear();
		try {
			claveDepartamento = new Long(ve.getNewValue().toString());
			this.cargaPuestoCat(claveDepartamento);
		} catch (Exception ex) {
			logger.error("Error::listenerPuesto " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerPuestoSel(ValueChangeEvent ve) {
		logger.info("listenerPuestoSelIn------------");
		try {
			clavePuesto = new Long(ve.getNewValue().toString());
			logger.info("valor seleccionado :" + ve.getNewValue().toString());
		} catch (Exception e) {
			logger.info("Error al consultar el Puesto");
		}
		logger.info("listenerPuestoSelOut------------");
	}

	public void listenerPuesto2(ValueChangeEvent ve) {
		lstPuesto.clear();
		try {
			claveDepartamento = new Long(ve.getNewValue().toString());
			claveDepartamentoRolAdd = new Long(ve.getNewValue().toString());
			claveDepartamentoModAdd = new Long(ve.getNewValue().toString());
			this.cargaPuestoCat(claveDepartamento);
			this.cargaPuestoCatAdd(claveDepartamentoRolAdd);
			this.cargaModuloDeptoCatAdd(claveDepartamentoModAdd);
		} catch (Exception ex) {
			logger.error("Error::listenerPuesto2 " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerDelegacion(ValueChangeEvent ve) {
		try {
			claveDelegacion = new Long(ve.getNewValue().toString());
		} catch (Exception ex) {
			logger.error("Error::listenerDelegacion " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerSubdelegacion(ValueChangeEvent ve) {
		try {
			claveSubdelegacion = new Long(ve.getNewValue().toString());
		} catch (Exception ex) {
			logger.error("Error::listenerSubdelegacion " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerUmf(ValueChangeEvent ve) {
		try {
			claveUMF = new Long(ve.getNewValue().toString());
		} catch (Exception ex) {
			logger.error("Error::listenerUmf " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerRol(ValueChangeEvent ve) {
		try {
			claveRolAdd = new Long(ve.getNewValue().toString());
		} catch (Exception ex) {
			logger.error("Error::listenerRol " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerMod(ValueChangeEvent ve) {
		try {
			claveModAdd = new Long(ve.getNewValue().toString());
		} catch (Exception ex) {
			logger.error("Error::listenerMod " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerPuestoAdd(ValueChangeEvent ve) {
		lstPuestoAdd.clear();
		try {
			claveDepartamentoRolAdd = new Long(ve.getNewValue().toString());
			this.cargaPuestoCatAdd(claveDepartamentoRolAdd);
		} catch (Exception ex) {
			logger.error("Error::listenerPuestoAdd " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void listenerModuloAdd(ValueChangeEvent ve) {
		lstModuloAdd.clear();
		try {
			claveDepartamentoModAdd = new Long(ve.getNewValue().toString());
			this.cargaModuloDeptoCatAdd(claveDepartamentoModAdd);
		} catch (Exception ex) {
			logger.error("Error::listenerModuloAdd " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void cargaModuloDeptoCat(Long idDepto) throws AdmonUsuariosException {
		lstModulo.clear();
		lstModuloAdd.clear();
		try {
			List<ModuloDTO> listaModulos = catalogoMB.listarModulosByDepartamento(idDepto.toString());
			for (ModuloDTO md : listaModulos) {
				lstModulo.add(new SelectItem(md.getCveIdModulo(), md.getDesModulo()));
			}
			for (ModuloDTO md : listaModulos) {
				lstModuloAdd.add(new SelectItem(md.getCveIdModulo(), md.getDesModulo()));
			}
		} catch (Exception ex) {
			logger.error("Error::cargaModuloDeptoCat " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public void cargaModuloDeptoCatAdd(Long idDepto) throws AdmonUsuariosException {
		lstModuloAdd.clear();
		try {
			List<ModuloDTO> listaModulos = catalogoMB.listarModulosByDepartamento(idDepto.toString());
			for (ModuloDTO md : listaModulos) {
				lstModuloAdd.add(new SelectItem(md.getCveIdModulo(), md.getDesModulo()));
			}
		} catch (Exception ex) {
			logger.error("Error::cargaModuloDeptoCatAdd " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public String inicializaConsultaAprobadores() {
		return "buscaAprobadores";
	}

	public void obtenerSolByDepto(ActionEvent event) {
		// solicitudCriteria.solicitudesByDepto();
	}

	public void consultaAprobadores(ActionEvent event) {
		resultados = new ArrayList<SolicitudDTO>();
	}

	// --------------------- seccion gets

	public List<SelectItem> getLstAreaNormativa() {
		return lstAreaNormativa;
	}

	public void setLstAreaNormativa(List<SelectItem> lstAreaNormativa) {
		this.lstAreaNormativa = lstAreaNormativa;
	}

	public List<SelectItem> getLstDepartamento() {
		return lstDepartamento;
	}

	public void setLstDepartamento(List<SelectItem> lstDepartamento) {
		this.lstDepartamento = lstDepartamento;
	}

	public List<SelectItem> getLstPuesto() {
		return lstPuesto;
	}

	public void setLstPuesto(List<SelectItem> lstPuesto) {
		this.lstPuesto = lstPuesto;
	}

	public List<SelectItem> getLstDelegacion() {
		return lstDelegacion;
	}

	public void setLstDelegacion(List<SelectItem> lstDelegacion) {
		this.lstDelegacion = lstDelegacion;
	}

	public List<SelectItem> getLstSubDelegacion() {
		return lstSubDelegacion;
	}

	public void setLstSubDelegacion(List<SelectItem> lstSubDelegacion) {
		this.lstSubDelegacion = lstSubDelegacion;
	}

	public List<SelectItem> getLstUMF() {
		return lstUMF;
	}

	public void setLstUMF(List<SelectItem> lstUMF) {
		this.lstUMF = lstUMF;
	}

	public List<SelectItem> getLstPerfiles() {
		return lstPerfiles;
	}

	public void setLstPerfiles(List<SelectItem> lstPerfiles) {
		this.lstPerfiles = lstPerfiles;
	}

	public List<SelectItem> getLstModulo() {
		return lstModulo;
	}

	public void setLstModulo(List<SelectItem> lstModulo) {
		this.lstModulo = lstModulo;
	}

	public List<SolicitudDTO> getResultados() {
		return resultados;
	}

	public void setResultados(List<SolicitudDTO> resultados) {
		this.resultados = resultados;
	}

	public boolean isPaginator() {
		return flag;
	}

	public void setPaginator(boolean flag) {
		this.flag = flag;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public List<SolicitudDTO> getSolicitudesByDepto() {
		return solicitudesByDepto;
	}

	public void setSolicitudesByDepto(List<SolicitudDTO> solicitudesByDepto) {
		this.solicitudesByDepto = solicitudesByDepto;
	}

	public CatalogosMB getCatalogoMB() {
		return catalogoMB;
	}

	public void setCatalogoMB(CatalogosMB catalogoMB) {
		this.catalogoMB = catalogoMB;
	}

	public boolean isFlag() {
		return this.flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public UsuarioMB getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioMB usuario) {
		this.usuario = usuario;
	}

	public long getClaveAreaNormativa() {
		return claveAreaNormativa;
	}

	public void setClaveAreaNormativa(long claveAreaNormativa) {
		try {
			this.claveAreaNormativa = claveAreaNormativa;
			cargaDepartamentoCat(claveAreaNormativa);
		} catch (Exception ex) {
			logger.error("Error::setClaveAreaNormativa " + ex.getMessage());
			logger.error("  ", ex);
		}
	}

	public long getClaveDepartamento() {
		return claveDepartamento;
	}

	public void setClaveDepartamento(long claveDepartamento) {
		this.claveDepartamento = claveDepartamento;
	}

	public long getClavePuesto() {
		return clavePuesto;
	}

	public void setClavePuesto(long clavePuesto) {
		this.clavePuesto = clavePuesto;
	}

	public long getClaveDelegacion() {
		return claveDelegacion;
	}

	public void setClaveDelegacion(long claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	public long getClaveSubdelegacion() {
		return claveSubdelegacion;
	}

	public void setClaveSubdelegacion(long claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
	}

	public long getClaveUMF() {
		return claveUMF;
	}

	public void setClaveUMF(long claveUMF) {
		this.claveUMF = claveUMF;
	}

	public boolean isFgUmf() {
		return fgUmf;
	}

	public void setFgUmf(boolean fgUmf) {
		this.fgUmf = fgUmf;
	}

	public boolean isFgSubdele() {
		return fgSubdele;
	}

	public void setFgSubdele(boolean fgSubdele) {
		this.fgSubdele = fgSubdele;
	}

	public boolean isFgDeleg() {
		return fgDeleg;
	}

	public void setFgDeleg(boolean fgDeleg) {
		this.fgDeleg = fgDeleg;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public long getClaveModulo() {
		return claveModulo;
	}

	public void setClaveModulo(long claveModulo) {
		this.claveModulo = claveModulo;
	}

	public List<SelectItem> getLstDepartamentoRol() {
		return lstDepartamentoRol;
	}

	public void setLstDepartamentoRol(List<SelectItem> lstDepartamentoRol) {
		this.lstDepartamentoRol = lstDepartamentoRol;
	}

	public List<SelectItem> getLstDepartamentoMod() {
		return lstDepartamentoMod;
	}

	public void setLstDepartamentoMod(List<SelectItem> lstDepartamentoMod) {
		this.lstDepartamentoMod = lstDepartamentoMod;
	}

	public List<SelectItem> getLstPuestoAdd() {
		return lstPuestoAdd;
	}

	public void setLstPuestoAdd(List<SelectItem> lstPuestoAdd) {
		this.lstPuestoAdd = lstPuestoAdd;
	}

	public List<SelectItem> getLstModuloAdd() {
		return lstModuloAdd;
	}

	public void setLstModuloAdd(List<SelectItem> lstModuloAdd) {
		this.lstModuloAdd = lstModuloAdd;
	}

	public long getClaveDepartamentoRolAdd() {
		return claveDepartamentoRolAdd;
	}

	public void setClaveDepartamentoRolAdd(long claveDepartamentoRolAdd) {
		this.claveDepartamentoRolAdd = claveDepartamentoRolAdd;
	}

	public long getClaveDepartamentoModAdd() {
		return claveDepartamentoModAdd;
	}

	public void setClaveDepartamentoModAdd(long claveDepartamentoModAdd) {
		this.claveDepartamentoModAdd = claveDepartamentoModAdd;
	}

	public long getClaveRolAdd() {
		return claveRolAdd;
	}

	public void setClaveRolAdd(long claveRolAdd) {
		this.claveRolAdd = claveRolAdd;
	}

	public long getClaveModAdd() {
		return claveModAdd;
	}

	public void setClaveModRolAdd(long claveModAdd) {
		this.claveModAdd = claveModAdd;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isMsg() {
		return msg;
	}

	public void setMsg(boolean msg) {
		this.msg = msg;
	}

	public SolicitudDTO llenaFiltro() {
		SolicitudDTO sol = new SolicitudDTO();
		if (getClaveDelegacion() > 0) {
			sol.getDelDTO().setCveDelegacion(getClaveDelegacion());
		} else {
			sol.setDelDTO(null);
		}
		if (getClaveSubdelegacion() > 0) {
			sol.getSubdelDTO().setCveSubelegacion(getClaveSubdelegacion());
		} else {
			sol.setSubdelDTO(null);
		}
		if (getClaveUMF() > 0) {
			sol.getUmfDTO().setCveUmf(getClaveUMF());
		} else {
			sol.setUmfDTO(null);
		}
		if (getClaveDepartamento() > 0) {
			sol.getDptoDTO().setCveSsodepto(getClaveDepartamento());
		} else {
			sol.setDptoDTO(null);
		}
		sol.setEstatusDTO(new EstatusDTO());
		sol.getEstatusDTO().setCveSsoestatus(2);
		return sol;
	}

}
