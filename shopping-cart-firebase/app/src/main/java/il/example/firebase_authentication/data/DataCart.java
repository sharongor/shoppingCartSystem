package il.example.firebase_authentication.data;

public class DataCart {
    public String nameArray;

    public String priceArray;

    public int picArray;


    public DataCart(String nameArray, String priceArray, int picArray) {
        this.nameArray = nameArray;
        this.priceArray = priceArray;
        this.picArray = picArray;
    }

    public String getNameArray() {
        return nameArray;
    }

    public String getPriceArray() {
        return priceArray;
    }

    public int getPicArray() {
        return picArray;
    }
}
