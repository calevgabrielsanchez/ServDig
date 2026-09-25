package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

@Stateless(mappedName = "personaGlobalServiceUtility")
public class PersonaGlobalUtility implements PersonaGlobalUtilityLocal{

	@Override
	public PersonaTO convertirPersonaFiscaAPersonaGlobal(Fisica fisica) {
		PersonaTO personaGbl = new PersonaTO();
		personaGbl.setCurp(fisica.getCurp());
		personaGbl.setLugarNacimiento(fisica.getLugarNacimiento());
		personaGbl.setEstadoCivil(fisica.getEstadoCivil());
		personaGbl.setFechaDefuncion(fisica.getFechaDefuncion());
		personaGbl.setFechaNacimiento(fisica.getFechaNacimiento());
		personaGbl.setIdPersona(fisica.getIdPersona());
		personaGbl.setNombre(fisica.getNombre());
		personaGbl.setPrimerApellido(fisica.getPrimerApellido());
		personaGbl.setSegundoApellido(fisica.getSegundoApellido());
		personaGbl.setPais(fisica.getPais());
		personaGbl.setRfc(fisica.getRfc());
		personaGbl.setSexo(fisica.getSexo());
		personaGbl.setTipoPersona(fisica.getTipoPersona());
		return personaGbl;
	}

	@Override
	public PersonaTO convertirPersonaMoralAPersonaGlobal(Moral moral) {
		PersonaTO personaGbl = new PersonaTO();
		personaGbl.setEscrituraConstitutiva(moral.getEscrituraConstitutiva());
		personaGbl.setIdPersona(moral.getIdPersona());
		personaGbl.setRazonSocial(moral.getRazonSocial());
		personaGbl.setRfc(moral.getRfc());
		personaGbl.setRegistroSindicato(moral.getRegistroSindicato());
		personaGbl.setTipoPersona(moral.getTipoPersona());
		personaGbl.setTipoSociedad(moral.getTipoSociedad());
		return personaGbl;
	}
	
	@Override
	public String quitarCaracteresEspeciales(String str) {
		final String ORIGINAL     ="¡·…ÈÕÌ”Û⁄˙—Ò‹¸—Ò#&,.";
		final String REPLACEMENT  ="AaEeIiOoUuNnUu      ";
		
	    if (str == null) {
	        return null;
	    }
	    char[] array = str.toCharArray();
	    for (int index = 0; index < array.length; index++) {
	        int pos = ORIGINAL.indexOf(array[index]);
	        if (pos > -1) {
	            array[index] = REPLACEMENT.charAt(pos);
	        }
	    }
	    String cadenaLimpia = new String(array);
	    return cadenaLimpia.replace(" ", "");
	}

}
