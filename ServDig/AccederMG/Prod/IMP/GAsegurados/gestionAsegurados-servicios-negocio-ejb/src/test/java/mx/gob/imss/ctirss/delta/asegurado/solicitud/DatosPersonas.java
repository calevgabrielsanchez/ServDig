package mx.gob.imss.ctirss.delta.asegurado.solicitud;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
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
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

public class DatosPersonas {

    public static void agregaTramitePersonaFisica(final Solicitud solicitud, final AsignacionNSS personaFisica) {
        final TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(1);

        final TramiteAsegurado tramite1 = new TramiteAsegurado(); 
        tramite1.setTipoTramite(tipoTramite);
        tramite1.setFisica(personaFisica);
        tramite1.getEstadoTramite().setIdEstadoTramitePersona(1);
        
        final Serie serie = new Serie();
        serie.setAnioRegistro(1970);
        serie.setIdSerie(1L);
        tramite1.setSerie(serie);

        solicitud.getTramites().add(tramite1);
    }

    public static void agregaPersonaFisica1(final AsignacionNSS personaFisica) {
        // INICIALIZA LA SOLICITUD
        personaFisica.setNombre("SAMUEL PRUEBA");
        personaFisica.setPrimerApellido("RODRIGUEZ");
        personaFisica.setSegundoApellido("GRAJEDA");
        personaFisica.setFechaNacimiento(new Date());
        personaFisica.getSexo().setIdSexo(1);
        personaFisica.getSexo().setDescripcion("Hombre");
        personaFisica.getLugarNacimiento().setClave("21");
        personaFisica.getLugarNacimiento().setNombre("PUEBLA");
        personaFisica.setCurp("EGGS820119HPLDRM03");

        //CALIFICACION DE LOS DATOS
        final Calificacion subestado = new Calificacion();
        subestado.setIdCalificacion(1L);
        final PersonaCalificacion calif = new PersonaCalificacion();
        calif.setCalificacion(subestado);
        personaFisica.getPersonaCalificaciones().add(calif);
        
        // PAIS
        final Pais pais = new Pais();
        pais.setIdPais(1);
        personaFisica.setPais(pais);
    }

    public static void agregaPersonaFisica2(final AsignacionNSS personaFisica) {
        // INICIALIZA LA SOLICITUD
        personaFisica.setNombre("CARLOS PRUEBA");
        personaFisica.setPrimerApellido("RODRIGUEZ");
        personaFisica.setSegundoApellido("GRAJEDA");
        personaFisica.setFechaNacimiento(new Date());
        personaFisica.getSexo().setIdSexo(1);
        personaFisica.getSexo().setDescripcion("Hombre");
        personaFisica.getLugarNacimiento().setClave("21");
        personaFisica.getLugarNacimiento().setNombre("PUEBLA");
        personaFisica.setCurp("YAYO820119HPLDRM03");

        //CALIFICACION DE LOS DATOS
        final Calificacion subestado = new Calificacion();
        subestado.setIdCalificacion(2L);
        final PersonaCalificacion calif = new PersonaCalificacion();
        calif.setCalificacion(subestado);
        personaFisica.getPersonaCalificaciones().add(calif);
    }

    public static void agregaDocumentosProbatorios(final AsignacionNSS personaFisica) {
        // DOCUMENTOS PROBATORIOS
        final Nacimiento actaNacimiento = new Nacimiento();
        actaNacimiento.setAnio(1982);
        actaNacimiento.setCrip("0");
        actaNacimiento.setFechaExpedicion(new Date());
        actaNacimiento.setFechaSuceso(new Date());
        actaNacimiento.setNoActa("111");
        actaNacimiento.setNoFoja("0");
        actaNacimiento.setNoLibro("7");
        actaNacimiento.setTomo("0");
        actaNacimiento.setNoJuzgado("333");
        final Municipio municipio = new Municipio();
        municipio.setClave("114");
        municipio.setNombre("PUEBLA DE LOS ANGELES");
        final EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave("21");
        entidadFederativa.setNombre("PUEBLA");
        municipio.setEntidadFederativa(entidadFederativa);
        actaNacimiento.setMunicipio(municipio);

        personaFisica.setActaNacimiento(actaNacimiento);
    }

