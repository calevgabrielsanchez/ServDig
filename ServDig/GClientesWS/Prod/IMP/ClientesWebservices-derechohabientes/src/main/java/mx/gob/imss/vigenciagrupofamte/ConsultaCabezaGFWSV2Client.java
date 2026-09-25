package mx.gob.imss.vigenciagrupofamte;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.enums.EstadoInconsistenciaVigenciaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultaCabezaGFWSV2Client {

    public CabezaGrupoFamiliarTE obtieneInfoCabGpoFamTE(Long idAsignacionNSS) throws DerechohabientesWebSserviceException {

        try {
            WSConsInfoCabGpoFamTE_Service service = new WSConsInfoCabGpoFamTE_Service();
            WSConsInfoCabGpoFamTE ws = service.getWSConsInfoCabGpoFamTEPort();
            RespuestaWSConsInfoCabGpoFam respuesta = ws.obtieneInfoCabGpoFam(idAsignacionNSS.intValue());
            CabezaGrupoFamiliarTE cgf;

            boolean isPensionado;
            Long idPatron;
            String rp;
            String modalidad;
            long idEstado;

            if (respuesta != null && respuesta.getCodigoError() == 0) {
                cgf = new CabezaGrupoFamiliarTE();
                InfoCabezaGrupoFamiliarVO itemRespuesta = respuesta.getInfoCabezaGrupoFamiliarVO();
                idEstado = itemRespuesta.getCveEstadoDerechohabiente().longValue();

                cgf.setAsignacionNSS(itemRespuesta.getCveIdAsignacionNss().longValue());

                Parentesco p = new Parentesco();
                Long idParentesco = itemRespuesta.getCveIdCalidadParentesco().longValue();
                isPensionado = idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
                p.setIdParentesco(idParentesco);
                cgf.setCalidadParentesco(p);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha;
                //Se valida fecha del ultimo movimiento que no venga nulo
                if (itemRespuesta.getFecUltimoMovto() != null) {
                    fecha = formatter.parse(itemRespuesta.getFecUltimoMovto());
                    cgf.setFechaUltimoMovAfiliacion(fecha);
                }

                if (itemRespuesta.getIndPatronImss() != null && itemRespuesta.getIndPatronImss() != null) {
                    cgf.setPatronImss(itemRespuesta.getIndPatronImss());
                }

                idPatron = itemRespuesta.getCveIdPatronGeneral() == null ? null : itemRespuesta.getCveIdPatronGeneral().longValue();
                rp = itemRespuesta.getRegPatron();
                modalidad = itemRespuesta.getCveModal();
                SujetoObligado patron = null;
                //Si es pensionado
                if (isPensionado) {
                    //verificamos si la modalidad es nula
                    if (modalidad != null) {
                        if (idPatron == null && (rp == null || rp.equals("00000000"))) {
                            //si tiene modalidad pero no tiene patron mandamos una excepcion
                            throw new DerechohabientesWebSserviceException(
                                    "El pensionado cuenta con modalidad " + modalidad + ", pero no cuenta con un patr&oacute;n v&aacute;lido(" + this
                                            .getRp(idPatron, rp) + ").");
                        } else {
                            if (idPatron != null || modalidad.equals("35")) {
                                patron = this.construirPatronSujetoObligado(idPatron, rp, modalidad);
                            }
                        }
                    }//si la modalidad es nula el patron no se seteara y sera nulo
                } else {
                    //si no es pensionado
                    if (modalidad == null) {//Si la modalidad es nula
                        //mandamos excepcion ya que siempre deberia mandar una modalidad aunque sea 00
                        throw new DerechohabientesWebSserviceException("El asegurado no cuenta con ninguna modalidad relacionada.");
                    } else { //Si la modalidad es diferente de nula
                        if (idPatron == null && (rp == null || rp.equals("00000000"))) {//si viene el patron o tiene rp
                            //Si el estado del asegurado es baja
                            throw new DerechohabientesWebSserviceException(
                                    "El asegurado no se encuentra relacionado a un patr&oacute;n v&aacute;lido(" + this.getRp(idPatron, rp) + ") mod("
                                            + modalidad + ").");
                        }
                    }
                    //Si no se manda ninguna excepcion se construye el patron en base al id, el rp y la modalidad
                    patron = this.construirPatronSujetoObligado(idPatron, rp, modalidad);
                }

                cgf.setPatronSujetoObligado(patron);

                if (itemRespuesta.getCveIdTipoMovimiento() != null && itemRespuesta.getCveIdTipoMovimiento() != null) {
                    TipoMovtoAsegurado movto = new TipoMovtoAsegurado();
                    movto.setIdTipoMvtoAsegurado(itemRespuesta.getCveIdTipoMovimiento().longValue());
                    cgf.setTipoMovtoAsegurado(movto);
                }

                EstadoDerechohabiente estado = new EstadoDerechohabiente();
                estado.setIdEstadoDerechohabiente(idEstado);
                cgf.setEstadoDerechohabiente(estado);

                SubEstadoDerechohabiente sub = new SubEstadoDerechohabiente();
                sub.setIdSubEstadoDerechohabiente(itemRespuesta.getCveSubestadoDerechohabiente().longValue());
                cgf.setSubEstadoDerechohabiente(sub);

                if (itemRespuesta.getFecInicioVigencia() != null && itemRespuesta.getFecInicioVigencia() != null) {
                    fecha = formatter.parse(itemRespuesta.getFecInicioVigencia());
                    cgf.setFechaInicioVigencia(fecha);
                }

                if (itemRespuesta.getFecFinVigencia() != null && itemRespuesta.getFecFinVigencia() != null) {
                    fecha = formatter.parse(itemRespuesta.getFecFinVigencia());
                    cgf.setFechaFinVigencia(fecha);
                }

                if (itemRespuesta.getFecValidezConstancia() != null && itemRespuesta.getFecValidezConstancia() != null) {
                    fecha = formatter.parse(itemRespuesta.getFecValidezConstancia());
                    cgf.setFechaValidezConstancia(fecha);
                }
                if (itemRespuesta.getIndEstudiante() != null) {
                    cgf.setCveEstadoInconsistencia(itemRespuesta.getIndEstudiante());
                    if (itemRespuesta.getIndEstudiante() == 1) {
                        cgf.setEsEstudiante(true);
                        cgf.setCveEstadoInconsistencia(EstadoInconsistenciaVigenciaEnum.ESTUDIANTES.getId());
                    } else {
                        cgf.setEsEstudiante(false);
                    }
                } else {
                    cgf.setEsEstudiante(false);
                    cgf.setCveEstadoInconsistencia(0);
                }

                if (itemRespuesta.getConDerechoSm() != null) {
                    cgf.setConDerechoSm(itemRespuesta.getConDerechoSm());
                }

            } else {
                throw new DerechohabientesWebSserviceException((respuesta != null ? respuesta.getCodigoError() : "") + ", " + (respuesta != null ? respuesta.getMensajeError() : ""));
            }

            cgf.setArticulo82(respuesta.getInfoCabezaGrupoFamiliarVO().getArticulo82().equalsIgnoreCase("SI"));
            cgf.setArticulo83(respuesta.getInfoCabezaGrupoFamiliarVO().getArticulo83().equalsIgnoreCase("SI"));
            cgf.setArticulo84(respuesta.getInfoCabezaGrupoFamiliarVO().getArticulo84().equalsIgnoreCase("SI"));
            cgf.setArticulo85(respuesta.getInfoCabezaGrupoFamiliarVO().getArticulo85().equalsIgnoreCase("SI"));
            cgf.setTiemposEspera(respuesta.getInfoCabezaGrupoFamiliarVO().getTiemposEspera());
            return cgf;
        } catch (Exception e) {
            System.out.println("Ocurrio un errro al consultar el ws de cabeza de grupo TE " + e);
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new DerechohabientesWebSserviceException("Ocurrio un error de comunicacion al intentar recuperar la cabeza de grupo familiar");
            }

            if (e instanceof com.sun.xml.ws.client.ClientTransportException) {
                throw new DerechohabientesWebSserviceException(
                        "Ocurrio un error en el WebService de Vigencia al obtener la informacion del la Cabeza de Grupo Familiar TE");
            }

            throw new DerechohabientesWebSserviceException(e.getMessage());
        }
    }

    private String getRp(Long idPatron, String rp) {

        String registrop = "";

        if (idPatron == null) {
            if (rp != null) {
                registrop = rp;
            } else {
                registrop = "s/p";
            }
        }

        return registrop;
    }

    private SujetoObligado construirPatronSujetoObligado(Long idPatron, String rp, String modalidad) {

        SujetoObligado patron = new SujetoObligado();
        patron.setCveIdSujetoObligado(idPatron);
        patron.setNumeroRegistroPatronal(rp);
        patron.setModalidad(new Modalidad());
        patron.getModalidad().setNumModalidad(modalidad);

        return patron;
    }
}
