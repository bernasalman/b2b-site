package com.example.application.services;

import com.example.application.models.Kullanici;
import com.example.application.repostories.KullaniciRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    public KullaniciServiceImpl(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    public Set<Kullanici> getList() {
        Set<Kullanici> kullaniciSet =new HashSet<>();
        kullaniciRepository.findAll().iterator().forEachRemaining(kullaniciSet::add);
        return kullaniciSet;
    }

    @Override
    public Set<Kullanici> getList(String filter) {
        Set<Kullanici> kullaniciSet =new HashSet<>();
        kullaniciRepository.findByAdSoyadContaining(filter).iterator().forEachRemaining(kullaniciSet::add);
        return kullaniciSet;
    }

    @Override
    public Set<Kullanici> getList(String filter, Long systemUserId) {
        Set<Kullanici> kullaniciSet =new HashSet<>();
        kullaniciRepository.findByAdSoyadContainingAndSystemUserId(filter,systemUserId).iterator().forEachRemaining(kullaniciSet::add);
        return kullaniciSet;
    }

    @Override
    public Set<Kullanici> getList(Long id) {
        Set<Kullanici> kullaniciSet =new HashSet<>();
        kullaniciRepository.findByIdEquals(id).iterator().forEachRemaining(kullaniciSet::add);
        return kullaniciSet;
    }

    @Override
    public Kullanici save(Kullanici k) {
        return kullaniciRepository.save(k);
    }

    @Override
    public void delete(Kullanici k) {kullaniciRepository.delete(k); }
}



