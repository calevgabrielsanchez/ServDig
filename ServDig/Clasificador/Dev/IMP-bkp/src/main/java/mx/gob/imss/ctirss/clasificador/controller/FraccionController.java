/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.controller;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.model.controller.FraccionDataTableReply;
import mx.gob.imss.ctirss.clasificador.model.controller.FraccionDataTableSend;
import mx.gob.imss.ctirss.clasificador.service.DataTableService;
import mx.gob.imss.ctirss.clasificador.service.FraccionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lucio
 *
 */
/**
 * @author vanderluk
 *
 */
@Controller
@RequestMapping(value="/fraccion")
public class FraccionController {
	
	
	private DataTableService fraccionDataTableService;
	
	private FraccionService fraccionService;
	

	@RequestMapping(value = "/lista")
    public String home() {
        
        
        return "resultadosBusqueda";
    }
	
	
	
	
	/**
	 * 
	 * @param aoData
	 * @return
	 */
	@RequestMapping(value="/buscarFraccionPorGrupo", method=RequestMethod.POST )
    public @ResponseBody FraccionDataTableReply getFraccionXGrupo(@RequestBody List aoData ) {
		
		FraccionDataTableSend send = new FraccionDataTableSend();
		send.parserArray(aoData);

		FraccionDataTableReply reply =(FraccionDataTableReply) this.fraccionDataTableService.filter(send);
		
		
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }

	/**
	 * 
	 * @param desFraccion
	 * @return
	 */
	@RequestMapping(value="/buscarFraccionPorClave", method=RequestMethod.GET )
    public @ResponseBody Fraccion getFraccionXClave(@RequestParam String desFraccion  ) {
		Fraccion f = this.fraccionService.obtenerFraccionPorClave(desFraccion );
        return f;
    }


	
	/**
	 * 
	 * @param aoData
	 * @return
	 */
	@RequestMapping(value="/buscarFraccionPorCatalogoAnterior", method=RequestMethod.POST )
    public @ResponseBody FraccionDataTableReply getFraccionXCatalogoAnterior(@RequestBody List aoData ) {
		
		FraccionDataTableSend send = new FraccionDataTableSend();
		send.parserArray(aoData);
		
		FraccionDataTableReply reply =null;
		
		if(send.getDispatch() != null && !send.getDispatch().equals("")){
			
			if(send.getDispatch().equals("dpPalabraClave")){
				
				reply = (FraccionDataTableReply) this.fraccionDataTableService
						.filterXPalabraAnterior(send);
			}else{
				
				reply =  (FraccionDataTableReply) this.fraccionDataTableService
						.filterXNumeroAnterior(send);
			}
			
		}
		
		
		
		
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	
	

	@RequestMapping(value="/buscarFraccionPorCatalogoNuevo", method=RequestMethod.POST )
    /**
     * 
     * @param aoData
     * @return
     */
	public @ResponseBody FraccionDataTableReply getFraccionXCatalogoNuevo(@RequestBody List aoData ) {
		
		FraccionDataTableSend send = new FraccionDataTableSend();
		send.parserArray(aoData);
		
		FraccionDataTableReply reply =null;
		
		if(send.getDispatch() != null && !send.getDispatch().equals("")){
			
			if(send.getDispatch().equals("dpPalabraClave")){
				
				reply = (FraccionDataTableReply) this.fraccionDataTableService
						.filterXPalabra(send);
			}else{
				
				reply =  (FraccionDataTableReply) this.fraccionDataTableService
						.filterXNumero(send);
			}
			
		}
		
		
		
		
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	

	@RequestMapping(value="/buscarFraccionEnCatalogoAnterior", method=RequestMethod.POST )
    public @ResponseBody FraccionDataTableReply buscarFraccionEnCatalogoAnterior(@RequestBody List aoData ) {
		
		FraccionDataTableSend send = new FraccionDataTableSend();
		send.parserArray(aoData);
		
		FraccionDataTableReply reply =null;
		
		if(send.getDispatch() != null && !send.getDispatch().equals("")){
			
			if(send.getDispatch().equals("dpPalabraClave")){
				
				reply = (FraccionDataTableReply) this.fraccionDataTableService
						.filterXPalabraEnAnterior(send);
			}else{
				
				reply =  (FraccionDataTableReply) this.fraccionDataTableService
						.filterXNumeroEnAnterior(send);
			}
			
		}
		
		
		
		
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	

	
	/**
	 * @param desFraccion
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/imprimir", method=RequestMethod.GET )
    public String  imprimir(@RequestParam String desFraccion, Model model  ) {
		
		Fraccion f = this.fraccionService.obtenerFraccionPorClave(desFraccion );
		model.addAttribute("fraccion", f);
        return "imprimir";
    }
	


	/**
	 * @param fraccionDataTableService the fraccionDataTableService to set
	 */
	@Autowired
	public void setFraccionDataTableService(
			DataTableService fraccionDataTableService) {
		this.fraccionDataTableService = fraccionDataTableService;
	}





	/**
	 * @param fraccionService the fraccionService to set
	 */
	@Autowired
	public void setFraccionService(FraccionService fraccionService) {
		this.fraccionService = fraccionService;
	}







}
