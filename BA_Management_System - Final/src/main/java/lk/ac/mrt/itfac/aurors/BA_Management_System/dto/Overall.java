package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;

public class Overall {

    @Id
    private String id;

    private String SRSid;

    private String datemodified;

    private String employeeid;

    private String type;

    private String description;

    private String diagram1;

    private String specification;

    private String diagram2;

    private String usercase;

    private String software;

    private String hardware;

    private String constraints;

    private String documents;

    private String assumptions;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiagram1() {
        return diagram1;
    }

    public void setDiagram1(String diagram1) {
        this.diagram1 = diagram1;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDiagram2() {
        return diagram2;
    }

    public void setDiagram2(String diagram2) {
        this.diagram2 = diagram2;
    }

    public String getUsercase() {
        return usercase;
    }

    public void setUsercase(String usercase) {
        this.usercase = usercase;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getAssumptions() {
        return assumptions;
    }

    public void setAssumptions(String assumptions) {
        this.assumptions = assumptions;
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
