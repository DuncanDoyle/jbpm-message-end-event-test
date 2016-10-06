package org.jboss.ddoyle.jbpm.endevent;

import org.jbpm.bpmn2.handler.SendTaskHandler;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

public class MessageEndEventPpiTest extends JbpmJUnitBaseTestCase {
	
	private RuntimeManager runtimeManager;

	private RuntimeEngine runtimeEngine;

	private KieSession kieSession;

	public MessageEndEventPpiTest() {
		// Setup the datasource and enable persistence.
		super(true, true);
	}

	@Before
	public void init() {
		runtimeManager = createPpiRuntimeManager("test-process.bpmn2");
		runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get());
		kieSession = runtimeEngine.getKieSession();
		kieSession.getWorkItemManager().registerWorkItemHandler("Send Task", new SendTaskHandler());
	}

    protected RuntimeManager createPpiRuntimeManager(String... process) {
    	return createRuntimeManager(Strategy.PROCESS_INSTANCE, null, process);
    }
	
	@After
	public void destroy() {
		runtimeManager.disposeRuntimeEngine(runtimeEngine);
		runtimeManager.close();
	}

	@Test
	public void testExecuteProcess() {
		ProcessInstance pInstance = kieSession.startProcess("test-process");
	}

}
