<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/registro.js"></script>
<style type="text/css">
.texto {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: normal;
	color: #000;
}
.texto2 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	font-weight: normal;
	color: #000;
}
</style>

<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
<div id="cuerpo_principal" class="form-comment">    

     <fieldset style="align:center"  >
        <table width="643" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="402"><img src="/denuncia-web/resources/plantilla_aplicacion_final/images/sistema_r2_c2.jpg" width="402" height="77" /></td>
    <td width="25">&nbsp;</td>
    <td width="392">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3"><img src="/denuncia-web/resources/plantilla_aplicacion_final/images/sistema_r4_c2.jpg" width="819" height="62" border="0" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" align="center"><p class="texto">Para registrar la denuncia de trabajadores por irregularidades en su inscripción al Seguro Social, es necesario que se registre y <br />
      obtenga su contraseña. </p>
    <p class="texto">Debe tener a la mano una dirección de correo electrónico. </p></td>
  </tr>
  <tr>
    <td colspan="3">&nbsp;</td>
  </tr>
  <tr>
    <td align="left" valign="top"><p><br />
      <br />
    </p>
      <table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
        <td width="300"><img src="/denuncia-web/resources/plantilla_aplicacion_final/images/sistema_r6_c4.jpg" width="301" height="29" /></td>
      </tr>
      <tr>
        <td height="468" align="center" bgcolor="#E3E4D4"><p><span class="texto"><br />
          Si ya cuenta con su contraseña de acceso, puede registrar una nueva denuncia o actualizar una previamente capturada.</span><br />
          </p>
          <table width="301" height="366" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="26" height="64">&nbsp;</td>
              <td width="206" align="left"><p><span class="texto">Correo electrónico</span><br />
                <input type="text" name="desEmail" id="desEmail" />
                  <br />
                  <br />
                  <span class="texto">Contraseña</span><br />
                  <input type="password" name="desPassword" id="desPassword" />
                  <br />
                  <br />
                 
<br />
                </p></td>
              <td width="69">&nbsp;</td>
            </tr>
            <tr>
              <td height="64">&nbsp;</td>
              <td colspan="2"  align="left">
                  <img alt="captcha" src="<%=request.getContextPath()%>/captchaController/captcha.htm"  width="200" height="100"  id="imgCaptchaLog" name="imgCaptchaLog" />
                  <br/>
                  <button type="button"  id="btnNuevaImagen" name="btnNuevaImagen" class="mboton" onclick="nuevaImagenLog();" ><span class="boton">Nueva imagen</span>  </button>
                <br />
                <br />
                <br />
                <br />               
                <br />
                <span class="required">* </span> Capture los caracteres de la imagen (Distingue entre may&uacute;sculas y min&uacute;sculas)<br />
                  <input type="captcha" name="log_j_captcha_response" id="log_j_captcha_response" />
                  <br />
                  <br />
                <br />
                <br />
                <br />
                <br />
                <br /></td>
              </tr>
            <tr>
              <td height="45">&nbsp;</td>
              <td colspan="2"><span class="texto">¿Olvidó o perdió su contraseña de acceso?<br />
                  <br />
Recuperar contraseña </span></td>
            </tr>
          </table>
          <p><br />
          </p></td>
      </tr>
      <tr>
        <td><img src="/denuncia-web/resources/plantilla_aplicacion_final/images/sistema_r8_c4.jpg" width="301" height="32" /></td>
      </tr>
  </table>
      <br />
      <table width="149" height="21" border="1" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td bgcolor="#FFFFFF"><img src="/denuncia-web/resources/plantilla_aplicacion_final/images/bullet.jpg" width="10" height="9" /><span class="texto2"><strong> Crear una cuenta</strong></span></td>
        </tr>
    </table>      <br /></td>
    <td>&nbsp;</td>
    <td valign="middle" class="texto"><strong>Nota:</strong> El documento de acuse de recepción que se genera requiere Acrobat Reader versión 5 o superior, puede descargarlo en:<br />
http://get.adobe.com/es/reader/ <br />
<br />
<br />
<br />
<p><strong>Términos y condiciones de este servicio:</strong><br />
  <br />
  1. 	La información registrada es de carácter <br />
  confi	dencial. </p>
<p>2. 	El uso de la contraseña registrada queda bajo la <br />
  responsabilidad del denunciante. </p>
<strong class="texto">3. Para orientación respecto del llenado de la denuncia favor de comunicarse al teléfono 01 800 623 23 23</strong>
<p>&nbsp;</p></td>
  </tr>
  <tr>
    <td colspan="3" bgcolor="#DF1E33">&nbsp;</td>
  </tr>
</table>                               
     </fieldset>
     
</div>
<form action="<%=request.getContextPath()%>/registro/irRegistro.do"  method="get" id="registroForm" name="registroForm" ></form>
     <form action="<%=request.getContextPath()%>/registro/irLogin.do"  method="get" id="irLoginForm" name="irLoginForm" ></form>
     <form action="<%=request.getContextPath()%>/registro/irRecuperar.do"  method="get" id="irRecuperarForm" name="irRecuperarForm" ></form>