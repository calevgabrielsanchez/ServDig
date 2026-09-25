package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;

public class UpdateEvent<I> implements Serializable{
    private final UUID key;
    private final I data;
    private UserProfile userProfile;
    
    public UpdateEvent(UUID key){
        this.key = key;
        this.data = null;
    }
    
    public UpdateEvent(UUID key, I data){
        this.key= key;
        this.data = data;
    }
    
    public UpdateEvent(UUID key, I data, UserProfile userProfile ){
        this.key= key;
        this.data = data;
        this.userProfile = userProfile;
    }
    
    public I getData(){
      return data;
    }
    
    public UUID getKey(){
      return key;
    }

  /**
   * @return the userProfile
   */
  public UserProfile getUserProfile() {
    return userProfile;
  }

  /**
   * @param userProfile the userProfile to set
   */
  public void setUserProfile(UserProfile userProfile) {
    this.userProfile = userProfile;
  }
}
