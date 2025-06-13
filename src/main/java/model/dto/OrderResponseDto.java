package model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record OrderResponseDto(
        Integer id,
        UserResponseDto userResponseDto,
        List<ProductResponseDto> productResponseDtoList
) {

}
