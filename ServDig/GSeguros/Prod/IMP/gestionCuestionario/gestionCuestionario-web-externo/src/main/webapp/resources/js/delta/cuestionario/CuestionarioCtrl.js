(function($) {
    var initListenerRespuestas = function() {
        $('input.respuesta', 'div#cuestionarioContainer').each(function() {
            var _type = $(this).attr('type');
            var _nameAttr = $(this).attr('name');
            
            if (_type == 'radio') {
                $("input[name='" + _nameAttr + "']").change(function(e) {
                    var _cvePregunta = $(this).attr('cP');
                    var _currentValue = $(this).val();
                    var _idOpc = $(this).attr('cOpc');
                    var _respuesta = new Object();
                    var _numP = $(this).attr('numP');
                    var _numSec = $(this).attr('numSec');
                    var _valor = new Object();
                    var _desc = $(this).parent().text();
                    
                    _valor.clave = _idOpc;
                    _valor.valor = _currentValue;
                    _valor.descripcion = _desc;
                    
                    _respuesta.numPregunta = _numP;
                    _respuesta.numSeccion = _numSec;
                    _respuesta.valores = new Array();
                    _respuesta.valores.push(_valor);
                    
                    respuestas[_cvePregunta] = _respuesta;
                });
            } else if (_type == 'checkbox') {
                $("input[name='" + _nameAttr + "']").change(function(e) {
                    var _cvePregunta = $(this).attr('cP');
                    var allVals = [];
                    var _respuesta = new Object();
                    var _numP = $(this).attr('numP');
                    var _numSec = $(this).attr('numSec');
                    

                    $("input[name='" + _nameAttr + "']:checked").each(function() {
                    	var _valor = new Object();

                        _valor.clave = $(this).attr('cOpc');
                        _valor.valor = $(this).val();
                        _valor.descripcion = $(this).parent().text();
                        allVals.push(_valor);
                    });
                    
                    _respuesta.numPregunta = _numP;
                    _respuesta.numSeccion = _numSec;
                    _respuesta.valores = allVals;

                    respuestas[_cvePregunta] = _respuesta;
                });
            }
        });

        $('select.respuesta', 'div#cuestionarioContainer').each(function(index) {
            var _nameAttr = $(this).attr('name');

            $("select[name='" + _nameAttr + "']").change(function(e) {
                var _cvePregunta = $(this).attr('cP');
                var _currentValue = $(this).val();
                var _selected = $('option:selected', this);
                var _idOpc = _selected.attr('cOpc');
                var _respuesta = new Object();
                var _numP = _selected.attr('numP');
                var _numSec = _selected.attr('numSec');
                var _valor = new Object();
                var _desc = _selected.text();
                
                _valor.clave = _idOpc;
                _valor.valor = _currentValue;
                _valor.descripcion = _desc;

                _respuesta.numPregunta = _numP;
                _respuesta.numSeccion = _numSec;
                _respuesta.valores = new Array();
                _respuesta.valores.push(_valor);
                
                respuestas[_cvePregunta] = _respuesta;
            });
        });
    };

    var initListenerDependientes = function() {
        $('input[dependencia=true]').each(function() {
            var _nameAttr = $(this).attr('name');
            var _baseValue = $(this).attr('id');
            var _idDivDependiente = _baseValue + 'Pregunta';

            $("input[name='" + _nameAttr + "']").change(function(e) {
                var _currentValue = $(this).attr('id');

                if (_baseValue == _currentValue) {
                    $('#' + _idDivDependiente).show();
                } else {
                    $('#' + _idDivDependiente).hide();
                    $('input:radio, input:checkbox', '#' + _idDivDependiente).each(function() {
                        $(this).removeAttr('checked');
                        var _cp = $(this).attr('cp');
                        delete respuestas[_cp];
                    });

                    $('select', '#' + _idDivDependiente).each(function() {
                        $(this).val(-1);
                        var _cp = $(this).attr('cp');
                        delete respuestas[_cp];
                    });
                }
            });
        });
    };

    var initListenerDisableCheckboxes = function() {
        $('input:checkbox.no-option').click(function() {
            var _id = $(this).prop('id');
            var _name = $(this).prop('name');

            $('input:checkbox[name="' + _name + '"]').each(function() {
                if ($(this).prop('id') != _id) {
                    if(!$("#"+_id).is(':checked')){
                        $(this).removeAttr('disabled');
                        $(this).parent().removeClass('disabled');
                    } else {
                        $(this).attr('disabled', 'disabled');
                        $(this).removeAttr('checked');
                        $(this).parent().addClass('disabled');
                    }
                }
            });
        });
    };

    var respuestas = {};

    var getRespuestas = function() {
        var respuestasCuestionario = new Object();
        respuestasCuestionario.respuestas = new Array();

        var respuesta = null;

        $.each(respuestas, function(key, value) {
            respuesta = new Object();
            respuesta.cvePregunta = key;
            respuesta.numPregunta = value.numPregunta;
            respuesta.numSeccion = value.numSeccion;
            respuesta.valores = value.valores;
        	            
            respuestasCuestionario.respuestas.push(respuesta);
        });

        return respuestasCuestionario;
    };

    var armarHidden = function(_name, _id, _value) {

        var htmlHidden = '<input type="hidden" name="' + _name + '" ';

        if (_id != null) {
            htmlHidden += 'id="' + _id + '" ';
        }

        htmlHidden += 'value="' + _value + '"/>';

        return htmlHidden;
    };

    var armarHerramientas = function() {
    	var _numSecciones = $('.seccionCuestionario').length;
    	
    	$('.seccionCuestionario').each(function() {
    		var idSeccion = $(this).prop('id');
            var cveSeccion = $(this).attr('csc');
            var idBtnLimpiar = 'btnLimpiarSeccion' + cveSeccion;
            var idBtnCerrarSeccion = 'btnCerrarSeccion' + cveSeccion;

            $(this).after($('<div/>', {
            	'class' : 'row'
            }).append($('<div/>', {
            	'class' : 'col-md-6 col-md-offset-6 col-sm-12 col-xs-12 text-right',
            	'style' : 'padding-right: 30px;'
            }).append($('<button/>', {
            	'type' : 'button',
            	'id' : idBtnLimpiar,
            	'class' : 'btn btn-default m-r-xs'
            }).text('LIMPIAR SECCI\u00d3N'), $('<button/>', {
            	'type' : 'button',
            	'id' : idBtnCerrarSeccion,
            	'class' : 'btn btn-primary'
            }).text('CERRAR SECCI\u00d3N'))));

            $('button#' + idBtnLimpiar).click(function() {
                limpiarFormulario('div#' + idSeccion, true);
                $('input, select', 'div#' + idSeccion).each(function() {
                    var _cp = $(this).attr('cp');
                    delete respuestas[_cp];
                });
                fnHideErrores('div#' + idSeccion);
                fnHideElement('span#' + idSeccion + 'Error');
                $('.pregDependienteContainer').hide();

                $('input, checkbox', 'div#' + idSeccion).each(function() {
                    if ($(this).is(':disabled')) {
                        $(this).removeAttr('disabled');
                        $(this).parent().removeClass('disabled');
                    }
                });
            });
            
            if (_numSecciones > 1) {
		        $('button#' + idBtnCerrarSeccion).click(function() {
		            $('#' + idSeccion + 'Header a').trigger('click');
		            $('#' + idSeccion + 'Header a').focus();
		        });
            } else {
            	$('button#' + idBtnCerrarSeccion).remove();
            }
        });
    };

    var settings = null;

    var methods = {
        init: function(options) {
            return this.each(function() {
                var $this = $(this);
                settings = $this.data('cuestionario');

                if (typeof (settings) === 'undefined') {

                    var defaults = {
                        formId: 'cuestionarioForm',
                        idCuestionario: null,
                        urlCuestionario: '/${mvn.web.app.root}/cuestionario/obtener',
                        urlValidarCuestionario: '/${mvn.web.app.root}/cuestionario/validar',
                        onSuccess: function() {

                        }
                    };

                    settings = $.extend({}, defaults, options);

                    $this.data('cuestionario', settings);
                } else {
                    settings = $.extend({}, settings, options);
                }

                if (settings.idCuestionario === null || settings.idCuestionario == -1) {
                    $.error('El tipo de cuestionario es requerido para la generación del cuestionario');
                }

                $.ajax({
                    url: settings.urlCuestionario,
                    type: 'post',
                    dataType: 'html',
                    data: {
                        idCuestionario: settings.idCuestionario
                    },
                    beforeSend: function() {
                        $this.html('<div class="loading"><img class="loading"></div>');
                        respuestas = {};
                    },
                    success: function(contenido) {
                        $this.html(contenido);

                        armarHerramientas();
                        initListenerDisableCheckboxes();
                        initListenerRespuestas();
                        initListenerDependientes();

                        if ($.isFunction(settings.onSuccess)) {
                            settings.onSuccess.call();
                        }
                    },
                    error: function(error) {
                        $this.html(error.responseText);
                    }
                });

            });
        },
        validar: function(options) {
            var _form = $('#' + settings.formId);
            var _url = settings.urlValidarCuestionario;
            var _respuestasCuestionario = getRespuestas();
            _respuestasCuestionario.tipoCuestionario = settings.idCuestionario;

            $.blockUI();
            fnHideErrores('div#cuestionarioForm');

            $.postJSON(_url, _respuestasCuestionario, function(response) {
                _form.append(armarHidden('respuestas.tipoCuestionario', 'tipoCuestionario', response.tipoCuestionario));
                _form.append(armarHidden('respuestas.sumatoriaRespuestas', 'sumatoriaRespuestas', response.sumatoriaRespuestas));

                $.each(response.respuestas, function(index) {
                	var _respuesta = this;
                    _form.append(armarHidden('respuestas.respuestas[' + index + '].cvePregunta', null, _respuesta.cvePregunta));
                    _form.append(armarHidden('respuestas.respuestas[' + index + '].numPregunta', null, _respuesta.numPregunta));
                    _form.append(armarHidden('respuestas.respuestas[' + index + '].numSeccion', null, _respuesta.numSeccion));
                    $.each(_respuesta.valores, function(idx) {
                        _form.append(armarHidden('respuestas.respuestas[' + index + '].valores[' + idx + '].clave', null, this.clave));
                        _form.append(armarHidden('respuestas.respuestas[' + index + '].valores[' + idx + '].valor', null, this.valor));
                        _form.append(armarHidden('respuestas.respuestas[' + index + '].valores[' + idx + '].descripcion', null, this.descripcion));
                    });
                });
                
                _form.submit();
            }).error(function(data) {
                var _response = $.parseJSON(data.responseText);
                $.each(_response.respuestas, function(index) {
                    var _error = $(this).prop('errorFormGeneral');
                    if (_error != null && _error != '') {
                        var _errorContainer = 'span#errorPreg' + $(this).prop('cvePregunta');
                        fnShowError(_errorContainer, _error);
                    }
                });

                $('.seccionCuestionario').each(function(index) {
                    var _errorNum = $('.error.showElement', this).length;
                    if (_errorNum > 0) {
                        fnShowError('span#' + $(this).prop('id') + 'Error', 'Secci\xf3n con ' + _errorNum + ' preguntas con errores');
                    }
                });
                
                $.unblockUI();
            });

        }
    };

    $.fn.cuestionario = function() {

        var method = arguments[0];

        if (methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if (typeof (method) == 'object' || !method) {
            method = methods.init;
        } else {
            $.error('El método ' + method + ' no existe');
            return this;
        }

        return method.apply(this, arguments);
    }
})(jQuery);