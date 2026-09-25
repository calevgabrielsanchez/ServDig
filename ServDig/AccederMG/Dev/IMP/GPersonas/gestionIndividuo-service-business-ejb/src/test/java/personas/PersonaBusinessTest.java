package personas;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.gestionpersonas.EJBLocator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaGlobalBusinessRemote;

import org.junit.Test;

import test.EjbLocator;


public class PersonaBusinessTest {

	@Test
	public void testisSocioisPersonaAutorizada() {
		Long idPersona = 84499092L;
		Boolean isPersonaAut = false;
		Boolean isSocio = false;
		
		isPersonaAut = EjbLocator.getPersonaFisicaBusiness().isPersonaAutorizada(idPersona);
		
		System.out.println("La persona " + idPersona + " es persona autorizada ? " + isPersonaAut);
		
		isSocio = EjbLocator.getPersonaFisicaBusiness().isSocio(idPersona);
		
		System.out.println("La persona " + idPersona + " es socio ? " + isSocio);
		
		idPersona = 64089202L;
		
		isPersonaAut = EjbLocator.getPersonaFisicaBusiness().isPersonaAutorizada(idPersona);
		
		System.out.println("La persona " + idPersona + " es persona autorizada ? " + isPersonaAut);
		
		isSocio = EjbLocator.getPersonaFisicaBusiness().isSocio(idPersona);
		
		System.out.println("La persona " + idPersona + " es socio ? " + isSocio);
		
		idPersona = 49338413L;
		
		isPersonaAut = EjbLocator.getPersonaFisicaBusiness().isPersonaAutorizada(idPersona);
		
		System.out.println("La persona " + idPersona + " es persona autorizada ? " + isPersonaAut);
		
		isSocio = EjbLocator.getPersonaFisicaBusiness().isSocio(idPersona);
		
		System.out.println("La persona " + idPersona + " es socio ? " + isSocio);
	}
	
    @Test
    public void testComponenteBusqueda() {
        System.out.println("testComponenteBusqueda. Inicio " + new Date());

        DatosEntradaPaginador<Fisica> paramsPager = new DatosEntradaPaginador<Fisica>();
        Fisica modelo = new Fisica();
        //modelo.setNombre("Josï¿½ Guadalupe");
        modelo.setNombre(" joaquin esteban ");
        //modelo.setIdPersona(21668663L);

        paramsPager.setModelo(modelo);
        paramsPager.setiDisplayStart(0);
        paramsPager.setiDisplayLength(100);

        //EJECUTA EL SERVICIO DE CONSULTA
        DatosSalidaPaginador<Fisica> personaFisicaResultado;
        try {
            personaFisicaResultado = EjbLocator.getPersonaBusiness().getPersonaFisicaFiltro(paramsPager);
            if (personaFisicaResultado != null) {
                List<Fisica> listaPersonaFisica = personaFisicaResultado.getAaData();

                System.out.println("testComponenteBusqueda. La consulta regreso los siguientes registros:\n\n");
                if (listaPersonaFisica != null) {
                    for (Fisica personaFisica : listaPersonaFisica) {
                        System.out.println(personaFisica.getIdPersona() + " - " + personaFisica.getNombre() + " - " + personaFisica.getPrimerApellido() + " - Nss: " +personaFisica.getNss());

                    }
                }
            }

        } catch (NumeroMaximoResultadosSuperadoException e) {
            e.printStackTrace();
        }

        System.out.println("testComponenteBusqueda. Final " + new Date());
    }

    @Test
    public void buscarPersonaFisicaPorCurpEnRenapo() throws ClienteWebserviceRenapoCurpException {
        System.out.println("buscarPersonaFisicaPorDatosBasicosEnImss. Inicio " + new Date());

        System.out.println("Persona recuperada:\n" + EjbLocator.getPersonaBusiness().buscarPersonaFisicaPorCurpEnRenapo("COBP830809MVZRNL02"));

        System.out.println("buscarPersonaFisicaPorDatosBasicosEnImss. Final " + new Date());
    }

