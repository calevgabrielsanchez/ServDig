package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramitesInvalidosException;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.WebserviceTools;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.PersonaFisicaDataTable;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.bean.UploadFileBean;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.CargaMasivaValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */

@Controller
@RequestMapping(value = "/persona/fisica")
public class CargaMasivaFisicaController extends AbstractController {
    
    @Autowired  
    private PersonaBusinessRemote personaBusiness;
    
    @Autowired
    private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
    
	@RequestMapping(value = "/registro-masivo", method = RequestMethod.GET)
	public String getPersona(Model model, HttpSession session,
			HttpServletRequest request) {
		session.removeAttribute("lstRegistros");
		model.addAttribute("uploadFileForm", new UploadFileBean());

		// se mete este atributo en el modelo para que no ponga errores de
		// javascript la primera vez que se carga la pagina en el
		// $(document).ready(...) de la jsp
		model.addAttribute("folioSolicitud", 0L);

		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		/*
		 * Procesamos la información del openSSO
		 */
		if (sso != null) {
			ServletContext context = session.getServletContext();
			String rolVentanilla = context.getInitParameter("rolVentanilla");
	        String rolInternet = context.getInitParameter("rolInternet");
	        String[] arrayRolVentanilla = {rolVentanilla};
	        String[] arrayRolInternet = {rolInternet};

			Usuario usuario = new Usuario();
			usuario.setUsuario(sso.getNombre());

			PerfilUsuario pu = new PerfilUsuario();
			if (this.checkGrantedAuthorities(arrayRolVentanilla)) {
				pu.setIdPerfilUsuario(100L);
			} else if (this.checkGrantedAuthorities(arrayRolInternet)) {
				pu.setIdPerfilUsuario(200L);
			}
			pu.setDescripcion(sso.getPerfil());
			usuario.setPerfilUsuario(pu);

			UsuarioFuncionario uf = new UsuarioFuncionario();
			if (sso.getDelegacion() != null) {
				uf.setDelegacion(new Delegacion());
				uf.getDelegacion().setId(sso.getDelegacion().longValue());
			}
			if (sso.getSubdelegacion() != null) {
				usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
				uf.setSubdelegacion(new Subdelegacion());
				uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}
			uf.setUsuario(usuario);
			usuario.setUsuarioFuncionario(uf);

			// Subimos a la sesion la informacion del usuario
			session.setAttribute(KEY_USUARIO, usuario);
			this.setFechaSistema(session);
		}

		return "CargaMasivaFisica";
	}

