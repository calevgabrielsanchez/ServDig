package personas;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import org.junit.Test;
import test.EjbLocator;

/**
 * @version $Revision: TODO $ $Date: 2012-02-25 23:12 -0500 (Sat, 25 Feb 2012) $
 */
public class PersonaMoralBusinessTest {

    private transient static PersonaMoralBusinessRemote personaMoralBusiness = EjbLocator.getPersonaMoralBusiness();

    @Test
    public void altaPersonaMoral() {
		System.out.println("altaPersonaMoral. Inicio " + new Date());
    	
		final Moral persona = new Moral();
//		personas.DatosPersonas.agregaPersonaMoral(persona);
//		personas.DatosPersonas.agregarMediosContacto(persona);
//		personas.DatosPersonas.agregarDomicilio(persona);
//		        
	    // EJB DE ALTA DE PERSONAS MORALES
        Moral personaMoralResultado = personaMoralBusiness.altaPersonaMoral(persona);
        System.out.println("resultado: " + personaMoralResultado.getIdPersona());
		System.out.println("altaPersonaMoral. Final " + new Date());        
    }

    @Test
    public void getDatosComplementariosPersonaMoral() {
		System.out.println("getDatosComplementariosPersonaMoral. Inicio " + new Date());

		try{
		    // EJB DE ALTA DE PERSONAS MORALES
	        Moral personaMoralResultado = personaMoralBusiness.getDatosComplementariosPersonaMoral(334394L);
	        
	        for (MedioContacto medioContacto : personaMoralResultado.getMediosContacto()) {
	            if (medioContacto instanceof TelefonoFijo) {
	                TelefonoFijo telefonoFijo = (TelefonoFijo) medioContacto;
	                System.out.println(telefonoFijo.getClaveLada());
	                System.out.println(telefonoFijo.getExtension());
	                System.out.println(telefonoFijo.getNumero());
	                System.out.println(telefonoFijo.getTipoMedioContacto().getIdTipoMedioContacto());
	                System.out.println(telefonoFijo.getClave());
	            } else if (medioContacto instanceof TelefonoMovil) {
	                TelefonoMovil telefonoMovil = (TelefonoMovil) medioContacto;
	                System.out.println(telefonoMovil.getNumero());
	                System.out.println(telefonoMovil.getClave());
	                System.out.println(telefonoMovil.getTipoMedioContacto().getIdTipoMedioContacto());
	                System.out.println(telefonoMovil.getClave());
	            } else if (medioContacto instanceof CorreoElectronico) {
	                CorreoElectronico correoElectronico = (CorreoElectronico) medioContacto;
	                System.out.println(correoElectronico.getCorreo());
	                System.out.println(correoElectronico.getClave());
	                System.out.println(correoElectronico.getTipoMedioContacto().getIdTipoMedioContacto());
	            }
	        }	        
	        System.out.println("resultado: " + personaMoralResultado);
		}
		catch(Exception e){
			e.printStackTrace();
		}
    			        
		System.out.println("getDatosComplementariosPersonaMoral. Final " + new Date());        
    }    
    
    @Test
    public void testBuscarPersonaMoralPorId() {
        Moral personaMoral = new Moral();
        personaMoral.setIdPersona(1L);
        System.out.println(personaMoralBusiness.getPersonaMoral(personaMoral));
    }
    
    @Test
    public void probarConsultasPersonasMoralChingon(){
    	
    	try{
    		
	    	Moral moral = new Moral();
	    	List<Candidato> candidatos = null;
	   
	    	moral.setRfc("ADE0501173H6s");
	    	moral.setRazonSocial("AVANSIS DESARROLLOS");
	
//	    	candidatos = EjbLocator.getConsultaPersonaMoralServiceBusiness().consultarPersonaMoral(moral);
//	    	System.out.println("LISTA DE CANDIDATOS: *****************************************************************************************" + candidatos);
//	    	System.out.println("*************************************************************************************************************");
	    	
	    	Moral moralResultado = null;
	    
	    	if(candidatos != null && candidatos.size() > 0){
	    		moralResultado = EjbLocator.getComplementarCalificacionPersonaMoralServiceBusiness().complementarCalificaciones((Moral)candidatos.get(0).getPersona(), moral);
		    	System.out.println("MORAL RESULTADO: *******************************************************************************************" + moralResultado);
		    	System.out.println("************************************************************************************************************");
	    	}else{
	    		//N2
	    		moralResultado = EjbLocator.getLocalizarPersonaMoralEnEntidadesExternasServiceBusiness().localizarPersonaMoralEnEntidadesExternas(moral);
		    	System.out.println("MORAL RESULTADO: *******************************************************************************************" + moralResultado);
		    	System.out.println("************************************************************************************************************");
	    	}
	    	
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    }

}
