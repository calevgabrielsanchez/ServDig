<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/registro.js"></script>

<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
<div class="form-comment" id="cuerpo_principal">    

     <fieldset style="align:center">
     <form action="<%=request.getContextPath()%>/registro/validaUsuarioInternet.do" method="post" id="LoginForm">
         <table width="643" cellspacing="0" cellpadding="0" border="0" align="center">
  <tbody><tr>
    <td width="402"><img width="402" height="77" src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/sistema_r2_c2.jpg"></td>
    <td width="25">&nbsp;</td>
    <td width="392">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3"><img width="819" height="62" border="0" src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/sistema_r4_c2.jpg"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center" colspan="3"><p class="texto"><strong class="texto">Para registrar la denuncia de trabajadores por irregularidades en su inscripción al Seguro Social, es necesario que se registre y <br>
      obtenga su contraseña.</strong></p>
    <p class="texto">Debe tener a la mano una dirección de correo electrónico. </p></td>
  </tr>
  <tr>
    
  </tr>
  <tr>
    <td valign="top" align="top">
      <table width="300" cellspacing="0" cellpadding="0" border="0" align="center">
        <tbody><tr>
        <td width="300"><img width="301" height="29" src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/sistema_r6_c4.jpg"></td>
      </tr>
      <tr>
        <td height="468" bgcolor="#E3E4D4" align="center" valign="top"><p><span class="texto">
          Si ya cuenta con su contraseña de acceso, puede registrar una nueva denuncia o actualizar una previamente capturada.</span><br>
          </p>
          <table width="301" height="366" cellspacing="0" cellpadding="0" border="0">
            <tbody><tr>
              <td width="26" height="64">&nbsp;</td>
              <td width="206" align="left"><p><span class="texto">Correo electrónico</span><br>
                <input type="text" id="desEmail" name="desEmail">
                  <br>
                  <label id="lblDesEmail" for="lblDesEmail"><span class="required">El campo es requerido</span></label>
                  <br>
                  <span class="texto">Contraseña</span><br>
                  <input type="password" maxlength="8" id="desPassword" name="desPassword">
                  <br>
                  <label id="lblDesPassword" for="lblDesPassword"></label>
           
                </p></td>
              <td width="69">&nbsp;</td>
            </tr>
            <tr>
              <td height="64">&nbsp;</td>
              <td align="left" colspan="2">
                  <img width="200" height="100" name="imgCaptchaLog" id="imgCaptchaLog" src="<%=request.getContextPath()%>/captchaController/captcha.htm" alt="captcha">
                  <br>
                  <button onclick="nuevaImagenLog();" class="mboton" name="btnNuevaImagen" id="btnNuevaImagen" type="button"><span class="boton">Nueva imagen</span>  </button>
               
                <br>               
                <br>
                <span class="required">* </span> Capture los caracteres de la imagen (Distingue entre mayúsculas y minúsculas)<br>
                  <input type="captcha" id="log_j_captcha_response" name="log_j_captcha_response">
                  <br>
                  <label id="lblj_captcha_response" for="lblj_captcha_response"></label>
                  
               
               </td>
              </tr>
            <tr>
              <td height="45">&nbsp;</td>
              <td colspan="2"> <br><span class="texto">¿Olvidó o perdió su contraseña de acceso?<br>
                  <br>

<a onclick="irRecuperar();" onmouseover="" style="cursor: pointer;">Recuperar contraseña </a></span></td>
            </tr>
          </tbody></table>
          <p>
          </p></td>
      </tr>
      <tr>
        <td><img width="301" height="32" style="cursor: pointer;" onmouseover="" onclick="logueo();" src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/sistema_r8_c4.jpg"></td>
      </tr>
  </tbody></table>
      <br>
      <table width="149" height="21" cellspacing="0" cellpadding="0" border="1" align="center">
        <tbody><tr>
          <td bgcolor="#FFFFFF" style="cursor: pointer;" onmouseover="" onclick="irRegistro();"><img width="10" height="9" src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/bullet.jpg"><span class="texto2"><strong> Crear una cuenta</strong></span></td>
        </tr>
    </tbody></table>      <br></td>
    <td>&nbsp;</td>
    <td valign="top" class="texto" style="text-align: justify;"><br><br><br><br><strong>Nota:</strong> El documento de acuse de recepción que se genera requiere Acrobat Reader versión 5 o superior, puede descargarlo en:<br>
<a target="_blank" title="Adobe Reader" href="http://get.adobe.com/es/reader/">http://get.adobe.com/es/reader/</a><br>

<p><strong>Términos y condiciones de este servicio:</strong><br>
  <br>
  1. 	La información registrada es de carácter <br>
  confi	dencial. </p>
<p>2. 	El uso de la contraseña registrada queda bajo la <br>
  responsabilidad del denunciante. </p>
<strong class="texto">3. Para orientación respecto del llenado de la denuncia favor de comunicarse al teléfono 01 800 623 23 23</strong>
<p>&nbsp;</p></td>
  </tr>
  <tr>
    <td bgcolor="#DF1E33" colspan="3">&nbsp;</td>
  </tr>
</tbody></table>   
</form>                               
     </fieldset>
     <form name="registroForm" id="registroForm" method="get" action="<%=request.getContextPath()%>/registro/irRegistro.do"></form>
     <form name="irLoginForm" id="irLoginForm" method="get" action="<%=request.getContextPath()%>/registro/irLogin.do"></form>
     <form name="irRecuperarForm" id="irRecuperarForm" method="get" action="<%=request.getContextPath()%>/registro/irRecuperar.do"></form>
</div>