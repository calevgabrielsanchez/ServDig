/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;

/**
 *
 * @author antonio
 * @param <I>
 */
public class RequestReadEvent<I> implements Serializable{
    private final UUID key;
    private final I data;
    private final UserProfile userProfile;
    
    public RequestReadEvent(UUID key){
        this.key = key;
        this.data = null;
        this.userProfile = null;
    }
    
    public RequestReadEvent(UUID key, I data){
        this.key= key;
        this.data = data;
        this.userProfile = null;
    }
    
    public RequestReadEvent(UUID key, I data, UserProfile userProfile){
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
    
    
}
