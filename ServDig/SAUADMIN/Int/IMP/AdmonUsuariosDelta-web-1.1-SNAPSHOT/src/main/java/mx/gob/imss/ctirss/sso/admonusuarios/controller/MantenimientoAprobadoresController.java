package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.AprobadoresServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.MensajeriaSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.model.table.RowStateMap;



@ManagedBean(name="mantenimientoAprobadores")
@CustomScoped("#{window}")
public class MantenimientoAprobadoresController {
	
    @ManagedProperty(value="#{catalogoMB}")	 
	private CatalogosMB catalogoMB;
	
    @ManagedProperty(value="#{usuarioMB}")	 
	private UsuarioMB usuario;

	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;

	@EJB
	private SolicitudServiceLocal solicitudCriteria;

	@EJB
	private AprobadoresServiceLocal aprobadoresService;

	@EJB
	private BitacoraServiceLocal bitacoraService;

	@EJB
	private MensajeriaSessionLocal mensajeriaService;

	/** Log de la clase */
    private static final Log log = LogFactory.getLog(MantenimientoAprobadoresController.class);
    
	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();

	private List<SelectItem> lstDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstSubDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstUMF = new ArrayList<SelectItem>();
	
	private List<SelectItem> lstPerfiles = new ArrayList<SelectItem>();
	private List<SelectItem> lstModulo = new ArrayList<SelectItem>(); 
	
	private List<AprobadorDTO> solicitudes = new ArrayList<AprobadorDTO>();
	private List<AprobadorDTO> aprobadores = new ArrayList<AprobadorDTO>();
	
	private static long ESTATUS_AUTORIZADO = 2;
	private static long ESTATUS_BAJA = 4;
	
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
   	
   	private String curp;
   	private String matricula;
   	
   	private boolean fgUmf = true;
   	private boolean fgSubdele = true;
   	private boolean fgDeleg = true;
   	
   	
   	private RowStateMap apr = new RowStateMap();
   	private RowStateMap usr = new RowStateMap();
   	
	private SolicitudDTO sol = new SolicitudDTO();
	
	private AprobadorDTO usuarioAdd = null;
	private AprobadorDTO aprobadorDel = null;

	@PostConstruct
	public void init() throws AdmonUsuariosException{
		System.out.println("entra al init del manage bean");
		cargaAreaNormativaNCat();
		cargaDelegacionNCat();
		obtieneDatosUsuario();
		solicitudes = new ArrayList<AprobadorDTO>();
		aprobadores = new ArrayList<AprobadorDTO>();
	}
	
	
	public void obtieneDatosUsuario()
	{
		try {
			claveAreaNormativa = usuario.getAreaNormativa();
			
			if(claveAreaNormativa==1||claveAreaNormativa>4)
			{
				if(usuario.getAprobadorSession().getSolicitud().getUmfId()==0 && usuario.getAprobadorSession().getSolicitud().getSubdelegacionId()>0)
					fgUmf = false;
				else
					if(usuario.getAprobadorSession().getSolicitud().getSubdelegacionId() == 0 && usuario.getAprobadorSession().getSolicitud().getDelegacionId()>0)
						fgSubdele = false;
					else
						if(usuario.getAprobadorSession().getSolicitud().getDelegacionId()==0)
							fgDeleg = false;
			}
			else
				if(claveAreaNormativa==2)
					fgSubdele = false;
				else
					if(claveAreaNormativa==3)
						fgUmf = false;
			
			
			
			claveDelegacion = usuario.getAprobadorSession().getSolicitud().getDelegacionId();
			claveSubdelegacion = usuario.getAprobadorSession().getSolicitud().getSubdelegacionId();
			claveUMF = new Long(usuario.getAprobadorSession().getSolicitud().getUmfId()).longValue();
			claveDepartamento = usuario.getAprobadorSession().getSolicitud().getDepartamentoId();
			clavePuesto = usuario.getAprobadorSession().getSolicitud().getPuestoId();

			cargaSubdelegacionNCat(new Long(claveDelegacion));
			cargaDepartamentoCat(new Long(claveAreaNormativa));
			cargaPuestoCat(new Long(claveDepartamento));
			cargaUmfCat(claveSubdelegacion);
			
			sol.setDelDTO(usuario.getAprobadorSession().getSolicitud().getDelDTO());
			sol.setSubdelDTO(usuario.getAprobadorSession().getSolicitud().getSubdelDTO());
			sol.setUmfDTO(usuario.getAprobadorSession().getSolicitud().getUmfDTO());
			sol.setDptoDTO(usuario.getAprobadorSession().getSolicitud().getDptoDTO());
			sol.setPuestoDTO(usuario.getAprobadorSession().getSolicitud().getPuestoDTO());
			sol.setEstatusDTO(new EstatusDTO());
			sol.getEstatusDTO().setCveSsoestatus(2);
			
			
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}

	}

	
	public void cargaAreaNormativaNCat() throws AdmonUsuariosException{
		this.lstAreaNormativa.clear();
		List<AreaNormativaDTO> lista = catalogoMB.cargaAreaNormativaNCat();
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
		List<DepartamentoDTO> lista = catalogoMB.cargaDepartamentoCat(idArea);
		for(DepartamentoDTO dl : lista){
			this.lstDepartamento.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
		}
	}

