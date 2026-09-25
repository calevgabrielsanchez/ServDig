package mx.gob.imss.ctirss.delta.test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.ParametroContactoRequeridoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;

public class EJBLocator {
	
	
	
	public static Context getContextoLocal(){
		Context ic = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			ic = new InitialContext(env);
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
	}
	
	
	public static MediosContactoServiceBusinessRemote getMediosBusinessRemote() {
		
		MediosContactoServiceBusinessRemote service = null;
        try {
            service = (MediosContactoServiceBusinessRemote) getContextoLocal()
                .lookup("mediosContactoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote");
        }
        catch (NamingException e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
		return service;
		
    }
	
	
	public static void main(String args[]){
		EJBLocator tester = new EJBLocator();
		
		tester.buscarContactosPersona();
		tester.buscarContactosrepresentanteLegal();	
		tester.buscarContactoCentroTrabajo();
		tester.obtenerCatalogoTiposContacto();
	}
	
	
	
	private void buscarContactosPersona(){
		
//		try {
			
//			MediosContactoServiceBusinessRemote ejb = (MediosContactoServiceBusinessRemote) 
//					EJBLocator
//					.getContextoLocal()
//					.lookup("mediosContactoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote");
//			
//			
//			Long idPropiertarioContacto = 25129175L;
//			Long idSolicitud = null;
//			PropietarioMedioContactoEnum propietario = PropietarioMedioContactoEnum.PERSONA_FISICA;
//			TipoPersona tipoPersona = new TipoPersona();
//			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
//			
//			Long idPatronSujetoObligado = null;
			
			
//			try {
				
//				List<MedioContacto> medios = ejb.consultarMediosContactoPorTipoPropietario(idPropiertarioContacto, idSolicitud, 
//						propietario, 
//						tipoPersona, 
//						idPatronSujetoObligado);
				
//				for(MedioContacto medio : medios){
//					System.out.println("Tipo: "+medio.getTipoMedioContacto()+ " Medio Contacto: " + medio.getDesFormaContacto());
//				}
				
				
				
//			} catch (PersonaSinMedioDeContactoException e) {
//				e.printStackTrace();
//			} catch (ParametroContactoRequeridoException e) {
//				e.printStackTrace();
//			} catch (SolicitudNoEncontradaException e) {
//				e.printStackTrace();
//			}
//						
//			
//		} catch (NamingException e) {
//			e.printStackTrace();
//			e.printStackTrace();
//		} 
//	
	}
	
	private void buscarContactosrepresentanteLegal(){
//		try {
//			
//			MediosContactoServiceBusinessRemote ejb = (MediosContactoServiceBusinessRemote) 
//					EJBLocator
//					.getContextoLocal()
//					.lookup("mediosContactoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote");
//			
//			
//			Long idPropiertarioContacto = 454L;
//			Long idSolicitud = null;
//			PropietarioMedioContactoEnum propietario = PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL;
//			TipoPersona tipoPersona = new TipoPersona();
//			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
//			
//			Long idPatronSujetoObligado = 1l;
//			
//			
//			try {
//				
//				List<MedioContacto> medios = ejb.consultarMediosContactoPorTipoPropietario(idPropiertarioContacto, idSolicitud, 
//						propietario, 
////						tipoPersona, 
//						idPatronSujetoObligado);
//				
//				for(MedioContacto medio : medios){
//					System.out.println("Tipo: "+medio.getTipoMedioContacto()+ " Medio Contacto: " + medio.getDesFormaContacto());
//				}
//				
//				
//				
//			} catch (PersonaSinMedioDeContactoException e) {
//				e.printStackTrace();
//			} catch (ParametroContactoRequeridoException e) {
//				e.printStackTrace();
//			} catch (SolicitudNoEncontradaException e) {
//				e.printStackTrace();
//			}
//						
//			
//		} catch (NamingException e) {
//			e.printStackTrace();
//		} 
//	
	}
	
	private void buscarContactoCentroTrabajo(){
//		try {
//			
//			MediosContactoServiceBusinessRemote ejb = (MediosContactoServiceBusinessRemote) 
//					EJBLocator
//					.getContextoLocal()
//					.lookup("mediosContactoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote");
//			
//			
//			Long idPropiertarioContacto = 1L;
//			Long idSolicitud = null;
//			PropietarioMedioContactoEnum propietario = PropietarioMedioContactoEnum.CENTRO_TRABAJO;
//			TipoPersona tipoPersona = new TipoPersona();
//			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
//			
//			Long idPatronSujetoObligado = 1l;
//			
//			
//			try {
//				
//				List<MedioContacto> medios = ejb.consultarMediosContactoPorTipoPropietario(idPropiertarioContacto, idSolicitud, 
//						propietario, 
////						tipoPersona, 
//						idPatronSujetoObligado);
//				
//				medios = medios != null ? medios : new ArrayList<MedioContacto>();
//				
//				for(MedioContacto medio : medios){
//					System.out.println("Tipo: "+medio.getTipoMedioContacto()+ " Medio Contacto: " + medio.getDesFormaContacto());
//				}
//				
//				
//				
//			} catch (PersonaSinMedioDeContactoException e) {
//				e.printStackTrace();
//			} catch (ParametroContactoRequeridoException e) {
//				e.printStackTrace();
//			} catch (SolicitudNoEncontradaException e) {
//				e.printStackTrace();
//			}
//						
//			
//		} catch (NamingException e) {
//			e.printStackTrace();
//		} 

	}
	
	
	private void obtenerCatalogoTiposContacto(){
		try {
			
			MediosContactoServiceBusinessRemote ejb = (MediosContactoServiceBusinessRemote) 
					EJBLocator
					.getContextoLocal()
					.lookup("mediosContactoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote");
			
				
			List<TipoContacto> tipos = ejb.consultarTipoContacto();
			
			tipos = tipos != null ? tipos : new ArrayList<TipoContacto>();
			
			for(TipoContacto medio : tipos){
				System.out.println("Tipo: "+medio.getCveIdTipoContacto()+ " Des Tipo Contacto: " + medio.getDesTipoContacto());
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		} 

	}
}
