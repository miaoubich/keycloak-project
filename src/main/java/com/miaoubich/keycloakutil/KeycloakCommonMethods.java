package com.miaoubich.keycloakutil;

import com.miaoubich.param.ParamRepository;
import org.springframework.stereotype.Component;

@Component
public class KeycloakCommonMethods {

    private final ParamRepository paramRepository;

    public KeycloakCommonMethods(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    public String getParamValueByParamName(String paramName) {
        return paramRepository.findByParamName(paramName).getParamValue();
    }
}
