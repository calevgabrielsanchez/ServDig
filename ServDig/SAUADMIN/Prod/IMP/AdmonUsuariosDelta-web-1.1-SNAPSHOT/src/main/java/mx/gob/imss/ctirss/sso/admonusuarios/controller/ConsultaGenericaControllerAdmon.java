package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@ManagedBean(name="consultaGenericaAdmon")
@CustomScoped("#{window}")

public class ConsultaGenericaControllerAdmon {
	
    @ManagedProperty(value="#{catalogoMB}")	 
	private CatalogosMB catalogoMB;
   
    @ManagedProperty(value="#{usuarioMB}")	 
	private UsuarioMB usuario;
    
    
    /** Log de la clase */
    private static final Log log = LogFactory.getLog(ConsultaGenericaControllerAdmon.class);
    
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
	
    private static final SelectItem[] POSITION_AVAILABLE = { new SelectItem("bottom", "Bottom"),
    	new SelectItem("top", "Top"),
        new SelectItem("both", "Both") };

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

   	
   	private long claveDepartamentoRolAdd = -99;
   	private long claveRolAdd = -99;
   	private long claveDepartamentoModAdd = -99;
   	private long claveModAdd = -99;
   	
   	private String curp;
   	private String matricula;
   	

   	private boolean fgUmf = false;
   	private boolean fgSubdele = false;
   	private boolean fgDeleg = false;
   	
   	private static int AREA_NIVEL_CENTRAL = 1;
   	private static int AREA_NIVEL_DELEGACION = 2;
   	private static int AREA_NIVEL_SUBDELEGACION = 3;
   	private static int AREA_NIVEL_UMF = 4;
   	private static int AREA_NIVEL_HOSPITALES = 15;

   	private String desc = "";
   	private boolean msg = false;
  	
   	
   	
	@PostConstruct
	public void init() throws AdmonUsuariosException{	
		cargaAreaNormativaNCat();
		cargaDelegacionNCat();
		obtieneDatosUsuario();
	}
	
	public void showMsg(String mens)
	{
		desc = mens;
		msg = true;
	}
	
	public void hide()  {
		desc = "";
		msg = false;
	}

	public void show()  {
		desc = "this is show!!!";
		msg = true;
	}

	public SolicitudDTO llenaFiltro() {
		SolicitudDTO sol = new SolicitudDTO();
		if (getClaveDelegacion() > 0)
			sol.getDelDTO().setCveDelegacion(getClaveDelegacion());
		else
			sol.setDelDTO(null);
		if (getClaveSubdelegacion() > 0)
			sol.getSubdelDTO().setCveSubelegacion(getClaveSubdelegacion());
		else
			sol.setSubdelDTO(null);
		if (getClaveUMF() > 0)
			sol.getUmfDTO().setCveUmf(getClaveUMF());
		else
			sol.setUmfDTO(null);
		if (getClaveDepartamento() > 0)
			sol.getDptoDTO().setCveSsodepto(getClaveDepartamento());
		else
			sol.setDptoDTO(null);
		sol.setEstatusDTO(new EstatusDTO());
		sol.getEstatusDTO().setCveSsoestatus(2);
		return sol;
	}

	
	public void obtieneDatosUsuario()
	{

		try {
			claveAreaNormativa = usuario.getAreaNormativa();
			claveDelegacion = usuario.getAprobadorSession().getSolicitud().getDelegacionId();
			claveSubdelegacion = usuario.getAprobadorSession().getSolicitud().getSubdelegacionId();
			claveUMF = new Long(usuario.getAprobadorSession().getSolicitud().getUmfId()).longValue();
			claveDepartamento = usuario.getAprobadorSession().getSolicitud().getDepartamentoId();
			clavePuesto = usuario.getAprobadorSession().getSolicitud().getPuestoId();
			controlaComponentes();
			if(claveAreaNormativa==AREA_NIVEL_CENTRAL||claveAreaNormativa>AREA_NIVEL_UMF)
			{
				cargaDepartamentoCat(claveAreaNormativa);
				cargaDelegacionNCat();
				cargaPuestoCat(claveDepartamento);
				claveDelegacion = -99;
				claveSubdelegacion = -99;
				claveUMF = -99;
			}
			else
				if(claveAreaNormativa==AREA_NIVEL_DELEGACION)
				{
					cargaDepartamentoCat(claveAreaNormativa);
					claveDepartamento = -99;
					clavePuesto = -99;
					
					cargaDelegacionNCat();
					claveDelegacion = usuario.getAprobadorSession().getSolicitud().getDelegacionId();
					cargaSubdelegacionNCat(claveDelegacion);
					claveSubdelegacion = -99;
					claveUMF = -99;
					
				}
				else
					if(claveAreaNormativa==AREA_NIVEL_SUBDELEGACION)
					{
						cargaDepartamentoCat(claveAreaNormativa);

						cargaDelegacionNCat();
						claveDelegacion = usuario.getAprobadorSession().getSolicitud().getDelegacionId();
						cargaSubdelegacionNCat(claveDelegacion);
						claveSubdelegacion = usuario.getAprobadorSession().getSolicitud().getSubdelegacionId();
						cargaUmfCat(claveSubdelegacion);
						claveUMF = -99;
					}
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}

	}

