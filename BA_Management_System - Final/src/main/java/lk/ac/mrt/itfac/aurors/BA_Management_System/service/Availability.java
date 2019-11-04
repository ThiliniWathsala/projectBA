package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Calender;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Employee;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.JBALeaderboard;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Project;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.CalenderSearchRepository;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.EmployeeSearchRepository;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.ProjectSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Service
public class Availability {

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    CalenderSearchRepository calenderSearchRepository;

    @Autowired
    EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    FileProcessor fileProcessor;

    public ArrayList<String> availableJBA(String startdate, String enddate) throws ParseException {
        Collection<Employee> u = employeeSearchRepository.jbaList();
        return  getList(startdate,enddate,u);
    }

    public ArrayList<String> availableSBA(String startdate, String enddate) throws ParseException {
        Collection<Employee> u = employeeSearchRepository.sbaList();
        return  getList(startdate,enddate,u);
    }

    public ArrayList<String> availableDev(String startdate,String enddate) throws ParseException {
        Collection<Employee> u = employeeSearchRepository.devList();
        return  getList(startdate,enddate,u);
    }

    public ArrayList<String> getList(String startdate, String enddate, Collection<Employee> u) throws ParseException {
        int countProject = 0, countDate = 0;
        ArrayList<String> nameSet = new ArrayList<String>();

        ArrayList<Employee> newList = new ArrayList<>(u);
        Iterator<Employee> i = newList.iterator();
        String empid;
        Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startdate);
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse(enddate);

        Collection<Project> p = projectSearchRepository.current();
        ArrayList<Project> newListp = new ArrayList<>(p);
        Iterator<Project> pi = newListp.iterator();

        while(i.hasNext()) {
            Employee e = i.next();
            empid = e.getEmployeeid();

            Collection<Calender> u4 = calenderSearchRepository.jbaList(empid);
            ArrayList<Calender> newList4 = new ArrayList<>(u4);

            Iterator<Calender> i2 = newList4.iterator();

            while (i2.hasNext()) {
                Calender c = i2.next();
                Date check = new SimpleDateFormat("dd/MM/yyyy").parse(c.getDate());

                if (check.after(start) && check.before(end) && c.getStatus().equals("1"))
                    countDate++;
            }

            while (pi.hasNext())
            {
                Project pro = pi.next();

                if(empid.equals(pro.getJba1()) || empid.equals(pro.getJba2()) || empid.equals(pro.getLba()) || empid.equals(pro.getDev1()) || empid.equals(pro.getDev2()) || empid.equals(pro.getDev3()) || empid.equals(pro.getDev4()) || empid.equals(pro.getDev5()))
                    countProject++;
            }

            if((e.getType().equals("JBA") && countProject<4 && countDate<6) || (e.getType().equals("SBA") && countProject<6 && countDate<8) || (e.getType().equals("Dev") && countProject<2 && countDate<4))
            {
                String s = e.getEmployeeid() + " - " + e.getFname() + " " + e.getLname();
                nameSet.add(s);
            }
            countDate = 0;
            countProject = 0;
        }
        return nameSet;
    }

    public ArrayList<String> availableChangeJBA(String startdate, String enddate, String empid) throws ParseException {
        Collection<Employee> u = employeeSearchRepository.jbaList();
        ArrayList<String> nameSet = new ArrayList<>();
        nameSet.add(empid+" - "+fileProcessor.getEmpName(empid));
        return  getChangeList(startdate,enddate,u,empid,nameSet);
    }

    public ArrayList<String> availableChangeSBA(String startdate, String enddate, String empid) throws ParseException {
        Collection<Employee> u = employeeSearchRepository.sbaList();
        ArrayList<String> nameSet = new ArrayList<>();
        nameSet.add(empid+" - "+fileProcessor.getEmpName(empid));
        return  getChangeList(startdate,enddate,u,empid,nameSet);
    }

    public ArrayList<String> availableChangeDev(String startdate,String enddate, String empid) throws ParseException {
        Collection<Employee> u = employeeSearchRepository.devList();
        ArrayList<String> nameSet = new ArrayList<>();
        if(empid.equals(""))
            nameSet.add(null);
        else
            nameSet.add(empid+" - "+fileProcessor.getEmpName(empid));
        return  getChangeList(startdate,enddate,u,empid,nameSet);
    }

    public ArrayList<String> getChangeList(String startdate, String enddate, Collection<Employee> u, String emp, ArrayList<String> nameSet) throws ParseException {
        int countProject = 0, countDate = 0;

        ArrayList<Employee> newList = new ArrayList<>(u);
        Iterator<Employee> i = newList.iterator();
        String empid;
        Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startdate);
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse(enddate);

        Collection<Project> p = projectSearchRepository.current();
        ArrayList<Project> newListp = new ArrayList<>(p);
        Iterator<Project> pi = newListp.iterator();

        while(i.hasNext()) {
            Employee e = i.next();
            empid = e.getEmployeeid();

            Collection<Calender> u4 = calenderSearchRepository.jbaList(empid);
            ArrayList<Calender> newList4 = new ArrayList<>(u4);

            Iterator<Calender> i2 = newList4.iterator();

            while (i2.hasNext()) {
                Calender c = i2.next();
                Date check = new SimpleDateFormat("dd/MM/yyyy").parse(c.getDate());

                if (check.after(start) && check.before(end) && c.getStatus().equals("1"))
                    countDate++;
            }

            while (pi.hasNext())
            {
                Project pro = pi.next();

                if(empid.equals(pro.getJba1()) || empid.equals(pro.getJba2()) || empid.equals(pro.getLba()) || empid.equals(pro.getDev1()) || empid.equals(pro.getDev2()) || empid.equals(pro.getDev3()) || empid.equals(pro.getDev4()) || empid.equals(pro.getDev5()))
                    countProject++;
            }

            if((e.getType().equals("JBA") && countProject<4 && countDate<6 && !emp.equals(e.getEmployeeid())) || (e.getType().equals("SBA") && countProject<6 && countDate<8 && !emp.equals(e.getEmployeeid())) || (e.getType().equals("Dev") && countProject<2 && countDate<4 && !emp.equals(e.getEmployeeid())))
            {
                String s = e.getEmployeeid() + " - " + e.getFname() + " " + e.getLname();
                nameSet.add(s);
            }
            countDate = 0;
            countProject = 0;
        }
        return nameSet;
    }
}
