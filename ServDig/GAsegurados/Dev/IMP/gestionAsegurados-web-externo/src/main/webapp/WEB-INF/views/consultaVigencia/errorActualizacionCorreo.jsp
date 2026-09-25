<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="baseHost" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/" />
<c:set var="semanasCotizadasUrl" value="${baseHost}semanascotizadas-web/" />

<script type="text/javascript">
  history.go(1);
  tipoTramite = '${tramite}';

	$(document).ready(initEvent);
	
    function redireccionar() {
        var origenExterno = document.getElementById('origenExterno').value;
        console.log('el valor de origenExterno es: ', origenExterno);
        if (origenExterno === 'true') {
            window.location.href = '${semanasCotizadasUrl}';
        } else {
            window.location.href = '${contextpath}/vigencia';
        }
    }
	
</script>

<input type="hidden" id="origenExterno" value="${origenExterno}" />

<div class="contenedor">

	<jsp:include page="encabezadoActualizacionCorreo.jsp">
		<jsp:param name="paso" value="1" />
	</jsp:include>

	<c:if test="${not empty fisica.errorFormGeneral}">
		<div class="alert alert-danger">${fisica.errorFormGeneral}</div>
	</c:if>

	<div class="row" >
		<div class="col-sm-12 text-right">
			<button class="btn btn-primary salir" id="finalizaTramite"
				onclick="redireccionar()">
				<spring:message code="label.btn.regresar" />
			</button>
		</div>
	</div>

	<div class="alert alert-info" style="margin-top: 20px">
		<p>
			<strong>Aviso de privacidad simplificado</strong>
		</p>
		El Instituto Mexicano del Seguro Social (IMSS) es responsable del
		tratamiento de los datos personales que nos proporciones, los cuales
		ser&aacute;n protegidos conforme a lo dispuesto por la Ley General de
		Protecci&oacute;n de Datos Personales en Posesi&oacute;n de Sujetos
		Obligados. Los datos personales que se recaben ser&aacute;n utilizados
		para actualizar tu correo electr&oacute;nico el cual es requisito
		indispensable para obtener en l&iacute;nea tu Constancia de Vigencia
		de Derechos para recibir servicio m&eacute;dico ante el IMSS,
		tr&aacute;mite con Homoclave IMSS-02-020-B y tu Constancia de semanas
		cotizadas en el IMSS, tr&aacute;mite con Homoclave IMSS-02-025. Si
		deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s
		consultar en la siguiente direcci&oacute;n electr&oacute;nica liga:<a
			href="https://www.gob.mx/privacidadintegral" target="_blank">https://www.gob.mx/privacidadintegral</a>
	</div>



</div>