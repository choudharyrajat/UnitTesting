package com.gl.CompleteUT.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gl.CompleteUT.model.CloudVendor;
import com.gl.CompleteUT.service.CloudVendorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CloudVendorController.class)
class CloudVendorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CloudVendorService cloudVendorService;
    @InjectMocks
    private CloudVendorController cloudVendorController;
    CloudVendor cloudVendorOne;
    CloudVendor cloudVendorTwo;
    CloudVendor cloudVendorTwo1;
    List<CloudVendor> cloudVendorList= new ArrayList<>();

    @BeforeEach
    void setUp() {
        cloudVendorOne = new CloudVendor("1", "Amazon",
                "USA", "xxxxx");
        cloudVendorTwo = new CloudVendor("2", "GCP",
                "UK", "yyyyy");
        cloudVendorTwo1 = new CloudVendor("3", "Gdf",
                "UKdf", "yyyyy");
        cloudVendorList.add(cloudVendorOne);
        cloudVendorList.add(cloudVendorTwo);
        cloudVendorList.add(cloudVendorTwo1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCloudVendorDetails() throws Exception {
        when(cloudVendorService.getCloudVendor("1")).thenReturn(cloudVendorOne);
        this.mockMvc.perform(get("/cloudvendor/" + "1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAllCloudVendorDetails() throws  Exception {
        when(cloudVendorService.getAllCloudVendors()).thenReturn(cloudVendorList);
        this.mockMvc.perform(get("/cloudvendor"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createCloudVendorDetails() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        //ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        //String requestJson=ow.writeValueAsString(cloudVendorOne);
        String requestJson=mapper.writeValueAsString(cloudVendorOne);

        when(cloudVendorService.createCloudVendor(cloudVendorOne)).thenReturn("Success");
        this.mockMvc.perform(post("/cloudvendor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateCloudVendorDetails() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cloudVendorOne);

        when(cloudVendorService.updateCloudVendor(cloudVendorOne))
                .thenReturn("Cloud Vendor Updated Successfully");
        this.mockMvc.perform(put("/cloudvendor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteCloudVendorDetails() throws Exception {
        when(cloudVendorService.deleteCloudVendor("1"))
                .thenReturn("Cloud Vendor Deleted Successfully");
        this.mockMvc.perform(delete("/cloudvendor/" + "1"))
                .andDo(print()).andExpect(status().isOk());

    }
}