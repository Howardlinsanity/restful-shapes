package com.example.shapes;


import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;

@SpringBootTest
@AutoConfigureMockMvc
public class ShapeTests {
	
	@Autowired private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;
	
	private String url = "http://localhost:8080/";
	
	@Before
	public void initTests() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void createThenUpdateThenDeleteCircle() throws Exception {
		// Post new shape
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url + "shape")
				.content(asJsonString(new Shape("circle", 0)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(0))
				.andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        // Get new shape
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(0));
        
        // Put new shape
        Shape updatedCircle = new Shape("circle", 4);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put(url + "shape/" + id)
				.content(asJsonString(updatedCircle))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(updatedCircle.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedCircle.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedCircle.getPerimeter()));
        
        // Get once again
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(updatedCircle.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedCircle.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedCircle.getPerimeter()));
        
        // Delete the shape 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url + "shape/" + id))
        		.andExpect(status().isOk());
        
        // Get once again just to make sure its gone
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void createThenUpdateThenDeleteSquare() throws Exception {
		// Post new shape
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url + "shape")
				.content(asJsonString(new Shape("square", 0)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(0))
				.andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        // Get new shape
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(0));
        
        // Put new shape
        Shape updatedSquare = new Shape("square", 4);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put(url + "shape/" + id)
				.content(asJsonString(updatedSquare))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(updatedSquare.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedSquare.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedSquare.getPerimeter()));
        
        // Get once again
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(updatedSquare.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedSquare.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedSquare.getPerimeter()));
        
        // Delete the shape 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url + "shape/" + id))
        		.andExpect(status().isOk());
        
        // Get once again just to make sure its gone
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateFromSquareToCircle() throws Exception {
		// Post new shape
		Shape fresh = new Shape("square", 300);
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url + "shape")
				.content(asJsonString(fresh))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(fresh.getSide()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(fresh.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(fresh.getPerimeter()))
				.andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        // Put new shape
        Shape updatedCircle = new Shape("circle", 4);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put(url + "shape/" + id)
				.content(asJsonString(updatedCircle))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(updatedCircle.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedCircle.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedCircle.getPerimeter()));
        
        // Get once again
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(updatedCircle.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedCircle.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedCircle.getPerimeter()));
        
        // Delete the shape 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url + "shape/" + id))
        		.andExpect(status().isOk());
	}
	
	@Test
	public void updateFromCircleToSquare() throws Exception {
		// Post new shape
		Shape fresh = new Shape("circle", 4324);
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url + "shape")
				.content(asJsonString(fresh))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(fresh.getRadius()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(fresh.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(fresh.getPerimeter()))
				.andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        // Put new shape
        Shape updatedCircle = new Shape("square", 574328);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put(url + "shape/" + id)
				.content(asJsonString(updatedCircle))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(updatedCircle.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedCircle.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedCircle.getPerimeter()));
        
        // Get once again
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(updatedCircle.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedCircle.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedCircle.getPerimeter()));
        
        // Delete the shape 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url + "shape/" + id))
        		.andExpect(status().isOk());
	}
	
	@Test
	public void postTest() throws Exception {
		// Post new shape
		Shape fresh = new Shape("circle", 3);
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url + "shape")
				.content(asJsonString(fresh))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(fresh.getRadius()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(fresh.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(fresh.getPerimeter()))
				.andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        // Delete the shape 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url + "shape/" + id))
        		.andExpect(status().isOk());
	}
	
	@Test
	public void getTest() throws Exception {
		// Post new shape
		Shape fresh = new Shape("circle", 34);
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url + "shape")
				.content(asJsonString(fresh))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(fresh.getRadius()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(fresh.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(fresh.getPerimeter()))
				.andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        this.mockMvc.perform(MockMvcRequestBuilders.get(url + "shape/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(fresh.getRadius()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(fresh.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(fresh.getPerimeter()));
        
        // Delete the shape 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url + "shape/" + id))
        		.andExpect(status().isOk());
	}
	
	@Test
	public void putTest() throws Exception {
		// Post new shape
		Shape fresh = new Shape("circle", 1243);
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post(url + "shape")
				.content(asJsonString(fresh))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("circle"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.radius").value(fresh.getRadius()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(fresh.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(fresh.getPerimeter()))
				.andReturn();

        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        // Put new shape
        Shape updatedSquare = new Shape("square", 4);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put(url + "shape/" + id)
				.content(asJsonString(updatedSquare))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("square"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.side").value(updatedSquare.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.area").value(updatedSquare.getArea()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(updatedSquare.getPerimeter()));
        
        // Delete the shape 
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url + "shape/" + id))
        		.andExpect(status().isOk());
	}
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
