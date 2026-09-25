<%@ include file="taglibs.jsp" %>

<div id="portal">
	<div class="row contenedor" style="width: 100% !important">
		<div  id="mediosdeacceso" class="cell portal_medios_acceso">
			
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div id="registronuevousuario" class="row">
				<h2> <spring:message code="label.portal.informacion.titulo" /> </h2>
		    	<div style="border: thin; border-bottom-color: red; border-bottom-style: solid;">&nbsp;</div>
		        <p style="font-size: .9em;">
		        	<spring:message code="label.portal.informacion.descripcion" />
		        </p>
		        <br />

			</div>
		</div>
		
		<div id="contenidodeportal" class="cell portal_informacion">
			<h2> <spring:message code="label.portal.titulo" /> </h2>
	        <p style="font-size: .9em;"> 
	        	<spring:message code="label.portal.subtitulo" />
	        </p>
	        
	        <div id="imagen_portal">
	        	<img  src="<spring:url value="/static/resources/imagenes/tramites.jpg" htmlEscape="true" />" title="Portal IMSS"  />
	        </div>
	       	<br />
	        <div id="areas" >
	  			<div class="area">
				    <div style="background:white; max-width:237px; max-height:170px;" class="cuadro K margen_derecho">
				      
				    	<div class="titulo_seccion">
				    		REGISTRO DE PERSONAS
				    	</div>
						
				    	<div class="lista_tipo_69">
					        <ul>
								<li><a href="${contextpath}/persona/tramites/agregar/fisica">Persona F&iacute;sica</a></li>
					        	<li><a href="${contextpath}/persona/tramites/agregar/moral">Persona Moral</a></li>
					        </ul>
				  		</div>
				    </div>
	  			</div>
	  			
	        </div>
	        
		</div>
	</div>
	
</div>