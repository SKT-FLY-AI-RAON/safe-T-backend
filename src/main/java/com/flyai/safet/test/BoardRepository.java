package com.flyai.safet.test;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findBoardById(Long id);

    List<Board> findAll();

    List<Board> findAllByTitleContains(String keyword);

    List<Board> findAllByContentContains(String keyword);

}