package mx.gob.imss.cit.cda.core.events;

import java.io.Serializable;
import java.util.UUID;

public class CreatedEvent<O> implements Serializable {

        protected boolean entityCreated = true;
        private final UUID key;
        private final O data;
        private String mensajeExcepcion;
        private String mensajeNegocio;

        public static CreatedEvent notFound(UUID key) {
                CreatedEvent event = new CreatedEvent(key);
                return event;
        }
        
        public static CreatedEvent error(UUID key,String mensajeExcepcion, String mensajeNegocio) {
        	CreatedEvent event = new CreatedEvent(key,mensajeExcepcion,mensajeNegocio);
    	    return event;
      }
        
        public static CreatedEvent error(UUID key,String mensaje) {
        	CreatedEvent event = new CreatedEvent(key,mensaje);
    	    return event;
      }

        private CreatedEvent(UUID key) {
                this.key = key;
                this.data = null;
                this.entityCreated = false;
        }
        
        private CreatedEvent(UUID key,String mensajeExcepcion, String mensajeNegocio) {
    	    this.key = key;
    	    this.data = null;
    	    this.entityCreated = false;
    	    this.mensajeExcepcion=mensajeExcepcion;
    	    this.mensajeNegocio=mensajeNegocio;
    	  }

        public CreatedEvent(UUID key, O data) {
                this.key = key;
                this.data = data;
        }

        public O getData() {
                return data;
        }

        public UUID getKey() {
                return key;
        }

        /**
         * @return the entityCreated
         */
        public boolean isEntityCreated() {
                return entityCreated;
        }

		public String getMensajeExcepcion() {
			return mensajeExcepcion;
		}

		public String getMensajeNegocio() {
			return mensajeNegocio;
		}
        
        
}
