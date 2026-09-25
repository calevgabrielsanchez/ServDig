package mx.gob.imss.ctirss.delta.gestion.domicilio.service.business.domicilio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.LocalizarUmfException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.entity.domicilio.DomicilioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.AreaGeograficaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Municipio;

import org.apache.log4j.Logger;

@Stateless(name = "domicilioExternosServiceBusiness", mappedName = "domicilioExternosServiceBusiness")
public class DomicilioExternosServiceBusiness implements DomicilioServiceBussinessExternosRemote {

    @EJB
    private DomicilioServiceBusinessRemote domicilioService;
    @EJB
    private UmfServiceRemote umfService;
    /**
     * Servicio para la consulta de domicilios
     */
    @EJB
    private DomicilioServiceEntityLocal entity;
    /**
     * Bean de cnsultas directas
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    protected EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(DomicilioExternosServiceBusiness.class);

    /**
     * Metodo que recupera una lista de UMF asociadas a un codigo postal a 5
     * digitos Servicio que conuslta una las umf asociadas a un asentamiento en
     * caso de no encontrar consulta una UMF defaul a nivel delegacion -
     * subdelegacion en caso de no econtrar registros arroja una excepcion
     *
     * @param String codigoPostal
     * @return lista de UMF con los datos de la delegacion y subdelegacion a la
     * que pertenece llenos
     * @throws GenerarNSSException
     */

    @Override
    public UnidadMedicaFamiliarTO[] consultarUMFPorCP(String codigoPostal)
            throws LocalizarUmfException {
        if (codigoPostal == null) {
            throw new IllegalArgumentException();
        }
        List<UnidadMedicaFamiliar> lista = null;
        try {
            /* se cambio la llamada por que el metodo hacia afuera trae regla de UMF de CFE*/
            //List<UnidadMedicaFamiliar> lista = domicilioService.getUmfByCodigoPostal(codigoPostal);
            lista = umfService.findUmfByCodigoPostal(codigoPostal, 1);
            List<UnidadMedicaFamiliarTO> listaFinal = new ArrayList<UnidadMedicaFamiliarTO>();

            if (lista == null) {
                lista = domicilioService.getUmfDefaultByCodigoPostal(codigoPostal);
            }

            for (UnidadMedicaFamiliar umf : lista) {
                UnidadMedicaFamiliarTO umfTO = new UnidadMedicaFamiliarTO();
                umfTO.setClavePresupuestal(umf.getClavePresupuestal());
                umfTO.setDescripcion(umf.getDescripcion());
                umfTO.setDesDireccion(umf.getDesDireccion());
                umfTO.setGeneracionCita(umf.getGeneracionCita());
                umfTO.setIdUMF(umf.getIdUMF());
                umfTO.setNivelAtencion(umf.getNivelAtencion());
                umfTO.setNoConsultorio(umf.getNoConsultorio());
                umfTO.setNoEconomico(umf.getNoEconomico());
                umfTO.setNombreCorto(umf.getNombreCorto());
                umfTO.setSubdelegacion(umf.getSubdelegacion());
                umfTO.setTipoUMF(umf.getTipoUMF());
                umfTO.setClavePresupuestal(umf.getClavePresupuestal());
                listaFinal.add(umfTO);
            }

            return listaFinal.toArray(new UnidadMedicaFamiliarTO[lista.size()]);

        } catch (Exception e) {
            LOGGER.error("Error en consultarUMFPorCP(String codigoPostal): " + codigoPostal + "]", e);
            throw new LocalizarUmfException(e.getMessage());
        }

    }

    @Override
    public Vialidad[] obtenerVialidadesAutocompletar(Localidad localidad,
            int periodo, String nomVialidad)
            throws VialidadesNoLocalizadasException {
        List<Vialidad> vialidades = domicilioService
                .obtenerVialidadesAutocompletar(localidad, periodo, nomVialidad.toUpperCase());

        List<Vialidad> lstVialidadesConCve = new ArrayList<Vialidad>();

        for (Vialidad vialidadSeleccionada : vialidades) {
            Domicilio domicilio = domicilioService.obtenerVialidadElegida(
                    localidad, periodo, vialidadSeleccionada);
            Vialidad vialidadEncontrada = domicilio.getVialidadPrimaria();
            lstVialidadesConCve.add(vialidadEncontrada);
        }

        return lstVialidadesConCve.toArray(new Vialidad[lstVialidadesConCve.size()]);
    }

