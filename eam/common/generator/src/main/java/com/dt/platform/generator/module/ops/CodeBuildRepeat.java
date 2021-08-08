package com.dt.platform.generator.module.ops;

import java.lang.reflect.Method;

public class CodeBuildRepeat {
    public static void test(String args){


    }
    public static void main(String[] args) throws Exception {
        String pstr="com.dt.platform.generator.module.ops";
        String[] clasnamearr={
                "OpsHostServiceGtr",
                "OpsServiceDetailGtr",
                "OpsServiceGtr"
        };
        for(int i=0;i<clasnamearr.length;i++){
            System.out.println("invoke clsss:"+clasnamearr[i]);
            try{
                Class clazz=Class.forName(pstr+"."+clasnamearr[i]);
                Method method=clazz.getMethod("generateCode");
                method.invoke(clazz.newInstance());
            }catch(Exception e){
                System.out.println("这个类真的不存在!");
            }
            System.out.println("------------------------invoke finish------------------------");
        }
    }
}
