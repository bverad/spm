  <g:form action="signIn" class="pure-form pure-form-stacked" >
    <input type="hidden" name="targetUri" value="${targetUri}" />
    <fieldset>
 		<legend>Ingreso al Sistema de Prestaciones Médicas</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">
				<isl:textInput cols="1-8" rotulo="Usuario"  nombre="username" value="${username}" />
				<isl:textInput cols="1-8" rotulo="Clave" nombre="password" value="" tipo="password"/>
			</div>
		</div>
	</fieldset>
	
	<div class="pure-g-r">		
		<input type="submit" value="Ingresar" class="pure-button pure-button-success" />
	</div>
</g:form>