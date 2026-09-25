package mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;

public class EstudiantesSimeValidationEventHandler extends
		ValidationEventCollector {

	@Override
	public boolean handleEvent(ValidationEvent event) {
		
		if (event.getSeverity() == ValidationEvent.FATAL_ERROR) {
			super.handleEvent(event);
		}
		
		return true;
	}
}
