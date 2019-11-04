package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NotificationManager {

    @Autowired
    NotificationMongoRepository notificationRepository;

    @Autowired
    NotificationSearchRepository notificationSearchRepository;

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    AnswerSearchRepository answerSearchRepository;

    @Autowired
    QuestionSearchRepository questionSearchRepository;

    @Autowired
    FileProcessor fileProcessor;

    @Autowired
    ReplySearchRepository replySearchRepository;

    public String giveTime()
    {
        Date time = new Date();
        String timeformat = "hh:mm a";
        DateFormat timeFormat = new SimpleDateFormat(timeformat);
        String formattedtime = timeFormat.format(time);
        return formattedtime;
    }

    public String giveDate()
    {
        Date date = new Date();
        String dateformat = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(dateformat);
        String formatteddate = dateFormat.format(date);
        return formatteddate;
    }

    public String getNotificationNo()
    {

        Iterable<Notification> i= notificationRepository.findAll();
        Iterator<Notification> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            Notification n = i2.next();
            num = Long.parseLong(n.getNotificationid());
        }
        num++;
        return String.valueOf(num);
    }

    public ArrayList<Notification> viewNewAll(String empid) {
        Collection<Notification> n = notificationSearchRepository.allNotifications(empid);
        ArrayList<Notification> newList = new ArrayList<>(n);
        ArrayList<Notification> newListFinal = new ArrayList<Notification>();
        Iterator<Notification> i = newList.iterator();
        while (i.hasNext())
        {
            Notification notification = i.next();
            if(notification.getStatus().equals("false"))
                newListFinal.add(notification);
            notification.setStatus("true");
            notificationRepository.save(notification);
        }
        return notifySort(newListFinal);
    }

    public ArrayList<Notification> viewOtherAll(String empid) {
        Collection<Notification> n = notificationSearchRepository.allNotifications(empid);
        ArrayList<Notification> newList = new ArrayList<>(n);
        ArrayList<Notification> newListFinal = new ArrayList<Notification>();
        Iterator<Notification> i = newList.iterator();
        while (i.hasNext())
        {
            Notification notification = i.next();
            if(notification.getStatus().equals("true"))
                newListFinal.add(notification);
        }
        return notifySort(newListFinal);
    }

    public static Comparator<Notification> notifyNo = new Comparator<Notification>() {

        public int compare(Notification s1, Notification s2) {

            int no1 = Integer.parseInt(s1.getNotificationid());
            int no2 = Integer.parseInt(s2.getNotificationid());

            /*For ascending order*/
//            return rollno1-rollno2;

            /*For descending order*/
            return no2-no1;
        }};

    public ArrayList<Notification> notifySort(ArrayList<Notification> notify)
    {
        Collections.sort(notify, notifyNo);
        return notify;
    }

    public String getProjectName(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        String name = newList.get(0).getName();
        return name;
    }

    // main method used to add the data to he notification table

    public void insertData(String content,String empid,String type,String typeid,String name)
    {
        Notification n = new Notification();
        n.setContent(content);
        n.setDate(giveDate());
        n.setTime(giveTime());
        n.setEmplyeeid(empid);
        n.setNotificationid(getNotificationNo());
        n.setStatus("false");
        n.setType(type);
        n.setTypeid(typeid);
        n.setChatname(name);
        notificationRepository.save(n);
    }

    // create notification for a new answer

    public void newAnswer(String empid,String projectid,String question) {
        String content = "The question "+question+" under project "+getProjectName(projectid) + " have got a new answer";
        insertData(content, empid, "QA", projectid,"none");
    }

    public void notifyNewAnswer(String questionid,String questioncontent,String projectid)
    {
        Collection<Answer> c = answerSearchRepository.getFromQuestionId(questionid);
        ArrayList<Answer> a = new ArrayList<>(c);
        Iterator<Answer> i = a.iterator();
        while (i.hasNext())
        {
            Answer answer = i.next();
            newAnswer(answer.getEmployeeid(),projectid,questioncontent);
        }
    }

    public void newReply(String empid,String projectid,String question,String answer) {
        String content = "The answer "+answer+" regarding the question "+question+" under project "+getProjectName(projectid) + " have got a new reply";
        insertData(content, empid, "QA", projectid,"none");
    }

    public void notifyNewReply(String answerid,String questioncontent,String projectid,String answercontent)
    {
        Collection<Reply> c = replySearchRepository.getFromAnswerId(answerid);
        ArrayList<Reply> a = new ArrayList<>(c);
        Iterator<Reply> i = a.iterator();
        while (i.hasNext())
        {
            Reply reply = i.next();
            newReply(reply.getEmployeeid(),projectid,questioncontent,answercontent);
        }
    }

    // create notification for a new question

    public void newQuestion(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" have got a new question";
        insertData(content,empid, "QA", projectid,"none");
    }

    public void notifyNewQuestion(String projectid)
    {
        Collection<Project> c = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> a = new ArrayList<>(c);
        newQuestion(a.get(0).getLba(),projectid);
        newQuestion(a.get(0).getJba1(),projectid);
        newQuestion(a.get(0).getJba2(),projectid);
    }

    // create notification for leave request

    public void leaveRequest(Calender c)
    {
        String content = fileProcessor.getEmpName(c.getBa())+" have requested a leave on "+c.getDate();
        insertData(content,c.getPm(),"LeaveReq", "none","none");
    }

    //create notification for leave approval for the BA and the other project ongoing PMs

    public void leaveApproved(String empid,String reason,String date)
    {
        Collection<Employee> e = employeeSearchRepository.searchUserId(empid);
        ArrayList<Employee> ae = new ArrayList<>(e);
        String name = ae.get(0).getFname()+" "+ae.get(0).getLname();
        String content = name+" leave note due to "+reason+" on "+date+" was acknowledged by the Project manager";
        insertData(content,empid,"LeaveResAll","none","none");
    }

    public void notifyLeaveApproved(Calender c)
    {
        String content = "Leave note for the day "+c.getDate()+" has been acknowledged";
        insertData(content,c.getBa(),"LeaveRes","none","none");
        Collection<Project> p = projectSearchRepository.current();
        ArrayList<Project> pa = new ArrayList<>(p);
        Iterator<Project> pi = pa.iterator();

        while(pi.hasNext())
        {
            Project pro = pi.next();
            if((pro.getJba1().equals(c.getBa()) || pro.getJba2().equals(c.getBa()) || pro.getLba().equals(c.getBa())) && c.getPm().equals(pro.getPm()))
            {
                leaveApproved(pro.getPm(),c.getReason(),c.getDate());
                if(!c.getBa().equals(pro.getLba()))
                    leaveApproved(pro.getLba(),c.getReason(),c.getDate());
                if(!c.getBa().equals(pro.getJba1()))
                    leaveApproved(pro.getJba1(),c.getReason(),c.getDate());
                if(!c.getBa().equals(pro.getJba2()))
                    leaveApproved(pro.getJba2(),c.getReason(),c.getDate());
                if(!pro.getDev1().equals(""))
                    leaveApproved(pro.getDev1(),c.getReason(),c.getDate());
                if(!pro.getDev2().equals(""))
                    leaveApproved(pro.getDev2(),c.getReason(),c.getDate());
                if(!pro.getDev3().equals(""))
                    leaveApproved(pro.getDev3(),c.getReason(),c.getDate());
                if(!pro.getDev4().equals(""))
                    leaveApproved(pro.getDev4(),c.getReason(),c.getDate());
                if(!pro.getDev5().equals(""))
                    leaveApproved(pro.getDev5(),c.getReason(),c.getDate());
            }
        }
    }

    public String getSenderName(String id)
    {
        Collection<Employee> u = employeeSearchRepository.searchUserId(id);
        ArrayList<Employee> newList = new ArrayList<>(u);
        String name = newList.get(0).getFname()+" "+newList.get(0).getLname();
        return name;
    }

    //create notification for new received message

    public void messageReceived(String empid,String senderid,String chatid)
    {
        String content = getSenderName(senderid)+" sent you a message";
        insertData(content,empid,"Chat",chatid,getSenderName(senderid));
    }

    //create notifications for employees to show that they have been assigned to a project

    public void assigned(String empid,String projectid)
    {
        String content = "You have been assigned to project "+getProjectName(projectid);
        insertData(content,empid,"Project",projectid,"none");
    }

    public void notifyByPMAssigned(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        assigned(newList.get(0).getLba(),projectid);
        assigned(newList.get(0).getJba1(),projectid);
        assigned(newList.get(0).getJba2(),projectid);
        if(!newList.get(0).getDev1().equals(""))
            assigned(newList.get(0).getDev1(),projectid);
        if(!newList.get(0).getDev2().equals(""))
            assigned(newList.get(0).getDev2(),projectid);
        if(!newList.get(0).getDev3().equals(""))
            assigned(newList.get(0).getDev3(),projectid);
        if(!newList.get(0).getDev4().equals(""))
            assigned(newList.get(0).getDev4(),projectid);
        if(!newList.get(0).getDev5().equals(""))
            assigned(newList.get(0).getDev5(),projectid);
    }

    //create notifications to show that the project is completed

    public void completed(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" was successfully completed";
        insertData(content,empid,"Project",projectid,"none");
    }

    public void notifyByPMComplete(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        completed(newList.get(0).getLba(),projectid);
        completed(newList.get(0).getJba1(),projectid);
        completed(newList.get(0).getJba2(),projectid);
        if(!newList.get(0).getDev1().equals(""))
            completed(newList.get(0).getDev1(),projectid);
        if(!newList.get(0).getDev2().equals(""))
            completed(newList.get(0).getDev2(),projectid);
        if(!newList.get(0).getDev3().equals(""))
            completed(newList.get(0).getDev3(),projectid);
        if(!newList.get(0).getDev4().equals(""))
            completed(newList.get(0).getDev4(),projectid);
        if(!newList.get(0).getDev4().equals(""))
            completed(newList.get(0).getDev5(),projectid);
    }

    //Notification for new comment by the PM

    public void commentedByPM(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" was given a comment by the Project Manager";
        insertData(content,empid,"SRS",projectid,"none");
    }

    public void notifyByPMCommented(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        commentedByPM(newList.get(0).getJba1(),projectid);
        commentedByPM(newList.get(0).getJba2(),projectid);
        commentedByPM(newList.get(0).getLba(),projectid);
    }

    //notification of project being approved by PM

    public void approvedByPM(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" was approved by the Project Manager";
        insertData(content,empid,"SRS",projectid,"none");
    }

    public void notifyByPMApproved(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        approvedByPM(newList.get(0).getLba(),projectid);
        approvedByPM(newList.get(0).getJba1(),projectid);
        approvedByPM(newList.get(0).getJba2(),projectid);
        if(!newList.get(0).getDev1().equals(""))
            approvedByPM(newList.get(0).getDev1(),projectid);
        if(!newList.get(0).getDev2().equals(""))
            approvedByPM(newList.get(0).getDev2(),projectid);
        if(!newList.get(0).getDev3().equals(""))
            approvedByPM(newList.get(0).getDev3(),projectid);
        if(!newList.get(0).getDev4().equals(""))
            approvedByPM(newList.get(0).getDev4(),projectid);
        if(!newList.get(0).getDev5().equals(""))
            approvedByPM(newList.get(0).getDev5(),projectid);
    }

    // new comment by SBA o SRS

    public void commentedBySBA(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" was given a comment by the Senior BA";
        insertData(content,empid,"SRS",projectid,"none");
    }

    public void notifyBySBACommented(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        commentedBySBA(newList.get(0).getJba1(),projectid);
        commentedBySBA(newList.get(0).getJba2(),projectid);
    }

    //notification of project being approved by SBA

    public void submittedBySBA(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" was approved by the Senior BA and was submitted to the Project Manager";
        insertData(content,empid,"SRS",projectid,"none");
    }

    public void notifyBySBASubmitted(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        submittedBySBA(newList.get(0).getJba1(),projectid);
        submittedBySBA(newList.get(0).getJba2(),projectid);
        submittedBySBA(newList.get(0).getPm(),projectid);
    }

    //notification of project being rejected by SBA

    public void rejectedBySBA(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" was rejected by the Senior BA";
        insertData(content,empid,"SRS",projectid,"none");
    }

    public void notifyBySBARejected(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        rejectedBySBA(newList.get(0).getJba1(),projectid);
        rejectedBySBA(newList.get(0).getJba2(),projectid);
        rejectedBySBA(newList.get(0).getPm(),projectid);
    }

    //notification of project being submitted by the JBAs to the SBA

    public void submittedByJBA(String empid,String projectid)
    {
        String content = getProjectName(projectid)+" was submitted to the Senior BA";
        insertData(content,empid,"SRS",projectid,"none");
    }

    public void notifyByJBASubmitted(String projectid)
    {
        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        submittedByJBA(newList.get(0).getLba(),projectid);
        submittedByJBA(newList.get(0).getPm(),projectid);
        submittedByJBA(newList.get(0).getJba1(),projectid);
        submittedByJBA(newList.get(0).getJba2(),projectid);
    }

}
