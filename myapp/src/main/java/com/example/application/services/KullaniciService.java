package com.example.application.services;

import com.example.application.models.Kullanici;

import java.util.Set;

public interface KullaniciService {
    Set<Kullanici> getList();
    Set<Kullanici> getList(String filter);
    Set<Kullanici> getList(String filter,Long systemUserId);
    Set<Kullanici> getList(Long id);
    Kullanici save (Kullanici k);
    void delete(Kullanici k);
}
