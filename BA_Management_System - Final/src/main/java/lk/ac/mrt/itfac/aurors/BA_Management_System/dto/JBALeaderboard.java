package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jbaleaderboard")
public class JBALeaderboard {

    @Id
    private String id;

    private String employeeid;

    private String name;

    private String customergrade;

    private String lbagrade;

    private String systemgrade;

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

    public String getCustomergrade() {
        return customergrade;
    }

    public void setCustomergrade(String customergrade) {
        this.customergrade = customergrade;
    }

    public String getLbagrade() {
        return lbagrade;
    }

    public void setLbagrade(String lbagrade) {
        this.lbagrade = lbagrade;
    }

    public String getSystemgrade() {
        return systemgrade;
    }

    public void setSystemgrade(String systemgrade) {
        this.systemgrade = systemgrade;
    }
}
