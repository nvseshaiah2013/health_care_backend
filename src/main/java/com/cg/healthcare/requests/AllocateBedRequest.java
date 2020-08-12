package com.cg.healthcare.requests;

import java.util.List;

public class AllocateBedRequest {
    private int diagnosticId;
    private List<Integer> waitingPatientIds;
    String type;

    public AllocateBedRequest()
    {

    }

    public AllocateBedRequest(int diagnosticId, List<Integer> waitingPatientIds, String type){
        this.diagnosticId = diagnosticId;
        this.type = type;
        this.waitingPatientIds = waitingPatientIds;
    }

    public int getDiagnosticId() {
        return diagnosticId;
    }

    public void setDiagnosticId(int diagnosticId) {
        this.diagnosticId = diagnosticId;
    }

    public List<Integer> getWaitingPatientIds() {
        return waitingPatientIds;
    }

    public void setWaitingPatientIds(List<Integer> waitingPatientIds) {
        this.waitingPatientIds = waitingPatientIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AllocateBedRequest [diagnosticId=" + diagnosticId + ", type=" + type + ", waitingPatientIds="
                + waitingPatientIds + "]";
    }

    
    
}