	public void cargaPuestoCat(Long idDepto) throws AdmonUsuariosException{
		this.lstPuesto.clear();
		List<PuestoDTO> lista = catalogoMB.cargaPuestoCat(idDepto);
		for(PuestoDTO dl : lista){
			this.lstPuesto.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
		}
	}
	
	public void cargaUmfCat(Long idSubdeleg) throws AdmonUsuariosException{
		this.lstUMF.clear();
		List<UmfDTO> lista = catalogoMB.cargaUMFCatSinHospitales(idSubdeleg);
		for(UmfDTO dl : lista){
			this.lstUMF.add(new SelectItem(dl.getCveUmf(), dl.getNombreUmf()));
		}
	}
	

	

	public String inicializaConsultaAprobadores()
	{
		
		solicitudes = new ArrayList<AprobadorDTO>();
		aprobadores = new ArrayList<AprobadorDTO>();
		obtieneDatosUsuario();
		buscaCambios();
		return "buscaAprobadores";
	}

	
	public void actualizaSolicitudesDel(ValueChangeEvent event)
	{
		claveAreaNormativa = usuario.getAreaNormativa();
		this.sol = new SolicitudDTO();
		sol.setDptoDTO(usuario.getAprobadorSession().getSolicitud().getDptoDTO());
		sol.setPuestoDTO(usuario.getAprobadorSession().getSolicitud().getPuestoDTO());


		DelegacionDTO dto  = new DelegacionDTO();
		dto.setCveDelegacion(new Long(event.getNewValue().toString()).longValue());
		obtieneDatosUsuario();
		if(dto.getCveDelegacion()!=-99)
		{
			sol.setDelDTO(dto);
			sol.setSubdelDTO(null);
			sol.setUmfDTO(null);
		}
		
	}

	public void actualizaSolicitudesSubdel(ValueChangeEvent event)
	{
		claveAreaNormativa = usuario.getAreaNormativa();
		this.sol = new SolicitudDTO();
		sol.setDptoDTO(usuario.getAprobadorSession().getSolicitud().getDptoDTO());
		sol.setPuestoDTO(usuario.getAprobadorSession().getSolicitud().getPuestoDTO());

		SubdelegacionDTO dto = new SubdelegacionDTO();
		dto.setCveSubelegacion(new Long(event.getNewValue().toString()).longValue());
		obtieneDatosUsuario();
		if(dto.getCveSubelegacion()!=-99)
		{
			sol.setSubdelDTO(dto);
			sol.setUmfDTO(null);
		}
			
	}

