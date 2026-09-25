//'use strict';

var MX = MX || {};

// Variable de la URL que se encuentra en Gruntfile.js para obtener la URl segun ambiente.
MX.root ='https://framework-gb.cdn.gob.mx/';
MX.emailService = 'https://www.gob.mx/subscribe';
MX.trackingID = '';


MX.comscore = 'gobmx';
MX.path = MX.root + 'assets/';
MX.imagesPath = MX.path + 'images/';
MX.scriptsPath = MX.path + 'scripts/';
MX.stylesPath = MX.path + 'styles/';

MX.gobmxPath = 'https://www.gob.mx/';

/**
 * [getParse URL parse current URL]
 * @return {[string]} [url ID]
 */
var getParseURL = function() {
    var urlHost     = window.location.host,
        urlPath     = window.location.pathname,
        host        = urlHost.toLowerCase().split( '.' ),
        path        = urlPath.toLowerCase(),
        hostClean   = host.slice( 0, 2 ).join( '.' ),
        pathClean;

    if ( !isNaN( host[ 0 ] ) ) hostClean = urlHost;

    if ( path.indexOf( '.' ) !== -1 ) {
        var _end = path.indexOf( '.' );
        path = path.substring( 0, _end );
    }

    if ( path.indexOf( '#' ) !== -1 ) {
        var _end = path.indexOf( '#' );
        path = path.substring( 0, _end );
    }

    if ( path.indexOf( '?' ) !== -1 ) {
        var _end = path.indexOf( '?' );
        path = path.substring( 0, _end );
    }

    if ( path === '/' ) path = '/index';

    pathClean = path.replace(/\//g, ".");

    return hostClean + pathClean;
}

MX.trackingID = getParseURL();

MX.isMetaHelper = function() {
  var metas = document.getElementsByTagName('meta');

  function isMeta() {
    for (var i = 0; i < metas.length; i++) {
      if ( metas[i].getAttribute('property') === 'gobmxhelper') {
        return metas[i].getAttribute('content');
      }
    }
  }
  if( isMeta() === 'no plugins') { return true; } else { return false; }
}

if( !MX.isMetaHelper() ) {
  var WebFontConfig = {
    google: { families: [ 'Open+Sans:300italic,400italic,400,300,600,700:latin' ] }
  };
  (function() {
    var wf = document.createElement('script');
    wf.src = ('https:' == document.location.protocol ? 'https' : 'http') +
      '://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js';
    wf.type = 'text/javascript';
    wf.async = 'true';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(wf, s);
  })();
}


MX.secBuilder = function () {
	var body = $('body');

	var navBar =
	'<header>'+
		'<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">'+
			'<div class="container">'+
			  '<div class="navbar-header">'+
			    '<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbarMainCollapse">'+
			      '<span class="sr-only">Interruptor de Navegaci&oacute;n</span>'+
			      '<span class="icon-bar"></span>'+
			      '<span class="icon-bar"></span>'+
			      '<span class="icon-bar"></span>'+
			    '</button>'+
			    '<a class="navbar-brand" style="padding-left:6px;" href="' + MX.gobmxPath + '">'+
			      '<img src="' + MX.imagesPath + 'gobmxlogo.svg" width="75" height="23" alt="PÃ¡gina de inicio, Gobierno de MÃ©xico">'+
			    '</a>'+
			  '</div>'+
			  '<div class="collapse navbar-collapse" id="navbarMainCollapse">'+
			    '<ul class="nav navbar-nav navbar-right">'+
			      '<li><a href="' + MX.gobmxPath + 'tramites">Tr&aacute;mites</a></li>'+
			      '<li><a href="' + MX.gobmxPath + 'gobierno">Gobierno</a></li>'+
            '<li><a href="' + MX.gobmxPath + 'participa">Participa</a></li>'+
            '<li><a href="http://datos.gob.mx" title="Datos abiertos">Datos</a></li>' +
			      '<li><a href="' + MX.gobmxPath + 'busqueda"><span class="sr-only">B&uacute;squeda</span><i class="icon-search"></i></a></li>'+
			    '</ul>'+
			  '</div>'+
			'</div>'+
		'</nav>'+
	'</header>';

  var footer =
  '<footer class="main-footer">'+
    '<div class="list-info">'+
      '<div class="container">'+
        '<div class="row">'+
          '<div class="col-sm-4">'+
            '<h5>Enlaces</h5>'+
            '<ul>'+
              '<li><a href="' + MX.gobmxPath + 'accesibilidad">DeclaraciÃ³n de Accesibilidad</a></li>'+
              '<li><a href="' + MX.gobmxPath + 'privacidad">Pol&iacute;tica de privacidad</a></li>'+
              '<li><a href="' + MX.gobmxPath + 'terminos">T&eacute;rminos y Condiciones</a></li>'+
              '<li><a href="http://www.ordenjuridico.gob.mx" target="_blank">Marco Jur&iacute;dico</a></li>'+
              '<li><a href="http://portaltransparencia.gob.mx" target="_blank">Portal de Obligaciones de Transparencia</a></li>'+
              '<li><a href="https://www.infomex.org.mx/gobiernofederal/home.action" target="_blank">Sistema Infomex</a></li>'+
              '<li><a href="http://inicio.ifai.org.mx/SitePages/ifai.aspx" target="_blank">INAI</a></li>'+
              '<li><a href="' + MX.gobmxPath + 'sitemap">Mapa de sitio</a></li>'+
            '</ul>'+
          '</div>'+
          '<div class="col-sm-4">'+
            '<h5>&iquest;Qu&eacute; es gob.mx?</h5>'+
            '<p>Es el portal &uacute;nico de tr&aacute;mites, informaci&oacute;n y participaci&oacute;n ciudadana. <a href="' + MX.gobmxPath + 'que-es-gobmx" class="gb-footer-link-underline">Leer m&aacute;s</a></p>'+
              '<ul>'+
                '<li><a href="' + MX.gobmxPath + 'en/index">English</a></li>'+
                '<li><a href="' + MX.gobmxPath + 'temas">Temas</a></li>'+
                '<li><a href="http://reformas.gob.mx" target="_blank">Reformas</a></li>'+
              '</ul>'+
          '</div>'+
          '<div class="col-sm-4">'+
            '<h5>Contacto</h5>'+
            // '<p>Insurgentes Sur 1735, Col. Guadalupe Inn.<br>Delegaci&oacute;n &Aacute;lvaro Obreg&oacute;n<br>M&eacute;xico, D.F. C.P. 01020</p> '+
            '<p>Mesa de ayuda: dudas e informaciÃ³n<br /> gobmx@funcionpublica.gob.mx</p>'+
            // '<p><a href="' + MX.gobmxPath + 'atencion">AtenciÃ³n Ciudadana</a></p>'+
            '<p><a href="' + MX.gobmxPath + 'tramites/ficha/presentacion-de-quejas-y-denuncias-en-la-sfp/SFP54" class="gb-footer-link-underline">Denuncia contra servidores pÃºblicos</a></p>'+
          '</div>'+
        '</div>'+
        '<div class="row">'+
          '<div class="col-sm-4">'+
            '<form role="form" id="subscribete" onsubmit="return validateEmail()">'+
              '<fieldset><legend class="vh">SuscrÃ­bete</legend>'+
                '<label for="email">Mantente informado. Suscr&iacute;bete.</label>'+
                '<div class="form-group-icon">'+
                  '<input type="text" class="form-control" id="email" name="email" aria-label="Ingresa tu correo electronico" placeholder="usuario@ejemplo.com">'+
                  '<button id="subscribe" type="button" class="blue-right btn"><span class="vh">SuscrÃ­bete</span><i class="icon-caret-right"></i></button>'+
                '</div>'+
                '<div class="message-subscribe hidden"></div>'+
              '</fieldset>'+
            '</form>'+
          '</div>'+
          '<div class="col-sm-4 col-sm-offset-4">'+
            '<h5>S&iacute;guenos en</h5>'+
            '<ul class="list-inline">'+
              '<li><a class="social-icon share-info facebook" target="_blank" href="https://www.facebook.com/gobmx" aria-label="Facebook de presidencia"></a></li>'+
              '<li><a class="social-icon  share-info twitter" target="_blank" href="https://twitter.com/gobmx" aria-label="Twitter de presidencia"></a></li>'+
            '</ul>'+
          '</div>'+
        '</div>'+
      '</div>'+
    '</div>'+
    '<div class="container">'+
      '<div class="row">'+
        '<div class="col-sm-4">'+
          '<img class="gobmx-footer" src="' + MX.imagesPath + 'gobmxlogo.svg" width="126" height="39" alt="PÃ¡gina de inicio, Gobierno de MÃ©xico">'+
        '</div>'+
        '<div class="col-sm-4 col-sm-offset-4">'+          
        '</div>'+       
      '</div>'+
    '</div>'+
    '<img src="https://sb.scorecardresearch.com/p?c1=2&c2=17183199&ns_site=' + MX.comscore + '&name=' + MX.trackingID + '" height="1" width="1" alt="*" class="vh">'
  '</footer>';

	body.prepend(navBar);
	body.append(footer);

  /* --- boton de suscribete --- */

  $('body').on( 'click', '#subscribe', function(e){
    e.preventDefault();
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var dataString = $('#email').serialize();
    var email = $('#email').val();
    var url = MX.emailService;
    var successMessage = 'Agradecemos tu registro dentro de gob.mx. De esta manera estar&aacute;s informado sobre las principales acciones y decisiones del Gobierno de la Rep&uacute;blica.<br><br>Gracias por tu inter&eacute;s y colaboraci&oacute;n. Juntos construimos <a href="/">gob.mx</a>';
    var registeredMessage = 'Este correo electr&oacute;nico ya ha sido registrado.';
    var failMessage = 'No se pudo registrar el correo electr&oacute;nico.';
    var validEmail = 'Ingrese un email v&aacute;lido';

    if($.trim(email).length == 0 || re.test(email) === false) {
        $('.message-subscribe').removeClass("hidden").html("<span role='alert' onclick='this.remove(); $(\"#email\").val(\"\")'>Hubo un error. <a href='javascript:void(0)'>Por favor validar su correo electrÃ³nico</a>.</span>");
        return false;
    } else {
        $('.message-subscribe').addClass("hidden").html();
        $.ajax({
            type: 'POST',
            data: { email: { address: $('#email').val() } },
            dataType: 'json',
            url: url
        }).done( function( data ) {
            if( data.status == 'error') {
              $('.message-subscribe').removeClass("hidden").html(registeredMessage);
            } else {
              $('#email').val('');
              $('#email').prop('disabled', true);
              $('.message-subscribe').removeClass("hidden").html(successMessage);
            }
        }).fail( function() {
            $('.message-subscribe').removeClass("hidden").html(failMessage);
        });
    }
    return false;
  });

};

$(function(){
	MX.secBuilder();
  if (!Modernizr.svg) {
      var imgs = document.getElementsByTagName('img');
      var svgExtension = /.*\.svg$/;
      var l = imgs.length;
      for(var i = 0; i < l; i++) {
          if(imgs[i].src.match(svgExtension)) {
              imgs[i].src = imgs[i].src.slice(0, -3) + 'png';
          }
      }
  }

  /**
   * Vertically center Bootstrap 3 modals so they aren't always stuck at the top
   */
  function reposition() {
    var modal = $(this),
        dialog = modal.find('.modal-dialog');
    modal.css('display', 'block');

    // Dividing by two centers the modal exactly, but dividing by three
    // or four works better for larger screens.
    dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 2));
  }

  // Reposition when a modal is shown
  $('.modal').on('show.bs.modal', reposition);

  // Reposition when the window is resized
  $(window).on('resize', function() {
    $('.modal:visible').each(reposition);
  });

});
function validateEmail(){
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var dataString = $('#email').serialize();
    var email = $('#email').val();
    var url = MX.emailService;
    var successMessage = 'Agradecemos tu registro dentro de gob.mx. De esta manera estar&aacute;s informado sobre las principales acciones y decisiones del Gobierno de la Rep&uacute;blica.<br><br>Gracias por tu inter&eacute;s y colaboraci&oacute;n. Juntos construimos <a href="/">gob.mx</a>';
    var registeredMessage = 'Este correo electr&oacute;nico ya ha sido registrado.';
    var failMessage = 'No se pudo registrar el correo electr&oacute;nico.';
    var validEmail = 'Ingrese un email v&aacute;lido';

    if($.trim(email).length == 0 || re.test(email) === false) {
        $('.message-subscribe').removeClass("hidden").html("<span role='alert' onclick='this.remove(); $(\"#email\").val(\"\")'>Hubo un error. <a href='javascript:void(0)'>Por favor validar su correo electrÃ³nico</a>.</span>");
        return false;
    } else {
        $('.message-subscribe').addClass("hidden").html();
        $.ajax({
            type: 'POST',
            data: { email: { address: $('#email').val() } },
            dataType: 'json',
            url: url
        }).done( function( data ) {
            if( data.status == 'error') {
                $('.message-subscribe').removeClass("hidden").html(registeredMessage);
            } else {
                $('#email').val('');
                $('#email').prop('disabled', true);
                $('.message-subscribe').removeClass("hidden").html(successMessage);
            }
        }).fail( function() {
            $('.message-subscribe').removeClass("hidden").html(failMessage);
        });
    }
    return false;
}
