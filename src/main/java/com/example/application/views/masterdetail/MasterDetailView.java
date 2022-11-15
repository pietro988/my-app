package com.example.application.views.masterdetail;

import com.example.application.data.entity.Misurazione;
import com.example.application.data.service.MisurazioneService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Misurazioni")
@Route(value = "misurazioni")
public class MasterDetailView extends VerticalLayout {

    public MasterDetailView(@Autowired MisurazioneService misurazioneService) {
        addClassNames("master-detail-view");

        final Grid<Misurazione> grid = new Grid<>();

        //final Grid<Misurazione> grid = new Grid<>(Misurazione.class);

        // Configure Grid
        grid.addColumn(Misurazione::getId).setHeader("id sensore");
        grid.addColumn(Misurazione::getConsumo).setHeader("potenza consumata");
        grid.addColumn(Misurazione::getDate).setHeader("data misurazione");

        grid.setItems(misurazioneService.findAll());

        add(grid);

    }



}
