package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import mx.gob.imss.ctirss.delta.gestion.individuo.web.utils.WrapperSessionDatosPersonaFisicaSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistroPersonaFisicaCapturaControllerTest {

    // TODO hay que mockear al wrapper, pero eso es mas tardado que hacer la
    // prueba online...
    @Test
    public void registrarPersonaFisica() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        final RegistroPersonaFisicaCapturaController controller = new RegistroPersonaFisicaCapturaController();

        final Fisica personaFisica = new Fisica();
        personaFisica.setNombre("a");
        personaFisica.setPrimerApellido("pa");
        personaFisica.setSegundoApellido("sa");
        personaFisica.getSexo().setIdSexo(1);
        personaFisica.setFechaNacimiento(new Date());
        personaFisica.getLugarNacimiento().setClave("2");

        final Fisica personaFisica1 = (Fisica) BeanUtils.cloneBean(personaFisica);

        final WrapperSessionDatosPersonaFisicaSalidaPaginador<Fisica> wrapperDataPager = new WrapperSessionDatosPersonaFisicaSalidaPaginador<Fisica>();
        ReflectionTestUtils.setField(controller, "wrapperDataPager", wrapperDataPager);
//        ISelectService selectService = prepararInvocacionXyz(personaFisica.getIdSexo(), personaFisica.getIdEntidadNacimiento());
        ReflectionTestUtils.setField(controller, "selectService", null);
        controller.registrarPersonaFisica(personaFisica, null);
        controller.registrarPersonaFisica(personaFisica1, new MockHttpServletResponse()); // en esta es donde procesa error de persona repetida.
    }

//    private ISelectService prepararInvocacionXyz(final Integer idSexo, final Integer idEntidadNacimiento) {
//        final ISelectService selectService = EasyMock.createMock(ISelectService.class);
//        
//        //expect(selectService.getOption(DicSexo.class, idSexo)).andReturn(new SelectBean(idSexo, (idSexo == 1) ? "HOMBRE" : "MUJER")).times(2);
//        
//        //expect(selectService.getOption(DgCatEstado.class, idEntidadNacimiento)).andReturn(new SelectBean(idEntidadNacimiento, "MexicoSiempre")).times(2);
//        replay(selectService);
//        return selectService;
//    }

}
