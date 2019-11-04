package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Employee;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Notification;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.EmployeeMongoRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class Password {

    @Autowired
    EmployeeMongoRepository employeeRepository;

    //All activities related to the account passwords are found here

    public String generatePassword()
    {
        List rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1), new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1));

        PasswordGenerator generator = new PasswordGenerator();
        String newPassword = generator.generatePassword(8, rules);

        return newPassword;
    }

    public String encryptPassword(String password)
    {
        String encoded = BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());

        return  encoded;
    }

    public boolean decryptPassword(String password,String dbpassword)
    {
        boolean validity = BCrypt.checkpw(password,dbpassword);

        return validity;
    }

}