    @RequestMapping(value="/registro-masivo/cargarArchivo", method=RequestMethod.POST)
    public String getInformacionArchivo(@ModelAttribute("uploadFileForm") UploadFileBean oForm, BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletRequest request ){
        
        log.debug("entre al metodo de cargar archivo" +  oForm);
        
        Solicitud solicitud = new Solicitud();
        
        /**
         * Debemos de obtener el nombre del usuario
         */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		if (usuario != null) {
			solicitud.setUsuario(usuario.getUsuario());
		}

        boolean registrarSolicitud = false, registroRepetido = false;
        
        // Resumen del data teibol
        int registrosAltaIMSS = 0, registrosExistentesIMSS = 0, registrosNoValidados = 0, registrosErrores = 0, registrosRepetidos = 0;
        Long folioSolicitud = 0L;
        
        String vista = "";
        ArrayList<Fisica> objListaRegistros = new ArrayList<Fisica>();	

        Fisica registroOriginal = null;
        Fisica objPersonaFisica = null;
        
        List<Fisica> personas= null;
        
        Solicitud solicitudRegresado = new Solicitud();
        
        // Lista que contendra los registros "distinct" del archivo, es decir SIN los repetidos
//        ArrayList<Fisica> registrosOriginales = new ArrayList<Fisica>();
        
        // Lista en donde se almacenaran las lineas que se vayan leyendo del archivo para validar posteriormente lineas repetidas
        ArrayList<String> lineas = new ArrayList<String>();
        
        try{
            if(!oForm.getFileData().isEmpty()){
                String linea = null;
                BufferedReader bfr = new BufferedReader(new InputStreamReader(oForm.getFileData().getInputStream()));
                int numRegistro = 1;
                while ((linea = bfr.readLine()) != null) {
                    if(numRegistro == 1000){
                        throw new Exception("error el archivo tiene mas de 1000 registros");
                    }
                    log.debug("el valor de la linea es [" + linea +"]");
                    personas = CargaMasivaValidator.validarRegistroArchivoFisica(linea, numRegistro);
                    registroOriginal = personas.get(0);
                    objPersonaFisica = personas.get(1);
                    log.debug("el objeto 'registroOriginal' es: " + registroOriginal + " extraido de la línea [" + numRegistro + "] del archivo");
                    log.debug("el objeto 'objPersonaFisica' es: " + objPersonaFisica + " extraido de la línea [" + numRegistro + "] del archivo");
                    
                	// Primero validamos los registros repetidos (excepto si hay lineas en blanco), en tal caso no se hace gran cosa
                	if(!linea.trim().equals("")){
//                		registroRepetido = verificarExistencia(registroOriginal, registrosOriginales);
                		registroRepetido = verificarExistencia(linea, lineas);
                	}
                    
                	if(registroRepetido){
                    	log.warn("el registro " + numRegistro + " del archivo ya existe!!");
                    	registroOriginal.setAltaEnImss("Registro repetido");
                    	objListaRegistros.add(registroOriginal);
                    	registrosRepetidos ++;
                    	registroRepetido = false; // reseteamos la bandera de registros repetidos
                    }else{
                    
	                    char estatusRegistroCompleto = objPersonaFisica.getPersonaCalificaciones().get(0).getCalificacion().getDescripcion().charAt(0);
	                    
	                    // Los estatuses validos son 0 y 1. Los invalidos son 2 y 3
	                    if(estatusRegistroCompleto == '0' || estatusRegistroCompleto == '1'){
	                        
	                        try{
	                        	Fisica pf = buscarRegistroArchivoEnEntidadesExternasYIMSS(objPersonaFisica, numRegistro);
	                        	Long idCalificacion = pf.getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion();
	                        	String descripcionCalificacion = pf.getPersonaCalificaciones().get(0).getCalificacion().getDescripcion();
	                            
	                        	// cuando el registro en cuestion es hallado en renapo, no se setea la propiedad 'SubEstadosFormateados', entonces hay que hacerlo a mano
	                        	if(pf.getSubEstadosFormateados() == null || pf.getSubEstadosFormateados().equals("")){
	                        		if(pf.getPersonaCalificaciones() != null && pf.getPersonaCalificaciones().size() > 0){
	                        			pf.setSubEstadosFormateados(descripcionCalificacion);
	                        		}
	                        	}
	                        	
	                            if(idCalificacion.equals(1L) || idCalificacion.equals(2L) || (idCalificacion.equals(4L) && estatusRegistroCompleto == '0')){
	                                TipoTramite tipoTramite = new TipoTramite();
	                                tipoTramite.setDesTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.TIPO_TRAMITE_1_REGISTRO_PERSONA);
	                                tipoTramite.setIdTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.REGISTRO_PERSONA.longValue());
	                                
	                                Tramite tramite = new Tramite();
	//                              tramite.setIdTramite(indiceRegistrosValidos);   esto funcionaba en el viejo modelo, en el nuevo no, sepa el armaño por que!
	                                tramite.setTipoTramite(tipoTramite);
	                                
	                                if(idCalificacion.equals(4L)){ // si el estatus es 4, quiere decir que la persona no se hallo en ningun lado, de modo que el objeto 'pf'
	                                							   // no contiene muchos datos, entonces setearemos en el tramite el objeto que contiene al registro original
	                                    
	                                    registroOriginal.setCurp(registroOriginal.getCurpRenapo()); // seteamos el campo CURP ya que la pagina d validacion manual requiere este
	                                                                                                // campo para llenar la tabla
	                                    tramite.setPersonaFisica(registroOriginal);
	                                    
	                                    registrosNoValidados ++;
	                                    pf.setAltaEnImss("Validacion Manual pendiente");
	                                    
	                                    registroOriginal.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(idCalificacion);
	                                    registroOriginal.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(descripcionCalificacion);
	                                    registroOriginal.setAltaEnImss(pf.getAltaEnImss());
	                                    registroOriginal.setNumeroLineaArchivo(pf.getNumeroLineaArchivo());
	                                    
	                                }else{
	                                    tramite.setPersonaFisica(pf); 
	                                }
	                                
	                                solicitud.getTramite().add(tramite);
	                                
	                                registrarSolicitud = true;
	                                
	                                if(idCalificacion.equals(1L) || idCalificacion.equals(2L)){ // En caso de que el registro haya sido localizado en RENAPO o SAT, mandaremos al
	                                															// data teibol el objeto resultante de la busqueda
	                                    
	                                    // Incrementamos el contador de los registros que sí se podran dar de alta en la BDU
	                                    registrosAltaIMSS ++;
	                                    pf.setAltaEnImss("Permitido");
	                                    
	                                    // 191807 231112
	                                    // Por peticion de neri, cada que haya un registro 'Permitido' no debera mostrarse la columna 'Estatus' puesto que
	                                    // no es correcto que se muestre la calificacion de un registro que acaba de ser grabado con carga masiva. Solo de
	                                    // podra mostrar esta columna en aquellos registros que ya existan previamente en BDU
	                                    pf.setSubEstadosFormateados("");
	                                    
	                                }
	                                                                    
	                            }else if(idCalificacion.equals(4L) && estatusRegistroCompleto != '0'){
	                              
	                               // Se guardara en la lista de data teibol el registro original cuando el bloque de CURP y/o RFC sean validos pero no se haya 
	                               // localizado a la persona en ninguna de las entidades externas y ademas el bloque de DATOS BASICOS NO seavalido ya que no habria 
	                               // con que hacer una validacion manual posterior
	                               registroOriginal.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(objPersonaFisica.getPersonaCalificaciones().get(0).getCalificacion().getDescripcion().substring(1)); // con este metodo substring quitamos el primer digito del mensaje 
	                                                                                                              																											 // de error que indica si el registro del archivo es valido o no
//	                               registroOriginal.setAltaEnImss("Registro con errores");
	                               registroOriginal.setAltaEnImss("No localizado ni en IMSS ni en EE");
	                               objListaRegistros.add(registroOriginal);
	                               
	                               // Se incrementa el contador cada que encuentra un registro que NO paso la validacion del metodo validaRegistroArchivoFisica(linea, numRegistro);
	                               registrosErrores ++;
	                               
	                        	}else if(idCalificacion.equals(5L)){ // En caso de que el registro haya sido localizado por DATOS BASICOS en el IMSS, mandamos al data teibol el
	                            									 // objeto resultante de la busqueda
	                                registrosExistentesIMSS ++;
	                                pf.setAltaEnImss("Ya existente en BDU");
	                                
	                                objListaRegistros.add(pf);
	                            }                         
	                        }catch(Exception e){
	                            log.error("algo salio mal, este es el error: ", e);
	                        }
	                        
	                    }else{
	                        
	                        // Se guardara en la lista de data teibol el registro original cuando ninguno de los 3 bloques sea valido, pero antes le pasamos al objeto 
	                        //  registroOriginal el estatus del objeto objPersonaFisica que se obtuvo de las validaciones, y el mensaje que dice si se dara de alta en el IMSS
	                        registroOriginal.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(objPersonaFisica.getPersonaCalificaciones().get(0).getCalificacion().getDescripcion().substring(1)); // con este metodo substring quitamos el primer digito del mensaje de
	                        																																														 // error que indica si el registro del archivo es valido o no
//	                        registroOriginal.setAltaEnImss("Registro con errores");
	                        registroOriginal.setAltaEnImss(objPersonaFisica.getPersonaCalificaciones().get(0).getCalificacion().getDescripcion().substring(1)); // con este metodo substring quitamos el primer digito del mensaje 
	                        																																	// de error que indica si el registro del archivo es valido o no);
	                        
	                        objListaRegistros.add(registroOriginal);
	                        
	                        // Se incrementa el contador cada que encuentra un registro que NO paso la validacion del metodo validaRegistroArchivoFisica(linea, numRegistro);
	                        registrosErrores ++;
	                    }
	                    
	                    // Agregamos el registro en cuestion a la lista de distincts (Agregamos la linea actual a la lista de lineas leidas del archivo)
//	                	registrosOriginales.add(registroOriginal);
	                	lineas.add(linea);
	
	                }//if-else de los registros repetidos
                    
                    numRegistro ++;
                }//while
                
                // Si la solicitud tiene al menos 1 registro con estatus 1 o 2, se registrara en BDU. Sino, pues no
                log.debug("¿La solicitud tiene registros validos? " + registrarSolicitud);
                if(registrarSolicitud){
                    
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&& la solicitud procesada es:_" + solicitud);
                    
                    solicitudRegresado = solicitudPersonaBusiness.procesarSolicitudNueva(solicitud);
                    String sXml = WebserviceTools.getStringXml(solicitudRegresado);
                    log.info("La solicitud recuperada de la base de datos es: \n" + sXml);

                    folioSolicitud = solicitudRegresado.getIdSolicitud();
                    log.debug("EL FOLIO DE LA NUEVA SOLICITUD DE CARGA MASIVA ES: " + folioSolicitud);
                    
                    // Ahora, se deben extraer de la solicitud ya procesada 'solicitudRegresado' todos aquellos registros con estatus 'Permitido' para mostrarlos en el 
                    // datateibol
                    for(Tramite tramite: solicitudRegresado.getTramite()){
                    	objListaRegistros.add(tramite.getPersonaFisica());
                    }
                    Collections.sort(objListaRegistros, new Comparator(){
                    	public int compare(Object o1 , Object o2){
                    		                    		
                    		Fisica f1 = (Fisica) o1;
                    			Fisica f2 = (Fisica) o2;
                    		return f1.getNumeroLineaArchivo().compareTo(f2.getNumeroLineaArchivo());
                    		
                    	}
                    });
                    
                }else{ //este else es porque no hubo un solo registro valido para la solicitud
                    throw new TramitesInvalidosException();
                }
                
                log.debug("el objeto 'objListaRegistros' es: " + objListaRegistros + " y tiene un tamaño de: " + objListaRegistros.size());
                
            }else{
                log.debug("el archivo esta vacio");
            }
            
        }catch(AbstractException e){
            result.reject ("", e.getMessage());
            log.error(e.getMessage());
        }catch(Exception e){
            log.error("error al tratar de leer el archivo", e);
        }
        
        model.addAttribute("personaFisica", new Fisica());

        session.setAttribute("lstRegistros", objListaRegistros);
        session.setAttribute("origen", "cmpf");
        
        // Resumen del data teibol
        model.addAttribute("folioSolicitud", folioSolicitud);
        model.addAttribute("personasFisicas", objListaRegistros);
        model.addAttribute("registrosArchivo", objListaRegistros.size());
        model.addAttribute("registrosAltaIMSS", registrosAltaIMSS);
        model.addAttribute("registrosNoValidados", registrosNoValidados);
        model.addAttribute("registrosErrores", registrosErrores);
        model.addAttribute("registrosExistentesIMSS", registrosExistentesIMSS);
        model.addAttribute("registrosRepetidos", registrosRepetidos);
        
        vista = "CargaMasivaFisica";
        
        return vista;
    }
    
    /**
     * Primera implementacion del datateibol (sin paginar)... para la segunda implementacion este metodo esta de sobra
     * @param aoData
     * @param status
     * @param session
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/registro-masivo/recuperaArchivo", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Fisica> recuperaDatosArchivo(@RequestBody PersonaFisicaDataTable aoData, SessionStatus status, HttpSession session){
      
        DatosEntradaPaginador<Fisica> objDataEntrada = new DatosEntradaPaginador<Fisica>();
        objDataEntrada.setModelo(aoData.getoForm());
                
        final DatosSalidaPaginador<Fisica> outData = new DatosSalidaPaginador<Fisica>();  
        Object objLista = session.getAttribute("lstRegistros");
        if (objLista != null){
            ArrayList<Fisica> objListPersonas = (ArrayList<Fisica>)objLista;
            outData.setAaData(objListPersonas);
            outData.setiTotalRecords(objListPersonas.size());
            outData.setiTotalDisplayRecords(objListPersonas.size());
            outData.setsEcho(objDataEntrada.getsEcho());
            
        }else{
            outData.setAaData(new ArrayList<Fisica>());
            outData.setiTotalRecords(0);
            outData.setiTotalDisplayRecords(0);
        }
        
        //outData.setsEcho(send.getsEcho());
        log.debug("Lst size: " + outData.getAaData().size());
        log.trace("entity list: " + outData.getAaData());
        return  outData;
    }
    
    private Fisica buscarRegistroArchivoEnEntidadesExternasYIMSS(Fisica objPersonaFisica, int numRegistro){
    
        Fisica objPersonaBusqueda = null;
        try{
            
            //VERIFICAMOS SI EL REGISTRO TIENE CURP PARA VALIDARLO EN IMSS Y RENAPO
            if(!StringUtils.isBlank(objPersonaFisica.getCurpRenapo())){
                
                //BUSCAMOS EN IMSS POR CURP
                log.debug("*** Se va a buscar a la persona fisica con la CURP: " + objPersonaFisica.getCurpRenapo() + " en el IMSS ***");
                List<Fisica> listaPersonasFisicas = personaBusiness.buscarPersonaFisicaPorCurpEnImss(objPersonaFisica.getCurpRenapo());
                objPersonaBusqueda = (listaPersonasFisicas != null && listaPersonasFisicas.size() > 0) ? listaPersonasFisicas.get(0) : null;
                log.debug("### Este objeto se regreso de la busqueda con la CURP: " + objPersonaFisica.getCurpRenapo() + " en el IMSS: " + objPersonaBusqueda + " ###");
                
                //VERIFICA SI SE ENCONTRO LA CURP EN EL IMSS
                if(objPersonaBusqueda != null){
                	
                	if(objPersonaBusqueda.getPersonaCalificaciones().size() == 0){
	            		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
	            		personaCalificacion_opf.setCalificacion(new Calificacion());
	            		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
                	}
                	
                    objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_5_ENCONTRADO_IMSS);
                    objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(Utilerias.convertir(CalificacionPersona.ENCONTRADO_IMSS));
                    objPersonaBusqueda.setNumeroLineaArchivo(objPersonaFisica.getNumeroLineaArchivo());
                    objPersonaBusqueda.setCurpRenapo(objPersonaBusqueda.getCurp()); // necesitamos setear el curpRenapo para el data teibol
                    log.debug("*** Se encontro a la persona fisica con la CURP: " + objPersonaFisica.getCurpRenapo() + " en el IMSS ***");
                }else{
                    //BUSCAMOS POR CURP EN RENAPO
                    log.debug("*** Se va a buscar a la persona fisica con la CURP: " + objPersonaFisica.getCurpRenapo() + " en el RENAPO ***");
                    objPersonaBusqueda = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(objPersonaFisica.getCurpRenapo());
                    log.debug("### Este objeto se regreso de la busqueda con la CURP: " + objPersonaFisica.getCurpRenapo() + " en el RENAPO: " + objPersonaBusqueda + " ###");

                    //VERIFICAMOS SI SE ENCONTRO LA CURP EN REANPO
                    if(objPersonaBusqueda != null){
                    	
                    	if(objPersonaBusqueda.getPersonaCalificaciones().size() == 0){
    	            		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
    	            		personaCalificacion_opf.setCalificacion(new Calificacion());
    	            		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
                    	}
                    	
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(Utilerias.convertir((CalificacionPersona.VALIDADO_RENAPO)));
                        objPersonaBusqueda.setNumeroLineaArchivo(objPersonaFisica.getNumeroLineaArchivo());
                        objPersonaBusqueda.setCurpRenapo(objPersonaBusqueda.getCurp()); // necesitamos setear el curpRenapo para el data teibol
                        log.debug("*** Se encontro a la persona fisica con la CURP: " + objPersonaFisica.getCurpRenapo() + " en el RENAPO ***");
                        
    					// 191807 311012 se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
    					// serviciosPersonaBusiness.agregarIdentificadores(objPersonaBusqueda); esto no funciona sepa suchi por que
//                        agregarIdentificadores(objPersonaBusqueda);
                        
                    }
                }
            }else{
                log.debug("$$$ NO se realizaran las busquedas por la CURP ya que no cumplio las validaciones de formato $$$");
            }
            
            //VERIFICAMOS SI EL PROCESO DE BUSQUEDA POR CURP TUVO EXITO
            if (objPersonaBusqueda == null){
                
                //REALIZAMOS LA BUSQUEDA DE LA PERSONA POR RFC
                if(!StringUtils.isBlank(objPersonaFisica.getRfc())){
                    
                    //BUSCAMOS EN IMSS POR RFC
                    log.debug("*** Se va a buscar a la persona fisica con el RFC: " + objPersonaFisica.getRfc() + " en el IMSS ***");
                    List<Fisica> listaPersonasFisicas = personaBusiness.buscarPersonaFisicaPorRfcEnImss(objPersonaFisica.getRfc());
                    objPersonaBusqueda = (listaPersonasFisicas != null && listaPersonasFisicas.size() > 0) ? listaPersonasFisicas.get(0) : null;
                    log.debug("### Este objeto se regreso de la busqueda con el RFC: " + objPersonaFisica.getRfc() + " en el IMSS: " + objPersonaBusqueda + " ###");

                    //VERIFICA SI ENCONTRO EL RFC EN IMSS
                    if(objPersonaBusqueda != null){
                    	
                    	if(objPersonaBusqueda.getPersonaCalificaciones().size() == 0){
    	            		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
    	            		personaCalificacion_opf.setCalificacion(new Calificacion());
    	            		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
                    	}
                    	
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_5_ENCONTRADO_IMSS);
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(Utilerias.convertir((CalificacionPersona.ENCONTRADO_IMSS)));
                        objPersonaBusqueda.setNumeroLineaArchivo(objPersonaFisica.getNumeroLineaArchivo());
                        objPersonaBusqueda.setCurpRenapo(objPersonaBusqueda.getCurp()); // necesitamos setear el curpRenapo para el data teibol
                        log.debug("*** Se encontro a la persona fisica con el RFC: " + objPersonaFisica.getRfc() + " en el IMSS ***");
                    }else{
                        //BUSCAMOS POR RFC EN SAT
                        log.debug("*** Se va se va a buscar a la persona fisica con el RFC: " + objPersonaFisica.getRfc() + " en el SAT ***");
                        objPersonaBusqueda = personaBusiness.buscarPersonaFisicaPorRfcEnSat(objPersonaFisica.getRfc());
                        log.debug("### Este objeto se regreso de la busqueda con el RFC: " + objPersonaFisica.getRfc() + " en el SAT: " + objPersonaBusqueda + " ###");
                        
                        //VERIFICAMOS SI SE ENCONTRO EL RFC EN SAT
                        if(objPersonaBusqueda != null){ 
                        	
                        	if(objPersonaBusqueda.getPersonaCalificaciones().size() == 0){
        	            		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
        	            		personaCalificacion_opf.setCalificacion(new Calificacion());
        	            		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
                        	}
                        	
                            objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);
                            objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(Utilerias.convertir((CalificacionPersona.VALIDADO_SAT)));
                            objPersonaBusqueda.setNumeroLineaArchivo(objPersonaFisica.getNumeroLineaArchivo());
                            objPersonaBusqueda.setCurpRenapo(objPersonaBusqueda.getCurp()); // necesitamos setear el curpRenapo para el data teibol
                            log.debug("*** Se encontro a la persona fisica con el RFC: " + objPersonaFisica.getRfc() + " en el SAT ***");
                            
        					// 191807 311012 se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
        					// serviciosPersonaBusiness.agregarIdentificadores(objPersonaBusqueda); esto no funciona sepa suchi por que
//                            agregarIdentificadores(objPersonaBusqueda);
                        }               
                    }
                }else{
                    log.debug("$$$ NO se realizaran las busquedas por el RFC ya que no cumplio las validaciones de formato $$$");
                }   
            }
            
            // Si no se obtuvo resultados al buscar por CURP en IMSS y RENAPO, ni por RFC en IMSS y SAT, haremos un ultimo intento por DATOS BASICOS en IMSS y RENAPO
            if (objPersonaBusqueda == null){
                
                // Si los 6 datos basicos estan todos presentes, quiere decir que el bloque de datos basicos SÍ es valido
                if(
                    !StringUtils.isBlank(objPersonaFisica.getNombre()) && 
                    !StringUtils.isBlank(objPersonaFisica.getPrimerApellido()) &&
                    !StringUtils.isBlank(objPersonaFisica.getSegundoApellido()) &&
                    objPersonaFisica.getSexo().getIdSexo() != null &&
                    !StringUtils.isBlank(objPersonaFisica.getFechaNacimientoFormateada()) &&
                    objPersonaFisica.getLugarNacimiento().getClave() != null
                   ){
                
                    // Realizamos la busqueda de la persona por DATOS BASICOS en el IMSS
                    log.debug("*** Se va a buscar a la persona fisica con los DATOS BASICOS: " + objPersonaFisica + " en el IMSS ***");
                    List<Fisica> personas = personaBusiness.buscarPersonaFisicaPorDatosBasicosEnImss(objPersonaFisica);
                    objPersonaBusqueda = (personas != null && personas.size() > 0) ? personas.get(0) : null;
                    log.debug("### Este objeto se regreso de la busqueda con los DATOS BASICOS: " + objPersonaFisica + " en el IMSS: " + objPersonaBusqueda + " ###");
                    
                    if(objPersonaBusqueda != null){
                    	
                    	if(objPersonaBusqueda.getPersonaCalificaciones().size() == 0){
    	            		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
    	            		personaCalificacion_opf.setCalificacion(new Calificacion());
    	            		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
                    	}
                    	
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_5_ENCONTRADO_IMSS);
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(Utilerias.convertir((CalificacionPersona.ENCONTRADO_IMSS)));
                        objPersonaBusqueda.setNumeroLineaArchivo(objPersonaFisica.getNumeroLineaArchivo());
                        objPersonaBusqueda.setCurpRenapo(objPersonaBusqueda.getCurp()); // necesitamos setear el curpRenapo para el data teibol
                        log.debug("*** Se encontro a la persona fisica con los DATOS BASICOS: " + objPersonaFisica + " en el IMSS ***");
                    }else{
                        // Si la persona no fue hallada en el IMSS, se buscara en RANAPO tambien mediante DATOS BASICOS
                        log.debug("*** Se va a buscar a la persona fisica con los DATOS BASICOS: " + objPersonaFisica + " en el RENAPO ***");
                        objPersonaBusqueda = personaBusiness.buscarPersonaFisicaPorDatosBasicosEnRenapo(objPersonaFisica.getNombre(), objPersonaFisica.getPrimerApellido(), objPersonaFisica.getSegundoApellido(), objPersonaFisica.getSexo().getIdSexo(), objPersonaFisica.getFechaNacimiento(), Integer.parseInt(objPersonaFisica.getLugarNacimiento().getClave()));
                        log.debug("### Este objeto se regreso de la busqueda con los DATOS BASICOS: " + objPersonaFisica + " en el RENAPO: " + objPersonaBusqueda + " ###");
                        
                        if(objPersonaBusqueda != null){
                        	
                        	if(objPersonaBusqueda.getPersonaCalificaciones().size() == 0){
        	            		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
        	            		personaCalificacion_opf.setCalificacion(new Calificacion());
        	            		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
                        	}
                        	
                            objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);
                            objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(Utilerias.convertir((CalificacionPersona.VALIDADO_RENAPO)));
                            objPersonaBusqueda.setNumeroLineaArchivo(objPersonaFisica.getNumeroLineaArchivo());
                            objPersonaBusqueda.setCurpRenapo(objPersonaBusqueda.getCurp()); // necesitamos setear el curpRenapo para el data teibol
                            log.debug("*** Se encontro a la persona fisica con los DATOS BASICOS: " + objPersonaFisica + " en el RENAPO ***");
                            
        					// 191807 311012 se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
        					// serviciosPersonaBusiness.agregarIdentificadores(objPersonaBusqueda); esto no funciona sepa suchi por que
//                            agregarIdentificadores(objPersonaBusqueda);
                        }
                    }
                    
                }else{
                    log.debug("$$$ NO se realizaran las busquedas por DATOS BASICOS ya que no cumplieron las validaciones de formato $$$");
                }
                
            }

            //VERIFICAMOS SI EL PROCESO DE BUSQUEDA EN IMSS Y EN ENTIDADADES EXTERNAS NO TUVO EXITO
            if(objPersonaBusqueda == null){
                //LA PERSONA NO FUE ENCONTRADA EN NINGUN LUGAR
                objPersonaBusqueda = new Fisica();
                
        		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
        		personaCalificacion_opf.setCalificacion(new Calificacion());
        		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
                
                objPersonaBusqueda.setRfc(objPersonaFisica.getRfc());
                objPersonaBusqueda.setCurpRenapo(objPersonaFisica.getCurpRenapo());
                objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_4_NO_VALIDADO);
                objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(4L);
                objPersonaBusqueda.setNumeroLineaArchivo(objPersonaFisica.getNumeroLineaArchivo());
                log.debug("&&& La persona fisica no se encontró en IMSS, ni en SAT y RENAPO &&&");
            }           
            
        }catch(Exception e){
            log.error("Se generó un error al consultar los datos del registro en el servidor", e);
        }
        
        return objPersonaBusqueda;  
    }
    
    /**
     * 191807 310812
     * Poderosisima funcion que verifica la existencia de registros repetidos en el archivo
     * @param fisica
     * @param registrosOriginales
     * @return
     */
