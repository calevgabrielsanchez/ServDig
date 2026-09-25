package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.exception.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/serie")
public class SerieController extends AbstractController{

    @Autowired
    private transient PersonaBusinessRemote personaBusiness;
	

	/**
	 * Metodo para obtener una lista de series para llenar el combo de la pantalla de datos complementarios de asegurados
	 * @param codigo
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/get/series", method=RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object> getSeries(@RequestParam String delegacion, @RequestParam String subdelegacion, HttpServletResponse response){

        Map result = new HashMap<String, Object>();
        
        Long d = delegacion.equals("") ? null : Long.getLong(delegacion);
        Long sd = subdelegacion.equals("") ? null : Long.getLong(subdelegacion);
        
        try{
        	List<Serie> series = personaBusiness.getSeriesNss(d, sd);

        	if(series != null && series.size() > 0){
	            this.log.debug("series[*** " + series + "***]");
	            result.put("series", series);
            }else{
            	throw new SeriesNoLocalizadasException("No se haigaron Series");
            }
        	
        }catch(SeriesNoLocalizadasException e) {
        	this.procesarErrorDeNegocio(e, result, response);
        }
       
        return result;
	       
	} 
	
	/**
	 * Metodo para obtener una sola serie a partir de un idSerie contenido en otro objeto Serie
	 * @param codigo
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/get/serie", method=RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object> getSeries(@RequestParam Long idSerie, HttpServletResponse response){

        Map result = new HashMap<String, Object>();
        
        try{
        	Serie serieSolicitada = new Serie();
        	serieSolicitada.setIdSerie(idSerie);
        			
        	Serie serie = personaBusiness.getSerie(serieSolicitada);

        	if(serie != null){
	            this.log.debug("serie --> " + serie);
	            result.put("serie", serie);
            }else{
            	throw new SeriesNoLocalizadasException("No se haigo ninguna Serie");
            }
        	
        }catch(SeriesNoLocalizadasException e) {
        	this.procesarErrorDeNegocio(e, result, response);
        }
       
        return result;
	       
	} 
}
