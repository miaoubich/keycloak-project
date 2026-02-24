package com.miaoubich.param;

import org.springframework.stereotype.Service;

@Service
public class ParamServiceImpl implements ParamService {

    private final ParamRepository paramRepository;

    public ParamServiceImpl(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    @Override
    public void saveParamValue(Param parameter) {
        paramRepository.save(parameter);
    }

    @Override
    public Param getParamById(Long id) {
        return paramRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Param not found: " + id));
    }

    @Override
    public Param updateParamValue(Param param) {
        Param existingParam = getParamById(param.getId());

        existingParam.setParamName(param.getParamName());
        existingParam.setParamValue(param.getParamValue());
        return paramRepository.save(existingParam);
    }

    @Override
    public void deleteParamById(Long id) {
        Param existingParam = getParamById(id);
        paramRepository.delete(existingParam);
    }

    @Override
    public void deleteAll() {
        paramRepository.deleteAll();
    }

}
