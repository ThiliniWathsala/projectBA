package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification")
public class Notification {

    @Id
    private String id;

    private String notificationid;

    private String employeeid;

    private String content;

    private String date;

    private String time;

    private String status;

    private String type;

    private String typeid;

    private String chatname;

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getEmplyeeid() {
        return employeeid;
    }

    public void setEmplyeeid(String emplyeeid) {
        this.employeeid = emplyeeid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
