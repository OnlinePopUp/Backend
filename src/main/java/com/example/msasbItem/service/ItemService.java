package com.example.msasbItem.service;

import com.example.msasbItem.dto.ItemDto;
import com.example.msasbItem.entity.ItemEntity;
import com.example.msasbItem.entity.PopUpEntity;
import com.example.msasbItem.jwt.JwtUtil;
import com.example.msasbItem.repository.ItemRepository;
import com.example.msasbItem.repository.PopUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final PopUpRepository popUpRepository;
    private final JwtUtil jwtUtil;

    // 아이템 생성
    public void saveItem(String token, ItemDto itemDto) {
        // 이메일을 기반으로 PopUpEntity 조회
        PopUpEntity popUpEntity = popUpRepository.findByEmail(jwtUtil.getEmail(token))
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 팝업 스토어가 존재하지 않습니다."));

        System.out.println("해당 이메일을 가진 팝업 스토어가 존재합니다.");

        ItemEntity item = ItemEntity.builder()
                .popId(popUpEntity.getPopId())
                .name(itemDto.getName())
                .amount(itemDto.getAmount())
                .price(itemDto.getPrice())
                .des(itemDto.getDes())
                .email(jwtUtil.getEmail(token))
                .imageUrl(itemDto.getImageUrl())
                .build();

        itemRepository.save(item);
    }

    // 아이템 조회
    public List<ItemDto> getAllItems(Long popId) {
        // 이메일을 기반으로 PopUpEntity 조회
        PopUpEntity popUpEntity = popUpRepository.findByPopId(popId)
                .orElseThrow(() -> new IllegalArgumentException("해당 팝업 ID를 가진 팝업 스토어가 존재하지 않습니다."));
        List<ItemEntity> itemEntities = itemRepository.findByPopId(popUpEntity.getPopId());
        
        return itemEntities.stream()
                .map(i -> ItemDto.builder()
                        .itemId(i.getItemId())
                        .popId(popUpEntity.getPopId())
                        .name(i.getName())
                        .amount(i.getAmount())
                        .price(i.getPrice())
                        .des(i.getDes())
                        .email(i.getEmail())
                        .imageUrl(i.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    // 아이템 수정
    public void updateItem(Long popId, Long itemId, ItemDto itemDto) {
        ItemEntity popItem = itemRepository.findById(popId)
                .orElseThrow(() -> new RuntimeException("해당 popId에 해당하는 아이템을 찾을 수 없습니다."));
        
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("해당 itemId에 해당하는 아이템을 찾을 수 없습니다."));
        
        if (!popItem.getPopId().equals(item.getPopId())) {
            throw new IllegalArgumentException("해당 아이템은 요청한 popId에 속하지 않습니다.");
        }
        
        item.setName(itemDto.getName());
        item.setAmount(itemDto.getAmount());
        item.setPrice(itemDto.getPrice());
        item.setDes(itemDto.getDes());
        item.setImageUrl(itemDto.getImageUrl());
        
        itemRepository.save(item);
    }

    // 아이템 삭제
    public void deleteItem(Long popId, Long id) {
        ItemEntity popItem = itemRepository.findById(popId)
                .orElseThrow(() -> new RuntimeException("해당 popId에 해당하는 아이템을 찾을 수 없습니다."));

        ItemEntity item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 itemId에 해당하는 아이템을 찾을 수 없습니다."));

        itemRepository.delete(item);
    }
}
