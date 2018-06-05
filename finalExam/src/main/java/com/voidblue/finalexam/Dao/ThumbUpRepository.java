package com.voidblue.finalexam.Dao;

import com.voidblue.finalexam.Model.ThumbUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbUpRepository extends JpaRepository<ThumbUp, Integer> {
}
