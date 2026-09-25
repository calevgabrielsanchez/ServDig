package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Grupo;
import mx.gob.imss.ctirss.clasificador.repository.GrupoRepository;
import mx.gob.imss.ctirss.clasificador.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrupoServicePOJOImpl
  implements GrupoService
{
  private GrupoRepository grupoRepository;
  
  public List<Grupo> cargarGruposPorDivision(int cveDivision) {
    return this.grupoRepository.cargarGruposPorDivision(cveDivision);
  }



  @Autowired
  public void setGrupoRepository(GrupoRepository grupoRepository) {
    this.grupoRepository = grupoRepository;
  }
}

