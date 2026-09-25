<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>


<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
<div id="cuerpo_principal" class="form-comment">    
<form action="" method="post" id="formRecupera">
     <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%" align="center">  
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
             <td style="background-color:#D21C1C;"class="titulo_sistema" colspan="3" style="width: 900px">           
                <h2 style="background-color:#D21C1C;color:white">   RECUPERACI&Oacute;N DE CONTRASEÑA</h2>
            </td>
          </tr>   
          <tr align="center">
        
            <td align="right" colspan="2">
                 <img id="imgCaptchaRec" name="imgCaptchaRec" src="<%=request.getContextPath()%>/captchaController/captcha.htm"/>
           </td>
            <td align="left" colspan="1">
                  <button type="button"  id="btnNuevaImagenRec" name="btnNuevaImagenRec" class="mboton" onclick="actucap();" ><span class="boton">Nueva imagen</span>  </button>
           </td>
          </tr>
          <tr>
                <td align="right"><span class="required">* </span> Capture los car&aacute;cteres de la imagen (Distingue entre may&uacute;sculas y min&uacute;sculas)</td>
                <td align="left" colspan="2"><input type="text" name="rj_captcha_response" id="rj_captcha_response" size="45" maxlength="6" style="text-align: center;" /></td>
          </tr>
           <tr align="center">
             <td align="right" style="width: 900px"> 
                <span class="required">* </span>  Correo Electr&oacute;nico
            </td>
            <td align="left" style="width: 900px" colspan="2"> 
                <input type="text" id="desMail" name="desMail" size="45"  style="text-align: center;"  > </input>
            </td>
           
          </tr> 
          
           
          <tr> <td> <br/></td> </tr>
          
          <tr align="center"> <td colspan="3" align="center">  <button type="button"  id="btnRecuperar" class="mboton" name="btnRecuperar"> <span class="boton"> Continuar </span> </button>
                       <button type="button"  id="btnCancelarRec" class="mboton" name="btnCancelarRec" onclick="irInicio();"> <span class="boton"> Cancelar </span> </button></td>
          </tr>
          
        </table>                                
     </fieldset>
    </form>
 <form action="<%=request.getContextPath()%>/registro/opciones.do"  method="get" id="wlForm" name="wlForm" ></form>
 <form action="<%=request.getContextPath()%>/registro/irRegistro.do"  method="get" id="registroForm" name="registroForm" ></form>
 <form action="<%=request.getContextPath()%>/registro/irLogin.do"  method="get" id="irLoginForm" name="irLoginForm" ></form>
 <form action="<%=request.getContextPath()%>/registro/irRecuperarConf.do"  method="get" id="irRecuperaConfForm" name="irRecuperaConfForm" ></form>
</div>
