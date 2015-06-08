package db;

// Generated by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * AddressType generated by hbm2java
 */
@Entity
@Table(name = "address_type")
public class AddressType implements java.io.Serializable {

	private int addressType;
	private String typeName;
	private Set<Address> addresses = new HashSet<Address>(0);

	public AddressType() {
	}

	public AddressType(int addressType) {
		this.addressType = addressType;
	}

	public AddressType(int addressType, String typeName, Set<Address> addresses) {
		this.addressType = addressType;
		this.typeName = typeName;
		this.addresses = addresses;
	}

	@Id
	@Column(name = "address_type", unique = true, nullable = false)
	public int getAddressType() {
		return this.addressType;
	}

	public void setAddressType(int addressType) {
		this.addressType = addressType;
	}

	@Column(name = "type_name", length = 200)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addressType")
	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

}