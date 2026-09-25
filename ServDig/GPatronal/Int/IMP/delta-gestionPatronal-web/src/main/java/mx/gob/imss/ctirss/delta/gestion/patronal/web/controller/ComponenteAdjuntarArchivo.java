package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.activation.FileDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/componente/adjuntarArchivo")
public class ComponenteAdjuntarArchivo extends AbstractController {

	private final String VIEW_COMPONENTE = "inicioComponenteAdjuntarArchivo";
	public static final String KEY_ARCHIVOS_ADJUNTOS = "keySesssionArchivosAdjuntos";
	
	@Autowired
	private ActividadEcServiceRemote actividadEcService;

	
	@RequestMapping("/")
	public String init(HttpSession session) {
		session.removeAttribute(KEY_ARCHIVOS_ADJUNTOS);
		return VIEW_COMPONENTE;
	}

	@RequestMapping(value="/guardar" , method = RequestMethod.POST)
	@ResponseBody
	public String guardar(
			@RequestParam("file") MultipartFile file,			
			@RequestParam("folio") String folio,
			@RequestParam("claveTipoTramite") String claveTipoTramite,
			HttpSession session, HttpServletRequest request) {
		
		String cadenaRespuesta = "ok";
		List<String> listaArchivos = new ArrayList<String>();
		Boolean error = false;

        try {
        	log.debug("::: folio: " + folio + "\n" + "archivo: " + file.getSize() + "Kbs - " + file.getContentType() + "nombreArchivo: "
                    + file.getOriginalFilename() + "claveTipoTramite: " + claveTipoTramite );

        	//Obtenemos el path de la carpeta destino para los archivos
        	//ServletContext context = session.getServletContext();
        	//String pathServer = context.getRealPath("/resources");        	
        	String pathServer = obtieneRutaServidor();
        	
        	log.debug("::: archivo: " + file.getSize() + "Kbs - " + file.getContentType());

        	 if(file.isEmpty()){
        		 log.debug("::: El archivo viene vacio");
        		 error = true;
        		 //cadenaRespuesta = "error";
        		 cadenaRespuesta = "El archivo viene vacio";
             }else if( file.getSize() > (7 * 1024 * 1024) ){ //validamos el peso del archivo por adjuntar 7 MB maximo
            	log.debug("::: Peso del archivo no permitido ");
                error = true;
                //cadenaRespuesta = "error";
                cadenaRespuesta = "Peso del archivo no permitido";
            }else{ //sino excede el maximo permitido por archivo
            	
            	listaArchivos = (List<String>) session.getAttribute(KEY_ARCHIVOS_ADJUNTOS);
            	if(listaArchivos != null){
            		for (Iterator<String> iterator = listaArchivos.iterator(); iterator.hasNext();) {
						 String n = iterator.next();
						 if(n.equals(file.getOriginalFilename())){
							 log.debug("::: El archivo ya existe");
							 cadenaRespuesta = "El archivo ya ha sido adjuntado";
							 error = true;
							 break;
						 }
					}
            	}
            	
            	if(!error){
                	String path = pathServer + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" + folio + "/";
                	log.debug("::: Se guarda el archivo "+file.getOriginalFilename()+" en la ruta: " + path);
                	guardarAdjuntoEnSistemaDeArchivos(file.getBytes(), path, file.getOriginalFilename());            	
                	log.debug("::: "+file.getOriginalFilename()+" se guardo con exito en file system");
                	//guardamos registro del archivo en la BD            	
                	AdjuntosClasificacion adjuntos = new AdjuntosClasificacion();
                	adjuntos.setCveIdTipoTramite(new Long(claveTipoTramite));
                	adjuntos.setNombreArchivo(file.getOriginalFilename());
                	adjuntos.setRefFolio(folio);
                	adjuntos.setRutaArchivo(path);
                	actividadEcService.guardarArchivoAdjunto(adjuntos);
                	log.debug("::: "+file.getOriginalFilename()+" se guardo con exito la referencia en BD");
                	//agregamos a la lista de sesion para los documentos 
                	listaArchivos.add(file.getOriginalFilename());
                	session.setAttribute(KEY_ARCHIVOS_ADJUNTOS, listaArchivos);
                	cadenaRespuesta = "ok";            		
            	}
            }

        } catch (Exception e) {
        	log.error("::: Ocurrio un error al guardar archivo adjunto: " + e.getMessage());
        	error = true;
        	cadenaRespuesta = "error";
        }        
        
		return cadenaRespuesta;
	}
	
