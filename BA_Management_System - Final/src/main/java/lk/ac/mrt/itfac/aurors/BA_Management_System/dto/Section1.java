package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "section1")
public class Section1 {

    @Id
    private String id;

    private String SRSid;

    private String datemodified;

    private String employeeid;

    private String input;

    private String output;

    private String user;

    private String userstory;

    private String objective;

    private String SBAcomment1;

    private String PMcomment1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSBAcomment1() {
        return SBAcomment1;
    }

    public void setSBAcomment1(String SBAcomment1) {
        this.SBAcomment1 = SBAcomment1;
    }

    public String getPMcomment1() {
        return PMcomment1;
    }

    public void setPMcomment1(String PMcomment1) {
        this.PMcomment1 = PMcomment1;
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

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserstory() {
        return userstory;
    }

    public void setUserstory(String userstory) {
        this.userstory = userstory;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

}
