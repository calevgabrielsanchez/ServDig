package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.util.Date;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

public class ConsultaSolicitud {

//	@Test
	public void getSolicitud(){
		System.out.println("::: Inicia ");
		SolicitudServiceBusinessRemote solEJB = EjbLocator.getSolicitudServiceBusiness();	
		Solicitud solicitud = solEJB.consultarSolicitudPorId(new Long("74988758"));
		System.out.println("::: //////////////////////////////////////////////////////////////////");
//		System.out.println(solicitud.getSujetoObligado());
		System.out.println("::: //////////////////////////////////////////////////////////////////");
				
		for(Tramite tramite : solicitud.getTramites()){
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			System.out.println("::: //////////////////////TRAMITE////////////////////////////////////////////");
			System.out.println(tso.getSolicitudEnProcesoOSB());			
		}
		System.out.println("::: Termine");
	}
	
	
    @Test
    public void testBuscarPersonaMoralPorId() {
    	PersonaMoralBusinessRemote personaMoralBusiness = EjbLocator.getPersonaMoralBusiness();
    	
        Moral personaMoral = new Moral();
        personaMoral.setIdPersona(new Long("5276563"));
        System.out.println(personaMoralBusiness.getPersonaMoral(personaMoral));
    }
	
	
//	@Test
	public void setSolicitudEnProcesoOSB(){
		System.out.println("::: Inicia, " + new Date());
		SolicitudServiceBusinessRemote solEJB = EjbLocator.getSolicitudServiceBusiness();	
		Solicitud solicitud = new Solicitud();		
		//solicitud = solEJB.consultarSolicitudPorId(new Long("74988758"));	
		solicitud.setSolicitudId(new Long("74989941"));
		try {
			System.out.println("****Validacion de estado para cveIdSolcitud " + solicitud.getSolicitudId() + ", " + new Date());			
			solicitud = solEJB.validaEstadoSolicitudEnOSB(solicitud);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
		System.out.println("::: Termine, " + new Date());
	}
	
	private Solicitud validaEstadoSolicitudEnOSB(Solicitud solicitud, SolicitudServiceBusinessRemote solEJB) throws GestionPatronalBusinessException{
		for(Tramite tramiteAlmacenado: solicitud.getTramites()) {
			if(tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
					tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()) {
				TramiteSujetoObligado tr = (TramiteSujetoObligado)tramiteAlmacenado;
				System.out.println("::: Estado de Proceso en OSB: " + tr.getSolicitudEnProcesoOSB() + ", solicitud: "+solicitud.getSolicitudId()+", " + new Date());
				if(tr.getSolicitudEnProcesoOSB() == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()) {
					System.out.println("::: La solicitud " + solicitud.getSolicitudId() + ", ya esta en proceso en el OSB, " + new Date());
					throw new GestionPatronalBusinessException("La solicitud se esta procesando, por favor espere");
				}else {
					System.out.println("::: La solicitud " + solicitud.getSolicitudId() + " aun NO es procesada en el OSB, " + new Date());
					solicitud = solEJB.actualizarTramiteAltaEnProcesoOSB(solicitud, EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue());
					System.out.println("::: La solicitud "+solicitud.getSolicitudId()+" fue actualizada con la marca de proceso, " + new Date());
					for(Tramite tramite : solicitud.getTramites()){
						TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
						System.out.println("::: //////////////////////TRAMITE////////////////////////////////////////////");
						System.out.println("::: Estado actual de la solitud " + solicitud.getSolicitudId() + ", " + tso.getSolicitudEnProcesoOSB());			
					}
					
				}
			}
		}
		return solicitud;
	}
	
//	@Test
	public void setSolicitudEnProcesoOSB2(){
		System.out.println("::: Inicia a poner otra marca");
		SolicitudServiceBusinessRemote solEJB = EjbLocator.getSolicitudServiceBusiness();	
		Solicitud solicitud = new Solicitud();		
		//solicitud.setSolicitudId(new Long(74988758));
		solicitud = solEJB.consultarSolicitudPorId(new Long("74989941"));	
		
		solicitud = solEJB.actualizarTramiteAltaEnProcesoOSB(solicitud, 0);
		for(Tramite tramite : solicitud.getTramites()){
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			System.out.println("::: //////////////////////TRAMITE////////////////////////////////////////////");
			System.out.println(tso.getSolicitudEnProcesoOSB());			
		}
		
		System.out.println("::: Termine");
	}
	
	
}
