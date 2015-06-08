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
 * UnitType generated by hbm2java
 */
@Entity
@Table(name = "unit_type")
public class UnitType implements java.io.Serializable {

	private long unitType;
	private String typeName;
	private String longName;
	private Set<Item> items = new HashSet<Item>(0);

	public UnitType() {
	}

	public UnitType(long unitType) {
		this.unitType = unitType;
	}

	public UnitType(long unitType, String typeName, String longName,
			Set<Item> items) {
		this.unitType = unitType;
		this.typeName = typeName;
		this.longName = longName;
		this.items = items;
	}

	@Id
	@Column(name = "unit_type", unique = true, nullable = false, precision = 10, scale = 0)
	public long getUnitType() {
		return this.unitType;
	}

	public void setUnitType(long unitType) {
		this.unitType = unitType;
	}

	@Column(name = "type_name", length = 200)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "long_name", length = 200)
	public String getLongName() {
		return this.longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unitType")
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

}