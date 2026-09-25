/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.imss.cit.cda.core.helper;

import mx.gob.imss.cit.cda.core.events.DeleteEvent;
import mx.gob.imss.cit.cda.core.events.DeletedEvent;


/**
 *
 * @author antonio
 * @param <I>
 * @param <O>
 */
public interface DeleteHelper<I,O> {
    DeletedEvent<O> requestEvent( 
            DeleteEvent<I> requestDeleteEvent );
}
