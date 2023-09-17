package com.example.licenta.classes;

import java.util.HashMap;
import java.util.Map;

public class TutorForm {
    private String descriere;
    private String durata;
    private String pret;
    private String contact;

    public TutorForm() {
    }

    public TutorForm(String descriere, String durata, String pret, String contact) {
        this.descriere = descriere;
        this.durata = durata;
        this.pret = pret;
        this.contact = contact;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getDurata() {
        return durata;
    }

    public String getPret() {
        return pret;
    }

    public String getContact() {
        return contact;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("descriere", descriere);
        result.put("durata", durata);
        result.put("pret", pret);
        result.put("contact", contact);
        return result;
    }
    @Override
    public String toString() {
        return "TutorForm{" +
                "descriere='" + descriere + '\'' +
                ", durata='" + durata + '\'' +
                ", pret='" + pret + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
