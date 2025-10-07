package model.dto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDTO {
    private String cusId;
    private String name;
    private String pno;
    private int age;
    private String city;
}
