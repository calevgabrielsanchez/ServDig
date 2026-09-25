package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;

@Stateless(mappedName = "domiciliosServiceUtility")
public class DomiciliosServiceUtility extends AbstractServiceUtility implements DomiciliosServiceUtilityLocal {

    @Override
    public List<String> armarRegistros(List<Object[]> tuples) {

        List<String> nombres = new ArrayList<String>();
        if (null != tuples && !tuples.isEmpty()) {
            Object[] tuple;
            for (int index = 0; index < tuples.size(); index++) {
                tuple = tuples.get(index);
                StringBuilder str = new StringBuilder();
                int count = 0;
                for (Object ob : tuple) {
                    str = anadecadena(count, str, ob);
                    count++;
                }
                nombres.add(str.toString());
            }
        }
        return nombres;
    }
    
    
	@Override
	public List<String> armarNombrePatron(List<Object[]> tuples) {

		List<String> nombres = new ArrayList<String>();
		StringBuilder str = new StringBuilder();
		if (null != tuples && !tuples.isEmpty()) {
			for (Object[] tuple : tuples) {
				if(tuple.length>2) {
					str.append(tuple[0]).append(" ").append(tuple[1]).append(" ").append(tuple[2]);
				}else {
					str.append(tuple[1]);
				}
				nombres.add(str.toString());
			}
		}
		return nombres;
	}

    private StringBuilder anadecadena(int contador, StringBuilder cadena, Object ob) {

        if (contador == 1) {
            cadena.append("-");
        }
        cadena.append(ob).append(" ");

        return cadena;

    }

}
