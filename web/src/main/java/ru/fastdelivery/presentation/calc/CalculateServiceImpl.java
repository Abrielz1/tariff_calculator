package ru.fastdelivery.presentation.calc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;

@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    @Override
    public CalculatePackagesResponse calculate(CalculatePackagesRequest request) {
        return null;
    }
}
