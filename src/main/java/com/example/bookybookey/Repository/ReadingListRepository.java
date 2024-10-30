package com.example.bookybookey.Repository;

import com.example.bookybookey.Model.ReadingList;
import com.example.bookybookey.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingListRepository extends JpaRepository<ReadingList, Integer> {

    ReadingList findReadingListByReadingListId(Integer readingListId);
  //  @Query("SELECT rl FROM ReadingList rl WHERE rl.readingListId =?1 AND rl.user =?2")
    ReadingList findReadingListByReadingListIdAndUser(Integer readingListId, User user);
    List<ReadingList> findAllByUser(User user);
}
