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
import mx.gob.imss.ctirss.delta.gestion.individuo.util.WebserviceTools;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.PersonaMoralDataTable;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.bean.UploadFileBean;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.CargaMasivaValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

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
@RequestMapping(value = "/persona/moral")
public class CargaMasivaMoralController extends AbstractController {
    
	@Autowired	
	private PersonaBusinessRemote personaBusiness;
    
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	
	@Autowired
    private PersonaMoralBusinessRemote personaMoralBusiness;

	@RequestMapping(value = "/registro-masivo", method = RequestMethod.GET)
	public String getPersona(Model model, HttpSession session,
			HttpServletRequest request) {
		session.removeAttribute("lstRegistros");
		model.addAttribute("uploadFileForm", new UploadFileBean());

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

		return "CargaMasivaMoral";
	}
	
	@RequestMapping(value="/registro-masivo/cargarArchivo", method=RequestMethod.POST)
	public String getInformacionArchivo( @ModelAttribute ("uploadFileForm") UploadFileBean oForm, BindingResult result, SessionStatus status, HttpSession session, Model model )throws Exception {
	
		log.debug("entre al metodo de cargar archivo" +  oForm);
		
 		Solicitud solicitud = new Solicitud();
 		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		if (usuario != null) {
			solicitud.setUsuario(usuario.getUsuario());
		}

		boolean registrarSolicitud = false, registroRepetido = false;
		
        /* Resumen del data teibol */
        int registrosAltaIMSS = 0, registrosExistentesIMSS = 0, registrosNoValidados = 0, registrosErrores = 0, registrosRepetidos = 0;
        Long folioSolicitud = 0L;
		
		String vista = "";
		ArrayList<Moral> objListaRegistros = new ArrayList<Moral>();
		Moral objPersonaMoral = null;
		
        // Lista que contendra los registros "distinct" del archivo, es decir SIN los repetidos
        ArrayList<Moral> registrosOriginales = new ArrayList<Moral>();
		
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
                	objPersonaMoral = CargaMasivaValidator.validaRegistroArchivoMoral(linea, numRegistro);
                	log.debug("el objeto 'objPersonaMoral' es: " + objPersonaMoral + " extraido de la línea [" + numRegistro + "] del archivo");
                	
                	// Primero validamos los registros repetidos (excepto si hay lineas en blanco), en tal caso no se hace gran cosa
                	if(!linea.trim().equals("")){
                		registroRepetido = verificarExistencia(objPersonaMoral, registrosOriginales);
                	}
                    
                	if(registroRepetido){
//                	if(false){ // hay que revisar por que no funciona esto para los registros repetidos de persona moral (en caso de que quieran que se haga)
                			   // pero con este if, funciona igual que como estaba antes
                    	log.warn("el registro " + numRegistro + " del archivo ya existe!!");
                    	objPersonaMoral.setAltaEnImss("Registro repetido");
                    	objPersonaMoral.setTipoSociedad(new TipoSociedad());
                    	objListaRegistros.add(objPersonaMoral);
                    	registrosRepetidos ++;
                    }else{
                	
	                	if(objPersonaMoral.getPersonaCalificaciones().get(0).getCalificacion().getDescripcion() == null){
	                		
	                		try{
	                			Moral pm = consultaRegistrosEnEntidadesGuvernamentales(objPersonaMoral, numRegistro);
	                			
	    	            		PersonaCalificacion personaCalificacion = new PersonaCalificacion();
	    	            		personaCalificacion.setCalificacion(new Calificacion());
	    	            		pm.getPersonaCalificaciones().add(personaCalificacion);
	                			
	                			if(pm.getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().intValue() == 2){ // 2 --> validado por SAT
	            					TipoTramite tipoTramite = new TipoTramite();
	            					tipoTramite.setDesTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.TIPO_TRAMITE_1_REGISTRO_PERSONA);
	            					tipoTramite.setIdTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.REGISTRO_PERSONA.longValue());
	            					
	            					Tramite tramite = new Tramite();
	//            					tramite.setIdTramite(indiceRegistrosValidos);	esto funcionaba en el viejo modelo, en el nuevo no, sepa el armaño por que!
	            					tramite.setTipoTramite(tipoTramite);
	            					tramite.setPersonaMoral(pm);
	            					
	            					solicitud.getTramite().add(tramite);
	            					
	            					registrarSolicitud = true;
	            					
	            					//indiceRegistrosValidos ++;
	            					
	                                /* Incrementamos el contador de los registros que sí se podran dar de alta en la BDU */
	                                registrosAltaIMSS ++;
	                                
	                                pm.setAltaEnImss("Permitido");
	            					
	                            }else if(pm.getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().intValue() == 4){
	                                registrosNoValidados ++;
//	                                pm.setAltaEnImss("Registro con errores");
	                                pm.setAltaEnImss("No localizado ni en IMSS ni en EE");
	                                objListaRegistros.add(pm);
	                            }else if(pm.getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().intValue() == 5){
	                                registrosExistentesIMSS ++;
	                                pm.setAltaEnImss("Ya existente en BDU");
	                                objListaRegistros.add(pm);
	                            }
	                			
	                		}catch(Exception e){
	                			log.error("algo salio mal, este es el error: ", e);
	                		}
	                	}else{
//	                        objPersonaMoral.setAltaEnImss("No Permitido");
	                		objPersonaMoral.setAltaEnImss(objPersonaMoral.getPersonaCalificaciones().get(0).getCalificacion().getDescripcion());
	                        
	                        objListaRegistros.add(objPersonaMoral);
	                        
	                        /* Se incrementa el contador cada que encuentra un registro que NO paso la validacion del metodo validaRegistroArchivoMoral(linea, numRegistro); */
	                        registrosErrores ++;
	                    }
                	
