<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script>
	var tipoTramiteEnum = {
		'ALTA_SRT' : <%= TipoTramiteEnum.ALTA_SRT.getCodigo() %>,
		'ALTA_SRT_PM' : <%= TipoTramiteEnum.ALTA_SRT_PM.getCodigo() %>,
		'ACTUALIZACION_SOCIO' : <%= TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo() %>,
		'BAJA_SOCIO' : <%= TipoTramiteEnum.BAJA_SOCIO.getCodigo() %>,
		'ACTUALIZACION_REPRESENTANTE_LEGAL' : <%= TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo() %>,
		'BAJA_REPRESENTANTE_LEGAL' : <%= TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo() %>,
		'RECUPERACION_REGISTRO_PATRONAL' : <%= TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL.getCodigo() %>,
		'COMPRA_SEGURO_INDIVIDUAL' : <%= TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo() %>,
		'COMPRA_SEGURO_DOMESTICO' : <%= TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo() %>,
		'CARTA_NO_ADEUDO' : <%= TipoTramiteEnum.CARTA_NO_ADEUDO.getCodigo() %>,
		'ACTUALIZACION_DATOS_GENERALES' : <%= TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo() %>
	};
</script>





