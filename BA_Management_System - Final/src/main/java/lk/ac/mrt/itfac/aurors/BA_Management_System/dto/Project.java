package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "project")
public class Project {

    @Id
    private String id;

    private String projectid;

    private String status;

    private String name;

    private String pm;

    private String lba;

    private String customerid;

    private String jba1;

    private String jba2;

    private String dev1;

    private String dev2;

    private String dev3;

    private String dev4;

    private String dev5;

    private String description;

    private String startdate;

    private String enddate;

    private String lastupdated;

    private String days;

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getLba() {
        return lba;
    }

    public void setLba(String lba) {
        this.lba = lba;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customer) {
        this.customerid = customer;
    }

    public String getJba1() {
        return jba1;
    }

    public void setJba1(String jba1) {
        this.jba1 = jba1;
    }

    public String getJba2() {
        return jba2;
    }

    public void setJba2(String jba2) {
        this.jba2 = jba2;
    }

    public String getDev1() {
        return dev1;
    }

    public void setDev1(String dev1) {
        this.dev1 = dev1;
    }

    public String getDev2() {
        return dev2;
    }

    public void setDev2(String dev2) {
        this.dev2 = dev2;
    }

    public String getDev3() {
        return dev3;
    }

    public void setDev3(String dev3) {
        this.dev3 = dev3;
    }

    public String getDev4() {
        return dev4;
    }

    public void setDev4(String dev4) {
        this.dev4 = dev4;
    }

    public String getDev5() {
        return dev5;
    }

    public void setDev5(String dev5) {
        this.dev5 = dev5;
    }
}
