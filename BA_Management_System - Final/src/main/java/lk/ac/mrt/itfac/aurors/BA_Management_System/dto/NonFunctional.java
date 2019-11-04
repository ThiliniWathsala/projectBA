package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;

public class NonFunctional {

    @Id
    private String id;

    private String SRSid;

    private String datemodified;

    private String employeeid;

    private String performance;

    private String damages;

    private String safety;

    private String security;

    private String description;

    private String reference;

    private String status;

    private String attribute;

    private String SBAcomment;

    private String PMcomment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSRSid() {
        return SRSid;
    }

    public void setSRSid(String SRSid) {
        this.SRSid = SRSid;
    }

    public String getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(String datemodified) {
        this.datemodified = datemodified;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getDamages() {
        return damages;
    }

    public void setDamages(String damages) {
        this.damages = damages;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSBAcomment() {
        return SBAcomment;
    }

    public void setSBAcomment(String SBAcomment) {
        this.SBAcomment = SBAcomment;
    }

    public String getPMcomment() {
        return PMcomment;
    }

    public void setPMcomment(String PMcomment) {
        this.PMcomment = PMcomment;
    }
}
