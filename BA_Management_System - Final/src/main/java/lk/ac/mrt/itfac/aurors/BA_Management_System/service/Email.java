package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class Email {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    SRSSearchRepository srsSearchRepository;

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    Section1SearchRepository section1SearchRepository;

    @Autowired
    Section2SearchRepository section2SearchRepository;

    @Autowired
    CustomerSearchRepository customerSearchRepository;

    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    IntroductionSearchRepository introductionSearchRepository;

    @Autowired
    OverallSearchRepository overallSearchRepository;

    @Autowired
    InterfaceDesignSearchRepository interfaceDesignSearchRepository;

    @Autowired
    SystemFeaturesSearchRepository systemFeaturesSearchRepository;

    @Autowired
    NonFunctionalSearchRepository nonFunctionalSearchRepository;

    public void forgotPasswordEmail(String password,String email,String user)
    {
        String text = "Greetings :)\nYou have reset your password. Please use the below password with your current email\n   New password:";

        String subject = "Password Reset of Existing User";

        try {
            sendResetPasswordEmail(password,email,text,subject,user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void newUserPasswordEmail(String password,String email,String user)
    {
        String text = "Greetings :)\nYour account have been created. Please use the below password with your current email\n   New password:";

        String subject = "New Account Created for New User";

        try {
            sendNewPasswordEmail(password,email,text,subject,user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void sendNewPasswordEmail(String password,String email,String text,String subject,String user) throws javax.mail.MessagingException
    {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("empname", user);
        model.put("password", password);

        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("new-account-template", context);

        try {
            helper.setTo(email);
            helper.setText(html,true);
            helper.setSubject(subject);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }

    public void sendResetPasswordEmail(String password,String email,String text,String subject,String user) throws javax.mail.MessagingException
    {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("empname", user);
        model.put("password", password);

        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("password-reset-template", context);

        try {
            helper.setTo(email);
            helper.setText(html,true);
            helper.setSubject(subject);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }

    public void SRStoCustomerEmail (Project p)
    {
        String subject = "Software Requirement Specification Customer Copy";

        Collection<Project> pc = projectSearchRepository.projectDetailsId(p.getProjectid());
        ArrayList<Project> pa = new ArrayList<>(pc);
        Collection<SRS> sc = srsSearchRepository.getFromProjectid(p.getProjectid());
        ArrayList<SRS> sa = new ArrayList<>(sc);
        Collection<Introdution> ic = introductionSearchRepository.getFromSRSId(sa.get(0).getSRSid());
        ArrayList<Introdution> ia = new ArrayList<>(ic);
        Collection<InterfaceDesign> inc = interfaceDesignSearchRepository.getFromSRSId(sa.get(0).getSRSid());
        ArrayList<InterfaceDesign> ina = new ArrayList<>(inc);
        Collection<Overall> oc = overallSearchRepository.getFromSRSId(sa.get(0).getSRSid());
        ArrayList<Overall> oa = new ArrayList<>(oc);
        Collection<NonFunctional> nc = nonFunctionalSearchRepository.getFromSRSId(sa.get(0).getSRSid());
        ArrayList<NonFunctional> na = new ArrayList<>(nc);
        Collection<SystemFeatures> sfc = systemFeaturesSearchRepository.getFromSRSId(sa.get(0).getSRSid());
        ArrayList<SystemFeatures> sfa = new ArrayList<>(sfc);
        Collection<Customer> cc = customerSearchRepository.searchCusID(pa.get(0).getCustomerid());
        ArrayList<Customer> ca = new ArrayList<>(cc);
        Collection<Employee> ec = employeeSearchRepository.searchUserId(pa.get(0).getPm());
        ArrayList<Employee> ea = new ArrayList<>(ec);

//        String text = "Customer Name                 : "+ ca.get(0).getName() + "\n" +
//                      "Customer Contact              : "+ ca.get(0).getContact1() + "\n" +
//                      "Customer Email                : "+ ca.get(0).getEmail() + "\n\n" +
//                      "Project ID                    : "+ pa.get(0).getProjectid() +"\n" +
//                      "Project Name                  : "+ pa.get(0).getName() + "\n" +
//                      "Project Start Date            : "+ pa.get(0).getStartdate() + "\n\n" +
//                      "Project Manager Name          : "+ ea.get(0).getFname()+" "+ea.get(0).getLname() + "\n" +
//                      "Project Manager Contact       : "+ ea.get(0).getContact1() + "\n" +
//                      "Project Manager Email         : "+ ea.get(0).getEmail() + "\n\n" +
//                      "Project Objective             : "+ sec1a.get(0).getObjective() + "\n" +
//                      "Users                         : "+ sec1a.get(0).getUser() + "\n" +
//                      "Inputs                        : "+ sec1a.get(0).getInput() + "\n" +
//                      "Outputs                       : "+ sec1a.get(0).getOutput() + "\n\n" +
//                      "User Stories                  : "+ sec1a.get(0).getUserstory() + "\n\n" +
//                      "System Specifications         : "+ sec2a.get(0).getSystemspecification() + "\n" +
//                      "Network Requirements          : "+ sec2a.get(0).getNetworkrequirement() + "\n" +
//                      "Technical Requirements        : "+ sec2a.get(0).getTechnicalrequirement() + "\n" +
//                      "Security Level Specifications : "+ sec2a.get(0).getSecuritylevelspecification() + "\n" +
//                      "Additional Notes              : "+ sec2a.get(0).getAdditionalnote() + "\n" +
//                      "Please confirm that the above Software Requirement Specification is according to the specifications that were" +
//                      "requested by you from our Business Analysts.\n"+
//                      "Please rate the service offered by our business analysts to you by sending us a number from below along with you confirmation. \n"+
//                      "1 - Excellent \n2 - Good \n3 - Average \n4 - Poor \n5 - Very Poor\n\n\n"+
//                      "Thanks & Regards";

        try {
            sendSRSEmail(ca.get(0).getEmail(),subject,pa,ca,ea,sfa,na,oa,ina,ia);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendSRSEmail(String email,String subject,ArrayList<Project> pa,ArrayList<Customer> ca,ArrayList<Employee> ea,ArrayList<SystemFeatures> sfa,ArrayList<NonFunctional> na,ArrayList<Overall> oa,ArrayList<InterfaceDesign> ina,ArrayList<Introdution> ia) throws javax.mail.MessagingException
    {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("customername",ca.get(0).getName());
        model.put("customercontact",ca.get(0).getContact1());
        model.put("customeremail",ca.get(0).getEmail());
        model.put("pmname",ea.get(0).getFname()+" "+ea.get(0).getLname());
        model.put("pmcontact",ea.get(0).getContact1());
        model.put("pmemail",ea.get(0).getEmail());
        model.put("projectid",pa.get(0).getProjectid());
        model.put("projectname",pa.get(0).getName());
        model.put("projectdate",pa.get(0).getStartdate());
        model.put("purpose",ia.get(0).getPurpose());
        model.put("ispecification",ia.get(0).getSpecifications());
        model.put("suggestion",ia.get(0).getSuggestions());
        model.put("audience",ia.get(0).getAudience());
        model.put("benefits",ia.get(0).getBenefits());
        model.put("goal",ia.get(0).getGoal());
        model.put("objectives",ia.get(0).getObjectives());
        model.put("ireference",ia.get(0).getReference());
        model.put("type",oa.get(0).getType());
        model.put("odescription",oa.get(0).getDescription());
        model.put("ospecification",oa.get(0).getSpecification());
        model.put("usercase",oa.get(0).getUsercase());
        model.put("software",oa.get(0).getSoftware());
        model.put("hardware",oa.get(0).getHardware());
        model.put("constraints",oa.get(0).getConstraints());
        model.put("documents",oa.get(0).getDocuments());
        model.put("assumptions",oa.get(0).getAssumptions());
        model.put("display",ina.get(0).getDisplay());
        model.put("dspecifications",ina.get(0).getSpecifications());
        model.put("connections",ina.get(0).getConnections());
        model.put("network",ina.get(0).getNetwork());
        model.put("encryption",ina.get(0).getEncryption());
        model.put("performance",na.get(0).getPerformance());
        model.put("damages",na.get(0).getDamages());
        model.put("safety",na.get(0).getSafety());
        model.put("security",na.get(0).getSecurity());
        model.put("attribute",na.get(0).getAttribute());
        model.put("ndescription",na.get(0).getDescription());
        model.put("nreference",na.get(0).getReference());
        model.put("features",sfa.get(0).getFeatureset());

//        Map<String, Feature> model2 = new HashMap<String, Feature>();
//        model2.put("features",sfa.get(0).getFeatureset());

        Context context = new Context();
        context.setVariables(model);
//        context.setVariables(model2);
        String html = templateEngine.process("srs-template", context);

        try {
            helper.setTo(email);
            helper.setText(html,true);
            helper.setSubject(subject);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }

    public void introMail(Project p) throws MessagingException {
        Collection<Employee> ce = employeeSearchRepository.searchUserId(p.getPm());
        ArrayList<Employee> ae = new ArrayList<>(ce);
        Collection<Customer> cc = customerSearchRepository.searchCusID(p.getCustomerid());
        ArrayList<Customer> ac = new ArrayList<>(cc);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("customername",ac.get(0).getName());
        model.put("pmname",ae.get(0).getFname()+" "+ae.get(0).getLname());
        model.put("pmcontact",ae.get(0).getContact1());
        model.put("pmemail",ae.get(0).getEmail());

        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("project-start-template", context);

        try {
            helper.setTo(ac.get(0).getEmail());
            helper.setText(html,true);
            helper.setSubject("Project Assistance");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }
}
