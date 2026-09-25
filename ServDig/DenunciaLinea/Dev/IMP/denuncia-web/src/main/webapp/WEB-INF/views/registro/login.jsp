<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/navegadorUtils.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/registro.js"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<div id="cuerpo_principal" class="form-comment" onload="reloads()">    
<form:form modelAttribute="dltUsuarioden"    action="${contextpath}/registro/validaUsuarioInternet.do" method="post" id="LoginForm">
     <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%" align="center">  
        	<tr>
				<td width="900px" align="left" colspan="3"><img
				src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/logoSideli.jpg"
				width="750" ></td>		
			</tr>
          <tr align="center">
             <td style="background-color:#137B62;"class="titulo_sistema" colspan="3" style="width: 900px">           
                 <h2 style="background-color:#137B62;color:white">  INGRESO A LA DENUNCIA</h2>
            </td>
          </tr> 
          <tr>      
           <td align="left" colspan="1">
                 <img alt="captcha" src="<%=request.getContextPath()%>/captchaController/captcha.htm" id="imgCaptchaLog" name="imgCaptchaLog" />
           </td>
            <td align="left" colspan="2">
                  <button type="button"  id="btnNuevaImagen" name="btnNuevaImagen" class="mboton" onclick="nuevaImagenLog();" ><span class="boton">Nueva imagen</span>  </button>
           </td>
           </tr>
           <tr>
                <td align="right"><span class="required">* </span> Capture los car&aacute;cteres de la imagen (Distingue entre may&uacute;sculas y min&uacute;sculas)</td>
                <td align="left" colspan="2"><input type="text" name="log_j_captcha_response" id="log_j_captcha_response" size="45" maxlength="6" style="text-align: center;" onkeypress="limpiaLblCaptcha();"/>
                <label for="lblj_captcha_response"  id="lblj_captcha_response"></label>
                </td>
          </tr>
           <tr align="center">
             <td align="right" style="width: 900px"> 
                <span class="required">* </span>Correo Electr&oacute;nico
            </td>
            <td align="left" style="width: 900px"> 
                <input type="text" id="desEmail" name="desEmail" maxlength="50" size="45" style="text-align: center;" onkeypress="limpiaDesMail();"><label for="lblDesEmail"  id="lblDesEmail"> </label></input>
                
            </td>
           
          </tr> 
           <tr align="center">
             <td align="right" style="width: 900px"> 
                <span class="required">* </span> Contraseña
            </td>
            <td align="left" style="width: 900px" colspan="2"> 
                <input type="password" id="desPassword" name="desPassword" size="45" style="text-align: center;" maxlength="8" onkeypress="limpiaDesPass();"><label for="lblDesPassword"  id="lblDesPassword"></label>           </td>
           
          </tr>
          <tr> <td> <br/></td> </tr>
          <tr>
             <td align="justify" style="width: 900px" colspan="3"> 
                Nota: El documento de acuse de recepci&oacute;n que se genera, se visualiza con Acrobat Reader versi&oacute;n 5.0 o superior, puede descargarlo en <a href="http://get.adobe.com/es/reader" title="Acrobat Reader" target="_blank">http://get.adobe.com/es/reader</a>
            </td>
           
          </tr>
          <tr align="center"> <td colspan="1">  <button type="button"  id="btnLogueo" class="mboton" onclick="logueo();"> <span class="boton"> Ingresar </span> </button></td>
          <td colspan="1">  <button type="button"  id="btnSalir" class="mboton" onclick="irInicio(); "> <span class="boton"> Salir </span> </button></td> </tr>
        </table>                                
     </fieldset>
    </form:form>
     <form action="<%=request.getContextPath()%>/registro/opciones.do"  method="get" id="wlForm" name="wlForm" ></form>
</div>
