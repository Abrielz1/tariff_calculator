package ru.fastdelivery.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Value;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.delivery.Departure;
import ru.fastdelivery.domain.common.delivery.Destination;
import ru.fastdelivery.domain.common.destinations.CoordinatesValue;
import ru.fastdelivery.domain.common.destinations.Latitude;
import ru.fastdelivery.domain.common.destinations.Longitude;
import ru.fastdelivery.domain.common.dimesions.CargoDimensions;
import ru.fastdelivery.domain.common.dimesions.Length;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ru.fastdelivery.domain.common.currency.Currency;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.DisplayName;
import org.assertj.core.util.BigDecimalComparator;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TariffCalculateUseCaseTest {

    @Mock
    private CurrencyFactory currencyFactory;

    @InjectMocks
    public TariffCalculateUseCase tariffCalculateUseCase;

    @Value("${cost.rub.perKg}")
    private Integer costPerKgs;

    private Currency currency; // +

    private Shipment shipment; // ++

    private Pack cargoUnit0; // ++

    private Pack cargoUnit1; // --

    private Pack cargoUnit2; // --

    private Price price; // --

    private Weight weight; // +

    private Departure departure; // ++

    private Destination destination; // ++

    private CoordinatesValue coordinatesValue; // ++

    private Latitude latitude; // ++

    private Longitude longitude; // ++

    private CargoDimensions cargoDimensions; // ++

    private Length lengthWidth; // ++

    private Length lengthHeight; // ++

    private Length lengthLength; // ++

    private List<Pack> packageofCargoList; // --

    @BeforeEach
    public void init() {
        //todo написать инициализацию груза и поставки

        currency = currencyFactory.create("RUB");

        // ============== 1st Package for Shipment ============

        weight = new Weight(BigInteger.valueOf(1000));

        lengthWidth = new Length(1200);

        lengthHeight = new Length(1000);

        lengthLength = new Length(1400);

        destination = new Destination(latitude, longitude);

        departure = new Departure(latitude, longitude);

        coordinatesValue = new CoordinatesValue(BigDecimal.valueOf(0.0d));

        latitude = new Latitude(coordinatesValue);

        longitude = new Longitude(coordinatesValue);

        cargoDimensions = new CargoDimensions(lengthLength, lengthWidth, lengthHeight);

        cargoUnit0 = new Pack(weight, cargoDimensions, (double) costPerKgs, 0.0d);

        // ============== 1st Package for Shipment ============

        packageofCargoList = List.of(cargoUnit0);

        shipment = new Shipment(packageofCargoList, 0.0d, 0.0d, currency, destination, departure);
    }

    @Test
    void calculatorPriceByCargoWeight() {

    }
}