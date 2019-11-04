package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Service
public class FileProcessor {

    @Autowired
    EmployeeMongoRepository employeeMongoRepository;

    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    CustomerMongoRepository customerMongoRepository;

    @Autowired
    ProjectMongoRepository projectMongoRepository;

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    GradeProjectSearchRepository gradeProjectSearchRepository;

    @Autowired
    GradeProjectMongoRepository gradeProjectMongoRepository;

    @Autowired
    QuestionMongoRepository questionMongoRepository;

    @Autowired
    AnswerMongoRepository answerMongoRepository;

    @Autowired
    SRSMongoRepository srsMongoRepository;

    @Autowired
    ReplyMongoRepository replyMongoRepository;

    public String generateCusID()
    {
        Iterable<Customer> i= customerMongoRepository.findAll();
        Iterator<Customer> i2 = i.iterator();
        String num = "CUS00000";

        while (i2.hasNext())
        {
            Customer cus = i2.next();
            num = cus.getCustomerid();
        }

        String nums = num.substring(3);
        long no = Long.parseLong(nums);
        no++;
        DecimalFormat df = new DecimalFormat("00000");
        String c = "CUS"+df.format(no);
        return  c;
    }

    public String generateProjectID()
    {
        Iterable<Project> i= projectMongoRepository.findAll();
        Iterator<Project> i2 = i.iterator();
        String num = "PRO00000";

        while (i2.hasNext())
        {
            Project pro = i2.next();
            num = pro.getProjectid();
        }

        String nums = num.substring(3);
        long no = Long.parseLong(nums);
        no++;
        DecimalFormat df = new DecimalFormat("00000");
        String p = "PRO"+df.format(no);
        return  p;
    }

    public String generateSRSid()
    {

        Iterable<SRS> i= srsMongoRepository.findAll();
        Iterator<SRS> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            SRS s = i2.next();
            num = Long.parseLong(s.getSRSid());
        }
        num++;
        return String.valueOf(num);
    }

    public String generateEmpID()
    {
        Iterable<Employee> i= employeeMongoRepository.findAll();
        Iterator<Employee> i2 = i.iterator();
        String num = "EMP00000";

        while (i2.hasNext())
        {
            Employee emp = i2.next();
            num = emp.getEmployeeid();
        }

        String nums = num.substring(3);
        long no = Long.parseLong(nums);
        no++;
        DecimalFormat df = new DecimalFormat("00000");
        String emp = "EMP"+df.format(no);
        return  emp;
    }

    public String getEmpName(String id)
    {
        if(id==null)
            id="";
        Collection<Employee> c = employeeSearchRepository.searchUserId(id);
        ArrayList<Employee> a = new ArrayList<>(c);
        if(a.size()==0)
            return "";
        else
            return a.get(0).getFname()+" "+a.get(0).getLname();
    }

    public String getQuestionNo()
    {

        Iterable<Question> i= questionMongoRepository.findAll();
        Iterator<Question> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            Question q = i2.next();
            num = Long.parseLong(q.getQuestionid());
        }
        num++;
        return String.valueOf(num);
    }

    public String getAnswerNo()
    {

        Iterable<Answer> i= answerMongoRepository.findAll();
        Iterator<Answer> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            Answer a = i2.next();
            num = Long.parseLong(a.getAnswerid());
        }
        num++;
        return String.valueOf(num);
    }

    public String getReplyNo()
    {

        Iterable<Reply> i= replyMongoRepository.findAll();
        Iterator<Reply> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            Reply r = i2.next();
            num = Long.parseLong(r.getReplyid());
        }
        num++;
        return String.valueOf(num);
    }



    //    public void imageUpload(String empid, String path) throws IOException
//    {
//        Mongo mongo = new Mongo("localhost",27017);
//        DB db = mongo.getDB("BA_Management_System");
////        DBCollection coll = ((DB) db).getCollection("check");
//        String newFileName = empid;
//        File imageFile = new File(path);
//        GridFS gfsPhoto = new GridFS(db, "photo");
//        GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
//        gfsFile.setFilename(newFileName);
//        gfsFile.setId(newFileName);
//        gfsFile.save();
//        System.out.println("done");
//    }

