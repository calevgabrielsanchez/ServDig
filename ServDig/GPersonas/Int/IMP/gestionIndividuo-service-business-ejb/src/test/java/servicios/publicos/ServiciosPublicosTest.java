package servicios.publicos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;

import test.EjbLocator;

public class ServiciosPublicosTest {

    @Test
    public void localizarPersonaFisica() throws ParseException, ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException {
        System.out.println("localizarPersonaFisica. Inicio " + new Date());
    	
        final ServiciosPersonaBusinessRemote servicios = EjbLocator.getServiciosPersonaBusiness();
        final DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
        final EntidadFederativa lugarNacimiento = new EntidadFederativa();
        final Sexo sexo = new Sexo();

        
        // CURP DE LA PERSONA UTILIZADA PARA LA PRUEBA: MOMM770108HDFRNR02
        //lugarNacimiento.setClave("9");
        //sexo.setIdSexo(1);
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("MOMM770108HDFRNR02", "EUJA700727", "MAURICIO ALBERTO", "MORENO", "MANRIQUEZ", formatoFecha.parse("08/01/1977"), lugarNacimiento, sexo);
        
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("YADM000712HNE22N07", null, null, null, null, null, null, null);
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("AAGA040223HNELR909", null, null, null, null, null, null, null);
        List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("ROGS820119HPLDRM03", null, null, null, null, null, null, null);
        //lugarNacimiento.setClave("21");
        //sexo.setIdSexo(1);
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica(null, null, "SAMUEL", "RODRIGUEZ", "GRAJEDA", formatoFecha.parse("19/01/1982"), lugarNacimiento, sexo);
        
        if (listaPersonasFisicas != null){
        	for (Fisica fisica : listaPersonasFisicas){
                System.out.println("Respuesta del servidor...");
                System.out.println(ToStringBuilder.reflectionToString(fisica, ToStringStyle.MULTI_LINE_STYLE));                
                System.out.println("fisica.getIdPersona(): " + fisica.getIdPersona());
                System.out.println("fisica.getNombre(): " + fisica.getNombre());
                System.out.println("fisica.getCurp(): " + fisica.getCurp());
                System.out.println("fisica.getRfc(): " + fisica.getRfc());
                if (fisica.getActaNacimiento() != null){
                	if (fisica.getActaNacimiento().getMunicipio() != null){
                		System.out.println("Municipio: " + ToStringBuilder.reflectionToString(fisica.getActaNacimiento().getMunicipio(), ToStringStyle.MULTI_LINE_STYLE));
                	}
                }
            	for (PersonaCalificacion personaCalificacion : fisica.getPersonaCalificaciones()){
            		System.out.println("personaCalificacion.getCalificacion().getIdCalificacion(): " + personaCalificacion.getCalificacion().getIdCalificacion());
            	}
        	}
        }

        System.out.println("localizarPersonaFisica. Final " + new Date());        
    }

}
