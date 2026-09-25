<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/validaciones.js"></script>
<c:set var="slcNumFolio" value="<%=session.getAttribute(\"numFolio\")%>" />
<c:set var="cveFolioDenuncia" value="<%=session.getAttribute(\"cveFolioDenuncia\")%>" />
<script>
function radios(){
	 var radios = document.getElementsByName("rdoTipoDenuncia");
	 for (i=0;i<radios.length;i++){
		 if(radios[i].checked){
			 var valor = radios[i].value;
	         if(valor=='nueva'){
	                document.getElementById("txtFolioDenuncia").disabled = true;
	            }else if(valor=='existente'){
	                document.getElementById("txtFolioDenuncia").disabled = false;
	            }	 
		 }
		 
	 }
}
function continuar(){
	
    var radios = document.getElementsByName("rdoTipoDenuncia");
    for (i=0;i<radios.length;i++){
        if(radios[i].checked){
            var valor = radios[i].value;
            if(valor=='nueva'){
            	   $('#nuevaSubForm').submit();         
               }else if(valor=='existente'){
            	   if($("#txtFolioDenuncia").val()==''){
            		   alert("Ingrese una clave de denuncia");
            		   return;
            	   }else{
            		   bloquear();
            		   $("#existenteSubForm").attr("action",getAppContextParaJS() + "/denuncia/consultaDen.do");		
                	   var inputFol = $("<input>").attr("type", "hidden").attr("name", "numeroFolioDenuncia").val($("#txtFolioDenuncia").val());
           				 $('#existenteSubForm').append($(inputFol));		
                	   
                	   $("#existenteSubForm").submit();
                	   
            	   }
               }    
        }
        
    }
   
   
}
</script>

	 <table style="width: 100%;" class="tablaverde2">       
          <tr>
                <td align="center" width="100%" colspan="4">
                       <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a>DENUNCIA PRESENCIAL</a>
                                </li>
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
                 </td>
          </tr> 
          <tr>
             <td align="center" width="100%" colspan="4" >
                <table style="width: 60%;" class="tablaverde2" >
            <tr align="center" >
               <td align="right" ><input type="radio" class="caja" name="rdoTipoDenuncia" id="rdoTipoDenuncia" value="nueva" checked="checked" onclick="radios();"/></td>
                <td align="left">Nueva Denuncia</td>
                <td></td>
            </tr>
            <tr align="center">
                <td align="right"><input type="radio" class="caja" name="rdoTipoDenuncia" id="rdoTipoDenuncia" value="existente" onclick="radios();"/></td>
                <td align="left">Ratificaci&oacuten de Denuncia</td>
                <td align="left"> <input type="text" size="25" disabled="disabled" id="txtFolioDenuncia" name="txtFolioDenuncia " value="${slcNumFolio}"> </td>
                
            </tr>
            <tr align="center">
                
                <td align="center" colspan="3"><input type="button" value="Continuar" onclick='continuar();'/> </td>
                
                
            </tr>
           
           </table>
            </td>
          </tr>
           </table>
           


<form  id="nuevaSubForm" name="nuevaSubForm" action="<%=request.getContextPath()%>/denuncia/nueva/nueva.do"></form>
<form  id="existenteSubForm" name="existenteSubForm" action="<%=request.getContextPath()%>/subdelegacion/denuncia/existente.do"></form>