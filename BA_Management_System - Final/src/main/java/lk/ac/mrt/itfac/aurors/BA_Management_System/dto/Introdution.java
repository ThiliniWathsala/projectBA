package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;

public class Introdution {

    @Id
    private String id;

    private String SRSid;

    private String datemodified;

    private String employeeid;

    private String purpose;

    private String specifications;

    private String suggestions;

    private String audience;

    private String benefits;

    private String goal;

    private String objectives;

    private String reference;

    private String status;

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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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
