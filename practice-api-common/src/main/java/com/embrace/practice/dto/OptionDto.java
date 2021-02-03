package com.embrace.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.util.Map;

/**
 * @author embrace
 * @describe  用于封装前端下拉类的dto
 * @date created in 2020/12/20 0:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto implements Serializable {

    private String key;

    private String value;

    public OptionDto(Map<String,Object> map, String key, String value) {
        this.key = String.valueOf(map.get(key));
        this.value = String.valueOf(map.get(key));
    }
}
