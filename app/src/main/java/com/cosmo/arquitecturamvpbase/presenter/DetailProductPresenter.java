package com.cosmo.arquitecturamvpbase.presenter;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.DeleteResponse;
import com.cosmo.arquitecturamvpbase.repository.IProductRepository;
import com.cosmo.arquitecturamvpbase.views.activities.IDetailProductView;

import retrofit.RetrofitError;

/**
 * Created by leidyzulu on 23/09/17.
 */

public class DetailProductPresenter extends BasePresenter<IDetailProductView> {

    private IProductRepository productRepository;

    public DetailProductPresenter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(String id) {
        if(getValidateInternet().isConnected()){
            createThreadDeleteProduct(id);
        }else{
            getView().showAlertDialog(R.string.validate_internet);
        }
    }

    public void createThreadDeleteProduct(final String id) {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                deleteProductRepository(id);
            }
        });
        thread.start();
    }

    public void deleteProductRepository(String id) {

        try{
            DeleteResponse deleteResponse = productRepository.deleteProduct(id);
            if(deleteResponse.isStatus()){
                getView().showToast(R.string.correct);
            }else{
                getView().showAlertDialogError(R.string.error);
            }

        }catch (RetrofitError retrofitError){
            //TODO: manejar error
        }finally {
            getView().hideProgress();
        }

    }


}
