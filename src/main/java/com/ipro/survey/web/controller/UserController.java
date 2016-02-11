package com.ipro.survey.web.controller;

import com.ipro.survey.exception.UserException;
import com.ipro.survey.service.UserService;
import com.ipro.survey.web.vo.JsonResult;
import com.ipro.survey.web.vo.user.UserDetailVO;
import com.ipro.survey.web.vo.user.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by weiqiang.yuan on 15/12/5 15:37.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @RequestMapping(value = { "/createUser" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JsonResult createUser(UserVO userVO) {

        try {
            userService.insertUser(userVO);
            return JsonResult.successJsonResult();
        } catch (UserException e) {
            logger.error("创建用户发生异常", e);
            return JsonResult.failureJsonResult("创建用户发生异常");
        } catch (Throwable e) {
            logger.error("创建用户发生未知异常", e);
            return JsonResult.failureJsonResult("创建用户发生未知异常");
        }
    }

    @RequestMapping(value = { "/showUserDetail" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JsonResult showUserDetail(String account, String nickName) {

        try {
            userService.createUserIfNotExist(account, nickName);
            UserDetailVO userDetailVO = userService.showUserDetail(account);
            return JsonResult.successJsonResult(userDetailVO);
        } catch (UserException e) {
            logger.error("获取用户详情发生异常", e);
            return JsonResult.failureJsonResult(e.getMessage());
        } catch (Throwable e) {
            logger.error("获取用户详情未知异常", e);
            return JsonResult.failureJsonResult("获取用户详情未知异常");
        }
    }


}
