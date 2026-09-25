<%@ include file="../../layout/taglibs.jsp" %>



<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/candidatos.js" htmlEscape="true" />"></script>

	
<div class="container">

      <div class="row">
      
    		<h2> Resultados de la b&uacute;squeda&nbsp;</h2>  
      		<p> 
      			Seleccione el registro de la persona que se aproxime m&aacute;s a sus datos de b&uacute;squeda.
      			
      			
      		 </p>





		<table class="table table-hover table-bordered" id="candidatos" style="width:100% !important; font-size:x-small !important;">
	      	
	      		<thead>
	      			<tr>
	      				<td> # </td>
	      				<td> RFC </td>
	      				<td> CURP </td>
	      				<td> Nombre(s) </td>
	      				<td> Primer Apellido </td>
	      				<td> Segundo Apellido </td>
	      				<td> Sexo </td>
	      				<td> Fecha de Nacimiento </td>
	      				<td> Lugar de Nacimiento </td>
	      				
	      				<td> Seleccionar </td>
	      			</tr>
	      		</thead>
	      		<tbody>
	      			<c:forEach items="${candidatos}" var="candidato" varStatus="index">
						
<%-- 						<c:if test="${candidato.probabilidad < 33}"> --%>
<!-- 							<tr class="error"> -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${candidato.probabilidad >= 33 && candidato.probabilidad < 60}"> --%>
<!-- 							<tr class="warning"> -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${candidato.probabilidad >= 60}" > --%>
<!-- 							<tr class="success"> -->
<%-- 						</c:if> --%>
						
						<tr>
							<td> ${candidato.persona.idPersona}  </td>
							<td> ${candidato.persona.rfc}  </td>
							<td> ${candidato.persona.curp}  </td>
	      					
	      					<td> ${candidato.persona.nombre}  </td>
	      					<td> ${candidato.persona.primerApellido}   </td>
	      					<td> ${candidato.persona.segundoApellido}   </td>
	      					<td> ${candidato.persona.sexo.descripcion}   </td>
	      					<td> ${candidato.persona.fechaNacimientoFormateada}   </td>
	      					<td> ${candidato.persona.lugarNacimiento.nombre}   </td>
	      					
	      					
	      					
					<c:if test="${candidato.probabilidad < 33}">
						<td> 
							<form>
							<input type="button" persona="${candidato.persona.idPersona}" 
							  porcentaje="1" class="mboton seleccion" value="Seleccionar" />
							  </form> 
							   
						</td>
					</c:if>
					<c:if test="${candidato.probabilidad >= 33 && candidato.probabilidad < 60}">
						<td> 
<%-- 						<button type="button" persona="${candidato.persona.idPersona}" porcentaje="2" class="btn btn-warning seleccion"> Seleccionar</button>  --%>
							<form>
							<input type="button" persona="${candidato.persona.idPersona}" 
							  porcentaje="2" class="mboton seleccion" value="Seleccionar" />
							  </form>
						</td>
					</c:if>
					<c:if test="${candidato.probabilidad >= 60}">
						<td> 
<%-- 						<button type="button" persona="${candidato.persona.idPersona}" porcentaje="3" class="btn btn-success seleccion"> Seleccionar</button>  --%>
<form>
							<input type="button" persona="${candidato.persona.idPersona}" 
							  porcentaje="3" class="mboton seleccion" value="Seleccionar" />
							  </form>
						</td>
					</c:if>
					
					</tr>
							      			
	      			</c:forEach>
	      		</tbody>
	      	
	      	</table>
	      
      </div>
</br>
</br>
    <footer>
    <div>
    <c:set var="contextpath" value="<%=request.getContextPath()    %>" />
	    <form action="${contextpath}/persona/fisica/ubicar/regresar" method="get">
	    	
	    	
	    	<input type="button" class="mboton" id="cancelar" value="Cancelar"/>
	    	<input type="submit" class="mboton"  value="Regresar"/>
	    	
	<!--     	<button class="mboton" type="button" id="cancelar" > Cancelar </button> -->
	<!--     	<button class="mboton" type="submit" > Regresar </button> -->
	    </form>
    
    </div>
    <div>
	    <!-- Forma de suporte para el envio del candidato seleccionado. -->
	    <c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form:form modelAttribute="fisica" id="forma" method="post" action="${contextpath}/persona/fisica/ubicar/complementar">
	    	<form:hidden path="idPersona" id="idPersona"/>
	    </form:form>
    </div>
    </footer>


	<!-- Modal de advertencia de que el registro no cuenta con una calificacion optima-->
	<div class="modal" id="modalAdvertencia" tabindex="-1" role="dialog"
		aria-labelledby="modalAdvertenciaLabel" aria-hidden="true">
		<div class="modal-header">
			
			<h4 id="modalAdvertenciaLabel"> !Advertencia¡ </h4>
		</div>
		<div class="modal-body">
			    <div class="alert alert-block">
    
    
    El registro seleccionado tiene un <span style="font-weight: bold;" id="msgPorcentajeProbabilidad"></span>  de probabilidad  de coincidencia 
    con los datos proporcionados. 
    <h6> ¿ Esta Ud. seguro de continuar  ?</h6>
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
			<button class="btn btn-default" data-dismiss="modal" aria-hidden="true" id="cancelar">Cancelar</button>
			<button class="btn btn-secondary continuar">Continuar</button>
		</div>
	</div>


</div>