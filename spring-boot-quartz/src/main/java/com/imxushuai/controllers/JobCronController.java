package com.imxushuai.controllers;

import com.imxushuai.entities.mysql.JobCron;
import com.imxushuai.services.JobCronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Product controller.
 */
@Controller
public class JobCronController {

    @Autowired
    private JobCronService jobCronService;

    /**
     * 获取cron表达式列表
     */
    @RequestMapping(value = "/jobCrons", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("jobCrons", jobCronService.listAllJobCron());
        return "crons";
    }

    /**
     * 查询指定id的cron表达式
     */
    @RequestMapping("jobCron/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        model.addAttribute("jobCron", jobCronService.getJobCronById(id));
        return "cronshow";
    }

    /**
     * 修改指定id的cron表达式
     */
    @RequestMapping("jobCron/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("jobCron", jobCronService.getJobCronById(id));
        return "cronform";
    }

    /**
     * 新建cron表达式
     */
    @RequestMapping("jobCron/new")
    public String newProduct(Model model) {
        model.addAttribute("jobCron", new JobCron());
        model.addAttribute("jobCrons", jobCronService.listAllJobCron());
        return "cronform";
    }

    /**
     * 修改启动的cron表达式
     */
    @RequestMapping(value = "jobCron", method = RequestMethod.POST)
    public String saveProduct(JobCron jobCron) {
        // 修改其他的cron为禁用状态
        jobCronService.disable();
        JobCron cron = jobCronService.getJobCronById(jobCron.getId());
        if (cron != null) {
            cron.setCurrent("1");
            jobCronService.saveJobCron(cron);
            return "redirect:/jobCrons";
        }
        return "redirect:/jobCrons";
    }

    /**
     * 删除cron表达式
     */
    @RequestMapping("jobCron/delete/{id}")
    public String delete(@PathVariable Long id) {
        jobCronService.deleteJobCron(id);
        return "redirect:/jobCrons";
    }

}