    @Override
    public mx.gob.imss.digital.modelo.domicilio.Domicilio consultarUltimoDomicilioParticilar(Long idPersona) throws DomicilioNoLocalizadoException,MunicipioImssNoLocalizadoException {
        List<Long> tiposDomicilio = new ArrayList<Long>();
        tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona personaModel = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona();
        personaModel.setIdPersona(idPersona);
        List<mx.gob.imss.ctirss.delta.model.domicilio.Domicilio> domicilios;
        try {
            domicilios = this.entity.consultarDomiciliosPersonaFisicaPorTipoOrdenadoPorFecha(personaModel,
                    tiposDomicilio,"DESC");
        } catch (Exception e) {
            domicilios = new ArrayList<mx.gob.imss.ctirss.delta.model.domicilio.Domicilio>();
        }

        mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio = new mx.gob.imss.digital.modelo.domicilio.Domicilio();
        if (!domicilios.isEmpty()) {
            mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioModelo = domicilios.get(0);
            domicilio.setCodigoPostal(domicilioModelo.getCodigoPostal().getCodigoPostal());
            domicilio.setIdDomicilio(domicilioModelo.getClave().longValue());
            domicilio.setCalle(domicilioModelo.getCalle());
            domicilio.setColonia(domicilioModelo.getColonia());
            domicilio.setNumExterior1(domicilioModelo.getNumExterior1());
            domicilio.setNumExterior2(domicilioModelo.getNumExterior2());
            domicilio.setNumExteriorAlf(domicilioModelo.getNumExteriorAlf());
            domicilio.setNumInterior(domicilioModelo.getNumInterior());
            domicilio.setNumInteriorAlf(domicilioModelo.getNumInteriorAlf());
            domicilio.setLatitud(domicilioModelo.getLatitud());
            domicilio.setLongitud(domicilioModelo.getLongitud());
            if (domicilioModelo.getVialidadPrimaria() != null) {
                domicilio.setVialidadPrimaria(new mx.gob.imss.digital.modelo.domicilio.Vialidad());
                domicilio.getVialidadPrimaria().setNombre(domicilioModelo.getVialidadPrimaria().getNombre());
            }
            if (domicilioModelo.getAsentamiento() != null) {
                domicilio.setColonia(domicilioModelo.getAsentamiento().getNombre());
                domicilio.setAsentamiento(new mx.gob.imss.digital.modelo.domicilio.Asentamiento());
                domicilio.getAsentamiento().setClave(domicilioModelo.getAsentamiento().getClave());
                domicilio.getAsentamiento().setCodigoPostal(domicilioModelo.getCodigoPostal().getCodigoPostal());
            }
            mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidad = domicilioModelo
                    .getLocalidad() != null ? domicilioModelo.getLocalidad() : domicilioModelo
                            .getAsentamiento().getLocalidad();
            if (localidad != null) {

                if(domicilio.getAsentamiento()==null){
                    domicilio.setAsentamiento(new mx.gob.imss.digital.modelo.domicilio.Asentamiento());
                    domicilio.getAsentamiento().setClave(domicilioModelo.getAsentamiento().getClave());
                }
                domicilio.getAsentamiento().setLocalidad(new mx.gob.imss.digital.modelo.domicilio.Localidad());
                domicilio.getAsentamiento().getLocalidad().setClave(localidad.getClave());
                domicilio.getAsentamiento().getLocalidad().setNombre(localidad.getNombre());

                domicilio.setLocalidad(new mx.gob.imss.digital.modelo.domicilio.Localidad());
                domicilio.getLocalidad().setClave(localidad.getClave());
                domicilio.getLocalidad().setNombre(localidad.getNombre());
                mx.gob.imss.ctirss.delta.model.domicilio.Municipio municipio = domicilioModelo
                        .getAsentamiento().getMunicipio() != null ? domicilioModelo
                                .getAsentamiento().getMunicipio() : localidad.getMunicipio();

                if (municipio != null) {

                    domicilio.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
                    domicilio.getAsentamiento().getLocalidad().getMunicipio().setClave(municipio.getClave());
                    domicilio.getAsentamiento().getLocalidad().getMunicipio().setNombre(municipio.getNombre());

                    domicilio.getLocalidad().setMunicipio(new Municipio());
                    domicilio.getLocalidad().getMunicipio().setClave(municipio.getClave());
                    domicilio.getLocalidad().getMunicipio().setNombre(municipio.getNombre());
                    if (municipio.getEntidadFederativa() != null) {

                        domicilio.getAsentamiento().getLocalidad().getMunicipio()
                                .setEntidadFederativa(new EntidadFederativa());
                        domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()
                                .setClave(municipio.getEntidadFederativa().getClave());
                        domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()
                                .setNombre(municipio.getEntidadFederativa().getNombre());

                        domicilio.getLocalidad().getMunicipio()
                                .setEntidadFederativa(new EntidadFederativa());
                        domicilio.getLocalidad().getMunicipio().getEntidadFederativa()
                                .setClave(municipio.getEntidadFederativa().getClave());
                        domicilio.getLocalidad().getMunicipio().getEntidadFederativa()
                                .setNombre(municipio.getEntidadFederativa().getNombre());
                    }
                    try {
                        List<MunicipioIMSS> municipiosImss = entity.getMunicipioIMSSbyEstadoMunCP(municipio,
                                domicilioModelo.getCodigoPostal().getCodigoPostal());
                        MunicipioIMSS municipioImss = municipiosImss.get(0);
                        DicMunicipioImss dicMunicipio = em.find(DicMunicipioImss.class, Long.valueOf(
                                municipioImss.getIdMunicipio()));
                        if(dicMunicipio!=null&&dicMunicipio.getDicAreaGeografica()!=null){
                            Long idArea = dicMunicipio.getDicAreaGeografica().getCveIdAreaGeografica();
                            LOGGER.info("idArea: "+idArea.intValue());
                            if(AreaGeograficaEnum.geFromId(idArea.intValue()).getClave()!=null){
                                String clave = AreaGeograficaEnum.geFromId(idArea.intValue()).getClave();
                                LOGGER.info("Clave: "+clave);
                                domicilio.setDescripcion(clave);
                            }else{
                                LOGGER.info("Clave null");
                                domicilio.setDescripcion(null);
                            }

                        }else{
                            LOGGER.info("Clave null");
                            domicilio.setDescripcion(null);
                        }

                    } catch (NumberFormatException e) {
                        LOGGER.error("ERROR: "+e.getMessage());
                        throw new DomicilioNoLocalizadoException("El municipio almacenado para el domicilio tiene error de datos. ");
                    } catch (NullPointerException ex){
                        LOGGER.error("ERROR: ",ex);
                        domicilio.setDescripcion(null);
                        LOGGER.error("SE setea descripcion en null");
                    }
                }

            }

        } else {
            throw new DomicilioNoLocalizadoException("El domicilio no ha sido localizado");
        }
        return domicilio;
    }

}
