<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>

<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
<div id="cuerpo_principal" class="form-comment">    

     <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%" align="center">  
        	<tr>
				<td width="900px" align="left" colspan="3"><img
				src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/logoSideli.jpg"
				width="750" ></td>		
			</tr>
          <tr align="center">
             <td style="background-color:#137B62;"class="titulo_sistema" colspan="3" style="width: 900px">           
                 <h2 style="background-color:#137B62;color:white">  RECUPERACI&Oacute;N DE CONTRASEÑA</h2>
            </td>
          </tr>  
          <tr>
             <td align="left" style="width: 900px" colspan="2"> 
                Para recuperar la contrasña por favor responda a la pregunta que proporcin&oacute; en su registro.
            </td>
           
           
          </tr>      
           <tr>
             <td align="left" style="width: 900px"> 
                Clave Unica de Registro de Población CURP
            </td>
            <td align="left" style="width: 900px"> 
                <input type="text"> </input>
            </td>
           
          </tr> 
           
           <tr>
             <td align="left" style="width: 900px"> 
           Pregunta: 
            </td>
            <td align="left" style="width: 900px"> 
                <input type="text"> </input>
            </td>
           
          </tr> 
           
           <tr>
             <td align="left" style="width: 900px"> 
           Respuesta: 
            </td>
            <td align="left" style="width: 900px"> 
                <input type="text"> </input>
            </td>
           
          </tr> 
           
          <tr> <td> <br/></td> </tr>
          
          <tr align="center"> <td>  <button type="button"  id="buttonSiguiente" class="mboton" onclick="loguear();"> <span class="boton"> Recuperar Contraseña </span> </button></td> </tr>
        </table>                                
     </fieldset>
    
</div>
