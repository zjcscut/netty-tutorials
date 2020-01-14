package club.throwable.server;

/**
 * @author throwable
 * @version v1.0
 * @description 方法选择器
 * @since 2020/1/12 11:37
 */
public interface MethodMatcher {

    /**
     * 查找一个匹配度最高的方法信息
     *
     * @param input input
     * @return output
     */
    MethodMatchOutput selectOneBestMatchMethod(MethodMatchInput input);
}
