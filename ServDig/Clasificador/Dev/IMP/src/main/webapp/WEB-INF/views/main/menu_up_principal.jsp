<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<div class="header_top ">
			
			<div class="top_version cell">
				<span> Versi&oacute;n</span>:
				<span> 2.0.1</span>
			</div>
			
			<div class="top_nav cell">
				<ul>
					<li><a href="http://www.imss.gob.mx" title="Portal IMSS">Visita 
					el portal oficial del Instituto Mexicano del Seguro Social
					</a></li>
				</ul>
				
			</div>
			
		</div>
		
		<!--inicio logo-->
		<img height="83" src="<spring:url value="/static/resources/images/encabezado_recortado.jpg" htmlEscape="true" />" title="Portal IMSS" width="471" />
		<!--Termino logo-->
		
		<div class="top_search">
			<!--inicio busqueda-->
			<form id="cse-search-box" action="http://www.imss.gob.mx/buscador/resultado.html">
				<input name="cx" type="hidden" value="002360038649913767611:zxhajmgbjye" />
				<input name="cof" type="hidden" value="FORID:11" />
				<input name="ie" type="hidden" value="ISO-8859-1" />
				<div>
					<input id="s" name="q" size="15" type="text" />
					<input id="searchsubmit" type="submit" value="Buscar" />
				</div>
			</form>
		</div>
		
		
		<div class="titulo_sistema texto-centrado">
			<ul> 
	            <li><a style="color: white !important;">Cat&aacute;logo de Clasificaci&oacute;n de Empresas</a> 
	          </ul>
		</div>

<!-- <div id="encabezado"> 
    inicia Encabezado
    <div id="barra_herramientas">
      <div id="login">&nbsp;</div>
      <div id="herramientas">&nbsp;</div>
    </div>
    <div id="firma_busqueda">
      <div id="firma"></div>
      <div id="buscar">
        <div id="dominio">&nbsp; </div>
      </div>
    </div>

	<div class="menu_principal"> 
	      inicia menu principal
	      <div align="center">
	        <div class="centrado">
	          <ul>
	            <li><a>Catálogo de Clasificación de Empresas</a> 
	          </ul>
	        </div>
	        fin centrado 
	      </div>
	    </div>
</div> -->