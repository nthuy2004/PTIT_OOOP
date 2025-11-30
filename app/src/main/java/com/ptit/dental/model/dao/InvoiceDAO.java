//
///*
// * PTIT OOP
// * QUAN LY PHONG KHAM RANG
// */
//package com.ptit.dental.model.dao;
//
//import com.ptit.dental.model.entity.Invoice;
//import com.ptit.dental.model.entity.InvoiceItem;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// *
// * @author Administrator
// */
//public class InvoiceDAO {
//
//    private Connection conn;
//
//    public InvoiceDAO(Connection conn) {
//        this.conn = conn;
//    }
//
//    public Invoice getById(int id) throws SQLException {
//        String sql = "SELECT * FROM invoice WHERE id = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, id);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    Invoice invoice = new Invoice();
//                    invoice.setId(rs.getInt("id"));
//                    invoice.setPatientId(rs.getInt("record_id")); // Using record_id as patientId temporarily
//                    invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
//                    invoice.setTotal(rs.getDouble("total"));
//
//                    // Calculate subtotal, tax, discount from total (assuming tax rate 10% and discount 5%)
//                    // This is a simplification - in real scenario, these should be stored separately
//                    double total = invoice.getTotal();
//                    double discount = total * 0.05 / 1.05; // Reverse calculate discount
//                    double subtotal = (total + discount) / 1.10; // Reverse calculate subtotal
//                    double tax = subtotal * 0.10;
//
//                    invoice.setSubtotal(subtotal);
//                    invoice.setTax(tax);
//                    invoice.setDiscount(discount);
//
//                    // Load items from service_usage table if available
//                    invoice.setItems(loadInvoiceItems(id));
//
//                    return invoice;
//                }
//            }
//        }
//        return null;
//    }
//
//    public List<Invoice> getAll() throws SQLException {
//        String sql = "SELECT * FROM invoice ORDER BY id DESC";
//        List<Invoice> invoiceList = new ArrayList<>();
//
//        try (PreparedStatement ps = conn.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Invoice invoice = new Invoice();
//                invoice.setId(rs.getInt("id"));
//                invoice.setPatientId(rs.getInt("record_id"));
//                invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
//                invoice.setTotal(rs.getDouble("total"));
//
//                // Calculate subtotal, tax, discount from total
//                double total = invoice.getTotal();
//                double discount = total * 0.05 / 1.05;
//                double subtotal = (total + discount) / 1.10;
//                double tax = subtotal * 0.10;
//
//                invoice.setSubtotal(subtotal);
//                invoice.setTax(tax);
//                invoice.setDiscount(discount);
//
//                invoice.setItems(loadInvoiceItems(invoice.getId()));
//
//                invoiceList.add(invoice);
//            }
//        }
//        return invoiceList;
//    }
//
//    public List<Invoice> searchByPatientName(String keyword) throws SQLException {
//        String sql = "SELECT i.* FROM invoice i " +
//                "INNER JOIN patients p ON i.record_id = p.id " +
//                "WHERE p.fullname LIKE ? ORDER BY i.id DESC";
//        List<Invoice> invoiceList = new ArrayList<>();
//
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, "%" + keyword + "%");
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Invoice invoice = new Invoice();
//                    invoice.setId(rs.getInt("id"));
//                    invoice.setPatientId(rs.getInt("record_id"));
//                    invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
//                    invoice.setTotal(rs.getDouble("total"));
//
//                    // Calculate subtotal, tax, discount from total
//                    double total = invoice.getTotal();
//                    double discount = total * 0.05 / 1.05;
//                    double subtotal = (total + discount) / 1.10;
//                    double tax = subtotal * 0.10;
//
//                    invoice.setSubtotal(subtotal);
//                    invoice.setTax(tax);
//                    invoice.setDiscount(discount);
//
//                    invoice.setItems(loadInvoiceItems(invoice.getId()));
//
//                    invoiceList.add(invoice);
//                }
//            }
//        }
//        return invoiceList;
//    }
//
//    public void insert(Invoice invoice) throws SQLException {
//        String sql = "INSERT INTO invoice (record_id, created_time, status, total) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
//            ps.setInt(1, invoice.getPatientId());
//            ps.setTimestamp(2, new java.sql.Timestamp(invoice.getCreatedDate().getTime()));
//            ps.setInt(3, 1); // Default status = 1 (active)
//            ps.setDouble(4, invoice.getTotal());
//            ps.executeUpdate();
//
//            // Get generated invoice ID
//            try (ResultSet rs = ps.getGeneratedKeys()) {
//                if (rs.next()) {
//                    int invoiceId = rs.getInt(1);
//                    invoice.setId(invoiceId);
//                    // Save invoice items to service_usage table
//                    saveInvoiceItems(invoiceId, invoice);
//                }
//            }
//        }
//    }
//
//    public boolean update(Invoice invoice) throws SQLException {
//        String sql = "UPDATE invoice SET record_id = ?, created_time = ?, total = ? WHERE id = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, invoice.getPatientId());
//            ps.setTimestamp(2, new java.sql.Timestamp(invoice.getCreatedDate().getTime()));
//            ps.setDouble(3, invoice.getTotal());
//            ps.setInt(4, invoice.getId());
//
//            // Update items
//            deleteInvoiceItems(invoice.getId());
//            saveInvoiceItems(invoice.getId(), invoice);
//
//            int rowsAffected = ps.executeUpdate();
//            return rowsAffected > 0;
//        }
//    }
//
//    public boolean delete(int id) throws SQLException {
//        // Delete invoice items first
//        deleteInvoiceItems(id);
//
//        String sql = "DELETE FROM invoice WHERE id = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, id);
//            int rowsAffected = ps.executeUpdate();
//            return rowsAffected > 0;
//        }
//    }
//
//    // Helper methods to save/load invoice items
//    // Note: This is a simplified implementation using service_usage table
//    // In a real scenario, you might want a dedicated invoice_items table
//    private void saveInvoiceItems(int invoiceId, Invoice invoice) throws SQLException {
//        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
//            return;
//        }
//
//        // For simplicity, we'll store items in service_usage table
//        // You might need to create a proper invoice_items table for production
//        // Here we'll try to find service by name and link it
//        String sql = "INSERT INTO service_usage (patient_id, service_id, usage_time, quantity) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            for (InvoiceItem item : invoice.getItems()) {
//                // Try to find service by name
//                int serviceId = findServiceIdByName(item.getServiceName());
//                if (serviceId > 0) {
//                    ps.setInt(1, invoice.getPatientId());
//                    ps.setInt(2, serviceId);
//                    ps.setTimestamp(3, new java.sql.Timestamp(invoice.getCreatedDate().getTime()));
//                    ps.setInt(4, item.getQuantity());
//                    ps.addBatch();
//                }
//            }
//            ps.executeBatch();
//        }
//    }
//
//    private List<InvoiceItem> loadInvoiceItems(int invoiceId) throws SQLException {
//        List<InvoiceItem> items = new ArrayList<>();
//
//        // Note: Schema doesn't have a dedicated invoice_items table
//        // This is a simplified implementation that tries to load from service_usage
//        // In a production environment, you should create an invoice_items table
//        try {
//            // First, get the invoice to get patient_id and created_time
//            String invoiceSql = "SELECT record_id, created_time FROM invoice WHERE id = ?";
//            int patientId = 0;
//            java.sql.Timestamp createdTime = null;
//
//            try (PreparedStatement ps = conn.prepareStatement(invoiceSql)) {
//                ps.setInt(1, invoiceId);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        patientId = rs.getInt("record_id");
//                        createdTime = rs.getTimestamp("created_time");
//                    }
//                }
//            }
//
//            if (patientId > 0 && createdTime != null) {
//                // Try to load items from service_usage table
//                // This is not ideal but works as a workaround
//                String sql = "SELECT su.*, s.name, s.price FROM service_usage su " +
//                        "INNER JOIN services s ON su.service_id = s.id " +
//                        "WHERE su.patient_id = ? AND DATE(su.usage_time) = DATE(?)";
//                try (PreparedStatement ps = conn.prepareStatement(sql)) {
//                    ps.setInt(1, patientId);
//                    ps.setTimestamp(2, createdTime);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        while (rs.next()) {
//                            InvoiceItem item = new InvoiceItem();
//                            item.setServiceName(rs.getString("name"));
//                            item.setUnitPrice(rs.getDouble("price"));
//                            item.setQuantity(rs.getInt("quantity"));
//                            item.setTotal(rs.getDouble("price") * rs.getInt("quantity"));
//                            items.add(item);
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            // If loading fails, return empty list
//            // This is acceptable since the schema doesn't have invoice_items table
//        }
//
//        return items;
//    }
//
//    private void deleteInvoiceItems(int invoiceId) throws SQLException {
//        // Note: Since there's no dedicated invoice_items table,
//        // we can't reliably delete items without affecting other data
//        // In a production environment, you should create an invoice_items table
//        // For now, we'll skip this to avoid deleting unrelated service_usage records
//        // This is a limitation of the current schema design
//    }
//
//    private int findServiceIdByName(String serviceName) throws SQLException {
//        String sql = "SELECT id FROM services WHERE name = ? LIMIT 1";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, serviceName);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt("id");
//                }
//            }
//        }
//        return 0;
//    }
//}
//
//

