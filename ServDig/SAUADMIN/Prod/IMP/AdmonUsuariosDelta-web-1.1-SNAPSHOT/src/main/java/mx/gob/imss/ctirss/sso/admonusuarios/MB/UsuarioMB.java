package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import sun.misc.Cleaner;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

@ManagedBean(name="usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AprobadorDTO aprobadorSession;
	
	private long claveAreaNormativa = 1;
	
	private long claveDelegacion = 36;//40;
	private long claveSubdelegacion = 0;//0;//142;
	private long claveUmf = 0;

	private long claveDepartamento = 26;//31; 
	private long clavePuesto = 58; 
	
//	@PostConstruct
//	public void obtieneDatosUsuario()
//	{
//		SolicitudDTO dto  = new SolicitudDTO();
//		
//		if(claveAreaNormativa!=0)
//		{
//			AreaNormativaDTO an = new AreaNormativaDTO();
//			an.setCveSsoareanorma(new Long(claveAreaNormativa));
//			dto.setAreaNorm(an);
//		}
//		else
//		{
//			dto.setAreaNorm(null);
//		}
//		
//		if(claveDepartamento!=0)
//		{
//			DepartamentoDTO depto = new DepartamentoDTO();
//			depto.setCveSsodepto(new Long(claveDepartamento));
//			dto.setDptoDTO(depto);
//		}
//		else
//		{
//			dto.setDptoDTO(null);
//		}
//
//		if(claveDelegacion!=0)
//		{
//			DelegacionDTO del = new DelegacionDTO();
//			del.setCveDelegacion(new Long(claveDelegacion));
//			dto.setDelDTO(del);
//		}
//		else
//		{
//			dto.setDelDTO(null);
//		}
//
//		if(claveSubdelegacion!=0)
//		{
//			SubdelegacionDTO subdel = new SubdelegacionDTO();
//			subdel.setCveSubelegacion(new Long(claveSubdelegacion));
//			dto.setSubdelDTO(subdel);
//		}
//		else
//		{
//			dto.setSubdelDTO(null);
//		}
//
//		if(clavePuesto!=0)
//		{
//			PuestoDTO puesto = new PuestoDTO();
//			puesto.setCvePuesto(new Long(clavePuesto));
//			dto.setPuestoDTO(puesto);
//		}
//		else
//		{
//			dto.setPuestoDTO(null);
//		}
//		if(claveUmf!=0)
//		{
//			UmfDTO umf = new UmfDTO();
//			umf.setCveUmf(clavePuesto+"");
//			dto.setUmfDTO(umf);
//		}
//		else
//		{
//			dto.setUmfDTO(null);
//		}
//		aprobadorSession = new AprobadorDTO();
//		aprobadorSession.setSolicitud(dto);
//		aprobadorSession.getSolicitud().getAreaNorm().setCveSsoareanorma(getAreaNormativa());
//	}

	public AprobadorDTO getAprobadorSession() {
		return aprobadorSession;
	}

	public void setAprobadorSession(AprobadorDTO aprobador) {
		this.aprobadorSession = aprobador;
	}

//	public long getAreaNormativa(){
//		if(aprobadorSession.getSolicitud().getAreaNorm()!=null)
//		{
//			if(aprobadorSession.getSolicitud().getDelDTO()!=null)
//			{
//				if(aprobadorSession.getSolicitud().getSubdelDTO()!=null)
//				{
//					if(aprobadorSession.getSolicitud().getUmfDTO()==null)
//						return 3;
//				}
//				else
//					return 2;
//			}
//			else
//				return  1;
//		}
//		return 0;
//	}

	public long getAreaNormativa(){
		if(aprobadorSession!=null&&aprobadorSession.getSolicitud()!=null&&aprobadorSession.getSolicitud().getDptoDTO()!=null&&aprobadorSession.getSolicitud().getDptoDTO().getAreaNormativa()!=null)
			return aprobadorSession.getSolicitud().getDptoDTO().getAreaNormativa().getCveSsoareanorma();
		else
			return 0;
	}

	public String getCorreoElectronico()
	{
		return aprobadorSession.getSolicitud().getRefCorreoElectronico();
	}
	

}
