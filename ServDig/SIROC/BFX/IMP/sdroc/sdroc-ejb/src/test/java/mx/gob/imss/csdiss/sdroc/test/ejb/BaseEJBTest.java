package mx.gob.imss.csdiss.sdroc.test.ejb;


import org.junit.runner.RunWith;
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
 * - Se indica el nombre del bean que se encargara de manejar la transaccion. Ademas para el caso de los tests, por definicion
 *   siempre debemos regresar al estado de los datos en que iniciamos, asi que al terminar cada test se hara rollback.
 * - Por ultimo indicamos con @Transactional que todas los test, a menos que indiquemos lo contrario, iniciaran una transaccion.
 * 
 * @author Brian Hernandez Garcia
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/mx/gob/imss/csdiss/sdroc/ejb/config/ejbcore-config.xml")
//@ContextConfiguration("classpath:*beanRefContext.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public abstract class BaseEJBTest {

}
