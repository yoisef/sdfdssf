package werpx.marketopia.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;


public class productViewmodel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<mytable>> mAllpro;
    private LiveData<List<Productltable>> mallproductsroom;
    private LiveData<List<historytable>> mallhis;
    private LiveData<Integer> mcount;
    private MutableLiveData<Productltable> searchResults;



    public productViewmodel (Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllpro = mRepository.getAllWords();
        mallhis=mRepository.getAllHis();
        searchResults = mRepository.getSearchResults();
        mcount=mRepository.getMcountrows();
        mallproductsroom=mRepository.getAllproductsroom();

    }

    //menu...

   public LiveData<List<mytable>> getAllWords() { return mAllpro; }
    public void insert(mytable word) { mRepository.insert(word); }
    public void delterow(mytable num){mRepository.deleterow(num);}
    public void updateproduct(long newitems ,long barcode){mRepository.updaterow( newitems , barcode);}



    public LiveData<Integer> getMcount() {
        return mcount;
    }

    public MutableLiveData<Productltable> getSearchResults() {
        return searchResults;
    }

    public void insertProductforlist(Productltable product) {
       mRepository.insertProduct(product);
    }

    public void findProduct(String barcode) {
        mRepository.findProduct(barcode);
    }











    ///history section
    public LiveData<List<historytable>> getAllhistory() { return mallhis; }

    public void inserthis(historytable hist) { mRepository.insert(hist); }
    public void deleteallhist(){mRepository.deletallhis();}
    public void deleteallproduct(){mRepository.deletallproducts();}

    public void deleteallmenu(){mRepository.deletemenuproduct();}



    //productsroom
    public void insertroom(Productltable productltable) { mRepository.insertproducts(productltable); }

    public LiveData<List<Productltable>> getAllproductroom() { return mallproductsroom; }

    public void updateproductroom(long newitems ,long barcode){mRepository.updaterowproduct( newitems , barcode);}

    public void deleteallroom(){mRepository.deleteproductroom();}

}
