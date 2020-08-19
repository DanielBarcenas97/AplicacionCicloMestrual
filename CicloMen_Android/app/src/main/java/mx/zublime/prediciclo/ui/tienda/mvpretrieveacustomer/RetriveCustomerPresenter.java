package mx.zublime.prediciclo.ui.tienda.mvpretrieveacustomer;

import mx.zublime.prediciclo.data.models.Direccion;

public class RetriveCustomerPresenter implements RetriveCustomerContract.RetriveCustomerPresenter {

    private RetriveCustomerContract.RetriveCustomerView mView;
    private  RetriveCustomerContract.RetriveCustomerModel mModel;

    public RetriveCustomerPresenter(RetriveCustomerContract.RetriveCustomerView mView) {
        this.mView = mView;
        this.mModel = new RetriveCustomerModel(this);
    }

    @Override
    public void onRetriveCustomer(String idCliente) {
      if(mView != null){
          mView.showDialogRetrive();
          mModel.getRetriveCustomer(idCliente);
      }
    }

    @Override
    public void responseRetriveCustomer(Direccion response) {
       mView.hideAlertRetrive();
        if(response.getShipping() !=  null){
            int count = 1;
            if(response.getShipping().getFirst_name().isEmpty()){
                count += 1;
            }else {
                count -= 1;
            }
            if(response.getShipping().getLast_name().isEmpty()){
                count += 1;
            }else {
                count -= 1;
            }
            if(response.getShipping().getAddress_1().isEmpty()){
                count += 1;
            }else {
                count -= 1;
            }
            if(response.getShipping().getFirst_name().isEmpty()){
                count += 1;
            }
            else {
                count -= 1;
            }
            if(response.getShipping().getCity().isEmpty()){
                count += 1;
            }else {
                count -= 1;
            }
            if(response.getShipping().getPostcode().isEmpty()){
                count += 1;
            }else {
                count -= 1;
            }
            if(response.getShipping().getCountry().isEmpty()){
                count += 1;
            }else {
                count -= 1;
            }

            if(response.getShipping().getState().isEmpty()){
                count += 1;
            }else {
                count -= 1;
            }

            if(count == 7){
                mView.saveInfoCustomer(response.getShipping().getFirst_name(),response.getShipping().getLast_name(),
                        response.getShipping().getCompany(),response.getShipping().getAddress_1(),response.getShipping().getAddress_2(),
                        response.getShipping().getCity(),response.getShipping().getPostcode(),response.getShipping().getCountry(),response.getShipping() .getState(),
                        false);
            }else if (count == 0){
                mView.saveInfoCustomer(response.getShipping().getFirst_name(),response.getShipping().getLast_name(),
                        response.getShipping().getCompany(),response.getShipping().getAddress_1(),response.getShipping().getAddress_2(),
                        response.getShipping().getCity(),response.getShipping().getPostcode(),response.getShipping().getCountry(),response.getShipping() .getState(),
                        true);
            }else {
                mView.saveInfoCustomer(response.getShipping().getFirst_name(),response.getShipping().getLast_name(),
                        response.getShipping().getCompany(),response.getShipping().getAddress_1(),response.getShipping().getAddress_2(),
                        response.getShipping().getCity(),response.getShipping().getPostcode(),response.getShipping().getCountry(),response.getShipping() .getState(),
                        false);
            }

        }

    }

    @Override
    public void setError(String message) {
        mView.hideAlertRetrive();
        mView.showError(message);

    }
}
