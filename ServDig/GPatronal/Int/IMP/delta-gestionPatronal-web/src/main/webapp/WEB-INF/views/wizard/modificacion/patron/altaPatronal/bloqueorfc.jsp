<%@ include file="../../../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/comprobacionICA.js" htmlEscape="true" />"></script>

<script>
	<c:if test="${idSolicitud != null}">
	var idSolicitud = ${idSolicitud};
	</c:if>
	<c:if test="${idSolicitud == null}">
	var idSolicitud = 0;
	</c:if>
</script>

<style>
	.dataTables_wrapper table thead {
		display: none;
	}
</style>

<div class="contenedor col-sm-12">

	<div class="contenido row">
		<div class="col-sm-12">
		<form:form modelAttribute="icaDatosAux" id="forma" method="post">

	
						<div class="alert alert-danger">

								Para realizar el alta patronal que requiere, deber&aacute; acudir a la subdelegaci&oacute;n que le corresponde con la documentaci&oacute;n que el tr&aacute;mite le solicita. <br><br>
								
						</div>


            <div class="pie">
                <center>
                    <div class="opciones">
                        <button class="btn btn-primary" id="cerrarWizard">Cerrar</button>
                    </div>
                    <div class="controles"></div>
                </center>
            </div>

		</form:form>
		</div>
	</div>
</div>
