<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><g:layoutTitle default="ISL :: Sistema de Prestaciones Médicas"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<g:javascript src="yui-3.11.0.min.js" />
			
		<meta name="description" content="ISL Sistema de Prestaciones Médicas" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		
		<!--[if lt IE 8]>
		<link rel="stylesheet" href="${resource(dir: 'font-awesome/css', file: 'font-awesome-ie7.min.css')}" type="text/css">
		<![endif]-->
		
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon" />
		
		<style type="text/css">		
			body {
				background-color:#EEEEEE;
			}
		</style>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'pure-min.css')}" type="text/css" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'site-isl.css')}" type="text/css" />
		<link rel="stylesheet" href="${resource(dir: 'font-awesome', file: 'css/font-awesome.min.css')}" type="text/css" />
		<resource:dateChooser />
				
	    <r:layoutResources/>	
	</head>
	
	<body class="yui3-skin-sam"> <!-- class="yui3-skin-sam"  -->
	<div class="content">
    	
		<!-- splash -->
		
	   <!--splash -->
	   
	   <!-- +++++++++++++++++++++++++++++ WORKAREA ++++++++++++++++++++++++++++++ -->						
		<div class="pure-g-r workarea-modal">		
			<div class="pure-u-1">
            	<g:layoutBody/>
            </div>
			<!-- +++++++++++++++++++++++++ MENSAJES-INFO +++++++++++++++++++++++ -->				
			<g:if test="${flash.mensajes}">
				<div class="pure-u-1 messages-info">
    				<i class="icon-exclamation icon-2x pull-left icon-muted"></i> ${flash.mensajes}
    			</div>
			</g:if>
					
			<!-- +++++++++++++++++++++++++ MENSAJES-INFO +++++++++++++++++++++++ -->	
			<!-- +++++++++++++++++++++++++ MENSAJES-ERROR +++++++++++++++++++++++ -->	
			<g:hasErrors>
				<div class="pure-u-1 messages">
			  		<g:renderErrors as="list" />
				</div>
			</g:hasErrors>
			<!-- +++++++++++++++++++++++++ MENSAJES-ERROR +++++++++++++++++++++++ -->	
			
		</div>
		<!-- +++++++++++++++++++++++++++++ WORKAREA +++++++++++++++++++++++++++++ -->
	   
		<div id="yui3-css-stamp" style="position: absolute !important; visibility: hidden !important"></div>
	</div>
	
    <r:layoutResources/>
	</body>

</html>