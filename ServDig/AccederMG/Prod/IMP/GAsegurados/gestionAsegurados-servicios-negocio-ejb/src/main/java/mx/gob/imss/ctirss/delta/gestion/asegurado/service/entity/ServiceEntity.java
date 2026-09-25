/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:ServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.asegurado.service.entity
 *  @Fecha:20/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.service.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import javax.persistence.NonUniqueResultException;

import org.hibernate.Session;
import org.hibernate.SQLQuery;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.persistence.DicFolioNss;
import mx.gob.imss.ctirss.delta.persistence.DicFolioNssPK;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitLlaveAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility.AseguradoHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Stateless
@Local(value = ServiceEntityLocal.class)
public class ServiceEntity extends AbstractServiceEntity implements ServiceEntityLocal{
	
    /**
     * 191807 090812
     * Este metodo SI calcula el digito verificador de un NSS
     */
    public long generarDigitoVerificador(String nss){
    	
    	// Este arreglo contendra el resultado de multiplicar los digitos ya sea por 1 o por 2 en el paso 1
    	int digitos [] = new int[10];

    	for(int i = 0; i < nss.length(); i ++){
    		// Paso 1: se multiplica por 2 los digitos colocados en posiciones impares. Los pares se dejan igual (o sea multiplicados por 1)
    		String digitoActualCadena = nss.substring(i, i + 1);
    		/**
    		 * Se modifico la logica del calculo de nss ya que se estaba tomando la posicion
    		 * del arrelgo desde el 0 cuando debe de tomarlo iniciando desde 1.
    		 */
    		if( (i + 1) % 2 == 0){
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
    	
    	// Paso 3: sumamos los 10 digitos resultantes
    	int resultadoSumaDigitosDoble = 0;
    	for(int i = 0; i < digitos.length; i ++){
    		resultadoSumaDigitosDoble += digitos[i];
    	}
    	
    	System.out.println("El resultado de sumar los digitos fue: " + resultadoSumaDigitosDoble);

    	//Paso 4: se resta la decena inmediata superior al resultado del paso 3, y obtenemos el digito verificador de las unidades del resultado de la resta
    	String sustraendoCadena = String.valueOf(resultadoSumaDigitosDoble);
    	int digitoVerificador = -1;
		if(sustraendoCadena.length() > 1){
			int decenas = (Integer.parseInt(sustraendoCadena.substring(0, 1)) + 1) * 10;
			int resta = decenas - Integer.parseInt(sustraendoCadena);
			digitoVerificador = resta % 10;		//con el % 10 obtenemos las unidades del resultado de la resta, o sea lo que viene siendo el digito verificador
			System.out.println("Sera necesario restar el numero anterior con la decena inmediata superior: " + decenas + " - " + sustraendoCadena + " = " + resta);
			System.out.println("Para extraer las unidades se hace: " + resta + " % 10 = " + digitoVerificador);
		}else{
			digitoVerificador = Integer.parseInt(sustraendoCadena);
			System.out.println("No fue necesario restar el numero anterior con la decena inmediata superior");
		}
    	
		System.out.println("\nEl Digito Verificador es finalmente: " + digitoVerificador);
		
		return (long)digitoVerificador;
    }
    
    /**
     * 191807 090812
     * Este metodo realiza una reduccion de cifras, o sea, suma los digitos de cifras dobles, p.e.:
     * 18 => 1 + 8 = 9 
     * @param cifraDobleCadena
     * @return
     */
    public String sumarCifrasDobles(String cifraDobleCadena){
    	int cifraReducida = 0;
		for(int j = 0; j < cifraDobleCadena.length(); j ++){
			cifraReducida += Integer.parseInt(cifraDobleCadena.substring(j, j + 1));
		}
    	return String.valueOf(cifraReducida);
    }
	
	@Override
	public String generaNss(Long cveIdSerie, Long numAnioNacimiento, Long numAnioRegistro){
		String sNss = "";
				
		//OBTENEMOS EL FOLIO ASIGNADO A LA SERIE-AâˆšÃ«O NACIMIENTO
		DicFolioNssPK dicFolioNssPK = new DicFolioNssPK();
		dicFolioNssPK.setCveIdSerie(cveIdSerie);
		dicFolioNssPK.setNumAnioNacimiento(numAnioNacimiento);		
		DicFolioNss dicFolioNss = em.find(DicFolioNss.class, dicFolioNssPK);
		
		NumberFormat formato2Digitos = new DecimalFormat("00");
		NumberFormat formato4Digitos = new DecimalFormat("0000");
		
		if( dicFolioNss != null){

			//AGREGA 1 AL FOLIO NSS
			dicFolioNss.setNumFolio(new BigDecimal( dicFolioNss.getNumFolio().intValue() + 1 ) );
			dicFolioNss.setFecRegistroActualizado(new Date());
			
			//ARMAMOS CADENA NSS
			sNss = formato2Digitos.format(dicFolioNss.getDicSeriesNss().getNumSerie()) + 
					formato2Digitos.format(numAnioRegistro) +
					formato2Digitos.format(numAnioNacimiento) +
					formato4Digitos.format(dicFolioNss.getNumFolio());
			
			System.out.println("nss: " + sNss);
			sNss = sNss + generarDigitoVerificador(sNss);
			System.out.println("nss con digito verficiador: " + sNss);
			
			//ALMACENA LA ACTUALIZACION DEL FOLIO
			em.persist(dicFolioNss);
		}
		
		return sNss;
	}
	
	public String altaPersonaNss(Fisica fisica, Serie serie){
		this.log.debug("Serie a procesar :" + serie);
		String sNss = "";
		
		//OBTENEMOS LOS DATOS DE LA PEROSNA FISICA
		DitPersona ditPersona = em.find(DitPersona.class, fisica.getIdPersona());
		
		if (ditPersona != null){
			
			//OBTENEMOS LA FECHA DE NACIMIENTO DE LA PERSONA CON 2 DIGITOS
			Date fechaNacimiento = ditPersona.getFecNacimiento();
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(fechaNacimiento);
			int numAnioNacimiento4Digitos = calendario.get(Calendar.YEAR);
			System.out.println(" de nacimiento: " + numAnioNacimiento4Digitos);
			NumberFormat formato2Digitos = new DecimalFormat("0000");			
			String sNumAnioNacimiento = formato2Digitos.format(numAnioNacimiento4Digitos);
			String sNumAnioNacimiento2Digitos = sNumAnioNacimiento.substring(2, sNumAnioNacimiento.length());
			Long numAnioNacimiento = Long.parseLong(sNumAnioNacimiento2Digitos);
			System.out.println("A de nacimiento 2 digitos: " + numAnioNacimiento);
			
			//OBTENEMOS EL NSS
			sNss = generaNss(serie.getIdSerie(), numAnioNacimiento, Long.valueOf( serie.getAnioRegistro() ) );
			
			//DAMOS DE ALTA LA PERSONA FISICA Y SU NSS
			DitAsignacionNss ditAsignacionNss = new DitAsignacionNss();
			ditAsignacionNss.setDitPersona(ditPersona);
			ditAsignacionNss.setNumNss(sNss);
			ditAsignacionNss.setFecRegistroActualizado(new Date());
			ditAsignacionNss.setFecRegistroAlta(new Date());
			em.persist(ditAsignacionNss);
			
			// Se inserta llaves de Asegurado
			DitLlaveAsegurado ditLlaveAsegurado = new DitLlaveAsegurado();
			ditLlaveAsegurado.setRefBusca(sNss);
			ditLlaveAsegurado.setDitAsignacionNss(ditAsignacionNss);
			ditLlaveAsegurado.setDitPersona(ditPersona);
			this.em.persist(ditLlaveAsegurado);
		}
		
		return sNss;
	}
	
	public String asignacionNss(Fisica fisica, String sNss){

		//OBTENEMOS LOS DATOS DE LA PEROSNA FISICA
		DitPersona ditPersona = em.find(DitPersona.class, fisica.getIdPersona());

		//DAMOS DE ALTA LA PERSONA FISICA Y SU NSS
		DitAsignacionNss ditAsignacionNss = new DitAsignacionNss();
		ditAsignacionNss.setDitPersona(ditPersona);
		ditAsignacionNss.setNumNss(sNss);
		ditAsignacionNss.setFecRegistroActualizado(new Date());
		ditAsignacionNss.setFecRegistroAlta(new Date());
		em.persist(ditAsignacionNss);
	
		// Se inserta llaves de Asegurado
		DitLlaveAsegurado ditLlaveAsegurado = new DitLlaveAsegurado();
		ditLlaveAsegurado.setRefBusca(sNss);
		ditLlaveAsegurado.setDitAsignacionNss(ditAsignacionNss);
		ditLlaveAsegurado.setDitPersona(ditPersona);
		this.em.persist(ditLlaveAsegurado);

		return sNss;
	}
	
	@Override
	public void cambiarDuenioNSS(String nss, Long idPersonaDuenia,
			Long idPersonaAsignar) {
		
		this.log.debug("Se va cambiar la relación del NSS [" + nss
				+ "] de la persona original " + idPersonaDuenia + " a la persona "
				+ idPersonaAsignar);
		
		DitPersona fisicaAsignar = this.em.find(DitPersona.class, idPersonaAsignar);
		DitLlaveAsegurado ditLlaveAsegurado = this.em.find(DitLlaveAsegurado.class, nss);
		if(ditLlaveAsegurado == null){
			ditLlaveAsegurado = new DitLlaveAsegurado();
			ditLlaveAsegurado.setRefBusca(nss);
		}
		
		StringBuffer queryJPA = new StringBuffer();
		queryJPA.append("from DitAsignacionNss asig ");
		queryJPA.append("where asig.ditPersona.cveIdPersona = :idPersonaDuenia ");
		queryJPA.append("and asig.numNss = :nss");
		
		Query query = this.em.createQuery(queryJPA.toString());
		query.setParameter("idPersonaDuenia", idPersonaDuenia);
		query.setParameter("nss", nss);
		
		DitAsignacionNss entity = (DitAsignacionNss) query.getSingleResult();

		entity.setDitPersona(fisicaAsignar);
		entity.setFecRegistroActualizado(new Date());

		ditLlaveAsegurado.setDitPersona(fisicaAsignar);
		ditLlaveAsegurado.setDitAsignacionNss(entity);
	}

	@Override
	public Long obtenerIdPersonaPorNSS(String nss) {
		StringBuffer queryJPA = new StringBuffer();
		queryJPA.append("select asig.ditPersona.cveIdPersona from DitAsignacionNss asig ");
		queryJPA.append("where asig.numNss = :nss");
		Query query = this.em.createQuery(queryJPA.toString());
		query.setParameter("nss", nss);
		Long idPersona = null;
		try{
			idPersona = (Long)query.getSingleResult();
		}catch(NoResultException nre){
			log.debug("No se localizó el NSS: "+nss);
		}
		
		return idPersona;
	}	
	
	
	 @Override
    public AsignacionNSS obtenerAseguradoPorNss(String nss) {

        BigDecimal indActivo = BigDecimal.ONE;
        BigDecimal indHomonimia = new BigDecimal(2);
		DitAsignacionNss ditAsignacion = null;
		
        Criteria criteria = this.getSession().createCriteria(DitAsignacionNss.class);

        criteria.add(Restrictions.eq("numNss", nss));
        criteria.add(Restrictions.isNull("fecRegistroBaja"));
        criteria.add(Restrictions.or(Restrictions.eq("indActivo", indHomonimia),Restrictions.or(Restrictions.eq("indActivo", indActivo), Restrictions.isNull("indActivo"))));

        ditAsignacion = (DitAsignacionNss) criteria.uniqueResult();
		return AseguradoHelper.persisToModel(ditAsignacion);
    }

    @Override
    public AsignacionNSS obtenerAseguradoPorNss(String nss, String curp) {

        DitAsignacionNss ditAsignacion = null;
        log.error("obtener asegurado por nss:" + nss + " curp:" + curp);
        try {
            Query query = em.createQuery(
                    "select a from DitAsignacionNss a where a.numNss=:numNss and a.ditPersona.curp=:curp and a.ditPersona.fecRegistroBaja is null and (a.indActivo=1 or a.indActivo=2 or a.indActivo is null)");
            query.setParameter("numNss", nss);
            query.setParameter("curp", curp);
            ditAsignacion = (DitAsignacionNss) query.getSingleResult();
        } catch (NoResultException e) {
            log.error("getAsignacionNss no obtuvo resultados", e);
            return null;
        } catch (NonUniqueResultException e) {
            log.error("getAsignacionNss regresa mes de un resultado", e);
            return null;
        }

        return AseguradoHelper.persisToModel(ditAsignacion);
    }
	 
    @Override
    public AsignacionNSS obtenerAseguradoPorNssConBajaLogica(String numNss) {
    	AsignacionNSS asignacion =null;
		Session session = this.getSession();
		String query ="select nss.CVE_ID_ASIGNACION_NSS idNss, nss.NUM_NSS numNss, persona.CVE_ID_PERSONA idPersona,"+
				"persona.NOM_NOMBRE nombre, persona.NOM_PRIMER_APELLIDO primerApe, persona.NOM_SEGUNDO_APELLIDO segundoApe,"+
				"persona.CURP from dit_asignacion_nss nss"+
				" inner join dit_persona persona on nss.cve_id_persona = persona.cve_id_persona"+
				" where nss.num_nss = '"+ numNss+"'"+" and nss.FEC_REGISTRO_BAJA is not null";
		
		SQLQuery queryNSS = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryNSS.list();

		if(!resultado.isEmpty()) {

			Object[] nss = resultado.get(0);

			Long idAsignacion = ((BigDecimal)nss[0]).longValue();
			String numNSS = (String) nss[1];
			Long idPersonaIntegrante = ((BigDecimal)nss[2]).longValue();
			String nombre = (String) nss[3];
			String primerApellido = (String) nss[4];
			String segundoApellido = (String) nss[5];
			String curp = (String)nss[6];

			asignacion = new AsignacionNSS();
			asignacion.setIdAsignacionNSS(idAsignacion);
			asignacion.setNss(numNSS);
			asignacion.setNssStr(numNSS);
			asignacion.setIdPersona(idPersonaIntegrante);
			asignacion.setNombre(nombre);
			asignacion.setPrimerApellido(primerApellido);
			asignacion.setSegundoApellido(segundoApellido);
			asignacion.setCurp(curp);

		}

		return asignacion;
    }
	
	 @Override
    public AsignacionNSS obtenerAseguradoPorIdAsignacion(Long idAsignacion) {

        BigDecimal indActivo = BigDecimal.ONE;
		DitAsignacionNss ditAsignacion = null;
		
        Criteria criteria = this.getSession().createCriteria(DitAsignacionNss.class);

        criteria.add(Restrictions.eq("cveIdAsignacionNss", idAsignacion));
        ditAsignacion = (DitAsignacionNss) criteria.uniqueResult();
		return AseguradoHelper.persisToModel(ditAsignacion);
    }
	


}
