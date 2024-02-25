package il.example.firebase_authentication.data;

public class Data {
    //public String name;
    public String id;
    public String user;
    public String pass;
    public String phone;

    public String name;

    public Data(String id, String user, String pass, String phone,String name) {
        //this.name = name;
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.phone = phone;
        this.name = name;
    }

    public Data(){}
}
