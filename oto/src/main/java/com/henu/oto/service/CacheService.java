package com.henu.oto.service;

public interface CacheService {
    /**
     * 依据key前缀删除匹配该模式下的所有k-v
     * 如传入shopcategory,则以shopcategory打头的k-v都会被清空
     * @param keyPreFix
     */
    void removeFromCache(String keyPreFix);
}
