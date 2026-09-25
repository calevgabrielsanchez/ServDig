/*
 *  * 2018.11.01:Editado por Ariel Lopez - ariel.lopez@bemusoft.com
 *   * JS para el control del acceso por medio de los servicios REST
 *    */

/**
 *  * Variables de error
 *   * 401 Invalid password   - com.sun.identity.idsvcs.InvalidPassword
 *    * 403 Maximum Sessions Limit Reached. - com.sun.identity.idsvcs.MaximumSessionReached
 *     * 401 - com.sun.identity.idsvcs.InvalidCredentials
 *      */
var invalidCredentials ='com.sun.identity.idsvcs.InvalidCredentials';
var invalidPassword = 'com.sun.identity.idsvcs.InvalidPassword';
var numberSessionsOpened = 'com.sun.identity.idsvcs.MaximumSessionReached';

$.postJSON2 = function(url, data, callback) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'headers': data,
        'success': callback
    });
};
var AuthenticateSSO = {

		authenticate: function(user_name, pass){
			var url = '/openam_10.0.0/json/authenticate?realm=IMSSDIGITAL';
			var data = {
					'X-OpenAM-Username' : user_name,
					'X-OpenAM-Password': pass,
                                       'Content-Type': 'application/json'
			};
			$.postJSON2(url , data , function(data) {
                            AuthenticateSSO.validateToken(data.tokenId);
                            });

		},

                validateToken : function(token){
		var url = "/openam_10.0.0/json/sessions?tokenId=" + token + '&_action=validate';
			var data ={
                            'Content-Type': 'application/json'
			};
			$.postJSON2(url , data , function(jsondata){
                            AuthenticateSSO.setCookie(token);
                            AuthenticateSSO.callbacks.call();
                             /* alert(jsondata.uid); */

			});
		},

                setCookie : function(cookie_val){
			var options = {
					'path': '/',
					'domain':'.imss.gob.mx'
			};
			$.cookie( 'iPlanetDirectoryPro', cookie_val, options );
		},

		setOnCloseCallback : function(_fnCallback){
			this.callbacks = _fnCallback;
		}
};

function setMensajeError(exceptionName) {
	var mensaje = "";
	$("#mensajeDialogo").html(mensaje);

	if(exceptionName == invalidPassword) {
		mensaje = "El password es incorrecto";
	} else if(exceptionName == numberSessionsOpened) {
		mensaje = "El n&uacute;mero m&aacute;ximo de sesiones abiertas ha sido alcanzado.";
	} else {
		mensaje = "Para poder ingresar, debe estar registrado como usuario.";
	}

	$("#mensajeDialogo").html(mensaje);
}