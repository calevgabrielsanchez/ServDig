<style>
	
	
#selectable .ui-selecting { background: #FECA40; }
#selectable .ui-selected { background: #F39814; color: white; }
#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }

#tabs_wrapper {
	width: 422px;
	background: white;
}
a:active {
	outline: none;
}
a:focus {
	-moz-outline-style: none;
}
#tabs_container {
	width: 100%;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;		
}
#tabs_container ul.tabs {
	background: white;
	list-style: none;
	padding: 5px 0 4px 0;
	font: arial;
	margin: 0 0 0 10px;
}
#tabs_container ul.tabs li {
	cursor:pointer;
	float: left;
}
#tabs_container ul.tabs li a {
	padding: 3px 10px;
	display: block;
	border-left: 1px solid #ccc;
	border-top: 1px solid #ccc;
	border-right: 1px solid #ccc;
	margin-right: 2px;
	text-decoration: none;color:white;
	background-color: #0A6659;
}
#tabs_container ul.tabs li.active a {
	text-decoration:none;color:#0A6659;
	background-color: #fff;
	padding-top: 4px;
}
div.tab_contents_container {
	border: 1px solid #ccc;
	border-top: none;
	padding: 10px;
	width: 100%;

}
div.tab_contents {
	display: none;
}
div.tab_contents_active {
	display: block;
}
div.clear {
	clear: both;
}

.etabs { margin: 0; padding: 0; }
.tab { display: inline-block; zoom:1; *display:inline; background: #eee; border: solid 1px #0A6659; border-bottom: none; -moz-border-radius: 4px 4px 0 0; -webkit-border-radius: 4px 4px 0 0; }
.tab a { font-size: 14px; line-height: 2em; display: block; padding: 0 10px; outline: none; }
.tab a:hover { text-decoration: underline; }
.tab.active { background: #fff; padding-top: 6px; position: relative; top: 1px; border-color: #665; }
.tab a.active { font-weight: bold; }
.tab-container .panel-container { background: #fff; border: solid #666 1px; padding: 10px; -moz-border-radius: 0 4px 4px 4px; -webkit-border-radius: 0 4px 4px 4px; }

</style>
