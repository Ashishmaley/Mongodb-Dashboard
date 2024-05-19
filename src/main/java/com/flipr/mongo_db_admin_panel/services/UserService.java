package com.flipr.mongo_db_admin_panel.services;

import com.flipr.mongo_db_admin_panel.modles.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {
    public User saveUser(User user, String url);

    void sendEmail(User user, String url) throws UnsupportedEncodingException;

    public boolean verifyAccount(String verificationCode);
}

