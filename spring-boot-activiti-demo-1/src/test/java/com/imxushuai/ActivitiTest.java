package com.imxushuai;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {

    @Autowired
    private ProcessEngine processEngine;

    private RuntimeService runtimeService;
    private TaskService taskService;
    private RepositoryService repositoryService;
    private HistoryService historyService;

    @Before
    public void init() {
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        repositoryService = processEngine.getRepositoryService();
        historyService = processEngine.getHistoryService();
    }

    /**
     * 创建流程实例，会在下列表中生成数据
     * 1. act_hi_actinst：会在该表中生成已经执行完毕和当前正在执行的节点的记录
     * 2. act_hi_identitylink：记录流程实例的参与者
     * 3. act_hi_proinst：流程实例的基本信息
     * 4. act_hi_taskinst：当前待执行节点
     * 5. act_ru_execution：当前正在执行的节点
     * 6. act_ru_identitylink：记录流程实例的当前节点的参与者
     * 7. act_ru_task：记录流程实例的当前节点的信息
     */
    @Test
    public void start() {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("qingjia", new HashMap<>());
        log.info("流程实例初始化成功：[{}]", instance.getId());
    }

    /**
     * 填写请假单
     */
    @Test
    public void apply1() {
        // 填写请假单,通过上一次打印的ID获取
        Task task = taskService.createTaskQuery().processInstanceId("e0e3e681-c80e-11e9-bf76-005056c00001").singleResult();
        // 构造请假参数
        Map<String, Object> params = new HashMap<>();
        params.put("dayNum", 1);
        params.put("remark", "我妈叫我回家吃饭");
        taskService.complete(task.getId(), params);
    }

    /**
     * 审批流程
     */
    @Test
    public void apply2() {
        // 部门经理审批
        Task task = taskService.createTaskQuery().processInstanceId("e0e3e681-c80e-11e9-bf76-005056c00001").singleResult();
        // 可以使用setAssignee设置下一个节点的受理人
        // task.setAssignee("imxushuai");
        Map<String, Object> params = taskService.getVariables(task.getId());
        log.info(params.toString());
        taskService.complete(task.getId());

        // 总经理审批
//        List<Task> list = taskService.createTaskQuery()
//                .processDefinitionKey("qingjia")// 查询请假流程
//                .taskAssignee("imxushuai")// 查询 imxushuai 当前代办的任务
//                .list();
        params = new HashMap<>();
        params.put("remark", "批准，注意安全");
        taskService.complete(task.getId(), params);
    }

}
