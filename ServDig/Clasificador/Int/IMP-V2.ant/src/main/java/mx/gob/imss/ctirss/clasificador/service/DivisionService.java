package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Division;

public interface DivisionService {
  List<Division> cargarDivisionesActivas();
  
  List<Division> cargarDivisionesInactivas();
  
  List<Division> cargarDivisionesVigentes();
}
