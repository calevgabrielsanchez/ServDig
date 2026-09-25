<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<style>
div#cuestionarioContainer {
	margin: 0px !important;
}
</style>

<script type="text/javascript"
  src="/${appRootCuestionario}/static/resources/js/delta/cuestionario/CuestionarioCtrl.js"></script>
<script type="text/javascript"
  src="<spring:url value="/static/resources/js/wizard/individual/cuestionario-ivro-individual.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="contenedor col-sm-12" style="min-height: 400px;">
  <div class="contenido row">
    <c:if test="${ not esRenovacion and not esExtemporanea }">
    <jsp:include page="pasosIndividual.jsp">
      <jsp:param name="paso" value="4" />
    </jsp:include>
    </c:if>
    <div class="col-sm-12">
      <c:if test="${not empty error}">
        <div style="min-height: 200px;">
          <div class="alert alert-danger">${error}</div>
        </div>
      </c:if>


      <c:if test="${empty error}">
        <div id="cuestionarioContainer"></div>
        <div class="alert alert-info" style="text-align: center;">
          <form:form id="aceptarTerminosCondiciones">
            <label> <input id="chkTCCuestionario" name="aceptarTC" type="checkbox" /> <span> He le&iacute;do y acepto los
                <a id="linkTCCuestionario" href="#" class="alert-link">T&eacute;rminos y condiciones</a>
            </span>
            </label>
          </form:form>
          <%@ include file="../../comunes/terminosCondicionesCuestionario.jsp"%>
        </div>
      </c:if>
    </div>
  </div>

  <form:form id="formCuestionarioIvro" modelAttribute="respuestasCuestionario" method="post"
    action="${contextpath}/wizard/individual/validaCuestionario"></form:form>
  <div class="pie row">
    <div class="opciones col-sm-6"></div>
    <div class="controles col-sm-6">
      <div class="pull-right">
        <c:if test="${empty error}">
          <button class="btn btn-default" id="cerrarWizard">Cerrar</button>
          <a id="siguienteCuestionario" class="btn btn-primary" style="display: none;"> <i class="glyphicon glyphicon-step-forward"></i>
            Siguiente
          </a>
        </c:if>
        <c:if test="${not empty error}">
          <button class="btn btn-default" id="cerrarWizardError">Cerrar</button>
        </c:if>
      </div>
    </div>
  </div>
</div>
