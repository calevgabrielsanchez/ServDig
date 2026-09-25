package mx.gob.imss.ctirss.reing.patrones.util;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.reing.patrones.entity.AptRegistroPatronal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArpUtility {

	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(ArpUtility.class);
	}

	//Si regresa true el patrona existe en REING
	public static boolean validaExistePatron(int tipoPersonaAlta,
			String municipioImssAlta, String nombreAlta,
			String nombreAcomodado, AptRegistroPatronal aptRegistroPatronal,
			int divisionAlta, int grupoAlta, int fraccionAlta, int modalidadAlta) {
		
		LOG.debug("Validando si ya existe el patron en REING");
		
		if(tipoPersonaAlta == Constantes.TIPO_PERSONA_FISICA.intValue()){
			if (aptRegistroPatronal.getCveModalidad().intValue() != new Integer(Constantes.MODALIDAD_32).intValue() &&						
				BigDecimal.valueOf(aptRegistroPatronal.getApcFraccion().getId().getCveGrupo()).intValue() == grupoAlta &&						 
				BigDecimal.valueOf(aptRegistroPatronal.getApcFraccion().getId().getCveDivision()).intValue() == divisionAlta &&				 		
			 	aptRegistroPatronal.getCveModalidad().intValue() == modalidadAlta &&				 		
			 	BigDecimal.valueOf(aptRegistroPatronal.getApcFraccion().getId().getCveFraccion()).intValue() == fraccionAlta &&				 		
			 	nombreAcomodado.trim().toUpperCase().equals(nombreAlta.trim().toUpperCase()) &&				 		
			 	aptRegistroPatronal.getCveMunicipio().equals(municipioImssAlta)) {
				
				 LOG.debug("Encontre el patron: " + nombreAcomodado);				 
				 LOG.debug("*******************" + Constantes.ERROR_PATRON_ENCONTRADO_SSPA);
				 return true;			 
			 }
			
		}else{
			if(aptRegistroPatronal.getCveModalidad().intValue() != new Integer(Constantes.MODALIDAD_32).intValue() &&
				 aptRegistroPatronal.getCveModalidad().intValue() == modalidadAlta &&
			 	 nombreAcomodado.trim().toUpperCase().equals(nombreAlta.trim().toUpperCase())){
				
				if(aptRegistroPatronal.getCveMunicipio().equals(municipioImssAlta)){
					LOG.debug("Encontre el patron: " + nombreAcomodado);
					LOG.debug("*******************" + Constantes.ERROR_PATRON_ENCONTRADO_SSPA);
					return true;							 
				}
				
				if(BigDecimal.valueOf(aptRegistroPatronal.getApcFraccion().getId().getCveFraccion()).intValue() != fraccionAlta ||
				   BigDecimal.valueOf(aptRegistroPatronal.getApcFraccion().getId().getCveGrupo()).intValue() !=  grupoAlta ||
				   BigDecimal.valueOf(aptRegistroPatronal.getApcFraccion().getId().getCveDivision()).intValue() != divisionAlta){
					 LOG.debug("Encontre el patron en temporal: "+ nombreAcomodado);
					 LOG.debug("*******************" + Constantes.ERROR_PATRON_ENCONTRADO_SSPA_DIFERENTE_ACTIVIDAD);
					 return true;
				}
		 
		    }
		}
		
		return false;
	}	
		
	public static String obtieneNombreAcomodado(int tipoPersonaAlta, String nombreRazonSocial, String apPaterno, String apMaterno, String descSociedadAlta) {
		String nombreAcomodado = null;
		LOG.debug("Obteniendo AcomodaNombreSINDO");
		if (tipoPersonaAlta == Constantes.TIPO_PERSONA_MORAL.intValue()) {
			nombreAcomodado = AcomodaNombreSINDO(tipoPersonaAlta, nombreRazonSocial, apPaterno, apMaterno, descSociedadAlta);
			LOG.debug("::::::::::nombreAcomodado Moral : " + nombreAcomodado);
		} else {
			nombreAcomodado = AcomodaNombreSINDO(tipoPersonaAlta, nombreRazonSocial, apPaterno, apMaterno, null);
			LOG.debug("::::::::::nombreAcomodado fisica  : " + nombreAcomodado);
		}

		return nombreAcomodado;
	}

	private static String AcomodaNombreSINDO(int tipoPersona, String nombre,
			String aptPaterno, String aptMaterno, String tipoSociedad) {
		String patNombre = null;
		String patNombreSspa = null;

		String patApellidoP = null;
		String patApellidoM = null;

		if (tipoPersona == Constantes.TIPO_PERSONA_FISICA.intValue()) {
			patNombre = nombre;
			patApellidoP = aptPaterno;
			patApellidoM = aptMaterno;

			patNombreSspa = patNombre;

			String apellidos = null;
			apellidos = patApellidoP;
			apellidos = apellidos != null ? apellidos.trim() : "";
			apellidos = (apellidos + " ") + (patApellidoM != null ? patApellidoM : "");

			patNombreSspa += " " + apellidos.trim();

			patNombreSspa = patNombreSspa.trim(); // En el caso de que hubiera
													// valor vacio y quedara
													// un espacio en blanco al
													// final
			patNombreSspa = patNombreSspa.length() > 80 ? patNombreSspa
					.substring(0, 80) : patNombreSspa;

			return patNombreSspa;

		} else {
			patNombre = nombre;

			if ((15 - tipoSociedad.length()) >= 0 && patNombre.length() >= (80 - tipoSociedad.length())) {
				patNombre = patNombre.substring(0, (80 - tipoSociedad.length()));
				LOG.debug("Entro  a el acomplete A  nombre " + patNombre);
			} else {
				if (patNombre.length() >= 65)
					patNombre = patNombre.trim().substring(0, 65);
				else
					patNombre = patNombre.trim();
			}

			patNombre = patNombre + " ";
			patNombre = patNombre + tipoSociedad;
			patNombre = patNombre.trim(); // En el caso de que hubiera valor
											// vacio y quedara un espacio en
											// blanco al final
			patNombre = patNombre.length() > 80 ? patNombre.substring(0, 80) : patNombre;
			
			return patNombre;
		}

	}
	
	public static String obtieneNombrePatron(int tipoPersonaAlta, String nombreAlta, String apPaternoAlta, String apMaternoAlta, String descSociedadAlta) {
		
		LOG.debug("Obteniendo nombre en obtieneNombrePatron");
		String patNombre = null;
		String patApellidoP = null; 
		String patApellidoM = null;
		String patNombreSspa = null;
		
		if (tipoPersonaAlta == Constantes.TIPO_PERSONA_FISICA.intValue()){
			patNombre = nombreAlta;
			patApellidoP = apPaternoAlta;
			patApellidoM = apMaternoAlta;
				
		    patNombreSspa = patNombre;
			String apellidos = null;
			apellidos = patApellidoP;
            apellidos = apellidos != null? apellidos.trim() : "";
            apellidos = (apellidos + " ") + (patApellidoM!=null?patApellidoM:"");
            patNombreSspa += " " + apellidos.trim();
    
            patNombreSspa = patNombreSspa.trim();  //En el caso de que hubiera valor vac�o y quedara un espacio en blanco al final
            patNombreSspa = patNombreSspa.length() > 80 ? patNombreSspa.substring(0,80) : patNombreSspa;				
            patNombre=patNombreSspa;
			
		}else {
			patNombre= nombreAlta;	

  	    	if ( (15-descSociedadAlta.length()) >= 0 && patNombre.length() >= (80-descSociedadAlta.length()) ){
  	    		patNombre=patNombre.substring(0,(80-descSociedadAlta.length()));
  	    		LOG.debug("Entro  a el acomplete A  nombre "+ patNombre);
  	    	}
  	    	else
  	    	{
  	    		if (patNombre.length()>=65)
  	    			patNombre=patNombre.trim().substring(0,65);
  	    		else
  	    			patNombre= patNombre.trim();    			  
  	    	}    	  
	  
  	    	patNombre=patNombre + " ";
  	    	patNombre=patNombre + descSociedadAlta;    
  	    	patNombre = patNombre.trim();  //En el caso de que hubiera valor vac�o y quedara un espacio en blanco al final
  	    	patNombre = patNombre.length() > 80 ? patNombre.substring(0,80) : patNombre;
  	    	patNombreSspa=patNombre;
		}	

		LOG.debug("::::::::::Nombre obtenido para buscar en SSPA_PATRONES: " + patNombre);

		return patNombre;
	}

	public static String convierteClaseReing(int clase) {
		String claseR = null;
		
		if(clase == 1)
			claseR = "I";
		else if(clase == 2)
			claseR = "II";
		else if(clase == 3)
			claseR = "III";
		else if(clase == 4)
			claseR = "IV";
		else if(clase == 5)
			claseR = "V";
				
		return claseR;
	}

}
