package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.Comparator;

public class OpcionComparator implements Comparator<Opcion> {

	@Override
	public int compare(Opcion o1, Opcion o2) {
		return Integer.valueOf(o1.getClave()).compareTo(Integer.valueOf(o2.getClave()));
	}

}
