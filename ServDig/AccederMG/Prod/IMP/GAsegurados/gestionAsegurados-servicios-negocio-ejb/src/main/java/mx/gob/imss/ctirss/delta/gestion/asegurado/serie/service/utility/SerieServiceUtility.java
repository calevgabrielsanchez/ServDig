/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSeriesNss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSerie;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionSerie;

/**
 * @author vanderluk
 *
 */
@Stateless
public class SerieServiceUtility extends AbstractServiceUtility implements
		SerieServiceUtilityLocal {

	
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy");
	
	
	private static final NumberFormat formato2Digitos = new DecimalFormat("00");
	private static final NumberFormat formato4Digitos = new DecimalFormat("0000");
	
	public static final Integer ASIGNACION_SERIE_NIVEL_CENTRAL = new Integer(3);
	public static final Integer ASIGNACION_SERIE_NIVEL_DELEGACION = new Integer(2);
	public static final Integer ASIGNACION_SERIE_NIVEL_SUBDELEGACION = new Integer(1);
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.SerieServiceUtilityLocal#transformarSerieModelo(mx.gob.imss.ctirss.delta.persistence.DicSeriesNss)
	 */
	@Override
	public Serie transformarSerieModelo(DicSeriesNss entity) {
		
		
		if(entity == null){
			
			this.log.warn("Parametro de entrada nulo, no se transformara nada.");
			
			return null;
		}
		
		
		Serie modelo = new Serie();
		
		
		modelo.setIdSerie(entity.getCveIdSerie());
		
		modelo.setAnioRegistro(entity.getNumAnioRegistro().intValue());
		modelo.setNumSerie(entity.getNumSerie().longValue());
		
		if(entity.getDicTipoSerie() != null){
			TipoSerie tipoSerie = new TipoSerie();
			tipoSerie.setDescripcion(entity.getDicTipoSerie().getDesTipoSerie());
			tipoSerie.setIdTipoSerie( new Long(entity.getDicTipoSerie().getCveIdTipoSerie()).intValue());
			modelo.setTipoSerie(tipoSerie);
		}
		
		
		return modelo;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.SerieServiceUtilityLocal#transformarSerieEntity(mx.gob.imss.ctirss.delta.model.gestion.nss.Serie)
	 */
	@Override
	public DicSeriesNss transformarSerieEntity(Serie modelo) {
		
		this.log.debug(" transformando el Modelo de Serie a la Clase Entity DicSeries..." + modelo) ;
		
		DicSeriesNss dicSeries = null;
		
		if(modelo != null){
			dicSeries = new DicSeriesNss();
			
			//Llave primaria...
			if(modelo.getIdSerie() != null){
				dicSeries.setCveIdSerie(modelo.getIdSerie());
			}
			
			//Tipo de Serie
			TipoSerie tipoSerie = modelo.getTipoSerie();
			if(tipoSerie != null && tipoSerie.getIdTipoSerie() != null){
				this.log.debug(" Convirtiendo el tipo de serie ..");
				
				DicTipoSerie dicTipoSerie = new DicTipoSerie();
				dicTipoSerie.setCveIdTipoSerie(tipoSerie.getIdTipoSerie());
				dicSeries.setDicTipoSerie(dicTipoSerie);
				
			}
			
			
			dicSeries.setNumAnioRegistro( new BigDecimal(modelo.getAnioRegistro()));
			dicSeries.setNumSerie(new BigDecimal(modelo.getNumSerie()));
			
			
			
		}else{
			this.log.warn(" El Modelo es Nulo, no se transformo nada...");
			return dicSeries;
		}
		
		
		
		return dicSeries;
	}

	@Override
	public Long obtenerDosDigitosDeAnio(Date fecha) {
		// TODO Auto-generated method stub
		Long anio = new Long(0);
		if(fecha != null){
			String _str = SerieServiceUtility.sdf.format(fecha);
			anio = Long.parseLong(_str);
		}
		return anio;
	}

	@Override
	public String generaNSS(Serie serie) {
		
		StringBuffer buffer = new StringBuffer();
		Long numSerie = serie.getNumSerie();
		Integer anioRegistro = serie.getAnioRegistro();
		Integer anioNacimiento = serie.getAnioNacimiento();
		Long folio = serie.getFolio();
		
		
		buffer.append(   formato2Digitos.format(numSerie));
		buffer.append(   formato2Digitos.format(anioRegistro));
		buffer.append(   formato2Digitos.format(anioNacimiento));
		buffer.append(   formato4Digitos.format(folio));
		
		Long digito = this.generarDigitoVerificador(buffer.toString());
		
		buffer.append((digito));
		
		return buffer.toString();
	}

	
	
	/**
     * 191807 090812
     * Este metodo SI calcula el digito verificador de un NSS
     */
    private long generarDigitoVerificador(String nss){
    	
		this.log.debug("Se va a calcular digito verificador para NSS -> " + nss);
		
    	// Este arreglo contendra el resultado de multiplicar los digitos ya sea por 1 o por 2 en el paso 1
    	int digitos [] = new int[10];

    	for(int i = 0; i < nss.length(); i ++){
    		// Paso 1: se multiplica por 2 los digitos colocados en posiciones impares. Los pares se dejan igual (o sea multiplicados por 1)
    		String digitoActualCadena = nss.substring(i, i + 1);
    		if(i % 2 == 0){
    			digitos[i] = Integer.parseInt(digitoActualCadena);
    			System.out.print(i + " es par,   o sea que " + digitoActualCadena + " se multiplica por 1 = " + digitos[i]);
    		}else{
    			digitos[i] = Integer.parseInt(digitoActualCadena) * 2;
    			System.out.print(i + " es impar, o sea que " + digitoActualCadena + " se multiplica por 2 = " + digitos[i]);
    		}
    		
    		// Paso 2: aquellas cifras que sean de 2 digitos, se reduciran a 1 digito, p.e.: 12 => 1 + 2 = 3
    		String cifraActualDobleCadena = String.valueOf(digitos[i]);
    		if(cifraActualDobleCadena.length() > 1){
    			digitos[i] = Integer.parseInt(sumarCifrasDobles(cifraActualDobleCadena));
    			System.out.print(" --> se tiene que reducir... \n");
    		}else{
    			System.out.println();
    		}
    	}
    	
    	System.out.print("\nCadena resultante: ");
    	for(int i = 0; i < digitos.length; i ++){
			System.out.print(digitos[i] + " ");
    	}
    	System.out.println("\n");
    	
    	// Paso 2.1: sumamos los 10 digitos resultantes
    	int resultadoSumaDigitosDoble = 0;
    	for(int i = 0; i < digitos.length; i ++){
    		resultadoSumaDigitosDoble += digitos[i];
    	}
    	
    	this.log.debug("El resultado de sumar los digitos fue: " + resultadoSumaDigitosDoble);

    	// Paso 3: El producto de la suma anterior se divide entre 10
    	int residuo = resultadoSumaDigitosDoble % 10;

    	// Paso 4: El residuo de la división anterior se resta de un número 10
    	int digitoVerificador = 10 - residuo; 
    	
    	/*
		 * Paso 5: El residuo debe ser igual al campo de dígito verificador.
		 * NOTA: Cuando el resultado de las operaciones aritméticas anteriores
		 * sea igual a 10, el dígito verificador será igual a 0.
		 */
    	if (digitoVerificador == 10) {
    		digitoVerificador = 0;
    		this.log.debug("Digito verificador igual a 10, se cambia a 0");
    	}
		
    	this.log.debug("El Digito Verificador para el NSS " + nss + " es: " + digitoVerificador);
		
		return (long)digitoVerificador;
    }
    
    
    /**
     * 191807 090812
     * Este metodo realiza una reduccion de cifras, o sea, suma los digitos de cifras dobles, p.e.:
     * 18 => 1 + 8 = 9 
     * @param cifraDobleCadena
     * @return
     */
    private String sumarCifrasDobles(String cifraDobleCadena){
    	int cifraReducida = 0;
		for(int j = 0; j < cifraDobleCadena.length(); j ++){
			cifraReducida += Integer.parseInt(cifraDobleCadena.substring(j, j + 1));
		}
    	return String.valueOf(cifraReducida);
    }
    
    
    
    
    @Override
	public Integer getNivelAsignacion(AsignacionSerieNSS asignacionSerie)
			throws NivelDeAsignacionSerieIndefinidoException {
    	Integer nivel = new Integer(0);
    	
    	this.log.debug("Determinando el nivel de asignacion de la serie ...");
		
    	if (asignacionSerie != null){
    		
    		
    		Delegacion delegacion = asignacionSerie.getDelegacion();
    		Subdelegacion subdelegacion = asignacionSerie.getSubdelegacion();
    		
			if ((delegacion == null || delegacion.getId() == null)
					&& (subdelegacion == null || subdelegacion.getId() == null)) {
				this.log.debug("El nivel de asignacion de la serie es Nivel Centrar :"
						+ ASIGNACION_SERIE_NIVEL_CENTRAL);
    			nivel = new Integer(ASIGNACION_SERIE_NIVEL_CENTRAL.intValue());
    			
    		} else if ((delegacion != null && delegacion.getId() != null)
    				&& (subdelegacion == null || subdelegacion.getId() == null)) {
    			
				this.log.debug("El nivel de asignacion de la serie es Nivel Delegacion :"
						+ ASIGNACION_SERIE_NIVEL_DELEGACION);
    			nivel = new Integer(ASIGNACION_SERIE_NIVEL_DELEGACION.intValue());
    			
			} else if ((delegacion != null && delegacion.getId() != null)
					&& (subdelegacion != null && subdelegacion.getId() != null)) {
				this.log.debug("El nivel de asignacion de la serie es Nivel Subdelegacion :"
						+ ASIGNACION_SERIE_NIVEL_SUBDELEGACION);
    			nivel = new Integer(ASIGNACION_SERIE_NIVEL_SUBDELEGACION.intValue());
    		}
    		
    	}else{
    		this.log.error("No se recibieron los parametros para determinar el nivel de asignacion");
    		throw new NivelDeAsignacionSerieIndefinidoException(" No se recibieron los suficientes parametros para determinar el nivel de asignaci\u00F3n");
    	}
    	
    	
    	return nivel;
    }

	public AsignacionSerieNSS transformarAsignacionNSSModelo(
			DitAsignacionSerie entity) {
		AsignacionSerieNSS asignacionSerieNSS = null;

		if (entity != null) {
			asignacionSerieNSS = new AsignacionSerieNSS();

			Delegacion delegacion = null;
			DicDelegacion dicDelegacion = entity.getDicDelegacion();
			if (dicDelegacion != null) {
				delegacion = new Delegacion();

				delegacion.setId(Long.valueOf(dicDelegacion
						.getCveIdDelegacion()));
				delegacion.setClave(dicDelegacion.getClaveDelegacion());
				delegacion.setDescripcion(dicDelegacion.getDesDeleg());
				delegacion.setCiz(dicDelegacion.getCveCiz());
			}
			asignacionSerieNSS.setDelegacion(delegacion);

			Subdelegacion subdelegacion = null;
			DicSubdelegacion dicSubdelegacion = entity.getDicSubdelegacion();
			if (dicSubdelegacion != null) {
				subdelegacion = new Subdelegacion();

				subdelegacion.setId(Long.valueOf(dicSubdelegacion
						.getCveIdSubdelegacion()));
				subdelegacion
						.setClave(dicSubdelegacion.getClaveSubdelegacion());
				subdelegacion.setDescripcion(dicSubdelegacion
						.getDesSubdelegacion());

				DicDelegacion dicDelegacionAux = dicSubdelegacion
						.getDicDelegacion();
				Delegacion delegacionAux = null;
				if (dicDelegacionAux != null) {
					delegacionAux = new Delegacion();

					delegacionAux.setId(Long.valueOf(dicDelegacionAux
							.getCveIdDelegacion()));
					delegacionAux.setClave(dicDelegacionAux
							.getClaveDelegacion());
					delegacionAux
							.setDescripcion(dicDelegacionAux.getDesDeleg());
				}
				subdelegacion.setDelegacion(delegacionAux);
			}
			asignacionSerieNSS.setSubdelegacion(subdelegacion);

			Serie serie = transformarSerieModelo(entity.getDicSeriesNss());
			asignacionSerieNSS.setSerie(serie);
		}

		return asignacionSerieNSS;
	}

	public DitAsignacionSerie transformarAsignacionNSSEntity(
			AsignacionSerieNSS modelo) {
		DitAsignacionSerie ditAsignacionSerie = null;

		if (modelo != null) {
			ditAsignacionSerie = new DitAsignacionSerie();

			DicDelegacion dicDelegacion = null;
			Delegacion delegacion = modelo.getDelegacion();
			if (delegacion != null) {
				dicDelegacion = new DicDelegacion();

				dicDelegacion
						.setCveIdDelegacion(delegacion.getId().longValue());
				dicDelegacion.setClaveDelegacion(delegacion.getClave());
				dicDelegacion.setDesDeleg(delegacion.getDescripcion());
			}
			ditAsignacionSerie.setDicDelegacion(dicDelegacion);

			DicSubdelegacion dicSubdelegacion = null;
			Subdelegacion subdelegacion = modelo.getSubdelegacion();
			if (subdelegacion != null) {
				dicSubdelegacion = new DicSubdelegacion();

				dicSubdelegacion.setCveIdSubdelegacion(subdelegacion.getId()
						.longValue());
				dicSubdelegacion
						.setClaveSubdelegacion(subdelegacion.getClave());
				dicSubdelegacion.setDesSubdelegacion(subdelegacion
						.getDescripcion());

				DicDelegacion dicDelegacionAux = null;
				Delegacion delegacionAux = modelo.getDelegacion();
				if (delegacionAux != null) {
					dicDelegacionAux = new DicDelegacion();

					dicDelegacionAux.setCveIdDelegacion(delegacionAux.getId()
							.longValue());
					dicDelegacionAux.setClaveDelegacion(delegacionAux
							.getClave());
					dicDelegacionAux
							.setDesDeleg(delegacionAux.getDescripcion());
				}
				dicSubdelegacion.setDicDelegacion(dicDelegacionAux);
			}
			ditAsignacionSerie.setDicSubdelegacion(dicSubdelegacion);

			DicSeriesNss dicSeriesNss = transformarSerieEntity(modelo
					.getSerie());
			ditAsignacionSerie.setDicSeriesNss(dicSeriesNss);
		}

		return ditAsignacionSerie;
	}
}
