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
 * ItemAttributeType generated by hbm2java
 */
@Entity
@Table(name = "item_attribute_type")
public class ItemAttributeType implements java.io.Serializable {

	private long itemAttributeType;
	private String typeName;
	private String multipleAttributes;
	private Long dataType;
	private Set<TypeAttribute> typeAttributes = new HashSet<TypeAttribute>(0);
	private Set<ItemAttribute> itemAttributes = new HashSet<ItemAttribute>(0);

	public ItemAttributeType() {
	}

	public ItemAttributeType(long itemAttributeType) {
		this.itemAttributeType = itemAttributeType;
	}

	public ItemAttributeType(long itemAttributeType, String typeName,
			String multipleAttributes, Long dataType,
			Set<TypeAttribute> typeAttributes, Set<ItemAttribute> itemAttributes) {
		this.itemAttributeType = itemAttributeType;
		this.typeName = typeName;
		this.multipleAttributes = multipleAttributes;
		this.dataType = dataType;
		this.typeAttributes = typeAttributes;
		this.itemAttributes = itemAttributes;
	}

	@Id
	@Column(name = "item_attribute_type", unique = true, nullable = false, precision = 10, scale = 0)
	public long getItemAttributeType() {
		return this.itemAttributeType;
	}

	public void setItemAttributeType(long itemAttributeType) {
		this.itemAttributeType = itemAttributeType;
	}

	@Column(name = "type_name")
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "multiple_attributes", length = 1)
	public String getMultipleAttributes() {
		return this.multipleAttributes;
	}

	public void setMultipleAttributes(String multipleAttributes) {
		this.multipleAttributes = multipleAttributes;
	}

	@Column(name = "data_type", precision = 10, scale = 0)
	public Long getDataType() {
		return this.dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemAttributeType")
	public Set<TypeAttribute> getTypeAttributes() {
		return this.typeAttributes;
	}

	public void setTypeAttributes(Set<TypeAttribute> typeAttributes) {
		this.typeAttributes = typeAttributes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemAttributeType")
	public Set<ItemAttribute> getItemAttributes() {
		return this.itemAttributes;
	}

	public void setItemAttributes(Set<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

}