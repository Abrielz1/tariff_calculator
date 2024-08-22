package ru.fastdelivery.presentation.calc;

import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;

public interface CalculateService {

    CalculatePackagesResponse calculate(CalculatePackagesRequest request);
}
