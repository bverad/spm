<g:form name="dp03" class="pure-form pure-form-stacked">
	<isl:header_sdaep sdaep="${sdaep}"/>
	<fieldset>
		<legend>Cuestionario de calificación de origen</legend>
		<div class="pure-g-r">
			<div class="pure-u-1">		    	
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta1" rotulo="¿Que trabajo realiza ud./el trabajador?" valor="${cuestionarioOrigen?.pregunta1}"/>
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta2" rotulo="Describa las actividades de su jornada habitual" valor="${cuestionarioOrigen?.pregunta2}"/>
				
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta3" rotulo="¿Que cantidad de trabajo realiza en una jornada habitual?" valor="${cuestionarioOrigen?.pregunta3}" />
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta4" rotulo="¿Hace cuanto tiempo que realiza estas tareas?" valor="${cuestionarioOrigen?.pregunta4}"/>
				
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta5" rotulo="Describa el malestar o dolor que denuncia" valor="${cuestionarioOrigen?.pregunta5}"/>
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta6" rotulo="Dentro de sus tareas ¿Cual es la situación que le provoca mas malestar o dolor?" valor="${cuestionarioOrigen?.pregunta6}"/>
				
				<isl:textArea cols="4-8" requerido="true" nombre="pregunta7" rotulo="¿Hace cuanto tiempo siente el dolor o malestar?" valor="${cuestionarioOrigen?.pregunta7}"/>
				<isl:radiogroup cols="4-8" nombre="codigo" name="codigo"
								rotulo="Esta denuncia de enfermedad profesional a que tipo de afección corresponde"
								values="${tipoEnfermedades.codigo}"
								labels="${tipoEnfermedades.descripcion}"
								requerido="true"
								valor="">
					${it.radio}
					${it.label}<br>
				</isl:radiogroup>
			</div>
		</div>
	</fieldset>	
	<div class="pure-g-r">
		<isl:button tipo="siguiente" action="cu03"/>
	</div>
</g:form>