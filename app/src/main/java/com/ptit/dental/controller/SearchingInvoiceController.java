package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.view.SearchingInvoice;

public class SearchingInvoiceController extends BaseController<SearchingInvoice> {
    public SearchingInvoiceController(SearchingInvoice view) {
        super(view);
        initListeners();
    }

    private void initListeners() {
        // Add action listeners and event handling logic here
        view.getSearchButton().addActionListener(e -> searchInvoices());
    }

    private void searchInvoices() {
        String query = view.getSearchField().getText();
        // Implement search logic here
    }

}
