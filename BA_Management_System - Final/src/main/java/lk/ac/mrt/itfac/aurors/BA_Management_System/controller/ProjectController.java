package lk.ac.mrt.itfac.aurors.BA_Management_System.controller;

import com.itextpdf.text.DocumentException;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.QuerydslWebConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.resources.cldr.chr.CalendarData_chr_US;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    ProjectMongoRepository projectMongoRepository;

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    FileProcessor fileProcessor;

    @Autowired
    CustomerMongoRepository customerMongoRepository;

    @Autowired
    Availability availability;

    @Autowired
    TempGradeSearchRepository tempGradeSearchRepository;

    @Autowired
    TempGradeMongoRepository tempGradeMongoRepository;

    @Autowired
    JBALeaderBoardSearchRepository jbaLeaderBoardSearchRepository;

    @Autowired
    Calculation calculation;

    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    EmployeeMongoRepository employeeMongoRepository;

    @Autowired
    GradeProjectMongoRepository gradeProjectMongoRepository;

    @Autowired
    GradeProjectSearchRepository gradeProjectSearchRepository;

    @Autowired
    AnswerMongoRepository answerMongoRepository;

    @Autowired
    AnswerSearchRepository answerSearchRepository;

    @Autowired
    QuestionMongoRepository questionMongoRepository;

    @Autowired
    QuestionSearchRepository questionSearchRepository;

    @Autowired
    CustomerSearchRepository customerSearchRepository;

    @Autowired
    SRSSearchRepository srsSearchRepository;

    @Autowired
    SRSMongoRepository srsMongoRepository;

    @Autowired
    Section1SearchRepository section1SearchRepository;

    @Autowired
    Section1MongoRepository section1MongoRepository;

    @Autowired
    Section2SearchRepository section2SearchRepository;

    @Autowired
    Section2MongoRepository section2MongoRepository;

    @Autowired
    IntroductionMongoRepository introductionMongoRepository;

    @Autowired
    IntroductionSearchRepository introductionSearchRepository;

    @Autowired
    InterfaceDesignMongoRepository interfaceDesignMongoRepository;

    @Autowired
    InterfaceDesignSearchRepository interfaceDesignSearchRepository;

    @Autowired
    NonFunctionalMongoRepository nonFunctionalMongoRepository;

    @Autowired
    NonFunctionalSearchRepository nonFunctionalSearchRepository;

    @Autowired
    SystemFeaturesMongoRepository systemFeaturesMongoRepository;

    @Autowired
    SystemFeaturesSearchRepository systemFeaturesSearchRepository;

    @Autowired
    OverallMongoRepository overallMongoRepository;

    @Autowired
    OverallSearchRepository overallSearchRepository;

    @Autowired
    Email email;

    @Autowired
    ReplyMongoRepository replyMongoRepository;

    @Autowired
    ReplySearchRepository replySearchRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    NotificationMongoRepository notificationMongoRepository;

    //start of creating a project

    @RequestMapping("/projectcreation")
    public @ResponseBody ProjectCustomer checkAvailable(@RequestBody ProjectCustomer pc) throws ParseException {
        System.out.println("Project Creation");
        ArrayList<ArrayList<String>> a = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(pc.getStartdate());
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        String d = sdfr.format(date);
        pc.setStartdate(d);
        date = sdf.parse(pc.getEnddate());
        d = sdfr.format(date);
        pc.setEnddate(d);
        System.out.println(pc.getStartdate());
        System.out.println(pc.getEnddate());

        Customer customer = new Customer();
        Project project = new Project();

        customer.setCustomerid(fileProcessor.generateCusID());
        customer.setContact1(pc.getContact());
        customer.setEmail(pc.getEmail());
        customer.setName(pc.getCname());
        project.setPm(pc.getPm());
        project.setStartdate(pc.getStartdate());
        project.setEnddate(pc.getEnddate());
        project.setProjectid(fileProcessor.generateProjectID());
        project.setDescription(pc.getDescription());
        project.setName(pc.getPname());
        project.setStatus("created");
        project.setCustomerid(customer.getCustomerid());

        customerMongoRepository.save(customer);
        projectMongoRepository.save(project);

        pc.setProjectid(project.getProjectid());
        return pc;
    }

    //get the available JBAs for a new project

    @RequestMapping("/getavailablejba")
    public @ResponseBody ArrayList<String> availableJBA(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableJBA(a.get(0).getStartdate(),a.get(0).getEnddate());
        return sa;
    }

    @RequestMapping("/getchangeavailablejba1")
    public @ResponseBody ArrayList<String> availableChangeJBA1(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeJBA(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getJba1());
        return sa;
    }

    @RequestMapping("/getchangeavailablejba2")
    public @ResponseBody ArrayList<String> availableChangeJBA2(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeJBA(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getJba2());
        return sa;
    }



    //get the available SBAs for a project

    @RequestMapping("/getavailablesba")
    public @ResponseBody ArrayList<String> availableSBA(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableSBA(a.get(0).getStartdate(),a.get(0).getEnddate());
        return sa;
    }

    @RequestMapping("/getchangeavailablesba")
    public @ResponseBody ArrayList<String> availableChangeSBA(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeSBA(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getLba());
        return sa;
    }

    //get the available Devs for a project

    @RequestMapping("/getavailabledev")
    public @ResponseBody ArrayList<String> availableDEV(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableDev(a.get(0).getStartdate(),a.get(0).getEnddate());
        return sa;
    }

    @RequestMapping("/getchangeavailabledev1")
    public @ResponseBody ArrayList<String> availableDEV1(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeDev(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getDev1());
        return sa;
    }

    @RequestMapping("/getchangeavailabledev2")
    public @ResponseBody ArrayList<String> availableDEV2(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeDev(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getDev2());
        return sa;
    }

    @RequestMapping("/getchangeavailabledev3")
    public @ResponseBody ArrayList<String> availableDEV3(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeDev(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getDev3());
        return sa;
    }

    @RequestMapping("/getchangeavailabledev4")
    public @ResponseBody ArrayList<String> availableDEV4(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeDev(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getDev4());
        return sa;
    }

    @RequestMapping("/getchangeavailabledev5")
    public @ResponseBody ArrayList<String> availableDEV5(@RequestBody Project p) throws ParseException {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<String> sa = availability.availableChangeDev(a.get(0).getStartdate(),a.get(0).getEnddate(),a.get(0).getDev5());
        return sa;
    }

    //finish creating the project

    @RequestMapping("/projectcreationfinal")
    public @ResponseBody Project completeProjectCreation(@RequestBody Project project) throws MessagingException {
        System.out.println("Final Creation");
        Collection<Project> p = projectSearchRepository.projectDetailsId(project.getProjectid());
        ArrayList<Project> a = new ArrayList<>(p);
        project.setDescription(a.get(0).getDescription());
        project.setId(a.get(0).getId());
        project.setCustomerid(a.get(0).getCustomerid());
        project.setStatus(a.get(0).getStatus());
        project.setStartdate(a.get(0).getStartdate());
        project.setEnddate(a.get(0).getEnddate());
        project.setName(a.get(0).getName());
        project.setPm(a.get(0).getPm());
        project.setLastupdated(notificationManager.giveDate());
        project.setDays("0");
        TempGrade tg = new TempGrade();
        tg.setProjectid(project.getProjectid());
//        tg.setCustomergrade("");
        tg.setCustomergrade1("");
        tg.setCustomergrade2("");
        tg.setDevcount("0");
        tg.setDevgrade("0.00");
        String sname = project.getJba1().substring(0,8);
        project.setJba1(sname);
        sname = project.getJba2().substring(0,8);
        project.setJba2(sname);
        sname = project.getLba().substring(0,8);
        project.setLba(sname);
        System.out.println(project.getDev2()+"123");
        if (!project.getDev1().equals("") || project.getDev1()!=null) {
            tg.setDev1grade("required");
            String s = project.getDev1().substring(0,8);
            project.setDev1(s);
        }
        else{
            tg.setDev1grade("");
            project.setDev1("");
        }
        if (project.getDev2()!=null){
            tg.setDev2grade("required");
            String s = project.getDev2().substring(0,8);
            project.setDev2(s);
        }
        else{
            tg.setDev2grade("");
            project.setDev2("");
        }
        if (project.getDev3()!=null){
            tg.setDev3grade("required");
            String s = project.getDev3().substring(0,8);
            project.setDev3(s);
        }
        else{
            tg.setDev3grade("");
            project.setDev3("");
        }
        if (project.getDev4()!=null){
            tg.setDev4grade("required");
            String s = project.getDev4().substring(0,8);
            project.setDev4(s);
        }
        else{
            tg.setDev4grade("");
            project.setDev4("");
        }
        if (project.getDev5()!=null){
            tg.setDev5grade("required");
            String s = project.getDev5().substring(0,8);
            project.setDev5(s);
        }
        else {
            tg.setDev5grade("");
            project.setDev5("");
        }
        tg.setPmgrade("");
        tg.setSbagrade1("");
        tg.setSbagrade2("");
        tg.setSystemgrade1("5.00");
        tg.setSystemgrade2("5.00");
        tg.setQagrade("5.00");
        tg.setStatus("progress");
        tempGradeMongoRepository.save(tg);
        projectMongoRepository.save(project);
        notificationManager.notifyByPMAssigned(project.getProjectid());
        SRS srs = new SRS();
        srs.setState("created");
        srs.setJba1status("notsubmitted");
        srs.setJba2status("notsubmitted");
        srs.setSRSid(fileProcessor.generateSRSid());
        srs.setProjectid(project.getProjectid());
        srsMongoRepository.save(srs);
        Introdution i = new Introdution();
        i.setSRSid(srs.getSRSid());
        i.setEmployeeid(project.getJba1());
        i.setDatemodified("");
        i.setPurpose("");
        i.setSpecifications("");
        i.setSuggestions("");
        i.setAudience("");
        i.setBenefits("");
        i.setGoal("");
        i.setObjectives("");
        i.setReference("");
        i.setSBAcomment("");
        i.setPMcomment("");
        i.setStatus("");
        introductionMongoRepository.save(i);
        InterfaceDesign id = new InterfaceDesign();
        id.setEmployeeid(project.getJba1());
        id.setSRSid(srs.getSRSid());
        id.setAttachment("notavailable");
        id.setDatemodified("");
        id.setDisplay("");
        id.setSpecifications("");
        id.setConnections("");
        id.setNetwork("");
        id.setEncryption("");
        id.setSBAcomment("");
        id.setPMcomment("");
        id.setStatus("");
        interfaceDesignMongoRepository.save(id);
        Overall ov = new Overall();
        ov.setSRSid(srs.getSRSid());
        ov.setEmployeeid(project.getJba1());
        ov.setDiagram1("notavailable");
        ov.setDiagram2("notavailable");
        ov.setDatemodified("");
        ov.setType("");
        ov.setDescription("");
        ov.setSpecification("");
        ov.setUsercase("");
        ov.setSoftware("");
        ov.setHardware("");
        ov.setConstraints("");
        ov.setDocuments("");
        ov.setAssumptions("");
        ov.setSBAcomment("");
        ov.setPMcomment("");
        ov.setStatus("");
        overallMongoRepository.save(ov);
        SystemFeatures sf = new SystemFeatures();
        sf.setSRSid(srs.getSRSid());
        sf.setEmployeeid(project.getJba2());
        sf.setDatemodified("");
        sf.setPMcomment("");
        sf.setSBAcomment("");
        sf.setStatus("");
        ArrayList<Feature> f= new ArrayList<>();
        sf.setFeatureset(f);
        systemFeaturesMongoRepository.save(sf);
        NonFunctional nf = new NonFunctional();
        nf.setSRSid(srs.getSRSid());
        nf.setEmployeeid(project.getJba2());
        nf.setDatemodified("");
        nf.setPerformance("");
        nf.setDamages("");
        nf.setSafety("");
        nf.setSecurity("");
        nf.setDescription("");
        nf.setReference("");
        nf.setAttribute("");
        nf.setSBAcomment("");
        nf.setPMcomment("");
        nf.setStatus("");
        nonFunctionalMongoRepository.save(nf);
        System.out.println("completed");
        email.introMail(project);
        return project;
    }

    @RequestMapping("/changeassigned")
    public @ResponseBody Project changeAssigned(@RequestBody Project p)
    {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        Collection<TempGrade> t = tempGradeSearchRepository.gradesId(a.get(0).getProjectid());
        ArrayList<TempGrade> at = new ArrayList<>(t);
        String emp;

        if(!p.getLba().equals(a.get(0).getLba()))
        {
            emp = p.getLba().substring(0,8);
            a.get(0).setLba(emp);
            at.get(0).setQagrade("5.00");
            at.get(0).setPmgrade("");
            at.get(0).setDevgrade("0.00");
            at.get(0).setDevcount("0");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        if(!p.getJba1().equals(a.get(0).getJba1()))
        {
            emp = p.getJba1().substring(0,8);
            a.get(0).setJba1(emp);
            at.get(0).setCustomergrade1("");
            at.get(0).setSystemgrade1("5.00");
            at.get(0).setSbagrade1("");
//            at.get(0).setCustomergrade("");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        if(!p.getJba2().equals(a.get(0).getJba2()))
        {
            emp = p.getJba2().substring(0,8);
            a.get(0).setJba2(emp);
            at.get(0).setCustomergrade2("");
            at.get(0).setSystemgrade2("5.00");
            at.get(0).setSbagrade2("");
//            at.get(0).setCustomergrade("");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        if(!p.getDev1().equals(a.get(0).getDev1()))
        {
            emp = p.getDev1().substring(0,8);
            a.get(0).setDev1(emp);
            at.get(0).setDev1grade("required");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        if(p.getDev2()!=null)
        {
            emp = p.getDev2().substring(0,8);
            a.get(0).setDev2(emp);
            at.get(0).setDev2grade("required");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        if(p.getDev3()!=null)
        {
            emp = p.getDev3().substring(0,8);
            a.get(0).setDev3(emp);
            at.get(0).setDev3grade("required");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        if(p.getDev4()!=null)
        {
            emp = p.getDev4().substring(0,8);
            a.get(0).setDev4(emp);
            at.get(0).setDev4grade("required");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        if(p.getDev5()!=null)
        {
            emp = p.getDev5().substring(0,8);
            a.get(0).setDev5(emp);
            at.get(0).setDev5grade("required");
            notificationManager.assigned(emp,a.get(0).getProjectid());
        }
        projectMongoRepository.save(a.get(0));
        tempGradeMongoRepository.save(at.get(0));

        return a.get(0);
    }

    //end an existing project after completion

    @RequestMapping("/endproject")
    public @ResponseBody Project endProject(@RequestBody Project p) throws IOException, DocumentException {
        Collection<SRS> collection = srsSearchRepository.getFromProjectid(p.getProjectid());
        ArrayList<SRS> arrayList = new ArrayList<>(collection);
        arrayList.get(0).setState("completed");
        srsMongoRepository.save(arrayList.get(0));
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        a.get(0).setStatus("completed");
        a.get(0).setEnddate(notificationManager.giveDate());
        projectMongoRepository.save(a.get(0));
        Collection<TempGrade> tc = tempGradeSearchRepository.gradesId(a.get(0).getProjectid());
        ArrayList<TempGrade> ta = new ArrayList<>(tc);
        ta.get(0).setStatus("end");
        tempGradeMongoRepository.save(ta.get(0));
        if(ta.get(0).getPmgrade().equals(""))
        {
            Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getPm());
            ArrayList<Employee> ac = new ArrayList<>(ec);
            ac.get(0).setGradestatus("required");
            employeeMongoRepository.save(ac.get(0));
            GradeProject gradeProject = new GradeProject();
            gradeProject.setDescription(a.get(0).getDescription());
            gradeProject.setProjectid(a.get(0).getProjectid());
            gradeProject.setEmployeeid(a.get(0).getPm());
            gradeProject.setStatus("new");
            gradeProject.setName(a.get(0).getName());
            gradeProjectMongoRepository.save(gradeProject);
        }
        if(ta.get(0).getSbagrade1().equals("") || ta.get(0).getSbagrade2().equals(""))
        {
            Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getLba());
            ArrayList<Employee> ac = new ArrayList<>(ec);
            ac.get(0).setGradestatus("required");
            employeeMongoRepository.save(ac.get(0));
            GradeProject gradeProject = new GradeProject();
            gradeProject.setDescription(a.get(0).getDescription());
            gradeProject.setProjectid(a.get(0).getProjectid());
            gradeProject.setEmployeeid(a.get(0).getLba());
            gradeProject.setStatus("new");
            gradeProject.setName(a.get(0).getName());
            gradeProjectMongoRepository.save(gradeProject);
        }
        if(ta.get(0).getDev1grade().equals("required"))
        {
            Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getDev1());
            ArrayList<Employee> ac = new ArrayList<>(ec);
            ac.get(0).setGradestatus("required");
            employeeMongoRepository.save(ac.get(0));
            GradeProject gradeProject = new GradeProject();
            gradeProject.setDescription(a.get(0).getDescription());
            gradeProject.setProjectid(a.get(0).getProjectid());
            gradeProject.setEmployeeid(a.get(0).getDev1());
            gradeProject.setStatus("new");
            gradeProject.setName(a.get(0).getName());
            gradeProjectMongoRepository.save(gradeProject);
        }
        if(ta.get(0).getDev2grade().equals("required"))
        {
            Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getDev2());
            ArrayList<Employee> ac = new ArrayList<>(ec);
            ac.get(0).setGradestatus("required");
            employeeMongoRepository.save(ac.get(0));
            GradeProject gradeProject = new GradeProject();
            gradeProject.setDescription(a.get(0).getDescription());
            gradeProject.setProjectid(a.get(0).getProjectid());
            gradeProject.setEmployeeid(a.get(0).getDev2());
            gradeProject.setStatus("new");
            gradeProject.setName(a.get(0).getName());
            gradeProjectMongoRepository.save(gradeProject);
        }
        if(ta.get(0).getDev3grade().equals("required"))
        {
            Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getDev3());
            ArrayList<Employee> ac = new ArrayList<>(ec);
            ac.get(0).setGradestatus("required");
            employeeMongoRepository.save(ac.get(0));
            GradeProject gradeProject = new GradeProject();
            gradeProject.setDescription(a.get(0).getDescription());
            gradeProject.setProjectid(a.get(0).getProjectid());
            gradeProject.setEmployeeid(a.get(0).getDev3());
            gradeProject.setStatus("new");
            gradeProject.setName(a.get(0).getName());
            gradeProjectMongoRepository.save(gradeProject);
        }
        if(ta.get(0).getDev4grade().equals("required"))
        {
            Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getDev4());
            ArrayList<Employee> ac = new ArrayList<>(ec);
            ac.get(0).setGradestatus("required");
            employeeMongoRepository.save(ac.get(0));
            GradeProject gradeProject = new GradeProject();
            gradeProject.setDescription(a.get(0).getDescription());
            gradeProject.setProjectid(a.get(0).getProjectid());
            gradeProject.setEmployeeid(a.get(0).getDev4());
            gradeProject.setStatus("new");
            gradeProject.setName(a.get(0).getName());
            gradeProjectMongoRepository.save(gradeProject);
        }
        if(ta.get(0).getDev5grade().equals("required"))
        {
            Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getDev5());
            ArrayList<Employee> ac = new ArrayList<>(ec);
            ac.get(0).setGradestatus("required");
            employeeMongoRepository.save(ac.get(0));
            GradeProject gradeProject = new GradeProject();
            gradeProject.setDescription(a.get(0).getDescription());
            gradeProject.setProjectid(a.get(0).getProjectid());
            gradeProject.setEmployeeid(a.get(0).getDev5());
            gradeProject.setStatus("new");
            gradeProject.setName(a.get(0).getName());
            gradeProjectMongoRepository.save(gradeProject);
        }
        notificationManager.notifyByPMComplete(p.getProjectid());

        storageService.storePDF(a.get(0));

        Collection<SRS> sc = srsSearchRepository.getFromProjectid(a.get(0).getProjectid());
        ArrayList<SRS> sa = new ArrayList<>(sc);
        Collection<InterfaceDesign> ic = interfaceDesignSearchRepository.getFromSRSId(sa.get(0).getSRSid());
        ArrayList<InterfaceDesign> ia = new ArrayList<>(ic);
        Collection<Overall> oc = overallSearchRepository.getFromSRSId(sa.get(0).getSRSid());
        ArrayList<Overall> oa = new ArrayList<>(oc);

        Path FROM = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\" + a.get(0).getProjectid() + ".pdf");
        boolean f = new File("I:\\Final Software Project\\Final\\New folder\\src\\assets\\Projects\\" + a.get(0).getProjectid()).mkdir();
        Path TO = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\Projects\\" + a.get(0).getProjectid() + "\\" + a.get(0).getProjectid() +".pdf");
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(FROM, TO, options);
        f = new File("C:\\Projects\\" + a.get(0).getProjectid()).mkdir();
        TO = Paths.get("C:\\Projects\\" + a.get(0).getProjectid() + "\\" + a.get(0).getProjectid() +".pdf");
        options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(FROM, TO, options);

        if(oa.get(0).getDiagram1().equals("available")) {
            FROM = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\" + a.get(0).getProjectid() + "overall.pdf");
            TO = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\Projects\\" + a.get(0).getProjectid() + "\\" + a.get(0).getProjectid() + "overall.pdf");
            options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(FROM, TO, options);
            TO = Paths.get("C:\\Projects\\" + a.get(0).getProjectid() + "\\" + a.get(0).getProjectid() + "overall.pdf");
            options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(FROM, TO, options);
        }

        if(ia.get(0).getAttachment().equals("available")) {
            FROM = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\" + a.get(0).getProjectid() + "display.pdf");
            TO = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\Projects\\" + a.get(0).getProjectid() + "\\" + a.get(0).getProjectid() + "display.pdf");
            options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(FROM, TO, options);
            TO = Paths.get("C:\\Projects\\" + a.get(0).getProjectid() + "\\" + a.get(0).getProjectid() + "display.pdf");
            options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(FROM, TO, options);
        }

        return a.get(0);
    }

    //get the project list

    @RequestMapping("/projectlist")
    public @ResponseBody ArrayList<Project> getProjectList(@RequestBody Employee e)
    {
        Collection<Project> c = projectSearchRepository.projectList(e.getEmployeeid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<Project> ordered = new ArrayList<Project>();
        int items = a.size(),i;
        for(i=items;i>0;i--)
            ordered.add(a.get(i-1));
        return ordered;
    }

    @RequestMapping("/ongoinglist")
    public @ResponseBody ArrayList<Project> getOngoingProjectList(@RequestBody Employee e) throws ParseException {
        Collection<Project> cupc = projectSearchRepository.current();
        ArrayList<Project> cupa = new ArrayList<>(cupc);
        Iterator<Project> cupi = cupa.iterator();
        String today = notificationManager.giveDate();
        Calendar endcalender = new GregorianCalendar();
        Date enddate = new SimpleDateFormat("dd/MM/yyyy").parse(today);
        endcalender.setTime(enddate);
        Calendar startcalender = new GregorianCalendar();
        int count = 0;
        while (cupi.hasNext())
        {
            Project p = cupi.next();
            Date startdate = new SimpleDateFormat("dd/MM/yyyy").parse(p.getLastupdated());
            startcalender.setTime(startdate);
            while (startcalender.before(endcalender)) {
                count++;
                startcalender.add(Calendar.DATE, 1);
            }
            p.setDays(String.valueOf(count));
            count = 0;
            startcalender.setTime(startdate);
            projectMongoRepository.save(p);
        }
        Collection<Project> c = projectSearchRepository.projectList(e.getEmployeeid());
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<Project> ordered = new ArrayList<Project>();
        int items = a.size(),i;
        for(i=items;i>0;i--) {
            Collection<SRS> sc = srsSearchRepository.getFromProjectid(a.get(i-1).getProjectid());
            ArrayList<SRS> sa = new ArrayList<>(sc);
            if(a.get(i-1).getJba1().equals(e.getEmployeeid()) && sa.get(0).getJba1status().equals("notsubmitted"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getJba2().equals(e.getEmployeeid()) && sa.get(0).getJba2status().equals("notsubmitted"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getPm().equals(e.getEmployeeid()) && sa.get(0).getState().equals("approved"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getLba().equals(e.getEmployeeid()) && sa.get(0).getState().equals("submitted"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getDev1().equals(e.getEmployeeid()) && sa.get(0).getState().equals("acknowledged"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getDev2().equals(e.getEmployeeid()) && sa.get(0).getState().equals("acknowledged"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getDev3().equals(e.getEmployeeid()) && sa.get(0).getState().equals("acknowledged"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getDev4().equals(e.getEmployeeid()) && sa.get(0).getState().equals("acknowledged"))
                ordered.add(a.get(i - 1));
            else if(a.get(i-1).getDev5().equals(e.getEmployeeid()) && sa.get(0).getState().equals("acknowledged"))
                ordered.add(a.get(i - 1));
        }
        return ordered;
    }

    // view information regarding a project

    @RequestMapping("/projectinfo")
    public @ResponseBody Project projectInfo(@RequestBody Project p)
    {
        Project pro = new Project();
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        pro = a.get(0);
        pro.setPm(fileProcessor.getEmpName(a.get(0).getPm()));
        pro.setLba(fileProcessor.getEmpName(a.get(0).getLba()));
        pro.setJba1(fileProcessor.getEmpName(a.get(0).getJba1()));
        pro.setJba2(fileProcessor.getEmpName(a.get(0).getJba2()));
        if(!a.get(0).getDev1().equals(""))
            pro.setDev1(fileProcessor.getEmpName(a.get(0).getDev1()));
        if(!a.get(0).getDev2().equals(""))
            pro.setDev2(fileProcessor.getEmpName(a.get(0).getDev2()));
        if(!a.get(0).getDev3().equals(""))
            pro.setDev3(fileProcessor.getEmpName(a.get(0).getDev3()));
        if(!a.get(0).getDev4().equals(""))
            pro.setDev4(fileProcessor.getEmpName(a.get(0).getDev4()));
        if(!a.get(0).getDev5().equals(""))
            pro.setDev5(fileProcessor.getEmpName(a.get(0).getDev5()));
        Collection<Customer> cc = customerSearchRepository.searchCusID(a.get(0).getCustomerid());
        ArrayList<Customer> ca = new ArrayList<>(cc);
        pro.setCustomerid(ca.get(0).getName());
        return pro;
    }

    //view the question list of a project

    @RequestMapping("/questionlist")
    public @ResponseBody ArrayList<Question> questionList(@RequestBody Question q)
    {
        System.out.println("working");
        Collection<Question> collection = questionSearchRepository.getFromProjectid(q.getProjectid());
        ArrayList<Question> newList = new ArrayList<>(collection);
        return calculation.questionSort(newList);
    }

    //raise an attention to a question

    @RequestMapping("/attentionquestion")
    public @ResponseBody Question raiseAttention(@RequestBody Question q)
    {
        Collection<Question> c = questionSearchRepository.getFromQuestionid(q.getQuestionid());
        ArrayList<Question> a = new ArrayList<>(c);
        int i = Integer.parseInt(a.get(0).getAttention())+1;
        a.get(0).setAttention(String.valueOf(i));
        a.get(0).setLastupdate(notificationManager.giveDate()+" "+notificationManager.giveTime());
        questionMongoRepository.save(a.get(0));
        Collection<TempGrade> tgc = tempGradeSearchRepository.gradesId(a.get(0).getProjectid());
        ArrayList<TempGrade> tga = new ArrayList<>(tgc);
        double d = Double.parseDouble(tga.get(0).getQagrade()) - 0.2;
        tga.get(0).setQagrade(String.valueOf(d));
        tempGradeMongoRepository.save(tga.get(0));
        return a.get(0);
    }

    // raise a new question

    @RequestMapping("/newquestion")
    public @ResponseBody Question saveQuestion(@RequestBody Question q)
    {
        q.setDate(notificationManager.giveDate());
        q.setTime(notificationManager.giveTime());
        q.setQuestionid(fileProcessor.getQuestionNo());
        q.setLastupdate(q.getDate()+" "+q.getTime());
        q.setAttention("1");
        Collection<Employee> c = employeeSearchRepository.searchUserId(q.getEmployeeid());
        ArrayList<Employee> ar = new ArrayList<>(c);
        q.setEmployeeid(ar.get(0).getFname()+" "+ar.get(0).getLname());
        questionMongoRepository.save(q);
        Collection<TempGrade> tgc = tempGradeSearchRepository.gradesId(q.getProjectid());
        ArrayList<TempGrade> tga = new ArrayList<>(tgc);
        double d = Double.parseDouble(tga.get(0).getQagrade()) - 0.2;
        tga.get(0).setQagrade(String.valueOf(d));
        tempGradeMongoRepository.save(tga.get(0));
        notificationManager.notifyNewQuestion(q.getProjectid());
        return q;
    }

    //view the answers for a question

    @RequestMapping("/answerlist")
    public @ResponseBody ArrayList<Answer> answerList(@RequestBody Question q)
    {
//        System.out.println(q.getQuestionid());
        Collection<Answer> c = answerSearchRepository.getFromQuestionId(q.getQuestionid());
        ArrayList<Answer> a = new ArrayList<>(c);
//        ArrayList<Answer> list = new ArrayList<Answer>();
//        System.out.println(a.get(0).getContent());
//        int i = a.size(),x=i-1;
//        while (x>=0) {
//            list.add(a.get(x));
//            x--;
//        }
        return a;
    }

    // submit a new answer to a question

    @RequestMapping("/newanswer")
    public @ResponseBody Answer saveAnswer(@RequestBody Answer a)
    {
        a.setAnswerid(fileProcessor.getAnswerNo());
        a.setDate(notificationManager.giveDate());
        a.setTime(notificationManager.giveTime());
        Collection<Employee> c = employeeSearchRepository.searchUserId(a.getEmployeeid());
        ArrayList<Employee> ar = new ArrayList<>(c);
        a.setEmployeeid(ar.get(0).getFname()+" "+ar.get(0).getLname());
        answerMongoRepository.save(a);
        Collection<Question> qc = questionSearchRepository.getFromQuestionid(a.getQuestionid());
        ArrayList<Question> qa = new ArrayList<>(qc);
        qa.get(0).setLastupdate(notificationManager.giveDate()+" "+notificationManager.giveTime());
        questionMongoRepository.save(qa.get(0));
        Collection<TempGrade> tgc = tempGradeSearchRepository.gradesId(qa.get(0).getProjectid());
        ArrayList<TempGrade> tga = new ArrayList<>(tgc);
        double d = Double.parseDouble(tga.get(0).getQagrade()) - 0.05;
        tga.get(0).setQagrade(String.valueOf(d));
        tempGradeMongoRepository.save(tga.get(0));
        notificationManager.notifyNewAnswer(a.getQuestionid(),qa.get(0).getContent(),qa.get(0).getProjectid());
        return a;
    }

    @RequestMapping("/replylist")
    public @ResponseBody ArrayList<Reply> replyList(@RequestBody Answer a)
    {
//        System.out.println(q.getQuestionid());
        Collection<Reply> c = replySearchRepository.getFromAnswerId(a.getAnswerid());
        ArrayList<Reply> ar = new ArrayList<>(c);
//        ArrayList<Answer> list = new ArrayList<Answer>();
//        System.out.println(a.get(0).getContent());
//        int i = a.size(),x=i-1;
//        while (x>=0) {
//            list.add(a.get(x));
//            x--;
//        }
        return ar;
    }


    @RequestMapping("/newreply")
    public @ResponseBody Reply saveReply(@RequestBody Reply r)
    {
        r.setReplyid(fileProcessor.getReplyNo());
        r.setDate(notificationManager.giveDate());
        r.setTime(notificationManager.giveTime());
        Collection<Employee> c = employeeSearchRepository.searchUserId(r.getEmployeeid());
        ArrayList<Employee> ar = new ArrayList<>(c);
        r.setEmployeeid(ar.get(0).getFname()+" "+ar.get(0).getLname());
        replyMongoRepository.save(r);
        Collection<Answer> ac = answerSearchRepository.getFromAnswerId(r.getAnswerid());
        ArrayList<Answer> aa = new ArrayList<>(ac);
        Collection<Question> qc = questionSearchRepository.getFromQuestionid(aa.get(0).getQuestionid());
        ArrayList<Question> qa = new ArrayList<>(qc);
        qa.get(0).setLastupdate(notificationManager.giveDate()+" "+notificationManager.giveTime());
        questionMongoRepository.save(qa.get(0));
        notificationManager.notifyNewReply(r.getAnswerid(),qa.get(0).getContent(),qa.get(0).getProjectid(),aa.get(0).getContent());
        return r;
    }

    // get the question to view on top of the answers

    @RequestMapping("/question")
    public @ResponseBody Question viewQuestion(@RequestBody Question q)
    {
        Collection<Question> c = questionSearchRepository.getFromQuestionid(q.getQuestionid());
        ArrayList<Question> a = new ArrayList<>(c);
        return a.get(0);
    }

    @RequestMapping("/answer")
    public @ResponseBody Answer viewAnswer(@RequestBody Answer a)
    {
        Collection<Answer> c = answerSearchRepository.getFromAnswerId(a.getAnswerid());
        ArrayList<Answer> ar = new ArrayList<>(c);
        return ar.get(0);
    }

    //get the project name to view on top of the questions

    @RequestMapping("/projectname")
    public @ResponseBody Question viewProjectName(@RequestBody Question q)
    {
        Collection<Project> c = projectSearchRepository.projectDetailsId(q.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        q.setProjectid(a.get(0).getName());
        return q;
    }

    //get the details of a project to view on the SRS

    @RequestMapping("/detailsforsrs")
    public @ResponseBody Project giveDetailsForSRS(@RequestBody Project p)
    {
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        p.setName(a.get(0).getName());
        p.setPm(fileProcessor.getEmpName(a.get(0).getPm()));
        Collection<Customer> cc = customerSearchRepository.searchCusID(a.get(0).getCustomerid());
        ArrayList<Customer> ac = new ArrayList<>(cc);
        p.setCustomerid(ac.get(0).getName());
        return p;
    }

    //get the SRS section 1

    @RequestMapping("/detailsforsrssec1")
    public @ResponseBody Section1 giveDetailsForSec1(@RequestBody Project p)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(p.getProjectid());
        ArrayList<SRS> a = new ArrayList<>(c);

        Collection<Section1> sc = section1SearchRepository.getFromSRSid(a.get(0).getSRSid());
        ArrayList<Section1> sa = new ArrayList<>(sc);

        return sa.get(0);
    }

    //get the SRS section 2

    @RequestMapping("/detailsforsrssec2")
    public @ResponseBody Section2 giveDetailsForSec2(@RequestBody Project p)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(p.getProjectid());
        ArrayList<SRS> a = new ArrayList<>(c);

        Collection<Section2> sc = section2SearchRepository.getFromSRSid(a.get(0).getSRSid());
        ArrayList<Section2> sa = new ArrayList<>(sc);

        return sa.get(0);
    }

    //after the editing save the SRS

    @RequestMapping("/saveSRS")
    public @ResponseBody ModelSRS saveSRS(@RequestBody ModelSRS ms)
    {
        Collection<SRS> sc = srsSearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<SRS> sa = new ArrayList<>(sc);
        Collection<Project> pc = projectSearchRepository.projectDetailsId(sa.get(0).getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        Collection<TempGrade> tc = tempGradeSearchRepository.gradesId(pa.get(0).getProjectid());
        ArrayList<TempGrade> ta = new ArrayList<>(tc);
        if(pa.get(0).getJba1().equals(ms.getEmployeeid()))
        {
            Collection<Section1> secc = section1SearchRepository.getFromSRSid(ms.getSRSid());
            ArrayList<Section1> seca = new ArrayList<>(secc);
            Section1 sec1 = seca.get(0);
            sec1.setDatemodified(notificationManager.giveDate());
            sec1.setEmployeeid(pa.get(0).getJba1());
            sec1.setInput(ms.getInput());
            sec1.setOutput(ms.getOutput());
            sec1.setUser(ms.getUser());
            sec1.setUserstory(ms.getUserstory());
            sec1.setObjective(ms.getObjective());
            sec1.setSRSid(ms.getSRSid());
            sec1.setPMcomment1(ms.getPMcomment1());
            sec1.setSBAcomment1(ms.getSBAcomment1());
            section1MongoRepository.save(sec1);
            Double d = Double.parseDouble(ta.get(0).getSystemgrade1()) - (0.1* ms.getLongsentence()) - (0.4*ms.getUncertain());
            ta.get(0).setSystemgrade1(String.valueOf(d));
            tempGradeMongoRepository.save(ta.get(0));
        }
        else if(pa.get(0).getJba2().equals(ms.getEmployeeid()))
        {
            Collection<Section2> secc = section2SearchRepository.getFromSRSid(ms.getSRSid());
            ArrayList<Section2> seca = new ArrayList<>(secc);
            Section2 sec2 = seca.get(0);
            sec2.setDatemodified(notificationManager.giveDate());
            sec2.setEmployeeid(pa.get(0).getJba2());
            sec2.setAdditionalnote(ms.getAdditionalnote());
            sec2.setNetworkrequirement(ms.getNetworkrequirement());
            sec2.setSystemspecification(ms.getSystemspecification());
            sec2.setTechnicalrequirement(ms.getTechnicalrequirement());
            sec2.setSecuritylevelspecification(ms.getSecuritylevelspecification());
            sec2.setSRSid(ms.getSRSid());
            sec2.setPMcomment2(ms.getPMcomment2());
            sec2.setSBAcomment2(ms.getSBAcomment2());
            section2MongoRepository.save(sec2);
            Double d = Double.parseDouble(ta.get(0).getSystemgrade2()) - (0.1* ms.getLongsentence()) - (0.4*ms.getUncertain());
            ta.get(0).setSystemgrade2(String.valueOf(d));
            tempGradeMongoRepository.save(ta.get(0));
        }
        return ms;
    }

    //after completing the SRS submit it to the SBA

    @RequestMapping("/submitSRS")
    public @ResponseBody ModelSRS submitSRS(@RequestBody ModelSRS ms)
    {
        Collection<SRS> sc = srsSearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<SRS> sa = new ArrayList<>(sc);
        Collection<Project> pc = projectSearchRepository.projectDetailsId(sa.get(0).getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        Collection<TempGrade> tc = tempGradeSearchRepository.gradesId(pa.get(0).getProjectid());
        ArrayList<TempGrade> ta = new ArrayList<>(tc);
        if(pa.get(0).getJba1().equals(ms.getEmployeeid()))
        {
            Collection<Section1> secc = section1SearchRepository.getFromSRSid(ms.getSRSid());
            ArrayList<Section1> seca = new ArrayList<>(secc);
            Section1 sec1 = seca.get(0);
            sec1.setDatemodified(notificationManager.giveDate());
            sec1.setEmployeeid(pa.get(0).getJba1());
            sec1.setInput(ms.getInput());
            sec1.setOutput(ms.getOutput());
            sec1.setUser(ms.getUser());
            sec1.setUserstory(ms.getUserstory());
            sec1.setObjective(ms.getObjective());
            sec1.setSRSid(ms.getSRSid());
            sec1.setPMcomment1(ms.getPMcomment1());
            sec1.setSBAcomment1(ms.getSBAcomment1());
            section1MongoRepository.save(sec1);
            sa.get(0).setJba1status("submitted");
            srsMongoRepository.save(sa.get(0));
            Double d = Double.parseDouble(ta.get(0).getSystemgrade1()) - (0.1* ms.getLongsentence()) - (0.4*ms.getUncertain());
            ta.get(0).setSystemgrade1(String.valueOf(d));
            tempGradeMongoRepository.save(ta.get(0));
        }
        else if(pa.get(0).getJba2().equals(ms.getEmployeeid()))
        {
            Collection<Section2> secc = section2SearchRepository.getFromSRSid(ms.getSRSid());
            ArrayList<Section2> seca = new ArrayList<>(secc);
            Section2 sec2 = seca.get(0);
            sec2.setDatemodified(notificationManager.giveDate());
            sec2.setEmployeeid(pa.get(0).getJba2());
            sec2.setAdditionalnote(ms.getAdditionalnote());
            sec2.setNetworkrequirement(ms.getNetworkrequirement());
            sec2.setSystemspecification(ms.getSystemspecification());
            sec2.setTechnicalrequirement(ms.getTechnicalrequirement());
            sec2.setSecuritylevelspecification(ms.getSecuritylevelspecification());
            sec2.setSRSid(ms.getSRSid());
            sec2.setPMcomment2(ms.getPMcomment2());
            sec2.setSBAcomment2(ms.getSBAcomment2());
            section2MongoRepository.save(sec2);
            sa.get(0).setJba2status("submitted");
            srsMongoRepository.save(sa.get(0));
            Double d = Double.parseDouble(ta.get(0).getSystemgrade2()) - (0.1* ms.getLongsentence()) - (0.4*ms.getUncertain());
            ta.get(0).setSystemgrade2(String.valueOf(d));
            tempGradeMongoRepository.save(ta.get(0));
        }
        Collection<SRS> newSRS = srsSearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<SRS> newSRSa = new ArrayList<>(newSRS);
        if(newSRSa.get(0).getJba1status().equals("submitted") && newSRSa.get(0).getJba2status().equals("submitted"))
        {
            newSRSa.get(0).setState("submitted");
            srsMongoRepository.save(newSRSa.get(0));
            notificationManager.notifyByJBASubmitted(newSRSa.get(0).getProjectid());
            pa.get(0).setStatus("Submitted By JBA");
            projectMongoRepository.save(pa.get(0));
        }

        return ms;
    }

    //SBA to add his comments and save the SRS

    @RequestMapping("/saveSBAComment1")
    public @ResponseBody ModelSRS saveSBAComments1(@RequestBody ModelSRS ms)
    {
        System.out.println("rwef"+ms.getSBAcomment1());
        Collection<Section1> cs1 = section1SearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<Section1> as1 = new ArrayList<>(cs1);
        as1.get(0).setSBAcomment1(ms.getSBAcomment1());
        section1MongoRepository.save(as1.get(0));
        Collection<SRS> c = srsSearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<SRS> a = new ArrayList<>(c);
        notificationManager.notifyBySBACommented(a.get(0).getProjectid());
        return ms;
    }

    @RequestMapping("/saveSBAComment2")
    public @ResponseBody ModelSRS saveSBAComments2(@RequestBody ModelSRS ms)
    {
        Collection<Section2> cs2 = section2SearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<Section2> as2 = new ArrayList<>(cs2);
        as2.get(0).setSBAcomment2(ms.getSBAcomment2());
        section2MongoRepository.save(as2.get(0));
        Collection<SRS> c = srsSearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<SRS> a = new ArrayList<>(c);
        notificationManager.notifyBySBACommented(a.get(0).getProjectid());
        return ms;
    }

    //SBA approve the SRS

    @RequestMapping("/approveSRS")
    public @ResponseBody Question approveSRS(@RequestBody Question q)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(q.getProjectid());
        ArrayList<SRS> a = new ArrayList<>(c);
        a.get(0).setState("approved");
        srsMongoRepository.save(a.get(0));
        notificationManager.notifyBySBASubmitted(a.get(0).getProjectid());
        Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        pa.get(0).setStatus("Approved by SBA");
        pa.get(0).setLastupdated(notificationManager.giveDate());
        projectMongoRepository.save(pa.get(0));
        return q;
    }

    //SBA reject the SRS

    @RequestMapping("/rejectSRS")
    public @ResponseBody Question rejectSRS(@RequestBody Question q)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(q.getProjectid());
        ArrayList<SRS> a = new ArrayList<>(c);
        a.get(0).setState("rejected");
        a.get(0).setJba1status("notsubmitted");
        a.get(0).setJba2status("notsubmitted");
        srsMongoRepository.save(a.get(0));
        notificationManager.notifyBySBARejected(a.get(0).getProjectid());
        Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        pa.get(0).setStatus("Rejected by SBA");
        pa.get(0).setLastupdated(notificationManager.giveDate());
        projectMongoRepository.save(pa.get(0));
        Collection<Introdution> ci = introductionSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<Introdution> ai = new ArrayList<>(ci);
        ai.get(0).setStatus("");
        introductionMongoRepository.save(ai.get(0));
        Collection<InterfaceDesign> cid = interfaceDesignSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<InterfaceDesign> aid = new ArrayList<>(cid);
        aid.get(0).setStatus("");
        interfaceDesignMongoRepository.save(aid.get(0));
        Collection<Overall> co = overallSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<Overall> ao = new ArrayList<>(co);
        ao.get(0).setStatus("");
        overallMongoRepository.save(ao.get(0));
        Collection<NonFunctional> cn = nonFunctionalSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<NonFunctional> an = new ArrayList<>(cn);
        an.get(0).setStatus("");
        nonFunctionalMongoRepository.save(an.get(0));
        Collection<SystemFeatures> cf = systemFeaturesSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<SystemFeatures> af = new ArrayList<>(cf);
        af.get(0).setStatus("");
        systemFeaturesMongoRepository.save(af.get(0));
        return q;
    }

    //save the PM added comments in the SRS

    @RequestMapping("/savePMComment1")
    public @ResponseBody ModelSRS savePMComments1(@RequestBody ModelSRS ms)
    {
        Collection<Section1> cs1 = section1SearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<Section1> as1 = new ArrayList<>(cs1);
        as1.get(0).setPMcomment1(ms.getPMcomment1());
        section1MongoRepository.save(as1.get(0));
        Collection<SRS> c = srsSearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<SRS> a = new ArrayList<>(c);
        notificationManager.notifyByPMCommented(a.get(0).getProjectid());
        return ms;
    }

    @RequestMapping("/savePMComment2")
    public @ResponseBody ModelSRS savePMComments2(@RequestBody ModelSRS ms)
    {
        Collection<Section2> cs2 = section2SearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<Section2> as2 = new ArrayList<>(cs2);
        as2.get(0).setPMcomment2(ms.getPMcomment2());
        section2MongoRepository.save(as2.get(0));
        Collection<SRS> c = srsSearchRepository.getFromSRSid(ms.getSRSid());
        ArrayList<SRS> a = new ArrayList<>(c);
        notificationManager.notifyByPMCommented(a.get(0).getProjectid());
        return ms;
    }

    //PM acknowleding the SRS

    @RequestMapping("/acknowledgeSRS")
    public @ResponseBody Question acknowledgeSRS(@RequestBody Question q)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(q.getProjectid());
        ArrayList<SRS> a = new ArrayList<>(c);
        a.get(0).setState("acknowledged");
        srsMongoRepository.save(a.get(0));
        Project p = new Project();
        p.setProjectid(a.get(0).getProjectid());
        email.SRStoCustomerEmail(p);
        notificationManager.notifyByPMApproved(p.getProjectid());
        Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        pa.get(0).setStatus("Approved by PM");
        pa.get(0).setLastupdated(notificationManager.giveDate());
        pa.get(0).setDays("99999");
        projectMongoRepository.save(pa.get(0));
        return q;
    }

    @RequestMapping("/currentprojects")
    public @ResponseBody ArrayList<Project> currentProjects()
    {
        Collection<Project> c = projectSearchRepository.current();
        ArrayList<Project> a = new ArrayList<>(c);
        ArrayList<Project> anew = new ArrayList<>();
        int i,count=a.size();

        for(i=count-1;i>=0;i--)
            anew.add(a.get(i));

        return anew;
    }

    @RequestMapping("/changepm")
    public @ResponseBody Employee changePM(@RequestBody Employee e)
    {
        Collection<Employee> c = employeeSearchRepository.searchUserEmail(e.getEmail());
        ArrayList<Employee> a = new ArrayList<>(c);

        if(a.isEmpty() || !a.get(0).getType().equals("PM"))
            e.setStatus("invalid");
        else
        {
            Collection<Project> cp = projectSearchRepository.projectDetailsId(e.getStatus());
            ArrayList<Project> ap = new ArrayList<>(cp);
            ap.get(0).setPm(a.get(0).getEmployeeid());
            projectMongoRepository.save(ap.get(0));
            Notification n = new Notification();
            n.setNotificationid(notificationManager.getNotificationNo());
            n.setEmplyeeid(a.get(0).getEmployeeid());
            n.setContent("Project "+ap.get(0).getName()+" was transferred to you");
            n.setDate(notificationManager.giveDate());
            n.setTime(notificationManager.giveTime());
            n.setStatus("false");
            n.setType("Project");
            n.setTypeid(ap.get(0).getProjectid());
            n.setChatname("none");
            notificationMongoRepository.save(n);
        }
        return e;
    }

    @RequestMapping("/getSRS")
    public @ResponseBody SRS getSRS(@RequestBody SRS s)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(s.getProjectid());
        ArrayList<SRS> a = new ArrayList<>(c);
        return a.get(0);
    }

    //get the state of the SRS to select what options to give in the SRS page

    @RequestMapping("/getstate")
    public @ResponseBody SRS giveState(@RequestBody Project p)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(p.getProjectid());
        ArrayList<SRS> a = new ArrayList<>(c);
        return a.get(0);
    }

    //get the state of the SRS to know to which users the SRS should be visible

    @RequestMapping("/checkstate")
    public @ResponseBody SRS checkState(@RequestBody String s)
    {
        System.out.println(s);
        Collection<SRS> c = srsSearchRepository.getFromProjectid(s);
        ArrayList<SRS> a = new ArrayList<>(c);
        return a.get(0);
    }

    //checks whether the employee had already graded the project

    @RequestMapping("/checkgraded")
    public @ResponseBody Project gradeCheck(@RequestBody Project p)
    {
        Collection<Project> pc = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        String s = null;
        if(pa.get(0).getPm().equals(p.getName()))
            s = "pm";                                   // The employee id is saved in the name from angular frontend
        if(pa.get(0).getLba().equals(p.getName()))
            s = "lba";
        if(pa.get(0).getDev1().equals(p.getName()))
            s = "dev1";
        if(pa.get(0).getDev2().equals(p.getName()))
            s = "dev2";
        if(pa.get(0).getDev3().equals(p.getName()))
            s = "dev3";
        if(pa.get(0).getDev4().equals(p.getName()))
            s = "dev4";
        if(pa.get(0).getDev5().equals(p.getName()))
            s = "dev5";
        Collection<TempGrade> gpc = tempGradeSearchRepository.gradesId(p.getProjectid());
        ArrayList<TempGrade> gpa = new ArrayList<>(gpc);
        if (s.equals("pm") && !gpa.get(0).getPmgrade().equals("") && !gpa.get(0).getCustomergrade1().equals(""))
            p.setProjectid("graded");
        if (s.equals("lba") && !gpa.get(0).getSbagrade1().equals("") && !gpa.get(0).getSbagrade2().equals(""))
            p.setProjectid("graded");
        if (s.equals("dev1") && !gpa.get(0).getDev1grade().equals("required"))
            p.setProjectid("graded");
        if (s.equals("dev2") && !gpa.get(0).getDev2grade().equals("required"))
            p.setProjectid("graded");
        if (s.equals("dev3") && !gpa.get(0).getDev3grade().equals("required"))
            p.setProjectid("graded");
        if (s.equals("dev4") && !gpa.get(0).getDev4grade().equals("required"))
            p.setProjectid("graded");
        if (s.equals("dev5") && !gpa.get(0).getDev5grade().equals("required"))
            p.setProjectid("graded");

        return p;
    }

    //projects to be graded by an employee

    @RequestMapping("/gradinglist")
    public @ResponseBody ArrayList<GradeProject> getGradeList(@RequestBody Employee e)
    {
        Collection<GradeProject> c = gradeProjectSearchRepository.searchUserId(e.getEmployeeid());
        ArrayList<GradeProject> a = new ArrayList<>(c);
        Iterator<GradeProject> i = a.iterator();
        ArrayList<GradeProject> newa = new ArrayList<GradeProject>();
        while (i.hasNext())
        {
            GradeProject gp = i.next();
            if(gp.getStatus().equals("new"))
                newa.add(gp);
        }
        return newa;
    }

    //get the developer position im the project

    @RequestMapping("/devposition")
    public @ResponseBody Project devPosition(@RequestBody Project p)
    {
        System.out.println(p.getProjectid());
        System.out.println(p.getName());
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        if(a.get(0).getDev1().equals(p.getName()))
            p.setDev1("dev1");
        else if(a.get(0).getDev2().equals(p.getName()))
            p.setDev1("dev2");
        else if(a.get(0).getDev3().equals(p.getName()))
            p.setDev1("dev3");
        else if(a.get(0).getDev4().equals(p.getName()))
            p.setDev1("dev4");
        else
            p.setDev1("dev5");
        return p;
    }

    //get the employee names on the project grading page

    @RequestMapping("/projectempdetails")
    public @ResponseBody Project viewProjectName(@RequestBody Project p)
    {
        System.out.println("testing");
        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> a = new ArrayList<>(c);
        p.setName(a.get(0).getName());
        p.setPm(fileProcessor.getEmpName(a.get(0).getPm()));
        p.setLba(fileProcessor.getEmpName(a.get(0).getLba()));
        p.setJba2(fileProcessor.getEmpName(a.get(0).getJba2()));
        p.setJba1(fileProcessor.getEmpName(a.get(0).getJba1()));
        return p;
    }

    //get the projects grades and save them

    @RequestMapping("/getgrades")
    public @ResponseBody TempGrade saveGrades(@RequestBody TempGrade t)
    {
        Collection<GradeProject> gpc = gradeProjectSearchRepository.searchUserId(t.getEmployeeid());
        ArrayList<GradeProject> gpa = new ArrayList<>(gpc);
        if(!gpa.isEmpty())
        {
            Iterator<GradeProject> i = gpa.iterator();
            while (i.hasNext())
            {
                GradeProject gradeProject = i.next();
                if(gradeProject.getProjectid().equals(t.getProjectid()) && gradeProject.getStatus().equals("new"))
                {
                    gradeProject.setStatus("changed");
                    gradeProjectMongoRepository.save(gradeProject);
                }
            }
        }
        int count = 0;
        Collection<GradeProject> newgpc = gradeProjectSearchRepository.searchUserId(t.getEmployeeid());
        ArrayList<GradeProject> newgpa = new ArrayList<>(newgpc);
        Iterator<GradeProject> newi = newgpa.iterator();
        while (newi.hasNext())
        {
            GradeProject gradeProject = newi.next();
            if(gradeProject.getStatus().equals("new"))
                count++;
        }
        if(count==0)
        {
            Collection<Employee> employee = employeeSearchRepository.searchUserId(t.getEmployeeid());
            ArrayList<Employee> aemployee = new ArrayList<>(employee);
            aemployee.get(0).setGradestatus("done");
            employeeMongoRepository.save(aemployee.get(0));
        }

        Collection<TempGrade> c = tempGradeSearchRepository.gradesId(t.getProjectid());
        ArrayList<TempGrade> a = new ArrayList<>(c);
        if(!t.getSbagrade1().equals("") && t.getSbagrade1()!=null)
            a.get(0).setSbagrade1(t.getSbagrade1());
        if(!t.getSbagrade2().equals("") && t.getSbagrade2()!=null)
            a.get(0).setSbagrade2(t.getSbagrade2());
        if(!t.getPmgrade().equals("") && t.getPmgrade()!=null)
            a.get(0).setPmgrade(t.getPmgrade());
        System.out.println(t.getCustomergrade());
        System.out.println(t.getCustomergrade1());
        System.out.println(t.getCustomergrade2());
        if(!t.getCustomergrade1().equals("") && t.getCustomergrade1()!=null) {
            a.get(0).setCustomergrade1(t.getCustomergrade1());
            a.get(0).setCustomergrade2(t.getCustomergrade1());
//            a.get(0).setCustomergrade(t.getCustomergrade());
        }
        if(!t.getDev1grade().equals("") && t.getDev1grade()!=null)
        {
            a.get(0).setDev1grade((t.getDev1grade()));
            int i = Integer.parseInt(a.get(0).getDevcount());
            i++;
            Double d = (Double.parseDouble(t.getDev1grade())+Double.parseDouble(a.get(0).getDevgrade())*(i-1))/i;
            DecimalFormat df = new DecimalFormat("#.##");
            a.get(0).setDevgrade(df.format(d));
            a.get(0).setDevcount(String.valueOf(i));
        }
        if(!t.getDev2grade().equals("") && t.getDev2grade()!=null)
        {
            a.get(0).setDev2grade((t.getDev2grade()));
            int i = Integer.parseInt(a.get(0).getDevcount());
            i++;
            Double d = (Double.parseDouble(t.getDev2grade())+Double.parseDouble(a.get(0).getDevgrade())*(i-1))/i;
            DecimalFormat df = new DecimalFormat("#.##");
            a.get(0).setDevgrade(df.format(d));
            a.get(0).setDevcount(String.valueOf(i));
        }
        if(!t.getDev3grade().equals("") && t.getDev3grade()!=null)
        {
            a.get(0).setDev3grade((t.getDev3grade()));
            int i = Integer.parseInt(a.get(0).getDevcount());
            i++;
            Double d = (Double.parseDouble(t.getDev3grade())+Double.parseDouble(a.get(0).getDevgrade())*(i-1))/i;
            DecimalFormat df = new DecimalFormat("#.##");
            a.get(0).setDevgrade(df.format(d));
            a.get(0).setDevcount(String.valueOf(i));
        }
        if(!t.getDev4grade().equals("") && t.getDev4grade()!=null)
        {
            a.get(0).setDev4grade((t.getDev4grade()));
            int i = Integer.parseInt(a.get(0).getDevcount());
            i++;
            Double d = (Double.parseDouble(t.getDev4grade())+Double.parseDouble(a.get(0).getDevgrade())*(i-1))/i;
            DecimalFormat df = new DecimalFormat("#.##");
            a.get(0).setDevgrade(df.format(d));
            a.get(0).setDevcount(String.valueOf(i));
        }
        if(!t.getDev5grade().equals("") && t.getDev5grade()!=null)
        {
            a.get(0).setDev5grade((t.getDev5grade()));
            int i = Integer.parseInt(a.get(0).getDevcount());
            i++;
            Double d = (Double.parseDouble(t.getDev5grade())+Double.parseDouble(a.get(0).getDevgrade())*(i-1))/i;
            DecimalFormat df = new DecimalFormat("#.##");
            a.get(0).setDevgrade(df.format(d));
            a.get(0).setDevcount(String.valueOf(i));
        }
        tempGradeMongoRepository.save(a.get(0));
        Collection<TempGrade> cnew = tempGradeSearchRepository.gradesId(t.getProjectid());
        ArrayList<TempGrade> anew = new ArrayList<>(cnew);
        if((!anew.get(0).getDev1grade().equals("required") || anew.get(0).getDev1grade().equals("")) && (!anew.get(0).getDev2grade().equals("required") || anew.get(0).getDev2grade().equals("")) && (!anew.get(0).getDev3grade().equals("required") || anew.get(0).getDev3grade().equals("")) && (!anew.get(0).getDev4grade().equals("required") || anew.get(0).getDev4grade().equals("")) && (!anew.get(0).getDev5grade().equals("required") || anew.get(0).getDev5grade().equals("")) && !anew.get(0).getPmgrade().equals("") && !anew.get(0).getSbagrade1().equals("") && !anew.get(0).getSbagrade2().equals("") && anew.get(0).getStatus().equals("end"))
        {
            Collection<Project> cp = projectSearchRepository.projectDetailsId(t.getProjectid());
            ArrayList<Project> ap = new ArrayList<>(cp);

            if(anew.get(0).getCustomergrade1().equals(""))
            {
                Collection<JBALeaderboard> jba1 = jbaLeaderBoardSearchRepository.gradesJBAId(ap.get(0).getJba1());
                ArrayList<JBALeaderboard> jba1a = new ArrayList<>(jba1);
                anew.get(0).setCustomergrade1(jba1a.get(0).getCustomergrade());

                Collection<JBALeaderboard> jba2 = jbaLeaderBoardSearchRepository.gradesJBAId(ap.get(0).getJba2());
                ArrayList<JBALeaderboard> jba2a = new ArrayList<>(jba2);
                anew.get(0).setCustomergrade2(jba2a.get(0).getCustomergrade());
            }
            else
            {
//                anew.get(0).setCustomergrade1(anew.get(0).getCustomergrade());
//                anew.get(0).setCustomergrade2(anew.get(0).getCustomergrade());
            }
            tempGradeMongoRepository.save(anew.get(0));
            calculation.endProjectGrades(t.getProjectid());
        }
        if(count==0)
            a.get(0).setEmployeeid("done");
        return a.get(0);
    }

    //get the customer details

    @RequestMapping("/getcustomer")
    public @ResponseBody Customer giveCustomer(@RequestBody Project p)
    {
        Collection<Project> pc = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        Collection<Customer> c = customerSearchRepository.searchCusID(pa.get(0).getCustomerid());
        ArrayList<Customer> a = new ArrayList<>(c);
        return a.get(0);
    }

    //edit the already existing customer details

    @RequestMapping("/editcustomer")
    public @ResponseBody Customer updateCustomer(@RequestBody Customer c)
    {
        Collection<Customer> cus = customerSearchRepository.searchCusID(c.getCustomerid());
        ArrayList<Customer> cusa = new ArrayList<>(cus);
        cusa.get(0).setEmail(c.getEmail());
        cusa.get(0).setName(c.getName());
        cusa.get(0).setContact1(c.getContact1());
        customerMongoRepository.save(cusa.get(0));
        return cusa.get(0);
    }

    //send the SRS manually to the customer to get the confirmation

    @RequestMapping("/manualmail")
    public @ResponseBody Project manualEmail(@RequestBody Project p)
    {
        email.SRStoCustomerEmail(p);
        return p;
    }

    @RequestMapping("/getdisplay")
    public @ResponseBody InterfaceDesign getDisplay(@RequestBody InterfaceDesign id)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(id.getDatemodified());
        ArrayList<SRS> a = new ArrayList<>(c);

        Collection<InterfaceDesign> cd = interfaceDesignSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<InterfaceDesign> ad = new ArrayList<>(cd);

        return ad.get(0);
    }

    @RequestMapping("/getintroduction")
    public @ResponseBody Introdution getIntroduction(@RequestBody Introdution id)
    {
        System.out.println(id.getDatemodified());
        System.out.println(id.getEmployeeid());

        Collection<SRS> c = srsSearchRepository.getFromProjectid(id.getDatemodified());
        ArrayList<SRS> a = new ArrayList<>(c);

        Collection<Introdution> cd = introductionSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<Introdution> ad = new ArrayList<>(cd);

        return ad.get(0);
    }

    @RequestMapping("/getnonfunctional")
    public @ResponseBody NonFunctional getNonFunctional(@RequestBody NonFunctional id)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(id.getDatemodified());
        ArrayList<SRS> a = new ArrayList<>(c);

        Collection<NonFunctional> cd = nonFunctionalSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<NonFunctional> ad = new ArrayList<>(cd);

        return ad.get(0);
    }

    @RequestMapping("/getoverall")
    public @ResponseBody Overall getOverall(@RequestBody Overall id)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(id.getDatemodified());
        ArrayList<SRS> a = new ArrayList<>(c);

        Collection<Overall> cd = overallSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<Overall> ad = new ArrayList<>(cd);

        return ad.get(0);
    }

    @RequestMapping("/getfeatures")
    public @ResponseBody SystemFeatures getFeatures(@RequestBody SystemFeatures id)
    {
        Collection<SRS> c = srsSearchRepository.getFromProjectid(id.getDatemodified());
        ArrayList<SRS> a = new ArrayList<>(c);

        Collection<SystemFeatures> cd = systemFeaturesSearchRepository.getFromSRSId(a.get(0).getSRSid());
        ArrayList<SystemFeatures> ad = new ArrayList<>(cd);

        return ad.get(0);
    }

    @RequestMapping("/savedisplay")
    public @ResponseBody InterfaceDesign saveDisplay(@RequestBody InterfaceDesign id)
    {
        interfaceDesignMongoRepository.save(id);
        return id;
    }

    @RequestMapping("/saveoverall")
    public @ResponseBody Overall saveOverall(@RequestBody Overall id)
    {
        overallMongoRepository.save(id);
        return id;
    }

    @RequestMapping("/savenonfunctional")
    public @ResponseBody NonFunctional saveNonFunctional(@RequestBody NonFunctional id)
    {
        nonFunctionalMongoRepository.save(id);
        return id;
    }

    @RequestMapping("/saveintroduction")
    public @ResponseBody Introdution saveIntroduction(@RequestBody Introdution id)
    {
        System.out.println(id.getReference());
        System.out.println(id.getSRSid());
        System.out.println(id.getPMcomment());
        introductionMongoRepository.save(id);
        return id;
    }

    @RequestMapping("/savefeatures")
    public @ResponseBody SystemFeatures saveFeatures(@RequestBody SystemFeatures id)
    {
        systemFeaturesMongoRepository.save(id);
        return id;
    }

    @RequestMapping("/submitdisplay")
    public @ResponseBody InterfaceDesign submitDisplay(@RequestBody InterfaceDesign id)
    {
        id.setStatus("submitted");
        interfaceDesignMongoRepository.save(id);

        int count = 0;
        Collection<Introdution> ci = introductionSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<Introdution> ai = new ArrayList<>(ci);
        if(ai.get(0).getStatus().equals("submitted"))
            count++;
        Collection<Overall> co = overallSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<Overall> ao = new ArrayList<>(co);
        if(ao.get(0).getStatus().equals("submitted"))
            count++;
        if(count==2)
        {
            Collection<SRS> c = srsSearchRepository.getFromSRSid(id.getSRSid());
            ArrayList<SRS> a = new ArrayList<>(c);
            a.get(0).setJba1status("submitted");
            if(a.get(0).getJba2status().equals("submitted")) {
                a.get(0).setState("submitted");
                notificationManager.notifyByJBASubmitted(a.get(0).getProjectid());
                Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
                ArrayList<Project> pa = new ArrayList<>(pc);
                pa.get(0).setStatus("Submitted to SBA");
                projectMongoRepository.save(pa.get(0));
            }
            srsMongoRepository.save(a.get(0));
        }
        return id;

    }

    @RequestMapping("/submitintroduction")
    public @ResponseBody Introdution submitIntroduction(@RequestBody Introdution id)
    {
        id.setStatus("submitted");
        introductionMongoRepository.save(id);

        int count = 0;
        Collection<InterfaceDesign> ci = interfaceDesignSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<InterfaceDesign> ai = new ArrayList<>(ci);
        if(ai.get(0).getStatus().equals("submitted"))
            count++;
        Collection<Overall> co = overallSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<Overall> ao = new ArrayList<>(co);
        if(ao.get(0).getStatus().equals("submitted"))
            count++;
        if(count==2)
        {
            Collection<SRS> c = srsSearchRepository.getFromSRSid(id.getSRSid());
            ArrayList<SRS> a = new ArrayList<>(c);
            a.get(0).setJba1status("submitted");
            if(a.get(0).getJba2status().equals("submitted")) {
                a.get(0).setState("submitted");
                notificationManager.notifyByJBASubmitted(a.get(0).getProjectid());
                Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
                ArrayList<Project> pa = new ArrayList<>(pc);
                pa.get(0).setStatus("Submitted to SBA");
                projectMongoRepository.save(pa.get(0));
            }
            srsMongoRepository.save(a.get(0));
        }
        return id;

    }

    @RequestMapping("/submitoverall")
    public @ResponseBody Overall submitOverall(@RequestBody Overall id)
    {
        id.setStatus("submitted");
        overallMongoRepository.save(id);

        int count = 0;
        Collection<InterfaceDesign> ci = interfaceDesignSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<InterfaceDesign> ai = new ArrayList<>(ci);
        if(ai.get(0).getStatus().equals("submitted"))
            count++;
        Collection<Introdution> co = introductionSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<Introdution> ao = new ArrayList<>(co);
        if(ao.get(0).getStatus().equals("submitted"))
            count++;
        if(count==2)
        {
            Collection<SRS> c = srsSearchRepository.getFromSRSid(id.getSRSid());
            ArrayList<SRS> a = new ArrayList<>(c);
            a.get(0).setJba1status("submitted");
            if(a.get(0).getJba2status().equals("submitted")) {
                a.get(0).setState("submitted");
                notificationManager.notifyByJBASubmitted(a.get(0).getProjectid());
                Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
                ArrayList<Project> pa = new ArrayList<>(pc);
                pa.get(0).setStatus("Submitted to SBA");
                projectMongoRepository.save(pa.get(0));
            }
            srsMongoRepository.save(a.get(0));
        }
        return id;

    }

    @RequestMapping("/submitnonfunctional")
    public @ResponseBody NonFunctional submitNonFunctional(@RequestBody NonFunctional id)
    {
        id.setStatus("submitted");
        nonFunctionalMongoRepository.save(id);

        int count = 1;
        Collection<SystemFeatures> ci = systemFeaturesSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<SystemFeatures> ai = new ArrayList<>(ci);
        if(ai.get(0).getStatus().equals("submitted"))
            count++;
        if(count==2)
        {
            Collection<SRS> c = srsSearchRepository.getFromSRSid(id.getSRSid());
            ArrayList<SRS> a = new ArrayList<>(c);
            a.get(0).setJba2status("submitted");
            if(a.get(0).getJba1status().equals("submitted")) {
                a.get(0).setState("submitted");
                Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
                ArrayList<Project> pa = new ArrayList<>(pc);
                pa.get(0).setStatus("Submitted to SBA");
                projectMongoRepository.save(pa.get(0));
                notificationManager.notifyByJBASubmitted(a.get(0).getProjectid());
            }
            srsMongoRepository.save(a.get(0));
        }
        return id;

    }

    @RequestMapping("/submitfeatures")
    public @ResponseBody SystemFeatures submitFeatures(@RequestBody SystemFeatures id)
    {
        id.setStatus("submitted");
        systemFeaturesMongoRepository.save(id);

        int count = 1;
        Collection<NonFunctional> ci = nonFunctionalSearchRepository.getFromSRSId(id.getSRSid());
        ArrayList<NonFunctional> ai = new ArrayList<>(ci);
        if(ai.get(0).getStatus().equals("submitted"))
            count++;
        if(count==2)
        {
            Collection<SRS> c = srsSearchRepository.getFromSRSid(id.getSRSid());
            ArrayList<SRS> a = new ArrayList<>(c);
            a.get(0).setJba2status("submitted");
            if(a.get(0).getJba1status().equals("submitted")) {
                a.get(0).setState("submitted");
                notificationManager.notifyByJBASubmitted(a.get(0).getProjectid());
                Collection<Project> pc = projectSearchRepository.projectDetailsId(a.get(0).getProjectid());
                ArrayList<Project> pa = new ArrayList<>(pc);
                pa.get(0).setStatus("Submitted to SBA");
                projectMongoRepository.save(pa.get(0));
            }
            srsMongoRepository.save(a.get(0));
        }
        return id;

    }

    @RequestMapping("/savegrades1")
    public @ResponseBody ModelSRS saveGrades1(@RequestBody ModelSRS ms)
    {
        System.out.println(ms.getLongsentence());
        System.out.println(ms.getUncertain());
        System.out.println(ms.getUser());
        Collection<Project> pc = projectSearchRepository.projectDetailsId(ms.getUser());
        ArrayList<Project> pa = new ArrayList<>(pc);
        Collection<TempGrade> tc = tempGradeSearchRepository.gradesId(pa.get(0).getProjectid());
        ArrayList<TempGrade> ta = new ArrayList<>(tc);
        Double d = Double.parseDouble(ta.get(0).getSystemgrade1()) - (0.1* ms.getLongsentence()) - (0.4*ms.getUncertain());
        ta.get(0).setSystemgrade1(String.valueOf(d));
        tempGradeMongoRepository.save(ta.get(0));

        return ms;
    }

    @RequestMapping("/savegrades2")
    public @ResponseBody ModelSRS saveGrades2(@RequestBody ModelSRS ms)
    {
        System.out.println(ms.getLongsentence());
        System.out.println(ms.getUncertain());
        System.out.println(ms.getUser());
        Collection<Project> pc = projectSearchRepository.projectDetailsId(ms.getUser());
        ArrayList<Project> pa = new ArrayList<>(pc);
        Collection<TempGrade> tc = tempGradeSearchRepository.gradesId(pa.get(0).getProjectid());
        ArrayList<TempGrade> ta = new ArrayList<>(tc);
        Double d = Double.parseDouble(ta.get(0).getSystemgrade2()) - (0.1* ms.getLongsentence()) - (0.4*ms.getUncertain());
        ta.get(0).setSystemgrade2(String.valueOf(d));
        tempGradeMongoRepository.save(ta.get(0));

        return ms;
    }
//    @RequestMapping("/pmapproved")
//    public @ResponseBody Project approvedPMProject(@RequestBody Project p)
//    {
//        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
//        ArrayList<Project> a = new ArrayList<>(c);
//        a.get(0).setStatus("pmapproved");
//        p.setStatus("pmapproved");
//        notificationManager.notifyByPMApproved(p.getProjectid());
//        projectMongoRepository.save(a.get(0));
//        return p;
//    }

//    @RequestMapping("/sbaapproved")
//    public @ResponseBody Project approvedSBAProject(@RequestBody Project p)
//    {
//        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
//        ArrayList<Project> a = new ArrayList<>(c);
//        a.get(0).setStatus("sbaapproved");
//        p.setStatus("sbaapproved");
//        notificationManager.notifyBySBASubmitted(p.getProjectid());
//        projectMongoRepository.save(a.get(0));
//        return p;
//    }




























//    @RequestMapping("/projectinfo")
//    public @ResponseBody Project viewProjectName(@RequestBody Project p)
//    {
//        Collection<Project> c = projectSearchRepository.projectDetailsId(p.getProjectid());
//        ArrayList<Project> a = new ArrayList<>(c);
//        p = a.get(0);
//        Collection<Employee> ec = employeeSearchRepository.searchUserId(a.get(0).getPm());
//        ArrayList<Employee> ea = new ArrayList<>(ec);
//        p.setPm(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        ec = employeeSearchRepository.searchUserId(a.get(0).getLba());
//        ea = new ArrayList<>(ec);
//        p.setLba(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        ec = employeeSearchRepository.searchUserId(a.get(0).getJba1());
//        ea = new ArrayList<>(ec);
//        p.setJba1(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        ec = employeeSearchRepository.searchUserId(a.get(0).getJba2());
//        ea = new ArrayList<>(ec);
//        p.setJba2(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        Collection<Customer> cc = customerSearchRepository.searchCusID(a.get(0).getCustomerid());
//        ArrayList<Customer> ca = new ArrayList<>(cc);
//        p.setCustomerid(ca.get(0).getName());
//        if(!a.get(0).getDev1().equals(""))
//        {
//            ec = employeeSearchRepository.searchUserId(a.get(0).getDev1());
//            ea = new ArrayList<>(ec);
//            p.setDev1(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        }
//        if(!a.get(0).getDev2().equals(""))
//        {
//            ec = employeeSearchRepository.searchUserId(a.get(0).getDev2());
//            ea = new ArrayList<>(ec);
//            p.setDev2(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        }
//        if(!a.get(0).getDev3().equals(""))
//        {
//            ec = employeeSearchRepository.searchUserId(a.get(0).getDev3());
//            ea = new ArrayList<>(ec);
//            p.setDev3(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        }
//        if(!a.get(0).getDev4().equals(""))
//        {
//            ec = employeeSearchRepository.searchUserId(a.get(0).getDev4());
//            ea = new ArrayList<>(ec);
//            p.setDev4(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        }
//        if(!a.get(0).getDev5().equals(""))
//        {
//            ec = employeeSearchRepository.searchUserId(a.get(0).getDev5());
//            ea = new ArrayList<>(ec);
//            p.setDev5(ea.get(0).getFname()+" "+ea.get(0).getLname());
//        }
//        return p;
//    }

















}

