package com.example.Wuzzuf_Data_Analysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class)
@AutoConfigureWebMvc
@ActiveProfiles({"test"})
public class WuzzufControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        System.out.println("From Setup");
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    public void TestStatisticsController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:statisticsData.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);
        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/PreliminaryStatistic").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);
        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(testExpected, testactual);
    }

    @Test
    public void testHeaderController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:headerData.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);

        ResultActions result =
                this.mockMvc.perform(get("/HeadOfTheDataSet").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemovaNullsController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:removeNullsData.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);
        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/RemovingNullValuesAndDuplicates").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);
        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(testExpected, testactual);
    }

    @Test
    public void testGetSchemaController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:DataSetSchemaStructure.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);
//        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/DataSetSchema").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);
//        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetCompanyOffersController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:getCompanyOffersData.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);
        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/CompanyOffers").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);
        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(testExpected, testactual);
    }

    @Test
    public void testGetMostPopJobsController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:MostPopJobs.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);
        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/MostPopularJobs").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);
        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(testExpected, testactual);
    }

    @Test
    public void testGetMostPopAreasController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:MostPopularAreas.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);
        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/MostPopularAreas").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);
        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(testExpected, testactual);
    }

    @Test
    public void testGetMostRepeatedSkillsController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:MostRepeatedSkills.json");
        Map expected = objectMapper.readValue(file, Map.class);
        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/MostRepeatedSkills").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        Map actual = objectMapper.readValue(res, Map.class);
        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(testExpected, testactual);
    }

    @Test
    public void testGetMgetDataAfterFactorizController() throws Exception {

        File file  = ResourceUtils.getFile("classpath:DataAfterFactorizeYearsExpFeature.json");
        List<Map> expected = objectMapper.readValue(file, ArrayList.class);
        String testExpected = expected.toString().substring(0,50);

        ResultActions result =
                this.mockMvc.perform(get("/DataAfterFactorizeYearsExpFeature").
                        contentType(MediaType.APPLICATION_JSON).
                        header("Content-Type", "application/json").
                        header("Accept", "application/json"))
                        .andDo(print());

        result.andExpect(status().isOk());
        String res = result.andReturn().getResponse().getContentAsString();
        List<Map> actual = objectMapper.readValue(res, ArrayList.class);
        String testactual = actual.toString().substring(0,50);

        Assert.assertEquals(testExpected, testactual);
    }
}