//    public void saveImage(String empid, MultipartFile m) throws IOException
//    {
////        Mongo mongo = new Mongo("localhost",27017);
////        DB db = mongo.getDB("BA_Management_System");
////        GridFS gfsPhoto = new GridFS(db, "photo");
////        GridFSDBFile imageForOutput = gfsPhoto.findOne(empid);
////        imageForOutput.writeTo("I:\\Project\\src\\assets\\black.jpg");
////        System.out.println(imageForOutput);
////        System.out.println("done");
//          Collection<Employee> c = employeeSearchRepository.searchUserId(empid);
//          ArrayList<Employee> a = new ArrayList<>(c);
////          a.get(0).setImage(new Binary(BsonBinarySubType.BINARY,m.getBytes()));
//    }
//
//    public Employee updateDatabase(Collection<Employee> u, Employee e,String state)
//    {
//        ArrayList<Employee> newList = new ArrayList<>(u);
//        e.setEmployeeid(newList.get(0).getEmployeeid());
//        e.setType(newList.get(0).getType());
//        e.setId(newList.get(0).getId());
//        e.setGradestatus(newList.get(0).getGradestatus());
//
////        if(state.equals("account"))
////        {
////            e.setFname(newList.get(0).getFname());
////            e.setLname(newList.get(0).getLname());
////            e.setEmail(newList.get(0).getEmail());
////            e.setContact1(newList.get(0).getContact1());
////            e.setAddressno(newList.get(0).getAddressno());
////            e.setContact2(newList.get(0).getContact2());
////            e.setAbout(newList.get(0).getAbout());
////            e.setStreet(newList.get(0).getStreet());
////            e.setCity(newList.get(0).getCity());
////            employeeMongoRepository.save(e);
////        }
////        else if (state.equals("login"))
////        {
////            e.setFname(newList.get(0).getFname());
////            e.setLname(newList.get(0).getLname());
////            e.setEmail(newList.get(0).getEmail());
////            e.setCity(newList.get(0).getCity());
////            e.setUsername(newList.get(0).getUsername());
////            e.setContact1(newList.get(0).getContact1());
////            e.setContact2(newList.get(0).getContact2());
////            e.setAddressno(newList.get(0).getAddressno());
////            e.setStreet(newList.get(0).getStreet());
////            e.setAbout(newList.get(0).getAbout());
////            e.setPassstatus(newList.get(0).getPassstatus());
////            e.setPassword(newList.get(0).getPassword());
////            e.setStatus("3");
////            employeeMongoRepository.save(e);
////        }
////        else if(state.equals("personal"))
////        {
////            e.setEmail(newList.get(0).getEmail());
////            e.setContact2(newList.get(0).getContact2());
////            e.setContact1(newList.get(0).getContact1());
////            e.setStreet(newList.get(0).getStreet());
////            e.setAddressno(newList.get(0).getAddressno());
////            e.setCity(newList.get(0).getCity());
////            e.setPassword(newList.get(0).getPassword());
////            employeeMongoRepository.save(e);
////        }
////        else if(state.equals("forgot"))
////        {
////            e.setStreet(newList.get(0).getStreet());
////            e.setFname(newList.get(0).getFname());
////            e.setContact2(newList.get(0).getContact2());
////            e.setLname(newList.get(0).getLname());
////            e.setUsername(newList.get(0).getUsername());
////            e.setEmail(newList.get(0).getEmail());
////            e.setContact1(newList.get(0).getContact1());
////            e.setAddressno(newList.get(0).getAddressno());
////            e.setCity(newList.get(0).getCity());
////            e.setAbout(newList.get(0).getAbout());
////            e.setStatus(newList.get(0).getStatus());
////            employeeMongoRepository.save(e);
////        }
////        else if(state.equals("contact"))
////        {
////            e.setFname(newList.get(0).getFname());
////            e.setLname(newList.get(0).getLname());
////            e.setAbout(newList.get(0).getAbout());
////            e.setUsername(newList.get(0).getUsername());
////            e.setPassword(newList.get(0).getPassword());
////            employeeMongoRepository.save(e);
////        }
//        return e;
//    }

//    public void checkForRequiredGrading(TempGrade t)
//    {
//        Collection<GradeProject> c = gradeProjectSearchRepository.searchUserId(t.getEmployeeid());
//        ArrayList<GradeProject> a = new ArrayList<>(c);
//        Iterator<GradeProject> i1 = a.iterator();
//        Iterator<GradeProject> i2 = a.iterator();
//
//        int count = 0;
//
//        while (i1.hasNext())
//        {
//            GradeProject gradeProject = i1.next();
//            if(gradeProject.getProjectid().equals(t.getProjectid())) {
//                gradeProject.setStatus("done");
//                gradeProjectMongoRepository.save(gradeProject);
//            }
//        }
//
//        while (i2.hasNext())
//        {
//            GradeProject gradeProject = i2.next();
//            if(gradeProject.getEmployeeid().equals(t.getEmployeeid()) && gradeProject.getStatus().equals("required"))
//                count++;
//        }
//
//        if(count!=0)
//        {
//            Collection<Employee> ec = employeeSearchRepository.searchUserId(t.getEmployeeid());
//            ArrayList<Employee> ac = new ArrayList<>(ec);
//            ac.get(0).setStreet("changed");
//            employeeMongoRepository.save(ac.get(0));
//        }
//    }

}
