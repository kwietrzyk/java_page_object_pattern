package pl.sii.annotations;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DisableConcurrentExecutionExtension implements ExecutionCondition {

    private static final Lock lock = new ReentrantLock();

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (context.getElement().isPresent() && context.getElement().get().isAnnotationPresent(DisableConcurrentExecution.class)) {
            lock.lock();
            return ConditionEvaluationResult.enabled("Test is NOT running in parallel");
        } else {
            return ConditionEvaluationResult.enabled("Test is running in parallel");
        }
    }
}