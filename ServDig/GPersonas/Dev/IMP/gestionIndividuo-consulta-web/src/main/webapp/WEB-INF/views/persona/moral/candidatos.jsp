<%@ include file="../../layout/taglibs.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/moral/busqueda/candidatos.js" htmlEscape="true" />"></script>
<div class="container">
	<div class="row">
    	<h2> Resultados de la b&uacute;squeda&nbsp;</h2>  
      	<p>
      	   Los resultados de su b&uacute;squeda&nbsp tienen un % de probabilidad de acuerdo a 
      	   los elementos que coinciden con los datos proporcionados.
      	</p>

		<table class="table table-hover table-bordered" id="candidatos">
      		<thead>
      			<tr>
      				<td> # </td>
      				<td> Raz&oacute;n Social </td>
      				<td> RFC </td>
      				<td> % </td>
      				<td> Seleccionar </td>
      			</tr>
      		</thead>
      		<tbody>
      			<c:forEach items="${candidatos}" var="candidato" varStatus="index">
					<c:if test="${candidato.probabilidad < 33}">
						<tr class="error">
					</c:if>
					<c:if test="${candidato.probabilidad >= 33 && candidato.probabilidad < 60}">
						<tr class="warning">
					</c:if>
					<c:if test="${candidato.probabilidad >= 60}" >
						<tr class="success">
					</c:if>
						<td> ${candidato.persona.idPersona}  </td>
      					<td> ${candidato.persona.razonSocial}  </td>
      					<td> ${candidato.persona.rfc}  </td>
      					<td> ${candidato.probabilidad}  </td>
      					
				<c:if test="${candidato.probabilidad < 33}">
					<td> <button type="button" persona="${candidato.persona.idPersona}" porcentaje="1" class="btn btn-danger seleccion"> Seleccionar</button> </td>
				</c:if>
				<c:if test="${candidato.probabilidad >= 33 && candidato.probabilidad < 60}">
					<td> <button type="button" persona="${candidato.persona.idPersona}" porcentaje="2" class="btn btn-warning seleccion"> Seleccionar</button> </td>
				</c:if>
				<c:if test="${candidato.probabilidad >= 60}">
					<td> <button type="button" persona="${candidato.persona.idPersona}" porcentaje="3" class="btn btn-success seleccion"> Seleccionar</button> </td>
				</c:if>
				</tr>
						      			
      			</c:forEach>
      		</tbody>
		</table>
      </div>

    <footer>
	    <c:set var="contextpath" value="<%=request.getContextPath()%>" />
	    <form action="${contextpath}/persona/moral/ubicar" method="get">
	    	<button class="btn btn-secondary" type="submit" > Regresar </button>
	    </form>
	    
	    <!-- Forma de suporte para el envio del candidato seleccionado. -->
	    <c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form:form modelAttribute="moral" id="forma" method="post" action="${contextpath}/persona/moral/ubicar/complementar">
	    	<form:hidden path="idPersona" id="idPersona"/>
	    </form:form>
    </footer>

	<!-- Modal de advertencia de que el registro no cuenta con una calificacion optima-->
	<div class="modal" id="modalAdvertencia" tabindex="-1" role="dialog"
		aria-labelledby="modalAdvertenciaLabel" aria-hidden="true">
		<div class="modal-header">
			<h4 id="modalAdvertenciaLabel"> !Advertencia¡ </h4>
		</div>
		<div class="modal-body">
			<div class="alert alert-block">
				El registro seleccionado no tiene un porcentaje de probabilidad alto de coincidencia 
				con los datos proporcionados. 
	    		<h6> ¿ Esta Ud. seguro de continuar ?</h6>
	    	</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancelar</button>
			<button class="btn btn-secondary continuar">Continuar</button>
		</div>
	</div>
	
	<!-- Modal de confirmación del registro para complementar su informacion-->
	<div class="modal" id="modalComplementar" tabindex="-1" role="dialog" aria-labelledby="modalComplementarLabel" aria-hidden="true">
		<div class="modal-header">
			<h4 id="modalComplementarLabel">Mensaje de Sistema</h4>
		</div>
		<div class="modal-body">
			<div class="alert alert-success">
				Se determinara si la informaci&oacute;n del registro seleccionado es vigente
				con el de las entidades externas ( RENAPO y SAT).
				<h6>¿ Esta Ud. seguro de continuar ?</h6>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancelar</button>
			<button class="btn btn-secondary continuar">Continuar</button>
		</div>
	</div>


</div>