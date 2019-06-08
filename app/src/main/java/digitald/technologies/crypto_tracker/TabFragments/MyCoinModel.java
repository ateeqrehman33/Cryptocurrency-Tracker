package digitald.technologies.crypto_tracker.TabFragments;

public class MyCoinModel {

    Float quantity;
    String symbol;
    Integer indexx;





    public MyCoinModel(){

    }

    public MyCoinModel(Integer indexx,String symbol,Float quantity){


        this.indexx = indexx;
        this.symbol = symbol;
        this.quantity = quantity;


    }
}