	private String obtieneRutaServidor(){		
		ResourceBundle rb = ResourceBundle.getBundle("clasificacion");
		String ruta = rb.getString("ruta.archivos.adjuntos.tramites.clasificacion");
		return ruta;
	}	
	
    private void guardarAdjuntoEnSistemaDeArchivos(byte [] doc, String ruta, String nombreDocumento) throws IOException {
        try {
            FileDataSource ds = new FileDataSource(ruta);
            if (!ds.getFile().exists()) {
                ds.getFile().mkdirs();
            }
            ds = new FileDataSource(ruta + nombreDocumento);
            OutputStream os = ds.getOutputStream();
            os.write(doc);
            os.close();
        } catch (IOException io) {
        	io.printStackTrace();
            log.error("Error al escribir el documento: " + io, io);
            throw new IOException();
        }
    }
	
	@RequestMapping(value="/quitar" , method = RequestMethod.POST)
	@ResponseBody
	public String quitar(@RequestParam("folio") String folio,
			@RequestParam("nombreArchivo") String nombreArchivo,
			HttpSession session) {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		String respuestaEliminarDoc;
		Boolean error = false;

		try {
			log.debug("::: Quitando archivo folio: "+folio+" , nombreArchivo: " + nombreArchivo);
			// buscamos el path del archivo a borrar en la tabla de adjuntos y
			// lo marcamos como borrado
			actividadEcService.quitarArchivoAdjunto(folio, nombreArchivo);
			
			//Quitamos el archivo del list de sesion
			List<String> listaArchivos = (List<String>) session.getAttribute(KEY_ARCHIVOS_ADJUNTOS);
			List<String> listaAux = new ArrayList<String>();			
			//Usamos lista auxiliar ignorando el archivo a borrar
			for(String arch: listaArchivos) {
				if(!arch.equals(nombreArchivo)) {
					listaAux.add(arch);
				}
			}
			session.setAttribute(KEY_ARCHIVOS_ADJUNTOS, listaAux);
			respuestaEliminarDoc = "ok";
		} catch (Exception e) {
			log.error("::: Ocurrio un error al borrar archivo adjunto: "
					+ e.getMessage());
			e.printStackTrace();
			error = true;
			respuestaEliminarDoc = "error";
		}

		respuesta.put("error", error);
		
		return respuestaEliminarDoc;
	}
	
	@RequestMapping(value="/consultar" , method = RequestMethod.POST)
	@ResponseBody
	public String consultar(@RequestParam("folio") String folio,
			HttpSession session) {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		List<String> listaArchivos = new ArrayList<String>();
		Boolean error = false;
		String respuestaConsulta = "";

		try {
			log.debug("::: Consultando lista de archivos para folio: " + folio);
			// buscamos los archivos que se han adjuntado en caso de retomar el tramite
			List<AdjuntosClasificacion> adjList = actividadEcService.consultarArchivoAdjunto(folio);
			log.debug("::: Se encontraron "+adjList.size()+" archivos adjuntos al folio " + folio);
			for (Iterator<AdjuntosClasificacion> iterator = adjList.iterator(); iterator.hasNext();) {
				AdjuntosClasificacion adjuntosClasificacion = iterator.next();
				log.debug(">>> Archivo " + adjuntosClasificacion.getNombreArchivo());
				listaArchivos.add(adjuntosClasificacion.getNombreArchivo());
				respuestaConsulta += adjuntosClasificacion.getNombreArchivo() + "|";
			}
			
			session.setAttribute(KEY_ARCHIVOS_ADJUNTOS, listaArchivos);
			
		} catch (Exception e) {
			log.error("::: Ocurrio un error al consultar archivos adjuntos: "
					+ e.getMessage());
			e.printStackTrace();
			error = true;
			respuestaConsulta = "error";
		}
		log.debug(">>> ressssssspuestaaaaa:::::::::::::: " + respuestaConsulta);
		respuesta.put("error", error);
		respuesta.put("listaDocFolio", listaArchivos);
		return respuestaConsulta;
	}
		

}
