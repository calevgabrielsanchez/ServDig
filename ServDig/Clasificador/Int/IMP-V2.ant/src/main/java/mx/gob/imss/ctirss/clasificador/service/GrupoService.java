package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Grupo;

public interface GrupoService {
  List<Grupo> cargarGruposPorDivision(int paramInt);
}

