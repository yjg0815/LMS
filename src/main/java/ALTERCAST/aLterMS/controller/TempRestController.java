package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.converter.TempConverter;
import ALTERCAST.aLterMS.dto.TempResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempRestController {

    @GetMapping("/test")
    public ApiResponse<TempResponse.TempTestDTO> testAPI(){

        return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
    }
}