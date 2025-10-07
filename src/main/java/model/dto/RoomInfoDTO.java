package model.dto;
import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomInfoDTO {
    private String roomID;
    private String type;
    private String description;
    private String price;

}
