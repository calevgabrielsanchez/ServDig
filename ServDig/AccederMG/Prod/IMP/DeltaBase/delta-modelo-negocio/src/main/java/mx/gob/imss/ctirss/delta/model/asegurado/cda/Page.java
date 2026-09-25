package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author antonio
 * @param <T> Modelo de la lista
 */
public class Page<T extends BaseModel> extends BaseModel {

  /**
   * Tamaño predeterminado de pagination
   */
  public static final int DEFAULT_PAGE_SIZE = 10;
  private @Getter @Setter List<T> data;
  private @Getter @Setter long currentPage;
  private @Getter @Setter long pageSize;
  private @Getter @Setter long totalOfRecords;
}
