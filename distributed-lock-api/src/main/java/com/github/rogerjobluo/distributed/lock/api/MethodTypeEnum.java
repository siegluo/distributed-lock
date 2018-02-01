package com.github.rogerjobluo.distributed.lock.api;

public enum MethodTypeEnum {

    /**
     *
     */
    FAILMETHOD("FAILMETHOD"),

    /**
     *
     */
    FINISHEDMETHOD("FINISHEDMETHOD"),

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
