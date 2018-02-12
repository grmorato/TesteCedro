package grmorato.testecedro.Models;


/**
 * Created by grmorato on 09/02/2018.
 */

public class UserProfile
{
    private String name;
    private String email;
    private byte[] image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
