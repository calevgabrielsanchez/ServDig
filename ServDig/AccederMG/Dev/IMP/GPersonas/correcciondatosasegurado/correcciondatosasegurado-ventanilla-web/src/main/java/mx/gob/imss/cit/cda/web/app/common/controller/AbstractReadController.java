/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.common.controller;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;

import java.io.Serializable;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author antonio
 * @param <I>
 * @param <O>
 */
public abstract class AbstractReadController<I extends Serializable,O extends Serializable> extends AbstractController{
  
  public abstract ReadHelper<I,O> getHelper();
   
  
  
  public ResponseEntity<O> load(I input, HttpServletRequest request){

    ReadEvent<O> readEvent = 
      getHelper().requestEvent(
        new RequestReadEvent<I>(UUID.randomUUID(), input, getUserProfile(request))
      );
    
    if( readEvent.isEntityFound() ){      
      return new ResponseEntity<O>(
              readEvent.getData(),HttpStatus.OK);
    }

    return new ResponseEntity<O>(HttpStatus.NOT_FOUND);
  }
}
