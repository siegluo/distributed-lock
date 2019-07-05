/*
 *
 *  * Copyright 2016 http://www.hswebframework.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package cn.roger.distributed.lock.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * ThreadLocal 工具类,通过在ThreadLocal存储map信息,来实现在ThreadLocal中维护多个信息
 * <br>e.g.<code>
 * ThreadLocalUtils.put("key",value);<br>
 * ThreadLocalUtils.get("key");<br>
 * ThreadLocalUtils.remove("key");<br>
 * ThreadLocalUtils.getAndRemove("key");<br>
 * ThreadLocalUtils.get("key",()-&gt;defaultValue);<br>
 * ThreadLocalUtils.clear();<br>
 * </code>
 *
 */
@SuppressWarnings("unchecked")
public final class ThreadLocalUtils {

    private ThreadLocalUtils() {
    }

    private static final ThreadLocal<Map<String, Object>> local = ThreadLocal.withInitial(HashMap::new);

    /**
     * @return threadLocal中的全部值
     */
    public static Map<String, Object> getAll() {
        return local.get();
    }

    /**
     * 设置一个值到ThreadLocal
     *
     * @return 被放入的值
     */
    public static <T> T put(String key, T value) {
        local.get().put(key, value);
        return value;
    }

    /**
     * 删除参数对应的值
     */
    public static void remove(String key) {
        local.get().remove(key);
    }

    /**
     * 清空ThreadLocal
     *
     */
    public static void clear() {
        local.remove();
    }

    /**
     * 从ThreadLocal中获取值
     *
     * @param key 键
     * @param <T> 值泛型
     * @return 值, 不存在则返回null, 如果类型与泛型不一致, 可能抛出{@link ClassCastException}
     */
    public static <T> T get(String key) {
        return ((T) local.get().get(key));
    }

    /**
     * 从ThreadLocal中获取值,并指定一个当值不存在的提供者
     *
     */
    public static <T> T get(String key, Supplier<T> supplierOnNull) {
        return ((T) local.get().computeIfAbsent(key, k -> supplierOnNull.get()));
    }

    /**
     * 获取一个值后然后删除掉
     *
     * @param key 键
     * @param <T> 值类型
     * @return 值, 不存在则返回null
     */
    public static <T> T getAndRemove(String key) {
        try {
            return get(key);
        } finally {
            remove(key);
        }
    }

}
