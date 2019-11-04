package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
public class Chat {

    private String chatid;

    private String employee1;

    private String employee2;

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getEmployee1() {
        return employee1;
    }

    public void setEmployee1(String employee1) {
        this.employee1 = employee1;
    }

    public String getEmployee2() {
        return employee2;
    }

    public void setEmployee2(String employee2) {
        this.employee2 = employee2;
    }

}
