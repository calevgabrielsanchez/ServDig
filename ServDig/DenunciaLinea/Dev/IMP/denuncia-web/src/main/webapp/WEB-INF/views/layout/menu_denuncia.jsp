<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/resources/js/delta/menu.js" htmlEscape="true" />"></script>
		
<div class="menu_holder" align="right">		
	<div id="dgCerrarSesion" title="Cerrar Sesion" style="display:none;">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>
			  Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		</br>
		<span id="errorNegocioLabel" class="hiddenElement error"></span>
	</div>

	<!--Inicio Menu -->
	<div>
		<table style="width: 100%" align="center">
			<tr style="background-color:#137B62;color:#FFFFFF"><td align="right">
			<h2 style="background-color:#137B62;color:white">  
			       Atención a Denuncias de Trabajadores por irregularidades en su Inscripción al Seguro Social  
			 </h2>
			  </td>
			</tr>
		</table>
	</div>
<div >	
	<ul class="menuN" >	
	   <li><a href="/denuncia-web/denunciaLinea/datosTrabajador.do" style="color:#FFFFFF;">Registro del trabajador</a>
	   		<ul class="sub-menu">
	   			<li><a href="/denuncia-web/denunciaLinea/datosTrabajador.do">Registro del trabajador</a></li>
	   			<li><a href="/denuncia-web/denunciaLinea/datosPatron.do">Registro datos del patr&oacute;n</a>
	   			    <ul class="sub-menu">
	   			       <li><a href="/denuncia-web/denunciaLinea/datosTrabajo.do">Registro datos del trabajo</a></li>
	   			    </ul>
	   			</li>
	   		</ul>
	   </li>	
	   <li><a href="/denuncia-web/denunciaLinea/datosPatron.do" style="color:#FFFFFF;">Registro datos del patr&oacute;n</a>
	   		<ul class="sub-menu">
	   			<li><a href="/denuncia-web/denunciaLinea/datosTrabajador.do">Registro del trabajador</a></li>
	   			<li><a href="/denuncia-web/denunciaLinea/datosPatron.do">Registro datos del patr&oacute;n</a>
	   			    <ul class="sub-menu">
	   			       <li><a href="/denuncia-web/denunciaLinea/datosTrabajo.do">Registro datos del trabajo</a></li>
	   			    </ul>
	   			</li>
	   		</ul>
	   </li>
	   <li><a href="/denuncia-web/denunciaLinea/datosTrabajo.do" style="color:#FFFFFF;">Registro datos del trabajo</a>
	   		<ul class="sub-menu">
	   			<li><a href="/denuncia-web/denunciaLinea/datosTrabajador.do">Registro del trabajador</a></li>
	   			<li><a href="/denuncia-web/denunciaLinea/datosPatron.do">Registro datos del patr&oacute;n</a>
	   			    <ul class="sub-menu">
	   			       <li><a href="/denuncia-web/denunciaLinea/datosTrabajo.do">Registro datos del trabajo</a></li>
	   			    </ul>
	   			</li>
	   		</ul>
	   </li>
     </ul>
</div>
<!-- Termino Menu -->
	
</div>



