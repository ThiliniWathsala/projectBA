package lk.ac.mrt.itfac.aurors.BA_Management_System.controller;

import com.itextpdf.text.DocumentException;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.service.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employee")
public class EmployeeController{

    static String image = null;
    static String overallpdf = null;
    static String displaypdf = null;

    @Autowired
    EmployeeMongoRepository userRepository;

    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    Email getemail;

    @Autowired
    Password getpassword;

    @Autowired
    Calculation calculation;

    @Autowired
    FileProcessor fileProcessor;

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    SBALeaderBoardMongoRepository sbaLeaderBoardRepository;

    @Autowired
    JBALeaderBoardMongoRepository jbaLeaderBoardRepository;

    @Autowired
    Availability availability;

    @Autowired
    CustomerMongoRepository customerMongoRepository;

    @Autowired
    ProjectMongoRepository projectMongoRepository;

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    ChatService chat;

    @Autowired
    MessageMongoRepository messageMongoRepository;

    @Autowired
    SBALeaderBoardSearchRepository sbaLeaderBoardSearchRepository;

    @Autowired
    ChatSearchRepository chatSearchRepository;

    @Autowired
    ChatMongoRepository chatMongoRepository;

    @Autowired
    CalenderService calenderService;

    @Autowired
    CalenderMongoRepository calenderMongoRepository;

    @Autowired
    CalenderSearchRepository calenderSearchRepository;

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/setstaticimage")
    public void staticImage(@RequestBody String empid)
    {
        EmployeeController.image = empid;
    }

    @RequestMapping(value = "/setstaticoverallpdf")
    public void staticOverallpdf(@RequestBody String projectid)
    {
        EmployeeController.overallpdf = projectid+"overall.pdf";
    }

    @RequestMapping(value = "/setstaticdisplaypdf")
    public void staticDisplaypdf(@RequestBody String projectid)
    {
        EmployeeController.displaypdf = projectid+"display.pdf";
    }

    //Creating a new user account

    @RequestMapping(value = "/register")
    public @ResponseBody Employee addUser(@RequestBody Employee employee) throws IOException {
        Collection<Employee> c = employeeSearchRepository.searchUserEmail(employee.getEmail());
        ArrayList<Employee> a = new ArrayList<>(c);
        if(a.isEmpty()) {
            String password = getpassword.generatePassword();
            employee.setPassword(getpassword.encryptPassword(password));
            employee.setEmployeeid(fileProcessor.generateEmpID());
            employee.setPassstatus("new");
            employee.setUsername("");
            employee.setStreet("");
            employee.setAbout("");
            employee.setContact1("");
            employee.setContact2("");
            employee.setCity("");
            employee.setAddressno("");
            employee.setStatus("3");
            employee.setGradestatus("changed");
            employee.setImage("default");
            userRepository.save(employee);
            JBALeaderboard jbal = new JBALeaderboard();
            SBALeaderboard sbal = new SBALeaderboard();
            if (employee.getType().equals("JBA")) {
                jbal.setSystemgrade("0.0");
                jbal.setLbagrade("0.0");
                jbal.setEmployeeid(employee.getEmployeeid());
                jbal.setCustomergrade("0.0");
                jbal.setProjectcount("0");
                jbal.setOverall("0.0");
                jbal.setName(employee.getFname() + " " + employee.getLname());
                jbaLeaderBoardRepository.save(jbal);
            } else if (employee.getType().equals("SBA")) {
                sbal.setDevgrade("0.0");
                sbal.setEmployeeid(employee.getEmployeeid());
                sbal.setPmgrade("0.0");
                sbal.setQagrade("0.0");
                sbal.setOverall("0.0");
                sbal.setProjectcount("0");
                sbal.setName(employee.getFname() + " " + employee.getLname());
                sbaLeaderBoardRepository.save(sbal);
            }
            getemail.newUserPasswordEmail(password, employee.getEmail(),employee.getFname()+" "+employee.getLname());

            Path FROM = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\default.jpg");
            Path TO = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\" + employee.getEmployeeid()+".jpg");
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(FROM, TO, options);

            return employee;
        }
        else {
            employee.setType(null);
            employee.setEmail(null);
            employee.setFname(null);
            employee.setLname(null);
            return employee;
        }
    }

