package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class SystemFeatures {

    @Id
    private String id;

    private String SRSid;

    private String datemodified;

    private String employeeid;

    private String status;

    private String SBAcomment;

    private String PMcomment;

    private ArrayList<Feature> featureset;

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

    public ArrayList<Feature> getFeatureset() {
        return featureset;
    }

    public void setFeatureset(ArrayList<Feature> featureset) {
        this.featureset = featureset;
    }
}
