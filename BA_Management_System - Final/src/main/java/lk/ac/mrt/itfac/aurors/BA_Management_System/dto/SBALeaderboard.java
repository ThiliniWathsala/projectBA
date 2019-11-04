package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sbaleaderboard")
public class SBALeaderboard {

    @Id
    private String id;

    private String employeeid;

    private String name;

    private String pmgrade;

    private String qagrade;

    private String devgrade;

    private String overall;

    private String projectcount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectcount() {
        return projectcount;
    }

    public void setProjectcount(String projectcount) {
        this.projectcount = projectcount;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getPmgrade() {
        return pmgrade;
    }

    public void setPmgrade(String pmgrade) {
        this.pmgrade = pmgrade;
    }

    public String getQagrade() {
        return qagrade;
    }

    public void setQagrade(String qagrade) {
        this.qagrade = qagrade;
    }

    public String getDevgrade() {
        return devgrade;
    }

    public void setDevgrade(String devgrade) {
        this.devgrade = devgrade;
    }

    }
