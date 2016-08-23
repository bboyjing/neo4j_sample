package cn.didadu.controller;

import cn.didadu.neo4jkernel.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by zhangjing on 16-8-23.
 */

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(path = "/threadA", method = RequestMethod.GET)
    public String treadA(){
        transactionService.threadA();
        return "ok";
    }

    @RequestMapping(path = "/threadB", method = RequestMethod.GET)
    public String treadB(){
        transactionService.threadB();
        return "ok";
    }

    @RequestMapping(path = "/readLock", method = RequestMethod.GET)
    public String readLock(){
        transactionService.readLock();
        return "ok";
    }

    @RequestMapping(path = "/writeWaitReadLock", method = RequestMethod.GET)
    public String writeWaitReadLock(){
        transactionService.writeWaitReadLock();
        return "ok";
    }
}
