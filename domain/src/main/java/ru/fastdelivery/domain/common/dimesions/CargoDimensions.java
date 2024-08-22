package ru.fastdelivery.domain.common.dimesions;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

/**
 * @param length Длина
 * @param width Ширина
 * @param height Высота
 */
public record CargoDimensions(

        @Positive
        @Min(1)
        @Max(1500)
        Length length,

        @Positive
        @Min(1)
        @Max(1500)
        Length  width,

        @Positive
        @Min(1)
        @Max(1500)
        Length  height
) {

}
