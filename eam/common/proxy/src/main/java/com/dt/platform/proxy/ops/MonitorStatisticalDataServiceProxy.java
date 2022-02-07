package com.dt.platform.proxy.ops;

import com.dt.platform.proxy.ServiceNames;
import org.github.foxnic.web.proxy.FeignConfiguration;
import org.github.foxnic.web.proxy.api.APIProxy;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = ServiceNames.OPS, contextId = MonitorNodeValueServiceProxy.API_CONTEXT_PATH , configuration = FeignConfiguration.class)
public interface MonitorStatisticalDataServiceProxy {

    /**
     * 基础路径 , service-ops
     */
    public static final String API_BASIC_PATH = "service-ops";

    /**
     * API 上下文路径 , ops-monitor-node-value
     */
    public static final String API_CONTEXT_PATH = "ops-statistics";

    /**
     * API 基础路径 , 由 API_BASIC_PATH 和 API_CONTEXT_PATH 两部分组成
     */
    public static final String API_PREFIX = "/" + API_BASIC_PATH + "/"+API_CONTEXT_PATH+"/";

    /**
     * 查询节点统计数据
     */
    public static final String QUERY_NODE_STATISTICS = API_PREFIX + "query-node-statistics";

    /**
     * 查询主机节点top数据
     */
    public static final String QUERY_NODE_HOST_TOP_DATA = API_PREFIX + "query-node-host-top-data";

    /**
     * 控制器类名
     * */
    public static final String CONTROLLER_CLASS_NAME="com.dt.platform.ops.controller.MonitorStatisticalDataController";

    /**
     * 统一的调用接口，实现在单体应用和微服务应用下的无差异调用
     * */
    public static MonitorStatisticalDataServiceProxy api() {
        return APIProxy.get(MonitorStatisticalDataServiceProxy.class,CONTROLLER_CLASS_NAME);
    }


}