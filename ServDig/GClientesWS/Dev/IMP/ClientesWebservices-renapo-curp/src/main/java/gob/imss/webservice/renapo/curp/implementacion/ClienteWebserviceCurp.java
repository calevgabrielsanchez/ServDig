package gob.imss.webservice.renapo.curp.implementacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gob.imss.webservice.renapo.curp.cliente.CurpKioscosBean;
import gob.imss.webservice.renapo.curp.cliente.ParametrosConsulta;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoActa;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ClavesRenapo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SexoEnum;

public class ClienteWebserviceCurp {

	Logger log = Logger.getLogger("cliente.webservices");
	private static int serviceTimeOut = 30000;
    private transient final DateFormat formatoFechaRenapo = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
    private final String STR_HOMBRE = "HOMBRE";
    private final String STR_MUJER = "MUJER";
    private final String STR_NO_BINARIO = "NO BINARIO";
    
   // private final StringUtils strUitls = new StringUtils();
    public Fisica buscarPersonaFisicaPorDatosBasicosEnRenapo(
    				final String sNombres,
    				final String sPrimerApellido,
    				final String sSegundoApellido,
    				final int iIdSexo,
    				final Date fechaNacimiento,
    				final int iIdEntidadFederativa) throws ClienteWebserviceRenapoCurpException{
        Fisica fisica = null;
        
    	//PREPARA EL FILTRO DE BUSQUEDA PARA EL RENAPO
        final ParametrosConsulta entrada = new ParametrosConsulta();
        entrada.setNombre(sNombres);
        entrada.setApellidoPaterno(sPrimerApellido);
        entrada.setApellidoMaterno(sSegundoApellido);
        // se ajusta el parse para considerar al no binario
        if(iIdSexo == SexoEnum.HOMBRE.getCodigo().intValue())
        	entrada.setSexo("H");
        else if(iIdSexo == SexoEnum.MUJER.getCodigo().intValue())
        	entrada.setSexo("M");
        else if(iIdSexo == SexoEnum.NO_BINARIO.getCodigo().intValue())
        	entrada.setSexo("X");
        
        
        
        entrada.setFechNac(formatoFechaRenapo.format(fechaNacimiento));

        // CONVERSION ENTIDAD FEDERATIVA
        for (Entry<String, Integer> entry : ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.entrySet()) {
            if (entry.getValue() == iIdEntidadFederativa) {
                entrada.setCveEntidadNac(entry.getKey());
                break;
            }
        }
        if (entrada.getCveEntidadNac() == null) {
            entrada.setCveEntidadNac("");
        }
        
        // CONSULTA RENAPO
        final CurpKioscosBean respuesta = consultaCURP(entrada);
    
        //OBTIENE LOS DATOS BASICOS DE LA PERSONA FISICA SI ESTOS FUERON LOCALIZADOS EN RENAPO;
        fisica = recuperaDatosBasicosRenapo(respuesta);
        
        //SE GENERA EL DOCUMENTO PROBATORIO|
        recuperaInformacionDocumentoProbatorio(respuesta, fisica);
        
        return fisica;        
    }

    public Fisica buscarPersonaFisicaPorCurpEnRenapo(final String curp) throws ClienteWebserviceRenapoCurpException { 
        Fisica fisica = null;
    	
    	// CONSULTA RENAPO
        final CurpKioscosBean respuesta = consultaDatosCURP(curp);
        
        //OBTIENE LOS DATOS BASICOS DE LA PERSONA FISICA SI ESTOS FUERON LOCALIZADOS EN RENAPO;
        fisica = recuperaDatosBasicosRenapo(respuesta);
        
        //SE GENERA EL DOCUMENTO PROBATORIO
        recuperaInformacionDocumentoProbatorio(respuesta, fisica);
        
        return fisica;
    }
    
