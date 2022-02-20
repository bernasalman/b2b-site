package com.example.application.views;

import com.example.application.models.Kategori;
import com.example.application.services.KategoriService;
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

@Route(value = "kategori", layout = MainView.class)
@PageTitle("Katogori Tanımlama")
public class KategorilerView extends VerticalLayout {

    private final KategoriService kategoriService;
    Grid<Kategori> grid = new Grid<>(Kategori.class);
    TextField txtFilter = new TextField();
    Binder<Kategori> binder = new Binder<>();
    Dialog kategoridialog = new Dialog();
    Long itemIdForEdition=0L;


    public KategorilerView(KategoriService kategoriService) {
        this.kategoriService = kategoriService;

        Button btnYeni = new Button("Yeni Kategori Ekle", VaadinIcon.PLUS.create());

        txtFilter.setPlaceholder("");
        Button btnFilter = new Button("kategori ara", VaadinIcon.SEARCH.create());
        btnFilter.addClickListener(buttonClickEvent -> {
            refreshData(txtFilter.getValue());
        });
        HorizontalLayout filterGroup = new HorizontalLayout();
        filterGroup.add(txtFilter,btnFilter);
        txtFilter.setPlaceholder("aranacak kategori");

        kategoridialog.setModal(true);
        TextField txtAd = new TextField("Kategori Adı");
        binder.bind(txtAd,Kategori::getKategori, Kategori::setKategori);

        FormLayout formLayout = new FormLayout();
        formLayout.add(txtAd);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        Button btnSave = new Button("Kaydet");
        Button btnCancel = new Button("Vazgeç");
        btnCancel.addClickListener(buttonClickEvent -> {
            kategoridialog.close();
        });

        btnSave.addClickListener(buttonClickEvent -> {
            Kategori kategori = new Kategori();
            try {
                binder.writeBean(kategori);
            } catch (ValidationException e) {
                e.printStackTrace();
            }

            kategori.setId(itemIdForEdition);
            kategoriService.save(kategori);
            refreshData();
            kategoridialog.close();
        });

        horizontalLayout.add(btnCancel, btnSave);
        kategoridialog.add(new H3("Yeni Kategori"), formLayout, horizontalLayout);

        btnYeni.addClickListener(buttonClickEvent -> {
            itemIdForEdition=0L;
            binder.readBean(new Kategori());
            kategoridialog.open();
        });

        refreshData();

        grid.removeColumnByKey("id");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setColumns("kategori");
        grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("");

        add(new H2("KATEGORİLER"), btnYeni,filterGroup, grid);
    }


    private void refreshData() {
        List<Kategori> kategoriList = new ArrayList<>();
        kategoriList.addAll(kategoriService.getList());
        grid.setItems(kategoriList);
    }

    private void refreshData(String filter) {
        List<Kategori> kategoriList = new ArrayList<>();
        kategoriList.addAll(kategoriService.getList(filter));
        grid.setItems(kategoriList);
    }

    private HorizontalLayout createRemoveButton(Grid<Kategori> grid, Kategori item) {
        @SuppressWarnings("unchecked")
        Button btnSil = new Button("Sil");
        btnSil.addClickListener(buttonClickEvent -> {
            kategoriService.delete(item);
            refreshData();
        });

        Button btnGuncelle = new Button("Güncelle");
        btnGuncelle.addClickListener(buttonClickEvent -> {
            itemIdForEdition = item.getId();
            binder.readBean(item);
            kategoridialog.open();
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(btnGuncelle, btnSil);

        return horizontalLayout;
    }

}