	public void seteaDepto()
	{
		try {
			claveDepartamentoModAdd = claveDepartamento;
			claveDepartamentoRolAdd = claveDepartamento;
			cargaPuestoCatAdd(claveDepartamentoRolAdd);
			cargaModuloDeptoCatAdd(claveDepartamentoModAdd);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	public void seteaDepto(long cve)
	{
		try {
			claveDepartamentoModAdd = cve;
			claveDepartamentoRolAdd = cve;
			cargaPuestoCatAdd(claveDepartamentoRolAdd);
			cargaModuloDeptoCatAdd(claveDepartamentoModAdd);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	public void cargaAreaNormativaNCat() throws AdmonUsuariosException{
		this.lstAreaNormativa.clear();
		List<AreaNormativaDTO> lista = null;
		if (usuario.getAprobadorSession().getTipoAprobador() == 3) {
//			System.out.println("########## SOLO CARGAR CATALOGOS DE PENSIONES ##########");
			lista = catalogoMB.cargaAreaNormativaNCatPensiones();
		} else {
//			System.out.println("########## CARGAR TODOS LOS CATALOGOS ##########");
			lista = catalogoMB.cargaAreaNormativaNCat();
		}
		for(AreaNormativaDTO an : lista){
			this.lstAreaNormativa.add(new SelectItem(an.getCveSsoareanorma(),	an.getDesAreanorma()));
		}
	}

	
	public void cargaDelegacionNCat() throws AdmonUsuariosException{
		this.lstDelegacion.clear();
		List<DelegacionDTO> lista = catalogoMB.cargaDelegacionNCat();
		for(DelegacionDTO dl : lista){
			this.lstDelegacion.add(new SelectItem(dl.getCveDelegacion(), dl.getNombreDelegacion()));
		}
	}

	public void cargaSubdelegacionNCat(Long idDelegacion) throws AdmonUsuariosException{
		this.lstSubDelegacion.clear();
		List<SubdelegacionDTO> lista = catalogoMB.cargaSubdelegacionNCat(idDelegacion);
		for(SubdelegacionDTO dl : lista){
			this.lstSubDelegacion.add(new SelectItem(dl.getCveSubelegacion(), dl.getNombreSubelegacion()));
		}
	}
	
	public void cargaDepartamentoCat(Long idArea) throws AdmonUsuariosException{
		this.lstDepartamento.clear();
		this.lstDepartamentoMod.clear();
		this.lstDepartamentoRol.clear();
		List<DepartamentoDTO> lista = null;
		if (usuario.getAprobadorSession().getTipoAprobador() == 3) {
			lista = catalogoMB.cargaDepartamentoCatPensiones(idArea);
		} else {
			lista = catalogoMB.cargaDepartamentoCat(idArea);
		}
		
		for(DepartamentoDTO dl : lista){
			this.lstDepartamento.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
		}
		for(DepartamentoDTO dl : lista){
			this.lstDepartamentoRol.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
		}
		for(DepartamentoDTO dl : lista){
			this.lstDepartamentoMod.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
		}
	}

	public void cargaPuestoCat(Long idDepto) throws AdmonUsuariosException{
		this.lstPuesto.clear();
		this.lstPuestoAdd.clear();
		List<PuestoDTO> lista = catalogoMB.cargaPuestoCat(idDepto);
		for(PuestoDTO dl : lista){
			this.lstPuesto.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
		}
		for(PuestoDTO dl : lista){
			this.lstPuestoAdd.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
		}
	}

	public void cargaPuestoCatAdd(Long idDepto) throws AdmonUsuariosException{
		this.lstPuestoAdd.clear();
		List<PuestoDTO> lista = catalogoMB.cargaPuestoCat(idDepto);
		for(PuestoDTO dl : lista){
			this.lstPuestoAdd.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
		}
	}

	public void cargaUmfCat(Long idSubdeleg) throws AdmonUsuariosException{
		this.lstUMF.clear();
		List<UmfDTO> lista = catalogoMB.cargaUMFCatSinHospitales(idSubdeleg);
		for(UmfDTO dl : lista){
			this.lstUMF.add(new SelectItem(dl.getCveUmf(), dl.getNombreUmf()));
		}
	}

	public void cargaHospitalesCat(Long idSubdeleg) throws AdmonUsuariosException{
		this.lstUMF.clear();
		List<UmfDTO> lista = catalogoMB.cargaHospitalesCat(idSubdeleg);
		for(UmfDTO dl : lista){
			this.lstUMF.add(new SelectItem(dl.getCveUmf(), dl.getNombreUmf()));
		}
	}

	public void listenerPuesto(ValueChangeEvent ve){
		lstPuesto.clear();
		try{
		claveDepartamento = new Long(ve.getNewValue().toString());
		this.cargaPuestoCat(claveDepartamento);
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}

	public void listenerArea(ValueChangeEvent ve){
		claveAreaNormativa = new Long(ve.getNewValue().toString());
		claveDelegacion = -99;
		claveSubdelegacion = -99;
		claveUMF = -99;
		claveDepartamento = -99;
		clavePuesto = -99;
		
		lstPuesto.clear();
		lstSubDelegacion.clear();
		lstUMF.clear();
		
		controlaComponentes();
		
		try{
		
			this.cargaDelegacionNCat();
			this.cargaDepartamentoCat(claveAreaNormativa);
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}

	
	public void controlaComponentes()
	{
		if(claveAreaNormativa==AREA_NIVEL_CENTRAL||(claveAreaNormativa>AREA_NIVEL_UMF&&claveAreaNormativa!=AREA_NIVEL_HOSPITALES))
		{
			fgDeleg = true;
			fgSubdele = true;
			fgUmf = true;
		}
		else
		{
			if(claveAreaNormativa==AREA_NIVEL_DELEGACION)
			{
				fgDeleg = false;
				fgSubdele = true;
				fgUmf = true;
			}
			else
			{
				if(claveAreaNormativa==AREA_NIVEL_SUBDELEGACION)
				{
					fgDeleg = false;
					fgSubdele = false;
					fgUmf = true;
				}
				else
				{
					if(claveAreaNormativa==AREA_NIVEL_UMF)
					{
						fgDeleg = false;
						fgSubdele = false;
						fgUmf = false;
					}
					else
					{
						if(claveAreaNormativa==AREA_NIVEL_HOSPITALES)
						{
							fgDeleg = false;
							fgSubdele = false;
							fgUmf = false;
						}
					}
				}
			}
		}

	}
	
	public void listenerDelegacion(ValueChangeEvent ve){
		try{
			claveDelegacion = new Long(ve.getNewValue().toString());
			this.cargaSubdelegacionNCat(claveDelegacion);
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}

	public void listenerSubdelegacion(ValueChangeEvent ve){
		try{
			claveSubdelegacion = new Long(ve.getNewValue().toString());
			if(claveAreaNormativa!=AREA_NIVEL_HOSPITALES)
				this.cargaUmfCat(claveSubdelegacion);
			else
				this.cargaHospitalesCat(claveSubdelegacion);
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}

	public void listenerUmf(ValueChangeEvent ve){
		try{
			claveUMF = new Long(ve.getNewValue().toString());
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}

	public void listenerPuestoSel(ValueChangeEvent ve){
		try{
			clavePuesto = new Long(ve.getNewValue().toString());
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}
	
	public void listenerRol(ValueChangeEvent ve)
	{
		try{
			claveRolAdd = new Long(ve.getNewValue().toString());
		}
		catch(Exception e){
			System.out.println("listener rol");
		}
	}

	public void listenerMod(ValueChangeEvent ve){
		try{
			claveModAdd = new Long(ve.getNewValue().toString());
		}
		catch(Exception e){
			System.out.println("listener modulo");
		}
	}

	public void listenerPuestoAdd(ValueChangeEvent ve){
		lstPuestoAdd.clear();
		try{
			claveDepartamentoRolAdd =new Long(ve.getNewValue().toString()); 
			this.cargaPuestoCatAdd(claveDepartamentoRolAdd);
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}

	public void listenerModuloAdd(ValueChangeEvent ve){
		lstModuloAdd.clear();
		try{
			claveDepartamentoModAdd = new Long(ve.getNewValue().toString()); 
			this.cargaModuloDeptoCatAdd(claveDepartamentoModAdd);
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}

	public void cargaModuloDeptoCat(Long idDepto) throws AdmonUsuariosException{
		lstModulo.clear();
		try{
			List<ModuloDTO> listaModulos = catalogoMB.listarModulosByDepartamento(idDepto.toString());			
			for(ModuloDTO md: listaModulos){
				lstModulo.add(new SelectItem(md.getCveIdModulo(),md.getDesModulo()));
			}			
			for(ModuloDTO md: listaModulos){
				lstModuloAdd.add(new SelectItem(md.getCveIdModulo(),md.getDesModulo()));
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void cargaModuloDeptoCatAdd(Long idDepto) throws AdmonUsuariosException{
		lstModuloAdd.clear();
		try{
			List<ModuloDTO> listaModulos = catalogoMB.listarModulosByDepartamento(idDepto.toString());			
			for(ModuloDTO md: listaModulos){
				lstModuloAdd.add(new SelectItem(md.getCveIdModulo(),md.getDesModulo()));
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public String inicializaConsultaAprobadores()
	{
		return "buscaAprobadores";
	}

	public void obtenerSolByDepto(ActionEvent event){
//		solicitudCriteria.solicitudesByDepto();
	}
	
	public void consultaAprobadores(ActionEvent event){
		resultados = new ArrayList<SolicitudDTO>();
	}
	
	
	//--------------------- seccion gets
	





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
		return flag;
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
		this.claveAreaNormativa = claveAreaNormativa;
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
	
	
	
}
