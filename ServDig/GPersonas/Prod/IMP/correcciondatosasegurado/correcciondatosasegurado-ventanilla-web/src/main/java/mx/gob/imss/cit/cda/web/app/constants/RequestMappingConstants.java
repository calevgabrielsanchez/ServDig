/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.constants;

/**
 *
 * @author antonio
 */
public interface RequestMappingConstants {  
  
  String READ_USER_PROFILE = "/userProfile.do";
  
  /* Mappings para funcionalidad del Responsable*/
  String ATENCION_RESPONSABLE = "/atencionResponsable"; 
  
  String READ_TRAMITES_ASIGNADOS = ATENCION_RESPONSABLE + "/tramitesAsignados.do";
  String READ_HISTORICO_SOLICITUDES = ATENCION_RESPONSABLE + "/historicoSolicitudes.do";
  
  String READ_SOLICITUD = ATENCION_RESPONSABLE + "/obtenerSolicitud";
  String RESPONSABLE_CONFIRMAR_SOLICITUD = ATENCION_RESPONSABLE + "/confirmarSolicitud";
  String RESPONSABLE_CANCELAR_SOLICITUD = ATENCION_RESPONSABLE + "/cancelarSolicitud";
  
  String UPDATE_TIPO_CORRECCION_SOLICITUD = ATENCION_RESPONSABLE + "/actualizarTipoCorreccion";
  String READ_NSS_SOLICITUD = ATENCION_RESPONSABLE + "/obtenerNssSolicitud";
  String READ_BITACORA_ESTATUS = ATENCION_RESPONSABLE + "/obtenerEstatus";
  
  String SOLICITAR_INFORMACION_RESPONSABLE = ATENCION_RESPONSABLE + "/solicitarInformacion.do";
  
  String REGISTRO_SOLICITUD_RESPONSABLE = "/wizard/correccionDatosAsegurado";
  String REGISTRO_SOLICITUD_RESPONSABLEE = "correccionDatosAseguradoNSS.do";
  String GUARDARDATOSNSSACTUALIZADOS = "guardardoDatosAseguradoNSS.do";
  
  String RESPONSABLE_SEGUIMIENTO = "/responsableSeguimiento";
  
  /* Mappings para funcionalidad del Autorizador*/
  String ATENCION_AUTORIZADOR = "/atencionAutorizador";
  
  String READ_TRAMITES_ASIGNADOS_AUTORIZADOR = ATENCION_AUTORIZADOR + "/tramitesAsignados.do";
  String READ_HISTORICO_SOLICITUDES_AUTORIZADOR = ATENCION_AUTORIZADOR + "/historicoSolicitudes.do";
  
  String UPDATE_REASIGNAR_RESPONSABLE = ATENCION_AUTORIZADOR + "/reasignarResponsable.do";
  String READ_RESPONSABLES = ATENCION_AUTORIZADOR + "/obtenerResponsables.do";
  
  String RECHAZAR_SOLICITUD = ATENCION_AUTORIZADOR + "/rechazarSolicitud.do";
  String SOLICITAR_INFORMACION = ATENCION_AUTORIZADOR + "/solicitarInformacion.do";
  
  String AUTORIZAR_SOLICITUD = ATENCION_AUTORIZADOR + "/autorizarSolicitud.do";
  
  String READ_DOCUMENTO = ATENCION_AUTORIZADOR + "/obtenerDocumento/{idPersona}/{folio}/{extension}/{nombreArchivo}/{idDocBoveda}";
  String READ_BITACORA_AUTORIZADOR = ATENCION_AUTORIZADOR + "/obtenerEstatus";
  String GENERAR_CERTIFICACION = ATENCION_AUTORIZADOR + "/generarCertificacion.do";
  String AUTORIZADOR_CANCELAR_SOLICITUD = ATENCION_AUTORIZADOR + "/cancelarSolicitud";
  
  String VIEW_RECURSO_NO_DISPONIBLE = "recursoNoDisponible";
  
  /* Mappings para funcionalidad de combos Autorizador y Responsable*/
  String READ_COMBO = ATENCION_AUTORIZADOR + "/obtenerCombo.do";
  String READ_COMBO_ORIGEN = ATENCION_AUTORIZADOR + "/obtenerComboOrigen.do";
  String READ_COMBO_TIPO_TRAMITE = ATENCION_AUTORIZADOR + "/obtenerComboTipoTramite.do";
  String READ_COMBO_RESPONSABLES = ATENCION_AUTORIZADOR + "/obtenerComboResponsables.do";
  String READ_COMBO_AUTORIZADORES= ATENCION_AUTORIZADOR + "/obtenerComboAutorizadores.do";
  String REGISTRO_DOCUMENTOS_PROBATORIOS = "/documentosProbatorios";
  String RESPONSABLE_DATOBENEFICIARIOS = "/datosBeneficiarios";
  
  /* Mappings para funcionalidad de Autorizador y Responsable*/
  String UPDATE_CORREO_ASEGURADO = ATENCION_AUTORIZADOR + "/capturarCorreoAsegurado.do";

  String CONSULTA_REPORTES="wizard/consultarReporte";
  String VIEW_REPORTES = "/vistaReportes";
  
  String ORIGEN_COMBO = VIEW_REPORTES + "/obtenerOrigenCombo.do";
  
  String TIPO_TRAMITE_COMBO = VIEW_REPORTES + "/obtenerTipoTramiteCombo.do";
  
  String DELEGACION_COMBO = VIEW_REPORTES + "/obtenerDelegacionCombo.do";
  
  String SUBDELEGACION_COMBO = VIEW_REPORTES + "/obtenerSubdelegacionCombo.do";
  
  String AUTORIZO_COMBO = VIEW_REPORTES + "/obtenerAutorizoCombo.do";
  
  String RESPONSABLE_COMBO = VIEW_REPORTES + "/obtenerResponsableCombo.do";
  
  String VARIABLE_COMBO = VIEW_REPORTES + "/obtenerVariables.do";
    
  String  READ_TRAMITES_REPORTES= VIEW_REPORTES + "/tramitesReportes.do";
  
  String READ_VARIABLE_ELEGIDA= VIEW_REPORTES + "/variableReporte.do";
  
  String READ_GENERA_PDF= VIEW_REPORTES + "/generarPDF.do";
  
  String READ_GENERA_XLS= VIEW_REPORTES + "/generarXLS.do";
  
  String READ_CUENTA_INDIVIDUAL = ATENCION_RESPONSABLE + "/cuentaIndividual";
  String READ_CUENTA_INDIVIDUAL_CERTIFICADOR = ATENCION_RESPONSABLE + "/cuentaIndividual/inicio.do";
}