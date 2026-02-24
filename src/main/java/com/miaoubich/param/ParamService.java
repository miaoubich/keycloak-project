package com.miaoubich.param;

public interface ParamService {

    void saveParamValue(Param parameter);
    Param getParamById(Long id);
    Param updateParamValue(Param paramValue);
    void deleteParamById(Long id);
    void deleteAll();
}