	public CurpKioscosBean consultaDatosCURP(final String entrada)
			throws ClienteWebserviceRenapoCurpException {
		CurpKioscosBean result = null;
		try {
			//log.error("WebserviceCurp. ClienteWebservice. CURP. " + entrada  + " timestamp " + new Date());
			DatosWebServiceCurp thread = new DatosWebServiceCurp();
			thread.setCurp(entrada);
			thread.start();
			log.error("WebserviceCurp. ClienteWebservice. Se establece el tiempo del timeout del thread = " + serviceTimeOut  + " CURP " + entrada +
					" thread name " + thread.getName() + " thread id " + Thread.currentThread().getId());
			thread.join(serviceTimeOut);
			log.error("WebserviceCurp. ClienteWebservice. Se recupera el control del proceso desde el thread. " + new Date() +
					"CURP " + entrada + " thread name " + thread.getName());

			// VERIFICA SI SE GENERO UN ERROR EN EL THREAD
			if (thread.isExisteErrorServicio()) {
				log.error("Error por el thread ... + CURP " + entrada);
				throw new ClienteWebserviceRenapoCurpException("WebserviceCurp. Thread. Se genero un error al accesar el webservice CURP " + entrada);
			}

			// VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
			if (thread.isAlive()) {
				log.error("WebserviceCurp. ClienteWebservice. El thread sigue esperando la respuesa de RENAPO CURP " + entrada);
				thread.interrupt();
				log.error("WebserviceCurp. ClienteWebservice. El thread se ha interrumpido y se generara un ClienteWebserviceRenapoCurpException CURP " + entrada );
				throw new ClienteWebserviceRenapoCurpException("El thread se ha interrumpido erro de rtineOut de respuesta de RENAPO CURO" + entrada, 10002);
			} else {
				log.error("WebserviceCurp. ClienteWebservice. El thread termino satisfactoriamente la consulta a RENAPO dentro del timeout especificado CURP " + entrada);
				result = thread.getRespuesta();
				
				
				log.error("Respuesta :::"+ result);
				log.error("Respuesta :::"+ result.getStatusOper());
				log.error("Respuesta :::"+ result.getCodigoError());
				
				/*
				 * Segun la especificacion del WS de RENAPO, toda operacion
				 * exitosa tendra el estatus EXITOSO
				 */
				
				if (!result.getStatusOper().equals("EXITOSO")) {
					// log.error("Codigo error RENAPO -> " + result.getCodigoError() + "] msg " + result.getMessage()+"]");
					if (result.getCodigoError() == -1) {
						throw new ClienteWebserviceRenapoCurpException(
								"La respuesta del servicio de RENAPO es que no se encuentra disponible", 10003);
					} else if(result.getCodigoError()== 6){
						log.info("Se obtuvo codigo de error 6");
						/*
						throw new ClienteWebserviceRenapoCurpException(
								"CURP no localizado en RENAPO", 10006);
								*/
					} else {
						throw new ClienteWebserviceRenapoCurpException("La respuesta del servicio de RENAPO no fe exitosa" + result.getMessage(), 10003);
					}
				}
			}

			thread = null;
		} catch(ClienteWebserviceRenapoCurpException e) {
			throw e;
		} catch (InterruptedException e) {
			log.error("WebserviceCurp. ClienteWebservice. se interrumpio el tiempo de espera del servicio." + e.getMessage());
			e.printStackTrace();
			throw new ClienteWebserviceRenapoCurpException(10002);
		} catch (Exception e) {
			log.error("WebserviceCurp. ClienteWebservice. Se genero un error al accesar el webservice", e);
			throw new ClienteWebserviceRenapoCurpException("WebserviceCurp. ClienteWebservice. Se genero un error al accesar el webservice " + e.getMessage()); 
		}

		// log.debug("CurpKioscosBean: " + result);
		return result;
	}

