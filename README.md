
1.0.1版本

功能：

可以通过在方法上添加 *@DisLock* 这个注解来使用分布式锁，可以减少开发成本.

使用方法

在你需要加分布式锁的方法上加上@DisLock

@DisLock目前支持三个参数lockMethod   failMethod   finshedMethod

可以分别赋值你 获得锁  加锁失败 释放锁 的方法名 

要求方法都在同一个类中，且，方法参数与被加锁的方法一致

maven： 

```
<dependency>
   <groupId>com.github.rogerjobluo</groupId>
   <artifactId>distributed-lock</artifactId>
   <version>1.0.1-SNAPSHOT</version>
   <type>pom</type>
 </dependency>
```

gradle

```
dependency("com.github.rogerjobluo:distributed-lock:1.0.1-SNAPSHOT")
compile('com.github.rogerjobluo:distributed-lock')
```

配置

由于初版目前依赖于spring-integrated的lockRegistry，所以需要使用spring-integrated关于lockRegistry的配置，
额外的需要配置lockAspect disLockConfigurater springPostProcessor

xml

```
<bean id="disLockConfigurater" class="cn.roger.distributed.lock.spring.SpringDisLockConfigurater"
           init-method="init">
     <property name="lockRegistry" ref="lockRegistry"/>
</bean>
<bean id="lockAspect" class="cn.roger.distributed.lock.spring.LockAspect" init-method="init">
     <property name="disLockConfigurater" ref="disLockConfigurater"/>
</bean>
<bean id="springPostProcessor" class="com.github.rogerjobluo.distributed.lock.spring.SpringPostProcessor"/>

```

java：

```
@Bean
public LockAspect lockAspect(DisLockConfigurater disLockConfigurater) {
LockAspect lockAspect = new LockAspect();
lockAspect.setDisLockConfigurater(disLockConfigurater);
return lockAspect;
}

@Bean
public DisLockConfigurater disLockConfigurater(LockRegistry lockRegistry) {
DisLockConfigurater disLockConfigurater = new DisLockConfigurater();
disLockConfigurater.setLockRegistry(lockRegistry);
return disLockConfigurater;
}
@Bean
public SpringPostProcessor springPostProcessor(){
  return new SpringPostProcessor();
}
```
