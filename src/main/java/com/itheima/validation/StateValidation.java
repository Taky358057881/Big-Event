package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     *
     * @param value 将来要校验的数据
     * @param context
     * @return 如果返回false，校验不通过，返回true则校验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        if(value.equals("已发布") || value.equals("草稿")){
            return true;
        }

        return false;
    }
}
