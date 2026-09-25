/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author antonio
 * @param <O>
 */
public class ReadEvent<O> implements Serializable {

  protected boolean entityFound = true;
  private final UUID key;
  private final O data;
  private String mensajeExcepcion;
  private String mensajeNegocio;

  public static ReadEvent notFound(UUID key) {
    ReadEvent event = new ReadEvent(key);
    return event;
  }
  
  public static ReadEvent error(UUID key,String mensajeExcepcion, String mensajeNegocio) {
	    ReadEvent event = new ReadEvent(key,mensajeExcepcion,mensajeNegocio);
	    return event;
  }

  private ReadEvent(UUID key) {
    this.key = key;
    this.data = null;
    this.entityFound = false;
  }
  
  private ReadEvent(UUID key,String mensajeExcepcion, String mensajeNegocio) {
	    this.key = key;
	    this.data = null;
	    this.entityFound = false;
	    this.mensajeExcepcion=mensajeExcepcion;
	    this.mensajeNegocio=mensajeNegocio;
	  }

  public ReadEvent(UUID key, O data) {
    this.key = key;
    this.data = data;
  }

  public boolean isEntityFound() {
    return entityFound;
  }

  public O getData() {
    return data;
  }

  public UUID getKey() {
    return key;
  }

public String getMensajeExcepcion() {
	return mensajeExcepcion;
}

public String getMensajeNegocio() {
	return mensajeNegocio;
}
  
  
}
