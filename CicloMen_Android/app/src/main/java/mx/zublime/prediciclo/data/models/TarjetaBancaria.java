package mx.zublime.prediciclo.data.models;

public class TarjetaBancaria {
    private String nombreTitular;
    private  String numeroTarjeta;
    private String ccv;
    private  String fechaVencimiento;
    private String numeroTarjetaConMascara;
    private int tipoTarjeta;

    public int getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(int tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getNumeroTarjetaConMascara() {
        return numeroTarjetaConMascara;
    }

    public void setNumeroTarjetaConMascara(String numeroTarjetaConMascara) {
        this.numeroTarjetaConMascara = numeroTarjetaConMascara;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
