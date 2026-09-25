package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Alan Garcia
 * @version 1.0
 */

public class SolicitudDTO  implements Serializable{
	
	/** Identificador único de la versión de la clase */
	private static final long serialVersionUID = 3515802327994150187L;
	
	private long cveSsosolicitud;
	private String cveMatricula;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private String nomMaterno;
	private String nomNombre;
	private String nomPaterno;
	private String refCorreoElectronico;
	private List<PerfilDTO> perfilesDTO;
	private DelegacionDTO delDTO;
	private SubdelegacionDTO subdelDTO;
	private UmfDTO umfDTO;
	private Date fecUsrNacimiento;
	private String desUsrCurp;
	private AreaNormativaDTO areaNorm;
	private DepartamentoDTO dptoDTO;
	private String desTelefonoOfi;
	private List<PuestoDTO> puestosDTO;
	private List<ModuloDTO> modulosDTO;
	private PuestoDTO puestoDTO;
	private String cveIdEntidad;
	private EstatusDTO estatusDTO;
	private String password;
	private String nombreAprobador;
	private Long cveAprobador;
	
	private String nssNom;
	private String puestoDescNom;
	private String departamentoDescNom;
	private String cveDelegacionNom;
	private String cveSubdelegacionNom;
	private String cveUmfNom;
	private long cveEstatusNom = 0;
	private AprobadorDTO aprobador;
	
	public SolicitudDTO(){
		perfilesDTO = new ArrayList<PerfilDTO>();
		delDTO = new DelegacionDTO();
		subdelDTO = new SubdelegacionDTO();
		umfDTO = new UmfDTO();
		areaNorm = new AreaNormativaDTO();
		dptoDTO = new DepartamentoDTO();
		puestoDTO = new PuestoDTO();
		estatusDTO = new EstatusDTO();
    	puestosDTO = new ArrayList<PuestoDTO>();
    	modulosDTO = new ArrayList<ModuloDTO>();
   }
	public String getNombreCompleto() {
		return getNomNombre() + " " + getNomPaterno() + " " + getNomMaterno();
	}
	
