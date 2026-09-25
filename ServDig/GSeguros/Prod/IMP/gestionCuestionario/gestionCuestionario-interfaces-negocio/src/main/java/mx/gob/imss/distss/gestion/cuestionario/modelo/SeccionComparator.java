package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.Comparator;

public class SeccionComparator implements Comparator<Seccion> {

	@Override
	public int compare(Seccion o1, Seccion o2) {
		return Integer.valueOf(o1.getClave()).compareTo(
				Integer.valueOf(o2.getClave()));
	}

}
