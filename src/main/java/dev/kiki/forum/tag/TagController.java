package dev.kiki.forum.tag;

import dev.kiki.forum.tag.dto.CreateTagDto;
import dev.kiki.forum.tag.dto.TagResponseDto;
import dev.kiki.forum.tag.dto.UpdateTagDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<?> createTag(@Valid @RequestBody CreateTagDto createTagDto) {
        Tag createdTag = tagService.create(createTagDto);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    Page<TagResponseDto> getAllTags(
            @RequestParam(defaultValue = "1", name = "page")int page,
            @RequestParam(defaultValue = "32", name = "size") int size,
            @RequestParam(defaultValue = "popular", name = "filter") String filter
            ) {
        Pageable pageable = PageRequest.of(page-1, size);

        return tagService.findAllWithStats(pageable, filter);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTag(@PathVariable(name = "tagId") UUID tagId) {
        return ResponseEntity.ok(tagService.getTag(tagId));
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(
            @PathVariable(name = "tagId") UUID tagId,
            @RequestBody UpdateTagDto updateTagDto
            ) {
        return ResponseEntity.ok(tagService.updateTag(tagId, updateTagDto));
    }

}
