package com.flipr.mongo_db_admin_panel.services;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.flipr.mongo_db_admin_panel.models.User;
import com.flipr.mongo_db_admin_panel.repositories.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Override
    public User saveUser(User user, String url) {
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user.setRole("ROLE_ADMIN");
        user.setEnable(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        User newUser = userRepo.save(user);
        if (newUser != null) {
            sendEmail(newUser, url);
        }
        return newUser;
    }

    @Override
    public void sendEmail(User user, String url) {
        String from = "dangiashishmaley17@gmail.com";
        String to = user.getUserName();
        String subject = "Verify your account!!";
        String content = "Dear user,<br>" + "Please click the link below to Verify your account"
                + "<h3><a href = \"[[URL]]\" target = \"_self\"> VERIFY</a></h3>" + "Thank You,";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(from, "Mongodb DashBoard");
            helper.setTo(to);
            helper.setSubject(subject);
            String siteUrl = url + "/verify?code=" + user.getVerificationCode();
            content = content.replace("[[URL]]", siteUrl);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (UnsupportedEncodingException e) {

        } catch (Exception exception) {
            // Handle other exceptions
            exception.printStackTrace();
        }
    }

    @Override
    public boolean verifyAccount(String verificationCode) {
        User user = userRepo.findByverificationCode(verificationCode);
        if (user == null)
            return false;
        else {
            user.setEnable(true);
            userRepo.save(user);
            return true;
        }
    }
}

