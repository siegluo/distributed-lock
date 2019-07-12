
1.0.1版本

**功能：**

- 可以通过在方法上添加 *@Lock* 这个注解来使用分布式锁，可以减少开发成本.
- 同时支持读写锁 *@ReadLock*  *@WriteLock*
- 目前支持zookeeper redis 两种类型，可以引入相应的依赖直接使用

**使用方法：**

在你需要加分布式锁的方法上加上@Lock value为锁的path，支持spel表达式，默认为方法名

**依赖：**  

- 可以直接引入springboot的starter，无需配置，可以快速开发
    Redis：
    ```
    <dependency>
      <groupId>com.github.rogerjobluo</groupId>
      <artifactId>distributed-lock-redis</artifactId>
      <version>1.0.0-SNAPSHOT</version>
     </dependency>
    ```
 
    zookeeper:

    ```
       <dependency>
         <groupId>com.github.rogerjobluo</groupId>
         <artifactId>distributed-lock-zookeeper</artifactId>
         <version>1.0.0-SNAPSHOT</version>
        </dependency>
    ```
- redis锁主要是对高性能分布式锁redisson的封装
- zookeeper锁主要是使用的apache的curator