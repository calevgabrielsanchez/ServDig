package mx.gob.imss.ctirss.clasificador.controller;

import mx.gob.imss.ctirss.clasificador.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
  private DivisionService divisionService;
  
  @RequestMapping({"/nuevo"})
  public String home() {
    return "welcome";
  }
  
  @RequestMapping({"/anterior"})
  public String anterior() {
    return "welcomeAnterior";
  }
  
  @Autowired
  public void setDivisionService(DivisionService divisionService) {
    this.divisionService = divisionService;
  }
}

