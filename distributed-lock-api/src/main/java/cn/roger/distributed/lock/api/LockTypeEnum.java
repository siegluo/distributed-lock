package cn.roger.distributed.lock.api;

public enum LockTypeEnum {

    /**
     * 通过全类名，方法名获得锁，方法级别锁，为默认值
     */
    METHOD("METHOD", "方法名"),

    /**
     * 参数类型的锁，需要注解中声明参数位置
     */
    PARAMETER("PARAMETER", "参数"),

    /**
     * 自定义方法类型的锁，需要在同一个类中声明自定义获得锁的方法名，而且方法需要返回一个String
     */
    CUSTOM("CUSTOM", "自定义方法"),

    /**
     * 请求级别类型的锁，需要配置一个filter
     */
    REQUEST("REQUEST", "请求级别");

    LockTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    LockTypeEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

    private final String code;
    private final String desp;

}
