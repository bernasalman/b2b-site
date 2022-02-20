package com.example.application.bootstrap;

import com.example.application.models.*;
import com.example.application.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Data implements CommandLineRunner {

    private final KullaniciService kullaniciService;
    private final SystemUserService systemUserService;
    private final KategoriService kategoriService;
    private final StokService stokService;
    private final SiparisService siparisService;

    public Data(KullaniciService kullaniciService, SystemUserService systemUserService, KategoriService kategoriService, StokService stokService, SiparisService siparisService) {
        this.kullaniciService = kullaniciService;
        this.systemUserService = systemUserService;
        this.kategoriService = kategoriService;
        this.stokService = stokService;
        this.siparisService = siparisService;
    }

    @Override
    public void run(String... args) throws Exception {

        SystemUser systemUser=new SystemUser();
        systemUser.setEmail("berna@trakya");
        systemUser.setPassword("1");
        systemUserService.save(systemUser);

        SystemUser systemUser1=new SystemUser();
        systemUser1.setEmail("ahmet@gmail");
        systemUser1.setPassword("12");
        systemUserService.save(systemUser1);

        SystemUser systemUser2=new SystemUser();
        systemUser2.setEmail("ayse@gmail");
        systemUser2.setPassword("123");
        systemUserService.save(systemUser2);

        SystemUser systemUser3=new SystemUser();
        systemUser3.setEmail("ayse@gmail");
        systemUser3.setPassword("123");
        systemUserService.save(systemUser3);

        Kullanici kullanici=new Kullanici();
        kullanici.setAdSoyad("Berna Salman");
        kullanici.setFirmaAdi("Arçelik");
        kullanici.setEmail("berna@trakya");
        kullanici.setSifre("1");
        kullanici.setSystemUser(systemUser);
        kullaniciService.save(kullanici);

        Kullanici kullanici1=new Kullanici();
        kullanici1.setAdSoyad("Ahmet Yılmaz");
        kullanici1.setFirmaAdi("Beko");
        kullanici1.setEmail("ahmet@gmail");
        kullanici1.setSifre("12");
        kullanici1.setSystemUser(systemUser1);
        kullaniciService.save(kullanici1);

        Kullanici kullanici2=new Kullanici();
        kullanici2.setAdSoyad("Ayşe Yılmaz");
        kullanici2.setFirmaAdi("Bosch");
        kullanici2.setEmail("ayse@gmail");
        kullanici2.setSifre("123");
        kullanici2.setSystemUser(systemUser2);
        kullaniciService.save(kullanici2);

        Kullanici kullanici3=new Kullanici();
        kullanici3.setAdSoyad("Elif Demir");
        kullanici3.setFirmaAdi("Grundig");
        kullanici3.setEmail("elif@gmail");
        kullanici3.setSifre("1234");
        kullanici3.setSystemUser(systemUser3);
        kullaniciService.save(kullanici3);

        Kategori buzdolabı= new Kategori();
        buzdolabı.setKategori("Buzdolabı");
        kategoriService.save(buzdolabı);

        Kategori camasir= new Kategori();
        camasir.setKategori("Çamaşır Makinesi");
        kategoriService.save(camasir);

        Kategori bulasik= new Kategori();
        bulasik.setKategori("Bulaşık Makinesi");
        kategoriService.save(bulasik);

        Kategori utu= new Kategori();
        utu.setKategori("Ütü");
        kategoriService.save(utu);

        Stok stok1= new Stok();
        stok1.setStokKodu("BZ101");
        stok1.setKategori(buzdolabı);
        stok1.setAciklama("no-frost buzdolabı");
        stok1.setAdet(125);
        stokService.save(stok1);

        Stok stok2= new Stok();
        stok2.setStokKodu("ÇM102");
        stok2.setKategori(camasir);
        stok2.setAciklama("9kg kapasite çamaşır makinesi");
        stok2.setAdet(140);
        stokService.save(stok2);

        Stok stok3= new Stok();
        stok3.setStokKodu("ÇM103");
        stok3.setKategori(camasir);
        stok3.setAciklama("max. 1200 devir çamaşır makinesi");
        stok3.setAdet(151);
        stokService.save(stok3);

        Stok stok4= new Stok();
        stok4.setStokKodu("BZ1004");
        stok4.setKategori(buzdolabı);
        stok4.setAciklama("gardrop tipi buzdolabı");
        stok4.setAdet(126);
        stokService.save(stok4);

        Stok stok5= new Stok();
        stok5.setStokKodu("BL5551");
        stok5.setKategori(bulasik);
        stok5.setAciklama("bardak korumalı bulaşık makinesi");
        stok5.setAdet(113);
        stokService.save(stok5);

        Stok stok6= new Stok();
        stok6.setStokKodu("BL534");
        stok6.setKategori(bulasik);
        stok6.setAciklama("antrasit rengi bulaşık makinesi");
        stok6.setAdet(110);
        stokService.save(stok6);

        Stok stok7= new Stok();
        stok7.setStokKodu("U302");
        stok7.setKategori(utu);
        stok7.setAciklama("buhar kazanlı ütü");
        stok7.setAdet(36);
        stokService.save(stok7);

        Stok stok8= new Stok();
        stok8.setStokKodu("U103");
        stok8.setKategori(utu);
        stok8.setAciklama("buharlı ütü");
        stok8.setAdet(101);
        stokService.save(stok8);

        Siparis siparis = new  Siparis();
        siparis.setStok(stok1);
        siparis.setKategori(buzdolabı);
        siparis.setKullanici(kullanici);
        siparis.setTarih(LocalDate.ofEpochDay((07/07/2021)));
        siparis.setAdet(5);
        siparisService.save(siparis);

        Siparis siparis1 = new  Siparis();
        siparis1.setStok(stok2);
        siparis1.setKategori(camasir);
        siparis1.setKullanici(kullanici2);
        siparis1.setTarih(LocalDate.of(2021,6,9));
        siparis1.setAdet(7);
        siparisService.save(siparis1);

        Siparis siparis2 = new  Siparis();
        siparis2.setStok(stok3);
        siparis2.setKategori(camasir);
        siparis2.setKullanici(kullanici2);
        siparis2.setTarih(LocalDate.of(2021,7,12));
        siparis2.setAdet(11);
        siparisService.save(siparis2);

        Siparis siparis3 = new  Siparis();
        siparis3.setStok(stok5);
        siparis3.setKategori(bulasik);
        siparis3.setKullanici(kullanici2);
        siparis3.setTarih(LocalDate.of(2021,7,25));
        siparis3.setAdet(16);
        siparisService.save(siparis3);

        Siparis siparis4 = new  Siparis();
        siparis4.setStok(stok4);
        siparis4.setKategori(buzdolabı);
        siparis4.setKullanici(kullanici1);
        siparis4.setTarih(LocalDate.of(2021,7,13));
        siparis4.setAdet(3);
        siparisService.save(siparis4);

        Siparis siparis5 = new  Siparis();
        siparis5.setStok(stok7);
        siparis5.setKategori(utu);
        siparis5.setKullanici(kullanici3);
        siparis5.setTarih(LocalDate.of(2021,6,30));
        siparis5.setAdet(8);
        siparisService.save(siparis5);

        Siparis siparis6 = new  Siparis();
        siparis6.setStok(stok8);
        siparis6.setKategori(utu);
        siparis6.setKullanici(kullanici1);
        siparis6.setTarih(LocalDate.of(2021,5,23));
        siparis6.setAdet(14);
        siparisService.save(siparis6);
    }


}
