package com.example.application.services;

import com.example.application.models.SystemUser;
import com.example.application.repostories.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepository systemUserRepository;

    public SystemUserServiceImpl(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    @Override
    public Set<SystemUser> getList() {
        Set<SystemUser> kullaniciSet =new HashSet<>();
        systemUserRepository.findAll().iterator().forEachRemaining(kullaniciSet::add);
        return kullaniciSet;
    }

    @Override
    public SystemUser login(String email, String password) {
    List<SystemUser> result =  systemUserRepository.findByEmailAndPassword(email,password);
       if (result.size()==0){
        return new SystemUser();
    }
        return result.get(0);
    }

    @Override
    public SystemUser save(SystemUser systemUser) {
        return systemUserRepository.save(systemUser);
    }
}
