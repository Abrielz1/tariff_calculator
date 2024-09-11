package ru.fastdelivery.domain.common.dimesions;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * length Длина
 * width  Ширина
 * height Высота
 */

@Setter
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CargoDimensions {

    @Positive
    @Min(1)
    @Max(1500)
    private Length length;

    @Positive
    @Min(1)
    @Max(1500)
    private Length width;

    @Positive
    @Min(1)
    @Max(1500)
    private Length height;
}
