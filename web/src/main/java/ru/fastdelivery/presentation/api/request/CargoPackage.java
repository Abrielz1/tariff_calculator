package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.fastdelivery.domain.common.delivery.Departure;
import ru.fastdelivery.domain.common.delivery.Destination;
import ru.fastdelivery.domain.common.dimesions.CargoDimensions;
import java.math.BigInteger;

public record CargoPackage(

        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        @Min(1)
        @Max(150)
        @Positive
        @NotNull
        BigInteger weight,

        @Positive
        @NotNull
        CargoDimensions dimensions,

        @Positive
        @NotNull
        Destination destination,

        @Positive
        @NotNull
        Departure departure
) {
}
