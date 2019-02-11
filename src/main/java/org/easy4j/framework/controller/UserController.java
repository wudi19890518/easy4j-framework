package org.easy4j.framework.controller;

import org.easy4j.framework.annotation.Controller;
import org.easy4j.framework.annotation.RequestMapping;
import org.easy4j.framework.bean.RequestMethod;

@Controller
@RequestMapping
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String list(){
        return null;
    }
}
