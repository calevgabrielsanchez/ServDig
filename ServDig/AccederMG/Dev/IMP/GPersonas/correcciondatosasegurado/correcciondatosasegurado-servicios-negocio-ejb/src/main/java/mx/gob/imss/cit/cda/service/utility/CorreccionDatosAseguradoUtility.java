package mx.gob.imss.cit.cda.service.utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name="correccionDatosAseguradoUtility", mappedName="correccionDatosAseguradoUtility")
public class CorreccionDatosAseguradoUtility implements
		CorreccionDatosAseguradoUtilityLocal {
	
	private final Logger log = LoggerFactory.getLogger(CorreccionDatosAseguradoUtility.class);
	
	private static final String FORMATO_FECHA_COMPLETO="dd/MM/yyyy kk:mm:ss";

	@Override
	public String convertDateToString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_MASK);
		return simpleDateFormat.format(date);
	}

	@Override
	public Date convertStringToDate(String dateString) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_MASK);
		return simpleDateFormat.parse(dateString);
		
	}
	
	/*
	 * 
	 * Convertir java a json
	 */
	@Override
	public String generarJsonDataWf(Map<String, Object> data){
		String jsonParams ="";
		
		ObjectMapper mapper = new ObjectMapper();		
		try {
			jsonParams = mapper.writeValueAsString(data);
		} catch (JsonGenerationException e) {
			log.error("Error al parsear el Json del workflow", e);
		} catch (JsonMappingException e) {
			log.error("Error al parsear el Json del workflow", e);
		} catch (IOException e) {
			log.error("Error al parsear el Json del workflow", e);
		}
		return jsonParams;
	}
	
	@Override
	public Map<String, Object> generarJavaDataWf(String json){
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();		
		try {
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});

		} catch (JsonParseException e) {
			log.error("Error al generar el objeto Java del workflow", e);
		} catch (JsonMappingException e) {
			log.error("Error al generar el objeto Java del workflow", e);
		} catch (IOException e) {
			log.error("Error al generar el objeto Java del workflow", e);
		}
		
		return map;
	}

	@Override
	public void filtraCuentasCDA(List<PeriodoMovimientoAfiliatorio> periodos,FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filter) {
		Iterator<PeriodoMovimientoAfiliatorio> cuentasIterator = periodos.iterator();
	    while (cuentasIterator.hasNext()) {
	    	PeriodoMovimientoAfiliatorio c = cuentasIterator.next();
	        if (filter.shouldRemove(c)) {
	            cuentasIterator.remove();
	        }
	    }
		
	}
	
	@Override
	public void filtraCuentasCDAUltimoMovimiento(List<PeriodoMovimientoAfiliatorio> periodos,FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filter) {
		Iterator<PeriodoMovimientoAfiliatorio> cuentasIterator = periodos.iterator();
	    while (cuentasIterator.hasNext()) {
	    	PeriodoMovimientoAfiliatorio c = cuentasIterator.next();
	        if (filter.shouldRemoveLast(c)) {
	            cuentasIterator.remove();
	        }
	    }
		
	}
	
	
	/**
     * Este método genera el dígito verificador de un String con NRP conformado paritr de 3 posiciones municipio 5 posiciones serie 2 modalidad
     * @param nrp El NRP correspondiente
     * @return El dígito verificador para ese NRP
     */
	@Override
    public  int generaDigitoVerificadorRP(String nrp){
        int factorDeConversion=10;
        int paso3=0;
        boolean bandera=true;
        String alfabeto= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String clave = nrp;
        int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
        
        if (primeraLetra!=-1){   
        	clave = (primeraLetra + factorDeConversion) + nrp.substring(1,nrp.length());
        }
        
        int i=clave.length()-1;

        while (i>=0) {
            if (bandera){   
            	int porDos = Integer.parseInt(""+clave.charAt(i)) * 2;
            	//si el resultado es un numero de dos cifras, es necesario tratar estas por separado.
                if (porDos>9){   
                	paso3+= (porDos%10) +  (porDos/10);
                }
                else{   
                	paso3+= porDos;
                }
                bandera=false;
            }
            else
            {   paso3+=Integer.parseInt(""+clave.charAt(i));
                bandera=true;
            }
            i--;
        }
        int digitoVerificador = 10-(paso3%10);
        if (digitoVerificador>9)
        {   digitoVerificador=0;
        }
        return digitoVerificador;
    }
	
	@Override
	public Date convertirStringToDateMask(String date, String mask){
		DateFormat df = new SimpleDateFormat(StringUtils.isNotBlank(mask)?mask:FORMATO_FECHA_COMPLETO);
		Date dateAux = null;
		try {
			dateAux = df.parse(date);
		} catch (ParseException e) {
			log.error("Ocurrio un error con el parseo {}",e);
			dateAux = new Date();
		}
		return dateAux;
	}

}
