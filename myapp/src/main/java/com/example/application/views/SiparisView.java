package com.example.application.views;

import com.example.application.models.*;
import com.example.application.services.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "siparis")
@PageTitle("Sipariş Ekle")
public class SiparisView extends VerticalLayout {
    Long loggedInSystemUserId;

    Button btnLogOut = new Button("Çıkış", VaadinIcon.CLOSE_CIRCLE.create());
    Icon logo = new Icon(VaadinIcon.HANDSHAKE);
    H1 isim = new H1("B2B-ŞİPARİŞ");

    private VerticalLayout createTopBar(HorizontalLayout header, Button btnLogOut) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList();
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.END,btnLogOut);
        layout.add(header,btnLogOut);
        return layout;
    }

    private HorizontalLayout createHeader(Icon logo, H1 isim) {
        HorizontalLayout header = new HorizontalLayout();
        header.setClassName("topmenu-header");
        header.setPadding(false);
        header.setSpacing(false);
        header.setWidthFull();
        header.setHeight("10px");
        header.setAlignItems(FlexComponent.Alignment.AUTO);
        logo.setSize("70px");
        logo.setColor("red");
        header.add(logo,isim);

        return header;
    }
    private final StokService stokService;
    private final SiparisService siparisService;
    private final KategoriService kategoriService;
    private final SystemUserService systemUserService;
    private final KullaniciService kullaniciService;
    Grid<Siparis> siparisGrid = new Grid<>(Siparis.class);
    Grid<Stok> stokGrid = new Grid<>(Stok.class);
    TextField txtFilter = new TextField();
    Binder<Siparis> binder = new Binder<>();
    Dialog siparisdialog = new Dialog();
    Long itemIdForEdition = 0L;
    //Long loggedInSystemUserId ;
    Button btnYeni = new Button("Sipariş Ekle", VaadinIcon.PLUS.create());
    ComboBox<Stok> cmbstok = new ComboBox<>("Stoklar");

    TextField siparisAra = new TextField();

    public SiparisView(StokService stokService, SiparisService siparisService, KategoriService kategoriService, SystemUserService systemUserService, KullaniciService kullaniciService) {
        this.stokService = stokService;
        this.siparisService = siparisService;
        this.kategoriService = kategoriService;
        this.systemUserService = systemUserService;
        this.kullaniciService = kullaniciService;

        if (VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId")==null){
            UI.getCurrent().getPage().setLocation("/login");
        }else {

            System.out.println("Logedin User ID");
            System.out.println(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
            loggedInSystemUserId=Long.valueOf(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
        }
        HorizontalLayout header = createHeader(logo,isim);
        add(createTopBar(header, btnLogOut));

        btnLogOut.addClickListener(buttonClickEvent -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().setLocation("/login");
        });

        txtFilter.setPlaceholder("");
        ComboBox<Kategori> stokKategoriAra = new ComboBox<>();
        stokKategoriAra.setPlaceholder("aranacak kategori türünü seçin");
        stokKategoriAra.setItems(kategoriService.getList());
        stokKategoriAra.setItemLabelGenerator(Kategori::getKategori);
        ComboBox<Kategori> siparisKategoriAra = new ComboBox<>();
        siparisKategoriAra.setPlaceholder("aranacak kategori türünü seçin");
        siparisKategoriAra.setItems(kategoriService.getList());
        siparisKategoriAra.setItemLabelGenerator(Kategori::getKategori);
        TextField aciklamaAra = new TextField();
        aciklamaAra.setPlaceholder("aranacak özellik");
        siparisAra.setPlaceholder("aranacak sipariş kodu");

        Button btnFilterStok = new Button("katagori ara", VaadinIcon.SEARCH.create());
        btnFilterStok.addClickListener(buttonClickEvent -> {
            refreshStokKategori(stokKategoriAra.getValue());
        });
        HorizontalLayout filterGroupStok = new HorizontalLayout();
        filterGroupStok.add(stokKategoriAra, btnFilterStok);

        Button btnFilterAciklama = new Button("özellik ara", VaadinIcon.SEARCH.create());
        btnFilterAciklama.addClickListener(buttonClickEvent -> {
            refreshStokAciklama(aciklamaAra.getValue());
        });
        HorizontalLayout filterGroupAciklama = new HorizontalLayout();
        filterGroupStok.add(aciklamaAra, btnFilterAciklama);

        Button btnFilterSiparis = new Button("sipariş ara", VaadinIcon.SEARCH.create());
        btnFilterSiparis.addClickListener(buttonClickEvent -> {
            refreshSiparis(siparisAra.getValue());
        });
        HorizontalLayout filterGroupSiparis = new HorizontalLayout();
        filterGroupSiparis.add(siparisAra, btnFilterSiparis);

        refreshData1();
        stokGrid.removeColumnByKey("id");
        stokGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        stokGrid.setColumns("stokKodu","aciklama","adet");
        stokGrid.addColumn(kategori->kategori.getKategori().kategori).setHeader("Kategori");
        stokGrid.addComponentColumn(this::createSiparis);

        siparisdialog.setModal(true);
        IntegerField adet = new IntegerField("Adet");
        DatePicker valueTarih = new DatePicker("Tarih");
        LocalDate now = LocalDate.now();
        valueTarih.setValue(now);
        cmbstok.setItems(stokService.getList());
        cmbstok.setItemLabelGenerator(stokKodu-> String.valueOf(stokKodu.getStokKodu()));

        binder.bind(adet, Siparis::getAdet, Siparis::setAdet);
        binder.bind(valueTarih, Siparis::getTarih, Siparis::setTarih);

        FormLayout formLayout = new FormLayout();
        formLayout.add(cmbstok,adet, valueTarih);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        Button btnSave = new Button("Kaydet");
        Button btnCancel = new Button("Vazgeç");
        btnCancel.addClickListener(buttonClickEvent -> {
            siparisdialog.close();
        });

        btnSave.addClickListener(buttonClickEvent -> {
            Siparis siparis = new Siparis();
            siparis.setAdet(adet.getValue());
            siparis.setTarih(valueTarih.getValue());
            siparis.setStok((Stok) cmbstok.getValue());
            siparis.setKategori(cmbstok.getValue().kategori);


            Kullanici loggedInSystemUser= new Kullanici();
            loggedInSystemUser.setId(loggedInSystemUserId);
            siparis.setKullanici(loggedInSystemUser);

            siparis.setId(itemIdForEdition);
            siparisService.save(siparis);
            refreshSiparis(siparisAra.getValue().toString());
            siparisdialog.close();

        });

        horizontalLayout.add(btnCancel, btnSave);
        siparisdialog.add(new H3("Yeni Sipariş"), formLayout, horizontalLayout);

        refreshSiparis(siparisAra.getValue());
        siparisGrid.removeColumnByKey("id");
        siparisGrid.setSelectionMode(Grid.SelectionMode.NONE);
        siparisGrid.setColumnReorderingAllowed(true);
        siparisGrid.setColumns("adet", "tarih");
        siparisGrid.addColumn(kategori -> kategori.getKategori().kategori).setHeader("Kategori");
        siparisGrid.addColumn(stokKodu -> stokKodu.getStok().stokKodu).setHeader("Stok Kodu");
        siparisGrid.addColumn(aciklama -> aciklama.getStok().aciklama).setHeader("Açıklama");

        siparisGrid.addComponentColumn(item -> createRemoveButton(siparisGrid, item)).setHeader("");

        add(new H2("STOKTA OLAN ÜRÜNLER"),filterGroupStok,filterGroupAciklama,stokGrid,new H2("SİPARİŞLER"),filterGroupSiparis,siparisGrid); }


    private Button createSiparis(Stok s) {
        btnYeni.addClickListener(buttonClickEvent -> {
            itemIdForEdition= 0L;
            binder.readBean(new Siparis());
            siparisdialog.open();

        });
        return btnYeni;
    }

    private void refreshData() {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList());
        siparisGrid.setItems(siparisList);
    }

    private void refreshData1() {
        List<Stok> stokList = new ArrayList<>();
        stokList.addAll(stokService.getList());
        stokGrid.setItems(stokList);
    }

    private void refreshData2(Kullanici kullanici) {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList(kullanici));
        siparisGrid.setItems(siparisList);
    }

    private void refreshStokKategori(Kategori kategori) {
        List<Stok> siparisList = new ArrayList<>();
        siparisList.addAll(stokService.getList(kategori));
        stokGrid.setItems(siparisList);
    }

    private void refreshStokAciklama(String filter) {
        List<Stok> siparisList = new ArrayList<>();
        siparisList.addAll(stokService.getList1(filter));
        stokGrid.setItems(siparisList);
    }

    private void refreshSiparisKategori(Kategori kategori) {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList(kategori));
        siparisGrid.setItems(siparisList);
    }

    private void refreshSiparis(String filter) {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList(filter,loggedInSystemUserId));
        siparisGrid.setItems(siparisList);
    }

    private HorizontalLayout createRemoveButton(Grid<Siparis> gridSiparis, Siparis item) {
        @SuppressWarnings("unchecked")
        Button btnSil = new Button("Sil");
        btnSil.addClickListener(buttonClickEvent -> {
            siparisService.delete(item);
            refreshSiparis(siparisAra.getValue());
        });

        Button btnGuncelle = new Button("Güncelle");
        btnGuncelle.addClickListener(buttonClickEvent -> {
            itemIdForEdition = item.getId();
            binder.readBean(item);
            siparisdialog.open();

            cmbstok.setValue(item.getStok());
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(btnGuncelle, btnSil);


        return horizontalLayout;
    }


}