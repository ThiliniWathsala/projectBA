package lk.ac.mrt.itfac.aurors.BA_Management_System.controller;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Chat;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Message;
import lk.ac.mrt.itfac.aurors.BA_Management_System.models.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.ChatSearchRepository;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.MessageMongoRepository;
import lk.ac.mrt.itfac.aurors.BA_Management_System.service.ChatService;
import lk.ac.mrt.itfac.aurors.BA_Management_System.service.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class WebSocketController {

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    MessageMongoRepository messageMongoRepository;

    @Autowired
    ChatSearchRepository chatSearchRepository;

    @Autowired
    ChatService chat;

    @MessageMapping("/hello")
    @SendTo("/topic/hi")
    public Message greeting(Message msg) throws Exception {
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
        return msg;
    }
}
