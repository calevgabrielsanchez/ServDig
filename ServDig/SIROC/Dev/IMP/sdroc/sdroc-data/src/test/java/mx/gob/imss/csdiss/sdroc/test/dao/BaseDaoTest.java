package mx.gob.imss.csdiss.sdroc.test.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Esta clase establece por medio de anotaciones la configuracion para ejecutar los tests.
 * 
 * - Primero se indica que JUnit sera el encargado de ejecutar las mismas y evaluar los resultados.
 * - Despues el archivo que contiene la configuracion de spring para iniciar el contendor y sus beans.
 * - Se activa el profile con el que se debe iniciar el contenedor.
 * 
 * @author Brian Hernandez Garcia
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/mx/gob/imss/csdiss/sdroc/data/config/hibernate-config.xml")
@ActiveProfiles("testing")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public abstract class BaseDaoTest {

}
