package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.repository.FraccionRepository;
import mx.gob.imss.ctirss.clasificador.service.FraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;















@Service
public class FraccionServicePOJOImpl
  implements FraccionService
{
  private FraccionRepository fraccionRepository;
  
  public List<Fraccion> obtenerFraccionesActivasPorGrupo(int cveGrupo, String sSearch, int iDisplayLength, int iDisplayStart) {
    return null;
  }



  
  @Autowired
  public void setFraccionRepository(FraccionRepository fraccionRepository) {
    this.fraccionRepository = fraccionRepository;
  }




  
  public Fraccion obtenerFraccionPorClave(String desFraccion) {
    return this.fraccionRepository.obtenerFraccionPorClave(desFraccion);
  }
}

