package com.example.shapes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
class ShapeController {
	
	/**
	 * TODO:
	 * - /shape : Should receive a body request to create a shape
	 * - /shape/{id} : Should receive a body request to update an existing shape
	 * - /shape/{id} : Should delete an existing shape whose id equals {id};
	 * - /shapes : Should return JSON containing a list of all shapes created
	 * - /shape/{id} : Should return a JSON describing the Shape whose id equals {id};
	 * - /shape/area/{id} : Should return the calculated area for the shape 
	 * - /shape/perimeter/{id} : Should return the calculated perimeter for the shape
	 * 
	 */
	private final ShapeRepository repository;
	
	private ObjectMapper mapper;

	ShapeController(ShapeRepository repository) {
		this.repository = repository;
		this.mapper = new ObjectMapper();
	}

	/*********************************************************************
	* /shapes : Should return JSON containing a list of all shapes created
	**********************************************************************/
	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/shapes")
	List<ObjectNode> all() {
		// Note: This is very inefficient both space and timewise once we have more shapes stored in the database. An idea to maybe
		// speed this up is to override findAll() in the repository interface and find a way to make it return a list of objects instead, 
		// OR somehow cause returning shapes in the responses to omit data better somehow without needing shapeToObjectNode();
		List<Shape> shapes = repository.findAll();
		List<ObjectNode> objects = new ArrayList<>();
		for (Shape shape : shapes )
		{
			objects.add(shapeToObjectNode(shape));
		}
		return objects;
	}
	// end::get-aggregate-root[]
	

	/*********************************************************************
	* /shape : Should receive a body request to create a shape
	**********************************************************************/
	@PostMapping("/shape")
	ObjectNode newShape(@RequestBody Shape newShape) {
		// Currently, this will run even if the user gives mismatched value and type, like {type:"circle", side:"2"};, 
		// One way we can fix this issue is if we can figure out a way to implement proper polymorphism for shapes
		// (as that will also allow ease of adding new shapes in future) and make sure we check if certain values are there
		// e.g. Circle class will have no "side" member, its default radius is -1 and we check if that radius is unset and thus
		// return error. If the user puts multiple values (e.g. "circle", side:"2", radius:"2") then we may want to consider doing 
		// raw request scan to count parameters IF we want to throw error in that case
		return shapeToObjectNode(repository.save(newShape));
	}
	
	
	/*********************************************************************
	 * /shape/{id} : /shape/{id} : Should receive a body request to update an existing shape
	 *********************************************************************/
	@PutMapping("/shape/{id}")
	ObjectNode replaceShape(@RequestBody Shape newShape, @PathVariable Long id) {
		// See note in newShape, as that logic also applies here
		return shapeToObjectNode(repository.findById(id).map(Shape -> {
			Shape.setType(newShape.getType());
			Shape.setValue(newShape.getValue());
			Shape.calculateArea();
			Shape.calculatePerimeter();
			return repository.save(Shape);
		}).orElseGet(() -> {
			newShape.setId(id);
			return repository.save(newShape);
		}));
	}
	

	/*********************************************************************
	* /shape/{id} : Should delete an existing shape whose id equals {id};
	**********************************************************************/
	@DeleteMapping("/shape/{id}")
	void deleteShape(@PathVariable Long id) {
		repository.deleteById(id);
	}


	/*********************************************************************
	* /shape/{id} : Should return a JSON describing the Shape whose id equals {id};
	**********************************************************************/
	@GetMapping("/shape/{id}")
	ObjectNode getShape(@PathVariable Long id) {

		return shapeToObjectNode(repository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id)));
	}
	
	
	/*********************************************************************
	* /shape/area/{id} : Should return the calculated area for the shape 
	**********************************************************************/
	@GetMapping("/shape/area/{id}")
	double getArea(@PathVariable Long id) {
		Shape retrievedShape = repository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		return retrievedShape.getArea();
	}
	
	
	/*********************************************************************
	* /shape/perimeter/{id} : Should return the calculated perimeter for the shape
	**********************************************************************/
	@GetMapping("/shape/perimeter/{id}")
	double getPerimeter(@PathVariable Long id) {
		Shape retrievedShape = repository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
		return retrievedShape.getPerimeter();
	}
	

	/*********************************************************************
	* Used to clean up the response to user
	**********************************************************************/
	private ObjectNode shapeToObjectNode(Shape shape)
	{
		ObjectNode objectNode = mapper.createObjectNode();
		if (shape != null)
		{
			objectNode.put("id", shape.getId());
			objectNode.put("type", shape.getType());
			switch (shape.getType())
			{
				case "circle":
					objectNode.put("radius", shape.getValue());
					break;
				case "square":
					objectNode.put("side", shape.getValue());
					break;
			}
			double area = shape.getArea();
			double perimeter = shape.getPerimeter();
			if (area >= 0)
			{
				objectNode.put("area", area);
			}
			if (perimeter >= 0)
			{
				objectNode.put("perimeter", perimeter);
			}
		}
		else
		{
			objectNode.put("error", "shape is null - cannot be converted to object node"); 
		}
		return objectNode;
	}
}
