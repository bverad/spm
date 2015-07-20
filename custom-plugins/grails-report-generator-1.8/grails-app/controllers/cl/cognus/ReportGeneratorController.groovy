package cl.cognus

class ReportGeneratorController {

    def reportGeneratorService


    def index() {
        [command: new ReportGeneratorCommand()]
    }

    def report(ReportGeneratorCommand command) {

        if (command.hasErrors()) {
            render(view: "index", model: [command: command])
            return
        }

        def reportParams = [:]
        command.keyValue.each { String line ->
            if (line) {
                String[] kv = line.split("=")
                if (kv.size() == 2) {
                    reportParams[kv[0]] = kv[1]
                }
            }
        }
        File tempFile = File.createTempFile("report", "generator")
        try {
            reportGeneratorService.generate(command.fileName, command.reportName, reportParams, command.type, tempFile)

            def fis = new FileInputStream(tempFile)
            if (command.contentDisposition == ContentDisposition.ATTACHMENT) {
                response.setHeader "Content-disposition", "attachment; filename=${command.fileName}"
            }
            response.contentType = command.type.mimeType
            response.outputStream << fis
            response.outputStream.flush()
        } catch (e) {
            flash.message = e.getCause()
            redirect action: 'index', command: command
            return
        }
    }

}

class ReportGeneratorCommand {
    ReportOutputType type
    ContentDisposition contentDisposition
    String reportName
    String fileName
    List<String> keyValue

    static constraints = {
        type nullable: false
        contentDisposition nullable: false
        reportName blank: false, maxSize: 255
        fileName blank: false, maxSize: 255
    }
}


