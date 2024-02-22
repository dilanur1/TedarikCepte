package com.example.tedarikcepte

class User {
    var id: Int = 0
    var ad: String = ""
    var soyad: String = ""
    var firma: String = ""
    var adres: String = ""
    var telefon: String = ""
    var kullaniciAdi: String = ""
    var parola: String = ""
    constructor(ad:String, soyad:String, firma:String, adres:String, telefon:String, kullaniciAdi:String, parola:String) {
        this.ad = ad
        this.soyad = soyad
        this.firma = firma
        this.adres = adres
        this.telefon = telefon
        this.kullaniciAdi = kullaniciAdi
        this.parola = parola
    }
    constructor() {

    }
}