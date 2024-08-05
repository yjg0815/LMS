package ALTERCAST.aLterMS.dto;

import lombok.Getter;

public class NotificationRequestDTO {

    @Getter
    public static class createNotiRequestDTO{
        String title;
        String description;
    }
}
