package dev.kiki.forum.tag;

import dev.kiki.forum.exception.ResourceNotFoundException;
import dev.kiki.forum.tag.dto.CreateTagDto;
import dev.kiki.forum.tag.dto.TagResponseDto;
import dev.kiki.forum.tag.dto.UpdateTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag create(CreateTagDto createTagDto) {
        Tag tag = new Tag();
        tag.setTitle(createTagDto.title());
        tag.setDescription(createTagDto.description());

        return tagRepository.save(tag);
    }


    public Page<TagResponseDto> findAllWithStats(Pageable pageable, String filter) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursAgo = now.minusHours(24);
        LocalDateTime sevenDaysAgo = now.minusDays(7);

        return switch (filter.toLowerCase()) {
            case "popular" -> tagRepository.findPopularTags(pageable, twentyFourHoursAgo, sevenDaysAgo);
            case "new" -> tagRepository.findNewTags(pageable, twentyFourHoursAgo, sevenDaysAgo);
            case "name" -> tagRepository.findAllTagsSortedByName(pageable, twentyFourHoursAgo, sevenDaysAgo);
            default -> throw new IllegalArgumentException("Invalid filter value: " + filter);
        };

    }


    public Tag getTag(UUID id) {
        return tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tag cannot be found")
        );
    }


    public Tag updateTag(UUID tagId, UpdateTagDto updateTagDto) {
        var existingTag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag with id " + tagId + " cannot be found"));
        if (updateTagDto.title() != null) {
            existingTag.setTitle(updateTagDto.title());
        }if(updateTagDto.description() != null) {
            existingTag.setDescription(updateTagDto.description());
        }

        return tagRepository.save(existingTag);
    }

}
