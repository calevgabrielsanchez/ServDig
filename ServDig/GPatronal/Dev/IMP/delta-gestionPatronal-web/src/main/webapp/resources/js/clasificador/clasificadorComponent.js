var $tablaClasificaciones;

;(function($,window){

    var nombrePlugin = 'plugin_clasificador';

    $.fn.clasificador = function(options) {

        var tipoOptions = $.type(options);
        if (tipoOptions == "string") {
            var pluginDom = $(this).data(nombrePlugin);
            if (pluginDom) {
                if (options == "get") {
                    if(validaClasif()){
                        return pluginDom.get();
                    }else {
                        return null;
                    }
                }
                return;
            } else {
                alert("El plugin no ha sido inicializado");
                return;
            }
        }



        return this.each(function() {

            var $elementoActual = $(this);

            if($elementoActual.data(nombrePlugin)) return;
            var instancia = new ClasificadorComponent(options, this);


            $elementoActual.data(nombrePlugin,instancia);
            $elementoActual.data(nombrePlugin).init();
        });

    };

    /**
     * Clase para generar el componente de clasificador
     * @param options
     * @param $element
     * @returns
     */
    ClasificadorComponent = function(options, elementoPantalla) {

        var currentInstance = this;

        if(elementoPantalla != undefined && elementoPantalla != null) {
            var idContenedorDOM = elementoPantalla.id;
            options.contenedor = idContenedorDOM;
        }

        var idFormulario = "#" + options.contenedor +" " + this.FORM;

        var dataEmpty = {"sEcho":"undefined","iTotalRecords":0,"iTotalDisplayRecords":0,"sColumns":null,"aaData":[]};

        var elegido = null;

        this.init =  function() {

            $.ajax({
                url : currentInstance.URL_CLASIFICADOR,
                async: false,
                type: 'POST',
                beforeSend : function() {$.blockUI();},
                contentType: 'application/json',
                success: function (html) {

                    $("#"+options.contenedor).html(html);
                    setEventos();
                }
            });

            $tablaClasificaciones = $('#resultados').dataTable({
                bFilter : false,
                bInfo:false,
                bSort: false,
                bAutoWidth : false,
                bServerSide : true,
                aoColumns : [

                    {
                        fnRender :function(oObj){
                            var retVal = '<input type="radio" value="' + oObj.aData['desFraccion'] +'" id="radio" name="radio" onclick="selecciona(this.value);"/> ';
                            return retVal;

                        },
                        aTargets: [0]
                    },{
                        "sTitle" : "Fracci&oacute;n",
                        "mDataProp" : "desFraccion",
                        "sClass": "dtFraccionClassColumn"
                    }, {
                        "sTitle" : "Actividad",
                        "mDataProp" : "nomActividad",
                        "sClass": "dtCenterClassColumn"

                    }, {
                        "sTitle" : "Descripci&oacute;n",
                        "mDataProp" : "desActividad",
                        "sClass":"dtJustifyClassHLColumn"
                    }


                ],

                bProcessing : true,
                sAjaxSource : '/clasificador/fraccion/buscarFraccionEnCatalogoAnterior.do',
                fnServerData : function(sSource, aoData, fnCallback) {

                    var vSearch ='';
                    var bSource = '';

                    elegido = null;

                    if($('#txtNumEnAnt').val() != ''){
                        //Buscamos por numero
                        bSource = 'dpNumero';
                        /*recuperamos el txt del numero*/
                        vSearch = $('#txtNumEnAnt').val();
                    } else if($('#txtPalabraEnAnt').val() != ''){
                        //Buscamos por palabra
                        bSource = 'dpPalabraClave';
                        /*recuperamos el txto de la palabra clave.*/
                        vSearch = $('#txtPalabraEnAnt').val().toUpperCase();
                    }

                    if (vSearch != '' && bSource != ''){
                        aoData.push({
                            "name" : "sSearch",
                            "value" : vSearch

                        }, {
                            "name" : "time",
                            "value" : new Date()
                        }, {
                            "name" : "dispatch",
                            "value" : bSource
                        });


                        $.postJSON(sSource, aoData, function(data) {

                            fnCallback(data);

                        });
                    }else{
                        fnCallback(dataEmpty);
                    }

                }
            });

            $(idFormulario).validate($.extend({},DEFAULTS_VALIDATE,{
                verifyErrors: function(existError) {

                },
                rules: {
                    txtNumEnAnt: {
                        number: true
                    }
                }
            }));

            getFraccion = function(pFraccion){

                var oFraccionSeleccionada;

                $.ajax({
                    url : "/clasificador/fraccion/buscarFraccionPorClave.do",
                    async: false, //se pone en false para que haga la consulta y devuelva el resultado bien
                    type: 'GET',
                    contentType: 'application/json',
                    data: { desFraccion: pFraccion },
                    dataType: 'json',
                    success: function (data) {
                        oFraccionSeleccionada = data;
                    },
                    error: function(data) {
                        alert('Error al obtener la fraccion')
                    }
                });

                return oFraccionSeleccionada;

            }

        }

        selecciona = function(value) {
            elegido = getFraccion(value);
        }

        this.get = function() {
            return elegido;
        }

        var setEventos = function() {
            var $form = $(idFormulario);

            $form.find('#findClasif').on('click',function(){
                if(validaClasif()){
                    $tablaClasificaciones.fnDraw();
                }
            });

            $form.find('#txtNumEnAnt').on('focus',function(){
                $form.find('#txtPalabraEnAnt').val('');
            });

            $form.find('#txtPalabraEnAnt').on('focus',function(){
                $form.find('#txtNumEnAnt').val('');
            });

            $.unblockUI();
        }

        validaClasif = function(){
            var valido = true;
            if($('#txtNumEnAnt').val().trim() == '' && $('#txtPalabraEnAnt').val().trim() == ''){
                valido = false;
            }
            mostrarMensajeErrorGenerico(idFormulario, !valido, MENSAJE_ERROR_FORM);
            return valido;
        }

    };

    ClasificadorComponent.prototype = {
        URL_CLASIFICADOR : getContext()+ '/clasificadorComponent/init',
        FORM: 'form#datosClasificadorForm'
    };


}(jQuery,window));