package com.ptit.dental.model.dao;

import com.ptit.dental.model.entity.Invoice;
import com.ptit.dental.model.entity.InvoiceItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDAO {

    private final Connection connection;

    public InvoiceDAO(Connection connection) {
        this.connection = connection;
    }

    // ===========================================================
    // CREATE INVOICE
    // ===========================================================
    public int createInvoice(Invoice invoice) throws SQLException {
        String sql = "INSERT INTO invoice (record_id, created_time, total) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, invoice.getPatientId());
            ps.setTimestamp(2, new java.sql.Timestamp(invoice.getCreatedDate().getTime()));
            ps.setDouble(3, invoice.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int invoiceId = rs.getInt(1);
                invoice.setId(invoiceId);

                // Save items
                for (InvoiceItem item : invoice.getItems()) {
                    createInvoiceItem(invoiceId, item);
                }

                return invoiceId;
            }
        }

        return -1;
    }

    // ===========================================================
    // CREATE INVOICE ITEM
    // ===========================================================
    private void createInvoiceItem(int invoiceId, InvoiceItem item) throws SQLException {
        String sql = "INSERT INTO invoice_item (invoice_id, service_name, quantity, price, total) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ps.setString(2, item.getServiceName());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());
            ps.setDouble(5, item.getTotal());
            ps.executeUpdate();
        }
    }

    // ===========================================================
    // GET ALL INVOICES
    // ===========================================================
    public List<Invoice> getAll() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM invoice ORDER BY id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setPatientId(rs.getInt("record_id"));
                invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
                invoice.setTotal(rs.getDouble("total"));

                invoice.setItems(getInvoiceItems(invoice.getId()));

                list.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===========================================================
    // GET INVOICE ITEMS
    // ===========================================================
    public List<InvoiceItem> getInvoiceItems(int invoiceId) {
        List<InvoiceItem> items = new ArrayList<>();

        String sql = "SELECT * FROM invoice_item WHERE invoice_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InvoiceItem item = new InvoiceItem();
                item.setServiceName(rs.getString("service_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("price"));
                item.setTotal(rs.getDouble("total"));

                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // ===========================================================
    // DELETE INVOICE
    // ===========================================================
    public boolean deleteInvoice(int id) {
        try {

            deleteInvoiceItems(id);

            String sql = "DELETE FROM invoice WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===========================================================
    // DELETE INVOICE ITEMS
    // ===========================================================
    private void deleteInvoiceItems(int invoiceId) throws SQLException {
        String sql = "DELETE FROM invoice_item WHERE invoice_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, invoiceId);
        ps.executeUpdate();
    }

    // Compatibility wrappers for older controller code
    public Invoice getById(int id) throws SQLException {
        String sql = "SELECT * FROM invoice WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setPatientId(rs.getInt("record_id"));
                    invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
                    invoice.setTotal(rs.getDouble("total"));
                    invoice.setItems(getInvoiceItems(invoice.getId()));
                    return invoice;
                }
            }
        }
        return null;
    }

    public List<Invoice> searchByPatientName(String keyword) throws SQLException {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "SELECT i.* FROM invoice i INNER JOIN patients p ON i.record_id = p.id WHERE p.fullname LIKE ? ORDER BY i.id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setPatientId(rs.getInt("record_id"));
                    invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
                    invoice.setTotal(rs.getDouble("total"));
                    invoice.setItems(getInvoiceItems(invoice.getId()));
                    invoiceList.add(invoice);
                }
            }
        }
        return invoiceList;
    }

    public void insert(Invoice invoice) throws SQLException {
        createInvoice(invoice);
    }

    public boolean update(Invoice invoice) throws SQLException {
        // Simple update implementation
        String sql = "UPDATE invoice SET record_id = ?, created_time = ?, total = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoice.getPatientId());
            ps.setTimestamp(2, new java.sql.Timestamp(invoice.getCreatedDate().getTime()));
            ps.setDouble(3, invoice.getTotal());
            ps.setInt(4, invoice.getId());

            // Replace items: delete and re-create
            deleteInvoiceItems(invoice.getId());
            for (InvoiceItem item : invoice.getItems()) {
                createInvoiceItem(invoice.getId(), item);
            }

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        return deleteInvoice(id);
    }
}
