package ru.fastdelivery.domain.delivery.pack;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.fastdelivery.domain.common.dimesions.CargoDimensions;
import ru.fastdelivery.domain.common.weight.Weight;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(
        @NotNull
        @Min(1)
        @Max(150_000)
        Weight weight,

        @NotNull
        CargoDimensions dimensions) {
}