    @RequestMapping("/editemployee")
    public @ResponseBody Employee editEmployee(@RequestBody Employee emp)
    {
        Collection<Employee> c = employeeSearchRepository.searchUserEmail(emp.getEmail());
        ArrayList<Employee> a = new ArrayList<>(c);
        a.get(0).setEmail(emp.getEmail());
        a.get(0).setType(emp.getType());
        a.get(0).setFname(emp.getFname());
        a.get(0).setLname(emp.getLname());
        userRepository.save(a.get(0));
        return emp;
    }

    //login to the user account

    @RequestMapping(value = "/loginnow")
    public @ResponseBody Employee loginnow(@RequestBody Employee emp){

        Collection<Employee> u = employeeSearchRepository.searchUserEmail(emp.getEmail());
        ArrayList<Employee> newList = new ArrayList<>(u);

        Collection<Employee> admin = employeeSearchRepository.getAdmin();
        ArrayList<Employee> adminarray = new ArrayList<>(admin);

//        System.out.println(pass);
//        System.out.println(emp.getEmail());

        if(!newList.isEmpty()) {
            int x = Integer.parseInt(newList.get(0).getStatus());
            String pass = newList.get(0).getPassword();

            if(getpassword.decryptPassword(emp.getPassword(),adminarray.get(0).getPassword()) && !emp.getEmail().equals(adminarray.get(0).getEmail()) && !getpassword.decryptPassword(emp.getPassword(),pass)) {
                newList.get(0).setEmail("admin");
                return newList.get(0);
            }

            if (x <= 0)
                emp.setEmail("expired");

            if (!emp.getEmail().equals("expired")) {
                if (getpassword.decryptPassword(emp.getPassword(), pass)) {
                    newList.get(0).setStatus("3");
                    userRepository.save(newList.get(0));
                    return newList.get(0);
                } else {
                    emp.setEmail(null);
                    if(!newList.get(0).getType().equals("admin")) {
                        int i = Integer.parseInt(newList.get(0).getStatus()) - 1;
                        String s = String.valueOf(i);
                        newList.get(0).setStatus(s);
                        userRepository.save(newList.get(0));
                        emp.setEmail(null);
                    }
                }
            }
        }
        else
            emp.setEmail(null);

        return emp;
    }

    //forgot password option (resetting the current password)

    @RequestMapping("/forgot")
    public @ResponseBody Employee sendPassword(@RequestBody Employee employee){

        Collection<Employee> ec = employeeSearchRepository.searchUserEmail(employee.getEmail());
        ArrayList<Employee> ea = new ArrayList<>(ec);

        if(ea.isEmpty())
            employee.setEmail(null);

        else if(Integer.parseInt(ea.get(0).getStatus())<=0)
            employee.setEmail("expired");

        else
        {
            String email = employee.getEmail();

            String password = getpassword.generatePassword();

            ea.get(0).setPassword(getpassword.encryptPassword(password));
            ea.get(0).setPassstatus("new");

            userRepository.save(ea.get(0));
            getemail.forgotPasswordEmail(password,email,ea.get(0).getFname()+" "+ea.get(0).getLname());
        }

        return employee;
    }

    //after the password is reset change of password in the next first login

    @RequestMapping("/newpassword")
    public @ResponseBody Employee newPassword(@RequestBody Employee e)
    {
        Collection<Employee> c = employeeSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<Employee> a = new ArrayList<>(c);
        String password = getpassword.encryptPassword(e.getPassword());
        a.get(0).setPassword(password);
        a.get(0).setPassstatus("changed");
        userRepository.save(a.get(0));
        return  a.get(0);
    }

    //get your own details to view in the profile

    @RequestMapping("/getempdetails")
    public @ResponseBody Employee getDetails(@RequestBody Employee e) throws IOException {
        Collection<Employee> c = employeeSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<Employee> a = new ArrayList<>(c);
        image = a.get(0).getEmployeeid();
        return a.get(0);
    }

    //edit the personal details in the profile

    @RequestMapping("/editprofile/personaldetails")
    public @ResponseBody Employee editPersonal(@RequestBody Employee e)
    {
        Collection<Employee> emp = employeeSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<Employee> a = new ArrayList<>(emp);
        a.get(0).setFname(e.getFname());
        a.get(0).setLname(e.getLname());
        a.get(0).setAbout(e.getAbout());
        a.get(0).setUsername(e.getUsername());
        userRepository.save(a.get(0));
        return a.get(0);
    }

