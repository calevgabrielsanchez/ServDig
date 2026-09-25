<%@ include file="taglibs.jsp" %>

<div id="portal">
	<div class="row contenedor" style="width: 100% !important">
		<div  id="mediosdeacceso" class="cell portal_medios_acceso">
			
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div id="registronuevousuario" class="row">
				<h2> Vista preliminar del sistema </h2>
		    	<div style="border: thin; border-bottom-color: red; border-bottom-style: solid;">&nbsp;</div>
		        <p style="font-size: .9em;">
		        	Esta p&aacute;gina se desarroll&oacute; con la intenci&oacute;n de acceder directamente a las funcionalidades
		        	sin pasar por ning&uacute;n portal, y s&oacute;lo se presenta para
		        	el ambiente de desarrollo y no aparecer&aacute; en ambientes productivos
		        </p>
		        <br />
			</div>
		</div>
		
		<div id="contenidodeportal" class="cell portal_informacion">
<%-- 	        <div id="imagen_portal">
	        	<img  src="<spring:url value="/static/resources/imagenes/tramites.jpg" htmlEscape="true" />" title="Portal IMSS"  />
	        </div> --%>
	        
			<h2> M&oacute;dulo de Gesti&oacute;n de Personas </h2>
	        <p style="font-size: .9em;"> 
	        	Mapa de funciones que se ofrecen en el sistema de Gesti&oacute;n de Personas
	        </p>
	        
	       	<br />
	        <div id="areas" >
	  			<div class="area">
				    <div style="background:white; max-width:600px; max-height:200px;" class="cuadro margen_derecho">
				      
				    	<div class="titulo_seccion">
				    		M&oacute;dulo de Gesti&oacute;n de Personas
				    	</div>
						
				    	<div class="lista_tipo_69">
					        <ul>
					          <li><a href="${contextpath}/persona/tramites">Tr&aacute;mite de alta de personas fisicas y morales (rol internet)</a></li>
					          <li><a href="${contextpath}/persona/tramites/ventanilla">Tr&aacute;mite de alta de personas fisicas y morales (rol ventanilla)</a></li>
					          <li><a href="${contextpath}/persona/fisica/busqueda">B&uacute;squeda de personas fisicas</a></li>
					          <li><a href="${contextpath}/persona/moral/busqueda">B&uacute;squeda de personas morales</a></li>
					          <li><a href="${contextpath}/solicitud/validar" >Validaci&oacute;n de Tr&aacute;mites de Solicitud</a></li>
					          <li><a href="${contextpath}/solicitud/reporte-comprobante">Imprimir Reporte Comprobante de Solicitud</a></li>
					          <li><a href="${contextpath}/persona/fisica/registro-masivo">Carga masiva personas f&iacute;sicas</a></li>
					          <li><a href="${contextpath}/persona/moral/registro-masivo">Carga masiva personas morales</a></li>
					        </ul>
				  		</div>
				  		<br /><br /><br />
				    </div>
				    
	  			</div>
	        </div>
	        
		</div>
	</div>
</div>