    @Test
    public void buscarPersonaFisicaPorDatosBasicosEnImss() {
        System.out.println("buscarPersonaFisicaPorDatosBasicosEnImss. Inicio " + new Date());

        Fisica personaFisica = new Fisica();
        personaFisica.setNombre("CARLOS");
        personaFisica.setPrimerApellido("PEREZ");
        //personaFisica.setSegundoApellido("PEREZ");
        personaFisica.setFechaNacimiento(new Date());
        personaFisica.getLugarNacimiento().setClave("17");
        personaFisica.getSexo().setIdSexo(1);

        System.out.println("Persona recuperada:\n" + EjbLocator.getPersonaBusiness().buscarPersonaFisicaPorDatosBasicosEnImss(personaFisica));
        System.out.println("buscarPersonaFisicaPorDatosBasicosEnImss. Final " + new Date());
    }

    @Test
    public void testBusquedaPersonaFisicaFiltro() {
        System.out.println(new Date());

        //DATOS BASICOS
        Fisica filtroBusqueda = new Fisica();
        filtroBusqueda.setNombre("JORGE");
        //        filtroBusqueda.setNombre("joaquin esteban");
        //filtroBusqueda.setNombre("Josï¿½ Guadalupe");
        filtroBusqueda.setPrimerApellido("DE LA LUZ");
        filtroBusqueda.setSegundoApellido("PRUEBA");
        filtroBusqueda.getSexo().setIdSexo(1);
        filtroBusqueda.setFechaNacimiento(new Date());

        //EJECUTA SERVICIO DE ALTA DE PERSONAS
        System.out.println("Se realizarï¿½ la busqueda de personas fisicas con el siguiente filtro:\n" + filtroBusqueda);
        List<Fisica> personaFisicaResultado = EjbLocator.getPersonaBusiness().buscarPersonaFisicaPorDatosBasicosEnImss(filtroBusqueda);

        if (personaFisicaResultado != null) {
            System.out.println("Se encontro en la base de datos lo siguiente:");
            for (Fisica personaFisica : personaFisicaResultado) {
                System.out.println(personaFisica);
            }
        }

        System.out.println(new Date());

    }

    @Test
    public void buscarPersonaFisicaPorRfcEnSat() throws ClienteWebserviceSatRfcException {
        System.out.println("buscarPersonaFisicaPorRfcEnSat. Inicio " + new Date());

        //EJECUTA SERVICIO DE ALTA DE PERSONAS
        Fisica personaFisicaResultado = EjbLocator.getPersonaBusiness().buscarPersonaFisicaPorRfcEnSat("PODJ7209024R8");

        //EVALUA EL RESULTADO
        if (personaFisicaResultado != null){
            System.out.println("IdPersona: " + personaFisicaResultado.getIdPersona());
            System.out.println("Nombre: " + personaFisicaResultado.getNombre());        	
        }
        
        System.out.println("buscarPersonaFisicaPorRfcEnSat. Final " + new Date());
    }
    
    @Test
    public void altaPersonaFisicaTest() throws DomicilioNoValidoException {
        System.out.println(new Date());

        Fisica personaFisica = prepararDatosBasicos();
        prepararDomicilio(personaFisica);
        prepararMediosContacto(personaFisica);
        prepararDocumentosProbatorios(personaFisica);
        prepararEstadoCalif(personaFisica);

        //EJECUTA SERVICIO DE ALTA DE PERSONAS
        Fisica personaFisicaResultado = EjbLocator.getPersonaBusiness().altaPersonaFisica(personaFisica);

        //EVALUA EL RESULTADO
        System.out.println("IdPersona: " + personaFisicaResultado.getIdPersona());
        assertNotNull("El servicio nunca deberia devolver nulo!", personaFisicaResultado);
        
        System.out.println("Domicilio(s): " + personaFisicaResultado.getDomicilios());
        System.out.println("Medios de Contacto: " + personaFisica.getMediosContacto());
        System.out.println("Documentos Probatorios: " + personaFisica.getDocumentosProbatorios());
        System.out.println("Estado Persona: " + personaFisica.getPersonaEstados());
        System.out.println("Persona Calificaciones: " + personaFisica.getPersonaCalificaciones());
        
        // recuperarMediosContactoDadosAlta(personaFisicaResultado);
        System.out.println(new Date());
    }