	public String datosBitacora(){
		StringBuilder cadena = new StringBuilder("Nombre:" + this.getNombreCompleto())
				.append("|idsolicitud:" + this.cveSsosolicitud)
				.append("|matricula:" + this.cveMatricula)
				.append("|curp:" + this.desUsrCurp)
				.append("|fechaalta:" + this.getFecRegistroAlta())
				.append("|fechabaja:" + this.fecRegistroBaja)
				.append("|fecharegistroactualizado:" + this.getFecRegistroActualizado())
				.append("|No.modulos:" + this.getNumModulos())
				.append("|No.Perfiles:" + this.getNumPerfiles())
				.append("|correo:" + this.refCorreoElectronico);

		if (this.getDelDTO() != null) {
			cadena.append("|delegacion:" + this.getDelDTO().getNombreDelegacion());
		} else {
			cadena.append("|delegacion:");
		}
		if (this.getSubdelDTO() != null) {
			cadena.append("|subdelegacion:" + this.getSubdelDTO().getNombreSubelegacion());
		} else {
			cadena.append("|subdelegacion:");
		}
		if (this.getUmfDTO() != null) {
			cadena.append("|umf:" + this.getUmfDTO().getNombreUmf());
		} else {
			cadena.append("|umf:");
		}

		cadena.append("|areanormativa:" + this.getAreaNorm().getDesAreanorma());
		cadena.append("|depto:" + this.getDptoDTO().getDesDepartamento());
		cadena.append("|puesto:" + this.getPuestoDTO().getNombrePuesto());
	
		return cadena.toString();
	}
	
	
	public AprobadorDTO getAprobador() {
		return aprobador;
	}
	public void setAprobador(AprobadorDTO aprobador) {
		this.aprobador = aprobador;
	}
	public long getCveSsosolicitud() {
		return cveSsosolicitud;
	}
	public void setCveSsosolicitud(long cveSsosolicitud) {
		this.cveSsosolicitud = cveSsosolicitud;
	}
	public String getCveIdEntidad() {
		return cveIdEntidad;
	}
	public void setCveIdEntidad(String cveIdEntidad) {
		this.cveIdEntidad = cveIdEntidad;
	}
	public String getCveMatricula() {
		return cveMatricula;
	}
	public void setCveMatricula(String cveMatricula) {
		this.cveMatricula = cveMatricula;
	}
	public String getDesUsrCurp() {
		return desUsrCurp;
	}
	public void setDesUsrCurp(String desUsrCurp) {
		this.desUsrCurp = desUsrCurp;
	}
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public Date getFecUsrNacimiento() {
		return fecUsrNacimiento;
	}
	public void setFecUsrNacimiento(Date fecUsrNacimiento) {
		this.fecUsrNacimiento = fecUsrNacimiento;
	}
	public String getNomMaterno() {
		if(nomMaterno!=null)
			return nomMaterno.trim();
		else 
			return nomMaterno;
	}
	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}
	public String getNomNombre() {
		return nomNombre;
	}
	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}
	public String getNomPaterno() {
		return nomPaterno;
	}
	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}
	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}
	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}
	public DelegacionDTO getDelDTO() {
		return delDTO;
	}
	public void setDelDTO(DelegacionDTO delegacionDTO) {
		this.delDTO = delegacionDTO;
	}
	public SubdelegacionDTO getSubdelDTO() {
		return subdelDTO;
	}
	public void setSubdelDTO(SubdelegacionDTO subdelDTO) {
		this.subdelDTO = subdelDTO;
	}
	public DepartamentoDTO getDptoDTO() {
		return dptoDTO;
	}
	public void setDptoDTO(DepartamentoDTO dptoDTO) {
		this.dptoDTO = dptoDTO;
	}
	public List<PerfilDTO> getPerfilesDTO() {
		return perfilesDTO;
	}
	public void setPerfilesDTO(List<PerfilDTO> perfilesDTO) {
		this.perfilesDTO = perfilesDTO;
	}
	public EstatusDTO getEstatusDTO() {
		return estatusDTO;
	}
	public void setEstatusDTO(EstatusDTO estatus) {
		this.estatusDTO = estatus;
	}
	public AreaNormativaDTO getAreaNorm() {
		return areaNorm;
	}
	public void setAreaNorm(AreaNormativaDTO areaNorm) {
		this.areaNorm = areaNorm;
	}
	public int getNumModulos(){
		if(this.modulosDTO!=null)
			return this.modulosDTO.size();
		else 
			return 0;
	}
	public int getNumPerfiles(){
		if(this.perfilesDTO!=null)
			return this.perfilesDTO.size();
		else
			return 0;
	}
	public List<ModuloDTO> getModulosDTO() {
		return modulosDTO;
	}
	public void setModulosDTO(List<ModuloDTO> modulosDTO) {
		this.modulosDTO = modulosDTO;
	}
	public PuestoDTO getPuestoDTO() {
		return puestoDTO;
	}
	public void setPuestoDTO(PuestoDTO puestoDTO) {
		this.puestoDTO = puestoDTO;
	}

	public String getDesTelefonoOfi() {
		return desTelefonoOfi;
	}

	public void setDesTelefonoOfi(String desTelefonoOfi) {
		this.desTelefonoOfi = desTelefonoOfi;
	}
	
	public UmfDTO getUmfDTO() {
		return umfDTO;
	}
	public void setUmfDTO(UmfDTO umfDTO) {
		this.umfDTO = umfDTO;
	}

	public List<PuestoDTO> getPuestosDTO() {
		return puestosDTO;
	}

	public void setPuestosDTO(List<PuestoDTO> puestosDTO) {
		this.puestosDTO = puestosDTO;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getAreaNormativaId(){
		if(areaNorm!=null)
		{
			return areaNorm.getCveSsoareanorma();
		}
		else 
			return 0;
	}

	public long getDepartamentoId(){
		if(dptoDTO!=null)
		{
			return dptoDTO.getCveSsodepto();
		}
		else 
			return 0;
	}

	public long getPuestoId(){
		if(puestoDTO!=null)
		{
			return puestoDTO.getCvePuesto();
		}
		else 
			return 0;
	}

	public long getDelegacionId(){
		if(delDTO!=null)
		{
			return delDTO.getCveDelegacion();
		}
		else 
			return 0;
	}

	public long getSubdelegacionId(){
		if(subdelDTO!=null)
		{
			return subdelDTO.getCveSubelegacion();
		}
		else 
			return 0;
	}

	public long getUmfId(){
		if(umfDTO!=null&&umfDTO.getCveUmf()!=null)
		{
			return umfDTO.getCveUmf();
		}
		else 
			return 0;
	}
	public String getNombreAprobador() {
		if(aprobador!=null)
			return aprobador.getSolicitud().getNombreCompleto()+"-"+aprobador.getSolicitud().getPuestoDTO().getNombrePuesto();
		else 
			return "";
	}
	public String getNssNom() {
		return nssNom;
	}
	public void setNssNom(String nssNom) {
		this.nssNom = nssNom;
	}
	public String getPuestoDescNom() {
		return puestoDescNom;
	}
	public void setPuestoDescNom(String puestoDescNom) {
		this.puestoDescNom = puestoDescNom;
	}
	public String getDepartamentoDescNom() {
		return departamentoDescNom;
	}
	public void setDepartamentoDescNom(String departamentoDescNom) {
		this.departamentoDescNom = departamentoDescNom;
	}
	public String getCveDelegacionNom() {
		return cveDelegacionNom;
	}
	public void setCveDelegacionNom(String cveDelegacionNom) {
		this.cveDelegacionNom = cveDelegacionNom;
	}
	public String getCveSubdelegacionNom() {
		return cveSubdelegacionNom;
	}
	public void setCveSubdelegacionNom(String cveSubdelegacionNom) {
		this.cveSubdelegacionNom = cveSubdelegacionNom;
	}
	public String getCveUmfNom() {
		return cveUmfNom;
	}
	public void setCveUmfNom(String cveUmfNom) {
		this.cveUmfNom = cveUmfNom;
	}
	public long getCveEstatusNom() {
		return cveEstatusNom;
	}
	public void setCveEstatusNom(long cveEstatusNom) {
		this.cveEstatusNom = cveEstatusNom;
	}
	public Long getCveAprobador() {
		return cveAprobador;
	}
	public void setCveAprobador(Long cveAprobador) {
		this.cveAprobador = cveAprobador;
	}
	public String getEstatusNom() {
		if(nssNom!=null&&nssNom.trim().length()>0)
		{
			if(cveEstatusNom==2)
				return "Inactivo";
			else
				return "Activo";
		}
		else
			return "";
	}
	
	
	public String getGruposDesc(){
		if(getPuestosDTO()!=null&&getPuestosDTO().size()>0)
		{
			String result = "";
			for(PuestoDTO p : getPuestosDTO())
				result = result + p.getNombrePuesto();
			result = result.substring(0, result.length()-1);
			return result;
		}
		else
			return "";
		
	}

	public String getModulosDesc(){
		if(getModulosDTO()!=null&&getModulosDTO().size()>0)
		{
			String result = "";
			for(ModuloDTO m : getModulosDTO())
				result = result + m.getDesModulo();
			result = result.substring(0, result.length()-1);
			return result;
		}
		else
			return "";
		
	}
	
	public int getEstatus(){
		return new Integer(estatusDTO.getCveSsoestatus()+"").intValue();
	}

}
