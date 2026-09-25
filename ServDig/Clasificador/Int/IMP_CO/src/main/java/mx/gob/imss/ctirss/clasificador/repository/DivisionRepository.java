package mx.gob.imss.ctirss.clasificador.repository;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Division;

public interface DivisionRepository {
  List<Division> cargarDivisionesActivas();
  
  List<Division> cargarDivisionesInactivas();
  
  List<Division> cargarDivisionesVigentes();
}

