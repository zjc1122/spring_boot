package cn.zjc.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum PackageType {


    PARENT_PACKAGE("便捷性", 0),

    CHILD_PACKAGE("价格", 1);

    private String desc;
    private Integer code;

    PackageType(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private static final Map<Integer, PackageType> map = Maps.newHashMap();

    static {

        for (PackageType packageType : values()) {
            map.put(packageType.getCode(), packageType);
        }
    }

    public static PackageType index(Integer code) {
        return map.get(code);
    }

}
