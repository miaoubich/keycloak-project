package com.miaoubich.param;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParamServiceImpl implements ParamService {

    private final ParamRepository paramRepository;

    public ParamServiceImpl(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    @Transactional
    @Override
    public void saveParamValue(Param parameter) {
        paramRepository.save(parameter);
    }

    @Transactional(readOnly = true)
    @Override
    public Param getParamById(Long id) {
        return paramRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Param not found: " + id));
    }

    @Transactional
    @Override
    public Param updateParamValue(Param param) {
        Param existingParam = getParamById(param.getId());

        existingParam.setParamName(param.getParamName());
        existingParam.setParamValue(param.getParamValue());
        return paramRepository.save(existingParam);
    }

    @Transactional
    @Override
    public void deleteParamById(Long id) {
        Param existingParam = getParamById(id);
        paramRepository.delete(existingParam);
    }

    @Transactional
    @Override
    public void deleteAll() {
        paramRepository.deleteAll();
    }

}
