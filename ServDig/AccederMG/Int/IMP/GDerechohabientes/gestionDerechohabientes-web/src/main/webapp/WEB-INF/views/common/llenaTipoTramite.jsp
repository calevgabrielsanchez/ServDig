<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type = "text/JavaScript">
	TIPO_TRAMITE_ENUM = {};
	<% for(TipoTramiteEnum tipoTramiteEnum : TipoTramiteEnum.values() ){ %>
		TIPO_TRAMITE_ENUM['<%= tipoTramiteEnum %>'] = <%= tipoTramiteEnum.getCodigo() %>;
	<%}%>
</script>


<script type = "text/JavaScript">
	var REGISTRO_PERSONA = 1;
	var REGISTRO_DERECHOHABIENTE = 2;
	var REGISTRO_CONCUBINARIO = <%=TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo()%>;
	var REGISTRO_PADRES = <%=TipoTramiteEnum.REGISTRO_PADRES.getCodigo()%>;
	var REGISTRO_ASEGURADO = <%=TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo()%>;
	var REGISTRO_PENSIONADO = <%=TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo()%>;
	var REGISTRO_CONCUBINA = <%=TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo()%>;
	var REGISTRO_CONYUGE = <%=TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo()%>;
	var REGISTRO_HIJOS = <%=TipoTramiteEnum.REGISTRO_PADRES.getCodigo()%>;
	
	var BAJA_DEFUNCION = <%=TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo()%>;
	var BAJA_CONCUBINATO = <%=TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO.getCodigo()%>;
	var BAJA_DIVORCIO = <%=TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO.getCodigo()%>;
	var BAJA_DEPENDENCIA = <%=TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo()%>;

	var CORRECCION_DATOS_DERECHOHABIENTE = <%=TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo()%>;
	var CAMBIO_UMF = <%=TipoTramiteEnum.CAMBIO_CLINICA.getCodigo()%>;
	var CAMBIO_MEDICO = <%=TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO.getCodigo()%>;
	var AUTORIZACION_CIRCUNSCRIPCION = <%=TipoTramiteEnum.AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo()%>;
	var SUSPENSION_CIRCUNSCRIPCION = <%=TipoTramiteEnum.SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo()%>;
	var ASIGNACION_MEDICO = <%=TipoTramiteEnum.ASIGNACION_CONSULTORIO_TURNO_MEDICO.getCodigo()%>;
	
	var PRORROGA_ACUERDOS = <%=TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT.getCodigo()%>;
	var PRORROGA_LAUDO = <%=TipoTramiteEnum.PRORROGA_POR_LAUDO.getCodigo()%>;
	var PRORROGA_PERMANENTE = <%=TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE.getCodigo()%>;
	var PRORROGA_TEMPORAL = <%=TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo()%>;
	var PRORROGA_ENFERMEDAD= <%=TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo()%>;
	var PRORROGA_ESTUDIOS = <%=TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo()%>;
	var PRORROGA_INVALIDEZ = 11;
	var PRORROGA_OBSTETRICOS = <%=TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo()%>;
	
	var SOLICITUD_REGISTRO = 1001;
	var REGISTRO_DERECHOHABIENTES_CU=1002;
	var REGISTRO_DERECHOHABIENTES_CC=1003;
	var CANCELA_REGISTRO = 1000;

	var ACTUALIZACION_DOMICILIO_PARTICULAR = <%=TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo()%>;
</script>