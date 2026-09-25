package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;
import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;

public interface FraccionService {
  List<Fraccion> obtenerFraccionesActivasPorGrupo(int paramInt1, String paramString, int paramInt2, int paramInt3);
  
  Fraccion obtenerFraccionPorClave(String paramString);
}
