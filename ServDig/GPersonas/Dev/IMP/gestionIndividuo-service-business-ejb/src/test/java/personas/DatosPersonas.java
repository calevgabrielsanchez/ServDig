package personas;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;

public class DatosPersonas {

	public static void agregaTramitePersona(Solicitud solicitud, Persona persona){
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.REGISTRO_PERSONA.longValue());
		tipoTramite.setDesTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.TIPO_TRAMITE_1_REGISTRO_PERSONA);

		final Tramite tramite1 = new Tramite();
		tramite1.setTipoTramite(tipoTramite);
		
		if (persona instanceof Moral){
			tramite1.setPersonaMoral((Moral)persona);	
		}
		else{
			tramite1.setPersonaFisica((Fisica)persona);
		}
		tramite1.setIdEstadoTramite(1L);
		solicitud.getTramite().add(tramite1);		
	}	
	
	public static void agregaPersonaMoral(Moral personaMoral){
//        personaMoral.setNombreComercial("EMPRESA DE PRUEBA");
        personaMoral.setRazonSocial("EMPRESA DE PRUEBA S.A DE C.V.");
        personaMoral.setRfc("RFCPRUEBA");
        personaMoral.setActaConstitutiva("ACTAPRUEBA1");
        TipoSociedad tipoSociedad = new TipoSociedad();
        tipoSociedad.setIdTipoSociedad(1L);
        personaMoral.setTipoSociedad(tipoSociedad);
        personaMoral.setFechaCreacion(new java.util.Date());	
        
		//CALIFICACION DE LOS DATOS
        Calificacion calificacion = new Calificacion();
        calificacion.setIdCalificacion(1L);
        PersonaCalificacion calif = new PersonaCalificacion();
        calif.setCalificacion(calificacion);
        personaMoral.getPersonaCalificaciones().add(calif);	        
	}

	public static void agregaPersonaFisica1(Fisica personaFisica){
		// INICIALIZA LA SOLICITUD
		personaFisica.setNombre("SAMUEL PRUEBA");
		personaFisica.setPrimerApellido("RODRIGUEZ");
		personaFisica.setSegundoApellido("GRAJEDA");
		personaFisica.setFechaNacimiento(new Date());
		personaFisica.getSexo().setIdSexo(1);
		personaFisica.getSexo().setDescripcion("Hombre");
		
		EntidadFederativa lugarNacimiento = new EntidadFederativa();
		lugarNacimiento.setClave("21");
		lugarNacimiento.setNombre("PUEBLA");
		personaFisica.setLugarNacimiento(lugarNacimiento);		
		personaFisica.setCurp("EGGS820119HPLDRM04");
		
		//CALIFICACION DE LOS DATOS
        Calificacion subestado = new Calificacion();
        subestado.setIdCalificacion(1L);
        PersonaCalificacion calif = new PersonaCalificacion();
        calif.setCalificacion(subestado);
        personaFisica.getPersonaCalificaciones().add(calif);		
	}

	public static void agregaPersonaFisica2(Fisica personaFisica){
		// INICIALIZA LA SOLICITUD
		personaFisica.setNombre("CARLOS PRUEBA");
		personaFisica.setPrimerApellido("RODRIGUEZ");
		personaFisica.setSegundoApellido("GRAJEDA");
		personaFisica.setFechaNacimiento(new Date());
		personaFisica.getSexo().setIdSexo(1);
		personaFisica.getSexo().setDescripcion("Hombre");
		EntidadFederativa lugarNacimiento = new EntidadFederativa();
		lugarNacimiento.setClave("21");
		lugarNacimiento.setNombre("PUEBLA");
		personaFisica.setLugarNacimiento(lugarNacimiento);	
		personaFisica.setCurp("YAYO820119HPLDRM03");
		
		//CALIFICACION DE LOS DATOS
        Calificacion subestado = new Calificacion();
        subestado.setIdCalificacion(2L);
        PersonaCalificacion calif = new PersonaCalificacion();
        calif.setCalificacion(subestado);
        personaFisica.getPersonaCalificaciones().add(calif);		
	}
	
    public static void agregaDocumentosProbatorios(Fisica personaFisica) {
        // DOCUMENTOS PROBATORIOS
        Nacimiento actaNacimiento = new Nacimiento();
        actaNacimiento.setAnio(1982);
        actaNacimiento.setCrip("0");
        actaNacimiento.setFechaExpedicion(new Date());
        actaNacimiento.setFechaSuceso(new Date());
        actaNacimiento.setNoActa("111");
        actaNacimiento.setNoFoja("0");
        actaNacimiento.setNoLibro("7");
        actaNacimiento.setTomo("0");
        actaNacimiento.setNoJuzgado("333");
        Municipio municipio = new Municipio();
        municipio.setClave("114");
        municipio.setNombre("PUEBLA DE LOS ANGELES");
        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setClave("21");
        entidadFederativa.setNombre("PUEBLA");
        municipio.setEntidadFederativa(entidadFederativa);
        actaNacimiento.setMunicipio(municipio);

        personaFisica.setActaNacimiento(actaNacimiento);
    }

    public static void agregarMediosContacto(Persona persona) {
        //MEDIOS DE CONTACTO - TELEFONO FIJO
        TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
        tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
        TelefonoFijo telefonoFijo = new TelefonoFijo("55-2834-3333", "345", "343");
        telefonoFijo.setTipoMedioContacto(tipoTelefonoFijo);
        persona.setTelefonoFijo(telefonoFijo);

        //MEDIOS DE CONTACTO - CORREO ELECTRONICO
        TipoMedioContacto tipoCorreoElectronico = new TipoMedioContacto();
        tipoCorreoElectronico.setIdTipoMedioContacto(1L);
        CorreoElectronico correoElectronico = new CorreoElectronico();
        correoElectronico.setCorreo("gamo@usuario.com");
        correoElectronico.setTipoMedioContacto(tipoCorreoElectronico);
        persona.setCorreoElectronico(correoElectronico);

        //MEDIOS DE CONTACTO - TELEFONO MOVIL
        TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
        tipoTelefonoMovil.setIdTipoMedioContacto(3L);
        TelefonoMovil telefonoMovil = new TelefonoMovil();
        telefonoMovil.setNumero("55-1433-5533");
        telefonoMovil.setTipoMedioContacto(tipoTelefonoMovil);
        persona.setTelefonoMovil(telefonoMovil);
    }
    
    public static void agregarDomicilio(Persona persona) {
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
        municipio.setNombre("CUAUHT�MOC");

        Localidad localidad = new Localidad();
        localidad.setClave("1");
        localidad.setMunicipio(municipio);
        localidad.setNombre("CUAUHT�MOC");

        Asentamiento asentamiento = new Asentamiento();
        asentamiento.setClave("10");
        asentamiento.setNombre("CUAUHT�MOC");
        asentamiento.setLocalidad(localidad);

        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal("6500");
        domicilio.setCodigoPostal(codigoPostal);
        asentamiento.setCodigoPostal(codigoPostal);
        domicilio.setAsentamiento(asentamiento);
        persona.getDomicilios().add(domicilio);
    }
}
