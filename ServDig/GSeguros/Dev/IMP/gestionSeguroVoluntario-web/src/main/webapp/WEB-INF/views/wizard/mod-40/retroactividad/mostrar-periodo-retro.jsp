<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
    
    
    document.addEventListener("DOMContentLoaded", function() {
        var boton = document.getElementById("btnContinuarRetroDomicilio");
        
        if (boton) {
        	
        	console.log('En funcion para dirigir a captura de domicilio');
        	
        
        	boton.addEventListener("click", function(event) {
        		event.preventDefault();

        
        	
        	var datosAEnviar = {
        			idPersona: "15161242",
        			correoSolicitante: "airf%40xe.com",
        			nombreSolicitante: "FAUSTINO+AVILA+REYES"
        		};
        	var urlDestino = contextPath+"/gestionSeguroVoluntario-web-ciudadano/wizard/continuacionVoluntaria/comunes/agregarDomicilio";
        	
        	$.post(urlDestino, datosAEnviar, function(respuestaHtml) {
        	    
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
    }  
	.pila-container {
	    display: flex;
	    flex-direction: column;
	    justify-content: center;
	    gap: 15px;
	    width: 100%;
	}
	.bloque {
	    width: 80%;
	}
	     
	.container{
		display: flex;
	    flex-direction: column;
	    justify-content: center;
	    text-align: center;
	}
	.table-container{
		margin-top: 30px;
		display: flex;
		justify-content: center;		
	}
.tabla-vino {
    width: 100%;
    border-collapse: collapse;
    font-family: Arial, sans-serif;
    background-color: #ffffff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); /* Sombra muy ligera para darle profundidad */
    border-radius: 4px;
    overflow: hidden;
}

.tabla-vino th {
    background-color: #722F37; /* Color vino cl�sico (puedes ajustarlo seg�n tu tono exacto) */
    color: #ffffff;
    text-align: left;
    padding: 12px 16px;
    font-weight: 600;
    font-size: 14px;
}

.tabla-vino td {
    padding: 12px 16px;
    border-bottom: 1px solid #eaeaea;
    color: #333333;
    font-size: 14px;
}

.tabla-vino tbody tr:nth-child(even) {
    background-color: #fcf6f6; /* Blanco con un toque rosado/vino muy sutil */
}

.tabla-vino tbody tr:hover {
    background-color: #f2e3e4; /* Un tono vino un poco m�s marcado para el hover */
    transition: background-color 0.2s ease;
}
    
</style>

    

<div id="eleccion-retroactividad" class="pila-container">

        <div class="bloque titulo">
            <span>Calculo de Periodo Retroactivo</span>
            <hr class="red m-b-none">
        </div>
        
        <div>
           <div id="cuerpo" class="container">
           <div>
	        	<p>El periodo calculado al que tiene derecho es el siguiente: </p>
				<p> del 12/01/2025 al 23/08/2025 </p>
	       </div>
	       <div class="table-container tabla-vino">
	        	<table>
	        		<tr>
	        		 <td>11/2020</td>
	        		 <td>11/2021</td>
	        		 <td>11/2022</td>
	        		 <td>11/2023</td>
	        		 <td>11/2024</td>
	        		 <td>11/2025</td>
	        		</tr>
	        	</table>
	        </div>
         </div>
        </div>

        <div class="modal-footer">
             <button type="button" class="btn btn-default" data-dismiss="modal">
                 Cancelar
             </button>
             <button type="button" id="btnContinuarRetroDomicilio"
                     class="btn btn-danger">
                 <i class="fa fa-check"></i> Continuar
             </button>
        </div>        
</div>
