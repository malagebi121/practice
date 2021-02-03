package com.embrace.practice.enums;


import com.embrace.practice.dto.OptionDto;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author embrace
 * @describe
 * @date created in 2020/12/19 23:59
 */
public enum CountryEnum {
    ONE(1,"齐国"),
    TWO(2,"楚国"),
    TREE(3,"燕国"),
    FOUR(4,"赵国"),
    FIVE(5,"魏国"),
    SIX(6,"齐国");

    @Getter
    private Integer key;

    @Getter
    private String  value;


    CountryEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     *  key 获取单个
     * @param key
     * @return
     */
    public static CountryEnum  getEnumByKey(Integer key){
        return Arrays.stream(values()).filter(v -> v.getKey() == key).findFirst().orElse(null);
    }

    /**
     *   key & v.getKey()  用于获取多个国家的集合
     * @param key
     * @return
     */
    public static CountryEnum[] valueOfs(int key) {
        return Arrays.stream(values()).filter(v -> (key & v.getKey()) == v.getKey()).toArray(CountryEnum[]::new);
    }

    /**
     * 返回所有集合的List
     * @return
     */
    public static List<CountryEnum> valueOfList() {
        return Arrays.stream(values()).collect(toList());
    }

    /**
     * 返回所有下拉的List
     * @return
     */
    public static List<OptionDto> optionDtoList() {
        return Arrays.stream(values()).map(v -> new OptionDto(v.getKey().toString(), v.getValue())).collect(toList());
    }

}
