package com.example.a19111036_ekobambangnuryanto;

public class Dataku {
    String kunci;
    String isi;

    String id;
    String name;
    String type;
    String price;

    public Dataku() {

    }

    public Dataku(String kunci, String id, String name, String type, String price) {
        this.kunci = kunci;
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getKunci() {
        return kunci;
    }

    public void setKunci(String kunci) {
        this.kunci = kunci;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