	                    // Agregamos el registro en cuestion a la lista de distincts
	                	registrosOriginales.add(objPersonaMoral);
	                	
	                }//if-else de los registros repetidos
                    
                    numRegistro ++;
                }//while
				
				/* Si la solicitud tiene al menos 1 registro con estatus 1 o 2, se registrara en BDU. Sino, pues no */
				log.debug("¿La solicitud tiene registros validos? " + registrarSolicitud);
				if(registrarSolicitud){
					
					Solicitud solicitudRegresado = solicitudPersonaBusiness.procesarSolicitudNueva(solicitud);
					String sXml = WebserviceTools.getStringXml(solicitudRegresado);
					log.info("La solicitud recuperada de la base de datos es: \n" + sXml);

	                folioSolicitud = solicitudRegresado.getIdSolicitud();
	                log.debug("EL FOLIO DE LA NUEVA SOLICITUD DE CARGA MASIVA ES: " + folioSolicitud);
	                
	                //Ahora, se deben extraer de la solicitud ya procesada 'solicitudRegresado' todos aquellos registros con estatus 'Permitido' para mostrarlos en el datateibol
	                for(Tramite tramite: solicitudRegresado.getTramite()){
	                	objListaRegistros.add(tramite.getPersonaMoral());
	                }
	                Collections.sort(objListaRegistros, new Comparator(){
	                	public int compare(Object o1 , Object o2){
	                			                		
	                		Moral f1 = (Moral) o1;
	                		Moral f2 = (Moral) o2;
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
		
		model.addAttribute("personaMoral", new Moral());

		session.setAttribute("lstRegistros", objListaRegistros);
		session.setAttribute("origen", "cmpm");
		
	    /* Resumen del data teibol */
        model.addAttribute("folioSolicitud", folioSolicitud);
        model.addAttribute("personasMorales", objListaRegistros);
        model.addAttribute("registrosArchivo", objListaRegistros.size());
        model.addAttribute("registrosAltaIMSS", registrosAltaIMSS);
        model.addAttribute("registrosNoValidados", registrosNoValidados);
        model.addAttribute("registrosErrores", registrosErrores);
        model.addAttribute("registrosExistentesIMSS", registrosExistentesIMSS);
        model.addAttribute("registrosRepetidos", registrosRepetidos);
        
		vista = "CargaMasivaMoral";
		return vista;
	}
		
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/registro-masivo/recuperaArchivo", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Moral> recuperaDatosArchivo(@RequestBody PersonaMoralDataTable aoData, SessionStatus status, HttpSession session){
   
    	DatosEntradaPaginador<Moral> objDataEntrada = new DatosEntradaPaginador<Moral>();
        objDataEntrada.setModelo(aoData.getoForm());
                
        final DatosSalidaPaginador<Moral> outData = new DatosSalidaPaginador<Moral>();  
        Object objLista = session.getAttribute("lstRegistros");
        if (objLista != null){
        	ArrayList<Moral> objListPersonas = (ArrayList<Moral>)objLista;
        	outData.setAaData(objListPersonas);
        	outData.setiTotalRecords(objListPersonas.size());
        	outData.setiTotalDisplayRecords(objListPersonas.size());
        	outData.setsEcho(objDataEntrada.getsEcho());
        	
        }else{
        	outData.setAaData(new ArrayList<Moral>());
        	outData.setiTotalRecords(0);
        	outData.setiTotalDisplayRecords(0);
        }
        
        log.debug("Lst size: " + outData.getAaData().size());
        log.trace("entity list: " + outData.getAaData());
        return  outData;
    }
	
    private Moral consultaRegistrosEnEntidadesGuvernamentales(Moral objPersonaMoral, int numRegistro){

        Moral objPersonaBusqueda = null;
        try{
                          
            //REALIZAMOS LA BUSQUEDA DE LA PERSONA POR RFC
            if(objPersonaMoral.getRfc() != null){
                
                //BUSCAMOS EN IMSS POR RFC
                List<Moral> listaPersonasMorales = personaMoralBusiness.buscarPersonaMoralPorRfcEnImss(objPersonaMoral.getRfc());
                objPersonaBusqueda = (listaPersonasMorales != null && listaPersonasMorales.size() > 0) ? listaPersonasMorales.get(0) : null;

                //VERIFICA SI ENCONTRO EL RFC EN IMSS
                if(objPersonaBusqueda != null){
                	
                	setPersonaCalificaciones(objPersonaBusqueda);
                	
                    objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_5_ENCONTRADO_IMSS);
                    objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(5L);
                    objPersonaBusqueda.setNumeroLineaArchivo(objPersonaMoral.getNumeroLineaArchivo());
                    log.debug("encontro rfc en imss");
                }else{
                    //BUSCAMOS POR RFC EN SAT
                    objPersonaBusqueda = personaBusiness.buscarPersonaMoralPorRfcEnSat(objPersonaMoral.getRfc());
                    
                    //VERIFICAMOS SI SE ENCONTRO EL RFC EN SAT
                    if(objPersonaBusqueda != null){
                    
                    	setPersonaCalificaciones(objPersonaBusqueda);
                    	
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);
                        objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(2L);
                        objPersonaBusqueda.setNumeroLineaArchivo(objPersonaMoral.getNumeroLineaArchivo());
                        log.debug("encontro rfc en sat");
                    }                       
                }
            }   

            //VERIFICAMOS SI EL PROCESO DE BUSQUEDA EN IMSS Y EN ENTIDADADES EXTERNAS NO TUVO EXITO
            if (objPersonaBusqueda == null){
                
                //LA PERSONA NO FUE ENCONTRADA EN NINGUN LUGAR
                objPersonaBusqueda = new Moral();
                
                setPersonaCalificaciones(objPersonaBusqueda);
                
                objPersonaBusqueda.setRfc(objPersonaMoral.getRfc());
                objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_4_NO_VALIDADO);
                objPersonaBusqueda.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(4L);
                objPersonaBusqueda.setNumeroLineaArchivo(objPersonaMoral.getNumeroLineaArchivo());
                objPersonaBusqueda.setTipoSociedad(new TipoSociedad());
                log.debug("el registro no se encontró en IMSS ni en SAT");
            }           
        }
        catch(Exception e){
            log.error("Se generó un error al consultar los datos del registro en el servidor", e);
        }
        
        return objPersonaBusqueda;  
    }   

    /**
     * Este metodo mete en la lista de personaCalificaciones 1 objeto Calificacion para evitar los errores diabolicos de NullPointerException
     * @param objPersonaBusqueda
     */
    public void setPersonaCalificaciones(Moral objPersonaBusqueda){
    	if(objPersonaBusqueda.getPersonaCalificaciones().size() == 0){
    		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
    		personaCalificacion_opf.setCalificacion(new Calificacion());
    		objPersonaBusqueda.getPersonaCalificaciones().add(personaCalificacion_opf);
    	}
    }
    
    /**
     * 191807 310812
     * Poderosisima funcion que verifica la existencia de registros repetidos en el archivo
     * @param moral
     * @param registrosOriginales
     * @return
     */
    private boolean verificarExistencia(Moral moral, List<Moral> registrosOriginales){
    	
    	boolean existencia = false;
    	
    	if(registrosOriginales.size() != 0){

    		for(Moral moralAux : registrosOriginales){
    			String rfcAux = moralAux.getRfc() == null ? "" : moralAux.getRfc();
    			if(rfcAux.equals(moral.getRfc() == null ? "" : moral.getRfc())){
    				existencia = true;
    				break;
    			}
    		}
    	}
    	
    	return existencia;
    	
    }
}