    //edit the contact details in the profile

    @RequestMapping("/editprofile/contactdetails")
    public @ResponseBody Employee editContact(@RequestBody Employee e)
    {
        Collection<Employee> emp = employeeSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<Employee> a = new ArrayList<>(emp);
        a.get(0).setContact1(e.getContact1());
        a.get(0).setContact2(e.getContact2());
        a.get(0).setAddressno(e.getAddressno());
        a.get(0).setStreet(e.getStreet());
        a.get(0).setCity(e.getCity());
        a.get(0).setEmail(e.getEmail());
        userRepository.save(a.get(0));
        return a.get(0);
    }

    //edit the account credentials in the profile

    @RequestMapping("/editprofile/accountdetails")
    public @ResponseBody Employee editAccount(@RequestBody Employee e)
    {
        Collection<Employee> emp = employeeSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<Employee> a = new ArrayList<>(emp);
        a.get(0).setPassword(getpassword.encryptPassword(e.getPassword()));
        userRepository.save(a.get(0));
        return a.get(0);
    }

    //view all the employees working

    @RequestMapping("/employeelist")
    public @ResponseBody ArrayList<Employee> empList(@RequestBody Employee emp)
    {
        Iterable<Employee> c = userRepository.findAll();
        ArrayList<Employee> a = new ArrayList<Employee>();
        Iterator<Employee> i = c.iterator();

        while(i.hasNext())
        {
            Employee e = i.next();
            if(!e.getEmployeeid().equals(emp.getEmployeeid()))
                a.add(e);
        }
        return a;
    }

    //view a searched employee

    @RequestMapping("/searchemployee")
    public @ResponseBody ArrayList<Employee> empSearchList(@RequestBody Employee cm)
    {
        System.out.println("search working");
        Collection<Employee> c = employeeSearchRepository.searchEmp(cm.getFname());
        ArrayList<Employee> a = new ArrayList<>(c);
        ArrayList<Employee> anew = new ArrayList<Employee>();
        Iterator<Employee> i = a.iterator();
        while (i.hasNext())
        {
            Employee e = i.next();
            if(!e.getEmployeeid().equals(cm.getEmployeeid()))
                anew.add(e);
        }
        return anew;
    }

    //get the profile details of another

    @RequestMapping("/otherprofile")
    public @ResponseBody Employee giveOthersDetails(@RequestBody Employee e)
    {
        Collection<Employee> ec = employeeSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<Employee> ea = new ArrayList<>(ec);
        e.setFname(ea.get(0).getFname());
        e.setLname(ea.get(0).getLname());
        e.setAddressno(ea.get(0).getAddressno());
        e.setAbout(ea.get(0).getAbout());
        e.setCity(ea.get(0).getCity());
        e.setStreet(ea.get(0).getStreet());
        e.setType(ea.get(0).getType());
        e.setContact1(ea.get(0).getContact1());
        e.setContact2(ea.get(0).getContact2());
        e.setEmail(ea.get(0).getEmail());
        e.setUsername(ea.get(0).getUsername());
        return e;
    }

    //get the SBA leader board

    @RequestMapping("/sbaleaderboard")
    public @ResponseBody ArrayList<SBALeaderboard> viewSBALeaderboard()
    {
        Iterable<SBALeaderboard> i = sbaLeaderBoardRepository.findAll();
        Collection<SBALeaderboard> collection = new ArrayList<SBALeaderboard>();
        for (SBALeaderboard e : i) {
            collection.add(e);
        }
        ArrayList<SBALeaderboard> newList = new ArrayList<>(collection);
        return calculation.sbaSort(newList);
    }

    //get the JBA leader board

    @RequestMapping("/jbaleaderboard")
    public @ResponseBody ArrayList<JBALeaderboard> viewJBALeaderboard()
    {
        Iterable<JBALeaderboard> i = jbaLeaderBoardRepository.findAll();
        Collection<JBALeaderboard> collection = new ArrayList<JBALeaderboard>();
        for (JBALeaderboard e : i) {
            collection.add(e);
        }
        ArrayList<JBALeaderboard> newList = new ArrayList<>(collection);
        return calculation.jbaSort(newList);
    }

