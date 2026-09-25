package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import java.util.List;

import javax.ejb.Local;

@Local
public interface DomiciliosServiceUtilityLocal {

    public List<String> armarRegistros(List<Object[]> tuples);
    
    public List<String> armarNombrePatron(List<Object[]> tuples);
}