    private void prepararDocumentosProbatorios(Fisica personaFisica) {
        // DOCUMENTOS PROBATORIOS
        Nacimiento actaNacimiento = new Nacimiento();
        actaNacimiento.setAnio(1950);
        actaNacimiento.setCrip("4552");
        actaNacimiento.setFechaExpedicion(new Date());
        actaNacimiento.setFechaSuceso(new Date());
        actaNacimiento.setNoActa("No Acta");
        actaNacimiento.setNoFoja("no foja");
        actaNacimiento.setNoJuzgado("no juzga"); // deben ser a lo mas 8 caracteres...
        actaNacimiento.setNoLibro("no libro");
        actaNacimiento.setTomo("tomo");
        Municipio municipio = new Municipio();
        municipio.setClave("114");
        municipio.setNombre("PUEBLA");
        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave("21");
        entidadFederativa.setNombre("PUEBLA");
        municipio.setEntidadFederativa(entidadFederativa);
        actaNacimiento.setMunicipio(municipio);

        personaFisica.setActaNacimiento(actaNacimiento);
    }

    private void prepararMediosContacto(Fisica personaFisica) {
        //MEDIOS DE CONTACTO - TELEFONO FIJO
        TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
        tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
        TelefonoFijo telefonoFijo = new TelefonoFijo("55-2834-3333", "345", "343");
        telefonoFijo.setTipoMedioContacto(tipoTelefonoFijo);
        personaFisica.setTelefonoFijo(telefonoFijo);

        //MEDIOS DE CONTACTO - CORREO ELECTRONICO
        TipoMedioContacto tipoCorreoElectronico = new TipoMedioContacto();
        tipoCorreoElectronico.setIdTipoMedioContacto(1L);
        CorreoElectronico correoElectronico = new CorreoElectronico();
        correoElectronico.setCorreo("gamo@usuario.com");
        correoElectronico.setTipoMedioContacto(tipoCorreoElectronico);
        personaFisica.setCorreoElectronico(correoElectronico);

        //MEDIOS DE CONTACTO - TELEFONO MOVIL
        TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
        tipoTelefonoMovil.setIdTipoMedioContacto(3L);
        TelefonoMovil telefonoMovil = new TelefonoMovil();
        telefonoMovil.setNumero("55-1433-5533");
        telefonoMovil.setTipoMedioContacto(tipoTelefonoMovil);
        personaFisica.setTelefonoMovil(telefonoMovil);

    }

