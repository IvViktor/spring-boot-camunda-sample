package org.example.api;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("process")
public class SampleRestController {

    private final ProcessEngine processEngine;

    @Autowired
    public SampleRestController(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    @PostMapping("start")
    public void startProcessInstance(@Valid @RequestBody StartProcessRequestBody body) {
        VariableMap variableMap = Variables.putValue("initiator", body.getInitiator())
                .putValue("age", body.getAge());
        processEngine.getRuntimeService().startProcessInstanceByMessage("start_by_rest", "REST-started", variableMap);
    }

    @GetMapping
    public List<String> findProcesses(@RequestParam("initiator") String initiator) {
        List<ProcessInstance> processInstances = processEngine.getRuntimeService().createProcessInstanceQuery()
                .variableValueEquals("initiator", initiator)
                .list();
        return processInstances.stream().map(ProcessInstance::getProcessInstanceId).collect(Collectors.toList());
    }
}
