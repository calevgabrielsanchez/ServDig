
<%@ include file="../general/taglibs.jsp" %>

    <!--inicia Encabezado-->
    <script type="text/javascript">
			history.go(1);
	</script>


		<div class="header_top "> 
             <div class="top_version cell"> 
                   <span> <spring:message code="label.version"/></span>: 
                   <span> <spring:message code="version" /></span> 
             </div> 
                        
             <div class="top_version cell"> 
                    <ul> 
                        <li>
	                        <a href="http://www.imss.gob.mx" title="Portal IMSS">
	                        	Visita el portal oficial del Instituto Mexicano del Seguro Social 
	                        </a>
                        </li> 
                     </ul> 
            </div>         
        </div> 
            
		<!--inicio logo-->
		<img height="83" src="<spring:url value="/static/resources/imagenes/banner.gif" htmlEscape="true" />" title="Portal IMSS" width="471" />
		<!--Termino logo-->
		<div class="top_search">
			<!--inicio busqueda-->
			<form id="cse-search-box" action="http://www.imss.gob.mx/buscador/resultado.html">
				<input name="cx" type="hidden" value="002360038649913767611:zxhajmgbjye" />
				<input name="cof" type="hidden" value="FORID:11" />
				<input name="ie" type="hidden" value="ISO-8859-1" />
				<input name="fechaSistema" type="hidden" id="fechaSistema"  value="${usuario.fechaSistema}"/>
				<input name="fechaAvisoSession" type="hidden" id="fechaAvisoSession"  value="${usuario.fechaAvisoSession}"/>
				<input name="fechaFinSession" type="hidden" id="fechaFinSession"  value="${usuario.fechaFinSession}"/>
				<input name="intervaloValidacionSession"  type="hidden" id="intervaloValidacionSession" value ="10000"/>
				<input name="validaAviso"  type="hidden" id="validaAviso" value ="${usuario.validaAvisoSession}"/>
				<input name="aseguradoFallecido"  type="hidden" id="aseguradoFallecido" value ="${aseguradoFallecido}"/>
			
				<div>
					<input id="s" name="q" size="15" type="text" />
					<input id="searchsubmit" type="submit" value="Buscar" />
				</div>
			</form>
		</div>
		
		<div id="dialog-Aviso-Session"
			title="Cierre de Sesi&oacute;n">
			<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>
				<label id="mensajeDialogoSession"></label>
			</p>
		</div>

	
<!-- Termina header -->
	