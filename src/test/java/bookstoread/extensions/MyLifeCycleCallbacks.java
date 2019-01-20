package bookstoread.extensions;

import java.util.Collections;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

// TODO p.124-6 - extensions, test life cycle callbacks
public class MyLifeCycleCallbacks
		implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback, AfterTestExecutionCallback {

	private Store getStore(ExtensionContext context) {
		return context.getStore(Namespace.GLOBAL);
	}

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		getStore(context).put("TEST_CLASS", System.currentTimeMillis());
	}

	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		getStore(context).put("TEST", System.currentTimeMillis());
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		long startTime = getStore(context).get("TEST", long.class);
		long timeTook = System.currentTimeMillis() - startTime;
		context.publishReportEntry(Collections.singletonMap("Summary",
				String.format("%s took %d ms", context.getDisplayName(), timeTook)));
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		long startTime = getStore(context).get("TEST_CLASS", long.class);
		long timeTook = System.currentTimeMillis() - startTime;
		context.publishReportEntry(Collections.singletonMap("Summary",
				String.format("%s took %d ms", context.getDisplayName(), timeTook)));
	}
}