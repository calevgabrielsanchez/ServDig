
<script type="text/javascript">



	function Abrir_ventana(pagina) {
	var opciones="toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=300,height=300,top=85,left=140";
	window.open(pagina,"",opciones);
	}
	$(document).ready(
		function(){	
			$("#txtFecha").datepicker();
			
			
	
			
				
			
			//$('#prueba').load('contenedorPdf.jsp')
		
			//$.get("/${mvn.web.app.root}/reportes/reporteSav011/1");
			
			/*$('#prueba').load('/${mvn.web.app.root}/reportes/reporteSav011/1',{nombre:'Guillermo',apellido:'hernandez'}, 
					function(responseText,textStatus,objXml	){
				alert('terminao la peticion');*/
//					alert(responseText);
//					alert(textStatus);
//					alert(objXml);
					/*if(textStatus=='error'){
						$('#error').html('Error:pagina no encontrada');
					}*/
				
			//	});
			
		}
		
	);

</script>			
<fieldset>
<div  id="prueba" style="display:none">
</div>

<input type="text" id="txtFecha"/>
 <table width="100%">
 	<tr align="left">
            <th>
            	Nombre(s)	
            </th>
            <th>
            	Apellido Paterno
            </th>
            <th>
            	Apellido Materno
            </th>
            <th>
            	Fecha de Nacimiento
            </th>
            <th>
            	Sexo
            </th>
            <th>
            	CURP
            </th>
            <th>
            	Parentesco
            </th>
            
        </tr>
 		<tr>
            <td>
                <a href="/${mvn.web.app.root}/prototipo/administracionProrrogas/RegistroProrrogaPorEstudios.jsp" >Guillermo I</a>
            </td>
            <td>
            	 Hernández
            </td>
            <td>
            	 Hernández
            </td>
            <td>
            	27/12/1993
            </td>
            <td>
                Masculino
            </td>
                    <td>
                HEDG831227EHVZRLLO07
            </td>
    
            <td>
                Hijo
            </td>
           
        </tr>
 		<tr>
            <td>
                <a href="/${mvn.web.app.root}/prototipo/administracionProrrogas/RegistroProrrogaPorEstudios.jsp" >Guillermo II</a>
             </td>
             <td>
            	 Hernández
            </td>
            <td>
            	 Hernández
            </td>
            <td>
            	27/12/2000
            </td>
            <td>
                Masculino
            </td>
                    <td>
                HEDG831227EHVZRLLO07
            </td>
    
            <td>
                Hijo
            </td>
             
        </tr>
        <tr>
            <td>
                <a href="/${mvn.web.app.root}/prototipo/administracionProrrogas/RegistroProrrogaPorEstudios.jsp" >Guillermo III</a>
            </td>
            <td>
            	 Hernández
            </td>
            <td>
            	 Hernández
            </td>
            <td>
            	27/12/2001
            </td>
            <td>
                Masculino
            </td>
                    <td>
                HEDG831227EHVZRLLO07
            </td>
    
            <td>
                Hijo
            </td>
             
        </tr>
 		<tr>
            <td>
                <a href="/${mvn.web.app.root}/prototipo/administracionProrrogas/RegistroProrrogaPorEstudios.jsp" >Guillermo IV</a>
            </td>
            <td>
            	 Hernández
            </td>
            <td>
            	 Hernández
            </td>
            <td>
            	27/12/2002
            </td>
            <td>
                Masculino
            </td>
                    <td>
                HEDG831227EHVZRLLO07
            </td>
    
            <td>
                Hijo
            </td>
             
        </tr>
 
  </table>
  <div id="opdf" style="width: 100%; height: 400px">
  	<iframe style="width: 100%; height: 400px" src="/${mvn.web.app.root}/reportes/reporteSav011/1"></iframe>
  </div>
  
</fieldset>