/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.common.controller;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonio
 * @param <I>
 * @param <O>
 */
public abstract class AbstractCreateController<I extends Serializable,O extends Serializable> extends AbstractController{
  
  public abstract CreateHelper<I,O> getHelper();
  
  @ResponseBody
  public ResponseEntity<O> load(@RequestBody I input){

    CreatedEvent<O> createdEvent = 
      getHelper().requestEvent(
        new CreateEvent<I>(UUID.randomUUID(), input)
      );
    
    if( createdEvent.isEntityCreated() ){      
      return new ResponseEntity<O>(
              createdEvent.getData(),HttpStatus.OK);
    }

    return new ResponseEntity<O>(HttpStatus.NOT_FOUND);
  }
}
