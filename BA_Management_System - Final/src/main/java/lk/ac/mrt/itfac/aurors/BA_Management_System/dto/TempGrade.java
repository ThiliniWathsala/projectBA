package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tempgrade")
public class TempGrade {

    @Id
    private String id;

    private String projectid;

    private String customergrade;

    private String customergrade1;

    private String customergrade2;

    private String systemgrade1;

    private String sbagrade1;

    private String systemgrade2;

    private String sbagrade2;

    private String qagrade;

    private String pmgrade;

    private String devcount;

    private String status;

    private String dev1grade;

    private String dev2grade;

    private String dev3grade;

    private String dev4grade;

    private String dev5grade;

    private String devgrade;

    private String employeeid;

    public String getCustomergrade1() {
        return customergrade1;
    }

    public void setCustomergrade1(String customergrade1) {
        this.customergrade1 = customergrade1;
    }

    public String getCustomergrade2() {
        return customergrade2;
    }

    public void setCustomergrade2(String customergrade2) {
        this.customergrade2 = customergrade2;
    }

    public String getDevgrade() {
        return devgrade;
    }

    public void setDevgrade(String devgrade) {
        this.devgrade = devgrade;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public String getDev1grade() {
        return dev1grade;
    }

    public void setDev1grade(String dev1grade) {
        this.dev1grade = dev1grade;
    }

    public String getDev2grade() {
        return dev2grade;
    }

    public void setDev2grade(String dev2grade) {
        this.dev2grade = dev2grade;
    }

    public String getDev3grade() {
        return dev3grade;
    }

    public void setDev3grade(String dev3grade) {
        this.dev3grade = dev3grade;
    }

    public String getDev4grade() {
        return dev4grade;
    }

    public void setDev4grade(String dev4grade) {
        this.dev4grade = dev4grade;
    }

    public String getDev5grade() {
        return dev5grade;
    }

    public void setDev5grade(String dev5grade) {
        this.dev5grade = dev5grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevcount() {
        return devcount;
    }

    public void setDevcount(String devcount) {
        this.devcount = devcount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getCustomergrade() {
        return customergrade;
    }

    public void setCustomergrade(String customergrade1) {
        this.customergrade = customergrade;
    }

    public String getSystemgrade1() {
        return systemgrade1;
    }

    public void setSystemgrade1(String systemgrade1) {
        this.systemgrade1 = systemgrade1;
    }

    public String getSbagrade1() {
        return sbagrade1;
    }

    public void setSbagrade1(String sbagrade1) {
        this.sbagrade1 = sbagrade1;
    }

    public String getSystemgrade2() {
        return systemgrade2;
    }

    public void setSystemgrade2(String systemgrade2) {
        this.systemgrade2 = systemgrade2;
    }

    public String getSbagrade2() {
        return sbagrade2;
    }

    public void setSbagrade2(String sbagrade2) {
        this.sbagrade2 = sbagrade2;
    }

    public String getQagrade() {
        return qagrade;
    }

    public void setQagrade(String qagrade) {
        this.qagrade = qagrade;
    }

    public String getPmgrade() {
        return pmgrade;
    }

    public void setPmgrade(String pmgrade) {
        this.pmgrade = pmgrade;
    }

}
