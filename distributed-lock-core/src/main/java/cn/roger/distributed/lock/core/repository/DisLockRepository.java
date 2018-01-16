package cn.roger.distributed.lock.core.repository;

public interface DisLockRepository {

    /**
     * 新增path
     *
     * @param path path
     * @return 新增数量
     */
    int create(String path);

    /**
     * 更新事务
     *
     * @param path 事务
     * @return 更新数量
     */
    int update(String path);

    /**
     * 删除事务
     *
     * @param path 事务
     * @return 删除数量
     */
    int delete(String path);

}
