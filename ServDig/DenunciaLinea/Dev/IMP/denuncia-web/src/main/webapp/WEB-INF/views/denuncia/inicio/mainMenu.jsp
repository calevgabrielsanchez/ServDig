<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>
		
<div id="cuerpo_principal" class="form-comment">	
<form:form action="/denuncia/consultaCurp.do" method="post" id="frmMain" name="frmMain" commandName="usuarioDen">
     
     <form:input type="hidden" path="cveUsuarioden" id="hdCveUsuarioden" name="hdCveUsuarioden"/>
     <form:input type="hidden" path="desEmail" id="hdDesEmail" name="hdDesEmail"/>
     
     <fieldset style="align:center"  style="width: 977px">
		<table style="width: 100%" align="center">
			<tr>
				<td width="900px" align="left"><img
				src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/logoSideli.jpg"
				width="750" ></td>		
			</tr>
			<tr align="center"><td><h3 align="center">MENU PRINCIPAL</h3></td></tr>	
			<tr><td><br/></td></tr>
			<tr align="center"><td>Para registrar la Denuncia de Trabajadores por Irregularidades en su Inscripción al Seguro Social, es necesario que se registre y obtenga su contraseña.</td></tr>
			<tr align="center"><td>Debe tener a la mano su dirección de correo electrónico.</td></tr>
			<tr align="center"><td><button type="button"  id="buttonSiguiente" class="mboton" onclick="javascript:siguiente();"> <span class="boton"> Registro </span> </button></td></tr>
			<tr><td></td></tr>
			<tr><td><br/></td></tr>
			<tr align="center"><td>Si ya cuenta con su contraseña de acceso puede registrar una nueva denuncia o actualizar una previamente registrada.</td></tr>
			<tr align="center"><td><button type="button"  id="buttonSiguiente" class="mboton" onclick="javascript:siguiente();"> <span class="boton"> Ingresar al sistema </span> </button></td></tr>
			<tr><td><br/></td></tr>
			<tr><td></td></tr>
			<tr align="center"><td>¿Olvidó o perdió su contraseña de acceso?</td></tr>
			<tr align="center"><td><button type="button"  id="buttonSiguiente" class="mboton" onclick="javascript:siguiente();"> <span class="boton"> Recuperar contraseña </span> </button></td></tr>
			<tr><td></td></tr>
			<tr><td><br/></td></tr>
			<tr align="center"><td>Nota: El documento de acuse de recepci&oacute;n que se genera requiere Acrobat Reader versi&oacute;n 5.0 o superior, puede descargarlo en http://get.adobe.com/es/reader/</td></tr>
			<tr><td><br/></td></tr>
			<tr><td></td></tr>
			<tr><td><br/></td></tr>	
			<tr><td>Términos y condiciones de este servicio:</td></tr>
			<tr><td><br/></td></tr>
			<tr><td>1. La informaci&oacute;n registrada es de car&aacute;cter confidencial.</td></tr>
			<tr><td>2. El uso de la contraseña registrada queda bajo la responsabilidad del denunciante.</td></tr>
			<tr><td>3. Cualquier duda o acalaraci&oacute;n sobre este servicio, deber&aacute; ser solicitada en las Subdelegaciones del IMSS.</td></tr>
			<tr><td><br/></td></tr>	
		</table>
	</fieldset>
</div>		
</form:form>
