package ru.fastdelivery.calc;

import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.usecase.TariffCalculateUseCase_old;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate/";
    @MockBean
    TariffCalculateUseCase_old useCase;
    @MockBean
    CurrencyFactory currencyFactory;

//    @Test
//    @DisplayName("Валидные данные для расчета стоимость -> Ответ 200")
//    void whenValidInputData_thenReturn200() {
//        var request = new CalculatePackagesRequest(
//                List.of(new CargoPackage(BigInteger.TEN)), "RUB");
//        var rub = new CurrencyFactory(code -> true).create("RUB");
//        when(useCase.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
//        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));
//
//        ResponseEntity<CalculatePackagesResponse> response =
//                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("Список упаковок == null -> Ответ 400")
//    void whenEmptyListPackages_thenReturn400() {
//        var request = new CalculatePackagesRequest(null, "RUB");
//
//        ResponseEntity<String> response = restTemplate.postForEntity(baseCalculateApi, request, String.class);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
}
