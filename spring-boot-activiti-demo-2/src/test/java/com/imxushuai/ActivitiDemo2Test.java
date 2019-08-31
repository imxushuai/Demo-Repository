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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDemo2Test {

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
     * 请假单流程执行，我事先给每个节点添加了任务负责人
     * 填写请假单：员工
     * 部门经理审批：部门经理
     * 总经理审批：总经理
     */
    @Test
    public void start() {
        // 开启流程
        ProcessInstance qingjia = runtimeService.startProcessInstanceByKey("qingjia");
        log.info("请假流程已开启：流程ID：[{}]", qingjia.getId());

        // 填写请假单(由于我这里确认只有一个任务，我直接使用singleResult，实际应为list)
        Task task = taskService.createTaskQuery().processDefinitionKey("qingjia").taskAssignee("员工").singleResult();
        if (task != null) {
            log.info("请填写请假单：任务ID：[{}]", task.getId());
            // 构造请假单参数
            Map<String, Object> params1 = new HashMap<>();
            // 请假人，请假天数，请假的开始日期，请假原因
            params1.put("name", "xushuai");
            // 修改num参数的值是否小于3 可以控制流程的走向
//            params1.put("num", 2);
            params1.put("num", 5);
            params1.put("date", new Date());
            params1.put("remark", "回家吃饭");

            // 提交请假单
            taskService.complete(task.getId(), params1);
            log.info("请假单填写完毕，请假单内容：{}", params1.toString());
        }

        // 查询部门经理任务
        task = taskService.createTaskQuery().processDefinitionKey("qingjia").taskAssignee("部门经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请部门经理审批请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("部门经理审核通过");
        }

        // 查询总经理任务
        task = taskService.createTaskQuery().processDefinitionKey("qingjia").taskAssignee("总经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请总经理审核请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("总经理审核通过");
        } else {
            log.info("总经理暂无任务");
        }

    }


    /**
     * 排他网关请假流程
     * 填写请假单：员工
     * 部门经理审批：部门经理
     * 总经理审批：总经理
     */
    @Test
    public void testExclusiveGateway() {
        // 开启流程
        ProcessInstance qingjia = runtimeService.startProcessInstanceByKey("exclusiveGateway_qingjia");
        log.info("请假流程已开启：流程ID：[{}]", qingjia.getId());

        // 填写请假单(由于我这里确认只有一个任务，我直接使用singleResult，实际应为list)
        Task task = taskService.createTaskQuery().processDefinitionKey("exclusiveGateway_qingjia").taskAssignee("员工").singleResult();
        if (task != null) {
            log.info("请填写请假单：任务ID：[{}]", task.getId());
            // 构造请假单参数
            Map<String, Object> params1 = new HashMap<>();
            // 请假人，请假天数，请假的开始日期，请假原因
            params1.put("name", "xushuai");
            // 修改num参数的值是否小于3 可以控制流程的走向
            params1.put("num", 2);
//            params1.put("num", 5);
            params1.put("date", new Date());
            params1.put("remark", "回家吃饭");

            // 提交请假单
            taskService.complete(task.getId(), params1);
            log.info("请假单填写完毕，请假单内容：{}", params1.toString());
        }

        // 查询部门经理任务
        task = taskService.createTaskQuery().processDefinitionKey("exclusiveGateway_qingjia").taskAssignee("部门经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请部门经理审批请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("部门经理审核通过");
        }

        // 查询总经理任务
        task = taskService.createTaskQuery().processDefinitionKey("exclusiveGateway_qingjia").taskAssignee("总经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请总经理审核请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("总经理审核通过");
        } else {
            log.info("总经理暂无任务");
        }

    }

    /**
     * 并行网关请假流程
     * 填写请假单：员工
     * 部门经理审批：部门经理
     * 总经理审批：总经理
     * 人事审核：人事
     * 人事存档：人事
     * 行政考勤：行政
     */
    @Test
    public void testParallelGateway() {
        // 开启流程
        ProcessInstance qingjia = runtimeService.startProcessInstanceByKey("parallelGateway_qingjia");
        log.info("请假流程已开启：流程ID：[{}]", qingjia.getId());

        // 填写请假单(由于我这里确认只有一个任务，我直接使用singleResult，实际应为list)
        Task task = taskService.createTaskQuery().processDefinitionKey("parallelGateway_qingjia").taskAssignee("员工").singleResult();
        if (task != null) {
            log.info("请填写请假单：任务ID：[{}]", task.getId());
            // 构造请假单参数
            Map<String, Object> params1 = new HashMap<>();
            // 请假人，请假天数，请假的开始日期，请假原因
            params1.put("name", "xushuai");
            // 修改num参数的值是否小于3 可以控制流程的走向
//            params1.put("num", 2);
            params1.put("num", 5);
            params1.put("date", new Date());
            params1.put("remark", "回家吃饭");

            // 提交请假单
            taskService.complete(task.getId(), params1);
            log.info("请假单填写完毕，请假单内容：{}", params1.toString());
        }

        // 查询部门经理任务
        task = taskService.createTaskQuery().processDefinitionKey("parallelGateway_qingjia").taskAssignee("部门经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请部门经理审批请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("部门经理审核通过");
        }

        // 查询总经理任务
        task = taskService.createTaskQuery().processDefinitionKey("parallelGateway_qingjia").taskAssignee("总经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请总经理审核请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("总经理审核通过");
        } else {
            log.info("总经理暂无任务");
        }

        // 查询人事任务
        task = taskService.createTaskQuery().processDefinitionKey("parallelGateway_qingjia").taskAssignee("人事").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("人事审核，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("人事审核通过");
        }

        // 查询人事任务
        task = taskService.createTaskQuery().processDefinitionKey("parallelGateway_qingjia").taskAssignee("人事").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("人事存档，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("人事存档完毕");
        }

        // 查询行政任务
        task = taskService.createTaskQuery().processDefinitionKey("parallelGateway_qingjia").taskAssignee("行政").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("行政考勤，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("行政考勤记录完毕");
        }

    }

    /**
     * 包含网关请假流程
     * 填写请假单：员工
     * 部门经理审批：部门经理
     * 总经理审批：总经理
     * 人事存档：人事
     * 行政考勤：行政
     */
    @Test
    public void testInclusiveGateway() {
        // 开启流程
        ProcessInstance qingjia = runtimeService.startProcessInstanceByKey("inclusiveGateway_qingjia");
        log.info("请假流程已开启：流程ID：[{}]", qingjia.getId());

        // 填写请假单(由于我这里确认只有一个任务，我直接使用singleResult，实际应为list)
        Task task = taskService.createTaskQuery().processDefinitionKey("inclusiveGateway_qingjia").taskAssignee("员工").singleResult();
        if (task != null) {
            log.info("请填写请假单：任务ID：[{}]", task.getId());
            // 构造请假单参数
            Map<String, Object> params1 = new HashMap<>();
            // 请假人，请假天数，请假的开始日期，请假原因
            params1.put("name", "xushuai");
            // 修改num参数的值是否小于3 可以控制流程的走向
            params1.put("num", 2);
//            params1.put("num", 5);
            params1.put("date", new Date());
            params1.put("remark", "回家吃饭");

            // 提交请假单
            taskService.complete(task.getId(), params1);
            log.info("请假单填写完毕，请假单内容：{}", params1.toString());
        }

        // 查询部门经理任务
        task = taskService.createTaskQuery().processDefinitionKey("inclusiveGateway_qingjia").taskAssignee("部门经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请部门经理审批请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("部门经理审核通过");
        }

        // 查询总经理任务
        task = taskService.createTaskQuery().processDefinitionKey("inclusiveGateway_qingjia").taskAssignee("总经理").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("请总经理审核请假单，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("总经理审核通过");
        } else {
            log.info("总经理暂无任务");
        }

        // 查询人事任务
        task = taskService.createTaskQuery().processDefinitionKey("inclusiveGateway_qingjia").taskAssignee("人事").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("人事审核，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("人事审核通过");
        }


        // 查询行政任务
        task = taskService.createTaskQuery().processDefinitionKey("inclusiveGateway_qingjia").taskAssignee("行政").singleResult();
        if (task != null) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            log.info("行政考勤，请假人:[{}], 请假天数:[{}]天", variables.get("name"), variables.get("num"));

            taskService.complete(task.getId());
            log.info("行政考勤记录完毕");
        }

    }



}
