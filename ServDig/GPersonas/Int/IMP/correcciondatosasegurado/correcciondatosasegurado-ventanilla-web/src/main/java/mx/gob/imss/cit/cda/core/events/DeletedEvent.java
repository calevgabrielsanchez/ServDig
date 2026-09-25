package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;

public class DeletedEvent<O> implements Serializable {

  protected boolean entityDeleted = true;
  private final UUID key;
  private final O data;

  public static DeletedEvent notFound(UUID key) {
    DeletedEvent event = new DeletedEvent(key);
    return event;
  }

  private DeletedEvent(UUID key) {
    this.key = key;
    this.data = null;
    this.entityDeleted = false;
  }

  public DeletedEvent(UUID key, O data) {
    this.key = key;
    this.data = data;
  }

  public O getData() {
    return data;
  }

  public UUID getKey() {
    return key;
  }

  public boolean isEntityDeleted() {
    return entityDeleted;
  }
}
