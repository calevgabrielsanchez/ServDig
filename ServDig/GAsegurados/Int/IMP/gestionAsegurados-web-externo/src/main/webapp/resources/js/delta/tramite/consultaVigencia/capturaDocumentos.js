function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}


$(document).ready(function(){
    console.log('entro al script');
    
    $("#uploadfile").change(function () {
        var fileSize = this.files[0].size; // Tamaño en bytes
        var maxSize = 1 * 1024 * 1024; // 1 MB

        if (fileSize > maxSize) {
        	alert("El tamaño del archivo no debe exceder 1 MB.");
            // Limpiar el input de archivo
            $("#uploadfile").val("");
        }
    })
    
    $('#validarDatos').click(function(e) {
		
        var nomOpcion;
        var radioButOpcion = document.getElementsByName("opcion");
        var error = false;
        

        for (var i = 0; i < radioButOpcion.length; i++) {
            if (radioButOpcion[i].checked == true) {
                nomOpcion = radioButOpcion[i].value;
            }
        }

        if (nomOpcion == 1) {
            console.log('entro a la funcion', nomOpcion);
            // Obtener el valor capturado en el campo
            var curpBenInput = document.getElementById("curpBen");
            var valorCapturado = curpBenInput.value.toUpperCase();
            if ($("#curpAsegurado").val().toUpperCase() == valorCapturado){
            	$("#erroresCaptura").html('La CURP de su beneficiario legal registrado no puede ser la misma que la del asegurado.').show();	
            	e.preventDefault();
    			error = true;
            }else if(valorCapturado == "000000000000000000"){
            	$("#erroresCaptura").html('El formato de la CURP no es v&aacute;lido, verif&iacute;quelo.').show();	
    			e.preventDefault();
    			error = true;
            }
            // Asignar la URL del controlador correspondiente
            var url = context_path + "/vigencia/validarCURP";

            // Enviar el formulario al controlador
            // enviarFormulario(url);
        } else if (nomOpcion == 2) {
            console.log('entro a la funcion', nomOpcion);
            // Obtener el valor capturado en el campo
            var umfAdscInput = document.getElementById("umfAdsc");
            var valorCapturado = umfAdscInput.value != 0 ? umfAdscInput.value : ""; 
            
            // Asignar la URL del controlador correspondiente
            var url = context_path + "/vigencia/validarUMF";

            // Enviar el formulario al controlador
            // enviarFormulario(url);
        } else if (nomOpcion == 3) {
            console.log('entro a la funcion', nomOpcion);
            // Obtener el valor capturado en el campo
            var rfcPatInput = document.getElementById("rfcPat");
            var valorCapturado = rfcPatInput.value;
            // Asignar la URL del controlador correspondiente
            var url = context_path + "/vigencia/validarRFC";

            // Enviar el formulario al controlador
            //enviarFormulario(url);
        } else if (nomOpcion == 4) {
            console.log('entro a la funcion', nomOpcion);
            // Asignar la URL del controlador correspondiente
            var url = context_path + "/vigencia/autorizacionPendiente";
            // Enviar el formulario al controlador
            // enviarFormulario(url);
        }
        
        var imgVal = $('#uploadfile').val();
        
        
        if (imgVal == '') { 
			$("#erroresCaptura").html('El documento probatorio de identidad es requerido.').show(); 
			e.preventDefault();
			error = true;
		} else if (valorCapturado == '') {
        	$("#erroresCaptura").html('Es necesario proporcionar la informaci\u00F3n marcada como obligatoria.').show();	
			e.preventDefault();
			error = true;
		}
        
		if(!error) {
			$("#erroresCaptura").hide();
			enviarFormulario(url);
        }
    });

    // Función para enviar el formulario al controlador
    function enviarFormulario(url) {
        var formOpcion = document.getElementById("capturaDocumentosForm");
        if (formOpcion) {
            formOpcion.action = url;
            formOpcion.method = "POST";
            console.log('Enviando formulario URL ' + url);
            formOpcion.submit();
        }
    }
    
    $('input[name="opcion"]').change(function() {
        var nomOpcion = $('input[name="opcion"]:checked').val();
        var selectElement = document.getElementById("umfAdsc");

        // Restablece el menú desplegable
        selectElement.innerHTML = ''; // Borra todas las opciones existentes

        // Agrega la opción predeterminada
        var defaultOption = document.createElement("option");
        defaultOption.text = "Seleccione alguna opci\u00f3n";
        defaultOption.value = 0;
        selectElement.appendChild(defaultOption);

        if (nomOpcion === "2") {
            // Variables que contienen los valores de las opciones
            var opcionUMF1 = $("#opcionUMF1").val();
            var opcionUMF2 = $("#opcionUMF2").val();
            var opcionUMF3 = $("#opcionUMF3").val();
            var opcionUMF4 = $("#opcionUMF4").val();
            var opcionUMF5 = $("#opcionUMF5").val();
            var opcionUMF6 = $("#opcionUMF6").val();
            var opcionUMF7 = $("#opcionUMF7").val();
            var opcionUMF8 = $("#opcionUMF8").val();
            var opcionUMF9 = $("#opcionUMF9").val();
            var opcionUMF10 = $("#opcionUMF10").val();

            // Crear un array con los valores de las opciones
            var opciones = [opcionUMF1, opcionUMF2, opcionUMF3, opcionUMF4, opcionUMF5, opcionUMF6, opcionUMF7, opcionUMF8, opcionUMF9, opcionUMF10];

            opciones = shuffleArray(opciones);
            
            // Recorre el array de opciones y crea elementos <option> para el select
            for (var i = 0; i < opciones.length; i++) {
                var option = document.createElement("option");
                // Solo se setea el valor de la UMF
                option.value = opciones[i].split('|')[0];
                option.text = opciones[i].split('|')[1];
                selectElement.appendChild(option);
            }
        }
    });
    
    $("#busquedaCpBtn").on("click", function () {
        // Obtiene el valor del código postal
        var codigoPostal = $("#codigoPostal").val();

        // Realiza la solicitud AJAX al servidor
        $.ajax({
            type: "POST",
            url: context_path + "/vigencia/obtenerDatosDesdeCodigoPostal",
            data: { codigoPostal: codigoPostal },
            success: function(response) {
                if (!response.error) {               	
                    $("#subdelegacion").empty().append(
                            $("<option></option>").val("").text("Seleccione una opci\u00F3n")
                        );
                    
                    response.subdelegacion.forEach(function(subdelegacion) {
                        $("#subdelegacion").append(
                            $("<option></option>")
                                .val(subdelegacion.id)
                                .text(subdelegacion.descripcion)
                        );
                    });
                   
                } else {
                    // Manejar el caso de error
                    console.error(response.error);
                }
            },
            error: function (error) {
                // Maneja cualquier error durante la solicitud AJAX
                console.error("Error en la solicitud AJAX:", error);
            }
        });
    });
    
    // Maneja el clic en el botón "Limpiar"
    $("#limpiarForm").on("click", function () {
        // Limpia el valor del input de código postal
        $("#codigoPostal").val("");
        $("#subdelegacion").empty();
    });
    
    // Obtener el valor del input hidden
    var seleccionarSubdelegacion = document.getElementById('seleccionarSubdelegacion').value;

    // Verificar si seleccionarSubdelegacion es true (ignorando si es una cadena o un booleano)
    if (seleccionarSubdelegacion && seleccionarSubdelegacion !== 'false') {
        // Continuar con el código para configurar el comportamiento del botón
        var finalizarBtn = document.getElementById('finalizarBtn');
        var subdelegacionSelect = document.getElementById('subdelegacion');
        
        // Deshabilita los campos de texto
        $('#curpBen').prop('disabled', true);
        $('#rfcPat').prop('disabled', true);
        $('#uploadfile').prop('disabled', true);

        // Deshabilita las opciones de radio
        $('input[type=radio][name=opcion]').prop('disabled', true);

        // Deshabilita el menú desplegable
        $('#umfAdsc').prop('disabled', true);
        
        finalizarBtn.addEventListener('click', function() {
            var subdelegacionSeleccionada = subdelegacionSelect.value;

            if (subdelegacionSeleccionada) {
                // Seleccionar una opción del menú desplegable

                // Realizar el envío del formulario
                var form = document.getElementById('datosDomicilioRecortadoForm');
                form.submit();
            } else {
                // No se ha seleccionado ninguna opción del menú desplegable
                alert("Por favor, seleccione una subdelegación antes de finalizar.");
            }
        });
    }

});

