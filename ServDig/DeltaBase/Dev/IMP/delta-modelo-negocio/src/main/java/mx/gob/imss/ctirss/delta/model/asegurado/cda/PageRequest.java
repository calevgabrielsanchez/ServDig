package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author antonio
 * @param <T> Modelo de la lista
 */
public class PageRequest<T extends BaseModel> extends BaseModel {
  private @Getter @Setter T model;  
  private @Getter @Setter long page;
}
