<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
    label  {  float: none; }
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/registro.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>
<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
<div id="cuerpo_principal" class="form-comment">    
<form action="" method="post" id="formRegistro">
     <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%; border: medium;" align="center  " >  
        	  <tr>
    <td width="402"><img src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/sistema_r2_c2.jpg" width="402" height="77" /></td>
    <td width="25">&nbsp;</td>
    <td width="392">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3"><img src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/sistema_r4_c2.jpg" width="819" height="62" border="0" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
          <tr align="center">
             <td style="background-color:#D21C1C;"class="titulo_sistema" colspan="4" style="width: 900px">           
               <h2 style="background-color:#D21C1C;color:white">    REGISTRO Y OBTENCI&Oacute;N DE CONTRASEÑA</h2>
            </td>
          </tr>       
           <tr> <td> <br/></td> </tr>
           <tr>
             <td align="right" > 
                <span class="required">* </span> Correo Electr&oacute;nico
            </td>
            <td align="left" style="width: 900px" colspan="2"> 
                <input type="email" size="50" maxlength="50" id="email" name="email" style="text-align: center;"> </input>
            </td>
           
          </tr>
          
          <tr>
             <td align="right" style="width: 900px"> 
                <span class="required">* </span> Confirme su correo Electr&oacute;nico
            </td>
            <td align="left" style="width: 900px" colspan="2"> 
                <input type="email" size="50" maxlength="50" id="confirmEmail" name="confirmEmail" style="text-align: center;"> </input>
            </td>
           
          </tr>
          <tr>
             <td align="right" > 
               <span class="required">* </span> Contraseña Deseada
            </td>
            <td align="left" style="width: 900px" colspan="2"> 
                <input type="password" size="50" maxlength="8" id="password" name="password" style="text-align: center;"> </input>
            </td>
           
          </tr>
          <tr>
             <td align="right" > 
               <span class="required">* </span> Confirme su contraseña
            </td>
            <td align="left" style="width: 900px" colspan="2"> 
                <input type="password" size="50" maxlength="8" id="confirm_pass" name="confirm_pass" style="text-align: center;"> </input>
            </td>
           
          </tr>
          <tr> <td> <br/></td> </tr>
          <tr>
             <td align="justify" style="width: 900px" colspan="3"> 
                Su contraseña deber&aacute; contener 8 caracteres alfanum&eacute;ricos distinguiendo may&uacute;sculas y min&uacute;sculas, no se permiten caracteres especiales, ni acentos.   
            </td>
           
          </tr>
          <tr> <td> <br/></td> </tr>
          <tr>
             <td align="justify" style="width: 900px" colspan="3"> 
                Una vez concluido su registro, la contraseña de acceso que usted captura para registrar la denuncia, le ser&aacute; confirmada mediante correo electr&oacute;nico a la dirección que usted haya proporcionado.
            </td>
           
          </tr>
           <tr> <td> <br/></td> </tr>
          <tr align="center">
         
            <td align="left" colspan="1">
                 <img alt="captcha" src="<%=request.getContextPath()%>/captchaController/captcha.htm" id="imgCaptcha" name="imgCaptcha" />
           </td>
            <td align="left" colspan="2">
                  <button type="button"  id="btnNuevaImagen" name="btnNuevaImagen" class="mboton" onclick="nuevaImagen();" ><span class="boton">Nueva imagen</span>  </button>
           </td>
          </tr>
           <tr> <td> <br/></td> </tr>
          <tr>
                <td align="right"><span class="required">* </span> Capture los caracteres de la imagen (Distingue entre may&uacute;sculas y min&uacute;sculas)</td>
                <td align="left" colspan="1"><input type="text" name="j_captcha_response" id="j_captcha_response" size="50" maxlength="6" style="text-align: center;" /></td>
          </tr>
          
          <tr>
                <td align="right"><span class="required">* </span> Pregunta para recuperaci&oacute;n de contraseña </td>
                <td align="left" colspan="2">
                    <select id="pregunta" name="pregunta" >
                        <option value="">--Seleccione una pregunta--</option>
                   </select>
                </td>
          </tr>
          <tr>
                <td align="right"><span class="required">* </span> Respuesta para recuperaci&oacute;n de contraseña </td>
                <td align="left" colspan="2"><input type="text" id="respuesta" name="respuesta" size="50" maxlength="30" style="text-align: center;"/> </td>
          </tr>
           <tr> <td> <br/></td> </tr>
           <tr>
             <td align="justify" style="width: 900px" colspan="3"> 
                Los campos marcados con <span class="required">* </span> son obligatorios.
                <br/>
                Conserve y resguarde la pregunta y la respuesta para su futura recuperaci&oacute;n.
            </td>
           
          </tr>
            <tr> <td> <br/></td> </tr>
          <tr align="center"> 
          <td></td>
          <td colspan="3" align="center">  <button type="button"  id="btnRegistrar" name="btnRegistrar" class="mboton" > <span class="boton"> Registrarse </span> </button></td> 
          <td colspan="1" align="center">  <button type="button"  id="btnCancelarRegistro" name="btnCancelarRegistro" class="mboton" onclick="irInicio();"> <span class="boton"> Cancelar </span> </button></td>
          <td></td>
          </tr>
          
        </table>                                
     </fieldset>
</form>
 <form action="<%=request.getContextPath()%>/registro/opciones.do"  method="get" id="wlForm" name="wlForm" ></form>
 <form action="<%=request.getContextPath()%>/registro/irRegistro.do"  method="get" id="registroForm" name="registroForm" ></form>
  <form action="<%=request.getContextPath()%>/registro/irLogin.do"  method="get" id="irLoginForm" name="irLoginForm" ></form>
</div>
