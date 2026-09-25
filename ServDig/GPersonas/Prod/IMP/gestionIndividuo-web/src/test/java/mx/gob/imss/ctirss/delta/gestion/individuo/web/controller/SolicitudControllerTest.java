package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.EstadoTramite;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.web.servlet.ModelAndView;

public class SolicitudControllerTest {

    private static final Logger LOG;
    private transient BindingResult result;
    private transient Solicitud oForm;
    private static final String COMMAND_NAME = "solicitud";

    static {
        LOG = LoggerFactory.getLogger(SolicitudControllerTest.class);
    }

    @Before
    public void setUp() {
        result = new DirectFieldBindingResult(oForm, COMMAND_NAME);
    }

    @Test
    public void procesarTramitesTest() throws SolicitudNoEncontradaException {
        final SolicitudController controller = new SolicitudController();
        ReflectionTestUtils.setField(controller, "solicitudPersonaBusiness", prepararInvocacionGetSolicitud(ID_SOLICITUD));
        // final String viewName =
        try {
			controller.procesarTramites(prepararSolicitudForm(ID_SOLICITUD), result, new ExtendedModelMap());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // assertEquals("Vista incorrecta", "resultadoBusquedaSolicitud",
        // viewName);
    }

    private static final Long ID_SOLICITUD = 1L;

    @Test
    public void buscarCasoSolicitudAtendidaTest() throws SolicitudNoEncontradaException {
        final SolicitudController controller = new SolicitudController();
        final SolicitudPersonaBusinessRemote solicitudPersonaBusiness = prepararInvocacionGetSolicitudAtendida(ID_SOLICITUD);
        ReflectionTestUtils.setField(controller, "solicitudPersonaBusiness", solicitudPersonaBusiness);
        final ModelAndView mav = controller.buscar(ID_SOLICITUD.toString());
        assertEquals("Vista incorrecta", "resultadoBusquedaSolicitud", mav.getViewName());
        assertEquals("Mensaje de status incorrecto", "Todos los tr\u00E1mites de la solicitud ya fueron procesados", mav.getModelMap().get("statusMsg"));
    }

    @Test
    public void buscarCasoSolicitudRegistradaTest() throws SolicitudNoEncontradaException {
        final SolicitudController controller = new SolicitudController();
        final SolicitudPersonaBusinessRemote solicitudPersonaBusiness = prepararInvocacionGetSolicitudRegistrada(ID_SOLICITUD);
        ReflectionTestUtils.setField(controller, "solicitudPersonaBusiness", solicitudPersonaBusiness);
        final ModelAndView mav = controller.buscar(ID_SOLICITUD.toString());
        assertEquals("Vista incorrecta", "resultadoBusquedaSolicitud", mav.getViewName());
        assertEquals("Mensaje de status incorrecto", "La solicitud no est\u00E1 lista para procesarse", mav.getModelMap().get("statusMsg"));
    }

    @Test
    public void buscarCasoSolicitudCanceladaTest() throws SolicitudNoEncontradaException {
        final SolicitudController controller = new SolicitudController();
        final SolicitudPersonaBusinessRemote solicitudPersonaBusiness = prepararInvocacionGetSolicitudCancelada(ID_SOLICITUD);
        ReflectionTestUtils.setField(controller, "solicitudPersonaBusiness", solicitudPersonaBusiness);
        final ModelAndView mav = controller.buscar(ID_SOLICITUD.toString());
        assertEquals("Vista incorrecta", "resultadoBusquedaSolicitud", mav.getViewName());
        assertEquals("Mensaje de status incorrecto", "La solicitud fue cancelada", mav.getModelMap().get("statusMsg"));
    }

    @Test
    public void buscarCasoSolicitudValidadaTest() throws SolicitudNoEncontradaException {
        final SolicitudController controller = new SolicitudController();
        final SolicitudPersonaBusinessRemote solicitudPersonaBusiness = prepararInvocacionGetSolicitudValidada(ID_SOLICITUD);
        ReflectionTestUtils.setField(controller, "solicitudPersonaBusiness", solicitudPersonaBusiness);
        final ModelAndView mav = controller.buscar(ID_SOLICITUD.toString());
        assertEquals("Vista incorrecta", "resultadoBusquedaSolicitud", mav.getViewName());
        assertEquals("Mensaje de status incorrecto", "", mav.getModelMap().get("statusMsg"));
        final Solicitud solicitudModel = (Solicitud) mav.getModelMap().get("solicitud");
        assertNotNull("Command Object Incorrect", solicitudModel);
        assertEquals("Command Object Incorrect", 1, solicitudModel.getTramite().size());
    }

    @Test
    public void buscarCasoSolicitudNoEncontradaTest() throws SolicitudNoEncontradaException {
        final SolicitudController controller = new SolicitudController();
        final SolicitudPersonaBusinessRemote solicitudPersonaBusiness = prepararInvocacionGetSolicitudNoEncontrada(ID_SOLICITUD);
        ReflectionTestUtils.setField(controller, "solicitudPersonaBusiness", solicitudPersonaBusiness);
        final ModelAndView mav = controller.buscar(ID_SOLICITUD.toString());
        assertEquals("Vista incorrecta", "resultadoBusquedaSolicitud", mav.getViewName());
        LOG.trace("statusMsg: " + mav.getModelMap().get("statusMsg"));
        // assertEquals("Mensaje de status incorrecto", "",
        // mav.getModelMap().get("statusMsg"));
        final Solicitud solicitudModel = (Solicitud) mav.getModelMap().get("solicitud");
        assertNull("Command Object Incorrect", solicitudModel);
    }

    private SolicitudPersonaBusinessRemote prepararInvocacionGetSolicitudNoEncontrada(Long idSolicitud) throws SolicitudNoEncontradaException {
        final SolicitudPersonaBusinessRemote solicitudPersonaBusinessRemote = EasyMock.createMock(SolicitudPersonaBusinessRemote.class);
        expect(solicitudPersonaBusinessRemote.getSolicitud(idSolicitud)).andReturn(null);
        replay(solicitudPersonaBusinessRemote);
        return solicitudPersonaBusinessRemote;
    }

    private Solicitud prepararSolicitudRegistradaForm(final Long idSolicitud) {
        final Solicitud solicitudForm = new Solicitud();
        solicitudForm.setIdSolicitud(idSolicitud);
        return solicitudForm;
    }

    private SolicitudPersonaBusinessRemote prepararInvocacionGetSolicitudRegistrada(final Long idSolicitud) throws SolicitudNoEncontradaException {
        final SolicitudPersonaBusinessRemote solicitudPersonaBusinessRemote = EasyMock.createMock(SolicitudPersonaBusinessRemote.class);
        final Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(idSolicitud);
        solicitud.setIdEstadoSolicitud(EstadoSolicitud.REGISTRADA);
        expect(solicitudPersonaBusinessRemote.getSolicitud(idSolicitud)).andReturn(solicitud);
        replay(solicitudPersonaBusinessRemote);
        return solicitudPersonaBusinessRemote;
    }

    private SolicitudPersonaBusinessRemote prepararInvocacionGetSolicitudAtendida(final Long idSolicitud) throws SolicitudNoEncontradaException {
        final SolicitudPersonaBusinessRemote solicitudPersonaBusinessRemote = EasyMock.createMock(SolicitudPersonaBusinessRemote.class);
        final Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(idSolicitud);
        solicitud.setIdEstadoSolicitud(EstadoSolicitud.ATENDIDA);
        expect(solicitudPersonaBusinessRemote.getSolicitud(idSolicitud)).andReturn(solicitud);
        replay(solicitudPersonaBusinessRemote);
        return solicitudPersonaBusinessRemote;
    }

    private SolicitudPersonaBusinessRemote prepararInvocacionGetSolicitudCancelada(final Long idSolicitud) throws SolicitudNoEncontradaException {
        final SolicitudPersonaBusinessRemote solicitudPersonaBusinessRemote = EasyMock.createMock(SolicitudPersonaBusinessRemote.class);
        final Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(idSolicitud);
        solicitud.setIdEstadoSolicitud(EstadoSolicitud.CANCELADA);
        expect(solicitudPersonaBusinessRemote.getSolicitud(idSolicitud)).andReturn(solicitud);
        replay(solicitudPersonaBusinessRemote);
        return solicitudPersonaBusinessRemote;
    }

    private SolicitudPersonaBusinessRemote prepararInvocacionGetSolicitudValidada(final Long idSolicitud) throws SolicitudNoEncontradaException {
        final SolicitudPersonaBusinessRemote solicitudPersonaBusinessRemote = EasyMock.createMock(SolicitudPersonaBusinessRemote.class);
        final Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(idSolicitud);
        solicitud.setIdEstadoSolicitud(EstadoSolicitud.EN_PROCESO);

        /*** Este registro se mostrara **/
        final Fisica persFisica0 = new Fisica();
        persFisica0.setNombre("Juan Alonso");
        persFisica0.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(Utilerias.convertir(CalificacionPersona.NO_VALIDADO));

        final Tramite tramite0 = new Tramite();
        tramite0.setPersonaFisica(persFisica0);
        tramite0.setIdEstadoTramite(EstadoTramite.REGISTRADO);
        solicitud.getTramite().add(tramite0);

        /*** Este registro no se mostrara **/

        final Tramite tramite1 = new Tramite();
        tramite1.setIdEstadoTramite(EstadoTramite.REGISTRADO);
        tramite1.setPersonaFisica(new Fisica());
        solicitud.getTramite().add(tramite1);

        /*************************************/

        expect(solicitudPersonaBusinessRemote.getSolicitud(idSolicitud)).andReturn(solicitud);
        replay(solicitudPersonaBusinessRemote);
        return solicitudPersonaBusinessRemote;
    }

    private SolicitudPersonaBusinessRemote prepararInvocacionGetSolicitud(final Long idSolicitud) throws SolicitudNoEncontradaException {
        final SolicitudPersonaBusinessRemote solicitudPersonaBusinessRemote = EasyMock.createMock(SolicitudPersonaBusinessRemote.class);
        final Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(idSolicitud);
        final Fisica persFisica = new Fisica();
        persFisica.setNombre("Juan Alonso");
        // persFisica.setIdEstatus();
        final Tramite tramite = new Tramite();
        tramite.setIdEstadoTramite(EstadoTramite.REGISTRADO);
        tramite.setPersonaFisica(persFisica);
        solicitud.getTramite().add(tramite);
        expect(solicitudPersonaBusinessRemote.getSolicitud(idSolicitud)).andReturn(solicitud);
        try {
			expect(solicitudPersonaBusinessRemote.modificar(solicitud)).andReturn(solicitud);
		} catch (SolicitudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			expect(solicitudPersonaBusinessRemote.procesarSolicitudNueva(solicitud)).andReturn(solicitud);
		} catch (SolicitudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        replay(solicitudPersonaBusinessRemote);
        return solicitudPersonaBusinessRemote;
    }

    private Solicitud prepararSolicitudForm(final Long idSolicitud) {
        final Solicitud solicitudForm = new Solicitud();
        // XXX Note that the actual method don´t supports calls with idSolicitud
        // in null, this because some methods expects long, not Long:
        solicitudForm.setIdSolicitud(idSolicitud);
        final Tramite tramite = new Tramite();
        tramite.setObservacion("Observ");
        // Prepares contradictory values in next 2 lines:
        // tramite.setTramitadorValidaDatos("true");
        // tramite.setIdRazonResultado(2L);
        tramite.setTramitadorValidaDatos("false");
        tramite.setIdRazonResultado(2L);
        final Fisica personaFisica = new Fisica();
        personaFisica.getLugarNacimiento().setClave("2");
        tramite.setPersonaFisica(personaFisica);
        solicitudForm.getTramite().add(tramite);
        return solicitudForm;
    }

    // Esta version usaba enum, pero no fue viable:
    // @Test(expected = IllegalArgumentException.class)
    // public void comboTramiteTest() {
    // final RazonRechazoTramite opcion =
    // RazonRechazoTramite.valueOf("SeleccioneOpcion");
    // LOG.debug(opcion.toString());
    // opcion.ordinal();
    //
    // final List<SelectBean> combo = new ArrayList<SelectBean>();
    // for (RazonRechazoTramite razonRechazo : RazonRechazoTramite.values()) {
    // if (!RazonRechazoTramite.Unused.equals(razonRechazo)) {
    // combo.add(new SelectBean(razonRechazo.ordinal(),
    // razonRechazo.toString()));
    // }
    // }
    // LOG.debug("combo: " + combo);
    //
    // RazonRechazoTramite.valueOf("NONE"); // throws IllegalArgumentException
    // }

}
