/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.common.controller;

import mx.gob.imss.cit.cda.core.events.DeleteEvent;
import mx.gob.imss.cit.cda.core.events.DeletedEvent;
import mx.gob.imss.cit.cda.core.helper.DeleteHelper;

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
public abstract class AbstractDeleteController<I extends Serializable,O extends Serializable> extends AbstractController{
  
  public abstract DeleteHelper<I,O> getHelper();
  
  @ResponseBody
  public ResponseEntity<O> load(@RequestBody I input){

    DeletedEvent<O> deletedEvent = 
      getHelper().requestEvent(
        new DeleteEvent<I>(UUID.randomUUID(), input)
      );
    
    if( deletedEvent.isEntityDeleted()){      
      return new ResponseEntity<O>(
              deletedEvent.getData(),HttpStatus.OK);
    }

    return new ResponseEntity<O>(HttpStatus.NOT_FOUND);
  }
}