    public CurpKioscosBean consultaCURP(final ParametrosConsulta entrada) throws ClienteWebserviceRenapoCurpException  {
    	CurpKioscosBean result = null;
    	//int serviceTimeOut = Integer.parseInt(ParametrosConfig.getParametro("timeOutClienteRenapo"));
		try {
			//log.error("WebserviceCurp. ClienteWebservice. Entrada. " + entrada );
			DatosWebServiceParametrosCurp thread = new DatosWebServiceParametrosCurp();
			thread.setParametrosEntrada(entrada);
			thread.start();

			log.error("WebserviceCurp. ClienteWebservice. Se establece el tiempo del timeout del thread = " + serviceTimeOut +
					" thread name " + thread.getName() + " thread id " + Thread.currentThread().getId());
			thread.join(serviceTimeOut);
			log.error("WebserviceCurp. ClienteWebservice. Se recupera el control del proceso desde el thread. " + new Date() +
					" thread name " + thread.getName());

			// VERIFICA SI SE GENERO UN ERROR EN EL THREAD
			if (thread.isExisteErrorServicio()) {
				throw new ClienteWebserviceRenapoCurpException("WebserviceCurp. Thread. Se genero un error al accesar el webservice oir datis estadisticos" + entrada);		
			}

			// VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
			if (thread.isAlive()) {
				log.error("WebserviceCurp. ClienteWebservice. El thread sigue esperando la respuesa de RENAPO" );
				thread.interrupt();
				log.error("WebserviceCurp. ClienteWebservice. El thread se ha interrumpido y se generara un ClienteWebserviceRenapoCurpException");
				throw new ClienteWebserviceRenapoCurpException("El thread se ha interrumpido erro de rtineOut de respuesta de RENAPO datos estadisticos" + entrada, 10002);
			} else {
				log.error("WebserviceCurp. ClienteWebservice. El thread termino satisfactoriamente la consulta a RENAPO dentro del timeout especificado");
				result = thread.getRespuesta();

				/*
				 * Segun la especificacion del WS de RENAPO, toda operacion
				 * exitosa tendra el estatus EXITOSO
				 */
				if (!result.getStatusOper().equals("EXITOSO")) {
					log.error("Codigo error RENAPO -> " + result.getCodigoError() + "] msg " + result.getMessage()+"]");
					if (result.getCodigoError() == -1) {
						throw new ClienteWebserviceRenapoCurpException(
								"La respuesta del servicio de RENAPO es que no se encuentra disponible", 10003);
					}else if(result.getCodigoError()== 6){
						log.info("Se obtuvo codigo de error 6");
						/*
						throw new ClienteWebserviceRenapoCurpException(
								"CURP no localizado en RENAPO", 10006);
								*/
					}else {
						throw new ClienteWebserviceRenapoCurpException("La respuesta del servicio de RENAPO datos estadisticos no fe exitosa " 
													+result.getMessage(), 10003);
					}
				}
			}

			thread = null;
		} catch(ClienteWebserviceRenapoCurpException e) {
			throw e;
		} catch (Exception e) {
			log.error("WebserviceCurp. ClienteWebservice. Se genero un error al accesar el webservice", e);
			throw new ClienteWebserviceRenapoCurpException("WebserviceCurp. ClienteWebservice. Se genero un error al accesar el webservice " + e.getMessage()); 
		}

		log.debug("CurpKioscosBean: " + result);
		return result;
    }
    
