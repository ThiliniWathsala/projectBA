package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "section2")
public class Section2 {

    @Id
    private String id;

    private String SRSid;

    private String datemodified;

    private String employeeid;

    private String systemspecification;

    private String networkrequirement;

    private String technicalrequirement;

    private String additionalnote;

    private String securitylevelspecification;

    private String SBAcomment2;

    private String PMcomment2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSBAcomment2() {
        return SBAcomment2;
    }

    public void setSBAcomment2(String SBAcomment2) {
        this.SBAcomment2 = SBAcomment2;
    }

    public String getPMcomment2() {
        return PMcomment2;
    }

    public void setPMcomment2(String PMcomment2) {
        this.PMcomment2 = PMcomment2;
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

    public String getSystemspecification() {
        return systemspecification;
    }

    public void setSystemspecification(String systemspecification) {
        this.systemspecification = systemspecification;
    }

    public String getNetworkrequirement() {
        return networkrequirement;
    }

    public void setNetworkrequirement(String networkrequirement) {
        this.networkrequirement = networkrequirement;
    }

    public String getTechnicalrequirement() {
        return technicalrequirement;
    }

    public void setTechnicalrequirement(String technicalrequirement) {
        this.technicalrequirement = technicalrequirement;
    }

    public String getAdditionalnote() {
        return additionalnote;
    }

    public void setAdditionalnote(String additionalnote) {
        this.additionalnote = additionalnote;
    }

    public String getSecuritylevelspecification() {
        return securitylevelspecification;
    }

    public void setSecuritylevelspecification(String securitylevelspecification) {
        this.securitylevelspecification = securitylevelspecification;
    }

}
