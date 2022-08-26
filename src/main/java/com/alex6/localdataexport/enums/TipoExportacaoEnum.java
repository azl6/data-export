package com.alex6.localdataexport.enums;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum TipoExportacaoEnum {

    ODS(1, "ODS"),
    XLSX(2, "XLSX");

    private Integer code;
    private String desc;
}
