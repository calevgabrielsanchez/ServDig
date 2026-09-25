package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;

public class DeleteEvent<I> implements Serializable{
    private final UUID key;
    private final I data;
    
    public DeleteEvent(UUID key){
        this.key = key;
        this.data = null;
    }
    
    public DeleteEvent(UUID key, I data){
        this.key= key;
        this.data = data;
    }
    
    public I getData(){
      return data;
    }
    
    public UUID getKey(){
      return key;
    }
}
