package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity(name="customers")
public class Customer extends Model  {

	private String name;
	
	private String address;
	
	private String document;
	
	private Boolean enabled;

    private int phone;
    private String cell;

	public Customer() {}
	
    public void setTelephone(int phone) {
        this.phone = phone;
    }
}
