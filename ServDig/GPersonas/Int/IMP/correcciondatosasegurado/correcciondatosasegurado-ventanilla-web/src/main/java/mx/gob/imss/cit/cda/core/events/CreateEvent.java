package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;

public class CreateEvent<I> implements Serializable{
    private final UUID key;
    private final I data;
    private UserProfile userProfile;
    
    public CreateEvent(UUID key){
        this.key = key;
        this.data = null;
    }
    
    public CreateEvent(UUID key, I data){
        this.key= key;
        this.data = data;
    }
    
    public CreateEvent(UUID key, I data, UserProfile userProfile ){
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

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
    
    
}