	public void actualizaSolicitudesUmf(ValueChangeEvent event)
	{
		claveAreaNormativa = usuario.getAreaNormativa();
		this.sol = new SolicitudDTO();
		sol.setDptoDTO(usuario.getAprobadorSession().getSolicitud().getDptoDTO());
		sol.setPuestoDTO(usuario.getAprobadorSession().getSolicitud().getPuestoDTO());

		UmfDTO dto = new UmfDTO();
		dto.setCveUmf(new Long(event.getNewValue().toString()));
		obtieneDatosUsuario();
		if((new Long(dto.getCveUmf()))!=-99)
			sol.setUmfDTO(dto);
	}
	
	public String actualiza() throws AdmonUsuariosException {
		solicitudes = new ArrayList<AprobadorDTO>();
		aprobadores = new ArrayList<AprobadorDTO>();
		buscaCambios();
		return "buscaAprobadores";
	}
	
	
	private void buscaCambios()
	{
		try {
//			List<SolicitudDTO> sols = solicitudCriteria.solicitudesByFiltro(sol,ESTATUS_AUTORIZADO, true, usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
			System.out.println("Invoca search solicitudes - MantenimientoAprobadoresController");
			List<SolicitudDTO> sols = solicitudCriteria.searchSolicitudes(sol, " ");
			//sols  = addSolsMismoNivel(sols);
			if(sols!=null&&sols.size()>0)
			{
				aprobadores = aprobadoresService.consultaAprobadoresBySolicitud(sols);
				solicitudes = getSolicitudesListAprobadores(sols);
			}
			else
				solicitudes = new ArrayList<AprobadorDTO>();
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	private List<SolicitudDTO> addSolsMismoNivel(List<SolicitudDTO> sols) throws AdmonUsuariosException {
		List<SolicitudDTO> result = new ArrayList<SolicitudDTO>();
		for(SolicitudDTO s : sols)
		{
			result.add(s);
		}
		SolicitudDTO filtro = getfiltroAreaAds(usuario.getAprobadorSession().getSolicitud());
		System.out.println("Invoca search solicitudes - MantenimientoAprobadoresController");
		List<SolicitudDTO> sols2 = solicitudCriteria.searchSolicitudes(filtro,usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
		for(SolicitudDTO s2 : sols2)
		{
			result.add(s2);
		}
		return result;
	}

//	private void buscaAprobadores()
//	{
//		solicitudes = new ArrayList<AprobadorDTO>();
//		aprobadores = new ArrayList<AprobadorDTO>();
//		try {
//			
//			SolicitudDTO filtro = getfiltroAreaAds(sol);
//			List<SolicitudDTO> sols = solicitudCriteria.searchSol(filtro,usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
//			if(sols!=null&&sols.size()>0)
//			{
//				aprobadores = aprobadoresService.consultaArobadoresBySolicitud(sols);
//				solicitudes = getSolicitudesListAprobadores(sols);
//			}
//			else
//				solicitudes = new ArrayList<AprobadorDTO>();
//		} catch (AdmonUsuariosException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	private SolicitudDTO getfiltroAreaAds(SolicitudDTO sol)
	{
		SolicitudDTO result = new SolicitudDTO();
		result.setDelDTO(sol.getDelDTO());
		result.setSubdelDTO(sol.getSubdelDTO());
		result.setUmfDTO(sol.getUmfDTO());
		result.setDptoDTO(sol.getDptoDTO());
		result.setPuestoDTO(sol.getPuestoDTO());
		EstatusDTO es = new EstatusDTO();
		es.setCveSsoestatus(ESTATUS_AUTORIZADO);
		result.setEstatusDTO(es);
		return result;
	}

	private List<AprobadorDTO> getSolicitudesListAprobadores(List<SolicitudDTO> sols) {
		List<AprobadorDTO> result = new ArrayList<AprobadorDTO>();
		for (SolicitudDTO sol : sols) {
			boolean agrega = true;
			AprobadorDTO dto = null;
			if(aprobadores!=null&&aprobadores.size()>0)
			{
				for (AprobadorDTO ap : aprobadores) 
				{
					if(ap.getSolicitud().getCveSsosolicitud()==sol.getCveSsosolicitud())
					{
						if(ap.getEstatus().getCveSsoestatus()==ESTATUS_AUTORIZADO)
						{
							agrega = false;
							dto = ap;
							break;
						}
						else
						{
							dto = ap;
							break;
						}
					}
				}
			}
			if(agrega)
			{
				if(dto==null)
					dto = new AprobadorDTO();
				else
					aprobadores.remove(dto);
				dto.setMatricula(sol.getCveMatricula());
				dto.setSolicitud(sol);
				result.add(dto);
			}
		}
		return result;
	}

    public void agregaAprobador(SelectEvent event) {
    	usuarioAdd = (AprobadorDTO)event.getObject();
    }
    


	public void agregaUsuario(SelectEvent event) {
    	aprobadorDel = (AprobadorDTO)event.getObject();
    }



	public String guardaCambios() throws AdmonUsuariosException {
		System.out.println("si entra");
		return inicializaConsultaAprobadores();
	}

	public String otogarPermisos() throws AdmonUsuariosException {
		
		if(aprobadores!=null&&aprobadores.size()<1)
		{
			if(usuarioAdd.getSolicitud().getCveMatricula()!=null)
			{
				aprobadoresService.addAprobador(usuarioAdd);
				solicitudes.remove(usuarioAdd);
				aprobadores.add(usuarioAdd);
				bitacoraService.guardaAprobadorBit(usuarioAdd.getSolicitud().getCveSsosolicitud(), usuario.getAprobadorSession().getCveIdAprobador(), true);
				filtrosConsulta.showMsg("Se le asignaron  permisos  de aprobador al usuario: "+usuarioAdd.getSolicitud().getNombreCompleto());
				enviaCorreoInformativo(usuarioAdd.getSolicitud(), "Mantenimiento de aprobadores", "se le otorgaron permisos de aprobador");
				
			}
			else
			{
				filtrosConsulta.showMsg("El usuario no pertenece a la nomina IMSS: "+usuarioAdd.getSolicitud().getNombreCompleto());
			}
		}
		else
		{
			filtrosConsulta.showMsg("Solo se permite otorgar los permisos de administrador a un solo usuario por nivel de adscripción");
		}
		return "buscaAprobadores";
	}

	public void buscaCuentas(SelectEvent ev) throws AdmonUsuariosException {
		System.out.println("si entra");
	}

	
	
	public String revocarPermisos() throws AdmonUsuariosException {

		if(aprobadores!=null&&aprobadores.size()>=1)
		{
			aprobadoresService.delAprobador(aprobadorDel);
			aprobadores.remove(aprobadorDel);
			solicitudes.add(aprobadorDel);
			bitacoraService.guardaAprobadorBit(usuarioAdd.getSolicitud().getCveSsosolicitud(), usuario.getAprobadorSession().getCveIdAprobador(), false);
			filtrosConsulta.showMsg("Se le revocaron  permisos  de aprobador al usuario: "+aprobadorDel.getSolicitud().getNombreCompleto());
			enviaCorreoInformativo(aprobadorDel.getSolicitud(), "Mantenimiento de aprobadores", "se le revocaron permisos de aprobador");
		}
		else
		{
			filtrosConsulta.showMsg("Se requiere seleccionar un usuario valido.");
		}

		return "buscaAprobadores";
	}

	
	public void enviaCorreoInformativo(SolicitudDTO sol,String titulo,String msg)
	{
		sol.setAprobador(usuario.getAprobadorSession());
	  	//String msg = "-- SE AUTORIZO SU CUENTA DE USUARIO --";
	  	//String titulo = "AUTORIZACION Y REGISTRO DE CUENTA DE USUARIO";
		String remitente = "serviciosdigitales@imss.gob.mx";
		System.out.println("Voy a enviar correo desde el nuevo remitente Normal [ " + remitente + " ]");
	  	try {
			mensajeriaService.enviarCorreo(remitente, sol.getRefCorreoElectronico(), msg, titulo,sol,"Modificación de permisos de aprobador");
			mensajeriaService.enviarCorreoAprobador(remitente,usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), msg, titulo,sol,"Modificación de permisos de aprobador");
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
			System.out.println("Error al enviar correo informativo de modificacion de perfiles de aprobador- MantenimientoAprobadores");
		}
	}

	
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


	public long getClaveAreaNormativa() {
		return claveAreaNormativa;
	}


	public void setClaveAreaNormativa(int claveAreaNormativa) {
		this.claveAreaNormativa = claveAreaNormativa;
	}


	public long getClaveDepartamento() {
		return claveDepartamento;
	}


	public void setClaveDepartamento(int claveDepartamento) {
		this.claveDepartamento = claveDepartamento;
	}


	public long getClavePuesto() {
		return clavePuesto;
	}


	public void setClavePuesto(int clavePuesto) {
		this.clavePuesto = clavePuesto;
	}


	public long getClaveDelegacion() {
		return claveDelegacion;
	}


	public void setClaveDelegacion(int claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}


	public long getClaveSubdelegacion() {
		return claveSubdelegacion;
	}


	public void setClaveSubdelegacion(int claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
	}


	public long getClaveUMF() {
		return claveUMF;
	}


	public void setClaveUMF(int claveUMF) {
		this.claveUMF = claveUMF;
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


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public UsuarioMB getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioMB usuario) {
		this.usuario = usuario;
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


	public void setClaveAreaNormativa(long claveAreaNormativa) {
		this.claveAreaNormativa = claveAreaNormativa;
	}


	public void setClaveDepartamento(long claveDepartamento) {
		this.claveDepartamento = claveDepartamento;
	}


	public void setClavePuesto(long clavePuesto) {
		this.clavePuesto = clavePuesto;
	}


	public void setClaveDelegacion(long claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}


	public void setClaveSubdelegacion(long claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
	}


	public void setClaveUMF(long claveUMF) {
		this.claveUMF = claveUMF;
	}


	public List<AprobadorDTO> getSolicitudes() {
		return solicitudes;
	}


	public void setSolicitudes(List<AprobadorDTO> solicitudes) {
		this.solicitudes = solicitudes;
	}


	public List<AprobadorDTO> getAprobadores() {
		return aprobadores;
	}


	public void setAprobadores(List<AprobadorDTO> aprobadores) {
		this.aprobadores = aprobadores;
	}


	public SolicitudServiceLocal getSolicitudCriteria() {
		return solicitudCriteria;
	}


	public void setSolicitudCriteria(SolicitudServiceLocal solicitudCriteria) {
		this.solicitudCriteria = solicitudCriteria;
	}


	public AprobadoresServiceLocal getAprobadoresService() {
		return aprobadoresService;
	}


	public void setAprobadoresService(AprobadoresServiceLocal aprobadoresService) {
		this.aprobadoresService = aprobadoresService;
	}


	public RowStateMap getApr() {
		return apr;
	}


	public void setApr(RowStateMap apr) {
		this.apr = apr;
	}


	public RowStateMap getUsr() {
		return usr;
	}


	public void setUsr(RowStateMap usr) {
		this.usr = usr;
	}

	public AprobadorDTO getUsuarioAdd() {
		return usuarioAdd;
	}


	public void setUsuarioAdd(AprobadorDTO usuarioAdd) {
		this.usuarioAdd = usuarioAdd;
	}


	public AprobadorDTO getAprobadorDel() {
		return aprobadorDel;
	}


	public void setAprobadorDel(AprobadorDTO aprobadorDel) {
		this.aprobadorDel = aprobadorDel;
	}


	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}


	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}


	public SolicitudDTO getSol() {
		return sol;
	}


	public void setSol(SolicitudDTO sol) {
		this.sol = sol;
	}

	
	
}
