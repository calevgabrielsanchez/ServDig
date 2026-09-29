<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        	var urlDestino = contextPath+"/wizard/continuacionVoluntaria/comunes/agregarDomicilio";
        	
        	$.post(urlDestino, datosAEnviar, function(respuestaHtml) {
        	    
        	    var iframe = window.frameElement;
        	    
        	    if (iframe) {
        	        var doc = iframe.contentDocument || iframe.contentWindow.document;
        	        doc.open();
        	        doc.write(respuestaHtml);
        	        doc.close();
        	    }
        	}).fail(function(jqXHR, textStatus, errorThrown) {
        	    console.error("Error al realizar la peticiï¿½n POST:", textStatus, errorThrown);
        	});
        	});
        } else {
                console.error("Error: No se encontrï¿½ el elemento con ID 'btnContinuar' en el DOM.");
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
    background-color: #722F37; /* Color vino clï¿½sico (puedes ajustarlo segï¿½n tu tono exacto) */
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
    background-color: #f2e3e4; /* Un tono vino un poco mï¿½s marcado para el hover */
    transition: background-color 0.2s ease;
}

table.cuadricula {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
            font-family: Arial, sans-serif;
        }
        table.cuadricula th, table.cuadricula td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }
        table.cuadricula th {
            background-color: #611232;
        }
        /* Estilo para los meses activos: Dorado traslúcido */
        .mes-activo {
            background-color: rgba(241, 196, 15, 0.35); /* Dorado ámbar con 35% de opacidad */
            color: #7d6608; /* Un tono dorado oscuro para que el texto resalte legible */
            font-weight: bold;
        }
        /* Estilo para los meses fuera de rango */
        .mes-inactivo {
            background-color: #f9f9f9;
            color: #bbb;
        }
	hr.red {
		margin-bottom: 40px;
	}   
	.body{
	padding-top: 40px;
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
				<p> del ${fechaInicio} al ${fechaFin} </p>
	       </div>
	    <div class="table-container tabla-vino">
		<table class="cuadricula">
	        <thead>
	            <tr>
	                <th>Año</th>

	                <th>Ene</th>
	                <th>Feb</th>
	                <th>Mar</th>
	                <th>Abr</th>
	                <th>May</th>
	                <th>Jun</th>
	                <th>Jul</th>
	                <th>Ago</th>
	                <th>Sep</th>
	                <th>Oct</th>
	                <th>Nov</th>
	                <th>Dic</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:forEach var="fila" items="${filasCuadricula}">
	                <tr>
	                    <td><strong>${fila.anio}</strong></td>
	                    
							<c:forEach var="mes" items="${fila.meses}">
							    <c:choose><c:when test="${mes.activo}">
							        <td class="mes-activo">${mes.nombreMes}</td>
							    </c:when><c:otherwise>
							        <td class="mes-inactivo">-</td>
							    </c:otherwise></c:choose>
							</c:forEach>
	                </tr>
	            </c:forEach>
	        </tbody>
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