    private void prepararDomicilio(Fisica personaFisica) {
        //DOMICILIO
        TipoDomicilio tipoDomicilio = new TipoDomicilio();
        tipoDomicilio.setClave(1);
        Domicilio domicilio = new Domicilio();
        domicilio.setTipoDomicilio(tipoDomicilio);

        // VIALIDADES
        TipoVialidad tipoVialidad = new TipoVialidad();
        tipoVialidad.setClave(1);
        Vialidad vialidad = new Vialidad();
        vialidad.setClave(1);
        vialidad.setTipoVialidad(tipoVialidad);
        vialidad.setNombre("MiHouse");
        domicilio.setVialidadPrimaria(vialidad);
        domicilio.setVialidadReferenciaPosterior(vialidad);
        domicilio.setVialidadReferenciaPrimaria(vialidad);
        domicilio.setVialidadReferenciaSecundaria(vialidad);

        domicilio.setLongitud(BigDecimal.valueOf(2L));
        domicilio.setNumExterior1(101);
        domicilio.setNumExterior2(102);
        domicilio.setNumExteriorAlf("B");
        domicilio.setNumInterior(2);
        domicilio.setNumInteriorAlf("1");

        TipoAmbito ambito = new TipoAmbito();
        ambito.setClave(1L);
        domicilio.setAmbito(ambito);

        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave("9");
        entidadFederativa.setNombre("DISTRITO FEDERAL");

        Municipio municipio = new Municipio();
        municipio.setClave("15");
        municipio.setEntidadFederativa(entidadFederativa);
        municipio.setNombre("CUAUHTï¿½MOC");

        Localidad localidad = new Localidad();
        localidad.setClave("1");
        localidad.setMunicipio(municipio);
        localidad.setNombre("CUAUHTï¿½MOC");

        Asentamiento asentamiento = new Asentamiento();
        asentamiento.setClave("10");
        asentamiento.setNombre("CUAUHTï¿½MOC");
        asentamiento.setLocalidad(localidad);

        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal("6500");
        domicilio.setCodigoPostal(codigoPostal);
        asentamiento.setCodigoPostal(codigoPostal);
        domicilio.setAsentamiento(asentamiento);
        personaFisica.getDomicilios().add(domicilio);
    }

    private void prepararEstadoCalif(Fisica personaFisica) {
        //ASIGNA CALIFICACION
        Calificacion calificacion1 = new Calificacion();
        calificacion1.setIdCalificacion(1L);
        Calificacion calificacion2 = new Calificacion();
        calificacion2.setIdCalificacion(2L);

        PersonaCalificacion personaCalificacion1 = new PersonaCalificacion();
        personaCalificacion1.setCalificacion(calificacion1);
        PersonaCalificacion personaCalificacion2 = new PersonaCalificacion();
        personaCalificacion2.setCalificacion(calificacion2);

        personaFisica.getPersonaCalificaciones().add(personaCalificacion1);
        personaFisica.getPersonaCalificaciones().add(personaCalificacion2);

        //ASIGNA ESTADOS
        EstadoPersona estadoPersona1 = new EstadoPersona();
        estadoPersona1.setIdEstadoPersona(1L);
        EstadoPersona estadoPersona2 = new EstadoPersona();
        estadoPersona2.setIdEstadoPersona(2L);

        PersonaEstado personaEstado1 = new PersonaEstado();
        personaEstado1.setEstadoPersona(estadoPersona1);
        PersonaEstado personaEstado2 = new PersonaEstado();
        personaEstado2.setEstadoPersona(estadoPersona2);

        personaFisica.getPersonaEstados().add(personaEstado1);
        personaFisica.getPersonaEstados().add(personaEstado2);
    }

    private Fisica prepararDatosBasicos() {
        //DATOS BASICOS
        Fisica personaFisica = new Fisica();
        personaFisica.setNombre("Jorge");
        personaFisica.setPrimerApellido("de la luz");
        personaFisica.setSegundoApellido("prueba");
        personaFisica.setRfc("RFCD0102024R2");
        personaFisica.setCurp("AAAABBBBCCCCDDDD22");

        //ENTIDAD FEDERATIVA
        personaFisica.getLugarNacimiento().setClave("9");

        //PAIS
        Pais pais = new Pais();
        pais.setIdPais(1);
        personaFisica.setPais(pais);

        //SEXO
        personaFisica.getSexo().setIdSexo(1);
        return personaFisica;
    }

