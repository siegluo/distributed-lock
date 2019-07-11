package cn.roger.distributed.lock.core.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jie.luo
 */
public class ExpressionUtils {
    private static final Map<String, Expression> CACHE = new ConcurrentHashMap<>();

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    public static <T> T analytical(String expressionString, Map<String, Object> argMap, Class<T> desiredResultType) {
        Expression expression = CACHE.get(expressionString);
        StandardEvaluationContext context = new StandardEvaluationContext(argMap);
        for (Map.Entry<String, Object> entry : argMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        if (expression == null) {
            expression = PARSER.parseExpression(expressionString);
            CACHE.putIfAbsent(expressionString, expression);
        }
        return expression.getValue(context, desiredResultType);
    }
}
