package test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IdentificadoresPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLocator {

    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(EjbLocator.class);
    }

    private static Context getContexto() {
        Context iCtx = null; // NOPMD
        try {
            final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            env.put(Context.PROVIDER_URL, "t3://localhost:7001");
            env.put(Context.SECURITY_PRINCIPAL, "weblogic");
            env.put(Context.SECURITY_CREDENTIALS, "admin2012");
            iCtx = new InitialContext(env);

		} catch (NamingException e) {
			LOG.error("In getContexto()", e);
		}
		return iCtx;
    }

    public static PortalCiudadanoServiceBusinessRemote getPortalCiudadanoService() {
    	
    	PortalCiudadanoServiceBusinessRemote ejb = null;
    	 try {
             ejb = (PortalCiudadanoServiceBusinessRemote) getContexto().lookup("portalCiudadanoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote");

         } catch (NamingException e) {
             LOG.error("In getPersonaBusiness()", e);
         }
    	return ejb;
    }
    
    public static PersonaFisicaServiceBusinessRemote getPersonaFisicaBusiness() {
    	 PersonaFisicaServiceBusinessRemote ejb = null; // NOPMD
         try {
             ejb = (PersonaFisicaServiceBusinessRemote) getContexto().lookup("personaBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote");

         } catch (NamingException e) {
             LOG.error("In getPersonaBusiness()", e);
         }
         return ejb;
    }
    
    public static PersonaBusinessRemote getPersonaBusiness() {
        PersonaBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (PersonaBusinessRemote) getContexto().lookup("personaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote");

        } catch (NamingException e) {
            LOG.error("In getPersonaBusiness()", e);
        }
        return ejb;
    }

    public static PersonaMoralBusinessRemote getPersonaMoralBusiness() {
        PersonaMoralBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (PersonaMoralBusinessRemote) getContexto().lookup("personaMoralBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote");

        } catch (NamingException e) {
            LOG.error("In getPersonaBusiness()", e);
        }
        return ejb;
    }

    public static ServiciosPersonaBusinessRemote getServiciosPersonaBusiness() {
        ServiciosPersonaBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (ServiciosPersonaBusinessRemote) getContexto().lookup("serviciosPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote");

        } catch (NamingException e) {
            LOG.error("In getServiciosPersonaBusiness()", e);
        }
        return ejb;
    }

    public static SolicitudPersonaBusinessRemote getSolicitudPersonaBusiness() {
        SolicitudPersonaBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (SolicitudPersonaBusinessRemote) getContexto().lookup("solicitudPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getSolicitudPersonaBusiness()", e);
        }
        return ejb;
    }
    
    /**
     * 191807 110912
     * Este metodo obtiene el EJB de consultar personas fisicas de lucy para nacho
     * @return
     */
    public static ConsultaPersonaFisicaServiceBusinessRemote getConsultaPersonaFisicaServiceBusiness(){
    	ConsultaPersonaFisicaServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (ConsultaPersonaFisicaServiceBusinessRemote) getContexto().lookup("consultaPersonaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getConsultaPersonaFisicaServiceBusiness()", e);
        }catch(Exception e){
        	LOG.error("In getConsultaPersonaFisicaServiceBusiness()", e);
        }
        return ejb;
    }
    
    /**
     * 191807 110912
     * Este metodo obtiene el EJB de complementar las calificaciones personas fisicas de lucy para nacho
     * @return
     */
    public static ComplementarCalificacionPersonaFisicaServiceBusinessRemote getComplementarCalificacionPersonaFisicaServiceBusiness(){
    	ComplementarCalificacionPersonaFisicaServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (ComplementarCalificacionPersonaFisicaServiceBusinessRemote) getContexto().lookup("complementarCalificacionPersonaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaFisicaServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getComplementarCalificacionPersonaFisicaServiceBusiness()", e);
        }catch(Exception e){
        	LOG.error("In getComplementarCalificacionPersonaFisicaServiceBusiness()", e);
        }
        return ejb;
    }
    
    /**
     * 191807 120912
     * Este metodo obtiene el EJB para localizar a una persona fisica en entidades externas, de acuerdo al diagrama N2
     * @return
     */
    public static LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote getLocalizarPersonaFisicaEnEntidadesExternasServiceBusiness(){
    	LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote) getContexto().lookup("localizarPersonaFisicaEnEntidadesExternasServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In geLocalizarPersonaFisicaEnEntidadesExternasServiceBusiness()", e);
        }catch(Exception e){
        	LOG.error("In geLocalizarPersonaFisicaEnEntidadesExternasServiceBusiness()", e);
        }
        return ejb;
    }
    
    /**
     * 191807 130912
     * Este metodo obtiene el EJB de consultar personas morales de lucy para nacho
     * @return
     */
    public static ConsultaPersonaMoralServiceBusinessRemote getConsultaPersonaMoralServiceBusiness(){
    	ConsultaPersonaMoralServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (ConsultaPersonaMoralServiceBusinessRemote) getContexto().lookup("consultaPersonaMoralServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaMoralServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getConsultaPersonaMoralServiceBusiness()", e);
        }catch(Exception e){
        	LOG.error("In getConsultaPersonaMoralServiceBusiness()", e);
        }
        return ejb;
    }

    /**
     * 191807 130912
     * Este metodo obtiene el EJB de complementar las calificaciones personas morales de lucy para nacho
     * @return
     */
    public static ComplementarCalificacionPersonaMoralServiceBusinessRemote getComplementarCalificacionPersonaMoralServiceBusiness(){
    	ComplementarCalificacionPersonaMoralServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (ComplementarCalificacionPersonaMoralServiceBusinessRemote) getContexto().lookup("complementarCalificacionPersonaMoralServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaMoralServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getComplementarCalificacionPersonaMoralServiceBusiness()", e);
        }catch(Exception e){
        	LOG.error("In getComplementarCalificacionPersonaMoralServiceBusiness()", e);
        }
        return ejb;
    }
    
    /**
     * 191807 130912
     * Este metodo obtiene el EJB para localizar a una persona moral en entidades externas, de acuerdo al diagrama N2
     * @return
     */
    public static LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote getLocalizarPersonaMoralEnEntidadesExternasServiceBusiness(){
    	LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote) getContexto().lookup("localizarPersonaMoralEnEntidadesExternasServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getLocalizarPersonaMoralEnEntidadesExternasServiceBusiness()", e);
        }catch(Exception e){
        	LOG.error("In getLocalizarPersonaMoralEnEntidadesExternasServiceBusiness()", e);
        }
        return ejb;
    }
    
    /**
     * 191807 140912
     * Este metodo obtiene el EJB para llamar a los metodos relacionados con el identificador de persona fisica
     * @return
     */
    public static IdentificadoresPersonaFisicaServiceBusinessRemote getIdentificadorPersonaFisicaServiceBusinessRemote(){
    	IdentificadoresPersonaFisicaServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (IdentificadoresPersonaFisicaServiceBusinessRemote) getContexto().lookup("identificadorPersonaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IdentificadorPersonaFisicaServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getIdentificadorPersonaFisicaServiceBusinessRemote()", e);
        }catch(Exception e){
        	LOG.error("In getIdentificadorPersonaFisicaServiceBusinessRemote()", e);
        }
        return ejb;
    }
    
    /**
     * 191807 081012
     * Este metodo obtiene el EJB para llamar al EJB PersonaFisica
     * @return
     */
    public static PersonaFisicaServiceBusinessRemote getPersonaFisicaServiceBusinessRemote(){
    	PersonaFisicaServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (PersonaFisicaServiceBusinessRemote) getContexto().lookup("personaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote");
        } catch (NamingException e) {
            LOG.error("In getPersonaFisicaServiceBusinessRemote()", e);
        }catch(Exception e){
        	LOG.error("In getPersonaFisicaServiceBusinessRemote()", e);
        }
        return ejb;
    }
}
