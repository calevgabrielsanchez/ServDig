package mx.gob.imss.ctirss.clasificador.repository;

import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableReply;

public interface FraccionRepository {
  AbstractDataTableReply obtenerFraccionesActivasPorGrupo(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, boolean paramBoolean);
  
  AbstractDataTableReply obtenerFraccionesActivasPorPalabraClaveAnterior(int paramInt1, String paramString, int paramInt2, int paramInt3);
  
  AbstractDataTableReply obtenerFraccionesActivasPorNumeroAnterior(int paramInt1, String paramString, int paramInt2, int paramInt3);
  
  AbstractDataTableReply obtenerFraccionesInactivasPorPalabraClave(String paramString, int paramInt1, int paramInt2);
  
  Fraccion obtenerFraccionPorClave(String paramString);
  
  AbstractDataTableReply obtenerFraccionesActivasPorPalabra(int paramInt1, String paramString, int paramInt2, int paramInt3);
  
  AbstractDataTableReply obtenerFraccionesActivasPorNumero(int paramInt1, String paramString, int paramInt2, int paramInt3);
  
  AbstractDataTableReply obtenerFraccionesInactivasPorNumeroAnterior(String paramString, int paramInt1, int paramInt2);
}