    @Test
    public void otraAlta() throws DomicilioNoValidoException {
        final Fisica personaFisica = new Fisica();
        personaFisica.setCurp("HDJGD7ERE98DFJW");
        personaFisica.setRfc("HDFJ37878733");
        personaFisica.setNombre("CARLOS ALBERTO X");
        personaFisica.setPrimerApellido("GARCIA");
        personaFisica.setSegundoApellido("REYES");
        personaFisica.getLugarNacimiento().setClave("11");
        personaFisica.getLugarNacimiento().setNombre("PUEBLA");
        personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(1L);

        //PAIS
        Pais pais = new Pais();
        pais.setIdPais(1);
        personaFisica.setPais(pais);

        //SEXO
        personaFisica.getSexo().setIdSexo(1);

        //MEDIOS DE CONTACTO - CORREO ELECTRONICO
        TipoMedioContacto tipoCorreoElectronico = new TipoMedioContacto();
        tipoCorreoElectronico.setIdTipoMedioContacto(1L);
        CorreoElectronico correoElectronico = new CorreoElectronico();
        correoElectronico.setCorreo("usuario@usuario.com");
        correoElectronico.setTipoMedioContacto(tipoCorreoElectronico);
        personaFisica.setCorreoElectronico(correoElectronico);

        //MEDIOS DE CONTACTO - TELEFONO FIJO
        TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
        tipoTelefonoFijo.setIdTipoMedioContacto(2L);
        TelefonoFijo telefonoFijo = new TelefonoFijo("55-2834-3333", "345", "343");
        telefonoFijo.setClaveLada("90");
        telefonoFijo.setExtension("34552");
        telefonoFijo.setNumero("5533-3355");
        telefonoFijo.setTipoMedioContacto(tipoTelefonoFijo);
        personaFisica.setTelefonoFijo(telefonoFijo);

        //MEDIOS DE CONTACTO - TELEFONO MOVIL
        TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
        tipoTelefonoMovil.setIdTipoMedioContacto(3L);
        TelefonoMovil telefonoMovil = new TelefonoMovil();
        telefonoMovil.setNumero("55-1433-5533");
        telefonoMovil.setTipoMedioContacto(tipoTelefonoMovil);
        personaFisica.setTelefonoMovil(telefonoMovil);

        //DOMICILIO
        Domicilio domicilio = new Domicilio();
        personaFisica.getDomicilios().add(domicilio);

        TipoDomicilio tipoDomicilio = new TipoDomicilio();
        tipoDomicilio.setClave(1);
        domicilio.setTipoDomicilio(tipoDomicilio);

        // VIALIDADES
        TipoVialidad tipoVialidad = new TipoVialidad();
        tipoVialidad.setClave(1);
        Vialidad vialidad = new Vialidad();
        vialidad.setClave(1);
        vialidad.setTipoVialidad(tipoVialidad);
        vialidad.setNombre("MiHouse");
        domicilio.setVialidadPrimaria(vialidad);
        domicilio.setVialidadReferenciaPosterior(vialidad);
        domicilio.setVialidadReferenciaPrimaria(vialidad);
        domicilio.setVialidadReferenciaSecundaria(vialidad);

        domicilio.setLongitud(BigDecimal.valueOf(2L));
        domicilio.setNumExterior1(101);
        domicilio.setNumExterior2(102);
        domicilio.setNumExteriorAlf("B");
        domicilio.setNumInterior(2);
        domicilio.setNumInteriorAlf("1");

        TipoAmbito ambito = new TipoAmbito();
        ambito.setClave(1L);
        domicilio.setAmbito(ambito);

        //CODIGO POSTAL
        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal("6500");
        domicilio.setCodigoPostal(codigoPostal);

        //CONFIGURACION DE ASENTAMIENTO
        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave("9");
        entidadFederativa.setNombre("DISTRITO FEDERAL");

        Municipio municipio = new Municipio();
        municipio.setClave("15");
        municipio.setEntidadFederativa(entidadFederativa);
        municipio.setNombre("CUAUHTï¿½MOC");

        Localidad localidad = new Localidad();
        localidad.setClave("1");
        localidad.setMunicipio(municipio);
        localidad.setNombre("CUAUHTï¿½MOC");

        Asentamiento asentamiento = new Asentamiento();
        asentamiento.setClave("10");
        asentamiento.setNombre("CUAUHTï¿½MOC");
        asentamiento.setLocalidad(localidad);
        asentamiento.setCodigoPostal(codigoPostal);

        domicilio.setAsentamiento(asentamiento);

        //PERSONA VALIDADA POR IMSS
        personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(1L);

        //EJECUTA SERVICIO DE ALTA DE PERSONAS
        Fisica personaFisicaResultado = EjbLocator.getPersonaBusiness().altaPersonaFisica(personaFisica);

        //EVALUA EL RESULTADO
        System.out.println("IdPersona: " + personaFisicaResultado.getIdPersona());
        assertNotNull("El servicio nunca deberia devolver nulo!", personaFisicaResultado);

    }

