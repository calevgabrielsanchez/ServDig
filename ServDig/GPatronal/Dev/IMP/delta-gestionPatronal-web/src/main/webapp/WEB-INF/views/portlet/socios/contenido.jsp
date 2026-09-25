<!-- JSP Contenido del Portlet de Socios. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/portlet/socios/sociosContenidoPortlet.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<input id="idPersonaHidden" type="hidden" value="${socio.idPersonaMoralPatron}"/>
		
<div class="contenedor">
			
	<div id="socio" style="height: 100%">				
		<form:form modelAttribute="socio" id="sociosFormPaginar">
				<form:hidden path="idPersonaMoralPatron" id="idPersonaMoralPatron" />
		</form:form>				
		<table style="width: 100%;">
			<tr>
				<td>
					<div id="lista" style="width: 100%;">
						<div id="tabla">
							<table id="tbSocios" style="width: 100%;"
								class="table table-striped table-bordered" cellpadding="0"
								cellspacing="0" border="0">
									<thead></thead><tbody style="width: 100%;"></tbody>
							</table>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>

</div>