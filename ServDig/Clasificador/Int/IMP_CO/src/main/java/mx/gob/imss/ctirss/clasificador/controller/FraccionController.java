package mx.gob.imss.ctirss.clasificador.controller;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend;
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

@Controller
@RequestMapping({"/fraccion"})
public class FraccionController {
  private DataTableService fraccionDataTableService;
  
  private FraccionService fraccionService;
  
  @RequestMapping({"/lista"})
  public String home() {
    return "resultadosBusqueda";
  }
  
  @RequestMapping(value = {"/buscarFraccionPorGrupo"}, method = {RequestMethod.POST})
  @ResponseBody
  public FraccionDataTableReply getFraccionXGrupo(@RequestBody List aoData) {
    FraccionDataTableSend send = new FraccionDataTableSend();
    send.parserArray(aoData);
    FraccionDataTableReply reply = (FraccionDataTableReply)this.fraccionDataTableService.filter((AbstractDataTableSend)send);
    reply.setsEcho(send.getsEcho());
    return reply;
  }
  
  @RequestMapping(value = {"/buscarFraccionPorClave"}, method = {RequestMethod.GET})
  @ResponseBody
  public Fraccion getFraccionXClave(@RequestParam String desFraccion) {
    Fraccion f = this.fraccionService.obtenerFraccionPorClave(desFraccion);
    return f;
  }
  
  @RequestMapping(value = {"/buscarFraccionPorCatalogoAnterior"}, method = {RequestMethod.POST})
  @ResponseBody
  public FraccionDataTableReply getFraccionXCatalogoAnterior(@RequestBody List aoData) {
    FraccionDataTableSend send = new FraccionDataTableSend();
    send.parserArray(aoData);
    FraccionDataTableReply reply = null;
    if (send.getDispatch() != null && !send.getDispatch().equals(""))
      if (send.getDispatch().equals("dpPalabraClave")) {
        reply = (FraccionDataTableReply)this.fraccionDataTableService.filterXPalabraAnterior((AbstractDataTableSend)send);
      } else {
        reply = (FraccionDataTableReply)this.fraccionDataTableService.filterXNumeroAnterior((AbstractDataTableSend)send);
      }  
    reply.setsEcho(send.getsEcho());
    return reply;
  }
  
  @RequestMapping(value = {"/buscarFraccionPorCatalogoNuevo"}, method = {RequestMethod.POST})
  @ResponseBody
  public FraccionDataTableReply getFraccionXCatalogoNuevo(@RequestBody List aoData) {
    FraccionDataTableSend send = new FraccionDataTableSend();
    send.parserArray(aoData);
    FraccionDataTableReply reply = null;
    if (send.getDispatch() != null && !send.getDispatch().equals(""))
      if (send.getDispatch().equals("dpPalabraClave")) {
        reply = (FraccionDataTableReply)this.fraccionDataTableService.filterXPalabra((AbstractDataTableSend)send);
      } else {
        reply = (FraccionDataTableReply)this.fraccionDataTableService.filterXNumero((AbstractDataTableSend)send);
      }  
    reply.setsEcho(send.getsEcho());
    return reply;
  }
  
  @RequestMapping(value = {"/buscarFraccionEnCatalogoAnterior"}, method = {RequestMethod.POST})
  @ResponseBody
  public FraccionDataTableReply buscarFraccionEnCatalogoAnterior(@RequestBody List aoData) {
    FraccionDataTableSend send = new FraccionDataTableSend();
    send.parserArray(aoData);
    FraccionDataTableReply reply = null;
    if (send.getDispatch() != null && !send.getDispatch().equals(""))
      if (send.getDispatch().equals("dpPalabraClave")) {
        reply = (FraccionDataTableReply)this.fraccionDataTableService.filterXPalabraEnAnterior((AbstractDataTableSend)send);
      } else {
        reply = (FraccionDataTableReply)this.fraccionDataTableService.filterXNumeroEnAnterior((AbstractDataTableSend)send);
      }  
    reply.setsEcho(send.getsEcho());
    return reply;
  }
  
  @RequestMapping(value = {"/imprimir"}, method = {RequestMethod.GET})
  public String imprimir(@RequestParam String desFraccion, Model model) {
    Fraccion f = this.fraccionService.obtenerFraccionPorClave(desFraccion);
    model.addAttribute("fraccion", f);
    return "imprimir";
  }
  
  @Autowired
  public void setFraccionDataTableService(DataTableService fraccionDataTableService) {
    this.fraccionDataTableService = fraccionDataTableService;
  }
  
  @Autowired
  public void setFraccionService(FraccionService fraccionService) {
    this.fraccionService = fraccionService;
  }
}
