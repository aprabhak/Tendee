/**
 * Created by ChaoLun on 1/30/18.
 */


public class User {
    private String name;
    private String email;
    private String id;
    private String address;
    private String description;
    private int phone;


    public User(String name, String email,String address,String description) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.address = address;
        this.description = description;

    }

    public String getName() {return this.name;}
    public String getEmail() {return this.email;}
    public String getId() {return this.id;}
    public String getAddress() {return this.address;}
    public String getDescription(){return this.description;}
    public int getPhone() {return this.phone;}

    public void setName(String name){this.name = name;}
    public void setEmail(String name){this.email = email;}
    public void setAddress(String address){this.address = address;}
    public void setDescription(String description){this.description = description;}
    public void setPhone(int phone){this.phone = phone;}
}
