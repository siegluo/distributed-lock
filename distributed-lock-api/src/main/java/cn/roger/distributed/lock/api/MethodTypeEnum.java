package cn.roger.distributed.lock.api;

public enum MethodTypeEnum {

    /**
     *
     */
    FAILMETHOD("FAILMETHOD"),

    /**
     *
     */
    FINSHEDMETHOD("FINSHEDMETHOD"),

    /**
     *
     */
    LOCKMETHOD("LOCKMETHOD");


    MethodTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private final String code;

}
