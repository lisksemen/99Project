package com.inc.tarik.project99.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"notEmpty"})
public class RowDTO {
    public static final RowDTO EMPTY = new RowDTO();

    @JsonProperty("ID")
    private Integer id;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("FACULTY")
    private Faculty faculty;
    @JsonProperty("KPI DIPLOMA")
    private String kpiDiploma;
    @JsonProperty("STATE DIPLOMA")
    private String stateDiploma;
    @JsonProperty("PROTOCOL NUMBER")
    private String protocolNum;
    @JsonProperty("KPI DIPLOMA YEAR")
    private Year kpiDiplomaYear;
    @JsonProperty("STATE DIPLOMA YEAR")
    private Year stateDiplomaYear;

    public boolean isNotEmpty() {
        return this != EMPTY;
    }
}
