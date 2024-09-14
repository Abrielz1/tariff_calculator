package ru.fastdelivery.domain.common.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Валюта для стоимости
 */

@Data
@AllArgsConstructor
public class Currency {
  private String code;

}
