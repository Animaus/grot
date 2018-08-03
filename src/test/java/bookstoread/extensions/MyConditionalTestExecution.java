package bookstoread.extensions;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

//TODO p.127 - extensions, conditional test execution
public class MyConditionalTestExecution implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext arg0) {
		String jenkinsHome = System.getenv("JENKINS_HOME");
		if (jenkinsHome == null) {
			return ConditionEvaluationResult.disabled("Test disabled: not on Jenkins");
		} else {
			return ConditionEvaluationResult.enabled("Test enabled: on Jenkins");
		}
	}

}
