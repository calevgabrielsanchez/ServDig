(function($) {
	$.widget("delta.identidad", {
		options : {
			idPersona : null,
			idTipoPersona : null,
			personaUbicada : null,
			idTipoTramite : null,
			onClean : null,
			onIdentidadValidada : null
		},
		_create : function() {
			this._options = {
				isIdentidadCompleta : false,
				validacionIdentidad : null
			};
			this.element.html(this._buildEmptyState());
			this._desactivarMenu();
		},
		_setOption : function(key, value) {
			this._super(key, value);
		},
		_setOptions : function(options) {
			this._super(options);
		},
		_destroy : function() {
			console.log("DESTRUYENDO");
		},
		_buildEmptyState : function() {		
			var html = '<div id="identidadWrapper">';
			html += '<div class="empty-state well">';
			html += '<div class="imagen">';
			html += '<i class="glyphicon glyphicon-exclamation-sign"></i>';
			html += '</div>';
			html += '<div class="titulo">No ha seleccionado a una identidad/persona.</div>';
			html += '</div>';
			html += '</div>';
			
			return html;
		},
		_activarMenu : function(){			
			$('ul#accionesIdentidadWrapper').show();
		},
		_desactivarMenu : function(){		
			$('ul#accionesIdentidadWrapper').hide();
		},
		_generarWarnings : function() {
			var _warnings = '';
			var _this = this;
			$.each(this._options.validacionIdentidad.errores, function() {
				_warnings += '<div class="';
				
				if (this.nivelError === 'ERROR') {
					_warnings += 'alert alert-danger">';
				} else if (this.nivelError === 'WARNING') {
					_warnings += 'alert alert-warning alert-dismissible">';
					_warnings += '<button type="button" class="close" ';
					_warnings += 'data-dismiss="alert" aria-label="Close">';
					_warnings += '<span aria-hidden="true">&times;</span></button>';
				} else {
					_warnings += 'alert alert-info alert-dismissible">';
					_warnings += '<button type="button" class="close" ';
					_warnings += 'data-dismiss="alert" aria-label="Close">';
					_warnings += '<span aria-hidden="true">&times;</span></button>';
				}
				
				_warnings += this.mensaje;
				_warnings += _this._generarLigaTramite(this.tramiteSolucion, this.parametros);
				_warnings += '</div>';
			});
			
			$('div#identidadWarningsContainer').append(_warnings);
		},
		_generarLigaTramite : function(idTramite, params) {
			
			var liga = null; 
			
			$.ajax({
				url : '/portal-ventanilla-web/menu/check',
				dataType : 'json',
				async : false,
				data : {
					idTramite : idTramite
				},
				success : function(response) {
					if (response) {
						liga = ', con el tr\u00E1mite de <a class="alert-link" ';
						
						if (idTramite === 2) {
							var _consultaRenapo = typeof params.CONSULTA_RENAPO !== 'undefined' ? params.CONSULTA_RENAPO : false;
							var _consultaSat = typeof params.CONSULTA_SAT !== 'undefined' ? params.CONSULTA_SAT : false;
							var _fn = null;
							
							if (_consultaRenapo && _consultaSat) {
								_fn = "datosPersonales()";
							} else if (_consultaRenapo && !_consultaSat) {
								_fn = "datosPersonalesSoloRenapo()";
							} else {
								_fn = "datosPersonalesSoloSat()";
							}
							
							liga += 'onclick="' + _fn + '">';
							liga += 'ACTUALIZACI\u00D3N DE DATOS';
						}
						
						liga += '</a>';
					} else {
						liga = '';
					}
				}
			}).error(function(){
				$.error('Error al validar si el trámite esta habilitado');
			});	
			
			return liga;
		},
		mostrar : function() {
			var _this = this;
			
			if (this.options.idPersona === null || this.options.idTipoPersona === null) {
				$.error('Datos insuficientes para mostrar el detalle de la identidad');
			} else {
				var _container = $('div#identidadWrapper', this.element);
				
				delete this.options.create;
				delete this.options.disabled;
				
				$.ajax({
					url: '/portal-ventanilla-web/detalle/identidad',
					type: 'post',
					dataType: 'json',
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify({
						idPersona : this.options.idPersona,
						idTipoPersona : this.options.idTipoPersona,
						idTramite : this.options.idTipoTramite
					}),
					beforeSend: function() {
						_container.html('<div class="loading well" style="text-align: center;"><img class="loading"></div>');
					},
					success: function(response) {
						var personaUbicada = response.persona;
						_this._options.validacionIdentidad = response.validacionIdentidad;
						
						if (_this.options.personaUbicada==null)
							_this.options.personaUbicada = personaUbicada;
						
						$.ajax({
							url: '/portal-ventanilla-web/detalle/identidad/mostrar',
							type: 'post',
							dataType: 'html',
							contentType: "application/json; charset=utf-8",
							data: JSON.stringify({
					 			idPersona : _this.options.idPersona,
								idTipoPersona : _this.options.idTipoPersona,
							}),
							success: function(contenido) {
								_container.html(contenido);
																
								if (_this._options.validacionIdentidad.valida == true) {
									_this._options.isIdentidadCompleta = true;
									_this._trigger( "complete", null, null );
								} 
								
								if (!$.isEmptyObject(_this._options.validacionIdentidad.errores)) {
									_this._generarWarnings();
								}
								
								$.ajax({
									url: '/portal-ventanilla-web/menu/identidad',
									type: 'post',
									dataType: 'html',
									contentType: "application/json; charset=utf-8",
									data: JSON.stringify({
										idTipoPersona : _this.options.idTipoPersona
									}),
									success: function(contenido) {
										$('#menuAccionesIdentidad').html(contenido);
										_this._activarMenu();
									},
									error: function(error) {
										$('#menuAccionesIdentidad').html('Error al obtener las acciones');
									}
								});
								
								_this.options.onIdentidadValidada.call();
							},
							error: function(error) {
								_container.html(error.responseText);
							}
						});
					},
					error: function(error) {
						_this._desactivarMenu();
						_container.html(error.responseText);
					}
				});
			}
		},
		actualizar : function() {
			this.limpiar();
			this.mostrar();
		},
		limpiar : function() {
			this.element.html(this._buildEmptyState());
			this.options.personaUbicada = null;
			this._desactivarMenu();
			this.options.onClean.call();
		},
		isIdentidadCompleta : function() {
			return this._options.isIdentidadCompleta;
		}
	});
})(jQuery);