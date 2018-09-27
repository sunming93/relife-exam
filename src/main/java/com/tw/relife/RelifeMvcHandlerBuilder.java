package com.tw.relife;

import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

public class RelifeMvcHandlerBuilder{
    private Set<Action> actions = new HashSet<>();

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        if(path == null || method == null || handler == null){
            throw new IllegalArgumentException("the parameter of addAction can't be null");
        }

        actions.add(new Action(path, method, handler));

        return this;
    }

    public RelifeMvcHandlerBuilder addController(Class controller) {
        if(controller == null){
            throw new IllegalArgumentException("the parameter of addController can't be null");
        }

        if(Modifier.isAbstract(controller.getModifiers())){
            throw new IllegalArgumentException("the parameter of addController can't be abstract");
        }

        if(controller.isInterface()){
            throw new IllegalArgumentException("the parameter of addController can't be interface");
        }

        if(controller.getDeclaredAnnotation(RelifeController.class) == null){
            throw new IllegalArgumentException("the parameter of addController don't have an annotation RelifeController");
        }


        addToActions(controller);

        return this;
    }

    private void addToActions(Class controller){
        Method[] methods = controller.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));

        for(Method method : methods){
            Annotation mappingAnnotation = method.getDeclaredAnnotation(RelifeRequestMapping.class);

            if(mappingAnnotation != null && isValidParameter(method)){
                RelifeRequestMapping requestMapping = (RelifeRequestMapping) mappingAnnotation;
                RelifeAppHandler handler = request ->
                {
                    try {
                        method.setAccessible(true);
                        return (RelifeResponse) method.invoke(controller.newInstance(), request);
                    }catch (InstantiationException e){}
                    catch (IllegalAccessException e1){}
                    catch (InvocationTargetException e2){
                        throw e2.getCause();
                    }
                    return null;
                };

                addAction(requestMapping.value(), requestMapping.method(),handler);
            }
        }
    }

    private boolean isValidParameter(Method method) {
        Parameter[] parameters = method.getParameters();
        if(parameters.length != 1){
            throw new IllegalArgumentException("the number of parameter isn't 1.");
        }

        if(parameters[0].getType() != RelifeRequest.class){
            throw new IllegalArgumentException("the type of parameter isn't RelifeRequest.");
        }
        return true;
    }

    public RelifeAppHandler build() {
            return new BindedActionHandler(new ArrayList<>(actions));
    }

}
