package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;

public class InterfaceDesign {

    @Id
    private String id;

    private String SRSid;

    private String datemodified;

    private String employeeid;

    private String display;

    private String attachment;

    private String specifications;

    private String connections;

    private String network;

    private String encryption;

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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
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
