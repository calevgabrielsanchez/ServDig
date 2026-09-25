var $j = jQuery.noConflict();
$j(document).ready(function() {

$j('.items .box img').each(function() {$j(this).hover(function() {$j(this).stop().animate({ opacity: 0.7 }, 500);},
           function() {$j(this).stop().animate({ opacity: 1.0 }, 500);});
});


$j("div.portfolio_box_holder img.portfolio_overlay").hide();
$j("div.portfolio_box_holder").hover(function() {$j(this).find('img').fadeIn(500);}, function() {$j(this).find('img.portfolio_overlay').fadeOut(500);});

<!--prettyPhoto -->
$j("a[rel^='prettyPhoto']").prettyPhoto({theme:'facebook'});

<!--Menu -->
$j("ul.menu").superfish();

});

Cufon.replace('h1, h2, h3, h4, h5, h6', {hover: true});
