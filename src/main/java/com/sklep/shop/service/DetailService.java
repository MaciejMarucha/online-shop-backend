package com.sklep.shop.service;

import com.sklep.shop.model.Detail;
import com.sklep.shop.repository.DetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailService {

    private final DetailRepository detailRepository;

    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public void saveAll(List<Detail> details) {
        detailRepository.saveAll(details);
    }

    public List<Detail> findAll() {
        return detailRepository.findAll();
    }
}
