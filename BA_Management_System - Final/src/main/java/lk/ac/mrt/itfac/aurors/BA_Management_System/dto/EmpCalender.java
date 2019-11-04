package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

public class EmpCalender {

    private String leaveid;

    private String employeeid;

    private String name;

    private String type;

    private String date;

    private String reason;

    public String getLeaveid() {
        return leaveid;
    }

    public void setLeaveid(String leaveid) {
        this.leaveid = leaveid;
    }


        public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
