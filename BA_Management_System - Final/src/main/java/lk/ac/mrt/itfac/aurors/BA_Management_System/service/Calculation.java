package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Calculation {

    @Autowired
    ProjectSearchRepository projectSearchRepository;

    @Autowired
    TempGradeSearchRepository tempGradeSearchRepository;

    @Autowired
    SBALeaderBoardMongoRepository sbaLeaderBoardRepository;

    @Autowired
    SBALeaderBoardSearchRepository sbaLeaderBoardSearchRepository;

    @Autowired
    JBALeaderBoardSearchRepository jbaLeaderBoardSearchRepository;

    @Autowired
    JBALeaderBoardMongoRepository jbaLeaderBoardRepository;

    public void endProjectGrades(String projectid)
    {
        DecimalFormat df = new DecimalFormat("#.##");

        Collection<Project> u = projectSearchRepository.projectDetailsId(projectid);
        ArrayList<Project> newList = new ArrayList<>(u);
        String jba1 = newList.get(0).getJba1();
        String jba2 = newList.get(0).getJba2();
        String lba  = newList.get(0).getLba();

        Collection<TempGrade> u2 = tempGradeSearchRepository.gradesId(projectid);
        ArrayList<TempGrade> newList2 = new ArrayList<>(u2);

        Collection<SBALeaderboard> u3 = sbaLeaderBoardSearchRepository.gradesSBAId(lba);
        ArrayList<SBALeaderboard> newList3 = new ArrayList<>(u3);
        SBALeaderboard sbal = new SBALeaderboard();
        int count = Integer.parseInt(newList3.get(0).getProjectcount())+1;
        double qa = ((Double.parseDouble(newList3.get(0).getQagrade())*Double.parseDouble(newList3.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getQagrade()))/count;
        double dev =((Double.parseDouble(newList3.get(0).getDevgrade())*Double.parseDouble(newList3.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getDevgrade()))/count;
        double pm = ((Double.parseDouble(newList3.get(0).getPmgrade())*Double.parseDouble(newList3.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getPmgrade()))/count;
        double overall = ((pm*0.5)+(qa*0.3)+(dev*0.2))*20;
        sbal.setProjectcount(String.valueOf(count));
        sbal.setQagrade(df.format(qa));
        sbal.setPmgrade(df.format(pm));
        sbal.setDevgrade(df.format(dev));
        sbal.setOverall(df.format(overall));
        sbal.setEmployeeid(newList3.get(0).getEmployeeid());
        sbal.setId(newList3.get(0).getId());
        sbal.setName(newList3.get(0).getName());
        sbaLeaderBoardRepository.save(sbal);

        ArrayList<JBALeaderboard> newList4 = getJBAMarks(jba1);
        count = Integer.parseInt(newList4.get(0).getProjectcount())+1;
        double system = ((Double.parseDouble(newList4.get(0).getSystemgrade())*Double.parseDouble(newList4.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getSystemgrade1()))/count;
        double customer = ((Double.parseDouble(newList4.get(0).getCustomergrade())*Double.parseDouble(newList4.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getCustomergrade1()))/count;
        double sba = ((Double.parseDouble(newList4.get(0).getLbagrade())*Double.parseDouble(newList4.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getSbagrade1()))/count;
        updateJBAMarks(system,customer,sba,count,jba1,newList4);

        ArrayList<JBALeaderboard> newList5 = getJBAMarks(jba2);
        count = Integer.parseInt(newList5.get(0).getProjectcount())+1;
        system = ((Double.parseDouble(newList5.get(0).getSystemgrade())*Double.parseDouble(newList5.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getSystemgrade2()))/count;
        customer = ((Double.parseDouble(newList5.get(0).getCustomergrade())*Double.parseDouble(newList5.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getCustomergrade2()))/count;
        sba = ((Double.parseDouble(newList5.get(0).getLbagrade())*Double.parseDouble(newList5.get(0).getProjectcount()))+Double.parseDouble(newList2.get(0).getSbagrade2()))/count;
        updateJBAMarks(system,customer,sba,count,jba2,newList5);
    }

    public ArrayList<JBALeaderboard> getJBAMarks(String jbaid)
    {
        Collection<JBALeaderboard> u4 = jbaLeaderBoardSearchRepository.gradesJBAId(jbaid);
        ArrayList<JBALeaderboard> newList4 = new ArrayList<>(u4);
        return newList4;
    }

    public void updateJBAMarks(double system,double customer,double sba,int count,String id,ArrayList<JBALeaderboard> jbascore)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        double overall = ((system*0.3)+(sba*0.5)+(customer*0.2))*20;
        JBALeaderboard jbal = new JBALeaderboard();
        jbal.setOverall(df.format(overall));
        jbal.setSystemgrade(df.format(system));
        jbal.setCustomergrade(df.format(customer));
        jbal.setEmployeeid(id);
        jbal.setLbagrade(df.format(sba));
        jbal.setProjectcount(String.valueOf(count));
        jbal.setName(jbascore.get(0).getName());
        jbal.setId(jbascore.get(0).getId());
        jbaLeaderBoardRepository.save(jbal);
    }

    //sorts the SBA leader board

    public static Comparator<SBALeaderboard> SbaOverall = new Comparator<SBALeaderboard>() {

        public int compare(SBALeaderboard s1, SBALeaderboard s2) {

            double overall1 = Double.parseDouble(s1.getOverall());
            double overall2 = Double.parseDouble(s2.getOverall());

            /*For ascending order*/
//            return rollno1-rollno2;

            /*For descending order*/
            return Double.compare(overall2,overall1);
        }};

    public ArrayList<SBALeaderboard> sbaSort(ArrayList<SBALeaderboard> sba)
    {
        Collections.sort(sba, SbaOverall);
        return sba;
    }

    //sorts the JBA leader board

    public static Comparator<JBALeaderboard> JbaOverall = new Comparator<JBALeaderboard>() {

        public int compare(JBALeaderboard s1, JBALeaderboard s2) {

            double overall1 = Double.parseDouble(s1.getOverall());
            double overall2 = Double.parseDouble(s2.getOverall());

            /*For ascending order*/
//            return rollno1-rollno2;

            /*For descending order*/
            return Double.compare(overall2,overall1);
        }};

    public ArrayList<JBALeaderboard> jbaSort(ArrayList<JBALeaderboard> jba)
    {
        Collections.sort(jba,JbaOverall);
        return jba;
    }

    // sorts question list from latest to oldest

    public static Comparator<Question> QuestionOverall = new Comparator<Question>() {

        public int compare(Question s1, Question s2) {

            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm a").parse(s1.getLastupdate());
                Date date2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a").parse(s2.getLastupdate());
                if(date1.before(date2))
                    return 1;
                else
                    return -1;

            } catch (ParseException e) {
                e.printStackTrace();
            }


            /*For ascending order*/
//            return rollno1-rollno2;

            /*For descending order*/
//            return Double.compare(overall2,overall1);
                return 0;
        }};

    public ArrayList<Question> questionSort(ArrayList<Question> question)
    {
        Collections.sort(question,QuestionOverall);
        return question;
    }
}
