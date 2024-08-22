package ru.fastdelivery.domain.common.dimesions;

import java.math.BigInteger;
/**
 * Размерность для опредиления габаритов в миллиметрах
 */
public record Length(
        BigInteger length
) {
}
