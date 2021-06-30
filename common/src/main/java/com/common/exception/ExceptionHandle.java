package com.common.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * @Author json
 * @Description 拦截的异常处理
 * @Date 2018/4/7
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger LOGGER = LogManager.getLogger(ExceptionHandle.class);

    /**
     * @Author ABin
     * @Description 拦截方法参数校验异常
     * @DateTime 2018/5/9 13:21
     * @param e
     * @return com.common.exception.Result
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public Result validException(Exception e) {
        LOGGER.error("【参数校验异常】:", e);
        List<ObjectError> errors = null;
        if(e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            errors = exception.getBindingResult().getAllErrors();
        } else {
            BindException exception = (BindException) e;
            errors = exception.getBindingResult().getAllErrors();
        }
        StringBuffer errorMsg = new StringBuffer();
//        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        for(ObjectError err : errors ) {
            if(err.getDefaultMessage().contains("Failed to convert")) {
                errorMsg.append("参数格式错误").append(";");
            } else {
                errorMsg.append(err.getDefaultMessage()).append(";");
            }
        }
        return ResultUtil.error(-1, errorMsg.toString());
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    @ResponseBody
    public Result duplicateKeyException(DuplicateKeyException exception){
        LOGGER.error("已存在该记录", exception);
        return ResultUtil.error(-1, "已存在该记录");
    }

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionGet(Exception e, HttpServletRequest request, HttpServletResponse response){
        if(e instanceof DescribeException){
            LOGGER.error("【自定义异常抛出】"+e);
            DescribeException myException = (DescribeException) e;
            return ResultUtil.error(myException.getCode(),myException.getMessage());
        }else if(e instanceof ErrorPageException){
            PrintWriter writer = null;
            try {
                response.setContentType("text/html;charset=UTF-8");
                writer = response.getWriter();
                writer.print("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>光头强pay</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>"+e.getMessage()+"</h1>\n" +
                        "</body>\n" +
                        "</html>");
            } catch (IOException e1) {
                LOGGER.error("页面抛出异常"+e);
            } finally {
                writer.close();
            }
            return null;
        }else if(e instanceof PageJumpException){
            try {
                response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+"/static/image/code.html");
            } catch (IOException e1) {
                LOGGER.error("页面跳转异常"+e1);
            }
            return null;
        }
        LOGGER.error("【系统异常】{}",e);
        if(!StringUtils.isEmpty(e.getMessage()) && e.getMessage().contains("Failed to convert")) {
            return ResultUtil.error(-1, "参数格式错误");
        } else {
            return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
        }
    }
}
