package mx.zublime.prediciclo;

import android.app.Application;

import mx.openpay.android.Openpay;

public class Prediciclo extends Application
{
    public static final String BASE_URL = "https://prediciclo.zublime.mx/wp-json/";
    public static Openpay openpay;

    public static void createOpenPay(){
        openpay = new Openpay("m9m1dbharfvbg0twrpsi",
                "pk_e768c7c67edb48df8f85750b2158e8c3",true);
    }

    public static Openpay getOpenPay(){
        return openpay;
    }



}
