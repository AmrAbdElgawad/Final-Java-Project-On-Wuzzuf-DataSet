package com.example.Wuzzuf_Data_Analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
public class Controller {

    @Autowired
    private Services services;

    @RequestMapping(value = "/PreliminaryStatistic", produces = "application/json")
    public List<Map> statistics ()  {
        return services.getStats();
    }

    @RequestMapping("/HeadOfTheDataSet")
    public List<Map> header ()  {
        return  services.getHeader();
    }

    @RequestMapping("/RemovingNullValuesAndDuplicates")
    public List<Map> RemovaNulls ()  {
        return services.cleanData();
    }

    @RequestMapping("/DataSetSchema")
    public List<Map> getSchema ()  {
        return services.getSchema();
    }

    @RequestMapping("/CompanyOffers")
    public List<Map> getCompanyOffers ()  {
        return services.getCompanyOffers();
    }

    @RequestMapping("/MostPopularJobs")
    public List<Map> getMostPopJobs ()  {
        return services.getMostPopJobs();
    }

    @RequestMapping("/MostPopularAreas")
    public List<Map> getMostPopAreas ()  {
        return services.getMostPopAreas();
    }

    @RequestMapping("/MostRepeatedSkills")
    public Map<String,Integer> getMostRepeatedSkills()  {
        return services.mostRepSkills();
    }

    @RequestMapping("/DataAfterFactorizeYearsExpFeature")
    public List<Map> getDataAfterFactoriz()  {
        return services.dataAfterFectorize();
    }

    @RequestMapping(value = "/CompanyOffersPieChart", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getCompanyOffersPieChart(HttpServletResponse response) throws IOException {

        services.PieChartPath();
        ClassPathResource imgFile = new ClassPathResource("companyOffers.jpg");

        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

    @RequestMapping(value = "/mostPopularJobsCategoryChart", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getMostPopJobsCatChart(HttpServletResponse response) throws IOException {

        services.jobsCategoryChart();
        ClassPathResource imgFile = new ClassPathResource("jobs.jpg");

        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

    @RequestMapping(value = "/mostPopularAreaCategoryChart", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getMostPopAreasCatChart(HttpServletResponse response) throws IOException {

        services.areaCategoryChart();
        ClassPathResource imgFile = new ClassPathResource("Areas.jpg");

        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }


}

