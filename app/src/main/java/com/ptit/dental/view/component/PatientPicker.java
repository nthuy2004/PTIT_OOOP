/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.view.component;

import com.ptit.dental.model.entity.Patient;
import java.awt.*;
import javax.swing.*;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class PatientPicker extends JComboBox<Patient> {

    public PatientPicker(List<Patient> patients) {
        super(patients.toArray(new Patient[0]));

        // renderer: hiển thị fullname thay vì PatientDTO.toString()
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Patient) {
                    Patient p = (Patient) value;
                    setText(p.getFullname() + " (" + p.getPhone() + ")");
                }

                return this;
            }
        });
    }

    public Patient getSelectedPatient() {
        return (Patient) getSelectedItem();
    }

    public void setSelectedById(int id) {
        for (int i = 0; i < getItemCount(); i++) {
            Patient p = getItemAt(i);
            if (p.getId() == id) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public void setSelectedByName(String name) {
        if (name == null)
            return;

        for (int i = 0; i < getItemCount(); i++) {
            Patient p = getItemAt(i);
            if (p.getFullname().equalsIgnoreCase(name)) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public void setSelected(java.util.function.Predicate<Patient> filter) {
        for (int i = 0; i < getItemCount(); i++) {
            Patient p = getItemAt(i);
            if (filter.test(p)) {
                setSelectedIndex(i);
                return;
            }
        }
    }
}