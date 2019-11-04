package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Calender;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.EmpCalender;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Employee;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Project;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.CalenderMongoRepository;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.CalenderSearchRepository;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.EmployeeSearchRepository;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.ProjectSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Service
public class CalenderService {

    @Autowired
    CalenderMongoRepository calenderMongoRepository;

    @Autowired
    CalenderSearchRepository calenderSearchRepository;

    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    NotificationManager notificationManager;

    public ArrayList<String> getPM(String empid)
    {
        ArrayList<String> pm = new ArrayList<String>();
        int count = 0;
        Collection<Project> pmc = projectSearchRepository.current();
        ArrayList<Project> pma = new ArrayList<>(pmc);
        Iterator<Project> pmi = pma.iterator();

        while(pmi.hasNext())
        {
            Project p = pmi.next();
            if(p.getJba1().equals(empid) || p.getJba2().equals(empid) || p.getLba().equals(empid))
            {
                count++;
                pm.add(p.getPm());
            }
        }
        if(count==0)
        {
            Collection<Project> pme = projectSearchRepository.ended();
            ArrayList<Project> pmea = new ArrayList<>(pme);
            Iterator<Project> pmei = pmea.iterator();

            while(pmei.hasNext())
            {
                Project p = pmei.next();
                if(p.getJba1().equals(empid) || p.getJba2().equals(empid) || p.getLba().equals(empid))
                {
                    count++;
                    pm.add(p.getPm());
                }
            }
        }
        if(count==0)
        {
            Collection<Employee> e = employeeSearchRepository.pmList();
            ArrayList<Employee> ea = new ArrayList<>(e);
            Iterator<Employee> ei = ea.iterator();

            while(ei.hasNext())
                pm.add(ei.next().getEmployeeid());
        }

        ArrayList<String> pmr = new ArrayList<String>();
//        pmr.add("Select PM");

        Iterator<String> ipm = pm.iterator();

        while(ipm.hasNext())
        {
            Collection<Employee> u = employeeSearchRepository.searchUserId(ipm.next());
            ArrayList<Employee> a = new ArrayList<>(u);
            pmr.add(a.get(0).getEmployeeid()+" - "+a.get(0).getFname()+" "+a.get(0).getLname());
        }

        return pmr;
    }

    public String getLeaveNo()
    {

        Iterable<Calender> i= calenderMongoRepository.findAll();
        Iterator<Calender> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            Calender c = i2.next();
            num = Long.parseLong(c.getLeaveid());
        }
        num++;
        return String.valueOf(num);
    }

    public ArrayList<EmpCalender> getLeaveList(String empid)
    {
        Collection<Calender> c = calenderSearchRepository.leaveList(empid);
        ArrayList<Calender> ac = new ArrayList<>(c);
        Iterator<Calender> ic = ac.iterator();

        ArrayList<EmpCalender> a = new ArrayList<EmpCalender>();

        while(ic.hasNext())
        {
            EmpCalender emp = new EmpCalender();
            Calender cal = ic.next();
            if(cal.getStatus().equals("0")) {
                Collection<Employee> e = employeeSearchRepository.searchUserId(cal.getBa());
                ArrayList<Employee> ae = new ArrayList<>(e);

                emp.setDate(cal.getDate());
                emp.setEmployeeid(ae.get(0).getEmployeeid());
                emp.setName(ae.get(0).getFname() + " " + ae.get(0).getLname());
                emp.setReason(cal.getReason());
                emp.setType(ae.get(0).getType());
                emp.setLeaveid(cal.getLeaveid());
                a.add(emp);
            }
        }
        return a;
    }

    public Calender pmApprove(String leaveid)
    {
        Collection<Calender> c = calenderSearchRepository.searchID(leaveid);
        ArrayList<Calender> ac = new ArrayList<>(c);
        Calender cal = new Calender();
        cal.setId(ac.get(0).getId());
        cal.setPm(ac.get(0).getPm());
        cal.setBa(ac.get(0).getBa());
        cal.setDate(ac.get(0).getDate());
        cal.setReason(ac.get(0).getReason());
        cal.setLeaveid(ac.get(0).getLeaveid());
        cal.setStatus("1");
        calenderMongoRepository.save(cal);
        notificationManager.notifyLeaveApproved(cal);
        return cal;
    }
}