    @Test
    public void testActualizarPersona() throws PersonaNoEncontradaException {
        final Fisica fisica = new Fisica();
        
        fisica.setIdPersona(25129008L);
        fisica.setFechaDefuncion(new Date());
        final Calendar calenFechaNac = Calendar.getInstance();
        calenFechaNac.set(1973, 11, 15);
        fisica.setFechaNacimiento(calenFechaNac.getTime());
        final EntidadFederativa lugarNacimiento = new EntidadFederativa();
        lugarNacimiento.setClave("14");
        fisica.setLugarNacimiento(lugarNacimiento);
        final EstadoCivil estadoCivil = new EstadoCivil();
        estadoCivil.setIdEstadoCivil(2);
        fisica.setEstadoCivil(estadoCivil);
        final Sexo sexo = new Sexo();
        sexo.setIdSexo(1);
        fisica.setSexo(sexo);
        fisica.setNombre("nameNew");
        fisica.setPrimerApellido("primerApellidoChange");
        fisica.setSegundoApellido("segundoApellidoChange");
        
        EjbLocator.getPersonaBusiness().actualizarPersona(fisica);
    }
    
    
    @Test
    public void probarConsultasPersonasFisicasChingon(){
    	
    	try{
    		
	    	Fisica fisica = new Fisica();
	    	List<Candidato> candidatos = null;
	   
	    	fisica.setCurp("ROGS820119HPLDRM03");
	    	fisica.setRfc("ROGS8201197D8");
	    	fisica.setNombre("SAMUEL");
	    	fisica.setPrimerApellido("RODRIGUEZ");
	    	fisica.setSegundoApellido("GRAJEDA");	    	
	    	
//	    	fisica.setCurp("ROGS820119HPLDRM03");
//	    	fisica.setRfc("ROGS8201197D888");
//	    	fisica.setNombre("SAMUELLL");
//	    	fisica.setPrimerApellido("RODRIGUEZ");
//	    	fisica.setSegundoApellido("GRAJEDA");
	    	
//	    	fisica.setCurp("");
//	    	fisica.setRfc("");
//	    	fisica.setNombre("ANA SANDRA");
//	    	fisica.setPrimerApellido("ESCUDERO");
//	    	fisica.setSegundoApellido("JIMENEZ");
	
	    //	candidatos = EjbLocator.getConsultaPersonaFisicaServiceBusiness().consultarPersonaFisica(fisica);
	    //	System.out.println("LISTA DE CANDIDATOS: *****************************************************************************************" + candidatos);
	    //	System.out.println("*************************************************************************************************************");
	    	
	    	Fisica fisicaResultado = null;
	    
	    	if(candidatos != null && candidatos.size() > 0){
	    		fisicaResultado = EjbLocator.getComplementarCalificacionPersonaFisicaServiceBusiness().complementarCalificaciones((Fisica)candidatos.get(0).getPersona(), fisica);
		    	System.out.println("FISICA RESULTADO: *******************************************************************************************" + fisicaResultado);
		    	System.out.println("*************************************************************************************************************");
	    	}else{
	    		//N2
	    		fisicaResultado = EjbLocator.getLocalizarPersonaFisicaEnEntidadesExternasServiceBusiness().localizarPersonaFisicaEnEntidadesExternas(fisica);
		    	System.out.println("FISICA RESULTADO: *******************************************************************************************" + fisicaResultado);
		    	System.out.println("*************************************************************************************************************");
	    	}
	    	
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    }
    
    /**
     * 191807 081012
     */
    @Test
    public void probarLocalizarPersonaFisicaEnEntidadesExternas(){
    	
    	try{
    		
	    	Fisica fisica = new Fisica();
//	    	List<Candidato> candidatos = null;
	   
	    	fisica.setCurp("ROGS820119HPLDRM03");
	    	fisica.setRfc("ROGS8201197D8");
	    	fisica.setNombre("SAMUEL");
	    	fisica.setPrimerApellido("RODRIGUEZ");
	    	fisica.setSegundoApellido("GRAJEDA");
	    	
	    	Fisica fisicaResultado = null;
	    	
	    	fisicaResultado = EjbLocator.getLocalizarPersonaFisicaEnEntidadesExternasServiceBusiness().localizarPersonaFisicaEnEntidadesExternas(fisica);
	    	System.out.println("FISICA RESULTADO: *******************************************************************************************" + fisicaResultado);
	    	System.out.println("*************************************************************************************************************");
	    	
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    
    @Test
    public void probarActualizarPersonaFisica(){
    	
    	try{
    		
	    	Fisica fisica = new Fisica();
	   
	    	fisica.setCurp("ROGS820119HPLDRM03");
	    	fisica.setRfc("ROGS8201197D8");
	    	fisica.setNombre("SAMUEL 302");
	    	fisica.setPrimerApellido("Rodriguez 302");
	    	fisica.setSegundoApellido("Grajeda 302");
	    	fisica.setCveFisica(25129253L);
    		
	    	Fisica fisicaResultado = null;
	    	
	    	// TODO VERIFICAR METODO
	    	//fisicaResultado = EjbLocator.getActualizarPersonaFisicaServiceBusinessRemote().actualizarPersonaFisica(fisica);
	    	System.out.println("FISICA RESULTADO: *******************************************************************************************" + fisicaResultado);
	    	System.out.println("*************************************************************************************************************");

    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    
    @Test
    public void icaGlobalTest(){
    	PersonaGlobalBusinessRemote pgbr= EJBLocator.getPersonaGlobalService();
    	PersonaTO persona = new PersonaTO();
    	persona.setCurp("MACH801112HDFRHG01");
    	persona.setIdPersona(37490878l);
    	persona.setRfc("MACH801112CEA");
    	pgbr.evaluarPersonaConInstanciaExternas(persona);
    }
    
    @Test
	public void registrarPersonaConEntidadesExternasTest() {
		PersonaBusinessRemote ejb = EjbLocator.getPersonaBusiness();

		assertNotNull(ejb);

		TipoPersonaFiscal tipoPersonaTest = TipoPersonaFiscal.FISICA;

		Persona persona = null;
		
		switch (tipoPersonaTest) {
		case FISICA:
			
			Fisica fisica = new Fisica();
			fisica.setCurp("SAAC820312HDFNLS06");
//			fisica.setRfc("VIAM8801025C4");
			
			persona = fisica;
			
			break;

		case MORAL:
			
			Moral moral = new Moral();
			moral.setRfc("COA150625FG7");
			
			persona = moral;
			
			break;
		}
    		
    	try {
			persona = ejb.registrarPersonaConEntidadesExternas(persona, true);
			
			System.out.println("Perosona recién creada -> \n " + persona);
		} catch (RegistroPersonaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (RegistroPersonaFisicaException e) {
			e.printStackTrace();
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
		} catch (DomicilioNoValidoException e) {
			e.printStackTrace();
		}
    }
}

