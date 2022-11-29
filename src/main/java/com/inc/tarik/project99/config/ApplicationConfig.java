package com.inc.tarik.project99.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CsvMapper getCsvMapper() {
        CsvMapper mapper = new CsvMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public CsvSchema getCsvSchema() {
        return CsvSchema.builder().setUseHeader(true)
                .addColumn("ID")
                .addColumn("NAME")
                .addColumn("FACULTY")
                .addColumn("KPI DIPLOMA")
                .addColumn("STATE DIPLOMA")
                .addColumn("PROTOCOL NUMBER")
                .addColumn("KPI DIPLOMA YEAR")
                .addColumn("STATE DIPLOMA YEAR")
                .build();
    }

}
