package lk.ac.mrt.itfac.aurors.BA_Management_System.dto;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class Employee {

    @Id
    private String id;

    private String employeeid;

    private String fname;

    private  String lname;

    private String password;

    private String type;

    private String email;

    private String username;

    private String contact1;

    private String contact2;

    private String addressno;

    private String street;

    private String city;

    private String status;

    private String about;

    private String image;

    private String passstatus;

    private String gradestatus;

    public String getGradestatus() {
        return gradestatus;
    }

    public void setGradestatus(String gradestatus) {
        this.gradestatus = gradestatus;
    }

    public String getPassstatus() {
        return passstatus;
    }

    public void setPassstatus(String passstatus) {
        this.passstatus = passstatus;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getAddressno() {
        return addressno;
    }

    public void setAddressno(String addressno) {
        this.addressno = addressno;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
