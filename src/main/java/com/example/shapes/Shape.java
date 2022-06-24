package com.example.shapes;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Entity
@Table(name = "shape_table")
public class Shape {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// TODO: Make an abstract shape class and have a circle and square class to go
	// off of this
	// Current types: "circle" or "square"
	private String shape_type;

	// Current values: "radius" (if circle), "side" (if square)
	private double shape_value;

	private double shape_area;

	private double shape_perimeter;

	Shape() {
	}

	Shape(String type, double value) {

		this.shape_type = type;
		this.shape_value = value;
		// Note: Calculating area and perimeter right away whenever we change value gives us the benefit of having those right away,
		// but with the extreme cost of work upfront. 
		// This would not be as efficient as asynchronously saving and updating the record if many new requests are being made
		calculateArea();
		calculatePerimeter();
	}

	public Long getId() {
		return this.id;
	}

	public String getType() {
		return this.shape_type;
	}

	public double getValue() {
		return this.shape_value;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(String type) {
		this.shape_type = type;
	}

	public void setValue(double value) {
		this.shape_value = value;
	}

	public double getArea() {
		return this.shape_area;
	}

	public double getPerimeter() {
		return this.shape_perimeter;
	}
	
	public void setRadius(double value) {
		this.shape_value = value;
		calculateArea();
		calculatePerimeter();
	}
	
	public double getRadius() {
		return this.shape_value;
	}
	
	public void setSide(double value) {
		this.shape_value = value;
		calculateArea();
		calculatePerimeter();
	}
	
	public double getSide() {
		return this.shape_value;
	}

	public void calculateArea() {
		switch (this.shape_type) {
		case "circle":
			this.shape_area = Math.PI * this.shape_value * this.shape_value;
			break;
		case "square":
			this.shape_area = this.shape_value * this.shape_value;
			break;
		default:
			this.shape_area = 0;
			break;
		}
	}

	public void calculatePerimeter() {
		switch (this.shape_type) {
		case "circle":
			this.shape_perimeter = Math.PI * 2 * this.shape_value;
			break;
		case "square":
			this.shape_perimeter = this.shape_value * 4;
			break;
		default:
			this.shape_perimeter = 0;
			break;
		}
	}
	
	public String getValueType() {
		String valueType = "value";
		switch (this.shape_type) {
			case "circle":
				valueType = "radius";
				break;
			case "square":
				valueType = "square";
				break;
		}
		return valueType;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Shape))
			return false;
		Shape shape = (Shape) o;
		return Objects.equals(this.id, shape.id) && Objects.equals(this.shape_type, shape.shape_type)
				&& (this.shape_value == shape.shape_value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.shape_type, this.shape_value);
	}

	@Override
	public String toString() {
		String valueType = "value";
		switch (this.shape_type) {
		case "circle":
			valueType = "radius";
			break;
		case "square":
			valueType = "side";
			break;
		}
		return "Shape {" +
	        "id=" + id +
	        ", type='" + this.shape_type + '\'' +
	        ", " + valueType + "'" + this.shape_value + '\'' +
	        ", area='" + this.shape_area + '\'' +
	        ", perimeter=" + this.shape_perimeter +
	        '}';
	}
}
