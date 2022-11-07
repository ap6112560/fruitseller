package com.ankit.fruitseller.service;

import com.ankit.fruitseller.models.Combo;
import com.ankit.fruitseller.repository.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComboService {
    @Autowired
    private ComboRepository comboRepository;

    public Combo save(Combo combo) {
        return comboRepository.save(combo);
    }

    public Combo get(UUID orderId) {
        return comboRepository.findById(orderId).get();
    }

    public List<Combo> get() {
        return comboRepository.findAll();
    }
}
