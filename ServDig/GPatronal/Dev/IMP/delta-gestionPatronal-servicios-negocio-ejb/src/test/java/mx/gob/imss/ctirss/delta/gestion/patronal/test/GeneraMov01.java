package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AltaPatronalHelperRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

public class GeneraMov01 {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(GeneraMov01.class);
	}	
	
	@Test
	public void generaMovSindo(){
		
		try {
			log.info("::: Obteniendo EJB y enviando movimiento");
			Long idP = new Long("1335188816");
			Solicitud sol = EjbLocator.getSolicitudServiceBusiness().consultarSolicitudPorId(idP);			
			
			SujetoObligado so = getSO(sol);
			log.debug("::: NumeroRegistroPatronal: " + so.getNumeroRegistroPatronal());
			
			
			
			if(sol != null) {
				log.debug("::: Se obtuvo EJB, obteniendo mov01");
				AltaPatronalHelperRemote ejb = EjbLocator.getAltaPatronalHelperRemote();
				MovimientoPatronalType mov = ejb.createMovimientoPatronalAlta(sol);
				log.debug("movimientoPatronalType");
				log.debug(mov.toString() + "numeroFolio="+mov.getNumeroFolio());
				log.debug("::: Faccion: " + mov.getDivision() + mov.getGrupo() + mov.getFraccion());
				StringBuffer cad = new StringBuffer();
				
				String del = "";
				String subDel = "";
				String folio = "";
				String frac = "";
				
				if(mov.getDelegacionOrigen() < 10) {
					del = "0" + mov.getDelegacionOrigen(); 
				}else {
					del = mov.getDelegacionOrigen()+""; //del
				}
				if(mov.getSubdelegacionOrigen() < 10) {
					subDel = "0" + mov.getSubdelegacionOrigen(); //subdel
				}else {
					subDel = mov.getSubdelegacionOrigen() + ""; //subdel
				}				
				
				if(mov.getNumeroFolio().length() == 4) {
					folio = "0" + mov.getNumeroFolio();
				}else {
					folio = mov.getNumeroFolio();
				}
				
				if(mov.getFraccion() < 10) {
					frac = "0" + mov.getFraccion();
				}else {
					frac = mov.getFraccion()+"";
				}
				
				cad.append(del); //del
				cad.append(subDel); //subdel
				cad.append(mov.getClaveAplicacion()); //CVE-APLIC
				cad.append("0"+mov.getTipoMovimiento());//TP-MOVTO
				cad.append(mov.getOrigenMovimiento()); //ORIGEN-MOV				
				cad.append(folio); //NUM-FOLIO
				cad.append(mov.getArgumento()); //ARGUMENTO
				cad.append(so.getNumeroRegistroPatronal().substring(0, 10)); //rp+mod
				cad.append(so.getNumeroRegistroPatronal().substring(10)); //dv
				DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
				String strDate = dateFormat.format(mov.getFechaMovimiento());
				cad.append(strDate); //F-MOVTO
				cad.append(strDate); //F-RECEP-MOVI
				if(mov.getCurp() != null && mov.getCurp().trim().length() > 0) {
					cad.append(mov.getCurp());//CVE-UNICA
				}else {
					cad.append("                  ");//CVE-UNICA
				}
				
				cad.append(mov.getSubrogacionServicio()); //ID-SUBR-SERV
				cad.append(mov.getClaveMunicipio());//CVE-MPIO
				cad.append(mov.getNombrePatron()); //NOM-PAT
				cad.append(mov.getDomicilioPatron()); //DOM-PAT
				cad.append(mov.getCodigoPostal()); //COD-POST
				cad.append(mov.getLocalidad()); //LOC-PAT
				cad.append(mov.getGiro()); //GIRO
				cad.append(mov.getClase()); //CLASE
				cad.append(mov.getDivision()); //FRACCION
				cad.append(mov.getGrupo()); //FRACCION
				cad.append(frac); //FRACCION
				cad.append("0000000"); //PRIMA
				cad.append("0");//CAUSA-BAJA
				cad.append(mov.getFechaCambioCla());//F-CAMB-CLA
				cad.append("1");//TP-COTIZ
				cad.append("00000000");//F-INIC-HUEL
				cad.append("00000000");//N-EMP-MPIO
				cad.append("          ");//N-REL-CORP
				cad.append(mov.getRfc());//NOMBRE-PAT
				cad.append("       ");
				cad.append(mov.getTipoPago());//TP-PAGO
				cad.append("00");//MES-EMI
				cad.append("0");//ID-SSF
						
				log.debug("Linea:");
				log.debug(cad.toString());
				
			}else {
				log.debug("NO se obtuvo la solicitud");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private SujetoObligado getSO(Solicitud sol){
		Tramite tramite = null;
		
		log.debug("Id-Solicitud: " + sol.getSolicitudId() + " - " + sol.getEstadoSolicitud().getDescripcion());
		
		for(Tramite t : sol.getTramites()){
			if(t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
					t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()){
				tramite = t;
				log.debug("Id-Tramite - " + tramite.getTramiteId() + " - " + tramite.getEstadoTramite().getIdEstadoTramitePersona() + "-" + tramite.getEstadoTramite().getDescripcion());
				break;
			}
		}
		return ((TramiteSujetoObligado)tramite).getSujetoObligado();
	}
	
	
	
}
