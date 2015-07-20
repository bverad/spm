package cl.cognus



/**
 * Created by edavis on 2/3/14.
 */
public enum ReportOutputType {

    PDF('pageable/pdf', 'application/pdf'),
    XLSX('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;page-mode=flow', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'),
    CSV('table/csv;page-mode=stream', 'text/csv')

    private String target
    private String mimeType

    ReportOutputType(String target, String mimeType) {
        this.target = target
        this.mimeType = mimeType
    }

    String getTarget() {
        return this.target
    }

    String getMimeType() {

        return this.mimeType
    }

    String getKey() {
        return name()
    }


}