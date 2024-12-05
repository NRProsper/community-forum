package dev.kiki.forum.tag;

import dev.kiki.forum.tag.dto.TagResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT COUNT (q) FROM Question  q JOIN q.tags t WHERE t.id = :tagId")
    Long countQuestionsByTagId(@Param("tagId") UUID tagId);

    @Query("SELECT COUNT(q) FROM Question q JOIN q.tags t WHERE t.id = :tagId AND q.createdAt >= :startOfDay")
    Long countQuestionsTodayByTagId(@Param("tagId") UUID tagId, @Param("startOfDay") LocalDateTime startOfDay);

    @Query("SELECT COUNT(q) FROM Question q JOIN q.tags t WHERE t.id = :tagId AND q.createdAt >= :startOfWeek")
    Long countQuestionsThisWeekByTagId(@Param("tagId") UUID tagId, @Param("startOfWeek") LocalDateTime startOfWeek);

    @Query("SELECT t FROM Tag t")
    Page<Tag> findAllPaginated(Pageable pageable);

    @Query("SELECT new dev.kiki.forum.tag.dto.TagResponseDto(" +
            "t.id, t.title, t.description, " +
            "COUNT(q), " +
            "COUNT(CASE WHEN q.createdAt >= :twentyFourHoursAgo THEN 1 END), " +
            "COUNT(CASE WHEN q.createdAt >= :sevenDaysAgo THEN 1 END)) " +
            "FROM Tag t " +
            "LEFT JOIN t.questions q " +
            "GROUP BY t.id")
    Page<TagResponseDto> findTagsWithStats(Pageable pageable,
                                           @Param("twentyFourHoursAgo") LocalDateTime twentyFourHoursAgo,
                                           @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Query("SELECT new dev.kiki.forum.tag.dto.TagResponseDto(" +
            "t.id, t.title, t.description, " +
            "COUNT(q), " +
            "COUNT(CASE WHEN q.createdAt >= :twentyFourHoursAgo THEN 1 END), " +
            "COUNT(CASE WHEN q.createdAt >= :sevenDaysAgo THEN 1 END)) " +
            "FROM Tag t " +
            "LEFT JOIN t.questions q " +
            "GROUP BY t.id " +
            "ORDER BY COUNT(q) DESC")
    Page<TagResponseDto> findPopularTags(Pageable pageable,
                                         @Param("twentyFourHoursAgo") LocalDateTime twentyFourHoursAgo,
                                         @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Query("SELECT new dev.kiki.forum.tag.dto.TagResponseDto(" +
            "t.id, t.title, t.description, " +
            "COUNT(q), " +
            "COUNT(CASE WHEN q.createdAt >= :twentyFourHoursAgo THEN 1 END), " +
            "COUNT(CASE WHEN q.createdAt >= :sevenDaysAgo THEN 1 END)) " +
            "FROM Tag t " +
            "LEFT JOIN t.questions q " +
            "WHERE t.createdAt >= :twentyFourHoursAgo " +
            "GROUP BY t.id " +
            "ORDER BY t.createdAt DESC")
    Page<TagResponseDto> findNewTags(Pageable pageable,
                                     @Param("twentyFourHoursAgo") LocalDateTime twentyFourHoursAgo,
                                     @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Query("SELECT new dev.kiki.forum.tag.dto.TagResponseDto(" +
            "t.id, t.title, t.description, " +
            "COUNT(q), " +
            "COUNT(CASE WHEN q.createdAt >= :twentyFourHoursAgo THEN 1 END), " +
            "COUNT(CASE WHEN q.createdAt >= :sevenDaysAgo THEN 1 END)) " +
            "FROM Tag t " +
            "LEFT JOIN t.questions q " +
            "GROUP BY t.id " +
            "ORDER BY t.title ASC")
    Page<TagResponseDto> findAllTagsSortedByName(Pageable pageable,
                                                 @Param("twentyFourHoursAgo") LocalDateTime twentyFourHoursAgo,
                                                 @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);



}
