<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
    
    document.addEventListener("DOMContentLoaded", function() {
        var boton = document.getElementById("btnContinuar");
        
        if (boton) {
            
            boton.addEventListener("click", function(event) {
                event.preventDefault();
                
                var radioSeleccionado = document.querySelector('input[name="tipoTramiteSeguro"]:checked');
                
                var urlDestino = "";

                if (radioSeleccionado) {
                    var valorSeleccionado = radioSeleccionado.value;
                    urlDestino = contextPath + "/retroactividad/periodos/"+valorSeleccionado;                    
                    
                } else {
                    console.log("No hay ning�n radio button seleccionado.");
                }
                
                $.get(urlDestino,1, function(respuestaHtml) {
                	var iframe = window.frameElement;
            	    if (iframe) {
            	        var doc = iframe.contentDocument || iframe.contentWindow.document;
            	        doc.open();
            	        doc.write(respuestaHtml);
            	        doc.close();
            	    }
                }).fail(function(jqXHR, textStatus, errorThrown) {
            	    console.error("Error al realizar la petici�n POST:", textStatus, errorThrown);
            	});
            	});
                
        } else {
            console.error("Error: No se encontr� el elemento con ID 'btnContinuar' en el DOM.");
        }
    }); 
</script>


<script src="<spring:url value='/static/resources/js/wizard/persona/ivro/detalle/detalle-seguro.js'/>"></script>

<script type = "text/javascript">

</script>
<style>
    a.print {
        color: inherit;
        text-decoration: none;
    }

    a.print:hover {
        color: black;
        text-decoration: none;
    }

    table.table {
        font-size: initial !important;
    }
    
    .cuerpo{
    	font-size: initial !important;
    	display: flex;
  		justify-content: center;
  		align-items: center;
  		margin-left: 30px;
    }
	.pila-container {
	    display: flex;
	    flex-direction: column;
	    align-items: center;   
	    justify-content: center;
	    gap: 15px;
	    width: 100%;            
	}    
	
	.bloque {
	    width: 80%;
	}
	.opcion-top{
		margin-top:15px;
	}	
	.opcion{
		margin-top:30px;
	}
	.container{
		display: flex;
	    flex-direction: column;
	}
	.descripcion{
		margin-top: 10px;
	}
	
	hr.red {
		margin-bottom: 40px;
	}
    
</style>

<div id="eleccion-retroactividad" class="pila-container">

        <div class="bloque titulo">
        	<p> Usted es candidato a la retroactividad, lo que quiere decir que tiene periodos previos que puede pagar.</p>
        	<br/>
            <span>Elige el tipo de tr�mite</span>
            <hr class="red m-b-none">
        </div>
        
        <div class="bloque col-sm-6">
        	<div class="container">
        		<div class="opcion-top">
			        <input type="radio" id="retroactividad" name="tipoTramiteSeguro" value="1" checked class="ns_"> Retroactividad<br>
			        <div class="descripcion">Si decide pagar su periodo retroactivo, se le har� una �nica l�nea de captura por todo el periodo, y posteriormente se generar� una l�nea de captura mensual.</div>
		        </div>
		        
		        <div class="opcion">
		        	<input type="radio" id="normal" name="tipoTramiteSeguro" value="2" checked class="ns_"> Compra inicial<br>
		        	<div class="descripcion"> Si usted elige la compra inicial, quiere decir que su seguro iniciar� el d�a de hoy y posteriormente se generar� una l�nea de captura mensual.</div>
	        	</div>
	        </div>
        </div>

        <div class="bloque modal-footer">
             <button type="button" class="btn btn-default" data-dismiss="modal">
                 Cancelar
             </button>
             <button type="button" 
                     class="btn btn-danger"
                     id="btnContinuar">
                 <i class="fa fa-check"></i> Continuar
             </button>
        </div>


</div>