    public static void agregaDocumentosProbatoriosCurp(final AsignacionNSS personaFisica) {
        // DOCUMENTOS PROBATORIOS
        CURP curp = new CURP();
        curp.setAnioRegistro(1974L);        
        curp.setNoTomo("0");
        curp.setCrip("0");
        curp.setNoFoja("0");
        curp.setNoLibro("2");
        curp.setNoActa("562");
        curp.setCurp("NEAG680629HVZRVD00");
        curp.setFechaInscripcion(new Date());
        curp.setRefFolio("SIN FOLIO");
        curp.setNumFolioExtranjero("1111");
    	                         
        //MUNICIPIO - ENTIDAD DEL ACTA DE NACIMIENTO
        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setNombre("VERACRUZ");
        entidadFederativa.setClave("30");            
        Municipio municipio = new Municipio();
        municipio.setNombre("CERRO AZUL");
        municipio.setClave("34");
        municipio.setEntidadFederativa(entidadFederativa);
        curp.setMunicipio(municipio);
        curp.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor().longValue());
        curp.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getDescripcion());
        
        //personaFisica.setCurpDocumento(curp);
    }
    
    public static void agregarMediosContacto(final AsignacionNSS personaFisica) {
        //MEDIOS DE CONTACTO - TELEFONO FIJO
        final TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
        tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
        final TelefonoFijo telefonoFijo = new TelefonoFijo("55-2834-3333", "345", "343");
        telefonoFijo.setTipoMedioContacto(tipoTelefonoFijo);
        personaFisica.setTelefonoFijo(telefonoFijo);

        //MEDIOS DE CONTACTO - CORREO ELECTRONICO
        final TipoMedioContacto tipoCorreoElectronico = new TipoMedioContacto();
        tipoCorreoElectronico.setIdTipoMedioContacto(1L);
        final CorreoElectronico correoElectronico = new CorreoElectronico();
        correoElectronico.setCorreo("gamo@usuario.com");
        correoElectronico.setTipoMedioContacto(tipoCorreoElectronico);
        personaFisica.setCorreoElectronico(correoElectronico);

        //MEDIOS DE CONTACTO - TELEFONO MOVIL
        final TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
        tipoTelefonoMovil.setIdTipoMedioContacto(3L);
        final TelefonoMovil telefonoMovil = new TelefonoMovil();
        telefonoMovil.setNumero("55-1433-5533");
        telefonoMovil.setTipoMedioContacto(tipoTelefonoMovil);
        personaFisica.setTelefonoMovil(telefonoMovil);
    }

    public static void agregarDomicilio(final AsignacionNSS personaFisica) {
        //DOMICILIO
        final TipoDomicilio tipoDomicilio = new TipoDomicilio();
        tipoDomicilio.setClave(1);
        final Domicilio domicilio = new Domicilio();
        domicilio.setTipoDomicilio(tipoDomicilio);

        // VIALIDADES
        final TipoVialidad tipoVialidad = new TipoVialidad();
        tipoVialidad.setClave(1);
        final Vialidad vialidad = new Vialidad();
        vialidad.setClave(1);
        vialidad.setTipoVialidad(tipoVialidad);
        vialidad.setNombre("MiHouse");
        domicilio.setVialidadPrimaria(vialidad);
        domicilio.setVialidadReferenciaPosterior(vialidad);
        domicilio.setVialidadReferenciaPrimaria(vialidad);
        domicilio.setVialidadReferenciaSecundaria(vialidad);

        //domicilio.setLongitud(2);
        domicilio.setNumExterior1(101);
        domicilio.setNumExterior2(102);
        domicilio.setNumExteriorAlf("B");
        domicilio.setNumInterior(2);
        domicilio.setNumInteriorAlf("1");

        final TipoAmbito ambito = new TipoAmbito();
        ambito.setClave(1L);
        domicilio.setAmbito(ambito);

        final EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave("9");
        entidadFederativa.setNombre("DISTRITO FEDERAL");

        final Municipio municipio = new Municipio();
        municipio.setClave("15");
        municipio.setEntidadFederativa(entidadFederativa);
        municipio.setNombre("CUAUHT�MOC");

        final Localidad localidad = new Localidad();
        localidad.setClave("1");
        localidad.setMunicipio(municipio);
        localidad.setNombre("CUAUHT�MOC");

        final Asentamiento asentamiento = new Asentamiento();
        asentamiento.setClave("10");
        asentamiento.setNombre("CUAUHT�MOC");
        asentamiento.setLocalidad(localidad);

        final CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal("6500");
        domicilio.setCodigoPostal(codigoPostal);
        asentamiento.setCodigoPostal(codigoPostal);
        domicilio.setAsentamiento(asentamiento);
        personaFisica.getDomicilios().add(domicilio);
    }

}
