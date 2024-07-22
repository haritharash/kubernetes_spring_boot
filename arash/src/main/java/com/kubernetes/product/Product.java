package com.kubernetes.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Product")
@Data
public class Product {

	@Id
	@Column
	private Integer id;

	@Column
	private String name;

	@Column
	private String description;
}
