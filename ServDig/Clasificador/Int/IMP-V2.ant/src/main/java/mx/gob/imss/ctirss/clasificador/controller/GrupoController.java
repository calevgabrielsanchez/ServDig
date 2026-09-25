 package mx.gob.imss.ctirss.clasificador.controller;
 
 import java.util.List;
 import mx.gob.imss.ctirss.clasificador.model.business.Grupo;
 import mx.gob.imss.ctirss.clasificador.service.GrupoService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 
 @Controller
 @RequestMapping({"/grupo"})
 public class GrupoController
 {
   private GrupoService grupoService;
   
   @RequestMapping(value = {"/cargarGrupos"}, method = {RequestMethod.GET})
   @ResponseBody
   public List<Grupo> getGrupoXDivision(@RequestParam int cveDivision) {
    System.out.println("GrupoController: Passing through..." + cveDivision);
    List<Grupo> grupos = this.grupoService.cargarGruposPorDivision(cveDivision);
   
    return grupos;
   }
 
 
 
 
   
   @Autowired
   public void setGrupoService(GrupoService grupoService) {
     this.grupoService = grupoService;
   }
 }


