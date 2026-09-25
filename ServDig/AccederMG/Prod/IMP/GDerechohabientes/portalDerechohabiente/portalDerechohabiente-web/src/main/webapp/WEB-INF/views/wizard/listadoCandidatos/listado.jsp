<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/listadoCandidatos/listadoCandidatos.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/correccionDerechohabiente/integracionICACorreccion.js" htmlEscape="true" />"></script>

<jsp:include page="../../common/listadoCandidatosCommon.jsp"></jsp:include>
<br>
<div class="col-sm-12">
	<div class="pull-right">
		<button class="btn btn-default" id="cerrarWizard">Cerrar</button>
		<c:if test="${not empty grupoFamiliar}">
		<button class="btn btn-primary" id="aceptar">Aceptar</button>
		</c:if>
	</div>
</div>

<div id="dialogICACorreccionDatosWeb"></div>

<script type = "text/JavaScript">
	PADRES = <%=ParentescoEnum.PADRES.getId()%>;
	HIJOS = <%=ParentescoEnum.HIJOS.getId()%>;
	CONYUGE = <%=ParentescoEnum.CONYUGE.getId()%>;
	CONCUBINARIO = <%=ParentescoEnum.CONCUBINARIO.getId()%>;
	ASEGURADO = <%=ParentescoEnum.ASEGURADO.getId()%>;
	PENSIONADO = <%=ParentescoEnum.PENSIONADO.getId()%>;
	CONCUBINA = <%=ParentescoEnum.CONCUBINA.getId()%>;
	MADRES = <%=ParentescoEnum.MADRE.getId()%>;

</script>