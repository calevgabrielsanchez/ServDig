/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.support.model;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseModel implements Serializable{
  
}
