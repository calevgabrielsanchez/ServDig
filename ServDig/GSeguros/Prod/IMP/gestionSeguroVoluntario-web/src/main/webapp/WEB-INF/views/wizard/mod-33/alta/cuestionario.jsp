<%@ include file="../../../general/taglibs.jsp"%>

<style>
    div#cuestionarioContainer {
        margin: 0px !important;
    }
</style>

<script type="text/javascript" src="/${appRootCuestionario}/static/resources/js/delta/cuestionario/CuestionarioCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/mod-33/alta/cuestionario.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="contenedor col-sm-12">
    <div class="contenido row">
        <div class="col-sm-12">
            <div class="titulo">
                <c:if test="${enRenovacion and not extemporanea}">
                    <span>Responder antecedentes de enfermedades</span>
                </c:if>

                <c:if test="${!enRenovacion or extemporanea}">
                    <span>Paso 2 de 5: Responder antecedentes de enfermedades</span>
                </c:if>

            </div>
            <c:choose>
                <c:when test="${not empty error}">
                    <div class="alert alert-danger">
                        <span>${error}</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="cuestionarioMedicoContainer" style="min-height: 400px;"></div>

                    <form:form id="nextStepForm" modelAttribute="respuestasCuestionario"
                               action="${contextPath}/wizard/seguroFamiliar/alta/validarCuestionario">
                    </form:form>

                    <form id="quitarUltimoIntegranteForm" 
                          action="${contextPath}/wizard/seguroFamiliar/alta/quitarUltimoIntegrante">
                    </form>

                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="pie row">
        <div class="opciones col-sm-6"></div>
        <div class="controles col-sm-6">
            <div class="pull-right">
                <c:choose>
                    <c:when test="${enRenovacion}">
						<c:choose>
							<c:when test="${extemporanea}">
								<button id="cerrar" class="btn btn-default">Cancelar</button>
							</c:when>
							<c:otherwise>
                        <button id="cerrarRenovaion" class="btn btn-default">Cancelar</button>
								
							</c:otherwise>
						</c:choose>
                    </c:when>
                    <c:otherwise>
                        <button id="cerrar" class="btn btn-default">Cancelar</button>
                    </c:otherwise>
                </c:choose>
                <c:if test="${empty error}">
                    <button id="siguientePaso" class="btn btn-primary">
                        <i class="glyphicon glyphicon-step-forward"></i>
                        Continuar
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</div>
