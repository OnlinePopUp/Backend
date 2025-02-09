package com.example.msasbItem.service;

import com.example.msasbItem.dto.ItemCreateDto;
import com.example.msasbItem.entity.ItemEntity;
import com.example.msasbItem.entity.PopUpEntity;
import com.example.msasbItem.repository.ItemRepository;
import com.example.msasbItem.repository.PopUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final PopUpRepository popUpRepository;

    public void saveItem(String email, ItemCreateDto itemCreateDto) {
        // 이메일을 기반으로 PopUpEntity 조회
        PopUpEntity popUpEntity = popUpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 이메일을 가진 팝업 스토어가 존재하지 않습니다."));

        // ItemEntity 생성 및 저장
        ItemEntity item = ItemEntity.builder()
                .popId(popUpEntity.getPopId())  // PopUpEntity에서 popId 가져오기
                .name(itemCreateDto.getName())  // DTO에서 값 가져오기
                .amount(itemCreateDto.getAmount())
                .price(itemCreateDto.getPrice())
                .des(itemCreateDto.getDes())
                .build();

        itemRepository.save(item);
    }

    // 모든 아이템 조회
    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    // 특정 아이템 조회
    public ItemEntity getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "아이템을 찾을 수 없습니다."));
    }
}
