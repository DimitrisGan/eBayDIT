package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository  extends JpaRepository<ItemEntity, Long> {


    ItemEntity findByItemID(Long id);

    List<ItemEntity> findByEventStartedTrueAndEventFinishedFalse();

    Page<ItemEntity> findByItemIDIn(List<Long> ids, Pageable pageable);

}

