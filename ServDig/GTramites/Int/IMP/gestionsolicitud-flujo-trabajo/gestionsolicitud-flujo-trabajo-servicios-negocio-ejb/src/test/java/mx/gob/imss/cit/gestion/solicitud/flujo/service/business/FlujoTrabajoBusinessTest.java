package mx.gob.imss.cit.gestion.solicitud.flujo.service.business;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;

public class FlujoTrabajoBusinessTest {

	private transient FlujoTrabajoRemote flujoTrabajoRemote = EjbLocator.getFlujoTrabajoRemote();

	@Test
	public void iniciarTramite() {
		try {
			InicioTramite inicioTramite = new InicioTramite();
			inicioTramite.setFolio("12346");
			inicioTramite.setIdTramite(1000);
			inicioTramite.setData("{\"responsableRegistro\":\"RGHJ\",\"nssInvolucrados\":\"96109061621\",\"subdelegacion\":\"139\",\"origen\":\"VENTANILLA\"}");
			inicioTramite.setEstatus("Iniciado");
			inicioTramite.setFechaSolicitud("08/10/2016");
			inicioTramite.setFechaActualizacion("08/10/2016");
			Map<String, String> participantes = new HashMap<String, String>();
			participantes.put(ParticipantesEnum.RESPONSABLE.getDescripcion(), "RYEA");
			participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion(), "BPM_ADMIN139");
			inicioTramite.setParticipantes(participantes);
			System.out.println(flujoTrabajoRemote.iniciarWorkFlow(ProcesosNegocioEnum.CDA.getId(), inicioTramite));

		} catch (BPMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void atender() {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setEstado("En espera de autorizaci\u00F3n");
		mensajeTarea.setFechaActualizacion("09/10/2016");
		mensajeTarea.setTipoTransicion(TipoTransicionEnum.PRINCIPAL.getId());
		mensajeTarea.setEstado("En espera de autorizaci\u00F3n");
		try {
			flujoTrabajoRemote.completarTarea(1775L, mensajeTarea);
		} catch (BPMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void atenderCancelar() {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setEstado("Cancelado");
		mensajeTarea.setFechaActualizacion("09/10/2016");
		mensajeTarea.setObservacion("Se cancela el tramite!!");
		mensajeTarea.setTipoTransicion(TipoTransicionEnum.ALTERNATIVA1.getId());
		try {
			flujoTrabajoRemote.completarTarea(1774L, mensajeTarea);
		} catch (BPMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void autorizar() {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setEstado("Autorizado");
		mensajeTarea.setFechaActualizacion("09/10/2016");
		mensajeTarea.setTipoTransicion(TipoTransicionEnum.ALTERNATIVA1.getId());
		Map<String, String> participantes = new HashMap<String, String>();
		participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion(), "MALA");
		mensajeTarea.setParticipantes(participantes);
		
		try {
			flujoTrabajoRemote.completarTarea(1486L, mensajeTarea);
		} catch (BPMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void canceladoAutorizar() {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setEstado("Cancelado");
		mensajeTarea.setFechaActualizacion("09/10/2016");
		mensajeTarea.setTipoTransicion(TipoTransicionEnum.ALTERNATIVA1.getId());
		mensajeTarea.setObservacion("Se cancela por autorizador");
		try {
			flujoTrabajoRemote.completarTarea(26L, mensajeTarea);
		} catch (BPMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void reasignarResponsable() {		
		try {
			flujoTrabajoRemote.reasignarTarea("PJJT", 1776L,null);
		} catch (BPMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void rechazarAutorizacion() {
		MensajeTarea mensajeTarea = new MensajeTarea();
		mensajeTarea.setEstado("En espera por responsable");
		mensajeTarea.setFechaActualizacion("09/10/2016");
		mensajeTarea.setObservacion("Se rechaza el tramite");
		mensajeTarea.setTipoTransicion(TipoTransicionEnum.PRINCIPAL.getId());
		try {
			flujoTrabajoRemote.completarTarea(26L, mensajeTarea);
		} catch (BPMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void consultarBandejasSubdelegacion() {
		DataPage dataPage = new DataPage();
		dataPage.setPageSize(10);
		dataPage.setCurrentPage(1);
		System.out.println("---RESPONSABLE---");
		if(((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "RYEA", null).getData()) != null && !((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "RYEA", null).getData()).isEmpty())
		for(TareaBandeja tareaBandeja:((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "RYEA", null).getData())){
			System.out.println(tareaBandeja.getIdTramite());
		}
		
		dataPage.setPageSize(10);
		dataPage.setCurrentPage(1);
		System.out.println("---AUTORIZADOR---");
		if(((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorSubdelegacion(dataPage, "139", null).getData())!= null && !((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorSubdelegacion(dataPage, "139", null).getData()).isEmpty())
		for(TareaBandeja tareaBandeja:((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorSubdelegacion(dataPage, "139", null).getData())){
			System.out.println(tareaBandeja.getIdTramite());
		}
		
		System.out.println("---RESPONSABLE---");
		if(((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "PJJT", null).getData()) != null && !((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "PJJT", null).getData()).isEmpty())
			for(TareaBandeja tareaBandeja:((List<TareaBandeja>)flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "PJJT", null).getData())){
				System.out.println(tareaBandeja.getIdTramite());
		}
		

	}

	@Test
	public void consultarBandejas() {
		DataPage dataPage = new DataPage();
		dataPage.setPageSize(10);
		dataPage.setCurrentPage(1);
		System.out.println("---RESPONSABLE---");
		System.out.println(((List<TareaUsuario>)flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "RYEA", null).getData()).size());
//		dataPage.setPageSize(10);
//		dataPage.setCurrentPage(1);
//		System.out.println("---AUTORIZADOR---");
//		System.out.println(flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "ASVA"));
//		dataPage.setPageSize(10);
//		dataPage.setCurrentPage(1);
//		System.out.println("---AUTORIZADOR---");
//		System.out.println(flujoTrabajoRemote.obtenerTareasPorUsuario(dataPage, "PJJT"));
	}
	
	@Test
	public void consultarHistoricos() {
		DataPage dataPage = new DataPage();
		dataPage.setPageSize(10);
		dataPage.setCurrentPage(1);
		dataPage = flujoTrabajoRemote.obtenerInstanciasHistoricasPorUsuario(dataPage, "TOME860312HMCRRR12", null);
		for(TareaBandeja tareaBandeja:(List<TareaBandeja>)dataPage.getData()){
			System.out.println(tareaBandeja.getInicioTramite().getEstatus());
			System.out.println(tareaBandeja.getInicioTramite().getParticipantes());		
		}
		
	}


}
