/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.common.controller;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;

import java.io.Serializable;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
public abstract class AbstractUpdateController<I extends Serializable, O extends Serializable>
        extends AbstractController {

    public abstract UpdateHelper<I, O> getHelper();

    @ResponseBody
    public ResponseEntity<O> update(@RequestBody I input,
            HttpServletRequest request) {

        // FIXME
        UpdatedEvent<O> updatedEvent = getHelper().requestEvent(
                new UpdateEvent<I>(UUID.randomUUID(), input,
                        getUserProfile(request)));

        if (updatedEvent.isEntityUpdated()) {
            return new ResponseEntity<O>(updatedEvent.getData(), HttpStatus.OK);
        }

        return new ResponseEntity<O>(HttpStatus.NOT_FOUND);
    }
}