    //get the PM list to whom can an employee submit his leave request

    @RequestMapping("/pmlist")
    public @ResponseBody ArrayList<String> getPMList(@RequestBody Employee e)
    {
        return calenderService.getPM(e.getEmployeeid());
    }

    //submitting of a leave request

    @RequestMapping("/submitleave")
    public @ResponseBody Calender submitLeave(@RequestBody Calender c) throws ParseException {

        Collection<Project> pc = projectSearchRepository.current();
        ArrayList<Project> pa = new ArrayList<>(pc);
        Iterator<Project> pi = pa.iterator();

        if(c.getEnddate()==null || c.getEnddate().length()==0)
        {
            while (pi.hasNext()) {
                Project p = pi.next();

                if (p.getJba1().equals(c.getBa()) || p.getJba2().equals(c.getBa()) || p.getLba().equals(c.getBa())) {

                    c.setPm(p.getPm());
                    c.setStatus("0");
                    Date old = new SimpleDateFormat("yyyy-MM-dd").parse(c.getStartdate());
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String newdate = dateFormat.format(old);
                    c.setDate(newdate);
                    c.setStartdate(null);
                    c.setEnddate(null);
                    c.setLeaveid(calenderService.getLeaveNo());
                    calenderMongoRepository.save(c);
                    notificationManager.leaveRequest(c);
                }
            }
        }
        else
        {
            while (pi.hasNext()) {
                Project p = pi.next();

                if (p.getJba1().equals(c.getBa()) || p.getJba2().equals(c.getBa()) || p.getLba().equals(c.getBa())) {

                    Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(c.getStartdate());
                    Date enddate = new SimpleDateFormat("yyyy-MM-dd").parse(c.getEnddate());
                    Calendar startcalendar = new GregorianCalendar();
                    startcalendar.setTime(startdate);
                    Calendar endcalendar = new GregorianCalendar();
                    endcalendar.setTime(enddate);
                    endcalendar.add(Calendar.DATE, 1);
                    while (startcalendar.before(endcalendar)) {
                        Calender cal = new Calender();
                        Date result = startcalendar.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String newdate = dateFormat.format(result);
                        startcalendar.add(Calendar.DATE, 1);
                        cal.setDate(newdate);
                        cal.setStartdate(null);
                        cal.setEnddate(null);
                        cal.setPm(p.getPm());
                        cal.setStatus("0");
                        cal.setBa(c.getBa());
                        cal.setReason(c.getReason());
                        cal.setLeaveid(calenderService.getLeaveNo());
                        calenderMongoRepository.save(cal);
                        notificationManager.leaveRequest(cal);

                }
            }
            }
        }
        return c;
    }

    //viewing of the leave requests received to a PM

    @RequestMapping("/leavelist")
    public @ResponseBody ArrayList<EmpCalender> leaveList(@RequestBody Employee e)
    {
        return calenderService.getLeaveList(e.getEmployeeid());
    }

    //approving a leave request

    @RequestMapping("/approveleave")
    public @ResponseBody Calender approveLeave(@RequestBody Calender c)
    {
        return calenderService.pmApprove(c.getLeaveid());
    }

    //get the people who have chatted so far

    @RequestMapping("/chatlist")
    public @ResponseBody ArrayList<ChatMessage> getChatList(@RequestBody ChatMessage cm1)
    {
        Collection<Chat> c = chatSearchRepository.searchChats(cm1.getEmpidowner());
        ArrayList<Chat> a = new ArrayList<>(c);
        Iterator<Chat> i = a.iterator();
        String s = "null";
        ArrayList<ChatMessage> chm = new ArrayList<ChatMessage>();

        while(i.hasNext())
        {
            ChatMessage cm = new ChatMessage();
            Chat ch = i.next();
            System.out.println(ch.getEmployee1());
            System.out.println(ch.getEmployee2());
            if(ch.getEmployee2().equals(cm1.getEmpidowner()))
                s = ch.getEmployee1();
            else if(ch.getEmployee1().equals(cm1.getEmpidowner()))
                s = ch.getEmployee2();
            Collection<Employee> e = employeeSearchRepository.searchUserId(s);
            ArrayList<Employee> ae = new ArrayList<>(e);
            cm.setEmpname(ae.get(0).getFname()+" "+ae.get(0).getLname());
            cm.setEmpid(ae.get(0).getEmployeeid());
            cm.setChatid(ch.getChatid());
            chm.add(cm);
            System.out.println(cm.getEmpname());
        }
//        System.out.println(chm.get(0).getEmpname());
//        System.out.println(chm.get(1).getEmpname());
        return chm;
    }

