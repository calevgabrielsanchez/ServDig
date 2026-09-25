package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

public class BajaDerechohabienteDto implements Serializable {

	private static final long serialVersionUID = 504357498351637381L;

	private Long cveIdBaja;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizaco;
	private Long cveIdPersonaIntegrante;
	private Long cveIdAsignacionNSS;
	private Long cveIdTipoBajaDer;
	private Long indBajaActiva;
	private Long cveIdTramite;
	private String matricula;
	private String motivo;
	private String fundamentoLegal;

	public Long getCveIdBaja() {
		return cveIdBaja;
	}

	public void setCveIdBaja(Long cveIdBaja) {
		this.cveIdBaja = cveIdBaja;
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

	public Date getFecRegistroActualizaco() {
		return fecRegistroActualizaco;
	}

	public void setFecRegistroActualizaco(Date fecRegistroActualizaco) {
		this.fecRegistroActualizaco = fecRegistroActualizaco;
	}

	public Long getCveIdPersonaIntegrante() {
		return cveIdPersonaIntegrante;
	}

	public void setCveIdPersonaIntegrante(Long cveIdPersonaIntegrante) {
		this.cveIdPersonaIntegrante = cveIdPersonaIntegrante;
	}

	public Long getCveIdAsignacionNSS() {
		return cveIdAsignacionNSS;
	}

	public void setCveIdAsignacionNSS(Long cveIdAsignacionNSS) {
		this.cveIdAsignacionNSS = cveIdAsignacionNSS;
	}

	public Long getCveIdTipoBajaDer() {
		return cveIdTipoBajaDer;
	}

	public void setCveIdTipoBajaDer(Long cveIdTipoBajaDer) {
		this.cveIdTipoBajaDer = cveIdTipoBajaDer;
	}

	public Long getIndBajaActiva() {
		return indBajaActiva;
	}

	public void setIndBajaActiva(Long indBajaActiva) {
		this.indBajaActiva = indBajaActiva;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	
	@Override
	public boolean equals(Object obj) {
		
		try{
			
			if (obj == this)
				return true;
			
			if( obj instanceof BajaDerechohabienteDto  ){
				BajaDerechohabienteDto baja = (BajaDerechohabienteDto)obj; 
				
				return (this.cveIdAsignacionNSS.equals(baja.cveIdAsignacionNSS)  &&  this.cveIdPersonaIntegrante.equals(baja.cveIdPersonaIntegrante) );
			}	
				
			if (!(obj instanceof GrupoFamiliar))
				return false;	
			
			Derechohabiente integrante = ((GrupoFamiliar)obj).getDerechohabiente();
			
			return ( integrante.getIdPersona().equals(this.cveIdPersonaIntegrante) && integrante.getAsignacionNSS().getIdAsignacionNSS().equals(this.cveIdAsignacionNSS) );
		
		}catch(Exception e){
			System.out.println("Ha ocurrido un error inesperado");
		}
		
		
		return false;
		
	}
	
	@Override
	public int hashCode(){
		
		try{
			return Double.valueOf(this.cveIdPersonaIntegrante+""+this.cveIdAsignacionNSS).hashCode();
		}catch(Exception e){
			System.out.println("Ha ocurrido un error inesperado");
		}
		
		return 0;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFundamentoLegal() {
		return fundamentoLegal;
	}

	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}
	
	
}
