package mx.zublime.prediciclo.ui.tienda.mvptienda;

import mx.zublime.prediciclo.data.models.ResponseRetrieveProduct;

public interface TiendaContract {

    interface  tiendaView{

        void showDialog();
        void hideDialog();
        void showProductNormal(String name,String descripction, String shortDescription,String price,String linkImage);
        void showProductOfert(String name,String descripction, String shortDescription,String price, String regularPrice,String linkImage);
        void showProductoNotStock(String name,String descripction, String shortDescription,String price, String regularPrice,String linkImage);
        void showConstructionScreen();
        void showError(String message);

    }
    interface tiendaPresenter{
        void  onGetProduct();
        void  responseProduct(ResponseRetrieveProduct responseProduct);
        void  setError(String message);
    }

    interface tiendaModel
    {
        void getProduct();

    }
}
