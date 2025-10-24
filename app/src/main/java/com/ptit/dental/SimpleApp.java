/*
 * Simple version of App.java that runs LoginFrame without database dependencies
 * Use this for testing if you encounter database connection issues
 */
package com.ptit.dental;


import com.ptit.dental.view.LoginView;

import javax.swing.UIManager;

public class SimpleApp {
   public static void main(String[] args) {
        // Initialize UI immediately without database
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                System.err.println("Could not set Nimbus look and feel: " + e.getMessage());
            }
            // Sử dụng CleanLoginFrame đơn giản
            new LoginView().setVisible(true);
        });
   }
}
