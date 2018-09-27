package com.tw.relife;

import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class BindedControllerHandler implements RelifeAppHandler {
    List<Class> controllers;

    public BindedControllerHandler(List<Class> controllers) {
        this.controllers = controllers;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        for(Class controller : controllers){
            Method[] methods = controller.getDeclaredMethods();
            for(Method method : methods){
                Annotation mappingAnnotation = method.getDeclaredAnnotation(RelifeRequestMapping.class);

                if(mappingAnnotation != null){
                    RelifeRequestMapping requestMapping = (RelifeRequestMapping) mappingAnnotation;
                    if(requestMapping.value().equals(request.getPath()) && requestMapping.method().equals(request.getMethod())){
                        try {
                            return (RelifeResponse) method.invoke(controller.newInstance(), request);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return new RelifeResponse(404);
    }
}
