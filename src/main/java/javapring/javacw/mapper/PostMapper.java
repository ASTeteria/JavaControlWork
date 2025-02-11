package javapring.javacw.mapper;


import javapring.javacw.dto.PostDto;
import javapring.javacw.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.id", target = "userId")
    PostDto toPostDto(Post post);

    @Mapping(source = "userId", target = "user.id")
    Post toPost(PostDto postDto);
}
