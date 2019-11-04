package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Chat;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Message;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    @Autowired
    ChatMongoRepository chatRepository;

    @Autowired
    MessageMongoRepository messageMongoRepository;

    @Autowired
    MessageSearchRepository messageSearchRepository;

    public String getMessageNo()
    {

        Iterable<Message> i= messageMongoRepository.findAll();
        Iterator<Message> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            Message m = i2.next();
            num = Long.parseLong(m.getMessageid());
        }
        num++;
        return String.valueOf(num);
    }

    public String getChatNo()
    {

        Iterable<Chat> i= chatRepository.findAll();
        Iterator<Chat> i2 = i.iterator();
        long num = 0;

        while (i2.hasNext())
        {
            Chat c = i2.next();
            num = Long.parseLong(c.getChatid());
        }
        num++;
        return String.valueOf(num);
    }

    public String  findChat(String empid1,String empid2)
    {
        Iterable<Chat> c = chatRepository.findAll();
        Iterator<Chat> i = c.iterator();

//        String sub = name.substring(0,8);

        String chatID = "null";
        System.out.println(empid1);
        System.out.println(empid2);
        while (i.hasNext())
        {
            Chat ch = i.next();
            if((ch.getEmployee1().equals(empid1) && ch.getEmployee2().equals(empid2)) || (ch.getEmployee2().equals(empid1) && ch.getEmployee1().equals(empid2)))
            {
                chatID = ch.getChatid();
            }
        }
        System.out.println(chatID);
        return chatID;
    }

    public ArrayList<Message> getMessageList(String chatid)
    {
        Collection<Message> m = messageSearchRepository.messageList(chatid);
        ArrayList<Message> a = new ArrayList<>(m);

        return a;
    }

//    public static Comparator<Message> msgNo = new Comparator<Message>() {
//
//        public int compare(Message s1, Message s2) {
//
//            int no1 = Integer.parseInt(s1.getMessageid());
//            int no2 = Integer.parseInt(s2.getMessageid());
//
//            /*For ascending order*/
////            return rollno1-rollno2;
//
//            /*For descending order*/
//            return no2-no1;
//        }};
//
//    public ArrayList<Message> msgSort(ArrayList<Message> notify)
//    {
//        Collections.sort(notify, msgNo);
//        return notify;
//    }
}
