package org.myhomeapps.walkers.validators;

import java.util.Collection;

public class GraphIssuesReportBuilder implements ValidationReportBuilder {

    private Collection<? extends GraphIssue> issues;

    public GraphIssuesReportBuilder(Collection<? extends GraphIssue> issues) {
        this.issues = issues;
    }

    @Override
    public String buildValidationReport() {
        StringBuffer stringBuffer = new StringBuffer();
        issues.forEach(graphIssue -> {
            stringBuffer.append("\n" + graphIssue.getName() + ":\n");
            graphIssue.getOccurrences().forEach(occurrence -> stringBuffer.append("\t" + occurrence + "\n"));
        });
        return stringBuffer.toString();
    }
}
