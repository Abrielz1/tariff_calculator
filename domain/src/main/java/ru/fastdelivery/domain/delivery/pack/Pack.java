package ru.fastdelivery.domain.delivery.pack;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fastdelivery.domain.common.dimesions.CargoDimensions;
import ru.fastdelivery.domain.common.weight.Weight;

/**
 * Упаковка груза
 * weight вес товаров в упаковке
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pack implements Comparable<Pack> {

    @NotNull
    @Min(1)
    @Max(150_000)
    private Weight weight;

    @NotNull
    private CargoDimensions dimensions;

    private Double pricePerCargoUnit;

    private Double volumeOfCargoUnit;

    @Override
    public int compareTo(Pack o) {
        return Double.compare(this.pricePerCargoUnit, o.pricePerCargoUnit);
    }
}
