package com.example.application.views;

import com.example.application.models.Kullanici;
import com.example.application.models.SystemUser;
import com.example.application.services.KullaniciService;
import com.example.application.services.SystemUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "kullanicilar", layout = MainView.class)
@PageTitle("Kullanıcı Tanımlama")
public class KullanicilarView extends VerticalLayout {

    private final KullaniciService kullaniciService;
    private final SystemUserService systemUserService;
    Grid<Kullanici> grid = new Grid<>(Kullanici.class);
    TextField txtFilter = new TextField();
    Binder<Kullanici> binder = new Binder<>();
    Dialog kullanıcıdialog = new Dialog();
    Long itemIdForEdition=0L;
    Long loggedInSystemUserId;


    public KullanicilarView(KullaniciService kullaniciService, SystemUserService systemUserService) {
        this.kullaniciService = kullaniciService;
        this.systemUserService = systemUserService;

        Button btnYeni = new Button("Yeni Kullanıcı Ekle", VaadinIcon.PLUS.create());

        txtFilter.setPlaceholder("");
        Button btnFilter = new Button("kullanıcı ara", VaadinIcon.SEARCH.create());
        btnFilter.addClickListener(buttonClickEvent -> {
            refreshData1(txtFilter.getValue());
        });

        HorizontalLayout filterGroup = new HorizontalLayout();
        filterGroup.add(txtFilter,btnFilter);
        txtFilter.setPlaceholder("aranacak kullanıcı adını girin");

        kullanıcıdialog.setModal(true);
        TextField txtAdSoyad = new TextField("Ad Soyad");
        TextField txtEmail = new TextField("E-mail");
        TextField txtFirmaAdi = new TextField("Firma Adı");
        TextField txtSifre = new TextField("Şifre");

        binder.bind(txtAdSoyad, Kullanici::getAdSoyad, Kullanici::setAdSoyad);
        binder.bind(txtEmail, Kullanici::getEmail, Kullanici::setEmail);
        binder.bind(txtFirmaAdi, Kullanici::getFirmaAdi, Kullanici::setFirmaAdi);
        binder.bind(txtSifre, Kullanici::getSifre, Kullanici::setSifre);

        FormLayout formLayout = new FormLayout();
        formLayout.add(txtAdSoyad, txtEmail, txtFirmaAdi, txtSifre);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        Button btnSave = new Button("Kaydet");
        Button btnCancel = new Button("Vazgeç");
        btnCancel.addClickListener(buttonClickEvent -> {
            kullanıcıdialog.close();
        });

        btnSave.addClickListener(buttonClickEvent -> {
            Kullanici kullanici = new Kullanici();
            try {
                binder.writeBean(kullanici);
            } catch (ValidationException e) {
                e.printStackTrace();
            }

            kullanici.setId(itemIdForEdition);


            SystemUser loggedInSystemUser=new SystemUser();
            loggedInSystemUser.setId(loggedInSystemUserId);
            kullanici.setSystemUser(loggedInSystemUser);
            loggedInSystemUser.setEmail(txtEmail.getValue());
            loggedInSystemUser.setPassword(txtSifre.getValue());
            systemUserService.save(loggedInSystemUser);

            kullaniciService.save(kullanici);
            refreshData1(txtFilter.getValue().toString());
            kullanıcıdialog.close();
        });

        horizontalLayout.add(btnCancel, btnSave);
        kullanıcıdialog.add(new H3("Yeni Kullanıcı"), formLayout, horizontalLayout);

        btnYeni.addClickListener(buttonClickEvent -> {
            itemIdForEdition=0L;
            binder.readBean(new Kullanici());
            kullanıcıdialog.open();
        });

        refreshData1(txtFilter.getValue().toString());
//refreshData();
        grid.removeColumnByKey("id");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setColumns("adSoyad", "email", "firmaAdi", "sifre");
        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("");

        add(new H2("KULLANICILAR"), btnYeni,filterGroup, grid);
    }

    private void refreshData() {
        List<Kullanici> kullaniciList = new ArrayList<>();
        kullaniciList.addAll(kullaniciService.getList());
        grid.setItems(kullaniciList);
    }

    private void refreshData1(String filter) {
        List<Kullanici> kullaniciList = new ArrayList<>();
        kullaniciList.addAll(kullaniciService.getList(filter));
        grid.setItems(kullaniciList);
    }

    private void refreshData(String filter) {
        List<Kullanici> kullaniciList = new ArrayList<>();
        kullaniciList.addAll(kullaniciService.getList(filter,loggedInSystemUserId));
        grid.setItems(kullaniciList);
    }


    private HorizontalLayout createRemoveButton(Grid<Kullanici> grid, Kullanici item) {
        @SuppressWarnings("unchecked")
        Button btnSil = new Button("Sil");
        btnSil.addClickListener(buttonClickEvent -> {
                kullaniciService.delete(item);
                refreshData1(txtFilter.getValue().toString());
        });

        Button btnGuncelle = new Button("Güncelle");
        btnGuncelle.addClickListener(buttonClickEvent -> {
            itemIdForEdition = item.getId();
            binder.readBean(item);
            kullanıcıdialog.open();

        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(btnGuncelle, btnSil);

        return horizontalLayout;
    }

}