    @RequestMapping("/leavedays")
    public @ResponseBody ArrayList<Calender> leaveDays(@RequestBody Employee e) throws ParseException {
        System.out.println("working");
        Collection<Calender> c = calenderSearchRepository.jbaList(e.getEmployeeid());
        ArrayList<Calender> a = new ArrayList<>(c);
        ArrayList<Calender> anew = new ArrayList<>();
        LocalDate d = LocalDate.now();
        int count = a.size(),i;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        for(i=count-1;i>=0;i--)
        {
            LocalDate date = LocalDate.parse(a.get(0).getDate(), formatter);

            if(d.isBefore(date) && a.get(i).getStatus().equals("1"))
                anew.add(a.get(i));
        }
        return anew;
    }

    @RequestMapping("/leavecancel")
    public void leaveCancel(@RequestBody Calender cal){
        System.out.println("working");
        Collection<Calender> c = calenderSearchRepository.searchID(cal.getLeaveid());
        ArrayList<Calender> a = new ArrayList<>(c);

        a.get(0).setStatus("2");
        calenderMongoRepository.save(a.get(0));
    }

    //to get the message list from the employee search option

    @RequestMapping("/messagelistfromsearch")
    public @ResponseBody ChatMessage getMessageListFromSearch(@RequestBody ChatMessage cm)
    {
        System.out.println(cm.getEmpidowner());
        String s = chat.findChat(cm.getEmpidowner(),cm.getEmpid());

        if(s.equals("null"))
        {
            cm.setChatid(chat.getChatNo());
            Chat ch = new Chat();
            ch.setChatid(cm.getChatid());
            ch.setEmployee1(cm.getEmpid());
            ch.setEmployee2(cm.getEmpidowner());
            chatMongoRepository.save(ch);
        }
        else
            cm.setChatid(s);
        Collection<Employee> c = employeeSearchRepository.searchUserId(cm.getEmpid());
        ArrayList<Employee> a = new ArrayList<>(c);
        cm.setEmpname(a.get(0).getFname()+" "+a.get(0).getLname());
        return cm;
    }

    //to get the message list from the chat list option

    @RequestMapping("/messagelistfromchat")
    public @ResponseBody ArrayList<Message> getMessageListFromChat(@RequestBody ChatMessage cm)
    {
        return chat.getMessageList(cm.getChatid());
    }

    //send a new message

    @RequestMapping("/newmessage")
    public @ResponseBody Message newMessage(@RequestBody Message msg)
    {
        System.out.println("started");
        msg.setDate(notificationManager.giveDate());
        msg.setTime(notificationManager.giveTime());
        msg.setMessageid(chat.getMessageNo());
        messageMongoRepository.save(msg);
        Collection<Chat> c = chatSearchRepository.searchChatsFromID(msg.getChatid());
        ArrayList<Chat> ac = new ArrayList<>(c);
        String empid = null;
        if(ac.get(0).getEmployee1().equals(msg.getSender()))
            empid = ac.get(0).getEmployee2();
        else if(ac.get(0).getEmployee2().equals(msg.getSender()))
            empid = ac.get(0).getEmployee1();
        notificationManager.messageReceived(empid,msg.getSender(),msg.getChatid());
        System.out.println("in method");
        return msg;
    }

    //admin to reset a blocked user account

    @RequestMapping("/adminreset")
    public @ResponseBody Employee adminReset(@RequestBody Employee employee)
    {
        Collection<Employee> ec = employeeSearchRepository.searchUserEmail(employee.getEmail());
        ArrayList<Employee> ea = new ArrayList<>(ec);

        if(ea.isEmpty() || ea.get(0).getType().equals("admin"))
            employee.setEmail(null);
        else
        {
            String email = employee.getEmail();

            String password = getpassword.generatePassword();

            ea.get(0).setPassword(getpassword.encryptPassword(password));
            ea.get(0).setPassstatus("new");
            ea.get(0).setStatus("3");
            userRepository.save(ea.get(0));

            getemail.forgotPasswordEmail(password,email,ea.get(0).getFname()+" "+ea.get(0).getLname());
        }

        return employee;
    }

