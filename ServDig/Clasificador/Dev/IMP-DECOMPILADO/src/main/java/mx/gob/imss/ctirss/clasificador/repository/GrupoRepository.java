package mx.gob.imss.ctirss.clasificador.repository;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Grupo;

public interface GrupoRepository {
  List<Grupo> cargarGruposPorDivision(int paramInt);
}


