package mx.zublime.prediciclo.data.models;

public class ResponseUpdateInfoDireccion {
    private int id;
    private Shipping shipping;

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
