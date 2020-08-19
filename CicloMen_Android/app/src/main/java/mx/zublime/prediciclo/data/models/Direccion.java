package mx.zublime.prediciclo.data.models;

public class Direccion extends Shipping {

    private Shipping shipping;

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }
}
