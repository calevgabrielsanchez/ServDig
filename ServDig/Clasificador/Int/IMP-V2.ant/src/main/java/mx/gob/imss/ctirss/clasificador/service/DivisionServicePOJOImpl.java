package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Division;
import mx.gob.imss.ctirss.clasificador.repository.DivisionRepository;
import mx.gob.imss.ctirss.clasificador.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivisionServicePOJOImpl
  implements DivisionService
{
  private DivisionRepository divisioRespository;
  
  public List<Division> cargarDivisionesActivas() {
    return this.divisioRespository.cargarDivisionesActivas();
  }


  
  public List<Division> cargarDivisionesInactivas() {
    return this.divisioRespository.cargarDivisionesInactivas();
  }
  
  @Autowired
  public void setDivisioRespository(DivisionRepository divisioRespository) {
    this.divisioRespository = divisioRespository;
  }

 
  public List<Division> cargarDivisionesVigentes() {
    return this.divisioRespository.cargarDivisionesVigentes();
  }
}
