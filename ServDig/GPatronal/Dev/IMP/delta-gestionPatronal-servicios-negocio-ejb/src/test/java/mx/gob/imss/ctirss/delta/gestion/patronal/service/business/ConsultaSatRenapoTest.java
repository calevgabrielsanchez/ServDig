package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnSATServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnSATServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;


public class ConsultaSatRenapoTest {

	
//	@Test
	public void imprimeSitSatRENAPOCont() {
		
		System.out.println("::: Consultando solicitud");
		String idSol = "1198004290";
		Solicitud solicitudAlta = EjbLocator.getSolicitudServiceBusiness().consultarSolicitudPorId(new Long(idSol));
		SujetoObligado soTr = getSO(solicitudAlta);
		String rfc = StringUtils.isNotBlank(soTr.getFisica().getRfc()) ? soTr.getFisica().getRfc().toUpperCase() : soTr.getFisica().getRfc();
		String curp = soTr.getFisica().getCurp() !=null ? soTr.getFisica().getCurp(): "----";
		
		System.out.println("::: rfc: " + rfc + ", curp: " + curp);
		
		
		System.out.println(":: Obteniendo EJB");
		PersonaBusinessRemote personaBusiness = EjbLocator.getPersonaBusinessRemote();
		
		try {
			Fisica pf = personaBusiness.buscarPFPorRfcEnSatSitCont(rfc);
			System.out.println(":: Resultados: ");
			System.out.println("CURP SAT: " + pf.getCurp() + "-" + pf.getCurpRenapo());
			System.out.println("pf.getSituacionesSAT().size(): " + pf.getSituacionesSAT().size());
			for (Iterator<SituacionSAT> iterator = pf.getSituacionesSAT().iterator(); iterator.hasNext();) {
				SituacionSAT sitSAT = iterator.next();
				System.out.println("-- " + sitSAT.getCveSituacionSAT() + " : " + sitSAT.getDescripcion());
			}
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		}
		
		System.out.println(":: Obteniendo EJB");
		LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote ejb = EjbLocator.getLocalizarPersonaFisicaEnRENAPOServiceBusiness();
		Fisica fisica = null;

		try {
			System.out.println(":: Consultando CURP: " + curp);
			fisica = ejb.localizarPersonaFisicaEnRENAPOxCURP(curp);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		}
		
		System.out.println("::: CURP: " + fisica.getCurp() + "-" + fisica.getCurpRenapo() + "-" + fisica.getCveEstatusRenapo() + "-" + fisica.getEstatusRenapo());
		if(fisica.getCurpsHistoricas() != null)
			System.out.println(":: CURP Historicas: " + fisica.getCurpsHistoricas().toString());
		
	}
	
	
	@Test
	public void revisaSituacionContribuyentePF() {
		
		try {
			PersonaFisicaServiceBusinessRemote ejb = EjbLocator.getPersonaServiceBusiness();
			System.out.println("::: Obtuve EJB");

			Fisica fisica = new Fisica();

//			fisica.setRfc("OATM5403054X9");
//			fisica.setCurp("OATM540305HTCCLR12");

			fisica.setRfc("MIPA840302NSA");
			fisica.setCurp("MIPA840302HDFLLL08");

			// CURP EN BAJA
//			fisica.setRfc("NIVR530326N64");
//			fisica.setCurp("NIVR530326MDFMLS04");

//			fisica.setRfc("RARV810816DXA");
//			fisica.setCurp("RARV810816HDFMBC04");

//			fisica.setRfc("AAMJ811020AQ6"); //17231639452131199170847
//			fisica.setCurp("AAMJ811020HTCLNN02");

			
			
			
			

			System.out.println("::: Revisando situacion del contribuyente para PF");
			fisica = ejb.revisaSituacionContribuyente(fisica);

			
		} catch (ErrorComparacionDatosSATException e) {
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		System.out.println("Termine");
	}

//	@Test
	public void revisaSituacionContribuyentePM() {

		try {
			System.out.println(":: Obteniendo EJB");
			PersonaMoralBusinessRemote pmService = EjbLocator.getPersonaMoralBusinessRemote();
			
			Moral objMoralRecuperado = new Moral();
			objMoralRecuperado.setRfc("NWM9709244W4"); //SDN150105KR4  CPC900424UJ5  NWM9709244W4

			System.out.println("::: Revisando situacion del contribuyente para PM, persona.getIdPersona(): " + objMoralRecuperado.getIdPersona());
			objMoralRecuperado = pmService.revisaSituacionContribuyente(objMoralRecuperado);

			System.out.println("::: La situacion es correcta imprimiendo sitaciones SAT");
			for (Iterator<SituacionSAT> iterator = objMoralRecuperado.getSituacionesSAT().iterator(); iterator.hasNext();) {
				SituacionSAT sitSAT = iterator.next();
				System.out.println("-- " + sitSAT.getCveSituacionSAT() + " : " + sitSAT.getDescripcion());
			}	
			
		} catch (ErrorComparacionDatosSATException e) {
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		}
		
		System.out.println("Termine");
	}
	
//	@Test
	public void revisaSituacionContribuyenteRENAPO() {
		String rfc = "RARV810816DXA";
		String curp = "NIVR530326MDFMLS04";
		
		System.out.println(":: Obteniendo EJB");
		try {
			System.out.println("::Buscando en SAT");
			PersonaBusinessRemote pb = EjbLocator.getPersonaBusinessRemote();
			Fisica pfSAT = pb.buscarPFPorRfcEnSatSitCont(rfc);
			
			System.out.println("::Buscando en RENAPO");			
			LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote ejbR = EjbLocator.getLocalizarPersonaFisicaEnRENAPOServiceBusiness();
			Fisica pfRENAPO = ejbR.localizarPersonaFisicaEnRENAPOxCURP(curp);
			
			System.out.println("::Revisando situacion contribuyente con RENAPO");
			System.out.println(":: Clave RENAPO: " + pfRENAPO.getCveEstatusRenapo() + "-" + pfRENAPO.getEstatusRenapo());
			List<String> curps = pfRENAPO.getCurpsHistoricas();
			for (Iterator<String> iterator = curps.iterator(); iterator.hasNext();) {
				System.out.println(":: CURP historica " + iterator.next());
			}

			IndividuoServiceBusinessRemote ejb = EjbLocator.getIndividuoServiceBusiness();
			ejb.revisaSituacionContribuyenteRENAPO(rfc, pfSAT.getCurp(), pfRENAPO);			
			
			System.out.println(":: La situacion con RENAPO es correcta");
			
		} catch (ErrorComparacionDatosSATException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		}		
		
	}
	
	

//	@Test
	public void buscarPMPorRfcEnSatSitCont() {
		String rfc = "CPC900424UJ5"; //SDN150105KR4  CPC900424UJ5
		System.out.println(":: Obteniendo EJB");
		PersonaBusinessRemote personaBusiness = EjbLocator.getPersonaBusinessRemote();
		
		try {
			Moral pf = personaBusiness.buscarPMPorRfcEnSatSitCont(rfc);
			System.out.println(":: Resultados " + rfc + ":");
			System.out.println("pf.getSituacionesSAT().size(): " + pf.getSituacionesSAT().size());
			for (Iterator<SituacionSAT> iterator = pf.getSituacionesSAT().iterator(); iterator.hasNext();) {
				SituacionSAT sitSAT = iterator.next();
				System.out.println("-- " + sitSAT.getCveSituacionSAT() + " : " + sitSAT.getDescripcion());
		}
			
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
//	@Test
	public void buscarPFPorRfcEnSatSitCont() {
		String rfc = "GOSM841213NZ3";
		System.out.println(":: Obteniendo EJB");
		PersonaBusinessRemote personaBusiness = EjbLocator.getPersonaBusinessRemote();
		
		try {
			Fisica pf = personaBusiness.buscarPFPorRfcEnSatSitCont(rfc);
			//Fisica pf = personaBusiness.buscarPersonaFisicaPorRfcEnSat(rfc);
			System.out.println(":: Resultados: ");
//			System.out.println(pf.toString());
			System.out.println("CURP SAT: " + pf.getCurp() + "-" + pf.getCurpRenapo());
			System.out.println("pf.getSituacionesSAT().size(): " + pf.getSituacionesSAT().size());
			for (Iterator<SituacionSAT> iterator = pf.getSituacionesSAT().iterator(); iterator.hasNext();) {
				SituacionSAT sitSAT = iterator.next();
				System.out.println("-- " + sitSAT.getCveSituacionSAT() + " : " + sitSAT.getDescripcion());

//				System.out.println("------------------------------------------------------------------");
//				System.out.println(sitSAT);
//				System.out.println("------------------------------------------------------------------");
			}
			
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
    @Test
    public void testBuscarPersonaFisicaMoral() throws SolicitudNoEncontradaException, ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException {

    	System.out.println("::: Obteniendo EJB - " + new Date());
    	PersonaBusinessRemote personaBusiness = EjbLocator.getPersonaBusiness();
    	System.out.println(":: Buscando persona - " + new Date());
        // BUSCA EN RENAPO
        Fisica persona = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo("MOCH430524XCLLSP05");
    	System.out.println(":: Obtuve persona - " + new Date());        
        System.out.println("persona: " + persona);

    }	
	
	
	
	
	@Test
	public void localizarPersonaFisicaEnRENAPOxCURP() {
		
		//String CURP = "SAMJ810311HGRNNN28";
		//String CURP = "SAMJ810311HGRNNN02";
		//String CURP = "ZUNA540308MNELTN05";
		
		//Con baja
		//String CURP = "RARV810816HDFMBC04"; //NIVR530326MDFMLS04
		String CURP = "MOCH430524XCLLSP05";
		
		System.out.println(":: Obteniendo EJB");
		LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote ejb = EjbLocator.getLocalizarPersonaFisicaEnRENAPOServiceBusiness();
		Fisica fisica = null;

		try {
			System.out.println(":: Consultando CURP: " + CURP);
			fisica = ejb.localizarPersonaFisicaEnRENAPOxCURP(CURP);
			System.out.println(fisica.toString());
			
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		}
		
		System.out.println("::: CURP: " + fisica.getCurp() + "-" + fisica.getCurpRenapo() + "-" + fisica.getCveEstatusRenapo() + "-" + fisica.getEstatusRenapo());
		
		System.out.println(":: CURP Historicas: " + fisica.getCurpsHistoricas().toString());
		
		System.out.println("::: Termine");
	}
	
//	@Test
	public void localizarPersonaFisicaEnSATxRFC() {
		
		String RFC = "OATM5403054X9";
		
		System.out.println(":: Obteniendo EJB");
		LocalizarPersonaFisicaEnSATServiceBusinessRemote ejb = EjbLocator.getLocalizarPersonaFisicaEnSATServiceBusiness();
		Fisica fisica = null;

		System.out.println(":: Consultando RFC: " + RFC);
		
		try {
			fisica = ejb.localizarPersonaFisicaEnSATxRFC(RFC);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		}
		System.out.println(fisica.toString());

		System.out.println("::: RFC: " + fisica.getRfc() + ", CURP: " + fisica.getCurp() + "-" + fisica.getCurpRenapo());
		
		if( !(fisica.getSituacionesSAT() == null || fisica.getSituacionesSAT().size() == 0) ) {
			SituacionSAT situacionSAT = null;
			for (Iterator<SituacionSAT> iterator = fisica.getSituacionesSAT().iterator(); iterator.hasNext();) {
				situacionSAT = iterator.next();
				System.out.println(":: Situacion SAT: " + situacionSAT.getCveSituacionSAT() + "-" + situacionSAT.getIdSituacionSAT() + "-" + situacionSAT.getDescripcion());
			}
		}else {
			System.out.println(":: NO se obtuvo situación SAT");
		}
		

		
		System.out.println("::: Termine");
	}
	
	
//	@Test
	public void localizarPersonaMoralEnSATxRFC() {
		
		String RFC = "PYC0912224Y7"; //CAL190705FJ6
		
		System.out.println(":: Obteniendo EJB");
		LocalizarPersonaMoralEnSATServiceBusinessRemote ejb = EjbLocator.getLocalizarPersonaMoralEnSATServiceBusiness();
		Moral moral = null;

		System.out.println(":: Consultando RFC: " + RFC);
		
		try {
			moral = ejb.localizarPersonaMoralEnSATxRFC(RFC);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		}
		System.out.println(moral.toString());
		

		System.out.println("::: RFC: " + moral.getRfc() + "-" + moral.getRfcOriginal() + "-" + moral.getRfcSat() + "-" + moral.getRfcSolicitado() + "-" + moral.getRfcVigente());
		
		if( !(moral.getSituacionesSAT() == null || moral.getSituacionesSAT().size() == 0) ) {
			SituacionSAT situacionSAT = null;
			for (Iterator<SituacionSAT> iterator = moral.getSituacionesSAT().iterator(); iterator.hasNext();) {
				situacionSAT = iterator.next();
				System.out.println(":: Situacion SAT: " + situacionSAT.getCveSituacionSAT() + "-" + situacionSAT.getIdSituacionSAT() + "-" + situacionSAT.getDescripcion());
			}
		}else {
			System.out.println(":: NO se obtuvo situación SAT");
		}

		System.out.println("::: Termine");
	}

	
	private SujetoObligado getSO(Solicitud sol){
		Tramite tramite = null;
		
		for(Tramite t : sol.getTramites()){
			if(t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
					t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()){
				tramite = t;
				System.out.println("Id-Tramite - "
						+ sol.getSolicitudId()
						+ "|"
						+ tramite.getTramiteId()
						+ " - "
						+ sol.getEstadoSolicitud().getIdEstadoSolicitud()
						+ " - "
						+ sol.getEstadoSolicitud().getDescripcion()
						+ " - "
						+ tramite.getEstadoTramite()
								.getIdEstadoTramitePersona() + "-"
						+ tramite.getEstadoTramite().getDescripcion());
				break;
			}
		}
		
		SujetoObligado so = ((TramiteSujetoObligado)tramite).getSujetoObligado();
		
		return ((TramiteSujetoObligado)tramite).getSujetoObligado();
	}
}

