<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>
	
<div class="menu_holder" align="right">
	
	
	<div id="dgCerrarSesion" title="Cerrar Sesion" >
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>
			 ¿ Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		</br>
		 
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>


	<!--Inicio Menu -->
    <div class="menu_holder">
      <ul class="menu">
        <li><a href="http://www.imss.gob.mx">Inicio</a></li>
        <li><a href="#">Tr&aacute;mites</a> 
          <ul class="sub-menu">
            <li class="no-border">
            <a href="/gestionIndividuo-web/persona/fisica/registro">Registro de Persona F&iacute;sica</a></li>   
            <li><a href="/gestionIndividuo-web/persona/moral/registro">Registro de Persona Moral</a></li>
            <li><a href="/gestionIndividuo-web/persona/fisica/busqueda">Busqueda de Persona F&iacute;sica</a></li>
            <li><a href="/gestionIndividuo-web/persona/moral/busqueda">Busqueda de Persona Moral</a></li>
            <li><a href="/gestionIndividuo-web/persona/fisica/registro-masivo">Registro Masivo</a></li>
          </ul>
        </li>
        <li><a href="#">Domicilios</a> 
          <ul class="sub-menu">
            <li class="no-border">
            <a href="<%= request.getContextPath()%>/domicilio/registro">Registro Domicilio</a></li>   
          </ul>
        </li>
         <li><a href="#">Catalogos</a> 
          <ul class="sub-menu">
             <li><a href="#">General</a> 
          	<ul class="sub-menu">
			
			<li class="no-border"><a href="/catalogos-web-internet/catalogo/accderechoservicio.do">accderechoservicio</a></li>
			<li><a href="/catalogos-web-internet/catalogo/acchospital.do">acchospital</a></li>
			<li><a href="/catalogos-web-internet/catalogo/accnivelatencion.do">accnivelatencion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/acctpunidad.do">acctpunidad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/accunidadmedica.do">accunidadmedica</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicaccion.do">dicaccion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicactividadusuario.do">dicactividadusuario</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicareageografica.do">dicareageografica</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicareasistema.do">dicareasistema</a></li>
			<li><a href="/catalogos-web-internet/catalogo/diccalidadcaracmigrat.do">diccalidadcaracmigrat</a></li>
			<li><a href="/catalogos-web-internet/catalogo/diccausa.do">diccausa</a></li>
			<li><a href="/catalogos-web-internet/catalogo/diccausabajaaseg.do">diccausabajaaseg</a></li>
			<li><a href="/catalogos-web-internet/catalogo/diccausaincapacidad.do">diccausaincapacidad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicciclo.do">dicciclo</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicclaseprima.do">dicclaseprima</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicclavepresupuestal.do">dicclavepresupuestal</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicconvenio.do">dicconvenio</a></li>
			</ul>
			</li>
			
			 <li><a href="#">Patr&oacute;n</a> 
          	<ul class="sub-menu">
			<li><a href="/catalogos-web-internet/catalogo/diccuestionariomedico.do">diccuestionariomedico</a></li>
			<li><a href="/catalogos-web-internet/catalogo/diccuestionariopregunta.do">diccuestionariopregunta</a></li>
			<li><a href="/catalogos-web-internet/catalogo/diccuestionariorespuesta.do">diccuestionariorespuesta</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicdelegacion.do">dicdelegacion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicdivision.do">dicdivision</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicdocumentacion.do">dicdocumentacion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicestadocivil.do">dicestadocivil</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicestatussolicitud.do">dicestatussolicitud</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicfacultad.do">dicfacultad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicfraccion.do">dicfraccion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicgradoparentesco.do">dicgradoparentesco</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicgrupo.do">dicgrupo</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicguarderia.do">dicguarderia</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicley.do">dicley</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmandato.do">dicmandato</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmedico.do">dicmedico</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmediodistribucion.do">dicmediodistribucion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmenu.do">dicmenu</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmenuaccion.do">dicmenuaccion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmodalidad.do">dicmodalidad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmodulo.do">dicmodulo</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicmunicipioimss.do">dicmunicipioimss</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicniveleducativo.do">dicniveleducativo</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicorigmovtopatsujoblig.do">dicorigmovtopatsujoblig</a></li>
			</ul>
			</li>
			<li><a href="#">Asegurados</a> 
          	<ul class="sub-menu">
			<li><a href="/catalogos-web-internet/catalogo/dicorigenmovtoasegurado.do">dicorigenmovtoasegurado</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicpais.do">dicpais</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicparentesco.do">dicparentesco</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicperfilusuario.do">dicperfilusuario</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicprestacion.do">dicprestacion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicrangocuotassf.do">dicrangocuotassf</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicregimen.do">dicregimen</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicservicio.do">dicservicio</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicsexo.do">dicsexo</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicsubdelegacion.do">dicsubdelegacion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipoambito.do">dictipoambito</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipoasegurado.do">dictipoasegurado</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipocombustible.do">dictipocombustible</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipocontratacion.do">dictipocontratacion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipocontribucion.do">dictipocontribucion</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipoconvenio.do">dictipoconvenio</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipodocidentidad.do">dictipodocidentidad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipodomicilio.do">dictipodomicilio</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipofacultad.do">dictipofacultad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipoforma.do">dictipoforma</a></li>
			</ul>
			</li>
			
			<li><a href="#">Asegurados</a> 
          	<ul class="sub-menu">
			<li><a href="/catalogos-web-internet/catalogo/dictipoincapacidad.do">dictipoincapacidad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipoindpatsujoblig.do">dictipoindpatsujoblig</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipointeresado.do">dictipointeresado</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipojornada.do">dictipojornada</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipomaquinariaequipo.do">dictipomaquinariaequipo</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipomovtoasegurado.do">dictipomovtoasegurado</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipomovtopatsujoblig.do">dictipomovtopatsujoblig</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipopago.do">dictipopago</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipoparentesco.do">dictipoparentesco</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipopoder.do">dictipopoder</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictiporegpatron.do">dictiporegpatron</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictiporetiro.do">dictiporetiro</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictiposalario.do">dictiposalario</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictiposeguro.do">dictiposeguro</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictiposemana.do">dictiposemana</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictiposociedad.do">dictiposociedad</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictipotrabajador.do">dictipotrabajador</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dictramite.do">dictramite</a></li>
			<li><a href="/catalogos-web-internet/catalogo/dicumf.do">dicumf</a></li>
			<li><a href="/catalogos-web-internet/catalogo/ditleyseguro.do">ditleyseguro</a></li>
			<li><a href="/catalogos-web-internet/catalogo/ditparametroarea.do">ditparametroarea</a></li>
			<li><a href="/catalogos-web-internet/catalogo/ditsalariogeneral.do">ditsalariogeneral</a></li>
			<li><a href="/catalogos-web-internet/catalogo/drtparametroinpc.do">drtparametroinpc</a></li>
          </ul>
        </li>
      </ul>
    </div>
    <!-- Termino Menu -->
	

	
</div>



