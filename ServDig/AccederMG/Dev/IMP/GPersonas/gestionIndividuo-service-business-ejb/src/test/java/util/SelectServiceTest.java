package util;

import mx.gob.imss.ctirss.delta.framework.exceptions.TechnicalPersistenceException;

import org.junit.Test;

public class SelectServiceTest {

    @Test
    public void testGetOptions() throws ClassNotFoundException, TechnicalPersistenceException {
        //final ISelectService selectService = EjbLocator.getSelectService();

        /*
        @SuppressWarnings("unchecked")
        final Class<? extends AbstractEntity> claseEstado = (Class<? extends AbstractEntity>) Class.forName(DgCatEstado.class.getName());

        final List<SelectBean> listaEstados = selectService.getOptions(claseEstado);

        for (SelectBean select : listaEstados) {
            System.out.println(select.getId() + " - " + select.getDescripcion());
        }

        assertEquals("Falto algun estado!! --><--", 34, listaEstados.size());
        // Hay 2 extras: relacionados con extranjeros.
		*/
    }

}
