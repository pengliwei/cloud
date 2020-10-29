package com.awei.ad.service.impl;


import com.awei.ad.dao.CreativeRepository;
import com.awei.ad.entity.Creative;
import com.awei.ad.service.ICreativeService;
import com.awei.ad.vo.CreativeRequest;
import com.awei.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 创意service实现层
 * @author: PENGLW
 * @date: 2020/10/28
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {

        Creative creative = creativeRepository.save(
                request.convertToEntity()
        );

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
