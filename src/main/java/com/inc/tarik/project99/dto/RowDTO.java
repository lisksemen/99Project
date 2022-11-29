package com.inc.tarik.project99.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowDTO {
    public static final RowDTO EMPTY = new RowDTO();

    private Integer id;
    private String name;
    private Faculty faculty;
    private String kpiDiploma;
    private String stateDiploma;
    private String protocolNum;
    private Year kpiDiplomaYear;
    private Year stateDiplomaYear;

    public boolean isNotEmpty() {
        return this != EMPTY;
    }
}