//    private boolean verificarExistencia(Fisica fisica, List<Fisica> registrosOriginales){
    private boolean verificarExistencia(String linea, List<String> lineas){
    	
    	boolean existencia = false;
    	
//    	if(registrosOriginales.size() != 0){
    	if(lineas.size() != 0){

//    		for(Fisica fisicaAux : registrosOriginales){
    		for(String lineaAux : lineas){
//    			if(
//    					fisicaAux.getCurpRenapo() != null && fisicaAux.getCurpRenapo().equals(fisica.getCurpRenapo()) &&
//    					fisicaAux.getRfc() != null && fisicaAux.getRfc().equals(fisica.getRfc()) && 
//    					fisicaAux.getNombre() != null && fisicaAux.getNombre().equals(fisica.getNombre()) &&
//    					fisicaAux.getPrimerApellido() != null && fisicaAux.getPrimerApellido().equals(fisica.getPrimerApellido()) &&
//    					fisicaAux.getSegundoApellido() != null && fisicaAux.getSegundoApellido().equals(fisica.getSegundoApellido()) &&
//    					fisicaAux.getSexo() != null && /* fisicaAux.getSexo().getIdSexo() != null && */ fisicaAux.getSexo().getIdSexo().equals(fisica.getSexo().getIdSexo()) &&
//    					((fisicaAux.getFechaNacimiento() != null && fisicaAux.getFechaNacimiento().equals(fisica.getFechaNacimiento())) || (fisicaAux.getFechaNacimiento() == null && fisica.getFechaNacimiento() == null)) &&
//    					fisicaAux.getLugarNacimiento() != null && /* fisicaAux.getLugarNacimiento().getNombre() != null && */ fisicaAux.getLugarNacimiento().getNombre().equals(fisica.getLugarNacimiento().getNombre())
//    				
//    			){
    			if(lineaAux.equals(linea)){
    				existencia = true;
    				break;
    			}
    		}
    	}
    	
    	return existencia;	
    }
        
}