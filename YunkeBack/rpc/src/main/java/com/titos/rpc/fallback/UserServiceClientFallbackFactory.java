package com.titos.rpc.fallback;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import com.titos.info.user.vo.UserVO;
import com.titos.rpc.clients.UserServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceClientFallbackFactory implements FallbackFactory<UserServiceClient> {
    @Override
    public UserServiceClient create(Throwable throwable) {
        return new UserServiceClient() {
            @Override
            public CommonResult addUser(User user) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult queryUserIdDynamic(UserVO userVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult<User> queryUserByNameOrEmail(LoginVO loginVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult updateUserInfo(User user) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult queryUserById(Integer userId) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult queryUserByUserVo(UserVO userVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult querySystemUserCount() {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult<PageInfo<User>> queryAllUserByPage(Integer pageNum, Integer pageSize) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult deleteUserById(Integer id) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult<PageInfo> queryUserByCondition(UserQuery userQuery) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }
            /**
             * 通用的处理方法
             * @return
             */
            private CommonResult generalProcess() {
                return new CommonResult(StatusEnum.MICROSERVICE_ERROR.getCode(), StatusEnum.MICROSERVICE_ERROR.getMsg());
            }
        };
    }
}
