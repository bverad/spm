<%@ page import="cl.cognus.ContentDisposition; cl.cognus.ReportOutputType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Report Generator</title>
    <meta name="layout" content="main">
</head>

<body>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:form action="report">
    <fieldset class="form">
        <div class="fieldcontain ${hasErrors(bean: command, field: 'fileName', 'error')} ">
            <label for="fileName">
                Nombre del archivo
            </label>
            <g:textField name="fileName" value="${command?.fileName}"/>
        </div>

        <div class="fieldcontain ${hasErrors(bean: command, field: 'reportName', 'error')} ">
            <label for="reportName">
                Nombre reporte
            </label>
            <g:textField name="reportName" value="${command?.reportName}"/>
        </div>

        <div class="fieldcontain ${hasErrors(bean: command, field: 'type', 'error')} ">
            <label for="type">
                Formato salida
            </label>
            <g:select name="type" from="${ReportOutputType.values()}" optionValue="key" value="${command?.type}"
                      optionKey="key"/>
        </div>

        <div class="fieldcontain ${hasErrors(bean: command, field: 'contentDisposition', 'error')} ">
            <label for="contentDisposition">
                Disposición de salida
            </label>
            <g:select name="contentDisposition" from="${ContentDisposition.values()}" optionValue="key" value="${command?.contentDisposition}"
                      optionKey="key"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>

        <div class="fieldcontain ">
            <label for="type">
                Parámetro del reporte
            </label>
            <g:textField name="keyValue" placeholder="llave=valor"/>
        </div>
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="create" class="save" value="Generar"/>
    </fieldset>
    </table>
</g:form>

</body>
</html>