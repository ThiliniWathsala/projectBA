package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

public class ChatMessage {

    private String empidowner;

    private String empid;

    private String chatid;

    private String ownername;

    private String empname;

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getEmpidowner() {
        return empidowner;
    }

    public void setEmpidowner(String empidowner) {
        this.empidowner = empidowner;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }
}
