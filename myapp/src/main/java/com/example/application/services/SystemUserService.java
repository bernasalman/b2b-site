package com.example.application.services;

import com.example.application.models.SystemUser;

import java.util.Set;

public interface SystemUserService {
    Set<SystemUser> getList();
    SystemUser login(String email, String password);
    SystemUser save(SystemUser systemUser);

}
