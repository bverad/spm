<%@ page import="cl.adexus.isl.spm.helpers.TaskHelper"%>

<g:form name="dp01" class="pure-form pure-form-stacked">
	<fieldset>
		<legend>Inbox</legend>

		<g:if test="${groupTasks}">
			<g:if test="${groupTasks.size() > 0 }">
				<legend>Tareas grupales</legend>
				<div class="pure-u-6-8" style="padding-top: 20px;">
					</br><table id="tablaTareasGrupo" class="pure-table" width="80%">
						<thead>
							<tr>
								<th>Id</th>
								<th>Proceso</th>
								<th>Nombre</th>
								<th>Datos</th>
								<th>Region</th>
								<th>Sucursal</th>
								<th>Usuario originador</th>
								<th>Acción</th>
							</tr>
						</thead>
						<tbody>
							<g:each var="task" in="${groupTasks}" status="i">
								<tr class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
									<td>${task.id}</td>
									<td>
										${TaskHelper.processName(task.processId)}
									</td>
									<td>
										${TaskHelper.taskName(task.processId+'.'+task.name)}
									</td>
									<td><ul>
											<g:each var="dato" in="${taskData[task.id]}">
												<li>
													${dato.label}: ${dato.valor}
												</li>
											</g:each>
										</ul></td>
									<td>${sucursalData[task.id].region}</td>
									<td>${sucursalData[task.id].sucursal}</td>
									<td>${sucursalData[task.id].usuario}</td>	
									
									<td><g:link action="claimTask"
											style="text-decoration: none;" id="${task.id}">[Asignar Tarea]</g:link>
									</td>
								</tr>
							</g:each>
						</tbody>
					</table></br>
				</div>
			</g:if>
		</g:if>
		
		<g:else>
			<legend>Tareas grupales</legend>
			</br><table id="noGroupTasksTable" class="pure-table" width="80%">
				<thead>
					<tr>
						<th>Proceso</th>
						<th>Nombre</th>
						<th>Datos</th>
						<th>Acción</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="4" style="text-align:center">No existen tareas grupales</td>
					</tr>
				</tbody>
			</table>
		</g:else>




		<g:if test="${personalTasks}">
			<g:if test="${personalTasks.size() > 0 }">
				<legend>Tareas personales</legend>
				<div class="pure-u-6-8" style="padding-top: 20px;">
					</br><table id="tablaTareasPersonales" class="pure-table" width="80%">
						<thead>
							<tr>
								<th>Proceso</th>
								<th>Nombre Tarea</th>
								<th>Datos</th>
								<th>Acción</th>
							</tr>
						</thead>
						<tbody>
							<g:each var="task" in="${personalTasks}" status="i">
								<tr
									class="${(i % 2) == 0 ? 'pure-table-odd' : 'pure-table-nodd'}">
									<td>
										${TaskHelper.processName(task.processId)}
									</td>
									<td>
										${TaskHelper.taskName(task.processId+'.'+task.name)}
									</td>
									<td><ul>
											<g:each var="dato" in="${taskData[task.id]}">
												<li>
													${dato.label}: ${dato.valor}
												</li>
											</g:each>
										</ul></td>
									<td><g:link action="resolveTask"
											style="text-decoration: none;" id="${task.id}">[Resolver]</g:link>
									</td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</div>
			</g:if>
		</g:if>
		<g:else>
			<legend>Tareas personales</legend>
			</br><table id="noGroupTasksTable" class="pure-table" width="80%">
				<thead>
					<tr>
						<th>Proceso</th>
						<th>Nombre tarea</th>
						<th>Datos</th>
						<th>Acción</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="4" style="text-align:center">No existen tareas personales</td>
					</tr>
				</tbody>
			</table>
		</g:else>
	</fieldset>
</g:form>
<!-- Despliegue de informacion -->
<g:if test="${flash.default}">
	<fieldset>
		<div class="pure-u-1 messages">
			<ul>
				<li><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></li>
			</ul>
		</div>
	</fieldset>
</g:if>
