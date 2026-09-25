package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;

public class UpdatedEvent<O> implements Serializable {

  protected boolean entityUpdated = true;
  private final UUID key;
  private final O data;

  public static UpdatedEvent notUpdated(UUID key) {
    UpdatedEvent event = new UpdatedEvent(key);
    return event;
  }

  private UpdatedEvent(UUID key) {
    this.key = key;
    this.data = null;
    this.entityUpdated = false;
  }

  public UpdatedEvent(UUID key, O data) {
    this.key = key;
    this.data = data;
  }

  public O getData() {
    return data;
  }

  public UUID getKey() {
    return key;
  }

  public boolean isEntityUpdated() {
    return entityUpdated;
  }
}
