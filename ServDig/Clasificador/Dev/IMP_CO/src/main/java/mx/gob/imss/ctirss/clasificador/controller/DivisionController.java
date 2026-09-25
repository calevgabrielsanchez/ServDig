package mx.gob.imss.ctirss.clasificador.controller;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Division;
import mx.gob.imss.ctirss.clasificador.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/division"})
public class DivisionController {
  private DivisionService divisionService;
  
  @RequestMapping(value = {"/cargarActivas"}, method = {RequestMethod.GET})
  @ResponseBody
  public List<Division> getActivas() {
    System.out.println("DivisionController: Passing through...");
    List<Division> divisiones = this.divisionService.cargarDivisionesActivas();
    return divisiones;
  }
  
  @Autowired
  public void setDivisionService(DivisionService divisionService) {
    this.divisionService = divisionService;
  }
  
  @RequestMapping(value = {"/cargarVigentes"}, method = {RequestMethod.GET})
  @ResponseBody
  public List<Division> getVigentes() {
    System.out.println("DivisionController: Passing through...");
    List<Division> divisiones = this.divisionService.cargarDivisionesVigentes();
    return divisiones;
  }
}
