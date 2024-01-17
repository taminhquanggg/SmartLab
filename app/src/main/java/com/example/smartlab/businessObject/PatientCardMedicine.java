package com.example.smartlab.businessObject;

public class PatientCardMedicine {
    public String name ;
    public int Soluong ;
    public int SoluongMua ;
    public int gia ;
    public String Image ;
    public  String medicineCardID;
    public int tong ;
    public int thue;
    public int tongcong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public int getSoluongMua() {
        return SoluongMua;
    }

    public void setSoluongMua(int   soluongMua) {
        SoluongMua = soluongMua;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMedicineCardID() {
        return medicineCardID;
    }

    public void setMedicineCardID(String medicineCardID) {
        this.medicineCardID = medicineCardID;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }

    public int getThue() {
        return thue;
    }

    public void setThue(int thue) {
        this.thue = thue;
    }

    public int getTongcong() {
        return tongcong;
    }

    public void setTongcong(int tongcong) {
        this.tongcong = tongcong;
    }
}
