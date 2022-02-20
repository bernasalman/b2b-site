package com.example.application.views;

import com.example.application.models.Kategori;
import com.example.application.models.Stok;
import com.example.application.services.KategoriService;
import com.example.application.services.StokService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "stok", layout = MainView.class)
@PageTitle("Stoklar")
public class StokKartıView extends VerticalLayout {

    private final StokService stokService;
    private final KategoriService kategoriService;
    Grid<Stok> grid = new Grid<>(Stok.class);
    TextField stokKoduAra = new TextField();
    Binder<Stok> binder = new Binder<>(Stok.class);
    Dialog stokdialog = new Dialog();
    Long itemIdForEdition = 0L;
    Long longgedInSystemUserId;

    TextField stokKodu = new TextField("Stok Kodu");
    ComboBox<Kategori> cmbkategori = new ComboBox<>("Kategori");
    TextArea aciklama = new TextArea("Açıklama");
    IntegerField intadet = new IntegerField("Adet");

    public StokKartıView(StokService stokService, KategoriService kategoriService) {

        this.stokService = stokService;
        this.kategoriService = kategoriService;

        Button btnYeni = new Button("Stok Kartı Ekle", VaadinIcon.PLUS.create());
        stokKoduAra.setPlaceholder("Stok Kodu");
        Button btnFilter = new Button("ara", VaadinIcon.SEARCH.create());
        btnFilter.addClickListener(buttonClickEvent -> {
            refreshStokKodu(stokKoduAra.getValue());
        });
        HorizontalLayout filterGroup = new HorizontalLayout();
        filterGroup.add(stokKoduAra, btnFilter);
        stokKoduAra.setPlaceholder("aranacak stok kodu ");

        ComboBox<Kategori> kategoriAra = new ComboBox<>();
        kategoriAra.setPlaceholder("kategori seçin");
        kategoriAra.setItems(this.kategoriService.getList());
        kategoriAra.setItemLabelGenerator(Kategori::getKategori);
        Button btnFilterSiparis = new Button("kategori ara", VaadinIcon.SEARCH.create());
        btnFilterSiparis.addClickListener(buttonClickEvent -> {
            refreshKategori(kategoriAra.getValue());
        });
        HorizontalLayout filterGroup1 = new HorizontalLayout();
        filterGroup1.add(kategoriAra, btnFilterSiparis);


        stokdialog.setModal(true);
        stokKodu.setPlaceholder("stok kodunu giriniz");
        cmbkategori.setItems(kategoriService.getList());
        cmbkategori.setItemLabelGenerator(Kategori::getKategori);
        cmbkategori.setPlaceholder("kategori türü seçin");
        aciklama.getStyle().set("maxHeight", "150px");
        aciklama.setPlaceholder("Buraya yazın...");
        aciklama.setHelperText("Mevcut stok kartı için kısa açıklama bilgisi");
        intadet.setPlaceholder("adet giriniz");

        FormLayout formLayout = new FormLayout();
        formLayout.add(cmbkategori, stokKodu, intadet, aciklama);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        Button btnSave = new Button("Kaydet");
        Button btnCancel = new Button("Vazgeç");
        btnCancel.addClickListener(buttonClickEvent -> {
            stokdialog.close();
        });

        btnSave.addClickListener(buttonClickEvent -> {
            Stok stok = new Stok();
            stok.setStokKodu(stokKodu.getValue());
            stok.setKategori(cmbkategori.getValue());
            stok.setAciklama(aciklama.getValue());
            stok.setAdet(intadet.getValue());

            stok.setId(itemIdForEdition);
            stokService.save(stok);
            refreshData();
            stokdialog.close();
        });

        horizontalLayout.add(btnCancel, btnSave);
        stokdialog.add(new H3("Yeni Stok"), formLayout, horizontalLayout);

        btnYeni.addClickListener(buttonClickEvent -> {
            itemIdForEdition = 0L;
            binder.readBean(new Stok());
            stokdialog.open();
        });

        refreshData();
        grid.removeColumnByKey("id");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setColumns("stokKodu", "aciklama", "adet");
        grid.addColumn(kategori -> kategori.getKategori().kategori).setHeader("Kategori");
        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("");

        add(new H2("STOKLAR"), btnYeni, filterGroup,filterGroup1, grid);
    }

    private void refreshData() {
        List<Stok> stokList = new ArrayList<>();
        stokList.addAll(stokService.getList());
        grid.setItems(stokList);
    }

    private void refreshStokKodu(String stokKodu) {
        List<Stok> stokList = new ArrayList<>();
        stokList.addAll(stokService.getList(stokKodu));
        grid.setItems(stokList);
    }

    private void refreshKategori(Kategori kategori) {
        List<Stok> stokList = new ArrayList<>();
        stokList.addAll(stokService.getList(kategori));
        grid.setItems(stokList);
    }

    private HorizontalLayout createRemoveButton(Grid<Stok> grid, Stok item) {
        @SuppressWarnings("unchecked")
        Button btnSil = new Button("Sil");
        btnSil.addClickListener(buttonClickEvent -> {
            stokService.delete(item);
            refreshData();
        });

        Button btnGuncelle = new Button("Güncelle");
        btnGuncelle.addClickListener(buttonClickEvent -> {
            itemIdForEdition = item.Id;
            binder.readBean(item);
            stokdialog.open();

            stokKodu.setValue(String.valueOf(item.getStokKodu()));
            intadet.setValue(Integer.valueOf(item.getAdet()));
            aciklama.setValue(String.valueOf(item.getAciklama()));
            cmbkategori.setValue(item.kategori);

        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(btnGuncelle, btnSil);

        return horizontalLayout;
    }
}