    //admin to block a user account

    @RequestMapping("/blockaccount")
    public @ResponseBody Employee blockAccount(@RequestBody Employee e)
    {
        Collection<Employee> c = employeeSearchRepository.searchUserEmail(e.getEmail());
        ArrayList<Employee> a = new ArrayList<>(c);

        if(a.isEmpty() || a.get(0).getType().equals("admin"))
        {
            e.setEmail(null);
        }
        else
        {
            a.get(0).setStatus("0");
            userRepository.save(a.get(0));
        }
        return e;
    }

    @RequestMapping("/accountdetails")
    public @ResponseBody Employee accountDetails(@RequestBody Employee e)
    {
        Collection<Employee> c = employeeSearchRepository.searchUserEmail(e.getEmail());
        ArrayList<Employee> a = new ArrayList<>(c);

        if(a.isEmpty())
        {
            e.setEmail(null);
        }
        else
        {
            if(a.get(0).getType().equals("admin"))
                e.setEmail(null);
            else
            {
                e.setFname(a.get(0).getFname());
                e.setLname(a.get(0).getLname());
                e.setType(a.get(0).getType());
            }
        }
        return e;
    }

    //admin to change the password to his profile

    @RequestMapping("/resetadminpassword")
    public @ResponseBody Employee changeAdminPassword(@RequestBody Employee e)
    {
        Collection<Employee> c = employeeSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<Employee> a = new ArrayList<>(c);
        //current password is taken from username
        if(!getpassword.decryptPassword(e.getUsername(),a.get(0).getPassword()))
        {
            e.setPassword("wrong");
        }
        else
        {
            String epass = getpassword.encryptPassword(e.getPassword());
            e.setPassword(epass);
            a.get(0).setPassword(epass);
            userRepository.save(a.get(0));
        }
        return e;
    }

    // view the new notifications

    @RequestMapping("/notificationviewnew")
    public @ResponseBody ArrayList<Notification> viewNewNotifications(@RequestBody Employee e)
    {
        return notificationManager.viewNewAll(e.getEmployeeid());
    }

    // view the other notifications

    @RequestMapping("/notificationviewother")
    public @ResponseBody ArrayList<Notification> viewOtherNotifications(@RequestBody Employee e)
    {
        return notificationManager.viewOtherAll(e.getEmployeeid());
    }

//    @RequestMapping("/viewprofile")
//    public @ResponseBody Employee viewProfile(@RequestBody Employee emp) throws IOException {
//        Collection<Employee> u = employeeSearchRepository.searchUserId(emp.getEmployeeid());
//        ArrayList<Employee> newList = new ArrayList<>(u);
//        Employee e = new Employee();
//        e.setUsername(newList.get(0).getUsername());
//        e.setAddressno(newList.get(0).getAddressno());
//        e.setCity(newList.get(0).getCity());
//        e.setStreet(newList.get(0).getStreet());
//        e.setEmail(newList.get(0).getEmail());
//        e.setContact1(newList.get(0).getContact1());
//        e.setContact2(newList.get(0).getContact2());
//        e.setType(newList.get(0).getType());
//        e.setAbout(newList.get(0).getAbout());
//        return e;
//    }













//    @RequestMapping("/fromlist")
//    public String fromListChat(ChatMessage cm)
//    {
//        String s = chat.findChat(cm.getEmpidowner(),cm.getEmpid());
//        String status;
//
//        if(s.equals("null"))
//            status = "new";
//        else
//            status = "existing";
//        return status;
//    }








    @RequestMapping("/qaz")
    public void sdasd()
    {
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(10);
        a.add(20);
        a.add(30);
        a.add(40);
        a.add(50);
        Iterator<Integer> i = a.iterator();
        System.out.println(i);
        while (i.hasNext())
        {
            Integer in = i.next();
            System.out.println(in);
        }
    }






}
