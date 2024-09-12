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
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import ru.fastdelivery.domain.common.currency.Currency;


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

    private Latitude latitudeDeparture; // ++

    private Longitude longitudeDeparture; // ++

    private Destination destination; // ++

    private Latitude latitudeDestination; // ++

    private Longitude longitudeDestination; // ++

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


        latitudeDestination = new Latitude(53.0d);

        longitudeDestination = new Longitude(58.0d);

        destination = new Destination(latitudeDestination, longitudeDestination);


        latitudeDeparture = new Latitude(45.0d);

        longitudeDeparture = new Longitude(55.0d);

        departure = new Departure(latitudeDeparture, longitudeDeparture);


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