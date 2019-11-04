package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "srs")
public class SRS {

    @Id
    private String id;

    private String SRSid;

    private String projectid;

    private String approveddate;

    private String state;

    private String jba1status;

    private String jba2status;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJba1status() {
        return jba1status;
    }

    public void setJba1status(String jba1status) {
        this.jba1status = jba1status;
    }

    public String getJba2status() {
        return jba2status;
    }

    public void setJba2status(String jba2status) {
        this.jba2status = jba2status;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(String approveddate) {
        this.approveddate = approveddate;
    }
}
