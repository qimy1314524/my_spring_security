/*
package cn.com.geovis.smartcity.aspect;

import cn.com.geovis.smartcity.Enum.EnumCode;
import cn.com.geovis.smartcity.api.base.BaseApi;
import cn.com.geovis.smartcity.entity.TraceRecord;
import cn.com.geovis.smartcity.entity.User;
import cn.com.geovis.smartcity.exception.MyException;
import cn.com.geovis.smartcity.mapper.UserMapper;
import cn.com.geovis.smartcity.service.TraceRecordService;
import cn.com.geovis.smartcity.utils.ResultUtil;
import cn.com.geovis.smartcity.utils.ServletUtils;
import com.alibaba.fastjson.JSONObject;
import com.langsin.my_spring_security.dao.mapper.UserMapper;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

*/
/**
 * @desc: Aspect切面类
 * @author: chengjz
 * @date: 2019/10/20
 *//*

@Aspect
@Component
public class HttpAspect extends BaseApi {

    private final static Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Autowired
    private TraceRecordService traceRecordService;
    @Autowired
    private UserMapper userMapper;

    @Pointcut("execution(public * cn.com.geovis.smartcity.api..*(..))")
    public void log() {

    }
    */
/**
     * @desc: 记录请求
     *
     * @author: chengjz
     * @date: 2019/04/26
     *//*

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        shiroFilter(joinPoint);
    }

    */
/**
     * @desc: 响应请求
     *
     * @author: chengjz
     * @date: 2019/04/26
     *//*

    @After("log()")
    public void doAfter() {
        log.info("========================== ↓响应请求↓ ==========================");
    }

    */
/**
     * @desc: 打印返回值
     *
     * @author: chengjz
     * @date: 2019/04/26
     *//*

    @AfterReturning(returning = "obj",pointcut = "log()")
    public void doAfterReturnning(Object obj) {
        log.info("请求返回值：{}",obj);
    }


    */
/**
     * @desc: 统一参数验证处理
     *
     * @author: chengjz
     * @date: 2019/04/26
     *//*

    @Around("execution(* cn.com.geovis.smartcity.api..*(..)) && args(..,bindingResult)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {

        shiroFilter(pjp);

        Object retVal;
        if (bindingResult.hasErrors()) {
            return ResultUtil.result(EnumCode.BAD_REQUEST.getValue(),bindingResult.getFieldError().getDefaultMessage(),null);
        } else {
            retVal = pjp.proceed();
        }
        return retVal;
    }

    */
/**
     * @desc: 请求拦截器
     *
     * @author: chengjz
     * @date: 2019/04/26
     *//*

    public void shiroFilter(JoinPoint joinPoint){

        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String requestUrl = request.getRequestURI().replaceAll(request.getContextPath(), "");
        String remoteAddr = request.getRemoteAddr();
        String method = request.getMethod();
        String args = Arrays.toString(joinPoint.getArgs());
        //String token=request.getHeader("token");
        String authorization = "";
        if(request.getHeader("Authorization") != null && !request.getHeader("Authorization").equals("")) {
            if(request.getHeader("Authorization").contains("Basic")) {
                authorization=request.getHeader("Authorization").split(" ")[1];
            } else {
                authorization=request.getHeader("Authorization");
            }

        }
        System.out.println(authorization);
        log.info("========================== ↓收到请求↓ ==========================");
        log.info("请求的token:{}",authorization);
        log.info("请求url:{}",requestUrl);
        log.info("请求源ip:{}",remoteAddr);
        log.info("请求方式:{}",method);
        // log.info("请求方法:{}",joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+ "()");
        log.info("请求参数:{}", args);
        log.info("请求参数备用参考:{}", params);
        log.info("getContextPath:{}",request.getContextPath());
        log.info("========================== ↑收到请求↑ ==========================");


        if(!"/api/login".equals(requestUrl) &&
            !"/api/loginOut".equals(requestUrl) &&
            !"/swagger-resources".equals(requestUrl) &&
            !"/v2/api-docs".equals(requestUrl) &&
            !"/webjars/springfox-swagger-ui/**".equals(requestUrl) &&
            !"/swagger-ui.html".equals(requestUrl)){
            String check = super.getUserId();
            if(authorization.equals("") || !authorization.equals(check)){
                throw new MyException(ResultUtil.result(EnumCode.TOKENERR.getValue(), EnumCode.TOKENERR.getText()));
            }
        }

        if("/api/login".equals(requestUrl) || requestUrl.contains("del")) {
            TraceRecord traceRecord = new TraceRecord();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date date=new java.util.Date();
            String datestr=sdf.format(date);
            if("/api/login".equals(requestUrl)) {
                List<User> list = userMapper.findUserInfo(args.substring(1,args.length()-1).split(",")[0].trim());
                if(list.size() > 0) {
                    if(!(args.substring(1,args.length()-1).split(",")[1].trim()).equals(
                        SecureUtil.md5(list.get(0).getPassword()))) {
                        throw new MyException(ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), "用户名或密码不正确"));
                    }
                } else{
                    throw new MyException(ResultUtil.result(EnumCode.INTERNAL_SERVER_ERROR.getValue(), "用户不存在"));
                }
                traceRecord.setTr_userid(list.get(0).getUserid());
                traceRecord.setTr_realname(list.get(0).getRealname());
                traceRecord.setTr_department(list.get(0).getDepartment());
            } else {
                Subject subject = SecurityUtils.getSubject();
                User user = (User) subject.getPrincipal();
                traceRecord.setTr_userid(user.getUserid());
                traceRecord.setTr_realname(user.getRealname());
                traceRecord.setTr_department(user.getDepartment());
            }

            String[] ru = requestUrl.split("\\/");
            traceRecord.setTr_interface(ru[ru.length-1]);
            if("/api/login".equals(requestUrl)) {
                traceRecord.setTr_operation("用户登录");
            } else {
                traceRecord.setTr_operation("数据删除");
            }
            traceRecord.setTr_requesturl(requestUrl);
            traceRecord.setTr_remoteip(remoteAddr);
            traceRecord.setTr_params(params);
            traceRecord.setTr_method(method);
            traceRecord.setTr_permname("预留字段-暂时无值");
            traceRecord.setTr_flag("预留字段-暂时无值");
            traceRecord.setTr_recordtime(datestr);

            traceRecordService.addTraceRecord(traceRecord);
        }
    }
}
*/
