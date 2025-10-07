package model.dto;
import lombok.*;

@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Setter
public class StaffInfoDTO {
    private String staffId;
    private String name;
    private String telno;
    private String email;
    private String reception;
    private double salary;
}
