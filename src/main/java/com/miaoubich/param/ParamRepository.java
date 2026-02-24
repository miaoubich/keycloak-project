package com.miaoubich.param;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParamRepository extends JpaRepository<Param, Long> {

    Param findByParamName(String paramName);
}
