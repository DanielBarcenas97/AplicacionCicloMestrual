package mx.zublime.prediciclo.ui.tienda.mvptienda;

import mx.zublime.prediciclo.data.models.ResponseRetrieveProduct;

public class TiendaPresenter implements TiendaContract.tiendaPresenter {

    private TiendaContract.tiendaView mView;
    private  TiendaContract.tiendaModel mmodel;
    private static final  String STATUS_PRODUCT = "publish";

    public TiendaPresenter(TiendaContract.tiendaView mView) {
        this.mView = mView;
        this.mmodel = new TiendaModel(this);
    }

    @Override
    public void onGetProduct() {
        if(mView != null){
            mView.showDialog();
            mmodel.getProduct();
        }
    }

    @Override
    public void responseProduct(ResponseRetrieveProduct responseProduct) {

        mView.hideDialog();
        String productImageLink = "";

        if(responseProduct != null)
        {
            if(responseProduct.getStatus().equals(STATUS_PRODUCT))
            {
                for (int index = 0; index < responseProduct.getImages().size(); index ++)
                {
                    if(responseProduct.getStock_quantity() > 0)
                    {
                        productImageLink = responseProduct.getImages().get(index).getSrc();

                        if(!responseProduct.getPrice().equals(responseProduct.getRegular_price()))
                        {

                            mView.showProductOfert(responseProduct.getName(),responseProduct.getDescription(),responseProduct.getShort_description(),
                                    responseProduct.getPrice(),responseProduct.getRegular_price(),productImageLink);
                        }
                        else
                        {

                            mView.showProductNormal(responseProduct.getName(),responseProduct.getDescription(),responseProduct.getShort_description(),
                                    responseProduct.getPrice(),productImageLink);
                        }
                    }
                    else
                        {
                        productImageLink = responseProduct.getImages().get(1).getSrc();
                        mView.showProductoNotStock(responseProduct.getName(),responseProduct.getDescription(),responseProduct.getShort_description(),
                                responseProduct.getPrice(),responseProduct.getRegular_price(),productImageLink);
                    }
                    break;
                }

            }else {
                mView.showConstructionScreen();
            }
        }
    }

    @Override
    public void setError(String message) {

    }
}