    private Fisica recuperaDatosBasicosRenapo(CurpKioscosBean respuesta){
        Fisica persona = null; // NOPMD 
    	log.error("respuesta .."+ respuesta.getMessage());
        if (respuesta != null && respuesta.getMessage().equals("")) {
            persona = new Fisica();
            persona.setNombre(respuesta.getNombre());
            persona.setPrimerApellido(respuesta.getApellido1());
            persona.setSegundoApellido(respuesta.getApellido2());
            persona.setCurp(respuesta.getCurp());
            //Se ajusta el parseo del sexo para considerar los no binarios
            if(respuesta.getSexo().equalsIgnoreCase(STR_HOMBRE))
            	persona.getSexo().setIdSexo(SexoEnum.HOMBRE.getCodigo());
            else if(respuesta.getSexo().equalsIgnoreCase(STR_MUJER))
            	persona.getSexo().setIdSexo(SexoEnum.MUJER.getCodigo());
            else if(respuesta.getSexo().equalsIgnoreCase(STR_NO_BINARIO))
            	persona.getSexo().setIdSexo(SexoEnum.NO_BINARIO.getCodigo());
            
            persona.getSexo().setDescripcion(respuesta.getSexo());
            persona.setFechaNacimientoFormateada(respuesta.getFechNac());
            String cveEntFedNac = ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get(respuesta.getCveEntidadNac()).toString();
            
            
            //se cambio el seteo del bean para que rellen con cero a la derecha en caso de ser una posicion
           
            persona.getLugarNacimiento().setClave(StringUtils.leftPad(cveEntFedNac, 2,'0'));
            
            //persona.getLugarNacimiento().setClave(cveEntFedNac.toString());
            persona.setEstatusRenapo(respuesta.getDesEstatusCURP());
            persona.setCveEstatusRenapo(respuesta.getEstatusCURP());

            // 191807 180912
            // Hay que hablitar este campo porque no aparece en el reporte PDF de asegurados
//          persona.getLugarNacimiento().setNombre("");
            persona.getLugarNacimiento().setNombre(respuesta.getDesEntidadNac());
            
            //FECHA DE NACIMIENTO
            if (respuesta.getFechNac() != null && !respuesta.getFechNac().equals("")){
                try{
                    persona.setFechaNacimiento(formatoFechaRenapo.parse(respuesta.getFechNac()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            
            //se setean las CURPs historicas
            log.error("la curp tiene como historica [" +respuesta.getCurpsHistoricas()+ "]");
            if(StringUtils.isNotEmpty(respuesta.getCurpsHistoricas())){
            	List <String> curpsHistoricas =  new ArrayList<String>();
            	String[] strCurpsHistoricas = respuesta.getCurpsHistoricas().split(",");
            	if(strCurpsHistoricas != null){
	            	for(String curp: strCurpsHistoricas){
	            		curpsHistoricas.add(curp);
	            	}
            	}else{
            		curpsHistoricas.add(respuesta.getCurpsHistoricas());
            	}
            	persona.setCurpsHistoricas(curpsHistoricas);
            }
        }
        return persona;
    }
    
    private Fisica recuperaInformacionDocumentoProbatorio(CurpKioscosBean respuesta, Fisica persona){ 
        if (persona != null) {
        	
        	// Se asigna el pais de acuerdo a lo que haya regresado el WS de RENAPO
        	asignarPais(respuesta, persona);
	        	
        	//VERIFICA SI AGREGAMOS EL ACTA DE NACIMIENTO
            if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor()){
                
                TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            	tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor());
            	tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getDescripcion());
            	
                //ACTA DE NACIMIENTO
                Nacimiento actaNacimiento = new Nacimiento();
                actaNacimiento.setAnio(respuesta.getAnioReg());
                actaNacimiento.setTomo(String.valueOf(respuesta.getTomo()));
                actaNacimiento.setCrip(String.valueOf(respuesta.getCRIP()));
                actaNacimiento.setNoFoja(String.valueOf(respuesta.getFoja()));
                actaNacimiento.setNoLibro(String.valueOf(respuesta.getLibro()));
                actaNacimiento.setNoActa(String.valueOf(respuesta.getNumActa()));
                actaNacimiento.setNoJuzgado("0"); //ESTE DATO DEBERA QUITARSE CUANDO LA TABLA DE DIT_NACIMIENTO.NUM_JUZGADO ACEPTE NULOS
                
                //MUNICIPIO - ENTIDAD DEL ACTA DE NACIMIENTO
                Municipio municipio = new Municipio(); 
               
                // 191807 231012 Se hace la asignacion del municipio y entidad de acuerdo a los valores recibidos en el WS
                corregirMunicipioInvalidoRENAPO(respuesta, municipio);
                actaNacimiento.setMunicipio(municipio); 
                
                TipoActa tipoActa = new TipoActa();
                tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor());
                tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getDescripcion());
                actaNacimiento.setTipoActa(tipoActa);
                
                Documento documento = new Documento();
                documento.setCveIdDocumento(DocumentosEnum.ACTA_NACIMIENTO.getId());
                documento.setDesDocumento(DocumentosEnum.ACTA_NACIMIENTO.getDescripcion());
                
                DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
                documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId());
                documentoPorTipo.setDocumento(documento);
                
                actaNacimiento.setDocumentoPorTipo(documentoPorTipo);
                
                persona.setActaNacimiento(actaNacimiento);
			} else {
            	
            	//ESTE BLOQUE DE CODIGO CORRESPONDE CUANDO EL TIPO DE DOCUMENTO NO ES UN ACTA DE NACIMIENTO
            	//Y POR LO TANTO CORRESPONDE A UN EXTRANJERO 
            	
				CURP docProbatorio = new CURP();
				docProbatorio.setCurp(persona.getCurp());
				
            	//MUNICIPIO - ENTIDAD IDENTIFICADO PARA PERSONAS EXTRANJERAS
                EntidadFederativa entidadFederativa = new EntidadFederativa();
                entidadFederativa.setClave(StringUtils.leftPad(String.valueOf(respuesta.getCveEntidadNac()),2 , '0'));   
                entidadFederativa.setNombre(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get(entidadFederativa.getClave()).toString());
                /*Municipio municipio = new Municipio();
                municipio.setEntidadFederativa(entidadFederativa);
                docProbatorio.setMunicipio(municipio);*/
            	
                if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor()){
                    //TIPO DE DOCUMENTO
                    docProbatorio.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor().longValue());
                    docProbatorio.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getDescripcion());
                    
                    docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getNumRegExtranjeros())); //NUMERO DEL REGISTRO NACIONAL DE EXTRANJEROS
                    
                    // DOCUMENTO POR TIPO
                    Documento documento = new Documento();
                    documento.setCveIdDocumento(DocumentosEnum.DOCUMENTO_MIGRATORIO.getId());
                    documento.setDesDocumento(DocumentosEnum.DOCUMENTO_MIGRATORIO.getDescripcion());
                    
                    DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
                    documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId());
                    documentoPorTipo.setDocumento(documento);
                    
                    docProbatorio.setDocumentoPorTipo(documentoPorTipo);
                    
                    persona.setDocumentoMigratorio(docProbatorio);
                } else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor()){
                    //TIPO DE DOCUMENTO
                    docProbatorio.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor().longValue());
                    docProbatorio.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getDescripcion());
                     
                    //DATOS DE CARTA DE NATURALIZACION
                    docProbatorio.setAnioRegistro(Long.valueOf(respuesta.getAnioReg())); //ANIO DE REGISTRO
                    docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getFolioCarta())); //FOLIO DE LA CARTA
                    
                    // DOCUMENTO POR TIPO
                    Documento documento = new Documento();
                    documento.setCveIdDocumento(DocumentosEnum.CARTA_NATURALIZACION.getId());
                    documento.setDesDocumento(DocumentosEnum.CARTA_NATURALIZACION.getDescripcion());
                    
                    DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
                    documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId());
                    documentoPorTipo.setDocumento(documento);
                    
                    docProbatorio.setDocumentoPorTipo(documentoPorTipo);
                    
                    persona.setCartaNaturalizacion(docProbatorio);
                } else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor()){
                    //TIPO DE DOCUMENTO
                    docProbatorio.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor().longValue());
                    docProbatorio.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion());
                	
                	//NUMERO UNICO DE EXTRANJERO 
                    docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getCRIP())); //CRIP
                    
                    // DOCUMENTO POR TIPO
                    Documento documento = new Documento();
                    documento.setCveIdDocumento(DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId());
                    documento.setDesDocumento(DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion());
                    
                    DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
                    documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId());
                    documentoPorTipo.setDocumento(documento);
                    
                    docProbatorio.setDocumentoPorTipo(documentoPorTipo);
                    
                    persona.setNumeroUnicoExtranjero(docProbatorio);
                } else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor()){
                    //TIPO DE DOCUMENTO
                    docProbatorio.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor().longValue());
                    docProbatorio.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion());

                    //DATOS DE CERTIFICADO DE NACIONALIDAD MEXICANA
                    docProbatorio.setAnioRegistro(Long.valueOf(respuesta.getAnioReg())); //ANIO DE REGISTRO
                    docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getFolioCarta())); //FOLIO DE LA CARTA
                    
                    // DOCUMENTO POR TIPO
                    Documento documento = new Documento();
                    documento.setCveIdDocumento(DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId());
                    documento.setDesDocumento(DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion());
                    
                    DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
                    documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId());
                    documentoPorTipo.setDocumento(documento);
                    
                    docProbatorio.setDocumentoPorTipo(documentoPorTipo);
                    
                    persona.setCertificadoNacionalidadMexicana(docProbatorio);
                } else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor()){
                    //TIPO DE DOCUMENTO
                    docProbatorio.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor().longValue());
                    docProbatorio.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion());

                    //DATOS DE OFICIO DE SOLICITANTE DE REFUGIADO
                    docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getCRIP())); //CRIP = NUMERO DE FOLIO DE OFICIO SOLICITANTE DE REFUGIADO
                    
                    // DOCUMENTO POR TIPO
                    Documento documento = new Documento();
                    documento.setCveIdDocumento(DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId());
                    documento.setDesDocumento(DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion());
                    
                    DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
                    documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId());
                    documentoPorTipo.setDocumento(documento);
                    
                    docProbatorio.setDocumentoPorTipo(documentoPorTipo);
                    
                    persona.setOficioSolicitanteRefugiado(docProbatorio);
                } else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor()){
                    //TIPO DE DOCUMENTO
                    docProbatorio.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor().longValue());
                    docProbatorio.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion());
                
                    //DATOS FORMA MIGRATORIA TURISTA
                    docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getCRIP())); //CRIP = NUMERO DE FOLIO DE OFICIO SOLICITANTE DE REFUGIADO
                    
                    // DOCUMENTO POR TIPO
                    Documento documento = new Documento();
                    documento.setCveIdDocumento(DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId());
                    documento.setDesDocumento(DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion());
                    
                    DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
                    documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId());
                    documentoPorTipo.setDocumento(documento);
                    
                    docProbatorio.setDocumentoPorTipo(documentoPorTipo);
                    
                    persona.setFormaMigratoriaTurista(docProbatorio);
                }            	
            }
        }
        return persona;
    }
    
    /**
     * 191807 231012
     * Este metodo sobre-escribe la clave de entidad de registro (campo NumEntidadReg del WS del renapo) en caso de que la entidad o el municipio
     * tengan algun valor reconocido como invalido. La llave compuesta 97-999 (entidad-municipio) que la identifica como 'invalida en renapo' ya
     * existe en el catalogo DG_CAT_MUNICIPIO, de modo que ya no va a fallar al momento de insertar los documentos probatorios en la BD 
     * @param respuesta
     */
    private void corregirMunicipioInvalidoRENAPO(CurpKioscosBean respuesta, Municipio municipio){
    	
        EntidadFederativa entidadFederativa = new EntidadFederativa();
        
        if(respuesta.getNumEntidadReg() > 32){
            entidadFederativa.setNombre("ENTIDAD INVALIDA RENAPO");
            entidadFederativa.setClave("97");
            
            municipio.setNombre("MUNICIPIO INVALIDO RENAPO");
            municipio.setClave("999");
        }else if(respuesta.getCveMunicipioReg() == 999){
            
        	entidadFederativa.setNombre(respuesta.getDesEntidadRegistro());
            entidadFederativa.setClave(StringUtils.leftPad(String.valueOf(respuesta.getNumEntidadReg()), 2, '0')); 
            
            municipio.setNombre("MUNICIPIO INVALIDO RENAPO");
            municipio.setClave("999");
        }else{
        	//se cambio el set de los datos para que el municipio sea a 3 posiciones y la entidad a 2
        	
            entidadFederativa.setNombre(respuesta.getDesEntidadRegistro());
            entidadFederativa.setClave(StringUtils.leftPad(String.valueOf(respuesta.getNumEntidadReg()), 2, '0')); 
            
            municipio.setNombre(respuesta.getDesMunicipio());
            municipio.setClave(StringUtils.leftPad(String.valueOf(respuesta.getCveMunicipioReg()), 3, '0'));
        }
        
        municipio.setEntidadFederativa(entidadFederativa);
    }
    
    /**
     * 191807 171212
     * Metodo que setea el pais dependiendo de la respuesta del WS de RENAPO
     * @param respuesta
     * @param persona
     */
    private void asignarPais(CurpKioscosBean respuesta, Fisica persona){
    	
    	// Se setea el pais para la pagina de resultado del servicio ICA
    	persona.setPais(new Pais());
    	
    	if(respuesta.getNacionalidad().equalsIgnoreCase("MEX")){
    		persona.getPais().setNacionalidad("MEXICANA");
    		persona.getPais().setIdPais(1);
    	}else{
    		persona.getPais().setNacionalidad("EXTRANJERO");
    		persona.getPais().setIdPais(2);
    	}
    	
    	persona.getPais().setDescripcion(respuesta.getNacionalidad());
    	
    }
    
}
